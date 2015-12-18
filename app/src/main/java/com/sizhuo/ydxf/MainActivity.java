package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ListView;
import android.widget.Toast;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.daimajia.slider.library.Tricks.ViewPagerEx;
import com.sizhuo.ydxf.adapter.MyBottomGridAdapter;
import com.sizhuo.ydxf.adapter.MyBottomGridAdapter2;
import com.sizhuo.ydxf.adapter.MyMainAdapter;
import com.sizhuo.ydxf.bean.GridBean;
import com.sizhuo.ydxf.bean.GridBean2;
import com.sizhuo.ydxf.bean.MainBean;
import com.sizhuo.ydxf.util.StatusBar;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

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
    private ListView listView;
    private List<MainBean> list = new ArrayList<MainBean>();
    private HashMap<String,String> url_maps = new LinkedHashMap<String, String>();
    private GridView gridView;//便民服务
    private List<GridBean> gridList = new ArrayList<GridBean>();
    private GridView gridView2;//便民114
    private List<GridBean2> gridList2 = new ArrayList<GridBean2>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        //前3栏目
        MainBean mainBean01 = new MainBean("1","1","1","1");
        MainBean mainBean02 = new MainBean("2","2","2","2");
        MainBean mainBean03 = new MainBean("3","3","3","3");
        MainBean mainBean04 = new MainBean("4","4","4","4");
        list.add(mainBean01);
        list.add(mainBean02);
        list.add(mainBean03);
        list.add(mainBean04);
        MyMainAdapter myMainAdapter = new MyMainAdapter(list,this);
        listView.setAdapter(myMainAdapter);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(MainActivity.this,"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        //便民服务
        for (int i = 0; i <4 ; i++) {
            GridBean gridBean = new GridBean(R.mipmap.ic_icon, "部门"+i);
            gridList.add(gridBean);
        }
        MyBottomGridAdapter myBottomGridAdapter = new MyBottomGridAdapter(gridList,this);
        gridView.setAdapter(myBottomGridAdapter);
        //便民114
        for (int i = 0; i <4 ; i++) {
            GridBean2 gridBean2 = new GridBean2("烟台市政府"+i);
            gridList2.add(gridBean2);
        }
        MyBottomGridAdapter2 myBottomListAdapter = new MyBottomGridAdapter2(gridList2,this);
        gridView2.setAdapter(myBottomListAdapter);
    }

    //初始化
    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.main_listview);
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
        mDemoSlider.startAutoCycle(2500, 4000, true);
        mDemoSlider.addOnPageChangeListener(this);

        gridView = (GridView) findViewById(R.id.main_bottom_grid);
        gridView2 = (GridView) findViewById(R.id.main_bottom_grid2);
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
