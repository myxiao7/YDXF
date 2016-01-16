package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
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

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.baidu.platform.comapi.map.t;
import com.renn.rennsdk.param.ShareType;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.ZProgressHUD;
import com.umeng.socialize.PlatformConfig;
import com.umeng.socialize.UMAuthListener;
import com.umeng.socialize.UMShareAPI;
import com.umeng.socialize.bean.SHARE_MEDIA;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;
import org.xutils.x;

import java.io.File;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Timer;
import java.util.TimerTask;

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
    private String nameStr, pwdStr;//账号，密码编辑
    private String sex, openid, nick, icon, shareType;//快捷登录用户信息
    private Button loginBtn;//登录按钮
    private TextView forgetBtn;//忘记密码
    private static final int REGISTER_REQUESTCODE = 0X200;
    private ImageView wechatBtn, qqBtn, weiboBtn;//第三方登录
    private UMShareAPI mShareAPI;

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
        setContentView(R.layout.activity_login);
        //初始化控件
        initViews();
        queue = Volley.newRequestQueue(this);
        dbManager = new MyApplication().getDbManager();
        progressHUD = new ZProgressHUD(this);
        progressHUD.setSpinnerType(ZProgressHUD.SIMPLE_ROUND_SPINNER  );
        initEvents();

    }

    private void initEvents() {
        wechatBtn.setOnClickListener(this);
        qqBtn.setOnClickListener(this);
        weiboBtn.setOnClickListener(this);
        loginBtn.setOnClickListener(this);
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

        mShareAPI = UMShareAPI.get(this);

    }

    public void initAuth(SHARE_MEDIA platforms) {

        SHARE_MEDIA platform = platforms;
        mShareAPI.doOauthVerify(this, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA platform, int action, Map<String, String> data) {
                Log.d("sso", "doOauthVerify----------"+data.toString());
                Toast.makeText(getApplicationContext(), "授权成功，正在登陆"+shareType+openid, Toast.LENGTH_SHORT).show();
                if(!TextUtils.isEmpty(openid)){
                    progressHUD.show();
//                    Toast.makeText(getApplicationContext(), "授权成功，正在登陆"+shareType+openid, Toast.LENGTH_SHORT).show();
                    new Timer().schedule(new TimerTask() {
                        @Override
                        public void run() {
                            fastLogin(shareType, openid);
                        }
                    },1200);
                }
            }

            @Override
            public void onError(SHARE_MEDIA platform, int action, Throwable t) {
                Toast.makeText(getApplicationContext(), "Authorize fail", Toast.LENGTH_SHORT).show();
                progressHUD.dismiss();
            }

            @Override
            public void onCancel(SHARE_MEDIA platform, int action) {
                Toast.makeText(getApplicationContext(), "Authorize cancel", Toast.LENGTH_SHORT).show();
                progressHUD.dismiss();
            }
        });
        mShareAPI.getPlatformInfo(Login.this, platform, new UMAuthListener() {
            @Override
            public void onComplete(SHARE_MEDIA share_media, int i, Map<String, String> data) {

                if (data != null) {
//                    Toast.makeText(getApplicationContext(), data.toString(), Toast.LENGTH_SHORT).show();
                    Log.d("sso", "getPlatformInfo----------" + data.toString());

                    if(SHARE_MEDIA.QQ.equals(share_media)){
                        shareType = "qqOpenid";
                    }else if(SHARE_MEDIA.WEIXIN.equals(share_media)){
                        shareType = "wxOpenid";
                        sex = data.get("sex");
                        openid = data.get("openid");
                        nick = data.get("nickname");
                        icon = data.get("headimgurl");
                    }else if(SHARE_MEDIA.SINA.equals(share_media)){
                        shareType = "wbOpenid";
                    }
                    //QQ,WEINXIN,SINA
                    Log.d("sso", "share_media" + icon);
                }
            }

            @Override
            public void onError(SHARE_MEDIA share_media, int i, Throwable throwable) {
                Toast.makeText(getApplicationContext(), "get fail", Toast.LENGTH_SHORT).show();
            }

            @Override
            public void onCancel(SHARE_MEDIA share_media, int i) {
                Toast.makeText(getApplicationContext(), "get cancel", Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        mShareAPI.onActivityResult(requestCode, resultCode, data);
        if(requestCode == REGISTER_REQUESTCODE&& resultCode == RESULT_OK){
            nameEdit.setText(data.getStringExtra("regiName"));
            pwdEdit.setText(data.getStringExtra("regiPwd"));
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater menuInflater = getMenuInflater();
        menuInflater.inflate(R.menu.login_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.login_menu_item01:
                Intent intent = new Intent(Login.this, Register.class);
                startActivityForResult(intent, REGISTER_REQUESTCODE);
            break;
        }
        return true;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.login_login_btn:
                nameStr = nameEdit.getText().toString();
                pwdStr = pwdEdit.getText().toString();
                login(nameStr, pwdStr);
                progressHUD.show();
                break;
            case R.id.login_wechat_btn:
                Toast.makeText(Login.this,"微信",Toast.LENGTH_SHORT).show();
                //授权
                initAuth(SHARE_MEDIA.WEIXIN);
                break;
            case R.id.login_qq_btn:
                Toast.makeText(Login.this,"QQ",Toast.LENGTH_SHORT).show();
                //授权
                initAuth(SHARE_MEDIA.QQ);
                break;
            case R.id.login_weibo_btn:
                Toast.makeText(Login.this,"微博",Toast.LENGTH_SHORT).show();
                //授权
                initAuth(SHARE_MEDIA.SINA);
                break;
        }
    }

    /**
     * 快速登录
     * @param type 类型
     * @param openID openID
     */
    private void fastLogin(String type, String openID) {
        Map<String, String> map = new HashMap<>();
        map.put(type,openID);
       /* map.put("wxOpenid","");
        map.put("qqOpenid","");
        map.put("wbOpenid","");*/
        JSONObject jsonObject = new JSONObject(map);
        Log.d("log.d", "jsonObject"+jsonObject.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.LOGIN, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d","result"+jsonObject.toString());
                try {
                    String code = jsonObject.getString("code");
                    //已经绑定了账号
                    if(code.equals("200")) {
                        //清空表
                        dbManager.delete(User.class);
                        //保存用户信息
                        User user = JSON.parseObject(jsonObject.getString("data").toString(),User.class);
//                        user.setUserPwd(userPwd);
                        dbManager.save(user);
                    }else if(code.equals("400")){
                        //未经绑定了账号
                        Toast.makeText(Login.this, "请先绑定账户", Toast.LENGTH_SHORT).show();
                        Intent intent = new Intent(Login.this, BindUser.class);
                        Bundle bundle = new Bundle();

                        bundle.putString("type",shareType);
                        bundle.putString("openid",openid);
                        bundle.putString("icon",icon);
                        bundle.putString("nick",nick);
                        bundle.putString("sex", sex);
                        intent.putExtras(bundle);
                        startActivity(intent);
                    }
                    Login.this.finish();
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

    private void login(final String userName,final String userPwd) {
        Map<String, String> map = new HashMap<>();
        map.put("userName",userName);
        map.put("userPwd",userPwd);
       /* map.put("wxOpenid","");
        map.put("qqOpenid","");
        map.put("wbOpenid","");*/
        JSONObject jsonObject = new JSONObject(map);
        Log.d("log.d", "jsonObject"+jsonObject.toString());
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
                            User user = JSON.parseObject(jsonObject.getString("data").toString(),User.class);
                            user.setUserPwd(userPwd);
                            dbManager.save(user);
                            Toast.makeText(Login.this, "suessful", Toast.LENGTH_SHORT).show();
                            progressHUD.dismissWithSuccess("登录成功");
                            Login.this.finish();
                        }else{
                            Toast.makeText(Login.this, "failt", Toast.LENGTH_SHORT).show();
                            progressHUD.dismissWithSuccess("登录失败");
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
