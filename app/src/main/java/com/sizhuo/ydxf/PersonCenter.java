package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.per.MyCollection;
import com.sizhuo.ydxf.per.MyComment;
import com.sizhuo.ydxf.per.MyNews;
import com.sizhuo.ydxf.per.MyPost;
import com.sizhuo.ydxf.setting.PersonInfo;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

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

    private DbManager dbManager;//数据库操作
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personcenter);
        initViews();
        initEvents();
        new Thread(new Runnable() {
            @Override
            public void run() {
                ImageLoaderHelper.getIstance().loadImg("http://192.168.1.114:8080/xinwen/img/icon.png",icon);
                Log.d("log.d","url");
            }
        }).start();
        dbManager = new MyApplication().getDbManager();
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                topBtn.setVisibility(View.VISIBLE);
                topDefBtn.setVisibility(View.GONE);

                nameTv.setText(user.getNickName());
            }else{
                topBtn.setVisibility(View.GONE);
                topDefBtn.setVisibility(View.VISIBLE);
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
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
                Intent intent3 = new Intent(PersonCenter.this, MyCollection.class);
                startActivity(intent3);
                break;
            case R.id.personcenter_menu02_re:
                Intent intent4 = new Intent(PersonCenter.this, MyComment.class);
                startActivity(intent4);
                break;
            case R.id.personcenter_menu03_re:
                Intent intent5 = new Intent(PersonCenter.this, MyPost.class);
                startActivity(intent5);
                break;
            case R.id.personcenter_menu04_re:
                Intent intent6= new Intent(PersonCenter.this, MyNews.class);
                startActivity(intent6);
                break;

        }
    }
}
