package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.kyleduo.switchbutton.SwitchButton;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.setting.About;
import com.sizhuo.ydxf.setting.Feedback;
import com.sizhuo.ydxf.setting.ModifyPwd;
import com.sizhuo.ydxf.setting.PersonInfo;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/13
 *
 * @version 1.0
 */
public class Setting extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private RelativeLayout LoginBtn;//未登录
    private RelativeLayout infoBtn, pwdBtn, clearCacheBtn, feedbackBtn, versionBtn, aboutBtn;//资料，修改密码，清除缓存，反馈没更新，关于
    private CircleImageView icon;//头像
    private TextView nickTv;//昵称

    private TextView cacheTv;//缓存大小
    private SwitchButton switchButton;//3G网络播放
    private LinearLayout logoutLinBtn;//注销
    private Button logoutBtn;//注销

    private DbManager dbManager;//数据库操作
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initViews();
        initEvents();


    }


    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.setting_toolbar);
        toolbar.setTitle("设置");
        setSupportActionBar(toolbar);
        LoginBtn = (RelativeLayout) findViewById(R.id.setting_info_def_re);
        infoBtn = (RelativeLayout) findViewById(R.id.setting_info_re);
        icon = (CircleImageView) findViewById(R.id.setting_info_icon_img);
        nickTv = (TextView) findViewById(R.id.setting_info_nike_tv);
        pwdBtn = (RelativeLayout) findViewById(R.id.setting_pwd_re);
        switchButton = (SwitchButton) findViewById(R.id.setting_net_switch);
        clearCacheBtn = (RelativeLayout) findViewById(R.id.setting_clearcache_re);
        cacheTv = (TextView) findViewById(R.id.setting_clearcache_size_tv);
        feedbackBtn = (RelativeLayout) findViewById(R.id.setting_feedback_re);
        versionBtn = (RelativeLayout) findViewById(R.id.setting_verson_re);
        aboutBtn = (RelativeLayout) findViewById(R.id.setting_about_re);
        logoutLinBtn = (LinearLayout) findViewById(R.id.setting_logout_lin);
        logoutBtn = (Button) findViewById(R.id.setting_logout_btn);
    }

    private void initEvents() {
        LoginBtn.setOnClickListener(this);
        infoBtn.setOnClickListener(this);
        pwdBtn.setOnClickListener(this);
        clearCacheBtn.setOnClickListener(this);
        feedbackBtn.setOnClickListener(this);
        versionBtn.setOnClickListener(this);
        aboutBtn.setOnClickListener(this);
        logoutBtn.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.setting_info_def_re:
                Intent intent = new Intent(Setting.this, Login.class);
                startActivity(intent);
                break;
            case R.id.setting_info_re:
                Intent intent2 = new Intent(Setting.this, PersonInfo.class);
                startActivity(intent2);
                break;
            case R.id.setting_pwd_re:
                Intent intent3 = new Intent(Setting.this, ModifyPwd.class);
                startActivity(intent3);
                break;
            case R.id.setting_clearcache_re:
                break;
            case R.id.setting_feedback_re:
                /*Intent intent4 = new Intent(Setting.this, Feedback.class);
                startActivity(intent4);*/
                break;
            case R.id.setting_verson_re:
                break;
            case R.id.setting_about_re:
                /*Intent intent5 = new Intent(Setting.this, About.class);
                startActivity(intent5);*/
                break;
            case R.id.setting_logout_btn:
                try {
                    if(user!=null){
                        dbManager.delete(User.class);
                        Intent data = new Intent();
                        data.putExtra("loginFlag",false);
                        setResult(RESULT_OK, data);
                        Setting.this.finish();
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }
                break;

        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbManager = new MyApplication().getDbManager();
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                infoBtn.setVisibility(View.VISIBLE);
                pwdBtn.setVisibility(View.VISIBLE);
                logoutLinBtn.setVisibility(View.VISIBLE);
                LoginBtn.setVisibility(View.GONE);
                //设置头像和昵称
                if(!TextUtils.isEmpty(user.getPortrait())){
                    ImageLoaderHelper.getIstance().loadImg(user.getPortrait(), icon);
                }
                if(!TextUtils.isEmpty(user.getNickName())){
                    nickTv.setText(user.getNickName());
                }
            }else{
                infoBtn.setVisibility(View.GONE);
                pwdBtn.setVisibility(View.GONE);
                logoutLinBtn.setVisibility(View.GONE);
                LoginBtn.setVisibility(View.VISIBLE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }
}
