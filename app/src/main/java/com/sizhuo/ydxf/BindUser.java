package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.ZProgressHUD;
import com.umeng.socialize.UMShareAPI;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/16
 *
 * @version 1.0
 */
public class BindUser extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolBar;//标题栏
    private TextInputLayout nameLayout, pwdLayout;//账号，密码
    private EditText nameEdit, pwdEdit;//账号，密码编辑
    private String nameStr, pwdStr;//账号，密码编辑
    private String sex, openid, nick, icon, shareType;//快捷登录用户信息
    private Button bindBtn;//绑定按钮

    //网络请求相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String REQUEST_TAB = "LOGIN_REQUEST";

    private DbManager dbManager;
//    private DbManager.DaoConfig daoConfig;

    ZProgressHUD progressHUD;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_binduser);
        //初始化控件
        initViews();
        queue = Volley.newRequestQueue(this);
        dbManager = new MyApplication().getDbManager();
        progressHUD = ZProgressHUD.getInstance(this);
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER);
        initEvents();

    }

    private void initEvents() {
        bindBtn.setOnClickListener(this);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolBar = (Toolbar) findViewById(R.id.bind_toolbar);
        toolBar.setTitle("绑定");
        setSupportActionBar(toolBar);
        nameLayout = (TextInputLayout) findViewById(R.id.bind_textinput_name);
        pwdLayout = (TextInputLayout) findViewById(R.id.bind_textinput_pwd);
        nameEdit = (EditText) findViewById(R.id.bind_name_txt);
        pwdEdit = (EditText) findViewById(R.id.bind_pwd_txt);
        bindBtn = (Button) findViewById(R.id.bind_bind_btn);
        Intent intent = this.getIntent();
        Bundle bundle = intent.getExtras();
        shareType = bundle.getString("type");
        openid = bundle.getString("openid");
        icon = bundle.getString("icon");
        nick = bundle.getString("nick");
        sex = bundle.getString("sex");
        Log.d("log.d","bind-----"+shareType);
        Log.d("log.d","bind-----"+openid);
        Log.d("log.d","bind-----"+icon);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.bind_bind_btn:
                nameStr = nameEdit.getText().toString();
                pwdStr = pwdEdit.getText().toString();
                bind(nameStr,pwdStr,shareType,openid,icon,nick,sex);
                break;
        }
    }

    /**
     * 绑定
     * @param userName 用户名
     * @param userPwd 密码
     * @param type  登录类型
     * @param openId openId
     * @param portrait 头像
     * @param nickName 昵称
     * @param sex 性别
     */
    private void bind(final String userName,final String userPwd, final String type, final String openId, final String portrait, final String nickName, final String sex) {
        Map<String, String> map = new HashMap<>();
        map.put("userName",userName);
        map.put("userPwd",userPwd);
        map.put("nickName",nickName);
        map.put("sex",sex);
        map.put("portrait",portrait);
        map.put(type,openId);

        JSONObject jsonObject = new JSONObject(map);
        Log.d("log.d", "jsonObject" + jsonObject.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.LOGIN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d","result"+jsonObject.toString());
                try {
                    String code = jsonObject.getString("code");
                    //登陆成功，返回信息data
                    if(code.equals("200")) {
                        //清空表
                        dbManager.delete(User.class);
                        //保存用户信息
                        User user = JSON.parseObject(jsonObject.getString("data").toString(), User.class);
                        user.setUserPwd(userPwd);
                        dbManager.save(user);
                        Toast.makeText(BindUser.this, "suessful", Toast.LENGTH_SHORT).show();
                        progressHUD.dismissWithSuccess("绑定成功");
                        BindUser.this.finish();
                    }else{
                        Toast.makeText(BindUser.this, "failt", Toast.LENGTH_SHORT).show();
                        progressHUD.dismissWithSuccess("绑定失败");
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                }catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("log.d",volleyError.toString());
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        queue.add(jsonObjectRequest);
        jsonObjectRequest.setTag(REQUEST_TAB);
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(progressHUD!=null){
            progressHUD.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(REQUEST_TAB);
    }
}
