package com.sizhuo.ydxf;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.adapter.MyForumAdapter;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._PostDetailData;
import com.sizhuo.ydxf.entity._ReplyData;
import com.sizhuo.ydxf.entity._SliderData;
import com.sizhuo.ydxf.entity.db.User;
import com.sizhuo.ydxf.entity.imgextra;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.SimpleFooter;
import com.sizhuo.ydxf.view.zrclistview.SimpleHeader;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

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
    private ZrcListView listView;
    private List<_PostDetailData> list = new ArrayList<>();
    private MyForumAdapter myForumAdapter;
    private final int REFRESH_COMPLETE = 0X100;//下拉刷新
    private final int LOADMORE_COMPLETE = 0X101;//上拉加载更多
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG

    private DbManager dbManager;//数据库操作
    private User user;
    private Boolean loginFlag = false;
    private int index = 1;

    @Override
    protected void onResume() {
        super.onResume();
        //检查登录状态
        try {
            user = dbManager.findFirst(User.class);
            if(user!=null){
                loginFlag = true;
//                Toast.makeText(Forum.this,"登录"+user.getNickName(),Toast.LENGTH_SHORT).show();
            }else{
                loginFlag = false;
//                Toast.makeText(Forum.this,"没有登录",Toast.LENGTH_SHORT).show();
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forum);
        initViews();
        queue = Volley.newRequestQueue(this);
        dbManager = new MyApplication().getDbManager();
        //加载缓存
        try {
            if(dbManager.selector(_PostDetailData.class).where("moduleType","=","f01").findAll()!=null) {
                if (dbManager.selector(_PostDetailData.class).where("moduleType", "=", "f01").findAll().size() > 0) {
                    list = dbManager.selector(_PostDetailData.class).where("moduleType", "=", "f01").findAll();
                    for (int i = 0; i < list.size(); i++) {
                        //通过外键查找图片和回复缓存
                        List<imgextra> imgextras = dbManager.selector(imgextra.class).where("parentId","=",list.get(i).getId()).findAll();
                        List<_ReplyData> replyDatas = dbManager.selector(_ReplyData.class).where("parentId","=",list.get(i).getId()).findAll();
                        list.get(i).setImgextra(imgextras);
                        list.get(i).setReply(replyDatas);
                    }
//                    Toast.makeText(Forum.this, "加载了" + list.size() + "条缓存", Toast.LENGTH_SHORT).show();
                } else {
//                    Toast.makeText(Forum.this, "没有缓存数据", Toast.LENGTH_SHORT).show();
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }
        loadData();
//        loadData();

        myForumAdapter = new MyForumAdapter(list, this);
        listView.setAdapter(myForumAdapter);
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadData();
            }
        });

        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                index++;
                loadMoreData(index);

            }
        });


        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                //获取选中帖子数据
                _PostDetailData postDetailData = list.get(position);
                Intent intent = new Intent(Forum.this, PostDetails.class);
                //传递选中帖子数据
                intent.putExtra("data", postDetailData);
                Forum.this.startActivity(intent);
            }
        });
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.forum_toolbar);
        toolbar.setTitle("我有好点子");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Forum.this.finish();
            }
        });
        listView = (ZrcListView)findViewById(R.id.forum_listview);
        initListView();

    }

    /**
     * 获取数据
     */
    private void loadData() {
        jsonObjectRequest =  new JsonObjectRequest(Const.MFORUM + 1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("xinwen", jsonObject.toString());
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        jsonObject.getString("data");
                        list = JSON.parseArray(jsonObject.getString("data"), _PostDetailData.class);
                        //先清除缓存数据
                            dbManager.delete(_PostDetailData.class, WhereBuilder.b("moduleType", "=", "f01"));
                            dbManager.delete(imgextra.class, WhereBuilder.b("moduleType", "=", "f01"));
                            dbManager.delete(_ReplyData.class, WhereBuilder.b("moduleType", "=", "f01"));
                        //缓存
                            for (_PostDetailData postDetailData:list) {
                                postDetailData.setModuleType("f01");
                                //图片URL
                                List<imgextra> imgextras = postDetailData.getImgextra();
                                //回复
                                List<_ReplyData> replyDatas = postDetailData.getReply();
                                dbManager.save(postDetailData);
                                //关联存储URL
                                for (imgextra img:imgextras) {
                                    img.setParentId(postDetailData.getId());
                                    img.setModuleType("f01");
                                    dbManager.saveBindingId(img);
                                }
                                //关联存储恢复数据
                                for (_ReplyData replyData:replyDatas) {
                                    replyData.setParentId(postDetailData.getId());
                                    replyData.setModuleType("f01");
                                    dbManager.saveBindingId(replyData);
                                }

                            }
                        myForumAdapter.notifyDataSetChanged(list);
                        listView.setRefreshSuccess("更新完成"); // 通知加载成功
                        if(list.size()==20){
                            listView.startLoadMore();
                        }
                    }else if(code == 400){
                        listView.setRefreshFail("没有数据");
                        Toast.makeText(Forum.this,"没有数据",Toast.LENGTH_SHORT).show();
                    }else{
                        listView.setRefreshFail("加载错误");
                        Toast.makeText(Forum.this,"加载错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }catch (DbException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen", volleyError.getMessage());
                listView.setRefreshFail("网络异常");
                Toast.makeText(Forum.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 800);
    }

    /**
     * 获取更多数据
     */
    private void loadMoreData(int index) {
        jsonObjectRequest =  new JsonObjectRequest(Const.MFORUM + index, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("xinwen", jsonObject.toString());
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        jsonObject.getString("data");

                        List<_PostDetailData> list2 = JSON.parseArray(jsonObject.getString("data"), _PostDetailData.class);
                        //缓存
                        for (_PostDetailData postDetailData:list2) {
                            list.add(postDetailData);
                            //缓存
                            postDetailData.setModuleType("f01");
                            //图片URL
                            List<imgextra> imgextras = postDetailData.getImgextra();
                            //回复
                            List<_ReplyData> replyDatas = postDetailData.getReply();
                            dbManager.save(postDetailData);
                            //关联存储URL
                            for (imgextra img:imgextras) {
                                img.setParentId(postDetailData.getId());
                                img.setModuleType("f01");
                                dbManager.saveBindingId(img);
                            }
                            //关联存储恢复数据
                            for (_ReplyData replyData:replyDatas) {
                                replyData.setParentId(postDetailData.getId());
                                replyData.setModuleType("f01");
                                dbManager.saveBindingId(replyData);
                            }
                        }
                        myForumAdapter.notifyDataSetChanged(list);
                        listView.setLoadMoreSuccess();
                    }else if(code == 400){
                        Toast.makeText(Forum.this,"没有更多了",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }else{
                        Toast.makeText(Forum.this,"加载错误",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                } catch (DbException e){
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen", volleyError.getMessage());
                listView.stopLoadMore();
                Toast.makeText(Forum.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 1200);
    }
    /**
     * 初始化listview的刷新样式
     */
    private void initListView() {
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

        // 设置列表项出现动画（可选）
        listView.setItemAnimForTopIn(R.anim.topitem_in);
        listView.setItemAnimForBottomIn(R.anim.bottomitem_in);
        listView.setFootable(footer);
    }


    //toolbar菜单
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.forum_menu, menu);
        return true;
    }

    //toolbar菜单点击事件
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()){
            case R.id.forum_menu_item01:
                if(loginFlag==true){
//                    Toast.makeText(Forum.this,"发帖",Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(Forum.this, Publish.class);
                    this.startActivity(intent);
                }else{
                    Intent intent = new Intent(Forum.this, Login.class);
                    Forum.this.startActivity(intent);
                }

                break;
        }
        return true;
    }

}
