package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.Toast;

/**
 * 项目名称: YDXF
 * 类描述:  发布帖子
 * Created by My灬xiao7
 * date: 2015/12/31
 *
 * @version 1.0
 */
public class Publish extends AppCompatActivity{
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_publish);
        toolbar = (Toolbar) findViewById(R.id.publish_toolbar);
        toolbar.setTitle("发表帖子");
        setSupportActionBar(toolbar);

    }

}
