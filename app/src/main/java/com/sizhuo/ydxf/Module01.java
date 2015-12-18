package com.sizhuo.ydxf;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.sizhuo.ydxf.adapter.MyMainAdapter;
import com.sizhuo.ydxf.adapter.MyModule01Adapter;
import com.sizhuo.ydxf.bean.MainBean;
import com.sizhuo.ydxf.bean.Module01Bean;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.VRefresh;

import java.util.ArrayList;
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
    private VRefresh refresh;
    private ListView listView;
    private List<Module01Bean> list = new ArrayList<Module01Bean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module01);
        //初始化
        initViews();
//前3栏目
        for (int i = 0; i <10 ; i++) {
            Module01Bean module01Bean = new Module01Bean(R.mipmap.ic_launcher,"标题"+i,"新闻"+i,"2015-10-10");
            list.add(module01Bean);
        }
        MyModule01Adapter myModule01Adapter = new MyModule01Adapter(list, this);
        listView.setAdapter(myModule01Adapter);

    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.module01_toolbar);
        toolbar.setTitle("大事小情");
        setSupportActionBar(toolbar);
        refresh = (VRefresh) findViewById(R.id.module01_vrefresh);
        listView = (ListView) findViewById(R.id.module01_list);
    }
}
