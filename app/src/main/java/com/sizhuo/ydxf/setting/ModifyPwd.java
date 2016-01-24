package com.sizhuo.ydxf.setting;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.Toast;

import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.HashMap;
import java.util.Map;

/**
 * 项目名称: YDXF
 * 类描述:  修改密码
 * Created by My灬xiao7
 * date: 2016/1/13
 *
 * @version 1.0
 */
public class ModifyPwd extends AppCompatActivity {
    private Toolbar toolbar;
    private TextInputLayout pwdLayout02, pwdLayout03;
    private EditText pwdEdit01, pwdEdit02, pwdEdit03;

    private DbManager dbManager;//数据库操作
    private User user;

    //网络请求相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String REQUEST_TAB = "BINDER_REQUEST";

    private ProgressDialog dialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_modifypwd);
        initViews();
        queue = Volley.newRequestQueue(this);
        dbManager = new MyApplication().getDbManager();
        try {
            user = dbManager.findFirst(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.set_modifypwd_toolbar);
        toolbar.setTitle("修改密码");
        setSupportActionBar(toolbar);
        pwdEdit01 = (EditText) findViewById(R.id.set_modifypwd_oldpwd_edit);
        pwdEdit02 = (EditText) findViewById(R.id.set_modifypwd_newpwd_edit);
        pwdLayout02 = (TextInputLayout) findViewById(R.id.set_modifypwd_textinput_newpwd);
        pwdEdit03 = (EditText) findViewById(R.id.set_modifypwd_newpwd_edit2);
        pwdLayout03 = (TextInputLayout) findViewById(R.id.set_modifypwd_textinput_newpwd2);
        pwdEdit02.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6 || s.length() > 15) {
                    pwdLayout02.setErrorEnabled(true);
                    pwdLayout02.setError("密码长度在6-15字符之间");
                    return;
                } else {
                    pwdLayout02.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        pwdEdit03.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() < 6 || s.length() > 15) {
                    pwdLayout03.setErrorEnabled(true);
                    pwdLayout03.setError("密码长度在6-15字符之间");
                    return;
                } else {
                    pwdLayout03.setErrorEnabled(false);
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
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
                if(TextUtils.isEmpty(pwdEdit01.getText().toString().trim())){
                    Toast.makeText(ModifyPwd.this, "请输入原密码", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (TextUtils.isEmpty(pwdEdit02.getText().toString().trim())){
                    Toast.makeText(ModifyPwd.this, "请输入新密码", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (pwdEdit02.getText().toString().trim().length()< 6 || pwdEdit02.getText().toString().trim().length() > 15){
                    Toast.makeText(ModifyPwd.this, "新密码长度在6-15字符之间", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (TextUtils.isEmpty(pwdEdit03.getText().toString().trim())){
                    Toast.makeText(ModifyPwd.this, "请再次输入新密码", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (pwdEdit03.getText().toString().trim().length()< 6 || pwdEdit03.getText().toString().trim().length() > 15){
                    Toast.makeText(ModifyPwd.this, "确认密码长度在6-15字符之间", Toast.LENGTH_SHORT).show();
                    return false;
                }
                if (!pwdEdit03.getText().toString().trim().equals(pwdEdit02.getText().toString().trim())){
                    Toast.makeText(ModifyPwd.this, "两次密码不一致", Toast.LENGTH_SHORT).show();
                    return false;
                }
                dialog = ProgressDialog.show(ModifyPwd.this,
                        null, "正在提交", true, true);
                dialog.show();
                    Map<String, String> map = new HashMap<>();
                    map.put("userName",user.getUserName());
                    map.put("userPwd",user.getUserPwd());
                    map.put("newPwd", pwdEdit03.getText().toString().trim());
                    JSONObject jsonObject = new JSONObject(map);
                    Log.d("log.d", "jsonObject" + jsonObject.toString());
                    jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.UPDATEINFO, jsonObject, new Response.Listener<JSONObject>() {
                        @Override
                        public void onResponse(JSONObject jsonObject) {
                            Log.d("log.d","result"+jsonObject.toString());
                            try {
                                String code = jsonObject.getString("code");
                                //修改成功，返回信息data
                                if(code.equals("200")) {
                                    //修改用户信息
                                    user.setNickName(pwdEdit03.getText().toString().trim());
                                    dbManager.update(user);
                                    Toast.makeText(ModifyPwd.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    dialog.dismiss();
                                    ModifyPwd.this.finish();
                                }else if(code.equals("400")){
                                    Toast.makeText(ModifyPwd.this, "修改失败", Toast.LENGTH_SHORT).show();
                                }else{
                                    Toast.makeText(ModifyPwd.this, "修改失败", Toast.LENGTH_SHORT).show();
                                }
                                dialog.dismiss();
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
                            Toast.makeText(ModifyPwd.this, "网络异常", Toast.LENGTH_SHORT).show();
                            dialog.dismiss();
                        }
                    }){
                        @Override
                        public Map<String, String> getHeaders() throws AuthFailureError {
                            HashMap<String, String> headers = new HashMap<String, String>();
                            headers.put("Accept", "application/json");
                            return headers;
                        }
                    };
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        queue.add(jsonObjectRequest);
                        jsonObjectRequest.setTag(REQUEST_TAB);
                    }
                }, 1200);
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(REQUEST_TAB);
    }
}
