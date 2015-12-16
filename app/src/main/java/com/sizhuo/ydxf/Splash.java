package com.sizhuo.ydxf;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;

/**
 *
 *项目名称: Splash
 *类描述:  闪屏
 *Created by zhanghao
 *date: 2015-12-16 08:37:54
 *@version 1.0
 *
 */
public class Splash extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent intent = new Intent(Splash.this, MainActivity.class);
                Splash.this.startActivity(intent);
                Splash.this.finish();
            }
        },100);
    }

}
