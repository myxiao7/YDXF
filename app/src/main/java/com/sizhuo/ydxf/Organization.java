package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.GridView;

import com.sizhuo.ydxf.adapter.MyOrgGridAdapter;
import com.sizhuo.ydxf.entity.GridBean;
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
    private TypeTextView mTypeTextView;
    private List<GridBean> gridList = new ArrayList<GridBean>();
    private static final String TEST_DATA = "       市委组织部是各级市委主管组织、干部工作的职能部门。其主要任务是：坚决贯彻执行党的组织路线和各项方针政策，围绕党在新时期的总路线，抓好组织工作的各项改革，按照干部\"四化\"方针，加强领导班子的组织、思想、作风建设和干部队伍的建设，发展党的组织，抓好党组织和党员的管理，发展党员教育，提高党组织的战斗力，为全市的经济建设服务。所谓所有者，就是股东，而经营者就是企业各级管理人员。所谓所有者，就是股东，而经营者就是企业各级管理人员。所谓所有者，就是股东，而经营者就是企业各级管理人员。";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.layout_org);
        toolbar = (Toolbar) findViewById(R.id.org_toolbar);
        toolbar.setTitle("组织机构");
        setSupportActionBar(toolbar);
        gridView = (GridView) findViewById(R.id.org_grid);
        //便民服务
        for (int i = 0; i <9 ; i++) {
            GridBean gridBean = new GridBean(R.mipmap.default_img, "部门"+i);
            gridList.add(gridBean);
        }
        MyOrgGridAdapter myBottomGridAdapter = new MyOrgGridAdapter(gridList,this);
        gridView.setAdapter(myBottomGridAdapter);
        mTypeTextView = ( TypeTextView )findViewById(R.id.org_des_tv);
        mTypeTextView.setOnTypeViewListener(new TypeTextView.OnTypeViewListener() {
            @Override
            public void onTypeStart() {

            }

            @Override
            public void onTypeOver() {

            }
        });
        mTypeTextView.start(TEST_DATA);
    }
}
