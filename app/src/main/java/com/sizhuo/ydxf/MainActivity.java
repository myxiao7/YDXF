package com.sizhuo.ydxf;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.Indicators.PagerIndicator;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.sizhuo.ydxf.util.StatusBar;

import java.util.HashMap;
import java.util.LinkedHashMap;

/**
 *
 *项目名称: MainActivity
 *类描述:  主菜单
 *Created by zhanghao
 *date: 2015-12-16 08:37:54
 *@version 1.0
 *
 */
public class MainActivity extends AppCompatActivity implements BaseSliderView.OnSliderClickListener, ViewPagerEx.OnPageChangeListener {
    private Toolbar toolbar;//Toolbar
    private SliderLayout mDemoSlider;//轮播图
    private HashMap<String,String> url_maps = new LinkedHashMap<String, String>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
    }

    //初始化
    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        mDemoSlider = (SliderLayout) findViewById(R.id.main_slider);
        //轮播图
        url_maps.put("测试01", "http://192.168.1.114:8080/xinwen/img/item01.jpg");
        url_maps.put("测试02", "http://192.168.1.114:8080/xinwen/img/item02.jpg");
        url_maps.put("测试03", "http://192.168.1.114:8080/xinwen/img/item03.jpg");
        url_maps.put("测试04", "http://192.168.1.114:8080/xinwen/img/item04.jpg");
        url_maps.put("小学足球联赛开幕 OMG 1:0 VG", "http://192.168.1.114:8080/xinwen/img/item05.jpg");
        for(String name : url_maps.keySet()){
            TextSliderView textSliderView = new TextSliderView(this);
            // initialize a SliderLayout
            textSliderView
                    .description(name)
                    .image(url_maps.get(name))
                    .setScaleType(BaseSliderView.ScaleType.Fit)
                    .setOnSliderClickListener(this);

            //add your extra information
            textSliderView.bundle(new Bundle());
            textSliderView.getBundle()
                    .putString("extra",name);

            mDemoSlider.addSlider(textSliderView);
        }
        mDemoSlider.setPresetTransformer(SliderLayout.Transformer.Default);
        mDemoSlider.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
        mDemoSlider.setCustomAnimation(new DescriptionAnimation());
        mDemoSlider.startAutoCycle(2500,4000,true);
        mDemoSlider.addOnPageChangeListener(this);
    }

    //toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    //toolbar菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_serch:
                Toast.makeText(MainActivity.this,"搜索",Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_menu_person:
                Toast.makeText(MainActivity.this,"我的",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }

    @Override
    public void onSliderClick(BaseSliderView slider) {
        Toast.makeText(this,slider.getBundle().get("extra") + "",Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

    }

    @Override
    public void onPageSelected(int position) {
        Log.d("Slider Demo", "Page Changed: " + position);
    }

    @Override
    public void onPageScrollStateChanged(int state) {

    }

    @Override
    protected void onStop() {
        mDemoSlider.stopAutoCycle();
        super.onStop();
    }
}
