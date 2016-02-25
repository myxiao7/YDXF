package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.Scroller;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.location.f;
import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.sizhuo.ydxf.adapter.MyBottomGridAdapter;
import com.sizhuo.ydxf.adapter.MyBottomGridAdapter2;
import com.sizhuo.ydxf.adapter.MyMainAdapter;
import com.sizhuo.ydxf.entity.GridBean;
import com.sizhuo.ydxf.entity.GridBean2;
import com.sizhuo.ydxf.entity._AddListData;
import com.sizhuo.ydxf.entity._MainData;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._OrgData;
import com.sizhuo.ydxf.entity._PostDetailData;
import com.sizhuo.ydxf.entity._ServiceData;
import com.sizhuo.ydxf.entity._SliderData;
import com.sizhuo.ydxf.util.ACache;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.NoScollerGridView;
import com.umeng.update.UmengUpdateAgent;
import com.wang.avi.AVLoadingIndicatorView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

/**
 *
 *项目名称: MainActivity
 *类描述:  主菜单
 *Created by zhanghao
 *date: 2015-12-16 08:37:54
 *@version 1.0
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener, BaseSliderView.OnSliderClickListener {
    private Toolbar toolbar;//Toolbar
    private SliderLayout mSlider;//轮播图
    private LinearLayout loading;
    private HashMap<String,String> url_maps = new LinkedHashMap<String, String>();

    private List<_NewsData> newsList = new LinkedList<_NewsData>();//新闻
    private List<_PostDetailData> forumList = new LinkedList<_PostDetailData>();//论坛
    private RelativeLayout itemReMore01,itemReMore02,itemReMore03,itemReMore04;//新闻和论坛更多
    private LinearLayout itemLin01,itemLin02,itemLin03,itemLin04;//新闻和论坛详情
    private LinearLayout menuLin01, menuLin02, menuLin03, menuLin04;
    private ImageView imageView01, imageView02, imageView03, imageView04;
    private TextView titleTv01, conTv01, dataTv01,titleTv02, conTv02, dataTv02,titleTv03, conTv03, dataTv03, titleTv04, conTv04, dataTv04;
    private ImageView mapBtn;
    private ScrollView mScrollView;
    private RelativeLayout moreRe;//便民更多
    private GridView gridView;//便民服务
    private List<_OrgData> gridList = new ArrayList<_OrgData>();
    private MyBottomGridAdapter myBottomGridAdapter;

    private RelativeLayout moreRe2;//便民114更多
    private NoScollerGridView gridView2;//便民114
    private List<_AddListData> gridList2 = new ArrayList<_AddListData>();
    private MyBottomGridAdapter2 myBottomGridAdapter2;


    //网络相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG = "JSONOBJECTREQUEST";

    long waitTime = 2000;
    long touchTime = 0;
    private ACache aCache;
    @Override
    public void onBackPressed() {
        long currentTime = System.currentTimeMillis();
        if((currentTime-touchTime) >= waitTime) {
            Toast.makeText(this, "再按一次退出", Toast.LENGTH_SHORT).show();
            touchTime = currentTime;
        }else {
            finish();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
        queue = Volley.newRequestQueue(this);
        aCache = ACache.get(this);
        //
        if(aCache.getAsJSONObject("mainData")!=null){
            _MainData mainCache = JSON.parseObject(aCache.getAsJSONObject("mainData").toString(), _MainData.class);
            Log.d("log.d","main----"+aCache.getAsJSONObject("mainData").toString());
            if(mainCache!=null){
                List<_SliderData> sliderDatas = mainCache.getCarousel();
                newsList = mainCache.getNews();
                forumList = mainCache.getCard();
                gridList = mainCache.getConvenience();
                gridList2 = mainCache.getDirectory();
                initData(sliderDatas);
                mScrollView.smoothScrollTo(0, 0);
            }
        }


       myBottomGridAdapter = new MyBottomGridAdapter(gridList,this);
        gridView.setAdapter(myBottomGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, OrgDetails.class);
                intent.putExtra("data", gridList.get(position));
                MainActivity.this.startActivity(intent);
            }
        });

        myBottomGridAdapter2 = new MyBottomGridAdapter2(gridList2,this);
        gridView2.setAdapter(myBottomGridAdapter2);
        mScrollView.smoothScrollTo(0, 0);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, AddressListDetails.class);
                intent.putExtra("data", gridList2.get(position));
                MainActivity.this.startActivity(intent);
            }
        });
        jsonObjectRequest = new JsonObjectRequest(Request.Method.GET, Const.MAIN_URL, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d","main"+jsonObject.toString());
                try {
                    String code = jsonObject.getString("code");
                    //获取成功，返回信息data
                    if(code.equals("200")){
                        aCache.put("mainData",jsonObject.getString("data"));
                        _MainData mainData = JSON.parseObject(jsonObject.getString("data"),_MainData.class);
                        List<_SliderData> sliderDatas = mainData.getCarousel();

                        newsList = mainData.getNews();
                        forumList = mainData.getCard();
                        /*Log.d("log.d",forumList.get(1).getImgextra().size()+"");
                        Log.d("log.d",forumList.get(1).getImgextra().toString()+"");*/
                        gridList = mainData.getConvenience();
                        gridList2 = mainData.getDirectory();

                        initData(sliderDatas);

                        myBottomGridAdapter.notifyDataSetChanged(gridList);
                        myBottomGridAdapter2.notifyDataSetChanged(gridList2);
                        new Handler().postDelayed(new Runnable() {
                            @Override
                            public void run() {
                                loading.setVisibility(View.GONE);
                            }
                        },800);
                    }else{
                        loading.setVisibility(View.GONE);
                    }
                    mScrollView.smoothScrollTo(0, 0);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("log.d","main"+volleyError.toString());
                loading.setVisibility(View.GONE);
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };

                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG);


    }

    private void initData(List<_SliderData> sliderDatas) {
        if(sliderDatas != null){
            if(sliderDatas.size()==0){
                mSlider.setVisibility(View.GONE);
            }else{
                mSlider.removeAllSliders();
                mSlider.setVisibility(View.VISIBLE);
                for (int i = 0; i <sliderDatas.size() ; i++) {
                    url_maps.put(sliderDatas.get(i).getTitle(),sliderDatas.get(i).getImgsrc());
                    Log.d("xinwen", sliderDatas.get(i).getTitle());
                    TextSliderView textSliderView = new TextSliderView(MainActivity.this);
                    // initialize a SliderLayout
                    textSliderView
                            .description(sliderDatas.get(i).getTitle())
                            .image(sliderDatas.get(i).getImgsrc())
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            .setOnSliderClickListener(MainActivity.this);

                    //add your extra information`
                    _NewsData slidNews = new _NewsData();
                    slidNews.setDocid(sliderDatas.get(i).getDocid());
                    slidNews.setDigest(sliderDatas.get(i).getDigest());
                    slidNews.setUrl(sliderDatas.get(i).getUrl());
                    slidNews.setTitle(sliderDatas.get(i).getTitle());
                    slidNews.setImgsrc(sliderDatas.get(i).getImgsrc());
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putSerializable("extra", slidNews);

                    mSlider.addSlider(textSliderView);
                }
            }
        }

        if(newsList.get(0)!=null){
            if(!TextUtils.isEmpty(newsList.get(0).getImgsrc())){
                ImageLoaderHelper.getIstance().loadImg(newsList.get(0).getImgsrc(),imageView01);
            }
            titleTv01.setText(newsList.get(0).getTitle());
            conTv01.setText(newsList.get(0).getDigest());
            dataTv01.setText(newsList.get(0).getPtime());
            itemLin01.setOnClickListener(MainActivity.this);
        }
        if(newsList.get(1)!=null){
            if(!TextUtils.isEmpty(newsList.get(1).getImgsrc())){
                ImageLoaderHelper.getIstance().loadImg(newsList.get(1).getImgsrc(),imageView02);
            }
            titleTv02.setText(newsList.get(1).getTitle());
            conTv02.setText(newsList.get(1).getDigest());
            dataTv02.setText(newsList.get(1).getPtime());
            itemLin02.setOnClickListener(MainActivity.this);
        }
        if(forumList.get(0)!=null){
            if(!TextUtils.isEmpty(forumList.get(0).getImgsrc())){
                ImageLoaderHelper.getIstance().loadImg(forumList.get(0).getImgsrc(),imageView03);
            }
            titleTv03.setText(forumList.get(0).getTitle());
            conTv03.setText(forumList.get(0).getDigest());
            dataTv03.setText(forumList.get(0).getPtime());
            itemLin03.setOnClickListener(MainActivity.this);
        }
        if(forumList.get(1)!=null){
            if(!TextUtils.isEmpty(forumList.get(1).getImgsrc())){
                ImageLoaderHelper.getIstance().loadImg(forumList.get(1).getImgsrc(),imageView04);
            }
            titleTv04.setText(forumList.get(1).getTitle());
            conTv04.setText(forumList.get(1).getDigest());
            dataTv04.setText(forumList.get(1).getPtime());
            itemLin04.setOnClickListener(MainActivity.this);
        }

    }


    //初始化
    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        loading = (LinearLayout) findViewById(R.id.main_loading);
        mScrollView = (ScrollView) findViewById(R.id.main_scrollview);
        mScrollView.smoothScrollTo(0,0);
        //自动更新
        UmengUpdateAgent.update(this);
        mSlider = (SliderLayout) findViewById(R.id.main_slider);
        mSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mSlider.setCustomAnimation(new DescriptionAnimation());
        mSlider.startAutoCycle(2500, 4000, true);

        itemReMore01 = (RelativeLayout) findViewById(R.id.main_list_item01_01_re_more);
        itemLin01 = (LinearLayout) findViewById(R.id.main_list_item01_01_lin);
        imageView01 = (ImageView) findViewById(R.id.main_list_item01_01_img);
        titleTv01 = (TextView) findViewById(R.id.main_list_item01_01_title_tv);
        conTv01 = (TextView) findViewById(R.id.main_list_item01_01_des_tv);
        dataTv01 = (TextView) findViewById(R.id.main_list_item01_01_date_tv);

        itemReMore02 = (RelativeLayout) findViewById(R.id.main_list_item01_02_re_more);
        itemLin02 = (LinearLayout) findViewById(R.id.main_list_item01_02_lin);
        imageView02 = (ImageView) findViewById(R.id.main_list_item01_02_img);
        titleTv02 = (TextView) findViewById(R.id.main_list_item01_02_title_tv);
        conTv02 = (TextView) findViewById(R.id.main_list_item01_02_des_tv);
        dataTv02 = (TextView) findViewById(R.id.main_list_item01_02_date_tv);

        itemReMore03 = (RelativeLayout) findViewById(R.id.main_list_item01_03_re_more);
        itemLin03 = (LinearLayout) findViewById(R.id.main_list_item01_03_lin);
        imageView03 = (ImageView) findViewById(R.id.main_list_item01_03_img);
        titleTv03 = (TextView) findViewById(R.id.main_list_item01_03_title_tv);
        conTv03 = (TextView) findViewById(R.id.main_list_item01_03_des_tv);
        dataTv03 = (TextView) findViewById(R.id.main_list_item01_03_date_tv);

        itemReMore04 = (RelativeLayout) findViewById(R.id.main_list_item01_04_re_more);
        itemLin04 = (LinearLayout) findViewById(R.id.main_list_item01_04_lin);
        imageView04 = (ImageView) findViewById(R.id.main_list_item01_04_img);
        titleTv04 = (TextView) findViewById(R.id.main_list_item01_04_title_tv);
        conTv04 = (TextView) findViewById(R.id.main_list_item01_04_des_tv);
        dataTv04 = (TextView) findViewById(R.id.main_list_item01_04_date_tv);

        menuLin01 = (LinearLayout) findViewById(R.id.main_list_item02_menu01_lin);
        menuLin02 = (LinearLayout) findViewById(R.id.main_list_item02_menu02_lin);
        menuLin03 = (LinearLayout) findViewById(R.id.main_list_item02_menu03_lin);
        menuLin04 = (LinearLayout) findViewById(R.id.main_list_item02_menu04_lin);

        mapBtn = (ImageView) findViewById(R.id.main_list_item03_img);

        moreRe = (RelativeLayout) findViewById(R.id.main_bottom_re_more);
        gridView = (GridView) findViewById(R.id.main_bottom_grid);

        moreRe2 = (RelativeLayout) findViewById(R.id.main_bottom_re_more02);
        gridView2 = (NoScollerGridView) findViewById(R.id.main_bottom_grid2);


    }

    private void initEvents() {
        itemReMore01.setOnClickListener(this);
//        itemLin01.setOnClickListener(this);
        itemReMore02.setOnClickListener(this);
//        itemLin02.setOnClickListener(this);
        itemReMore03.setOnClickListener(this);
//        itemLin03.setOnClickListener(this);
        itemReMore04.setOnClickListener(this);
//        itemLin04.setOnClickListener(this);

        menuLin01.setOnClickListener(this);
        menuLin02.setOnClickListener(this);
        menuLin03.setOnClickListener(this);
        menuLin04.setOnClickListener(this);
        mapBtn.setOnClickListener(this);
        moreRe.setOnClickListener(this);
        moreRe2.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_list_item01_01_re_more:
                Intent intent = new Intent(MainActivity.this, Module01.class);
                startActivity(intent);
                break;
             case R.id.main_list_item01_02_re_more:
                Intent intent2 = new Intent(MainActivity.this, Module02.class);
                startActivity(intent2);
                break;
             case R.id.main_list_item01_03_re_more:
                Intent intent3 = new Intent(MainActivity.this, Forum.class);
                startActivity(intent3);
                break;
             case R.id.main_list_item01_04_re_more:
                Intent intent4 = new Intent(MainActivity.this, Forum2.class);
                startActivity(intent4);
                break;
            case R.id.main_list_item01_01_lin:
                Intent intent12 = new Intent(MainActivity.this, NewsDetails.class);
                intent12.putExtra("data",newsList.get(0));
                startActivity(intent12);
                break;
            case R.id.main_list_item01_02_lin:
                Intent intent13 = new Intent(MainActivity.this, NewsDetails.class);
                intent13.putExtra("data",newsList.get(1));
                startActivity(intent13);

                break;
            case R.id.main_list_item01_03_lin:
                Intent intent14 = new Intent(MainActivity.this, PostDetails.class);
                intent14.putExtra("data", forumList.get(0));
                startActivity(intent14);

                break;
            case R.id.main_list_item01_04_lin:
                Intent intent15 = new Intent(MainActivity.this, PostDetails.class);
                intent15.putExtra("data", forumList.get(1));
                startActivity(intent15);
                break;

             case R.id.main_list_item02_menu01_lin:
                Intent intent5 = new Intent(MainActivity.this, Module03.class);
                startActivity(intent5);
                break;
             case R.id.main_list_item02_menu02_lin:
                Intent intent6 = new Intent(MainActivity.this, Module04.class);
                startActivity(intent6);
                break;
             case R.id.main_list_item02_menu03_lin:
                Intent intent7 = new Intent(MainActivity.this, Module05.class);
                startActivity(intent7);
                break;
             case R.id.main_list_item02_menu04_lin:
                Intent intent8 = new Intent(MainActivity.this, VideoModule.class);
                startActivity(intent8);
                break;
             case R.id.main_list_item03_img:
                Intent intent9 = new Intent(MainActivity.this, RedMap.class);
                startActivity(intent9);
                break;
             case R.id.main_bottom_re_more:
                Intent intent10 = new Intent(MainActivity.this, Organization.class);
                startActivity(intent10);
                break;
            case R.id.main_bottom_re_more02:
                Intent intent11 = new Intent(MainActivity.this, AddressList.class);
                startActivity(intent11);
                break;
        }
    }


    //toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //toolbar菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_serch:
//                Toast.makeText(MainActivity.this,"搜索",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Search.class);
                startActivity(intent);
                break;

            case R.id.main_menu_person:
//                Toast.makeText(MainActivity.this,"我的",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this, PersonCenter.class);
                startActivity(intent2);
                break;
        }
        return true;
    }


    @Override
    protected void onStop() {
//        mSlider.stopAutoCycle();
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(TAG);
    }


    @Override
    public void onSliderClick(BaseSliderView slider) {
        //传递轮播数据
        Intent intent12 = new Intent(MainActivity.this, NewsDetails.class);
        intent12.putExtra("data", slider.getBundle().getSerializable("extra"));
        startActivity(intent12);
    }
}
