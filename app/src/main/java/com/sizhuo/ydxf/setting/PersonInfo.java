package com.sizhuo.ydxf.setting;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.RelativeLayout;
import android.widget.TextView;
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
import com.sizhuo.ydxf.entity.imgextra;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.model.PhotoInfo;
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
    private TextView sexTv;//性别
    private TextView accountTv;//用户名
    private TextView sex01, sex02;//男，女
    private RelativeLayout iconRe, nickRe, sexRe;//头像，昵称，性别

    private final int REQUEST_CODE_GALLERY = 0X206;
    //配置功能
    FunctionConfig functionConfig;

    private DbManager dbManager;//数据库操作
    private User user;

    //网络请求相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String REQUEST_TAB = "BINDER_REQUEST";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personinfo);
        initViews();
        initEvents();
        queue = Volley.newRequestQueue(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
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
                ImageLoaderHelper.getIstance().loadImg("file://storage/sdcard1/GalleryFinal/edittemp/IMG_20160118_210702_crop.jpg",icon);
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                PersonInfo.this.finish();
            }
        });
        icon = (CircleImageView) findViewById(R.id.set_personinfo_icon_img);
        nickNameTv = (TextView) findViewById(R.id.set_personinfo_nike_tv);
        sexTv = (TextView) findViewById(R.id.set_personinfo_sex_tv);
        accountTv = (TextView) findViewById(R.id.set_personinfo_username_tv);
        iconRe = (RelativeLayout) findViewById(R.id.set_personinfo_icon_re);
        nickRe = (RelativeLayout) findViewById(R.id.set_personinfo_nike_re);
        sexRe = (RelativeLayout) findViewById(R.id.set_personinfo_sex_re);

        //配置功能
        functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setMutiSelectMaxSize(1)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setEnablePreview(true)
                .setCropHeight(120)
                .setCropWidth(120)
                .setCropSquare(true)
                .setForceCrop(true)
                .setForceCrop(true)
                        .build();
    }

    private void initEvents() {
        iconRe.setOnClickListener(this);
        nickRe.setOnClickListener(this);
        sexRe.setOnClickListener(this);
    }

   /* @Override
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
*/
   GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
       @Override
       public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
           Toast.makeText(PersonInfo.this, "选择了" + resultList.size() + "张图", Toast.LENGTH_SHORT).show();
           for (int i = 0; i < resultList.size(); i++) {
               Log.d("xinwen", resultList.get(i).getPhotoPath());
               if(!TextUtils.isEmpty(resultList.get(i).getPhotoPath())){


                   ImageLoaderHelper.getIstance().loadImg("file:///storage/sdcard1/GalleryFinal/edittemp/IMG_20160118_210702_crop.jpg",icon);
               }
           }
       }

       @Override
       public void onHanlderFailure(int requestCode, String errorMsg) {

       }
   };

   AlertDialog dialog;
    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.set_personinfo_icon_re:
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY,functionConfig,mOnHanlderResultCallback);
                break;

            case R.id.set_personinfo_nike_re:
                Intent intent = new Intent(PersonInfo.this, ModifyNike.class);
                startActivity(intent);
                break;

            case R.id.set_personinfo_sex_re:
                dialog = new AlertDialog.Builder(this).create();
                dialog.show();
                Window window = dialog.getWindow();
                window.setContentView(R.layout.per_info_dialog_sex);
                sex01 = (TextView) window.findViewById(R.id.per_info_dialog_sex_01);
                sex02 = (TextView) window.findViewById(R.id.per_info_dialog_sex_02);
                sex01.setOnClickListener(this);
                sex02.setOnClickListener(this);
                break;

            case R.id.per_info_dialog_sex_01:
                Toast.makeText(this,"男",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;

            case R.id.per_info_dialog_sex_02:
                Toast.makeText(this,"女",Toast.LENGTH_SHORT).show();
                dialog.dismiss();
                break;
        }
    }
    private void update(final String sex){
            Map<String, String> map = new HashMap<>();
            map.put("userName",user.getUserName());
            map.put("userPwd",user.getUserPwd());
            map.put("sex",sex);
            JSONObject jsonObject = new JSONObject(map);
            Log.d("log.d", "jsonObject" + jsonObject.toString());
            jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.LOGIN, jsonObject, new Response.Listener<JSONObject>() {
                @Override
                public void onResponse(JSONObject jsonObject) {
                    Log.d("log.d","result"+jsonObject.toString());
                    try {
                        String code = jsonObject.getString("code");
                        //修改成功，返回信息data
                        if(code.equals("200")) {
                            //修改用户信息
                            user.setSex(sex);
                            dbManager.update(user);
                            sexTv.setText(sex);
                            Toast.makeText(PersonInfo.this, "修改成功", Toast.LENGTH_SHORT).show();
                        }else if(code.equals("400")){
                            Toast.makeText(PersonInfo.this, "修改失败", Toast.LENGTH_SHORT).show();
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
                    Toast.makeText(PersonInfo.this, "服务器异常", Toast.LENGTH_SHORT).show();
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
