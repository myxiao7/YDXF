package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.adapter.MyAddressListAdapter;
import com.sizhuo.ydxf.entity.AddressListData;
import com.sizhuo.ydxf.entity._AddListData;
import com.sizhuo.ydxf.entity._OrgData;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
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
    private ZrcListView listView;
    private List<_AddListData> list = new ArrayList<_AddListData>();
    private MyAddressListAdapter myAddressListAdapter;

    //网络请求
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);
        initViews();
        queue = Volley.newRequestQueue(this);
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

        jsonObjectRequest = new JsonObjectRequest(Const.ADDRESSLIST, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        list = JSON.parseArray(jsonObject.getString("data"), _AddListData.class);
                        myAddressListAdapter.notifyDataSetChanged(list);
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("xinwen", volleyError.toString() + volleyError);
            }
        });
        queue.add(jsonObjectRequest);
        jsonObjectRequest.setTag(TAG01);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.addresslist_toolbar);
        toolbar.setTitle("便民114");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.addresslist_listview);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(TAG01);
    }
}
