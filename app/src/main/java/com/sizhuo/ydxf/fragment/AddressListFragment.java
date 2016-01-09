package com.sizhuo.ydxf.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.adapter.MyAddressListAdapter;
import com.sizhuo.ydxf.entity.AddressListData;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  便民114fragment
 * Created by My灬xiao7
 * date: 2016/1/9
 *
 * @version 1.0
 */
public class AddressListFragment extends Fragment{
    private ZrcListView zrcListView;
    private List<AddressListData> list = new ArrayList<>();
    private MyAddressListAdapter myAddressListAdapter;
    private View mView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView == null){
            initViews(inflater, container);
        }
        AddressListData data = new AddressListData("","烟台高新区银行","0535-5165161","高新区","银行","烟台高新区创业路36号");
        AddressListData data2 = new AddressListData("","烟台莱山区酒店","0535-213123231","莱山区","餐饮","烟台高新区创业路36号");
        AddressListData data3 = new AddressListData("","烟台高新区政府","0535-42524342","高新区","政务","烟台高新区创业路36号");
        list.add(data);
        list.add(data2);
        list.add(data3);
        myAddressListAdapter = new MyAddressListAdapter(list,getActivity());
        zrcListView.setAdapter(myAddressListAdapter);
        return mView;
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_addresslist, container, false);
        zrcListView = (ZrcListView) mView.findViewById(R.id.fragment_addresslist_listview);

    }
}
