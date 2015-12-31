package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.widget.BaseAdapter;
import android.widget.GridView;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2015/12/31
 *
 * @version 1.0
 */
public class SelectPhoto extends AppCompatActivity{
    private GridView gridView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        gridView = (GridView) findViewById(R.id.selectphoto_gridview);

    }


}
