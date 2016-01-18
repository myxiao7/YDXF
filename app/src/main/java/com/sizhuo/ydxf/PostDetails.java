package com.sizhuo.ydxf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.adapter.MyPostDetailsAdapter;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity.ReplyData;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

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
    private PostDetailData postDetailData;//当前帖子数据
    private String replyCount; //回复数目
    private List<ReplyData> list = new ArrayList<>();//回复数据
    private View headView;
    private ImageView icon, img01, img02, img03;//发帖人头像， 3图
    private TextView nameTv, dataTV, titleTv, desTv, countTv; //发帖人ID, 时间, 标题, 内容, 回复数

    private EditText contentEdit;//回复内容
    private Button replyBtn;//回复

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;

    @Override
    protected void onResume() {
        super.onResume();
        dbManager = new MyApplication().getDbManager();
        //检查登录状态
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                loginFlag = true;
                Toast.makeText(PostDetails.this,"登录"+user.getNickName(),Toast.LENGTH_SHORT).show();
            }else{
                loginFlag = false;
                Toast.makeText(PostDetails.this,"没有登录",Toast.LENGTH_SHORT).show();
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
        listView.addHeaderView(headView);
        adapter = new MyPostDetailsAdapter(list, this);
        listView.setAdapter(adapter);
        //点击隐藏键盘
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                InputMethodManager imm = (InputMethodManager)
                        getSystemService(Context.INPUT_METHOD_SERVICE);
                imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
            }
        });

        replyBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(loginFlag==true){
                    Toast.makeText(PostDetails.this,"发帖",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(PostDetails.this, Publish.class);
                    PostDetails.this.startActivity(intent);
                }else{
                    Intent intent = new Intent(PostDetails.this, Login.class);
                    PostDetails.this.startActivity(intent);
                }
            }
        });
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
        postDetailData = (PostDetailData) intent.getSerializableExtra("data");
        //回复数目
        replyCount = postDetailData.getReplyCount();

        //没有回复的处理
        if(replyCount.equals("0")){
            Toast.makeText(PostDetails.this,"还没有回复",Toast.LENGTH_SHORT).show();
        }else{
            //回复数据
            list =  postDetailData.getReplyDatas();
        }

        //获取并处理当前帖子信息
        ImageLoaderHelper.getIstance().loadImg(postDetailData.getIconUrl(), icon);
        nameTv.setText(postDetailData.getId());
        dataTV.setText(postDetailData.getDate());
        titleTv.setText(postDetailData.getTitle());
        desTv.setText(postDetailData.getDes());
        countTv.setText(postDetailData.getReplyCount());
        if(postDetailData.getType().equals("1")){
            ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgUrl(), img01);
            img02.setVisibility(View.GONE);
            img03.setVisibility(View.GONE);
        }else if(postDetailData.getType().equals("2")){
            ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(0).getUrl(), img01);
            ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(1).getUrl(), img02);
            ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(2).getUrl(), img03);
        }else{
            img01.setVisibility(View.GONE);
            img02.setVisibility(View.GONE);
            img03.setVisibility(View.GONE);
        }
    }
}
