package com.sizhuo.ydxf.per;

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
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.NewsDetails;
import com.sizhuo.ydxf.PostDetails;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.MyCollectionDate;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity.ReplyData;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._PostDetailData;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.SimpleFooter;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private LinearLayout loading;
    private ZrcListView listView;
    private List<_PostDetailData> list = new ArrayList<>();
    private MyPostAdapter adapter;
    private TextView delSwitchBtn;
    private Boolean isEdit = false;//是否处于编辑模式下
    private int index = 1;

    private String userName = "";
    private String userPwd = "";

    //网络请求
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mypost);
        initViews();
        queue = Volley.newRequestQueue(this);
        adapter = new MyPostAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                //获取选中帖子数据
                _PostDetailData postDetailData = list.get(position);
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
        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                index++;
                loadMoreData(index);
            }
        });
        //加载数据
        loadData();
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPwd", userPwd);
        JSONObject object = new JSONObject(map);
        Log.d("log.d", object.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.MYPOST+"1", object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d", "收藏"+jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        list = JSON.parseArray(jsonObject.getString("data"), _PostDetailData.class);
                        adapter.notifyDataSetChanged(list);
                        if(list.size()==20){
                            listView.startLoadMore();
                        }
                    }else if(code == 400){
                        Toast.makeText(MyPost.this,"没有数据",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(MyPost.this,"加载错误",Toast.LENGTH_SHORT).show();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading.setVisibility(View.GONE);
                        }
                    }, 800);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen",volleyError.toString()+volleyError);
                loading.setVisibility(View.GONE);
                Toast.makeText(MyPost.this,"网络不给力",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
        jsonObjectRequest.setTag(TAG01);
    }

    private void loadMoreData(int index) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPwd", userPwd);
        JSONObject object = new JSONObject(map);
        Log.d("log.d", object.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.MYPOST+index, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d", "收藏"+jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        List<_PostDetailData> list2 = JSON.parseArray(jsonObject.getString("data"), _PostDetailData.class);
                        for (_PostDetailData data:list2) {
                            list.add(data);
                        }
                        adapter.notifyDataSetChanged(list);
                        listView.setLoadMoreSuccess();
                    }else if(code == 400){
                        Toast.makeText(MyPost.this,"没有更多了",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }else{
                        Toast.makeText(MyPost.this,"加载错误",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("xinwen",volleyError.toString()+volleyError);
                Toast.makeText(MyPost.this,"网络不给力",Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 1000);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.mypost_toolbar);
        toolbar.setTitle("我的帖子");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyPost.this.finish();
            }
        });
        delSwitchBtn = (TextView) findViewById(R.id.mypost_toolbar_btn);
        loading = (LinearLayout) findViewById(R.id.mypost_loading);
        listView = (ZrcListView) findViewById(R.id.mypost_list);
        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);
        Intent intent = this.getIntent();
        userName = intent.getStringExtra("userName");
        userPwd = intent.getStringExtra("userPwd");
    }

    class MyPostAdapter extends BaseAdapter {
        private List<_PostDetailData> list;
        private HashMap<Integer,Integer> isVisiableMap = new HashMap<>();//用于存储删除按钮是否显示
        private Map<Integer,Integer> replyMap = new HashMap<>();//记录留言技计数位置
        public MyPostAdapter(List<_PostDetailData> list) {
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

        /**
         *  保存留言计数的位置
         * @param visi
         */
        public void configReplyMap(Integer visi){
            for (int i = 0; i < list.size(); i++) {
                replyMap.put(i,visi);
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
                holder.contentTv = (TextView) convertView.findViewById(R.id.mypost_list_item_content_tv);
                holder.dateTv = (TextView) convertView.findViewById(R.id.mypost_list_item_date_tv);
                holder.replyLin = (LinearLayout) convertView.findViewById(R.id.mypost_list_item_replycount_lin);
                holder.replyCountTv = (TextView) convertView.findViewById(R.id.mypost_list_item_reply_tv);
                holder.delBtn = (TextView) convertView.findViewById(R.id.mypost_list_item_del_btn);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            final _PostDetailData date = list.get(position);
            holder.titleTv.setText(date.getTitle());
            holder.contentTv.setText(date.getDigest());
            holder.dateTv.setText(date.getPtime());
            if(TextUtils.isEmpty(date.getReplyCount())){
                replyMap.put(position, View.GONE);
            }else{
                replyMap.put(position, View.VISIBLE);
            }
            holder.replyLin.setVisibility(replyMap.get(position));
            holder.replyCountTv.setText(date.getReplyCount());
            if(isVisiableMap.get(position)!=null){
                if(isVisiableMap.get(position)==View.VISIBLE){
                    holder.delBtn.setVisibility(View.VISIBLE);
                }else{
                    holder.delBtn.setVisibility(View.GONE);
                }
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
            public TextView contentTv;
            public TextView dateTv;
            public LinearLayout replyLin;
            public TextView replyCountTv;
            public TextView delBtn;
        }

        public void notifyDataSetChanged(List<_PostDetailData> list) {
            this.list = list;
            notifyDataSetChanged();
        }
    }
    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(TAG01);
    }
}
