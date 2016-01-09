package com.sizhuo.ydxf;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.sizhuo.ydxf.adapter.MyAddressListAdapter;
import com.sizhuo.ydxf.adapter.MyFragPagerAdapter;
import com.sizhuo.ydxf.entity.AddressListData;
import com.sizhuo.ydxf.fragment.AddressListFragment;
import com.sizhuo.ydxf.fragment.ForumFragment;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  便民114
 * Created by My灬xiao7
 * date: 2016/1/9
 *
 * @version 1.0
 */
public class AddressList extends AppCompatActivity{
    private Toolbar toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"热门","餐饮","娱乐","医疗","其他"};
    private MyFragPagerAdapter myFragPagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);
        initViews();
        for (int i = 0; i < 5; i++) {
            AddressListFragment addressListFragment = new AddressListFragment();
            fragments.add(addressListFragment);
        }
        myFragPagerAdapter = new MyFragPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(myFragPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myFragPagerAdapter);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.addresslist_toolbar);
        toolbar.setTitle("便民114");
        setSupportActionBar(toolbar);
        tabLayout = (TabLayout) findViewById(R.id.addresslist_tablayout);
        viewPager = (ViewPager) findViewById(R.id.addresslist_viewpager);
    }
}
