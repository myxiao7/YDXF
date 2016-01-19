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
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.sizhuo.ydxf.adapter.MyPostDetailsAdapter;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity.ReplyData;
import com.sizhuo.ydxf.entity._ReplyData;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: YDXF
 * 类描述:  新闻评论
 * Created by My灬xiao7
 * date: 2016/1/13
 *
 * @version 1.0
 */
public class NewsComment extends AppCompatActivity {
    private Toolbar toolbar;
    private ZrcListView listView;
    private MyPostDetailsAdapter adapter;
    private List<_ReplyData> list = new ArrayList<>();//评论数据
    private EditText comEdit;//评论内容
    private Button comBtn;//提交评论

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;

    //网络相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//评论TAG

    private ProgressDialog dialog;

    @Override
    protected void onResume() {
        super.onResume();
        dbManager = new MyApplication().getDbManager();
        //检查登录状态
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                loginFlag = true;
                Toast.makeText(NewsComment.this, "登录" + user.getNickName(), Toast.LENGTH_SHORT).show();
            }else{
                loginFlag = false;
                Toast.makeText(NewsComment.this,"没有登录",Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newscomment);
        //初始化控件
        initViews();
        adapter = new MyPostDetailsAdapter(list, this);
        listView.setAdapter(adapter);
        comBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if(loginFlag==true){
                    final String contentStr = comEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(contentStr)) {
                        Toast.makeText(NewsComment.this, "请填写评论内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialog = ProgressDialog.show(NewsComment.this,
                            null, "正在提交", true, true);
                    dialog.show();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userName", user.getUserName());
                    map.put("userPwd", user.getUserPwd());
                    map.put("news","");
                    map.put("content", contentStr);
                    JSONObject jsonObject = new JSONObject(map);
                    Log.d("xinwen", jsonObject.toString());
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.NEWSCOMMENT, jsonObject, new Response.Listener<JSONObject>() {
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
                                    list.add(reply);
                                    adapter.notifyDataSetChanged(list);
                                    listView.setSelection(list.size());
                                    Toast.makeText(NewsComment.this, "评论成功", Toast.LENGTH_SHORT).show();
                                } else if (code == 201) {
                                    Toast.makeText(NewsComment.this, "您已被禁言", Toast.LENGTH_SHORT).show();
                                    //禁言
                                } else {
                                    Toast.makeText(NewsComment.this, "失败，可能是人品问题", Toast.LENGTH_SHORT).show();
                                }
                                comEdit.setText("");
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
                            hideSoftInput(v);
                            dialog.dismiss();
                        }
                    });
                    jsonObjectRequest.setTag(TAG01);
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            queue.add(jsonObjectRequest);
                        }
                    }, 1000);
                }else{
                    Intent intent = new Intent(NewsComment.this, Login.class);
                    NewsComment.this.startActivity(intent);
                }
            }
        });
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
        toolbar = (Toolbar) findViewById(R.id.newscomment_toolbar);
        toolbar.setTitle("评论列表");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                NewsComment.this.finish();
            }
        });
        listView = (ZrcListView) findViewById(R.id.newscomment_listview);
        comEdit = (EditText) findViewById(R.id.newscomment_reply_edit);
        comBtn = (Button) findViewById(R.id.newscomment_com_btn);
        list = (List<_ReplyData>) getIntent().getSerializableExtra("datas");
    }

}
