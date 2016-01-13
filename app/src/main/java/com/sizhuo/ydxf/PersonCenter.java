package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.setting.PersonInfo;
import com.sizhuo.ydxf.util.StatusBar;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称: YDXF
 * 类描述:  个人中心
 * Created by My灬xiao7
 * date: 2016/1/4
 *
 * @version 1.0
 */
public class PersonCenter extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout topDefBtn, topBtn, menuBtn01, menuBtn02, menuBtn03, menuBtn04;//登录，我的资料，我的收藏，消息，帖子，评论
    private CircleImageView icon;//头像
    private TextView nameTv;//昵称
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personcenter);
        initViews();
        initEvents();
    }


    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.personcenter_toolbar);
        toolbar.setTitle("个人中心");
        setSupportActionBar(toolbar);
        topDefBtn = (RelativeLayout) findViewById(R.id.personcenter_def_top_re);
        topBtn = (RelativeLayout) findViewById(R.id.personcenter_top_re);
        menuBtn01 = (RelativeLayout) findViewById(R.id.personcenter_menu01_re);
        menuBtn02 = (RelativeLayout) findViewById(R.id.personcenter_menu02_re);
        menuBtn03 = (RelativeLayout) findViewById(R.id.personcenter_menu03_re);
        menuBtn04 = (RelativeLayout) findViewById(R.id.personcenter_menu04_re);
        icon = (CircleImageView) findViewById(R.id.personcenter_icon_img);
        nameTv = (TextView) findViewById(R.id.personcenter_name_txt);
    }


    private void initEvents() {
        topDefBtn.setOnClickListener(this);
        topBtn.setOnClickListener(this);
        menuBtn01.setOnClickListener(this);
        menuBtn02.setOnClickListener(this);
        menuBtn03.setOnClickListener(this);
        menuBtn04.setOnClickListener(this);
    }
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.person_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.per_menu_setting:
                Intent intent = new Intent(PersonCenter.this, Setting.class);
                startActivity(intent);
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.personcenter_def_top_re:
                Intent intent = new Intent(PersonCenter.this, Login.class);
                startActivity(intent);
                break;
            case R.id.personcenter_top_re:
                Intent intent2 = new Intent(PersonCenter.this, PersonInfo.class);
                startActivity(intent2);
                break;
            case R.id.personcenter_menu01_re:

                break;
            case R.id.personcenter_menu02_re:

                break;
            case R.id.personcenter_menu03_re:

                break;
            case R.id.personcenter_menu04_re:

                break;

        }
    }
}
