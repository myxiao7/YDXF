package com.sizhuo.ydxf.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  论坛Fragment Adapter
 * Created by My灬xiao7
 * date: 2015/12/28
 *
 * @version 1.0
 */
public class MyFragPagerAdapter extends FragmentStatePagerAdapter {
    private List<Fragment> fragments;
    private String[] titles;

    public MyFragPagerAdapter(FragmentManager fm,List<Fragment> fragments, String[] titles) {
        super(fm);
        this.titles = titles;
        this.fragments = fragments;
    }

    public MyFragPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {
        return fragments.get(position);
    }

    @Override
    public int getCount() {
        return fragments.size();
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return titles[position];
    }

}
