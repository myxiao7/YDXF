package com.sizhuo.ydxf;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ListView;

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
import com.sizhuo.ydxf.bean.NewsBean;
import com.sizhuo.ydxf.bean.NewsData;
import com.sizhuo.ydxf.bean.SliderData;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.VRefresh;

import org.json.JSONException;
import org.json.JSONObject;

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
public class Module01 extends AppCompatActivity{
    private Toolbar toolbar;//标题栏
    private VRefresh vRefresh;//下拉刷新
    private SliderLayout sliderLayout;//轮播
    private ListView listView;
    private List<NewsData> list = new LinkedList<NewsData>();//数据列表
    private HashMap<String,String> url_maps = new LinkedHashMap<String, String>();//幻灯片数据
    private MyModule01Adapter myModule01Adapter;
    private final int REFRESH_COMPLETE = 0X100;//刷新完成
    private final int LOADMORE_COMPLETE = 0X101;//加载完成
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG
    long time,time02 = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module01);
        time = System.currentTimeMillis();
        //初始化
        initViews();
        queue = Volley.newRequestQueue(this);
        loadData();
    }

    /**
     * 获取数据
     */
    private void loadData() {
        jsonObjectRequest = new JsonObjectRequest(Const.URL+Const.M01, null, new Response.Listener<JSONObject>() {
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
                        //获取轮播图数据
                        List<SliderData> sliderDatas = JSON.parseArray(data.getString("carousel").toString(), SliderData.class);
                        Log.d("xinwen", "sliderDatas:------" + sliderDatas.size());
                        for (int i = 0; i <sliderDatas.size() ; i++) {
                            url_maps.put(sliderDatas.get(i).getTitle(),sliderDatas.get(i).getImgsrc());
                            Log.d("xinwen",sliderDatas.get(i).getTitle());
                        }
                        for(String name : url_maps.keySet()){
                            TextSliderView textSliderView = new TextSliderView(Module01.this);
                            // initialize a SliderLayout
                            textSliderView
                                    .description(name)
                                    .image(url_maps.get(name))
                                    .setScaleType(BaseSliderView.ScaleType.Fit)
                            ;

                            //add your extra information`
                            textSliderView.bundle(new Bundle());
                            textSliderView.getBundle()
                                    .putString("extra",name);

                            sliderLayout.addSlider(textSliderView);
                        }
                        //获取新闻
                        list = JSON.parseArray(data.getString("news").toString(), NewsData.class);
                       /* Log.d("xinwen", list.size()+"-----111");
                        Log.d("xinwen", list.get(3).getImgextra().toString()+"-----22222");
                        Log.d("xinwen", list.get(0).getTitle()+"------3333");*/
                        time02 = System.currentTimeMillis();
                        Log.d("xinwen",time02-time+"s");
                        myModule01Adapter.notifyDataSetChanged(list);
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
        toolbar = (Toolbar) findViewById(R.id.module01_toolbar);
        toolbar.setTitle("大事小情");
        setSupportActionBar(toolbar);
        View view = LayoutInflater.from(this).inflate(R.layout.module01_list_header,null);
        sliderLayout = (SliderLayout) view.findViewById(R.id.module01_list_item01_slider);
        /*url_maps.put("测试01", "http://192.168.1.114:8080/xinwen/img/item01.jpg");
        url_maps.put("测试02", "http://192.168.1.114:8080/xinwen/img/item02.jpg");
        url_maps.put("测试03", "http://192.168.1.114:8080/xinwen/img/item03.jpg");
        url_maps.put("测试04", "http://192.168.1.114:8080/xinwen/img/item04.jpg");
        url_maps.put("小学足球联赛开幕 OMG 1:0 VG", "http://192.168.1.114:8080/xinwen/img/item05.jpg");*/

        sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
        sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        sliderLayout.setCustomAnimation(new DescriptionAnimation());
        sliderLayout.startAutoCycle(2500, 4000, true);
        myModule01Adapter = new MyModule01Adapter(list, this);
        listView = (ListView) findViewById(R.id.module01_list);
        listView.addHeaderView(view);
        listView.setAdapter(myModule01Adapter);
        vRefresh = (VRefresh)findViewById(R.id.module01_vrefresh);
        vRefresh.setView(this, listView);//设置嵌套的子view -listview
        vRefresh.setMoreData(true);//设置是否还有数据可加载(一般根据服务器反回来决定)
        listView.setAdapter(myModule01Adapter);
//        vRefresh.autoRefresh();//自动刷新一次
//        vRefresh.setLoading(false);//停止刷新
//        vRefresh.setRefreshing(false);//让刷新消失
        vRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                Message message = handler.obtainMessage();
                message.what = REFRESH_COMPLETE;
                handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新
            }
        });
        vRefresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                Message message = handler.obtainMessage();
                message.what = LOADMORE_COMPLETE;
                handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新
            }
        });

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOADMORE_COMPLETE:
                    myModule01Adapter.notifyDataSetChanged(list);
                    vRefresh.setMoreData(true);//设置还有数据可以加载
                    vRefresh.setLoading(false);//停止加载更多
                    break;
                case REFRESH_COMPLETE:
                    list.clear();
                    sliderLayout.removeAllSliders();
                    loadData();
                    vRefresh.setMoreData(true);//设置还有数据可以加载
                    vRefresh.setLoading(false);//停止刷新
                    vRefresh.setRefreshing(false);//让刷新消失
                    Log.d("xinwen", list.size() + "!!!!!!!!!!!!!!");
                    break;
            }
        }
    };

    @Override
    protected void onStop() {
        super.onStop();
        queue.cancelAll(TAG01);
    }
}
