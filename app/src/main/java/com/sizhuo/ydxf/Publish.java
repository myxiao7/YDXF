package com.sizhuo.ydxf;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.bm.library.PhotoView;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.PublicData;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.entity.imgextra;
import com.sizhuo.ydxf.util.MyJsonObjectRequest;
import com.sizhuo.ydxf.util.StatusBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.security.PublicKey;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import Decoder.BASE64Encoder;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;

/**
 * 项目名称: YDXF
 * 类描述:  发布帖子
 * Created by My灬xiao7
 * date: 2015/12/31
 *
 * @version 1.0
 */
public class Publish extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;
    private EditText titleEdit, conEdit;
    private ImageView selectPhoto;
    private ImageView uploadPhoto;
    private final int REQUEST_CODE_GALLERY = 0X100;
    private final String REQUEST_TAG = "request";

    private List<imgextra> imgextras = new ArrayList<>();
    private Map<String, String> imgMap = new HashMap<>();
    private RequestQueue queue;

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;

    @Override
    protected void onResume() {
        super.onResume();
        dbManager = new MyApplication().getDbManager();
        //检查登录状态
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                loginFlag = true;
                Toast.makeText(Publish.this,"登录"+user.getNickName(),Toast.LENGTH_SHORT).show();
            }else{
                loginFlag = false;
                Toast.makeText(Publish.this,"没有登录",Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        toolbar = (Toolbar) findViewById(R.id.publish_toolbar);
        toolbar.setTitle("发表帖子");
        setSupportActionBar(toolbar);
        titleEdit = (EditText) findViewById(R.id.public_title_edit);
        conEdit = (EditText) findViewById(R.id.public_content_edit);
        selectPhoto = (ImageView) findViewById(R.id.publish_photo_img);
        selectPhoto.setOnClickListener(this);
        selectPhoto = (ImageView) findViewById(R.id.publish_photo_img2);
        selectPhoto.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_photo_img:
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                break;
            case R.id.publish_photo_img2:
                Map<String,Object> map = new HashMap<>();
                map.put("userName",user.getUserName());
                map.put("userPwd",user.getUserPwd());
                map.put("title", titleEdit.getText().toString());
                map.put("content", conEdit.getText().toString());
                map.put("imgextra", imgextras.toString());
                JSONObject jsonObject = new JSONObject(map);
                Log.d("xinwen",map.toString()+"++++++++");
                MyJsonObjectRequest request = new MyJsonObjectRequest(Request.Method.POST, "http://112.54.80.235:50406/IndustryPioneer.svc/insertCard", "", new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("xinwen",jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("xinwen",volleyError.toString());
                        Log.e("log.d", volleyError.getMessage(), volleyError);
                        byte[] htmlBodyBytes = volleyError.networkResponse.data;
                        Log.e("log.d", new String(htmlBodyBytes), volleyError);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> header = new HashMap<>();
                        header.put("Accept", "application/json");
                        header.put("charset", "utf-8");
                        return header;
                    }
                };
                JsonObjectRequest request2 = new JsonObjectRequest(Request.Method.POST, "http://112.54.80.235:50406/IndustryPioneer.svc/insertCard", jsonObject, new Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject jsonObject) {
                        Log.d("xinwen",jsonObject.toString());
                    }
                }, new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError volleyError) {
                        Log.d("xinwen",volleyError.toString());
                        Log.e("log.d", volleyError.getMessage(), volleyError);
                        byte[] htmlBodyBytes = volleyError.networkResponse.data;
                        Log.e("log.d", new String(htmlBodyBytes), volleyError);
                    }
                }) {
                    @Override
                    public Map<String, String> getHeaders() throws AuthFailureError {
                        Map<String,String> header = new HashMap<>();
                        header.put("Accept", "application/json");
                        header.put("charset", "utf-8");
                        return header;
                    }
                };
                queue.add(request);
//                request.setTag(REQUEST_TAG);
                break;
        }
    }

    GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            Toast.makeText(Publish.this, "选择了" + resultList.size() + "张图", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < resultList.size(); i++) {
                Log.d("xinwen", resultList.get(i).getPhotoPath());
                new Thread(new Runnable() {
                    @Override
                    public void run() {

                    }
                }).start();
            }
            Log.d("xinwen",imgextras.size()+"+++++++++");
            Log.d("xinwen",imgextras.get(0).getUrl()+"+++++++++");
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

}
