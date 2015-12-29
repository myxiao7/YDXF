package com.sizhuo.ydxf;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.os.PersistableBundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.cjj.MaterialRefreshLayout;
import com.cjj.MaterialRefreshListener;
import com.sizhuo.ydxf.adapter.MyForumRecycleAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2015/12/29
 *
 * @version 1.0
 */
public class TestRecycler extends Activity {
    private MaterialRefreshLayout materialRefreshLayout;
    //    private ListView listView;
    private RecyclerView recyclerView;
    private View mFootView;
    private List<String> list = new ArrayList<>();
    private MyForumRecycleAdapter myForumRecycleAdapter;
    //    private MyForumAdapter myForumAdapter;
    private final int REFRESH_COMPLETE = 0X100;
    private final int LOADMORE_COMPLETE = 0X101;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);
        materialRefreshLayout = (MaterialRefreshLayout) findViewById(R.id.test_refresh);
        recyclerView = (RecyclerView) findViewById(R.id.test_recyclerView);
        for (int i = 'A'; i < 'z'; i++) {
            list.add(" "+(char)(i));
        }
//        myForumAdapter = new MyForumAdapter(list, getActivity());
        myForumRecycleAdapter = new MyForumRecycleAdapter(list, this);
        recyclerView.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false));
        recyclerView.setAdapter(myForumRecycleAdapter);
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
            public void onfinish() {
                super.onfinish();
            }

            @Override
            public void onRefreshLoadMore(MaterialRefreshLayout materialRefreshLayout) {
                Toast.makeText(TestRecycler.this, "load more", Toast.LENGTH_LONG).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                materialRefreshLayout
                        .autoRefreshLoadMore();
            }
        }, 1000);
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
                    materialRefreshLayout.finishRefreshLoadMore();
                    break;

                case REFRESH_COMPLETE:
                    list.clear();
                    for (int i = 0; i < 10; i++) {
                        list.add("fresh"+i);
                    }
                    myForumRecycleAdapter.notifyDataSetChanged();
                    materialRefreshLayout.finishRefresh();
                    break;
            }
        }
    };
}
