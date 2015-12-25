package com.sizhuo.ydxf.application;

import android.app.Application;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;

/**
 * 项目名称: YDXF
 * 类描述:  Application
 * Created by My灬xiao7
 * date: 2015/12/24
 *
 * @version 1.0
 */
public class MyApplication extends Application{
    public RequestQueue queue;
    @Override
    public void onCreate() {
        super.onCreate();
        queue = Volley.newRequestQueue(getApplicationContext());
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(getApplicationContext());
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(imageLoaderConfiguration);
    }

    public RequestQueue getHttpRequestQueue(){
        return queue;
    }
}
