package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ListView;

import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.VRefresh;
import com.tencent.qcload.playersdk.ui.VideoRootFrame;
import com.tencent.qcload.playersdk.util.VideoInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  电视栏目
 * Created by My灬xiao7
 * date: 2015/12/25
 *
 * @version 1.0
 */
public class VideoModule extends AppCompatActivity{
    private Toolbar toolbar;
    private com.tencent.qcload.playersdk.ui.VideoRootFrame videoRootFrame;
    private ListView listView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videomodule);
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.videomodule_toolbar);
        toolbar.setTitle("党建电视节目");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoModule.this.finish();
            }
        });
        listView = (ListView) findViewById(R.id.videomodule_list);
        //获取页面中的播放器控件
        final VideoRootFrame player=(VideoRootFrame) findViewById(R.id.videomodule_player);
//调用播放器的播放方法
        List<VideoInfo> videos=new ArrayList<VideoInfo>();
        VideoInfo v1=new VideoInfo();
        v1.description="标清";
        v1.type=VideoInfo.VideoType.MP4;

        v1.url="http://4500.vod.myqcloud.com/4500_d754e448e74c11e4ad9e37e079c2b389.f20.mp4?vkey=693D66AF23164CA4741745A2FE9675DCC4493BF10CF724CBE3769CB237121D AB55F3D494AC2C6DB7&ocid=12345";
        videos.add(v1);
        VideoInfo v2=new VideoInfo();
        v2.description="高清";
        v2.type=VideoInfo.VideoType.MP4;

        v2.url="http://4500.vod.myqcloud.com/4500_d754e448e74c11e4ad9e37e079c2b389.f0.mp4?vkey=77F279B72A3788656E0A14837DA6C89AA57D5CA46FBAD14A81FE3B63FE2DE92 C5668CBD27304071B&ocid=12345";
        videos.add(v2);
        player.play(videos);
    }
}
