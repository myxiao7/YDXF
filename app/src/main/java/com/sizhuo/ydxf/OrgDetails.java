package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sizhuo.ydxf.util.StatusBar;

/**
 * 项目名称: YDXF
 * 类描述:  组织机构详情
 * Created by My灬xiao7
 * date: 2016/1/9
 *
 * @version 1.0
 */
public class OrgDetails extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgdetails);
        initViews();

    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.orgdetails_toolbar);
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);

    }
}
