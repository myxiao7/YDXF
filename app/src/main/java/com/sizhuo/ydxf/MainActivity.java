package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.*;
import android.support.v7.widget.Toolbar;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.GridView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.daimajia.slider.library.SliderLayout;
import com.sizhuo.ydxf.adapter.MyBottomGridAdapter;
import com.sizhuo.ydxf.adapter.MyBottomGridAdapter2;
import com.sizhuo.ydxf.adapter.MyMainAdapter;
import com.sizhuo.ydxf.entity.GridBean;
import com.sizhuo.ydxf.entity.GridBean2;
import com.sizhuo.ydxf.entity.MainBean;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.NoScollerGridView;
import com.sizhuo.ydxf.view.zrclistview.SimpleHeader;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;
import com.umeng.update.UmengUpdateAgent;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 *
 *项目名称: MainActivity
 *类描述:  主菜单
 *Created by zhanghao
 *date: 2015-12-16 08:37:54
 *@version 1.0
 *
 */
public class MainActivity extends AppCompatActivity implements View.OnClickListener {
    private Toolbar toolbar;//Toolbar
    private SliderLayout mSlider;//轮播图
    private ZrcListView listView;
    private View headView, footView;
    private final int REFRESH_COMPLETE = 0X100;//刷新完成
    private final int LOADMORE_COMPLETE = 0X101;//加载完成
    private List<MainBean> list = new ArrayList<MainBean>();
    private HashMap<String,String> url_maps = new LinkedHashMap<String, String>();
    private RelativeLayout moreRe;//便民更多
    private GridView gridView;//便民服务
    private List<GridBean> gridList = new ArrayList<GridBean>();
    private RelativeLayout moreRe2;//便民114更多
    private NoScollerGridView gridView2;//便民114
    private List<GridBean2> gridList2 = new ArrayList<GridBean2>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initViews();
        initEvents();
        //前3栏目
        MainBean mainBean01 = new MainBean("1","1","1","1");
        MainBean mainBean02 = new MainBean("2","2","2","2");
        MainBean mainBean03 = new MainBean("3","3","3","3");
        MainBean mainBean04 = new MainBean("4","4","4","4");
        list.add(mainBean01);
        list.add(mainBean02);
        list.add(mainBean03);
        list.add(mainBean04);
        MyMainAdapter myMainAdapter = new MyMainAdapter(list,this);
        listView.addHeaderView(headView);
        listView.addFooterView(footView);
        listView.setAdapter(myMainAdapter);
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                Intent intent = new Intent(MainActivity.this, NewsDetails.class);
                MainActivity.this.startActivity(intent);
                Toast.makeText(MainActivity.this,"点击了"+position,Toast.LENGTH_SHORT).show();
            }
        });
        //便民服务
        for (int i = 0; i <4 ; i++) {
            GridBean gridBean = new GridBean(R.mipmap.default_img, "部门"+i);
            gridList.add(gridBean);
        }
        MyBottomGridAdapter myBottomGridAdapter = new MyBottomGridAdapter(gridList,this);
        gridView.setAdapter(myBottomGridAdapter);
        //便民114
        for (int i = 0; i <4 ; i++) {
            GridBean2 gridBean2 = new GridBean2("烟台市政府"+i);
            gridList2.add(gridBean2);
        }
        MyBottomGridAdapter2 myBottomListAdapter = new MyBottomGridAdapter2(gridList2,this);
        gridView2.setAdapter(myBottomListAdapter);
        gridView2.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
               /* Intent intent = new Intent(MainActivity.this, AddressListDetails.class);
                MainActivity.this.startActivity(intent);*/
                showDetails(gridList2.get(position).getItem());
            }
        });
    }

    /**
     * 便民114 Dialog
     */
    private void showDetails(final String phone) {
        AlertDialog alertDialog = new AlertDialog.Builder(this).create();
        alertDialog.show();
        Window window = alertDialog.getWindow();
        window.setContentView(R.layout.dialog_addresslist);
        Button callBtn= (Button) window.findViewById(R.id.dialog_addresslist_call_btn);
        callBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(MainActivity.this, "call" + phone, Toast.LENGTH_SHORT).show();
            }
        });
    }

    //初始化
    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.main_toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        //自动更新
        UmengUpdateAgent.update(this);
        listView = (ZrcListView) findViewById(R.id.main_list);
        LayoutInflater inflater = getLayoutInflater();
        headView = inflater.inflate(R.layout.main_list_header,null);
        mSlider = (SliderLayout) headView.findViewById(R.id.main_list_header_slider);
        footView = inflater.inflate(R.layout.main_bottom,null);
        moreRe = (RelativeLayout) footView.findViewById(R.id.main_bottom_re_more);
        gridView = (GridView) footView.findViewById(R.id.main_bottom_grid);
        moreRe2 = (RelativeLayout) footView.findViewById(R.id.main_bottom_re_more02);
        gridView2 = (NoScollerGridView) footView.findViewById(R.id.main_bottom_grid2);
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                Message message = handler.obtainMessage();
                message.what = REFRESH_COMPLETE;
                handler.sendMessageDelayed(message, 2500);//2.5秒后通知停止刷新
            }
        });

    }

    private void initEvents() {
        moreRe.setOnClickListener(this);
        moreRe2.setOnClickListener(this);
    }

    //toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.main_menu, menu);
        return true;
    }

    //toolbar菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.main_menu_serch:
                Toast.makeText(MainActivity.this,"搜索",Toast.LENGTH_SHORT).show();
                Intent intent = new Intent(MainActivity.this, Login.class);
                startActivity(intent);
                break;

            case R.id.main_menu_person:
                Toast.makeText(MainActivity.this,"我的",Toast.LENGTH_SHORT).show();
                Intent intent2 = new Intent(MainActivity.this, PersonCenter.class);
                startActivity(intent2);
                break;
        }
        return true;
    }


    @Override
    protected void onStop() {
        mSlider.stopAutoCycle();
        super.onStop();
    }
    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what){
                case LOADMORE_COMPLETE:
                    listView.setLoadMoreSuccess();
                    listView.stopLoadMore();
                    break;
                case REFRESH_COMPLETE:
                    listView.setRefreshSuccess("更新完成"); // 通知加载成功
                    break;
            }
        }
    };

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.main_bottom_re_more:
                Intent intent = new Intent(MainActivity.this, Organization.class);
                startActivity(intent);
                break;
            case R.id.main_bottom_re_more02:
                Intent intent2 = new Intent(MainActivity.this, AddressList.class);
                startActivity(intent2);
                break;
        }
    }

}
