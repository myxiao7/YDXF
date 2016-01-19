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

import com.sizhuo.ydxf.NewsDetails;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.MyCommentData;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  我的评论
 * Created by My灬xiao7
 * date: 2016/1/14
 *
 * @version 1.0
 */
public class MyComment extends AppCompatActivity {
    private Toolbar toolbar;
    private ZrcListView listView;
    private List<MyCommentData> list = new ArrayList<>();
    private MyCommentAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycomment);
        initViews();
        for (int i = 0; i < 4  ; i++) {
            MyCommentData date = new MyCommentData("我的第"+i+"条回复","2015-12-30", "中国闯进2108世界杯，   了么？");
            list.add(date);
        }
        adapter = new MyCommentAdapter(list,this);
        listView.setAdapter(adapter);
        /*listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(MyComment.this, NewsDetails.class);
                MyComment.this.startActivity(intent);
            }
        });*/
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.mycomment_toolbar);
        toolbar.setTitle("新闻评论");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.mycomment_list);
    }

    class MyCommentAdapter extends BaseAdapter {
        private List<MyCommentData> list;
        private Context context;

        public MyCommentAdapter(List<MyCommentData> list, Context context) {
            this.list = list;
            this.context = context;
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
                convertView = inflater.inflate(R.layout.mycomment_list_item, parent,false);
                holder = new ViewHolder();
                holder.contentTv = (TextView) convertView.findViewById(R.id.mycomment_list_item_content_tv);
                holder.dateTv = (TextView) convertView.findViewById(R.id.mycomment_list_item_date_tv);
                holder.titleTv = (TextView) convertView.findViewById(R.id.mycomment_list_item_title_tv);
                convertView.setTag(holder);
            }else{
                convertView.setTag(holder);
            }
            final MyCommentData date = list.get(position);
            holder.titleTv.setText(date.getContent());
            holder.dateTv.setText(date.getDate());
            holder.titleTv.setText(date.getTitle());
            holder.titleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyComment.this, NewsDetails.class);
                    MyComment.this.startActivity(intent);
                }
            });
            return convertView;
        }

        class ViewHolder{
            public TextView contentTv;
            public TextView dateTv;
            public TextView titleTv;
        }
    }
}
