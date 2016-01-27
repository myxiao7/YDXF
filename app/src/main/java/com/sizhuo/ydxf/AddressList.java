package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.adapter.MyAddressListAdapter;
import com.sizhuo.ydxf.entity.AddressListData;
import com.sizhuo.ydxf.entity._AddListData;
import com.sizhuo.ydxf.entity._MainData;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._OrgData;
import com.sizhuo.ydxf.entity._SliderData;
import com.sizhuo.ydxf.util.ACache;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.SimpleFooter;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  便民114
 * Created by My灬xiao7
 * date: 2016/1/9
 *
 * @version 1.0
 */
public class AddressList extends AppCompatActivity{
    private Toolbar toolbar;
    private LinearLayout loading;
    private ZrcListView listView;
    private List<_AddListData> list = new ArrayList<_AddListData>();
    private MyAddressListAdapter myAddressListAdapter;

    //网络请求
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG
    private int index = 1;

    private ACache aCache;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);
        initViews();
        queue = Volley.newRequestQueue(this);
        aCache = ACache.get(this);
        if(aCache.getAsJSONArray("addressList")!=null){
            list = JSON.parseArray(aCache.getAsJSONArray("addressList").toString(), _AddListData.class);
        }
        myAddressListAdapter = new MyAddressListAdapter(list,this);
        listView.setAdapter(myAddressListAdapter);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(AddressList.this, AddressListDetails.class);
                intent.putExtra("data", list.get(position));
                AddressList.this.startActivity(intent);
            }
        });
        loadData();
//        listView.startLoadMore();
    }

    private void loadData() {
        jsonObjectRequest = new JsonObjectRequest(Const.ADDRESSLIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        list = JSON.parseArray(jsonObject.getString("data"), _AddListData.class);
                        aCache.put("addressList",jsonObject.getString("data"));
                        myAddressListAdapter.notifyDataSetChanged(list);
                    }else if(code == 400){
                        Toast.makeText(AddressList.this,"没有数据",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(AddressList.this,"加载错误",Toast.LENGTH_SHORT).show();
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
//                Log.d("xinwen", volleyError.toString() + volleyError);
                loading.setVisibility(View.GONE);
                Toast.makeText(AddressList.this,"网络不给力",Toast.LENGTH_SHORT).show();
            }
        });
        queue.add(jsonObjectRequest);
        jsonObjectRequest.setTag(TAG01);
    }

    private void loadMoreData(int index) {
        jsonObjectRequest = new JsonObjectRequest(Const.ADDRESSLIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        List<_AddListData> list2 = JSON.parseArray(jsonObject.getString("data"), _AddListData.class);
                        for (_AddListData adddata: list2) {
                            list.add(adddata);
                        }
                        myAddressListAdapter.notifyDataSetChanged(list);
                        listView.setLoadMoreSuccess();
                    }else if(code==400){
                        Toast.makeText(AddressList.this, "没有更多了", Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }else{
                        Toast.makeText(AddressList.this,"加载错误",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen", volleyError.toString() + volleyError);
                Toast.makeText(AddressList.this,"网络异常",Toast.LENGTH_SHORT).show();
                listView.stopLoadMore();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 1200);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.addresslist_toolbar);
        toolbar.setTitle("便民114");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddressList.this.finish();
            }
        });
        loading = (LinearLayout) findViewById(R.id.addresslist_loading);
        listView = (ZrcListView) findViewById(R.id.addresslist_listview);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

       /* // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                index++;
                loadMoreData(index);
            }
        });*/
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(TAG01);
    }
}
