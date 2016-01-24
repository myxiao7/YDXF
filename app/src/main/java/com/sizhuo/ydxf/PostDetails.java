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
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.adapter.MyPostDetailsAdapter;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity.ReplyData;
import com.sizhuo.ydxf.entity._PostDetailData;
import com.sizhuo.ydxf.entity._ReplyData;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: YDXF
 * 类描述:  帖子详情
 * Created by My灬xiao7
 * date: 2016/1/7
 *
 * @version 1.0
 */
public class PostDetails extends AppCompatActivity{
    private Toolbar toolbar;
    private ZrcListView listView;
    private MyPostDetailsAdapter adapter;
    private _PostDetailData postDetailData;//当前帖子数据
    private String replyCount, postID; //回复数目,帖子ID
    private List<_ReplyData> list = new LinkedList<_ReplyData>();//回复数据
    private View headView;
    private ImageView icon, replyIcon, img01, img02, img03;//发帖人头像，回复数目图, 3图
    private TextView nameTv, dataTV, titleTv, desTv, countTv; //发帖人ID, 时间, 标题, 内容, 回复数

    private EditText contentEdit;//回复内容
    private Button replyBtn;//回复

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;

    //网络相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG

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
//                Toast.makeText(PostDetails.this,"登录"+user.getNickName(),Toast.LENGTH_SHORT).show();
            }else{
                loginFlag = false;
//                Toast.makeText(PostDetails.this,"没有登录",Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_postdetails);
        //初始化控件
        initViews();
        queue = Volley.newRequestQueue(this);
        listView.addHeaderView(headView);
        adapter = new MyPostDetailsAdapter(list, this);
        listView.setAdapter(adapter);
        //点击隐藏键盘
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                hideSoftInput(view);
            }
        });

        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(final View v) {
                if (loginFlag == true) {
                    final String contentStr = contentEdit.getText().toString().trim();
                    if (TextUtils.isEmpty(contentStr)) {
                        Toast.makeText(PostDetails.this, "请填写回复内容", Toast.LENGTH_SHORT).show();
                        return;
                    }
                    dialog = ProgressDialog.show(PostDetails.this,
                            null, "正在提交", true, true);
                    dialog.show();
                    Map<String, String> map = new HashMap<String, String>();
                    map.put("userName", user.getUserName());
                    map.put("userPwd", user.getUserPwd());
                    map.put("iP", "火星");
                    map.put("card", postID);
                    map.put("content", contentEdit.getText().toString().trim());
                    JSONObject jsonObject = new JSONObject(map);
                    Log.d("xinwen", jsonObject.toString());
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.POSTREPLY, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("xinwen", jsonObject.toString());
                            try {
                                //获取服务器code
                                int code = jsonObject.getInt("code");
                                if (code == 200) {
                                    Log.d("log.d", jsonObject.toString());
                                    _ReplyData replyData = new _ReplyData();
                                    replyData.setPortrait(user.getPortrait());
                                    replyData.setNickName(user.getNickName());
                                    replyData.setContent(contentStr);
                                    replyData.setpTime("刚刚");
                                    list.add(replyData);
                                    adapter.notifyDataSetChanged(list);
                                    listView.setSelection(list.size());
                                    int count = 0;
                                    if (TextUtils.isEmpty(countTv.getText().toString())) {
                                        replyIcon.setVisibility(View.VISIBLE);
                                        count++;
                                    } else {
                                        count = Integer.parseInt(countTv.getText().toString()) + 1;
                                    }
                                    countTv.setText(count + "");
                                    Toast.makeText(PostDetails.this, "回复成功", Toast.LENGTH_SHORT).show();
                                } else if (code == 201) {
                                    Toast.makeText(PostDetails.this, "您已被禁言", Toast.LENGTH_SHORT).show();
                                    //禁言
                                } else {
                                    Toast.makeText(PostDetails.this, "回复失败", Toast.LENGTH_SHORT).show();
                                }
                                contentEdit.setText("");
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
                            Toast.makeText(PostDetails.this, "回复失败,网络异常", Toast.LENGTH_SHORT).show();
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
                    }, 2000);
                } else {
                    Intent intent = new Intent(PostDetails.this, Login.class);
                    PostDetails.this.startActivity(intent);
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
        toolbar = (Toolbar) findViewById(R.id.postdetails_toolbar);
        toolbar.setTitle("查看帖子");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.postdetails_list);
        replyBtn = (Button) findViewById(R.id.postdetails_reply_btn);
        contentEdit = (EditText) findViewById(R.id.postdetails_content_edit);
        LayoutInflater inflater = getLayoutInflater();
        headView = inflater.inflate(R.layout.postdetails_list_header, null, false);
        icon = (ImageView) headView.findViewById(R.id.postdetails_list_header_icon_img);
        replyIcon = (ImageView) headView.findViewById(R.id.postdetails_list_header_replycount_img);
        nameTv = (TextView) headView.findViewById(R.id.postdetails_list_header_name_tv);
        dataTV = (TextView) headView.findViewById(R.id.postdetails_list_header_date_tv);
        titleTv = (TextView) headView.findViewById(R.id.postdetails_list__header_title_tv);
        desTv = (TextView) headView.findViewById(R.id.postdetails_list_header_des_tv);
        countTv = (TextView) headView.findViewById(R.id.postdetails_list_header_replycount_tv);
        img01 = (ImageView) headView.findViewById(R.id.postdetails_list_header_img01);
        img02 = (ImageView) headView.findViewById(R.id.postdetails_list_header_img02);
        img03 = (ImageView) headView.findViewById(R.id.postdetails_list_header_img03);
        //获取数据
        Intent intent = this.getIntent();
        postDetailData = (_PostDetailData) intent.getSerializableExtra("data");
        //帖子ID
        postID = postDetailData.getId();
        //回复数目
        replyCount = postDetailData.getReplyCount();

        //没有回复的处理
        if(TextUtils.isEmpty(replyCount)){
//            Toast.makeText(PostDetails.this,"还没有回复",Toast.LENGTH_SHORT).show();
            replyIcon.setVisibility(View.GONE);
        }else{
            //回复数据
            list =  postDetailData.getReply();
            replyIcon.setVisibility(View.VISIBLE);
        }

        //获取并处理当前帖子信息
        if(!TextUtils.isEmpty(postDetailData.getPortrait())){
            ImageLoaderHelper.getIstance().loadImg(postDetailData.getPortrait(), icon);
        }
        nameTv.setText(postDetailData.getNickName());
        dataTV.setText(postDetailData.getPtime());
        titleTv.setText(postDetailData.getTitle());
        desTv.setText(postDetailData.getDigest());
        countTv.setText(postDetailData.getReplyCount());
        if(postDetailData.getImgextra().size()!=0){
            if(postDetailData.getImgextra().size()==1){
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(0).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(0).getUrl(), img01);
                }
                img02.setVisibility(View.GONE);
                img03.setVisibility(View.GONE);
            }
            if(postDetailData.getImgextra().size()==2){
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(0).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(0).getUrl(), img01);
                }
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(1).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(1).getUrl(), img02);
                }
                img03.setVisibility(View.GONE);
            }
            if(postDetailData.getImgextra().size()==3){
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(0).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(0).getUrl(), img01);
                }
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(1).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(1).getUrl(), img02);
                }
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(2).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(2).getUrl(), img03);
                }
            }
        }else{
            img01.setVisibility(View.GONE);
            img02.setVisibility(View.GONE);
            img03.setVisibility(View.GONE);
        }
    }
}
