package com.sizhuo.ydxf;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;

import com.sizhuo.ydxf.adapter.MyPostDetailsAdapter;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity.ReplyData;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

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
    private List<ReplyData> list = new ArrayList<>();//评论数据
    private EditText comEdit;//评论内容
    private Button comBtn;//提交评论

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_newscomment);
        //初始化控件
        initViews();
        ReplyData replyData = new ReplyData("","my灬xiao7","沙发","2015-1-13");
        ReplyData replyData2 = new ReplyData("","admin","呵呵","2015-1-13");
        ReplyData replyData3 = new ReplyData("","guest","沙发","2015-1-13");
        ReplyData replyData4 = new ReplyData("","test","沙发","2015-1-13");
        list.add(replyData);
        list.add(replyData2);
        list.add(replyData3);
        list.add(replyData4);
        adapter = new MyPostDetailsAdapter(list, this);
        listView.setAdapter(adapter);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.newscomment_toolbar);
        toolbar.setTitle("评论列表");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.newscomment_listview);
        comEdit = (EditText) findViewById(R.id.newscomment_reply_edit);
        comBtn = (Button) findViewById(R.id.newscomment_com_btn);
    }

}
