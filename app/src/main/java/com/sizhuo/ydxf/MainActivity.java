package com.sizhuo.ydxf;

import android.app.Activity;
import android.net.wifi.WifiConfiguration;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

import com.sizhuo.ydxf.util.StatusBar;

/**
 *
 *项目名称: MainActivity
 *类描述:  主菜单
 *Created by zhanghao
 *date: 2015-12-16 08:37:54
 *@version 1.0
 *
 */
public class MainActivity extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_serch:
                Toast.makeText(MainActivity.this,"搜索",Toast.LENGTH_SHORT).show();
                break;

            case R.id.main_menu_person:
                Toast.makeText(MainActivity.this,"我的",Toast.LENGTH_SHORT).show();
                break;
        }
        return true;
    }
}
