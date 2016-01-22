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
import android.widget.EditText;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
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
 * 类描述:  修改昵称
 * Created by My灬xiao7
 * date: 2016/1/20
 *
 * @version 1.0
 */
public class ModifyNikeName extends AppCompatActivity {
    private Toolbar toolbar;
    private EditText nameEdit;

    private DbManager dbManager;//数据库操作
    private User user;

    //网络请求相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String REQUEST_TAB = "BINDER_REQUEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_modifynike);
        initViews();
        queue = Volley.newRequestQueue(this);
        dbManager = new MyApplication().getDbManager();

        try {
            user = dbManager.findFirst(User.class);
        } catch (DbException e) {
            e.printStackTrace();
        }
        nameEdit.setText(user.getNickName());
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.modifynike_toolbar);
        toolbar.setTitle("修改昵称");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ModifyNikeName.this.finish();
            }
        });
        nameEdit = (EditText) findViewById(R.id.modifynike_edit);
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
                final String name = nameEdit.getText().toString().trim();
                if(TextUtils.isEmpty(name)){
                    Toast.makeText(ModifyNikeName.this, "昵称不能为空", Toast.LENGTH_SHORT).show();
                }else if(name.equals(user.getNickName())){
                    Toast.makeText(ModifyNikeName.this, "昵称不能重复", Toast.LENGTH_SHORT).show();
                }else{
                    Map<String, String> map = new HashMap<>();
                    map.put("userName",user.getUserName());
                    map.put("userPwd",user.getUserPwd());
                    map.put("nickName",name);
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
                                    user.setNickName(name);
                                    dbManager.update(user);
                                    Toast.makeText(ModifyNikeName.this, "修改成功", Toast.LENGTH_SHORT).show();
                                    ModifyNikeName.this.finish();
                                }else if(code.equals("400")){
                                    Toast.makeText(ModifyNikeName.this, "修改失败", Toast.LENGTH_SHORT).show();
                                }else{
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
                            Toast.makeText(ModifyNikeName.this, "服务器异常", Toast.LENGTH_SHORT).show();
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
                break;
        }
        return true;
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        queue.cancelAll(REQUEST_TAB);
    }
}
