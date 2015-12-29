package com.sizhuo.ydxf.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.adapter.MyForumAdapter;
import com.sizhuo.ydxf.adapter.MyForumRecycleAdapter;
import com.sizhuo.ydxf.view.VRefresh;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2015/12/28
 *
 * @version 1.0
 */
public class ForumFragment extends Fragment {
    private View mView;
    private MaterialRefreshLayout materialRefreshLayout;
    private ListView listView;
//    private RecyclerView recyclerView;
    private View mFootView;
    private List<String> list = new ArrayList<>();
//    private MyForumRecycleAdapter myForumRecycleAdapter;
    private MyForumAdapter myForumAdapter;
    private final int REFRESH_COMPLETE = 0X100;
    private final int LOADMORE_COMPLETE = 0X101;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView == null){
            initViews(inflater, container);
        }
        for (int i = 'A'; i < 'z'; i++) {
            list.add(" "+(char)(i));
        }
        myForumAdapter = new MyForumAdapter(list, getActivity());
//        myForumRecycleAdapter = new MyForumRecycleAdapter(list, getActivity());
//        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        listView.setAdapter(myForumAdapter);
        materialRefreshLayout.setLoadMore(true);
        materialRefreshLayout.setMaterialRefreshListener(new MaterialRefreshListener() {
            @Override
            public void onRefresh(MaterialRefreshLayout materialRefreshLayout) {
                Log.d("xinwen", "onRefresh");
                Message message = handler.obtainMessage();
                message.what = REFRESH_COMPLETE;
                handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                super.onRefreshLoadMore(materialRefreshLayout);
                Log.d("xinwen", "onRefreshLoadMore");
                Message message = handler.obtainMessage();
                message.what = LOADMORE_COMPLETE;
                handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新
            }
        });
      /*  vRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
                        Message message = handler.obtainMessage();
                        message.what = REFRESH_COMPLETE;
                        handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新
                    }
                });
        vRefresh.setOnLoadListener(new VRefresh.OnLoadListener() {
            @Override
            public void onLoadMore() {
                Message message = handler.obtainMessage();
                message.what = LOADMORE_COMPLETE;
                handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新
            }
        });*/
        return mView;
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_forum,container,false);
//        listView = (ListView) mView.findViewById(R.id.fragment_forum_listview);
        listView = (ListView) mView.findViewById(R.id.fragment_forum_listview);
        materialRefreshLayout = (MaterialRefreshLayout) mView.findViewById(R.id.fragment_forum_refresh);
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOADMORE_COMPLETE:
                    for (int i = 0; i <5 ; i++) {
                        list.add("add"+i);
                    }
                    myForumAdapter.notifyDataSetChanged();
                    materialRefreshLayout.finishRefreshLoadMore();
                    break;

                case REFRESH_COMPLETE:
                    list.clear();
                    for (int i = 0; i < 10; i++) {
                        list.add("fresh"+i);
                    }
                    myForumAdapter.notifyDataSetChanged();
                    materialRefreshLayout.finishRefresh();
                    break;
            }
        }
    };
}
