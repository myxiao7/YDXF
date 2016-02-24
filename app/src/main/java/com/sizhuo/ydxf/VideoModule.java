package com.sizhuo.ydxf;

import android.app.AlertDialog;
import android.content.ComponentName;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.provider.Settings;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.entity.VideoList;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.util.ImageLoaderHelper;
import com.sizhuo.ydxf.util.NetworkCheck;
import com.sizhuo.ydxf.util.StatusBar;
import com.sizhuo.ydxf.view.zrclistview.SimpleFooter;
import com.sizhuo.ydxf.view.zrclistview.SimpleHeader;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  电视栏目
 * Created by My灬xiao7
 * date: 2015/12/25
 *
 * @version 1.0
 */
public class VideoModule extends AppCompatActivity{
    private Toolbar toolbar;
    private ZrcListView listView;
    private int index = 1;
    private List<VideoList> list=new ArrayList<VideoList>();
    private MyVideoAdapter adapter;

    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG
    private LinearLayout loading;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_videomodule);
        initViews();
        queue = Volley.newRequestQueue(this);
        adapter = new MyVideoAdapter(list);
        listView.setAdapter(adapter);
        loadData();
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                loadData();
            }
        });
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                index++;
                loadMoreData(index);
            }
        });
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, final int position, long id) {
                //wifi下自动播放
                if(new NetworkCheck(VideoModule.this).state().equals(NetworkCheck.NetState.NET_WIFI)) {
                    Log.d("log.d", "WiFi");
                    Intent intent = new Intent(VideoModule.this, VideoDetails.class);
//                Toast.makeText(Module01.this, "" + position + "----" + list.get(position - 1).getDigest(), Toast.LENGTH_SHORT).show();
                    intent.putExtra("data", list.get(position));
                    startActivity(intent);
                }else if(new NetworkCheck(VideoModule.this).state().equals(NetworkCheck.NetState.NET_3G)){
                    Log.d("log.d", "3G");
                    new AlertDialog.Builder(VideoModule.this).setMessage("你未连接WIFI，是否要播放视频？").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("播放", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            Intent intent = new Intent(VideoModule.this, VideoDetails.class);
//                Toast.makeText(Module01.this, "" + position + "----" + list.get(position - 1).getDigest(), Toast.LENGTH_SHORT).show();
                            intent.putExtra("data", list.get(position));
                            startActivity(intent);
                        }
                    }).create().show();
                }else{
                    new AlertDialog.Builder(VideoModule.this).setMessage("网络连接没有设置").setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    }).setPositiveButton("设置", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                           /* Intent intent = new Intent("/");
                            ComponentName cm = new ComponentName("com.android.settings","com.android.settings.WirelessSettings");
                            intent.setComponent(cm);
                            intent.setAction("android.intent.action.VIEW");
                            startActivityForResult(intent, 0);*/
                            startActivity(new Intent(Settings.ACTION_WIFI_SETTINGS));
                        }
                    }).create().show();
                }
            }
        });
    }

    private void loadData() {
        jsonObjectRequest = new JsonObjectRequest(Const.VIDEO + 1, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        //获取数据成功
                        Log.d("xinwen", "data:------" + jsonObject.getString("data"));
                        //获取data所有数据
                        list = JSON.parseArray(jsonObject.getString("data"),VideoList.class);
                        adapter.notifyDataSetChanged(list);
                        listView.setRefreshSuccess("更新完成"); // 通知加载成功
                        if(list.size()==20){
                            listView.startLoadMore();
                        }
                        index = 1;
                    }else if(code == 400){
                        listView.setRefreshFail("没有数据");
                        Toast.makeText(VideoModule.this, "没有数据", Toast.LENGTH_SHORT).show();
                    }else{
                        listView.setRefreshFail("加载错误");
                        Toast.makeText(VideoModule.this,"加载错误",Toast.LENGTH_SHORT).show();
                    }
                    new Handler().postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            loading.setVisibility(View.GONE);
                        }
                    },800);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen",volleyError.toString()+volleyError);
                listView.setRefreshFail("网络不给力");
                loading.setVisibility(View.GONE);
                Toast.makeText(VideoModule.this, "网络不给力",Toast.LENGTH_SHORT).show();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 500);
    }

    /**
     * 获取更多数据
     */
    private void loadMoreData(int index) {
        jsonObjectRequest = new JsonObjectRequest(Const.VIDEO + index, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("xinwen", jsonObject.toString()+"");
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        //获取数据成功
                        Log.d("xinwen", "data:------" + jsonObject.getString("data"));
                        //获取data所有数据
                        com.alibaba.fastjson.JSONObject data = JSON.parseObject(jsonObject.getString("data"));
                        List<VideoList> list2=new ArrayList<VideoList>();
                        for (VideoList l: list2) {
                                list.add(l);
                        }
                        adapter.notifyDataSetChanged(list);
                        listView.setLoadMoreSuccess();
                    }else if(code == 400){
                        Toast.makeText(VideoModule.this,"没有更多了",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }else{
                        Toast.makeText(VideoModule.this,"加载错误",Toast.LENGTH_SHORT).show();
                        listView.stopLoadMore();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen",volleyError.toString()+volleyError);
                Toast.makeText(VideoModule.this,"网络不给力",Toast.LENGTH_SHORT).show();
                listView.stopLoadMore();
            }
        });
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 1000);
    }

    private void initViews() {
        new StatusBar(this).initStatusBar();
        toolbar = (Toolbar) findViewById(R.id.videomodule_toolbar);
        toolbar.setTitle("党建电视节目");
        setSupportActionBar(toolbar);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                VideoModule.this.finish();
            }
        });
        listView = (ZrcListView) findViewById(R.id.videomodule_list);
        loading = (LinearLayout) findViewById(R.id.videomodule_loading);
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(this);
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(this);
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

        /*// 设置列表项出现动画（可选）
        listView.setItemAnimForTopIn(R.anim.topitem_in);
        listView.setItemAnimForBottomIn(R.anim.bottomitem_in);
        listView.setFootable(footer);*/
    }

    class MyVideoAdapter extends BaseAdapter{
        private List<VideoList> list;

        public MyVideoAdapter(List<VideoList> list) {
            this.list = list;
        }

        @Override
        public int getCount() {
            return list.size();
        }

        @Override
        public Object getItem(int position) {
            return list.get(position);
        }

        @Override
        public long getItemId(int position) {
            return position;
        }

        @Override
        public View getView(int position, View convertView, ViewGroup parent) {
            ViewHolder holder;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.videomodule_list_item, parent, false);
                holder = new ViewHolder();
                holder.imageView = (ImageView) convertView.findViewById(R.id.videomodule_list_item_img);
                holder.titleTv = (TextView) convertView.findViewById(R.id.videomodule_list_item_title_tv);
                holder.desTv = (TextView) convertView.findViewById(R.id.videomodule_list_item_des_tv);
                holder.dataTv = (TextView) convertView.findViewById(R.id.videomodule_list_item_date_tv);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            VideoList videoList = list.get(position);
            if(!TextUtils.isEmpty(videoList.getPicture())){
            ImageLoaderHelper.getIstance().loadImg(videoList.getPicture(),holder.imageView);
            }
            holder.titleTv.setText(videoList.getTitle());
            holder.desTv.setText(videoList.getSynopsis());
            holder.dataTv.setText(videoList.getpTime());
            return convertView;
        }

        public void notifyDataSetChanged(List<VideoList> list) {
            this.list = list;
            notifyDataSetChanged();
        }

        class ViewHolder{
            ImageView imageView;
            TextView titleTv;
            TextView desTv;
            TextView dataTv;
        }
    }
}
