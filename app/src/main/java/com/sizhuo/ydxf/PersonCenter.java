package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sizhuo.ydxf.util.StatusBar;

/**
 * 项目名称: YDXF
 * 类描述:  个人中心
 * Created by My灬xiao7
 * date: 2016/1/4
 *
 * @version 1.0
 */
public class PersonCenter extends AppCompatActivity{
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personcenter);
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.personcenter_toolbar);
        toolbar.setTitle("个人中心");
        setSupportActionBar(toolbar);
    }
}
