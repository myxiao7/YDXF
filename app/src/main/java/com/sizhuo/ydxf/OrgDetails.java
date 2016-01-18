package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sizhuo.ydxf.entity._OrgData;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private CircleImageView icon;//图标
    private TextView nameTv;//名称
    private TextView desTv;//简介
    private _OrgData orgData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orgdetails);
        initViews();
        Intent intent = this.getIntent();
        orgData = (_OrgData) intent.getSerializableExtra("data");
        if(!TextUtils.isEmpty(orgData.getPicture())){
            ImageLoaderHelper.getIstance().loadImg(orgData.getPicture(),icon);
        }
        nameTv.setText(orgData.getName());
        desTv.setText(orgData.getIntroduces());
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.orgdetails_toolbar);
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);
        icon = (CircleImageView) findViewById(R.id.orgdetails_img);
        nameTv = (TextView) findViewById(R.id.orgdetails_name_tv);
        desTv = (TextView) findViewById(R.id.orgdetails_des_tv);
    }
}
