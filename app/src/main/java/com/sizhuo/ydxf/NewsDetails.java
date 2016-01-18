package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

/**
 * 项目名称: YDXF
 * 类描述:  新闻详情
 * Created by My灬xiao7
 * date: 2016/1/12
 *
 * @version 1.0
 */
public class NewsDetails extends AppCompatActivity{
    private Toolbar toolbar;
    private WebView webView;
    private EditText replyEdit;//评论编辑
    private TextView countTv;//评论数
    private Button pubBtn;//发布

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;

    @Override
    public void onBackPressed() {
        if(replyEdit.hasFocus()){
            replyEdit.clearFocus();
        }else{
            this.finish();
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager = new MyApplication().getDbManager();
        //检查登录状态
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                loginFlag = true;
                Toast.makeText(NewsDetails.this,"登录"+user.getNickName(),Toast.LENGTH_SHORT).show();
            }else{
                loginFlag = false;
                Toast.makeText(NewsDetails.this,"没有登录",Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newsdetails);
        initViews();
        webView = (WebView) findViewById(R.id.newsdetails_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl("http://www.sizsoft.cn/index");

        replyEdit.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    countTv.setVisibility(View.GONE);
                    pubBtn.setVisibility(View.VISIBLE);
                } else {
                    countTv.setVisibility(View.VISIBLE);
                    pubBtn.setVisibility(View.GONE);
                }
            }
        });
        countTv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(NewsDetails.this, NewsComment.class);
                startActivity(intent);
            }
        });
        pubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginFlag==true){
                    Toast.makeText(NewsDetails.this,"发表...",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(NewsDetails.this, Login.class);
                    NewsDetails.this.startActivity(intent);
                }
            }
        });
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(NewsDetails.this, platform + " 分享成功啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NewsDetails.this,platform + " 分享失败啦", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NewsDetails.this, platform +" 分享取消啦", Toast.LENGTH_SHORT).show();
        }
    };

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {

        @Override
        public void onclick(SnsPlatform snsPlatform,SHARE_MEDIA share_media) {
            new ShareAction(NewsDetails.this).setPlatform(share_media).setCallback(umShareListener)
                    .withText("业达先锋")
                    .withTitle("为什么需要新闻许可证明")
                    .withTargetUrl("http://www.baidu.com")
                            .share();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.newsdetails_toolbar);
        toolbar.setTitle("返回");
        setSupportActionBar(toolbar);
        replyEdit = (EditText) findViewById(R.id.newsdetails_reply_edit);
        countTv = (TextView) findViewById(R.id.newsdetails_count_tv);
        pubBtn = (Button) findViewById(R.id.newsdetails_pub_btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.news_menu_like:
                item.setIcon(R.mipmap.ic_love_yet);
                if(loginFlag==true){
                    Toast.makeText(NewsDetails.this,"收藏成功",Toast.LENGTH_SHORT).show();
                }else{
                    Intent intent = new Intent(NewsDetails.this, Login.class);
                    NewsDetails.this.startActivity(intent);
                }
                break;

            case R.id.news_menu_share:
                final SHARE_MEDIA[] displaylist = new SHARE_MEDIA[]
                        {
                                SHARE_MEDIA.WEIXIN, SHARE_MEDIA.WEIXIN_CIRCLE,SHARE_MEDIA.SINA,
                                SHARE_MEDIA.QQ, SHARE_MEDIA.QZONE
                        };

                new ShareAction(this).setDisplayList(displaylist)
                        .setShareboardclickCallback(shareBoardlistener)
                        .open();
                break;
        }
        return true;
    }
}
