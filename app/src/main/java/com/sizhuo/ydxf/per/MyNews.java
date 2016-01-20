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
import android.widget.ImageView;
import android.widget.TextView;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.MyNewsData;
import com.sizhuo.ydxf.entity._PostDetailData;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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

    private String userName = "";
    private String userPwd = "";

    //网络请求
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mynews);
        initView();
        queue = Volley.newRequestQueue(this);
        for (int i = 0; i < 4  ; i++) {
            MyNewsData date = new MyNewsData("","admin","2015-01-14","恭喜 评为精品贴","我来水经验了");
            list.add(date);
        }
        adapter = new MyNewsAdapter(list,this);
        listView.setAdapter(adapter);

        //加载数据
//        loadData();
        /*listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(MyComment.this, NewsDetails.class);
                MyComment.this.startActivity(intent);
            }
        });*/
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("userName", userName);
        map.put("userPwd", userPwd);
        map.put("index", "1");
        JSONObject object = new JSONObject(map);
        Log.d("log.d", object.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.MYNEWS, object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d", "收藏"+jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
//                        list = JSON.parseArray(jsonObject.getString("data"), _PostDetailData.class);
//                        adapter.notifyDataSetChanged(list);
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

    private void initView() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.mynews_toolbar);
        toolbar.setTitle("我的消息");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyNews.this.finish();
            }
        });
        listView = (ZrcListView) findViewById(R.id.mynews_list);
        Intent intent = this.getIntent();
        userName = intent.getStringExtra("userName");
        userPwd = intent.getStringExtra("userPwd");
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
    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(TAG01);
    }
}
