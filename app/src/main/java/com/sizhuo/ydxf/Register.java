package com.sizhuo.ydxf;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.telephony.TelephonyManager;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;

import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

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
    private String nameStr, pwdStr, pwdStr2;//账号，密码编辑
    private String phoneId = "无法获得";
    private String ipAddress = "无法获得";
    private Button registerBtn;//登录按钮
    //网络请求相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String REQUEST_TAB = "REGISTER_REQUEST";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        //初始化控件
        initViews();
        queue = Volley.newRequestQueue(this);
        //创建电话管理

       TelephonyManager tm = (TelephonyManager) this
               .getSystemService(Context.TELEPHONY_SERVICE);
        //获取手机号码
        phoneId = tm.getLine1Number();
        Log.d("log.d", "num" + phoneId);
        //输入验证
        inputValidate();
        registerBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("log.d", "click");
                nameStr = nameEdit.getText().toString();
                pwdStr = pwdEdit.getText().toString();
                pwdStr2 = pwdEdit2.getText().toString();
                if (TextUtils.isEmpty(nameStr)) {
                    nameLayout.setHint("请输入用户名");
                    nameEdit.requestFocus();
                    return;
                }
                if (nameStr.length() < 6 || nameStr.length() > 12) {
                    return;
                }
                if (TextUtils.isEmpty(pwdStr)) {
                    pwdLayout.setHint("请输入密码");
                    pwdEdit.requestFocus();
                    return;
                }
                if (pwdStr.length() < 6 || pwdStr.length() > 15) {
                    return;
                }
                if (TextUtils.isEmpty(pwdStr2)) {
                    pwdLayout2.setHint("请再次输入密码");
                    pwdEdit2.requestFocus();
                    return;
                }
                if (pwdStr2.length() < 6 || pwdStr2.length() > 15) {
                    return;
                }
                if (!pwdStr.equals(pwdStr2)) {
                    pwdLayout2.setErrorEnabled(true);
                    pwdLayout2.setError("密码不一致");
                    return;
                } else {
                    pwdLayout2.setErrorEnabled(false);
                }
                Toast.makeText(Register.this, "正在注册", Toast.LENGTH_SHORT).show();
                //注册
                register(nameStr, pwdStr2);
            }
        });
    }

    private void inputValidate() {
        nameEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6 || s.length() > 12) {
                    nameLayout.setErrorEnabled(true);
                    nameLayout.setError("用户名应该是6-12个英文字符和下划线");
                } else {
                    nameLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwdEdit.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6 || s.length() > 15) {
                    pwdLayout.setErrorEnabled(true);
                    pwdLayout.setError("密码长度在6-15字符之间");
                    return;
                } else {
                    pwdLayout.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwdEdit2.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6 || s.length() > 15) {
                    pwdLayout2.setErrorEnabled(true);
                    pwdLayout2.setError("密码长度在6-15字符之间");
                    return;
                } else {
                    pwdLayout2.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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

    private void register(final String userName, final String userPwd) {
        Map<String, String> map = new HashMap<>();
        map.put("userName",userName);
        map.put("userPwd",userPwd);
        /*map.put("nickName", userName);
        map.put("sex","");
        map.put("mobile", phoneId);
        map.put("portrait", "");
        map.put("wxOpenid","");
        map.put("qqOpenid","");
        map.put("wbOpenid","");
        map.put("registerIP","");*/
        JSONObject jsonObject = new JSONObject(map);
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.REGISTER, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d","result------"+jsonObject.toString());
                Intent intent = new Intent();
                intent.putExtra("regiName",userName);
                intent.putExtra("regiPwd",userPwd);
                setResult(RESULT_OK, intent);
                Register.this.finish();
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
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(REQUEST_TAB);
    }
}