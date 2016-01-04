package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.bm.library.PhotoView;

import java.security.PublicKey;
import java.util.List;

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
    private ImageView selectPhoto;
    private final int REQUEST_CODE_GALLERY = 0X100;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        toolbar = (Toolbar) findViewById(R.id.publish_toolbar);
        toolbar.setTitle("发表帖子");
        setSupportActionBar(toolbar);
        selectPhoto = (ImageView) findViewById(R.id.publish_photo_img);
        selectPhoto.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.publish_photo_img:
                GalleryFinal.openGalleryMuti(REQUEST_CODE_GALLERY, mOnHanlderResultCallback);
                break;
        }
    }

    GalleryFinal.OnHanlderResultCallback mOnHanlderResultCallback = new GalleryFinal.OnHanlderResultCallback() {
        @Override
        public void onHanlderSuccess(int reqeustCode, List<PhotoInfo> resultList) {
            Toast.makeText(Publish.this, "选择了" + resultList.size() + "张图", Toast.LENGTH_SHORT).show();
            for (int i = 0; i < resultList.size(); i++) {
            Log.d("xinwen",resultList.get(i).getPhotoPath());
            }
        }

        @Override
        public void onHanlderFailure(int requestCode, String errorMsg) {

        }
    };
}
