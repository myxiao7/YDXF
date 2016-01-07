package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.baidu.platform.comapi.map.t;
import com.sizhuo.ydxf.util.StatusBar;

/**
 * 项目名称: YDXF
 * 类描述:  登录
 * Created by My灬xiao7
 * date: 2016/1/7
 *
 * @version 1.0
 */
public class Login extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolBar;//标题栏
    private TextInputLayout nameLayout, pwdLayout;//账号，密码
    private EditText nameEdit, pwdEdit;//账号，密码编辑
    private Button loginBtn;//登录按钮
    private TextView forgetBtn;//忘记密码
    private ImageView wechatBtn, qqBtn, weiboBtn;//第三方登录

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        //初始化控件
        initViews();
        wechatBtn.setOnClickListener(this);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolBar = (Toolbar) findViewById(R.id.login_toolbar);
        toolBar.setTitle("登录");
        setSupportActionBar(toolBar);
        nameLayout = (TextInputLayout) findViewById(R.id.login_textinput_name);
        pwdLayout = (TextInputLayout) findViewById(R.id.login_textinput_pwd);
        nameEdit = (EditText) findViewById(R.id.login_name_txt);
        pwdEdit = (EditText) findViewById(R.id.login_pwd_txt);
        loginBtn = (Button) findViewById(R.id.login_login_btn);
        forgetBtn = (TextView) findViewById(R.id.login_forget_btn);
        wechatBtn = (ImageView) findViewById(R.id.login_wechat_btn);
        qqBtn = (ImageView) findViewById(R.id.login_qq_btn);
        weiboBtn = (ImageView) findViewById(R.id.login_weibo_btn);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.login_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.login_menu_item01:
                Intent intent = new Intent(Login.this, Register.class);
                startActivity(intent);
            break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_wechat_btn:
                Toast.makeText(Login.this,"微信",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}
