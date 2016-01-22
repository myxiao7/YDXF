package com.sizhuo.ydxf.per;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.google.gson.JsonObject;
import com.sizhuo.ydxf.NewsDetails;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.MyCollectionDate;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._SliderData;
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
 * 类描述:  我的收藏
 * Created by My灬xiao7
 * date: 2016/1/14
 *
 * @version 1.0
 */
public class MyCollection extends AppCompatActivity{
    private Toolbar toolbar;
    private ZrcListView listView;
    private List<_NewsData> list = new ArrayList<>();
    private MyCollectionAdapter adapter;
    private Boolean isEdit = false;//是否处于编辑模式下

    private String userName = "";
    private String userPwd = "";

    //网络请求
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mycollection);
        initViews();
        queue = Volley.newRequestQueue(this);
        adapter = new MyCollectionAdapter(list);
        listView.setAdapter(adapter);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(MyCollection.this, NewsDetails.class);
                intent.putExtra("data",list.get(position));
                MyCollection.this.startActivity(intent);
            }
        });
        //加载数据
        loadData();
    }

    private void loadData() {
        Map<String, String> map = new HashMap<>();
        map.put("userName",userName);
        map.put("userPwd",userPwd);
        JSONObject object = new JSONObject(map);
        Log.d("log.d", object.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.MYCOLLECTION+"1", object, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d", "收藏"+jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        list = JSON.parseArray(jsonObject.getString("data"), _NewsData.class);
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
        toolbar = (Toolbar) findViewById(R.id.mycollection_toolbar);
        toolbar.setTitle("新闻收藏");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MyCollection.this.finish();
            }
        });
        listView = (ZrcListView) findViewById(R.id.mycollection_list);
        Intent intent = this.getIntent();
        userName = intent.getStringExtra("userName");
        userPwd = intent.getStringExtra("userPwd");

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
        private List<_NewsData> list;
        private HashMap<Integer,Integer> isVisiableMap = new HashMap<>();//用于存储删除按钮是否现实
        public MyCollectionAdapter(List<_NewsData> list) {
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
                holder = (ViewHolder) convertView.getTag();
            }
            final _NewsData date = list.get(position);
            holder.titleTv.setText(date.getTitle());
            holder.dateTv.setText(date.getPtime());
            //
            holder.replyCountTv.setText(date.getDocid());
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

        public void notifyDataSetChanged(List<_NewsData> list) {
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
