package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.VRefresh;

/**
 * 项目名称: YDXF
 * 类描述:  电视栏目
 * Created by My灬xiao7
 * date: 2015/12/25
 *
 * @version 1.0
 */
public class VideoModule extends AppCompatActivity{
    private Toolbar toolbar;
    private ListView listView;//下拉刷新
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videomodule);
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.videomodule_toolbar);
        toolbar.setTitle("党建电视节目");
        setSupportActionBar(toolbar);
        listView = (ListView) findViewById(R.id.videomodule_list);

    }
}
