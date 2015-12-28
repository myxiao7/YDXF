package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sizhuo.ydxf.adapter.MyFragPagerAdapter;
import com.sizhuo.ydxf.fragment.ForumFragment;
import com.sizhuo.ydxf.util.StatusBar;

import java.util.ArrayList;
import java.util.List;

/**
 *
 *项目名称: Forum
 *类描述:  论坛
 *Created by zhanghao
 *date: 2015-12-16 08:37:54
 *@version 1.0
 *
 */

public class Forum extends AppCompatActivity {
    private Toolbar toolbar;//标题栏
    private TabLayout tabLayout;//选项卡
    private ViewPager viewPager;//滑动
    private FloatingActionButton fab;//刷新
    private MyFragPagerAdapter myFragPagerAdapter;
    private List<Fragment> fragments = new ArrayList<Fragment>();
    private String[] titles = {"看帖","热点","精品","其他"};
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        initViews();
        for (int i = 0; i < 4; i++) {
            ForumFragment forumFragment = new ForumFragment();
            fragments.add(forumFragment);
        }
        myFragPagerAdapter = new MyFragPagerAdapter(getSupportFragmentManager(), fragments);
        viewPager.setAdapter(myFragPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myFragPagerAdapter);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.forum_toolbar);
        toolbar.setTitle("论坛");
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.forum_tablayout);
        viewPager = (ViewPager) findViewById(R.id.forum_viewpager);
        fab = (FloatingActionButton) findViewById(R.id.fab);
    }

}
