package com.sizhuo.ydxf;

import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.TextUtils;
import android.text.style.ForegroundColorSpan;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.SimpleAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.alibaba.fastjson.JSON;
import com.android.volley.AuthFailureError;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.sizhuo.ydxf.application.MyApplication;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._PostDetailData;
import com.sizhuo.ydxf.entity._ReplyData;
import com.sizhuo.ydxf.entity.db.SearchKey;
import com.sizhuo.ydxf.entity.imgextra;
import com.sizhuo.ydxf.util.Const;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import org.json.JSONException;
import org.json.JSONObject;
import org.xutils.DbManager;
import org.xutils.db.sqlite.WhereBuilder;
import org.xutils.ex.DbException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: YDXF
 * 类描述:  搜素
 * Created by My灬xiao7
 * date: 2016/1/18
 *
 * @version 1.0
 */
public class Search extends AppCompatActivity{
    private Toolbar toolbar;//标题栏
    private LinearLayout hisLin;
    private ListView hisListView;//搜索历史
    private View hisListViewFooter;//清除历史
    //搜索历史
    List<SearchKey> searchKeys = new ArrayList<>();
    private List<Map<String,String>> data = new ArrayList<>();
    private Map<String,String> map;
    private ZrcListView listView;
    private MySearchAdapter adapter;
    private List<_NewsData> list = new ArrayList<>();

    private EditText contentEdit;

    private DbManager dbManager;//数据库操作

    private RequestQueue queue;
    private JsonObjectRequest jsonObjectRequest;
    private final String TAG01 = "jsonObjectRequest";//请求数据TAG

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        initViews();
        queue = Volley.newRequestQueue(this);
        dbManager = new MyApplication().getDbManager();

        try {
            //搜索历史
            searchKeys = dbManager.selector(SearchKey.class).findAll();
            if(searchKeys!=null){
                hisLin.setVisibility(View.VISIBLE);
                for (SearchKey searchKey:searchKeys) {
                    map = new HashMap<>();
                    map.put("str",searchKey.getStr());
                    data.add(map);
                }
                hisListView.setAdapter(new SimpleAdapter(Search.this,data,R.layout.search_his_list_item,new String[]{"str"},new int[]{R.id.search_his_list_item_tv}));
                if(searchKeys.size()>0){
                    hisListView.addFooterView(hisListViewFooter);
                }
            }
        } catch (DbException e) {
            e.printStackTrace();
        }

        hisListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position==data.size()){
                    Toast.makeText(Search.this,"清除",Toast.LENGTH_SHORT);
                    try {
                        dbManager.delete(SearchKey.class);
                    } catch (DbException e) {
                        e.printStackTrace();
                    }
                    hisLin.setVisibility(View.GONE);
                    hisListView.setVisibility(View.GONE);
                }else{
                    contentEdit.setText(searchKeys.get(position).getStr());
                }

            }
        });

        adapter = new MySearchAdapter(list,"");
        listView.setAdapter(adapter);

        contentEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    if(!TextUtils.isEmpty(contentEdit.getText().toString().trim())){
//                        Toast.makeText(Search.this,"搜索",Toast.LENGTH_SHORT).show();
                        hisLin.setVisibility(View.GONE);
                        listView.setVisibility(View.VISIBLE);
                        try {
                            dbManager.save(new SearchKey(contentEdit.getText().toString().trim()));
                        } catch (DbException e) {
                            e.printStackTrace();
                        }
                        loadData(contentEdit.getText().toString().trim());
                        return true;
                    }

                }
                return false;
            }
        });


    }

    /**
     * 获取数据
     */
    private void loadData(final String str) {
        Log.d("xinwen", Const.SEARCH +str+"-----------" );
        jsonObjectRequest =  new JsonObjectRequest(Request.Method.GET, Const.SEARCH + str.toString(), null, new Response.Listener<JSONObject>() {
            @Override
            public void onResponse(JSONObject jsonObject) {
                try {
                    //获取服务器code
                    int code = jsonObject.getInt("code");
                    Log.d("xinwen", code+"-----------");
                    if(code == 200){
                        list = JSON.parseArray(jsonObject.getString("data"), _NewsData.class);
                        adapter.notifyDataSetChanged(list,str);
                    }else if(code == 400){
                        Toast.makeText(Search.this,"没有内容",Toast.LENGTH_SHORT).show();
                    }else{
                        Toast.makeText(Search.this,"加载错误",Toast.LENGTH_SHORT).show();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError volleyError) {
//                Log.d("xinwen", volleyError.getMessage());
                Log.e("log.d", volleyError.getMessage(), volleyError);
                byte[] htmlBodyBytes = volleyError.networkResponse.data;
                Log.e("log.d", new String(htmlBodyBytes), volleyError);
                Toast.makeText(Search.this,"网络异常",Toast.LENGTH_SHORT).show();
            }
        }){
            @Override
            public Map<String, String> getHeaders() throws AuthFailureError {
                HashMap<String, String> headers = new HashMap<String, String>();
                headers.put("Charset", "UTF-8");
                headers.put("Accept", "application/json");
                return headers;
            }
        };
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                queue.add(jsonObjectRequest);
                jsonObjectRequest.setTag(TAG01);
            }
        }, 500);
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("搜索");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.search_listview);
        contentEdit = (EditText) findViewById(R.id.search_content_edit);
        hisLin = (LinearLayout) findViewById(R.id.search_his_lin);
        hisListView = (ListView) findViewById(R.id.search_his_listview);
        LayoutInflater inflater = getLayoutInflater();
        hisListViewFooter =inflater.inflate(R.layout.search_his_list_footer,null,false);


    }

    class MySearchAdapter extends BaseAdapter{
        private List<_NewsData> list;
        private String keywordStr;

        public MySearchAdapter(List<_NewsData> list, String keywordStr) {
            this.keywordStr = keywordStr;
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
            ViewHolder holder = null;
            if(convertView == null){
                LayoutInflater inflater = LayoutInflater.from(parent.getContext());
                convertView = inflater.inflate(R.layout.search_list_item, parent, false);
                holder = new ViewHolder();
                holder.titleTv = (TextView) convertView.findViewById(R.id.search_list_item_title_tv);
                holder.dateTv = (TextView) convertView.findViewById(R.id.search_list_item_date_tv);
                convertView.setTag(holder);
            }else{
                holder = (ViewHolder) convertView.getTag();
            }
            _NewsData newsData = list.get(position);
            //关键字高亮
            String str = newsData.getTitle();
            int fstart=str.indexOf(keywordStr);
            int fend=fstart+keywordStr.length();
            SpannableStringBuilder style=new SpannableStringBuilder(str);
            style.setSpan(new ForegroundColorSpan(Color.RED),fstart,fend, Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            holder.titleTv.setText(style);
            holder.dateTv.setText(newsData.getPtime());
            return convertView;
        }

        class ViewHolder{
            TextView titleTv;
            TextView dateTv;
        }

        public void notifyDataSetChanged(List<_NewsData> list, String keywordStr) {
            this.keywordStr = keywordStr;
            this.list = list;
            notifyDataSetChanged();
        }
    }

}
