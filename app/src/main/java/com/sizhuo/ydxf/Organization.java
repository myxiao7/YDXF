package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;

import com.sizhuo.ydxf.adapter.MyOrgGridAdapter;
import com.sizhuo.ydxf.entity.GridBean;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.TypeTextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  组织机构
 * Created by My灬xiao7
 * date: 2016/1/11
 *
 * @version 1.0
 */
public class Organization extends AppCompatActivity{
    private Toolbar toolbar;
    private GridView gridView;//便民服务
    private List<GridBean> gridList = new ArrayList<GridBean>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_org);
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.org_toolbar);
        toolbar.setTitle("组织机构");
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.org_grid);
        //便民服务
        for (int i = 0; i <9 ; i++) {
            GridBean gridBean = new GridBean("http://192.168.1.114:8080/xinwen/img/item01.jpg", "部门"+i);
            gridList.add(gridBean);
        }
        MyOrgGridAdapter myBottomGridAdapter = new MyOrgGridAdapter(gridList,this);
        gridView.setAdapter(myBottomGridAdapter);
        gridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(Organization.this, OrgDetails.class);
                Organization.this.startActivity(intent);
            }
        });
    }
}
