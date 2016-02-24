package com.sizhuo.ydxf;

import android.app.Activity;
import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ListView;
import android.widget.Toast;

import com.sizhuo.ydxf.entity.VideoList;
import com.sizhuo.ydxf.util.NetworkCheck;
import com.tencent.qcload.playersdk.ui.VideoRootFrame;
import com.tencent.qcload.playersdk.util.PlayerListener;
import com.tencent.qcload.playersdk.util.VideoInfo;
import com.wang.avi.AVLoadingIndicatorView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  视频播放
 * Created by My灬xiao7
 * date: 2016/2/19
 *
 * @version 1.0
 */
public class VideoDetails extends Activity {
    private com.tencent.qcload.playersdk.ui.VideoRootFrame player;
    private AVLoadingIndicatorView loading;
    List<VideoInfo> videos=new ArrayList<VideoInfo>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videodetails);

        loading= (AVLoadingIndicatorView) findViewById(R.id.videodetail_loading);
        Intent intent = this.getIntent();
        VideoList videoList = (VideoList) intent.getSerializableExtra("data");
        //获取页面中的播放器控件
        player=(VideoRootFrame) findViewById(R.id.videodetail_player);

        VideoInfo v1=new VideoInfo();
        v1.description="标清";
        v1.type=VideoInfo.VideoType.MP4;
        if(!TextUtils.isEmpty(videoList.getSdAddress())){
            v1.url=videoList.getSdAddress();
            videos.add(v1);
        }

        if(!TextUtils.isEmpty(videoList.getHdAddress())){
            VideoInfo v2=new VideoInfo();
            v2.description="高清";
            v2.type=VideoInfo.VideoType.MP4;
            v2.url=videoList.getHdAddress();
            videos.add(v2);
        }

        //调用播放器的播放方法
        player.play(videos);



        player.setListener(new PlayerListener(){
            @Override
            public void onError(Exception arg0) {
                arg0.printStackTrace();  //这里断点抛出一个非法状态异常 ，见下面
            }

            @Override
            public void onStateChanged(int arg0) {
                Log.d("TAG", "player states:" + arg0);//程序在这里断点的话这里的状态依次监听到的是：2,1,2,3
                switch (arg0){
                    case 3:
                        loading.setVisibility(View.VISIBLE);
                        break;
                    case 5:
                        loading.setVisibility(View.GONE);
                        break;
                    case 4:
                        loading.setVisibility(View.GONE);
                        break;
                    case 6:
                        loading.setVisibility(View.GONE);
                        break;
                }
            }});

    }

    @Override
    protected void onPause() {
        super.onPause();
        player.pause();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        player.release();
    }
}
