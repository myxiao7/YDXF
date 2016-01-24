package com.sizhuo.ydxf;

import android.app.ProgressDialog;
import android.content.Context;
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
import android.view.inputmethod.InputMethodManager;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._ReplyData;
import com.sizhuo.ydxf.entity.db.News;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.Const;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.shareboard.SnsPlatform;
import com.umeng.socialize.utils.ShareBoardlistener;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
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
    private List<_ReplyData> replyData = new LinkedList<_ReplyData>();//评论数据

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;
    private News cruNews;
    private Boolean loveFlag = false;

    //网络相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest,jsonObjectRequest2,jsonObjectRequest3;
    private final String TAG01 = "jsonObjectRequest";//获取评论
    private final String TAG02 = "jsonObjectRequest2";//评论TAG
    private final String TAG03 = "jsonObjectRequest3";//收藏TAG

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
                //查找当前用户 当前新闻 是否被收藏
                cruNews = dbManager.selector(News.class).where("username","=",user.getUserName()).and("docid","=",newsData.getDocid()).findFirst();
                if(cruNews!=null){
                    loveFlag = true;
                    Log.d("log.d","已收藏");
                }else{
                    loveFlag = false;
                    cruNews = new News();
                    cruNews.setUsername(user.getUserName());
                    cruNews.setDocid(newsData.getDocid());
                    cruNews.setTitle(newsData.getTitle());
                    cruNews.setUrl(newsData.getUrl());
                    cruNews.setPtime(newsData.getPtime());
                    cruNews.setReply(newsData.getReply());
                    Log.d("log.d","未收藏");
                }
                loginFlag = true;
//                Toast.makeText(NewsDetails.this,"登录"+user.getNickName(),Toast.LENGTH_SHORT).show();
            }else{
                loginFlag = false;
//                Toast.makeText(NewsDetails.this,"没有登录",Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        loadData();
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
                if(replyData!=null&&replyData.size()>0){
                    Intent intent = new Intent(NewsDetails.this, NewsComment.class);
                    intent.putExtra("datas",(Serializable)replyData);
                    intent.putExtra("id",newsData.getDocid());
                    startActivity(intent);
                }else{
//                    Toast.makeText(NewsDetails.this, "还没有回复", Toast.LENGTH_SHORT).show();
                }
            }
        });
        pubBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(loginFlag==true){
                    final String contentStr = replyEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(contentStr)) {
                        Toast.makeText(NewsDetails.this, "请填写评论内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialog = ProgressDialog.show(NewsDetails.this,
                            null, "正在提交", true, true);
                    dialog.show();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userName", user.getUserName());
                    map.put("userPwd", user.getUserPwd());
                    map.put("news",newsData.getDocid());
                    map.put("content", contentStr);
                    JSONObject jsonObject = new JSONObject(map);
                    Log.d("xinwen", jsonObject.toString());
                    jsonObjectRequest2 = new JsonObjectRequest(Request.Method.POST, Const.NEWSCOMMENT, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("xinwen", jsonObject.toString());
                            try {
                                //获取服务器code
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    Log.d("log.d", jsonObject.toString());
                                    _ReplyData reply = new _ReplyData();
                                    reply.setPortrait(user.getPortrait());
                                    reply.setNickName(user.getNickName());
                                    reply.setContent(contentStr);
                                    reply.setpTime("刚刚");
                                    replyData.add(reply);
                                    countTv.setText(replyData.size()+"");
                                    Toast.makeText(NewsDetails.this, "评论成功", Toast.LENGTH_SHORT).show();
                                } else if (code == 201) {
                                    Toast.makeText(NewsDetails.this, "您已被禁言", Toast.LENGTH_SHORT).show();
                                    //禁言
                                } else {
                                    Toast.makeText(NewsDetails.this, "失败，可能是网络问题", Toast.LENGTH_SHORT).show();
                                }
                                replyEdit.setText("");
                                replyEdit.clearFocus();
                                hideSoftInput(v);
                                dialog.dismiss();
                            } catch (JSONException e) {
                                e.printStackTrace();
                            }
                        }
                    }, new Response.ErrorListener() {
                        @Override
                        public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen", volleyError.getMessage());
                            Toast.makeText(NewsDetails.this, "失败，可能是网络问题", Toast.LENGTH_SHORT).show();
                            hideSoftInput(v);
                            dialog.dismiss();
                        }
                    });
                    jsonObjectRequest2.setTag(TAG02);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            queue.add(jsonObjectRequest2);
                        }
                    }, 1000);
                }else{
                    Intent intent = new Intent(NewsDetails.this, Login.class);
                    NewsDetails.this.startActivity(intent);
                }
            }
        });
    }

    /**
     * 加载数据
     */
    private void loadData() {
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Const.SELNEWSCOMMENT+newsData.getDocid()+"/1", null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("xinwen", jsonObject.toString());
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        Log.d("log.d", jsonObject.toString());
                        replyData = JSON.parseArray(jsonObject.getString("data"), _ReplyData.class);
                        if(replyData!=null){
                            countTv.setText(replyData.size()+"");
                        }
                    }  else if(code == 400){
                        countTv.setText("0");
                        replyData = new LinkedList<>();
//                        Toast.makeText(NewsDetails.this, "没有评论数据", Toast.LENGTH_SHORT).show();
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
    }

    private UMShareListener umShareListener = new UMShareListener() {
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Toast.makeText(NewsDetails.this, platform + " 分享成功", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Toast.makeText(NewsDetails.this,platform + " 分享失败", Toast.LENGTH_SHORT).show();
        }

        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Toast.makeText(NewsDetails.this, platform +" 分享取消", Toast.LENGTH_SHORT).show();
        }
    };

    private ShareBoardlistener shareBoardlistener = new ShareBoardlistener() {
        @Override
        public void onclick(SnsPlatform snsPlatform,SHARE_MEDIA share_media) {
            new ShareAction(NewsDetails.this).setPlatform(share_media).setCallback(umShareListener)
                    .withText(newsData.getDigest()+" "+"https://www.baidu.com")
                    .withTitle(newsData.getTitle())
                    .withMedia(new UMImage(NewsDetails.this,"https://www.baidu.com/img/bd_logo1.png"))
                            .share();
        }
    };


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        /** attention to this below ,must add this**/
        UMShareAPI.get(this).onActivityResult(requestCode, resultCode, data);

    }

    /**
     * 隐藏键盘
     * @param view
     */
    private void hideSoftInput(View view) {
        InputMethodManager imm = (InputMethodManager)
                getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
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
        //评价详情
        replyData = newsData.getReply();

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.news_menu, menu);
        if(loveFlag==true){
            menu.getItem(1).setTitle("已收藏");
            menu.getItem(1).setIcon(R.mipmap.ic_love_yet);
//            Toast.makeText(NewsDetails.this, "已收藏", Toast.LENGTH_SHORT).show();
        }else{
            menu.getItem(1).setTitle("未收藏");
            menu.getItem(1).setIcon(R.mipmap.ic_love_not);
//            Toast.makeText(NewsDetails.this, "未收藏", Toast.LENGTH_SHORT).show();
        }
        Log.d("log.d","menu..........");
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(final MenuItem item) {
        switch (item.getItemId()) {
            case R.id.news_menu_like:
                if(loginFlag==true){
                    if(loveFlag==false){
                        insertLove(item,"yet", "收藏成功");
                    }else{
                        insertLove(item,"not", "取消收藏");
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

    private void insertLove(final MenuItem item, final String flag, final String str) {
        Map<String, String> map = new HashMap<String, String>();
        map.put("userName", user.getUserName());
        map.put("userPwd", user.getUserPwd());
        map.put("news", newsData.getDocid());
        map.put("flag", flag);
        JSONObject jsonObject = new JSONObject(map);
        Log.d("xinwen", jsonObject.toString());
        jsonObjectRequest3 = new JsonObjectRequest(Request.Method.POST, Const.NEWSLOVE, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("xinwen", jsonObject.toString());
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if (code == 200) {
                        if(flag.equals("yet")){
                            item.setIcon(R.mipmap.ic_love_yet);
                            item.setTitle("已收藏");
                            loveFlag = true;
                            Log.d("log.d", "---------------"+cruNews.getDocid());
                            dbManager.save(cruNews);
                            Log.d("log.d", "DB+保存收藏新闻到本地");
                        }else if(flag.equals("not")){
                            dbManager.delete(News.class, WhereBuilder.b("username","=",user.getUserName()).and("docid","=",newsData.getDocid()));
                            item.setIcon(R.mipmap.ic_love_not);
                            item.setTitle("未收藏");
                            loveFlag = false;
                            Log.d("log.d", "DB+删除收藏新闻在本地");
                        }
                        Toast.makeText(NewsDetails.this, str+"成功", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(NewsDetails.this, "请到我的收藏同步", Toast.LENGTH_SHORT).show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen", volleyError.getMessage());
                Toast.makeText(NewsDetails.this, "可能是网络问题...", Toast.LENGTH_SHORT).show();
            }
        });
        jsonObjectRequest3.setTag(TAG03);
        queue.add(jsonObjectRequest3);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(TAG01);
        queue.cancelAll(TAG02);
        queue.cancelAll(TAG03);
    }
}
