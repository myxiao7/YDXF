package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.sizhuo.ydxf.adapter.MyModule01Adapter;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._SliderData;
import com.sizhuo.ydxf.util.ACache;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.SimpleFooter;
import com.sizhuo.ydxf.view.zrclistview.SimpleHeader;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  第一个功能模块
 * Created by My灬xiao7
 * date: 2015/12/18
 *
 * @version 1.0
 */
public class Module03 extends AppCompatActivity implements BaseSliderView.OnSliderClickListener {
    private Toolbar toolbar;//标题栏
    private SliderLayout sliderLayout;//轮播
    private LinearLayout loading;
    private ZrcListView listView;
    private View headView;
    private List<_NewsData> list = new LinkedList<_NewsData>();//数据列表
    private HashMap<String,String> url_maps = new LinkedHashMap<String, String>();//幻灯片数据
    private MyModule01Adapter myModule01Adapter;
    private final int REFRESH_COMPLETE = 0X100;//刷新完成
    private final int LOADMORE_COMPLETE = 0X101;//加载完成
    private int index = 1;

    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG
    long time,time02 = 0;

    private ACache aCache;//数据缓存

    private DbManager dbManager;//数据库操作
    private List<_NewsData> newsCache = new ArrayList<>();
    private List<_SliderData> sliderCache = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module01);
        time = System.currentTimeMillis();
        //初始化
        initViews();
        queue = Volley.newRequestQueue(this);
        loadData();
//        listView.refresh();
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(Module03.this, NewsDetails.class);
//                Toast.makeText(Module01.this, "" + position + "----" + list.get(position - 1).getDigest(), Toast.LENGTH_SHORT).show();
                intent.putExtra("data", (_NewsData) parent.getAdapter().getItem(position));
                startActivity(intent);
            }
        });
    }

    /**
     * 获取数据
     */
    private void loadData() {
        jsonObjectRequest = new JsonObjectRequest(Const.M03 + 1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
//                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        //获取数据成功
                        Log.d("xinwen", "data:------" + jsonObject.getString("data"));
                        //获取data所有数据
                        com.alibaba.fastjson.JSONObject data = JSON.parseObject(jsonObject.getString("data"));
                        if(TextUtils.isEmpty(data.getString("carousel"))){
                            listView.removeHeaderView(headView);
                        }else {
                            //获取轮播图数据
                            List<_SliderData> sliderDatas = JSON.parseArray(data.getString("carousel").toString(), _SliderData.class);
                            Log.d("xinwen", "sliderDatas:------" + sliderDatas.size());
                            //先清除缓存数据
                            dbManager.delete(_SliderData.class, WhereBuilder.b("moduleType", "=", "m03"));
                            if (sliderDatas == null) {
                                listView.removeHeaderView(headView);
                            } else {
                                if (sliderDatas.size() == 0) {
                                    listView.removeHeaderView(headView);
                                } else {
                                    for (_SliderData cache : sliderDatas) {
                                        cache.setModuleType("m03");
                                        dbManager.save(cache);
                                    }
                                    loadSlider(sliderDatas);
                                }
                            }
                        }
                        //获取新闻
                        list = JSON.parseArray(data.getString("news").toString(), _NewsData.class);
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                //先清除缓存数据
                                try {
                                    dbManager.delete(_NewsData.class, WhereBuilder.b("moduleType", "=", "m03"));
                                    //缓存
                                    for (_NewsData cache:list ) {
                                        cache.setModuleType("m03");
                                        dbManager.save(cache);
                                    }
                                } catch (DbException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();

                       /* Log.d("xinwen", list.size()+"-----111");
                        Log.d("xinwen", list.get(3).getImgextra().toString()+"-----22222");
                        Log.d("xinwen", list.get(0).getTitle()+"------3333");*/
                        time02 = System.currentTimeMillis();
                        Log.d("xinwen", time02 - time + "s");
                        myModule01Adapter.notifyDataSetChanged(list);
                        listView.setRefreshSuccess("更新完成"); // 通知加载成功
                        if(list.size()==20){
                            listView.startLoadMore();
                        }
                        index = 1;
                    }else if(code == 400){
                        listView.setRefreshFail("没有数据");
                        Toast.makeText(Module03.this,"没有数据",Toast.LENGTH_SHORT).show();
                    }else{
                        listView.setRefreshFail("加载错误");
                        Toast.makeText(Module03.this,"加载错误",Toast.LENGTH_SHORT).show();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading.setVisibility(View.GONE);
                        }
                    },800);
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
                listView.setRefreshFail("网络不给力");
                loading.setVisibility(View.GONE);
                Toast.makeText(Module03.this, "网络不给力",Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 500);
    }

    /**
     * 加载幻灯片数据
     * @param sliderDatas
     */
    private void loadSlider(List<_SliderData> sliderDatas) {
        sliderLayout.removeAllSliders();
        for (int i = 0; i <sliderDatas.size() ; i++) {
            url_maps.put(sliderDatas.get(i).getTitle(),sliderDatas.get(i).getImgsrc());
            Log.d("xinwen", sliderDatas.get(i).getTitle());
            TextSliderView textSliderView = new TextSliderView(Module03.this);
            // initialize a SliderLayout
            textSliderView
                    .description(sliderDatas.get(i).getTitle())
                    .image(sliderDatas.get(i).getImgsrc())
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(Module03.this);

            //add your extra information`

            _NewsData slidNews = new _NewsData();
            slidNews.setDocid(sliderDatas.get(i).getDocid());
            slidNews.setDigest(sliderDatas.get(i).getDigest());
            slidNews.setUrl(sliderDatas.get(i).getUrl());
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putSerializable("extra", slidNews);

            sliderLayout.addSlider(textSliderView);
        }
    }

    /**
     * 获取更多数据
     */
    private void loadMoreData(int index) {
        jsonObjectRequest = new JsonObjectRequest(Const.M03 + index, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        //获取数据成功
                        Log.d("xinwen", "data:------" + jsonObject.getString("data"));
                        //获取data所有数据
                        com.alibaba.fastjson.JSONObject data = JSON.parseObject(jsonObject.getString("data"));
                        //获取新闻
                        List<_NewsData> list2 = JSON.parseArray(data.getString("news").toString(), _NewsData.class);
                        for (_NewsData newdata: list2) {
                            list.add(newdata);
                            //缓存
                            newdata.setModuleType("m03");
                            dbManager.save(newdata);
                        }
                        myModule01Adapter.notifyDataSetChanged(list);
                        listView.setLoadMoreSuccess();
                    }else if(code == 400){
                        Toast.makeText(Module03.this,"没有更多了",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }else{
                        Toast.makeText(Module03.this,"加载错误",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
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
                Toast.makeText(Module03.this,"网络不给力",Toast.LENGTH_SHORT).show();
                listView.stopLoadMore();
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
        toolbar = (Toolbar) findViewById(R.id.module01_toolbar);
        toolbar.setTitle("上级精神");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Module03.this.finish();
            }
        });
        headView = LayoutInflater.from(this).inflate(R.layout.module01_list_header,null);

        sliderLayout = (SliderLayout) headView.findViewById(R.id.module01_list_item01_slider);
        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.startAutoCycle(2500, 4000, true);
        listView = (ZrcListView) findViewById(R.id.module01_list);
        loading = (LinearLayout) findViewById(R.id.module01_loading);
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

        // 设置列表项出现动画（可选）
        listView.setItemAnimForTopIn(R.anim.topitem_in);
        listView.setItemAnimForBottomIn(R.anim.bottomitem_in);
        listView.setFootable(footer);

        myModule01Adapter = new MyModule01Adapter(list, this);
        listView.addHeaderView(headView);
        listView.setAdapter(myModule01Adapter);

        //加载本地缓存
        dbManager = new MyApplication().getDbManager();
        try {
            if(dbManager.selector(_SliderData.class).where("moduleType","=","m03").findAll()!=null) {
                if (dbManager.selector(_SliderData.class).where("moduleType", "=", "m03").findAll().size() > 0) {
                    List<_SliderData> sliderDatas = dbManager.selector(_SliderData.class).where("moduleType", "=", "m03").findAll();
                    loadSlider(sliderDatas);
//                    Toast.makeText(Module01.this, "加载了" + sliderDatas.size() + "条幻灯片缓存", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(Module01.this, "没有幻灯片缓存数据", Toast.LENGTH_SHORT).show();
                    listView.removeHeaderView(headView);
                }
            }/*else{
                listView.removeHeaderView(headView);
            }*/
            if(dbManager.selector(_NewsData.class).where("moduleType","=","m03").findAll()!=null) {
                if (dbManager.selector(_NewsData.class).where("moduleType", "=", "m03").findAll().size() > 0) {
                    list = dbManager.selector(_NewsData.class).where("moduleType", "=", "m03").findAll();
                    myModule01Adapter.notifyDataSetChanged(list);
//                    Toast.makeText(Module01.this, "加载了" + list.size() + "条缓存", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(Module01.this, "没有缓存数据", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                /*Message message = handler.obtainMessage();
                message.what = REFRESH_COMPLETE;
                handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新*/
                loadData();
            }
        });
        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                /*Message message = handler.obtainMessage();
                message.what = LOADMORE_COMPLETE;
                handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新*/
                index++;
                loadMoreData(index);
            }
        });
//        vRefresh.autoRefresh();//自动刷新一次
//        vRefresh.setLoading(false);//停止刷新
//        vRefresh.setRefreshing(false);//让刷新消失

    }

   /* Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOADMORE_COMPLETE:
                    myModule01Adapter.notifyDataSetChanged(list);
                    listView.setLoadMoreSuccess();
                    listView.stopLoadMore();
                    break;
                case REFRESH_COMPLETE:
                    list.clear();
                    sliderLayout.removeAllSliders();
                    loadData();
                    listView.startLoadMore(); // 开启LoadingMore功能
                    break;
            }
        }
    };*/

    @Override
    protected void onStop() {
        super.onStop();
        queue.cancelAll(TAG01);
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        //传递轮播数据
        Intent intent = new Intent(Module03.this, NewsDetails.class);
        intent.putExtra("data", slider.getBundle().getSerializable("extra"));
        startActivity(intent);
    }
}
