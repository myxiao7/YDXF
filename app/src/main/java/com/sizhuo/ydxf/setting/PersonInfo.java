package com.sizhuo.ydxf.setting;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.util.StatusBar;

/**
 * 项目名称: YDXF
 * 类描述:  个人资料（修改）
 * Created by My灬xiao7
 * date: 2016/1/13
 *
 * @version 1.0
 */
public class PersonInfo extends AppCompatActivity {
    private Toolbar toolbar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting_personinfo);
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.set_personinfo_toolbar);
        toolbar.setTitle("个人资料");
        setSupportActionBar(toolbar);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.personinfo_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.perinfo_menu_save:
                break;
        }
        return true;
    }
}
