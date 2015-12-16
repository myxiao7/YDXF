package com.sizhuo.ydxf.util;

import android.annotation.TargetApi;
import android.app.Activity;
import android.content.Context;
import android.os.Build;
import android.view.Window;
import android.view.WindowManager;

import com.sizhuo.ydxf.R;


/**
 * 项目名称: XinWenShow
 * 类描述: 初始化statusbar
 * Created by My灬xiao7
 * date: 2015/12/9
 *
 * @version 1.0
 */
public class StatusBar {
    private Activity mActivity;
    public StatusBar(Activity activity) {
        this.mActivity = activity;
    }

    public void initStatusBar(){
        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT){
            setTranslucentStatus(true);
            SystemBarTintManager tintManager = new SystemBarTintManager(mActivity);
            tintManager.setStatusBarTintEnabled(true);
            //保证和状态栏同色
            tintManager.setStatusBarTintResource(R.color.holo_red_light);
        }
    }
    @TargetApi(19)
    private void setTranslucentStatus(boolean on) {
        Window win = mActivity.getWindow();
        WindowManager.LayoutParams winParams = win.getAttributes();
        final int bits = WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS;
        if (on) {
            winParams.flags |= bits;
        } else {
            winParams.flags &= ~bits;
        }
        win.setAttributes(winParams);
    }
}