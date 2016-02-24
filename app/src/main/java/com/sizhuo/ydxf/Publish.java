package com.sizhuo.ydxf;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
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
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.IconResult;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.PictureUtil;
import com.sizhuo.ydxf.view.HorizontalListView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.ex.DbException;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

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

//    private List<imgextra> imgextras = new ArrayList<>();
    private List<JSONObject> imgs = new ArrayList<>();
//    private List<String> imgString = new ArrayList<>();
    private JSONArray jsonArray = new JSONArray();

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;

    //网络请求相关
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String REQUEST_TAB = "REQUEST";

    private HorizontalListView horizontalListView;
    List<String> imgList = new ArrayList<>();//选择的图片

    private ProgressDialog dialog;
    @Override
    protected void onResume() {
        super.onResume();
        dbManager = new MyApplication().getDbManager();
        //检查登录状态
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                loginFlag = true;
//                Toast.makeText(Publish.this,"登录"+user.getNickName(),Toast.LENGTH_SHORT).show();
            }else{
                loginFlag = false;
//                Toast.makeText(Publish.this,"没有登录",Toast.LENGTH_SHORT).show();
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
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Publish.this.finish();
            }
        });
        titleEdit = (EditText) findViewById(R.id.public_title_edit);
        conEdit = (EditText) findViewById(R.id.public_content_edit);
        selectPhoto = (ImageView) findViewById(R.id.publish_photo_img);
        selectPhoto.setOnClickListener(this);
        queue = Volley.newRequestQueue(this);

        horizontalListView = (HorizontalListView) findViewById(R.id.public_horlistview);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_photo_img:
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                break;
        }
    }

    private void saveCroppedImage(Bitmap bmp) {
        File file = new File(getApplicationContext().getFilesDir().getAbsolutePath());
        Log.d("log.d",getApplicationContext().getFilesDir().getAbsolutePath());
        if (!file.exists())
            file.mkdir();
        /*file = new File("/sdcard/temp.jpg".trim());
        String fileName = file.getName();
        String mName = fileName.substring(0, fileName.lastIndexOf("."));
        String sName = fileName.substring(fileName.lastIndexOf("."));
        getApplicationContext().getFilesDir().getAbsolutePath()*/
        // /sdcard/myFolder/temp_cropped.jpg
        String newFilePath = getApplicationContext().getFilesDir().getAbsolutePath()+System.currentTimeMillis();
        file = new File(newFilePath);
        try {
            file.createNewFile();
            FileOutputStream fos = new FileOutputStream(file);
            bmp.compress(Bitmap.CompressFormat.JPEG, 50, fos);
            fos.flush();
            fos.close();
            imgList.add("file://" + newFilePath);
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

    GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
//            Toast.makeText(Publish.this, "选择了" + resultList.size() + "张图", Toast.LENGTH_SHORT).show();
            imgList.clear();
            for (int i = 0; i < resultList.size(); i++) {
//                imgList.add("file://" + resultList.get(i).getPhotoPath());
                Bitmap bitmap = PictureUtil.getSmallBitmap(resultList.get(i).getPhotoPath());
                saveCroppedImage(bitmap);
            }
            Gallary gallary = new Gallary(imgList);
            horizontalListView.setAdapter(gallary);
            gallary.notifyDataSetChanged();
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };

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
                Log.d("log.d", result.getUrl() + "图片上传成功dz");
                //图片上传成功
                Map<String, String> map = new HashMap<>();
                map.put("url", result.getUrl());
                JSONObject jsonObject = new JSONObject(map);
                jsonArray.put(jsonObject);
//                imgs.add(jsonObject);
                Log.d("log.d", jsonObject.toString() + "json");
//                imgextras.add(imgextra);
                if(jsonArray.length()==imgList.size()){
                    update(jsonArray);
                    Log.d("log.d", imgs.size() + "图片上传成功开始发帖");
                }
                Log.d("log.d",imgs.size()+"图片上传发帖");
            }else{
                //
                Log.d("log.d", imgs.size() + "图片没有上传完");
                dialog.dismiss();
            }

            /*Toast.makeText(PersonInfo.this,"上传成功",Toast.LENGTH_SHORT).show();*/
            Log.e("log.d", "post_running222222---------");
        }else{
            dialog.dismiss();
            Toast.makeText(Publish.this, "上传失败",Toast.LENGTH_SHORT).show();
        }
        outStream.close();
        conn.disconnect();
        in.close();
        Log.e("log.d", sb2.toString() + "----------");
        return sb2.toString();
    }

    private void update(JSONArray imgArray) {
        Map<String, Object> map = new HashMap<>();
        map.put("userName", user.getUserName());
        map.put("userPwd", user.getUserPwd());
        map.put("title", titleEdit.getText().toString().trim());
        map.put("content", conEdit.getText().toString().trim());
        if(imgArray!=null){
            map.put("imgextra", imgArray);
        }
        JSONObject jsonObject = new JSONObject(map);
        Log.d("log.d", "jsonObject-------------------" + jsonObject.toString());
        jsonObjectRequest = new JsonObjectRequest(Request.Method.POST, Const.PUBLISH, jsonObject, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("log.d", "result" + jsonObject.toString());
                try {
                    String code = jsonObject.getString("code");
                    //修改成功，返回信息data
                    if (code.equals("200")) {
                        Toast.makeText(Publish.this, "发帖成功", Toast.LENGTH_SHORT).show();
                        Intent data = new Intent();
                        Bundle bundle = new Bundle();
                        data.putExtra("result", "succ");
                        data.putExtras(bundle);
                        setResult(RESULT_OK, data);
                        Publish.this.finish();
                    } else if (code.equals("400")) {
                        Toast.makeText(Publish.this, "发帖失败", Toast.LENGTH_SHORT).show();
                    } else if (code.equals("201")){
                        Toast.makeText(Publish.this, "您已被禁言，无法发帖", Toast.LENGTH_SHORT).show();
                        Publish.this.finish();
                    }else{
                        Toast.makeText(Publish.this, "网络错误", Toast.LENGTH_SHORT).show();
                    }
                    dialog.dismiss();
                } catch (JSONException e2) {
                    e2.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
                Log.d("log.d", volleyError.toString() + "--------error");
                Log.e("log.d", volleyError.getMessage(), volleyError);
                byte[] htmlBodyBytes = volleyError.networkResponse.data;
                Log.e("log.d", new String(htmlBodyBytes), volleyError);
                Toast.makeText(Publish.this, "服务器异常", Toast.LENGTH_SHORT).show();
                dialog.dismiss();
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
    protected void onPause() {
        super.onPause();
        if(dialog!=null){
            dialog.dismiss();
        }
    }

    //toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.publish_menu, menu);
        return true;
    }

    //toolbar菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.piblish_menu_item01:
                if(loginFlag==true){
                    if(TextUtils.isEmpty(titleEdit.getText().toString().trim())){
                        Toast.makeText(Publish.this, "请填写标题", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    if(TextUtils.isEmpty(conEdit.getText().toString().trim())){
                        Toast.makeText(Publish.this, "请填写内容", Toast.LENGTH_SHORT).show();
                        return false;
                    }
                    dialog = ProgressDialog.show(Publish.this,
                            null, "正在提交", true, true);
                    dialog.show();
                    if(imgList.size()>0){
                        //选择了图片
                        new Thread(new Runnable() {
                            @Override
                            public void run() {
                                try {
                                    for (int i=0; i<imgList.size();i++) {
                                        Map<String, File> files = new HashMap<>();
                                        Map<String, String> param = new HashMap<>();
                                        files.put("icon", new File(imgList.get(i).substring(7, imgList.get(i).length())));
//                                        Log.d("log.d", imgList.get(i).substring(7, imgList.get(0).length()) + "--------------imgsrc");
                                        //上传图片
                                        post(Const.UPLOADIMG, param, files);
                                    }
                                } catch (IOException e) {
                                    e.printStackTrace();
                                }
                            }
                        }).start();
                    }else{
//                        Toast.makeText(Publish.this, "没有选择图片", Toast.LENGTH_SHORT).show();
                        update(null);
                    }

                    }else{
                    Intent intent = new Intent(Publish.this, Login.class);
                    Publish.this.startActivity(intent);
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

    class Gallary extends BaseAdapter{
        private List<String> list;

        public Gallary(List<String> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(final int position, View convertView, ViewGroup parent) {
            convertView = LayoutInflater.from(parent.getContext()).inflate(R.layout.hor_list_item,parent,false);
            ImageView imageView = (ImageView) convertView.findViewById(R.id.hor_list_item_img);
            ImageView removeBtn = (ImageView) convertView.findViewById(R.id.hor_list_item_remove);
            if(!TextUtils.isEmpty(list.get(position))){
                ImageLoaderHelper.getIstance().loadImg(list.get(position),imageView);
            }
            removeBtn.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    list.remove(position);
                    notifyDataSetChanged();
                }
            });
            return convertView;
        }

        public void notifyDataSetChanged(List<String> list) {
            this.list = list;
            notifyDataSetChanged();
        }
    }

}
