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
import com.sizhuo.ydxf.entity.IconResult;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.entity.imgextra;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.StatusBar;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.common.Callback;
import org.xutils.ex.DbException;
import org.xutils.ex.HttpException;
import org.xutils.http.RequestParams;
import org.xutils.x;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import Decoder.BASE64Encoder;
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
            if (user != null) {
                if (!TextUtils.isEmpty(user.getPortrait())) {
                    ImageLoaderHelper.getIstance().loadImg(user.getPortrait(), icon);
                }
                if (!TextUtils.isEmpty(user.getNickName())) {
                    nickNameTv.setText(user.getNickName());
                }
                if (!TextUtils.isEmpty(user.getSex())) {
                    sexTv.setText(user.getSex());
                }
                if (!TextUtils.isEmpty(user.getUserName())) {
                    accountTv.setText(user.getUserName());
                }
//                ImageLoaderHelper.getIstance().loadImg("file:///storage/sdcard1/DCIM/GalleryFinal/IMG20160121114606.jpg",icon);
            } else {
                Toast.makeText(this, "请先登录", Toast.LENGTH_SHORT).show();
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
    GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback;

    {
        mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
            @Override
            public void onHanlderSuccess(int reqeustCode, final List<PhotoInfo> resultList) {
//                Toast.makeText(PersonInfo.this, "选择了" + resultList.size() + "张图", Toast.LENGTH_SHORT).show();
                for (int i = 0; i < resultList.size(); i++) {
                    Log.d("xinwen", resultList.get(i).getPhotoPath());
                    if (!TextUtils.isEmpty(resultList.get(i).getPhotoPath())) {
//                        ImageLoaderHelper.getIstance().loadImg("file:///storage/sdcard1/GalleryFinal/edittemp/IMG_20160118_210702_crop.jpg", icon);
                        //图片上传
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    Map<String, File> files = new HashMap<>();
                                    Map<String, String> param = new HashMap<>();
                                    files.put("icon", new File(resultList.get(0).getPhotoPath()));
                                    //上传图片
                                    post("http://112.54.80.235:50406/IndustryPioneer.svc/uploadImage", param, files);
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }
                }
            }

            @Override
            public void onHanlderFailure(int requestCode, String errorMsg) {

            }
        };
    }

    public String post(String url, Map<String, String> params,
                       Map<String, File> files) throws IOException {
        Log.e("log.d", "---------------post_running");
        String BOUNDARY = UUID.randomUUID().toString();
        String PREFIX = "--", LINEND = "\r\n";
        String MULTIPART_FROM_DATA = "multipart/form-data";
        String CHARSET = "UTF-8";

        URL uri = new URL(url);
        HttpURLConnection conn = (HttpURLConnection) uri.openConnection();
        conn.setReadTimeout(10 * 1000); // 缓存的最长时间
        conn.setDoInput(true);// 允许输入
        conn.setDoOutput(true);// 允许输出
        conn.setUseCaches(false); // 不允许使用缓存
        conn.setRequestMethod("POST");
        conn.setRequestProperty("connection", "keep-alive");
        conn.setRequestProperty("Charsert", "UTF-8");
        conn.setRequestProperty("Content-Type", MULTIPART_FROM_DATA
                + ";boundary=" + BOUNDARY);
        Log.e("log.d", "------------post_running");
        // 首先组拼文本类型的参数
        StringBuilder sb = new StringBuilder();
        for (Map.Entry<String, String> entry : params.entrySet()) {
            String map = String.valueOf(entry.getKey());
            sb.append(PREFIX);
            sb.append(BOUNDARY);
            sb.append(LINEND);
            sb.append("Content-Disposition: form-data; name=\"" + map + "\" --");
            // sb.append("Content-Disposition: form-data; name=\"file\";" +
            // entry.getKey() + "\"="+params.get(entry.getKey())+"\"" + LINEND);
            sb.append("Content-Type: text/plain; charset=" + CHARSET + LINEND);
            sb.append("Content-Transfer-Encoding: 8bit" + LINEND);
            sb.append(LINEND);
            sb.append(entry.getValue());
            sb.append(LINEND);
        }
        Log.e("log.d", sb.toString() + "----------");
        Log.e("log.d", "------------post_running");
        DataOutputStream outStream = new DataOutputStream(
                conn.getOutputStream());
        outStream.write(sb.toString().getBytes());
        // 发送文件数据
        if (files != null)
            for (Map.Entry<String, File> file : files.entrySet()) {
                Log.e("log.d", "post_running----------");
                StringBuilder sb1 = new StringBuilder();
                sb1.append(PREFIX);
                sb1.append(BOUNDARY);
                sb1.append(LINEND);
//				sb1.append("Content-Disposition: form-data;name=\""
//						+ file.getKey() + "\"; filename=\""
//						+ file.getValue().getName() + "\"" + LINEND);
                sb1.append("Content-Type: application/jpg; charset=" + CHARSET
                        + LINEND);
                sb1.append(LINEND);

                outStream.write(sb1.toString().getBytes());

                InputStream is = new FileInputStream(file.getValue());
                byte[] buffer = new byte[1024];
                int len = 0;
                while ((len = is.read(buffer)) != -1) {
                    outStream.write(buffer, 0, len);
                }

                is.close();
                outStream.write(LINEND.getBytes());
            }

        // 请求结束标志
        byte[] end_data = (PREFIX + BOUNDARY + PREFIX + LINEND).getBytes();
        outStream.write(end_data);
        outStream.flush();
        // 得到响应码
        int res = conn.getResponseCode();
        Log.e("log.d", res + "------code");
        InputStream in = conn.getInputStream();
        StringBuilder sb2 = new StringBuilder();

        if (res == 200) {
            int ch;
            while ((ch = in.read()) != -1) {
                sb2.append((char) ch);
            }
            IconResult result = JSON.parseObject(sb2.toString(), IconResult.class);
            if(result.getCode().equals("200")){
                //图片上传成功
                update(result.getUrl(), null);
            }else{
                //
            }

            /*Toast.makeText(PersonInfo.this,"上传成功",Toast.LENGTH_SHORT).show();*/
            Log.e("log.d", "post_running222222---------");
        }else{
            Toast.makeText(PersonInfo.this,"上传失败",Toast.LENGTH_SHORT).show();
        }
        outStream.close();
        conn.disconnect();
        in.close();
        Log.e("log.d", sb2.toString() + "----------");
        return sb2.toString();
    }

    AlertDialog dialog;
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.set_personinfo_icon_re:
                GalleryFinal.openGallerySingle(REQUEST_CODE_GALLERY, functionConfig, mOnHanlderResultCallback);
                break;

            case R.id.set_personinfo_nike_re:
                Intent intent = new Intent(PersonInfo.this, ModifyNikeName.class);
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
//                Toast.makeText(this, "男", Toast.LENGTH_SHORT).show();
                if(sexTv.getText().equals("女")){
                    update(null, "男");
                    dialog.dismiss();
                }
                break;

            case R.id.per_info_dialog_sex_02:
//                Toast.makeText(this, "女", Toast.LENGTH_SHORT).show();
                if(sexTv.getText().equals("男")){
                    update(null,"女");
                    dialog.dismiss();
                }
                break;
        }
    }

    private void update(final String iconStr,final String sex) {
        Map<String, String> map = new HashMap<>();
        map.put("userName", user.getUserName());
        map.put("userPwd", user.getUserPwd());
        if(!TextUtils.isEmpty(iconStr)){
            map.put("portrait", iconStr);
        }
        if(!TextUtils.isEmpty(sex)){
            map.put("sex", sex);
        }
        JSONObject jsonObject = new JSONObject(map);
        Log.d("log.d", "jsonObject" + jsonObject.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.UPDATEINFO, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d", "result" + jsonObject.toString());
                try {
                    String code = jsonObject.getString("code");
                    //修改成功，返回信息data
                    if (code.equals("200")) {
                        //修改用户信息
                        if(!TextUtils.isEmpty(iconStr)){
                            user.setPortrait(iconStr);
                            dbManager.update(user);
                            //修改当前头像
                            ImageLoaderHelper.getIstance().loadImg(user.getPortrait(), icon);
                        }
                        if(!TextUtils.isEmpty(sex)){
                            user.setSex(sex);
                            dbManager.update(user);
                            sexTv.setText(sex);
                        }
                        Toast.makeText(PersonInfo.this, "修改成功", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("400")) {
                        Toast.makeText(PersonInfo.this, "修改失败", Toast.LENGTH_SHORT).show();
                    } else {
                    }
                } catch (DbException e) {
                    e.printStackTrace();
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("log.d", volleyError.toString());
                Toast.makeText(PersonInfo.this, "服务器异常", Toast.LENGTH_SHORT).show();
            }
        }) {
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
