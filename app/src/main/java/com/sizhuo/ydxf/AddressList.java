package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.sizhuo.ydxf.adapter.MyAddressListAdapter;
import com.sizhuo.ydxf.entity.AddressListData;
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
    private ZrcListView listView;
    private List<AddressListData> list = new ArrayList<>();
    private MyAddressListAdapter myAddressListAdapter;
   /* private TabLayout tabLayout;
    private ViewPager viewPager;
    private List<Fragment> fragments = new ArrayList<>();
    private String[] titles = {"热门","餐饮","娱乐","医疗","其他"};
    private MyFragPagerAdapter myFragPagerAdapter;*/

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_addresslist);
        initViews();
        AddressListData data = new AddressListData("","烟台高新区银行","0535-5165161","烟台高新区创业路36号");
        AddressListData data2 = new AddressListData("","烟台莱山区酒店","0535-213123231","烟台高新区创业路36号");
        AddressListData data3 = new AddressListData("","烟台高新区政府","0535-42524342","烟台高新区创业路36号");
        list.add(data);
        list.add(data2);
        list.add(data3);
        myAddressListAdapter = new MyAddressListAdapter(list,this);
        listView.setAdapter(myAddressListAdapter);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(AddressList.this, AddressListDetails.class);
                AddressList.this.startActivity(intent);
            }
        });
       /* for (int i = 0; i < 5; i++) {
            AddressListFragment addressListFragment = new AddressListFragment();
            fragments.add(addressListFragment);
        }
        myFragPagerAdapter = new MyFragPagerAdapter(getSupportFragmentManager(), fragments, titles);
        viewPager.setAdapter(myFragPagerAdapter);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.setTabsFromPagerAdapter(myFragPagerAdapter);*/
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.addresslist_toolbar);
        toolbar.setTitle("便民114");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.addresslist_listview);
       /* tabLayout = (TabLayout) findViewById(R.id.addresslist_tablayout);
        viewPager = (ViewPager) findViewById(R.id.addresslist_viewpager);*/
    }
}
