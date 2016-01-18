package com.sizhuo.ydxf.per;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.NewsDetails;
import com.sizhuo.ydxf.PostDetails;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.MyCollectionDate;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity.ReplyData;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  我的帖子
 * Created by My灬xiao7
 * date: 2016/1/14
 *
 * @version 1.0
 */
public class MyPost extends AppCompatActivity {
    private Toolbar toolbar;
    private ZrcListView listView;
    private List<PostDetailData> list = new ArrayList<>();
    private List<ReplyData> replyDataList = new ArrayList<>();
    private MyPostAdapter adapter;
    private TextView delSwitchBtn;
    private Boolean isEdit = false;//是否处于编辑模式下
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.mypost_toolbar);
        toolbar.setTitle("我的帖子");
        setSupportActionBar(toolbar);
        delSwitchBtn = (TextView) findViewById(R.id.mypost_toolbar_btn);
        listView = (ZrcListView) findViewById(R.id.mypost_list);
        ReplyData replyData =  new ReplyData("","admin","沙发","2015-12-30");
        ReplyData replyData2 =  new ReplyData("","admin","二楼","2015-12-30");
        ReplyData replyData3 =  new ReplyData("","admin","合影","2015-12-30");
        replyDataList.add(replyData);
        replyDataList.add(replyData2);
        replyDataList.add(replyData3);
        for (int i = 0; i < 4  ; i++) {
            PostDetailData date = new PostDetailData("","", "2015-12-25","","16","我发表的帖子", "我是来水经验的","3","",null,replyDataList);
            list.add(date);
        }
        adapter = new MyPostAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                //获取选中帖子数据
                PostDetailData postDetailData = list.get(position);
                Intent intent = new Intent(MyPost.this, PostDetails.class);
                //传递选中帖子数据
                intent.putExtra("data", postDetailData);
                startActivity(intent);
            }
        });
        delSwitchBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //编辑模式
                if (delSwitchBtn.getText().equals("编辑")) {
                    isEdit = true;
                    delSwitchBtn.setText("完成");
                } else {
                    isEdit = false;
                    delSwitchBtn.setText("编辑");
                }
                adapter = new MyPostAdapter(list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
            }
        });
    }

    class MyPostAdapter extends BaseAdapter {
        private List<PostDetailData> list;
        private HashMap<Integer,Integer> isVisiableMap = new HashMap<>();//用于存储删除按钮是否现实

        public MyPostAdapter(List<PostDetailData> list) {
            this.list = list;
            if(isEdit){
                //编辑模式下，显示删除按钮
                configVisiable(View.VISIBLE);
            }else{
                configVisiable(View.GONE);
            }
        }

        /**
         *  初始化按钮显隐情况
         * @param visi View.VISIBLE---显示,
         *             View.GONE---隐藏
         *
         */
        private void configVisiable(Integer visi){
            for (int i = 0; i < list.size(); i++) {
                isVisiableMap.put(i, visi);
            }
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.mypost_list_item, parent,false);
                holder = new ViewHolder();
                holder.titleTv = (TextView) convertView.findViewById(R.id.mypost_list_item_title_tv);
                holder.dateTv = (TextView) convertView.findViewById(R.id.mypost_list_item_date_tv);
                holder.replyCountTv = (TextView) convertView.findViewById(R.id.mypost_list_item_reply_tv);
                holder.delBtn = (TextView) convertView.findViewById(R.id.mypost_list_item_del_btn);
                convertView.setTag(holder);
            }else{
                convertView.setTag(holder);
            }
            final PostDetailData date = list.get(position);
            holder.titleTv.setText(date.getTitle());
            holder.dateTv.setText(date.getDate());
            holder.replyCountTv.setText(date.getReplyCount());
            if(isVisiableMap.get(position)==View.VISIBLE){
                holder.delBtn.setVisibility(View.VISIBLE);
            }else{
                holder.delBtn.setVisibility(View.GONE);
            }
            holder.delBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(date);
                    adapter = new MyPostAdapter(list);
                    listView.setAdapter(adapter);
                    adapter.notifyDataSetChanged();
                }
            });
            return convertView;
        }

        class ViewHolder{
            public TextView titleTv;
            public TextView dateTv;
            public TextView replyCountTv;
            public TextView delBtn;
        }
    }
}
