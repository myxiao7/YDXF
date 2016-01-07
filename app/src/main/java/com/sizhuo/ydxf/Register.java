package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.Button;
import android.widget.EditText;

import com.sizhuo.ydxf.util.StatusBar;

/**
 * 项目名称: YDXF
 * 类描述:  注册
 * Created by My灬xiao7
 * date: 2016/1/7
 *
 * @version 1.0
 */
public class Register extends AppCompatActivity {
    private Toolbar toolBar;//标题栏
    private TextInputLayout nameLayout, pwdLayout, pwdLayout2;//账号, 密码, 确认密码
    private EditText nameEdit, pwdEdit2, pwdEdit;//账号，密码编辑
    private Button registerBtn;//登录按钮

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化控件
        initViews();
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolBar = (Toolbar) findViewById(R.id.register_toolbar);
        toolBar.setTitle("注册");
        setSupportActionBar(toolBar);
        nameLayout = (TextInputLayout) findViewById(R.id.register_textinput_name);
        pwdLayout = (TextInputLayout) findViewById(R.id.register_textinput_pwd);
        pwdLayout2 = (TextInputLayout) findViewById(R.id.register_textinput_pwd2);
        nameEdit = (EditText) findViewById(R.id.register_name_txt);
        pwdEdit = (EditText) findViewById(R.id.register_pwd_txt);
        pwdEdit2 = (EditText) findViewById(R.id.register_pwd_txt2);
        registerBtn = (Button) findViewById(R.id.register_register_btn);
    }

}