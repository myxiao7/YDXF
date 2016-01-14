package com.sizhuo.ydxf.per;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.NewsDetails;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.MyCollectionDate;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  我的收藏
 * Created by My灬xiao7
 * date: 2016/1/14
 *
 * @version 1.0
 */
public class MyCollection extends AppCompatActivity{
    private Toolbar toolbar;
    private ZrcListView listView;
    private List<MyCollectionDate> list = new ArrayList<>();
    private MyCollectionAdapter adapter;
    private Boolean isEdit = false;//是否处于编辑模式下
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollection);
        initViews();
        for (int i = 0; i < 4  ; i++) {
        MyCollectionDate date = new MyCollectionDate("我的第"+i+"条收藏","2015-12-30", "26");
            list.add(date);
        }
        adapter = new MyCollectionAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(MyCollection.this, NewsDetails.class);
                MyCollection.this.startActivity(intent);
            }
        });

    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.mycollection_toolbar);
        toolbar.setTitle("新闻收藏");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.mycollection_list);

    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.edit_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId()){
            case R.id.edit_menu_edit:
                //编辑模式
                if(item.getTitle().equals(getResources().getString(R.string.menu_edit_edit))){
                    isEdit = true;
                    item.setTitle(R.string.menu_edit_comp);
                }else{
                    isEdit = false;
                    item.setTitle(R.string.menu_edit_edit);
                }
                adapter = new MyCollectionAdapter(list);
                listView.setAdapter(adapter);
                adapter.notifyDataSetChanged();
                break;
        }
        return true;
    }

    class MyCollectionAdapter extends BaseAdapter{
        private List<MyCollectionDate> list;
        private HashMap<Integer,Integer> isVisiableMap = new HashMap<>();//用于存储删除按钮是否现实
        public MyCollectionAdapter(List<MyCollectionDate> list) {
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
                convertView = inflater.inflate(R.layout.mycollection_list_item, parent,false);
                holder = new ViewHolder();
                holder.titleTv = (TextView) convertView.findViewById(R.id.mycollection_list_item_title_tv);
                holder.dateTv = (TextView) convertView.findViewById(R.id.mycollection_list_item_date_tv);
                holder.replyCountTv = (TextView) convertView.findViewById(R.id.mycollection_list_item_reply_tv);
                holder.delBtn = (TextView) convertView.findViewById(R.id.mycollection_list_item_del_btn);
                convertView.setTag(holder);
            }else{
                convertView.setTag(holder);
            }
            final MyCollectionDate date = list.get(position);
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
                    adapter = new MyCollectionAdapter(list);
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
