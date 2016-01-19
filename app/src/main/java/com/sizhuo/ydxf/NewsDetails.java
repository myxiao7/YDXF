package com.sizhuo.ydxf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._ReplyData;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.HashMap;
import java.util.Map;

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

    private _NewsData newsData;//新闻数据

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;

    //网络相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG

    private ProgressDialog dialog;

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
        queue = Volley.newRequestQueue(this);
        webView = (WebView) findViewById(R.id.newsdetails_webview);
        webView.getSettings().setJavaScriptEnabled(true);
        webView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        webView.getSettings().setSupportMultipleWindows(true);
        webView.setWebViewClient(new WebViewClient());
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(newsData.getUrl());

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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsDetails.this.finish();
            }
        });
        replyEdit = (EditText) findViewById(R.id.newsdetails_reply_edit);
        countTv = (TextView) findViewById(R.id.newsdetails_count_tv);
        pubBtn = (Button) findViewById(R.id.newsdetails_pub_btn);
        Intent intent = this.getIntent();
        newsData = (_NewsData) intent.getSerializableExtra("data");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);
        if(false){
            menu.getItem(1).setTitle("已收藏");
            menu.getItem(1).setIcon(R.mipmap.ic_love_yet);
        }else{
            menu.getItem(1).setTitle("未收藏");
            menu.getItem(1).setIcon(R.mipmap.ic_love_not);
        }
            Toast.makeText(NewsDetails.this,"收藏成功",Toast.LENGTH_SHORT).show();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.news_menu_like:
                if(loginFlag==true){
                    if(item.getTitle().equals("未收藏")){
                        Map<String, String> map = new HashMap<String, String>();
                        map.put("userName", user.getUserName());
                        map.put("userPwd", user.getUserPwd());
                        map.put("news", newsData.getDocid());
                        JSONObject jsonObject = new JSONObject(map);
                        Log.d("xinwen", jsonObject.toString());
                        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.NEWSLOVE, jsonObject, new Response.Listener<JSONObject>() {
                            @Override
                            public void onResponse(JSONObject jsonObject) {
                                Log.d("xinwen", jsonObject.toString());
                                try {
                                    //获取服务器code
                                    int code = jsonObject.getInt("code");
                                    if (code == 200) {
                                        item.setIcon(R.mipmap.ic_love_yet);
                                        item.setTitle("已收藏");
                                        Toast.makeText(NewsDetails.this, "已收藏", Toast.LENGTH_SHORT).show();
                                    } else {
                                        Toast.makeText(NewsDetails.this, "失败，可能是人品问题", Toast.LENGTH_SHORT).show();
                                    }

                                } catch (JSONException e) {
                                    e.printStackTrace();
                                }
                            }
                        }, new Response.ErrorListener() {
                            @Override
                            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen", volleyError.getMessage());
                            }
                        });
                        jsonObjectRequest.setTag(TAG01);
                        queue.add(jsonObjectRequest);
                    }else{
                        item.setIcon(R.mipmap.ic_love_not);
                        item.setTitle("未收藏");
                        Toast.makeText(NewsDetails.this,"收藏取消",Toast.LENGTH_SHORT).show();
                    }
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
