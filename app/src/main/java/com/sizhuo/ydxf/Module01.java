package com.sizhuo.ydxf;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.widget.ListView;

import com.sizhuo.ydxf.adapter.MyModule01Adapter;
import com.sizhuo.ydxf.bean.NewsBean;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.VRefresh;

import java.util.LinkedList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  第一个功能模块
 * Created by My灬xiao7
 * date: 2015/12/18
 *
 * @version 1.0
 */
public class Module01 extends AppCompatActivity{
    private Toolbar toolbar;//标题栏
    private VRefresh vRefresh;
    private ListView listView;
    private List<NewsBean> list = new LinkedList<NewsBean>();
    private MyModule01Adapter myModule01Adapter;
    private final int REFRESH_COMPLETE = 0X100;//刷新完成
    private final int LOADMORE_COMPLETE = 0X101;//加载完成
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_module01);
        //初始化
        initViews();
        //前3栏目
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.module01_toolbar);
        toolbar.setTitle("大事小情");
        setSupportActionBar(toolbar);
        for (int i = 0; i <10 ; i++) {
            NewsBean newsBean = new NewsBean("1","2","3","新闻标题","新闻简介","2015-12-22","1","");
            list.add(newsBean);
        };
        for (int i = 0; i <1 ; i++) {
            NewsBean newsBean = new NewsBean("1","2","3","新闻标题3图","新闻简介","2015-12-22","2","");
            list.add(newsBean);

        };
        for (int i = 0; i <2 ; i++) {
            NewsBean newsBean = new NewsBean("1","2","3","新闻标题无图","新闻简介","2015-12-22","3","");
            list.add(newsBean);
        };
        myModule01Adapter = new MyModule01Adapter(list, this);
        listView = (ListView) findViewById(R.id.module01_list);
        vRefresh = (VRefresh)findViewById(R.id.module01_vrefresh);
        vRefresh.setView(this, listView);//设置嵌套的子view -listview
        vRefresh.setMoreData(true);//设置是否还有数据可加载(一般根据服务器反回来决定)
        listView.setAdapter(myModule01Adapter);
//        vRefresh.autoRefresh();//自动刷新一次
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

    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOADMORE_COMPLETE:
                    for (int i = 0; i <2 ; i++) {
                        NewsBean newsBean = new NewsBean("1","2","3","新添加的新闻标题","新闻简介","2015-12-22","1","");
                        list.add(newsBean);
                    };
                    myModule01Adapter.notifyDataSetChanged();
                    vRefresh.setMoreData(true);//设置还有数据可以加载
                    vRefresh.setLoading(false);//停止加载更多
                    break;
                case REFRESH_COMPLETE:
                    list.clear();
                    for (int i = 0; i <10 ; i++) {
                        NewsBean newsBean = new NewsBean("1","2","3","更新后新闻标题","新闻简介","2015-12-22","1","");
                        list.add(newsBean);
                    };
                    for (int i = 0; i <1 ; i++) {
                        NewsBean newsBean = new NewsBean("1","2","3","更新后新闻标题3图","新闻简介","2015-12-22","2","");
                        list.add(newsBean);

                    };
                    for (int i = 0; i <2 ; i++) {
                        NewsBean newsBean = new NewsBean("1","2","3","更新后新闻标题无图","新闻简介","2015-12-22","3","");
                        list.add(newsBean);
                    };
                    vRefresh.setMoreData(true);//设置还有数据可以加载
                    myModule01Adapter.notifyDataSetChanged();
                    vRefresh.setLoading(false);//停止刷新
                    vRefresh.setRefreshing(false);//让刷新消失
                    break;
            }
        }
    };
}
