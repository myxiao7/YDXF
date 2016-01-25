package com.sizhuo.ydxf.application;

import android.app.Application;
import android.graphics.Color;
import android.util.Log;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.baidu.mapapi.SDKInitializer;
import com.daimajia.slider.library.BuildConfig;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.util.UILImageLoader;
import com.umeng.socialize.PlatformConfig;


import org.xutils.DbManager;
import org.xutils.x;

import cn.finalteam.galleryfinal.CoreConfig;
import cn.finalteam.galleryfinal.FunctionConfig;
import cn.finalteam.galleryfinal.GalleryFinal;
import cn.finalteam.galleryfinal.ThemeConfig;

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
//    public DbManager.DaoConfig daoConfig;
    public DbManager dbManager;
    @Override
    public void onCreate() {
        super.onCreate();
        x.Ext.init(this);
        x.Ext.setDebug(BuildConfig.DEBUG);
        queue = Volley.newRequestQueue(getApplicationContext());
        //微信 appid appsecret
        PlatformConfig.setWeixin("wx9b63dbf2820914ef", "5aa18dcbac1f9a9fba06c48fd0789876");
        //新浪微博 appkey appsecret
        PlatformConfig.setSinaWeibo("3132564276", "622b15838ec6c62f505c758d0ed7a027");
        // QQ和Qzone appid appkey
        PlatformConfig.setQQZone("1105095650", "SY79nsQpRpeIN29I");
        //创建默认的ImageLoader配置参数
        ImageLoaderConfiguration imageLoaderConfiguration = ImageLoaderConfiguration.createDefault(getApplicationContext());
        //Initialize ImageLoader with configuration.
        ImageLoader.getInstance().init(imageLoaderConfiguration);

        ThemeConfig  theme = new ThemeConfig.Builder()
        .setTitleBarBgColor(Color.RED)//选择框选中颜色
                .build();
        //配置功能
        FunctionConfig functionConfig = new FunctionConfig.Builder()
                .setEnableCamera(true)
                .setEnableEdit(true)
                .setEnableCrop(true)
                .setEnableRotate(true)
                .setCropSquare(true)
                .setEnablePreview(true)
                .setMutiSelectMaxSize(3)
                .setCropHeight(1024)
                .setCropWidth(768)
        .build();
        //配置imageloader
        UILImageLoader imageloader = new UILImageLoader();
        CoreConfig coreConfig = new CoreConfig.Builder(getApplicationContext(), imageloader, theme)
                .setDebug(BuildConfig.DEBUG)
                .setFunctionConfig(functionConfig)
        .build();
        GalleryFinal.init(coreConfig);
        SDKInitializer.initialize(getApplicationContext());
        Log.d("log.d","myapplication");
    }

    public RequestQueue getHttpRequestQueue(){
        return queue;
    }

    public DbManager getDbManager(){
        //创建数据库
        DbManager.DaoConfig daoConfig = new DbManager.DaoConfig()
                .setDbName("ydxf_db")
                .setDbVersion(1);
        dbManager = x.getDb(daoConfig);
        return dbManager;
    }
}
