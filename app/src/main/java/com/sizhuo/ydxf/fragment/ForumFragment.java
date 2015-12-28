package com.sizhuo.ydxf.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sizhuo.ydxf.R;
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
    private VRefresh vRefresh;
    private RecyclerView recyclerView;
    private List<String> list = new ArrayList<>();
    private MyForumRecycleAdapter myForumRecycleAdapter;
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

        vRefresh.setView(getActivity(), recyclerView);//设置嵌套的子view -listview
        vRefresh.setMoreData(true);//设置是否还有数据可加载(一般根据服务器反回来决定)
        recyclerView.setLayoutManager(new LinearLayoutManager(getActivity(), LinearLayoutManager.VERTICAL, false));
        myForumRecycleAdapter = new MyForumRecycleAdapter(list, getActivity());
        recyclerView.setAdapter(myForumRecycleAdapter);
                vRefresh.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
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
        });
        return mView;
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_forum,container,false);
        recyclerView = (RecyclerView) mView.findViewById(R.id.fragment_forum_recycleviewr);
        vRefresh = (VRefresh)mView.findViewById(R.id.fragment_forum_vrefresh);
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
                    myForumRecycleAdapter.notifyDataSetChanged();
                    vRefresh.setMoreData(true);//设置还有数据可以加载
                    vRefresh.setLoading(false);//停止加载更多
                    break;

                case REFRESH_COMPLETE:
                    list.clear();
                    for (int i = 'A'; i < 'A'; i--) {
                        list.add(" "+(char)(i));
                    }
                    myForumRecycleAdapter.notifyDataSetChanged();
                    vRefresh.setMoreData(true);//设置还有数据可以加载
                    vRefresh.setLoading(false);//停止刷新
                    vRefresh.setRefreshing(false);//让刷新消失
                    break;
            }
        }
    };
}
