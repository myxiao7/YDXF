package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.sizhuo.ydxf.adapter.MyOrgGridAdapter;
import com.sizhuo.ydxf.entity.GridBean;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._OrgData;
import com.sizhuo.ydxf.entity._SliderData;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.TypeTextView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  组织机构
 * Created by My灬xiao7
 * date: 2016/1/11
 *
 * @version 1.0
 */
public class Organization extends AppCompatActivity{
    private Toolbar toolbar;
    private GridView gridView;//便民服务
    private MyOrgGridAdapter myBottomGridAdapter;//便民服务
    private List<_OrgData> gridList = new ArrayList<_OrgData>();

    //网络请求
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_org);
        initViews();
        queue = Volley.newRequestQueue(this);
        myBottomGridAdapter = new MyOrgGridAdapter(gridList,this);
        gridView.setAdapter(myBottomGridAdapter);

        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Organization.this, OrgDetails.class);
                intent.putExtra("data",gridList.get(position));
                Organization.this.startActivity(intent);
            }
        });
        jsonObjectRequest = new JsonObjectRequest(Const.ORGANIZATION, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        gridList = JSON.parseArray(jsonObject.getString("data"), _OrgData.class);
                        myBottomGridAdapter.notifyDataSetChanged(gridList);
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
        toolbar = (Toolbar) findViewById(R.id.org_toolbar);
        toolbar.setTitle("组织机构");
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.org_grid);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(TAG01);
    }
}
