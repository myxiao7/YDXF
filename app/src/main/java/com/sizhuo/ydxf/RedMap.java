package com.sizhuo.ydxf;

import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.CardView;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.map.BaiduMap;
import com.baidu.mapapi.map.BitmapDescriptor;
import com.baidu.mapapi.map.BitmapDescriptorFactory;
import com.baidu.mapapi.map.MapPoi;
import com.baidu.mapapi.map.MapStatusUpdate;
import com.baidu.mapapi.map.MapStatusUpdateFactory;
import com.baidu.mapapi.map.MapView;
import com.baidu.mapapi.map.Marker;
import com.baidu.mapapi.map.MarkerOptions;
import com.baidu.mapapi.map.OverlayOptions;
import com.baidu.mapapi.model.LatLng;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.MapInfo;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._SliderData;
import com.sizhuo.ydxf.entity.db._MapInfo;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  红色地图
 * Created by My灬xiao7
 * date: 2016/1/6
 *
 * @version 1.0
 */
public class RedMap extends AppCompatActivity {
    private Toolbar toolbar;
    MapView mMapView = null;
    BaiduMap mBaiduMap;

    private List<_MapInfo> mapInfos = new ArrayList<>();

    //覆盖物相关
    private BitmapDescriptor mMarket;
    private CardView cardView;

    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG

    private DbManager dbManager;//数据库操作
    @Override
    public void onBackPressed() {
        if(cardView.isShown()){
            cardView.setVisibility(View.GONE);
        }else{
            this.finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_map);
        new StatusBar(this).initStatusBar();
        cardView = (CardView) findViewById(R.id.mapmarker_info_card);
        toolbar = (Toolbar) (Toolbar) findViewById(R.id.map_toolbar);
        toolbar.setTitle("红色地图");
        setSupportActionBar(toolbar);
        queue = Volley.newRequestQueue(this);
        dbManager = new MyApplication().getDbManager();
        //获取地图控件引用
        mMapView = (MapView) findViewById(R.id.map_mapview);
        mBaiduMap = mMapView.getMap();
        //设置初始中心和缩放比例
        MapStatusUpdate msu = MapStatusUpdateFactory.newLatLngZoom(new LatLng(37.569872, 121.260381), 15.0f);
        mBaiduMap.setMapStatus(msu);
        //初始化覆盖物
        initMarkers();
        try {
            mapInfos = dbManager.selector(_MapInfo.class).findAll();
            if(mapInfos != null){
                addOverlays(mapInfos);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        loadData();
        /*MapInfo info = new MapInfo(37.569572,121.260288,R.mipmap.ic_icon,"开发区团总支1","");
        MapInfo info2 = new MapInfo(37.561806,121.243978,R.mipmap.ic_main_top,"开发区团总支22","");
        MapInfo info3 = new MapInfo(37.551586,121.381275,R.mipmap.ic_main_top2,"开发区团总支333","");
        MapInfo info4 = new MapInfo(37.557765,121.259798,R.mipmap.ic_icon,"开发区团总支4444","");*/
        mBaiduMap.setOnMarkerClickListener(new BaiduMap.OnMarkerClickListener() {
            @Override
            public boolean onMarkerClick(Marker marker) {
                Bundle bundle = marker.getExtraInfo();
                _MapInfo mapInfo = (_MapInfo) bundle.getSerializable("info");
                ImageView imageView = (ImageView) cardView.findViewById(R.id.mapmarker_info_img);
                TextView nameTv = (TextView) cardView.findViewById(R.id.mapmarker_info_name_txt);
                TextView desTv = (TextView) cardView.findViewById(R.id.mapmarker_info_des_txt);
                TextView addTv = (TextView) cardView.findViewById(R.id.mapmarker_info_des_txt);
                TextView phoneTv = (TextView) cardView.findViewById(R.id.mapmarker_info_phone_txt);
                if(TextUtils.isEmpty(mapInfo.getPicture())){
                    ImageLoaderHelper.getIstance().loadImg(mapInfo.getPicture(),imageView);
                }
                nameTv.setText(mapInfo.getName());
                desTv.setText(mapInfo.getSynopsis());
                addTv.setText(mapInfo.getPosition());
                Animation animation = AnimationUtils.loadAnimation(RedMap.this, R.anim.map_info_anim);
                cardView.startAnimation(animation);
                cardView.setVisibility(View.VISIBLE);
                cardView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        cardView.setVisibility(View.GONE);
                    }
                });
                return true;
            }
        });
        mBaiduMap.setOnMapClickListener(new BaiduMap.OnMapClickListener() {
            @Override
            public void onMapClick(LatLng latLng) {
                cardView.setVisibility(View.GONE);
            }

            @Override
            public boolean onMapPoiClick(MapPoi mapPoi) {
                return false;
            }
        });
    }

    /**
     * 获取数据
     */
    private void loadData() {
        jsonObjectRequest = new JsonObjectRequest(Const.REDMAP, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        mapInfos = JSON.parseArray(jsonObject.getString("data").toString(), _MapInfo.class);
                        if(mapInfos != null){
                            addOverlays(mapInfos);
                        }
                        dbManager.delete(_MapInfo.class);
                        dbManager.save(mapInfos);
                    }else if(code == 400){
                        Toast.makeText(RedMap.this, "没有数据", Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(RedMap.this,"加载错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen",volleyError.toString()+volleyError);
                Toast.makeText(RedMap.this, "网络异常",Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 800);
    }

    private void initMarkers() {
        mMarket =BitmapDescriptorFactory.fromResource(R.mipmap.ic_m08);
    }

    /**
     * 添加覆盖物
     * @param mapInfos
     */
    private void addOverlays(List<_MapInfo> mapInfos){
        //清除图层
        mBaiduMap.clear();
        //覆盖物的坐标
        LatLng point= null;
        Marker marker = null;
        //构建MarkerOption，用于在地图上添加Marker
        OverlayOptions option = null;
        for (_MapInfo info :mapInfos) {
            point = new LatLng(info.getLongitude(),info.getLatitude());
            Log.d("log.d",info.getLongitude()+"-----------getLangtitude");
            Log.d("log.d",info.getLatitude()+"------------getLatitude");
            option = new MarkerOptions().position(point).icon(mMarket);
            marker = (Marker) mBaiduMap.addOverlay(option);
            //设置覆盖物额外信息
            Bundle bundle = new Bundle();
            bundle.putSerializable("info",info);
            marker.setExtraInfo(bundle);
        }

    }

    //toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.map_menu, menu);
        return true;
    }

    //toolbar菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.map_menu_item01:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_NORMAL);
                break;
            case R.id.map_menu_item02:
                mBaiduMap.setMapType(BaiduMap.MAP_TYPE_SATELLITE);
                break;

            case R.id.map_menu_item03:
                if (mBaiduMap.isTrafficEnabled()) {
                    mBaiduMap.setTrafficEnabled(false);
                    item.setTitle("交通状况(OFF)");
                } else {
                    mBaiduMap.setTrafficEnabled(true);
                    item.setTitle("交通状况(ON)");
                }
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        // activity 暂停时同时暂停地图控件
        mMapView.onPause();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // activity 恢复时同时恢复地图控件
        mMapView.onResume();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        // activity 销毁时同时销毁地图控件
        mMapView.onDestroy();
        mMarket.recycle();
    }
}
