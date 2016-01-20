package com.sizhuo.ydxf.per;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.NewsDetails;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.MyCommentData;
import com.sizhuo.ydxf.entity._MyComment;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
    private List<_MyComment> list = new ArrayList<>();
    private MyCommentAdapter adapter;

    private String userName = "";
    private String userPwd = "";

    //网络请求
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycomment);
        initViews();

        queue = Volley.newRequestQueue(this);
        adapter = new MyCommentAdapter(list,this);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(MyComment.this, NewsDetails.class);
                intent.putExtra("data",list.get(position).getNews());
                MyComment.this.startActivity(intent);
            }
        });
        //加载数据
        loadData();
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPwd", userPwd);
        map.put("index", "1");
        JSONObject object = new JSONObject(map);
        Log.d("log.d", object.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.MYCOMMENT, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d", "评论"+jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        list = JSON.parseArray(jsonObject.getString("data"), _MyComment.class);
                        adapter.notifyDataSetChanged(list);
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("xinwen",volleyError.toString()+volleyError);
            }
        });
        queue.add(jsonObjectRequest);
        jsonObjectRequest.setTag(TAG01);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.mycomment_toolbar);
        toolbar.setTitle("新闻评论");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyComment.this.finish();
            }
        });
        listView = (ZrcListView) findViewById(R.id.mycomment_list);
        Intent intent = this.getIntent();
        userName = intent.getStringExtra("userName");
        userPwd = intent.getStringExtra("userPwd");
    }

    class MyCommentAdapter extends BaseAdapter {
        private List<_MyComment> list;
        private Context context;

        public MyCommentAdapter(List<_MyComment> list, Context context) {
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
            final _MyComment date = list.get(position);
            holder.titleTv.setText("我的回复......");
            holder.dateTv.setText(date.getNews().getPtime());
            holder.titleTv.setText(date.getNews().getTitle());
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

        public void notifyDataSetChanged(List<_MyComment> list) {
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
