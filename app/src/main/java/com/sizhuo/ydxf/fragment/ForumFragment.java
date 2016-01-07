package com.sizhuo.ydxf.fragment;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.PostDetails;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.adapter.MyForumAdapter;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity.ForumData;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity.ReplyData;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.view.zrclistview.SimpleFooter;
import com.sizhuo.ydxf.view.zrclistview.SimpleHeader;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
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
    private ZrcListView listView;
    private List<PostDetailData> list = new ArrayList<>();
    private MyForumAdapter myForumAdapter;
    private final int REFRESH_COMPLETE = 0X100;//下拉刷新
    private final int LOADMORE_COMPLETE = 0X101;//上拉加载更多
    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG
    private Context context;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        if(mView == null){
            initViews(inflater, container);
        }
        queue = Volley.newRequestQueue(getActivity());
        loadData();
        myForumAdapter = new MyForumAdapter(list, getActivity());
        listView.setAdapter(myForumAdapter);
        listView.setOnRefreshStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                Message message = handler.obtainMessage();
                message.what = REFRESH_COMPLETE;
                handler.sendMessageDelayed(message, 500);//2.5秒后通知停止刷新
            }
        });
        // 加载更多事件回调（可选）
        listView.setOnLoadMoreStartListener(new ZrcListView.OnStartListener() {
            @Override
            public void onStart() {
                Message message = handler.obtainMessage();
                message.what = LOADMORE_COMPLETE;
                handler.sendMessageDelayed(message, 500);//2.5秒后通知停止刷新
            }
        });

        listView.startLoadMore(); // 开启LoadingMore功能
        listView.setOnItemClickListener(new ZrcListView.OnItemClickListener() {
            @Override
            public void onItemClick(ZrcListView parent, View view, int position, long id) {
                //获取选中帖子数据
                PostDetailData postDetailData = list.get(position);
                Intent intent = new Intent(getActivity(), PostDetails.class);
                //传递选中帖子数据
                intent.putExtra("data", postDetailData);
                context.startActivity(intent);
            }
        });
//
        return mView;
    }

    /**
     * 获取数据
     */
    private void loadData() {
        jsonObjectRequest =  new JsonObjectRequest(Const.MFORUM, null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                Log.d("xinwen", jsonObject.toString());
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    if(code == 200){
                        jsonObject.getString("data");
                        list = JSON.parseArray(jsonObject.getString("data"),PostDetailData.class);
                        Log.d("xinwen",list.get(3).getImgextra().size()+"222222222");
                        myForumAdapter.notifyDataSetChanged(list);
                        listView.setRefreshSuccess("更新完成"); // 通知加载成功
                    }else{

                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen", volleyError.getMessage());
            }
        });
        jsonObjectRequest.setTag(TAG01);
        queue.add(jsonObjectRequest);
    }

    private void initViews(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_forum,container,false);
        listView = (ZrcListView) mView.findViewById(R.id.fragment_forum_listview);
        context = container.getContext();
        initListView();

    }

    /**
     * 初始化listview的刷新样式
     */
    private void initListView() {
        // 设置下拉刷新的样式（可选，但如果没有Header则无法下拉刷新）
        SimpleHeader header = new SimpleHeader(getActivity());
        header.setTextColor(0xff0066aa);
        header.setCircleColor(0xff33bbee);
        listView.setHeadable(header);

        // 设置加载更多的样式（可选）
        SimpleFooter footer = new SimpleFooter(getActivity());
        footer.setCircleColor(0xff33bbee);
        listView.setFootable(footer);

        // 设置列表项出现动画（可选）
        listView.setItemAnimForTopIn(R.anim.topitem_in);
        listView.setItemAnimForBottomIn(R.anim.bottomitem_in);
        listView.setFootable(footer);
    }

    Handler handler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what){
                case LOADMORE_COMPLETE:

                    myForumAdapter.notifyDataSetChanged();
                    listView.setLoadMoreSuccess();


                    break;

                case REFRESH_COMPLETE:
                    list.clear();
                    loadData();
                    break;
            }
        }
    };
}
