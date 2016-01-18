package com.sizhuo.ydxf;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.style.ForegroundColorSpan;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.entity.NewsData;
import com.sizhuo.ydxf.view.zrclistview.ZrcListView;

import java.util.ArrayList;
import java.util.List;

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
    private ListView hisListView;//阅读历史
    private ZrcListView listView;
    private List<NewsData> list = new ArrayList<>();

    private EditText contentEdit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_serch);
        initViews();

        NewsData newsData = new NewsData();
        newsData.setTitle("看过的的新闻看过的的新闻看过的的新闻看过的的新闻看过的的新闻看过的的新闻");
        newsData.setPtime("2016-1-18");
        list.add(newsData);
        list.add(newsData);
        list.add(newsData);
        list.add(newsData);
        list.add(newsData);

        MySearchAdapter adapter = new MySearchAdapter(list);
        hisListView.setAdapter(adapter);
        contentEdit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
                if(actionId== EditorInfo.IME_ACTION_SEARCH){
                    Toast.makeText(Search.this,"搜索",Toast.LENGTH_SHORT).show();
                    hisLin.setVisibility(View.GONE);
                    listView.setVisibility(View.VISIBLE);
                    MySearchAdapter adapter = new MySearchAdapter(list);
                    listView.setAdapter(adapter);
                    return true;
                }
                return false;
            }
        });
    }

    private void initViews() {
        toolbar = (Toolbar) findViewById(R.id.search_toolbar);
        toolbar.setTitle("搜索");
        setSupportActionBar(toolbar);
        listView = (ZrcListView) findViewById(R.id.search_listview);
        contentEdit = (EditText) findViewById(R.id.search_content_edit);
        hisLin = (LinearLayout) findViewById(R.id.search_his_lin);
        hisListView = (ListView) findViewById(R.id.search_his_listview);
    }

    class MySearchAdapter extends BaseAdapter{
        private List<NewsData> list;

        public MySearchAdapter(List<NewsData> list) {
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
            NewsData newsData = list.get(position);
            //关键字高亮
            String str = newsData.getTitle();
            int fstart=str.indexOf("新闻");
            int fend=fstart+"新闻".length();
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
    }

}
