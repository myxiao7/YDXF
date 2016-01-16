package com.sizhuo.ydxf.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;

import org.xutils.DbManager;
import org.xutils.ex.DbException;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称: YDXF
 * 类描述:  个人资料（修改）
 * Created by My灬xiao7
 * date: 2016/1/13
 *
 * @version 1.0
 */
public class PersonInfo extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private CircleImageView icon;//头像
    private TextView nickNameTv;//昵称
    private TextView sexTv;//昵称
    private TextView accountTv;//昵称
    private RelativeLayout iconRe, nickRe, sexRe;//头像，昵称，性别
    private DbManager dbManager;//数据库操作
    private User user;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personinfo);
        initViews();
        initEvents();
        dbManager = new MyApplication().getDbManager();
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                if(!TextUtils.isEmpty(user.getPortrait())){
                    ImageLoaderHelper.getIstance().loadImg(user.getPortrait(),icon);
                }
                if(!TextUtils.isEmpty(user.getNickName())){
                    nickNameTv.setText(user.getNickName());
                }
                if(!TextUtils.isEmpty(user.getSex())){
                    sexTv.setText(user.getSex());
                }
                if(!TextUtils.isEmpty(user.getUserName())){
                    accountTv.setText(user.getUserName());
                }
            }else{
                Toast.makeText(this,"请先登录",Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.set_personinfo_toolbar);
        toolbar.setTitle("个人资料");
        setSupportActionBar(toolbar);
        icon = (CircleImageView) findViewById(R.id.set_personinfo_icon_img);
        nickNameTv = (TextView) findViewById(R.id.set_personinfo_nike_tv);
        sexTv = (TextView) findViewById(R.id.set_personinfo_sex_tv);
        accountTv = (TextView) findViewById(R.id.set_personinfo_username_tv);
        iconRe = (RelativeLayout) findViewById(R.id.set_personinfo_icon_re);
        nickRe = (RelativeLayout) findViewById(R.id.set_personinfo_nike_re);
        sexRe = (RelativeLayout) findViewById(R.id.set_personinfo_sex_re);
    }

    private void initEvents() {
        iconRe.setOnClickListener(this);
        nickRe.setOnClickListener(this);
        sexRe.setOnClickListener(this);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.personinfo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.perinfo_menu_save:
                break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_personinfo_icon_re:
                break;

            case R.id.set_personinfo_nike_re:
                break;

            case R.id.set_personinfo_sex_re:
                break;
        }
    }
}
