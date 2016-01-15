package com.sizhuo.ydxf;

import android.app.Activity;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sizhuo.ydxf.util.StatusBar;

/**
 * 项目名称: YDXF
 * 类描述:  便民114详情
 * Created by My灬xiao7
 * date: 2016/1/9
 *
 * @version 1.0
 */
public class AddressListDetails extends AppCompatActivity {
    private Toolbar toolbar;
    private TextView phoneTv, webLTv, addTv;//电话，网址，地址
    private LinearLayout callBtn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddetails);
        initViews();

    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.adddetails_toolbar);
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);
        phoneTv = (TextView) findViewById(R.id.adddetails_menu01_phone_tv);
        webLTv = (TextView) findViewById(R.id.adddetails_menu02_web_tv);
        addTv = (TextView) findViewById(R.id.adddetails_menu03_add_tv);
        callBtn = (LinearLayout) findViewById(R.id.adddetails_call_lin);
    }
}
