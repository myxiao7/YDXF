package com.sizhuo.ydxf;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sizhuo.ydxf.entity._AddListData;
import com.sizhuo.ydxf.entity._OrgData;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;

import de.hdodenhof.circleimageview.CircleImageView;

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
    private CircleImageView icon;//图标
    private TextView nameTv, phoneTv, webTv, addTv;//电话，网址，地址
    private LinearLayout callBtn;
    private _AddListData addListData;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_adddetails);
        initViews();
        Intent intent = this.getIntent();
        addListData = (_AddListData) intent.getSerializableExtra("data");
        if(!TextUtils.isEmpty(addListData.getPicture())){
            ImageLoaderHelper.getIstance().loadImg(addListData.getPicture(),icon);
        }
        nameTv.setText(addListData.getName());
        phoneTv.setText(addListData.getTelephone());
        webTv.setText(addListData.getWebsite());
        addTv.setText(addListData.getAdd());
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse("tel:"+addListData.getTelephone());
                Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                AddressListDetails.this.startActivity(intent);
            }
        });
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.adddetails_toolbar);
        toolbar.setTitle("详情");
        setSupportActionBar(toolbar);
        icon = (CircleImageView) findViewById(R.id.adddetails_img);
        nameTv = (TextView) findViewById(R.id.adddetails_name_tv);
        phoneTv = (TextView) findViewById(R.id.adddetails_menu01_phone_tv);
        webTv = (TextView) findViewById(R.id.adddetails_menu02_web_tv);
        addTv = (TextView) findViewById(R.id.adddetails_menu03_add_tv);
        callBtn = (LinearLayout) findViewById(R.id.adddetails_call_lin);
    }
}
