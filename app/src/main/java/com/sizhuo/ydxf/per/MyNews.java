package com.sizhuo.ydxf.per;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.MyNewsData;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

import static com.sizhuo.ydxf.R.id.mynews_list_item_reply_icon_img;

/**
 * 项目名称: YDXF
 * 类描述:  我的消息
 * Created by My灬xiao7
 * date: 2016/1/14
 *
 * @version 1.0
 */
public class MyNews extends AppCompatActivity {
    private Toolbar toolbar;
    private ZrcListView listView;
    private List<MyNewsData> list = new ArrayList<>();
    private MyNewsAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynews);
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.mynews_toolbar);
        toolbar.setTitle("我的消息");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.mynews_list);
        for (int i = 0; i < 4  ; i++) {
            MyNewsData date = new MyNewsData("","admin","2015-01-14","恭喜 评为精品贴","我来水经验了");
            list.add(date);
        }
        adapter = new MyNewsAdapter(list,this);
        listView.setAdapter(adapter);
        /*listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(MyComment.this, NewsDetails.class);
                MyComment.this.startActivity(intent);
            }
        });*/
    }

    class MyNewsAdapter extends BaseAdapter {
        private List<MyNewsData> list;
        private Context context;

        public MyNewsAdapter(List<MyNewsData> list, Context context) {
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
                convertView = inflater.inflate(R.layout.mynews_list_item, parent,false);
                holder = new ViewHolder();

                holder.icon = (ImageView) convertView.findViewById(mynews_list_item_reply_icon_img);
                holder.nameTv = (TextView) convertView.findViewById(R.id.mynews_list_item_reply_name_tv);
                holder.dateTv = (TextView) convertView.findViewById(R.id.mynews_list_item_reply_date_tv);
                holder.contentTv = (TextView) convertView.findViewById(R.id.mynews_list_item_reply_content_tv);
                holder.titleTv = (TextView) convertView.findViewById(R.id.mynews_list_item_post_tv);
                convertView.setTag(holder);
            }else{
                convertView.setTag(holder);
            }
            final MyNewsData date = list.get(position);
            ImageLoaderHelper.getIstance().loadImg(date.getReplyIcon(), holder.icon);
            holder.nameTv.setText(date.getReplyName());
            holder.dateTv.setText(date.getReplyDate());
            holder.contentTv.setText(date.getReplyContent());
            holder.titleTv.setText(date.getPost());
            /*holder.titleTv.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyNews.this, NewsDetails.class);
                    MyNews.this.startActivity(intent);
                }
            });*/
            return convertView;
        }

        class ViewHolder{
            public ImageView icon;
            public TextView nameTv;
            public TextView dateTv;
            public TextView contentTv;
            public TextView titleTv;
        }
    }
}
