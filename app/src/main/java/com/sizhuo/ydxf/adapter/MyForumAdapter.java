package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sizhuo.ydxf.R;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  论坛Adapter
 * Created by My灬xiao7
 * date: 2015/12/29
 *
 * @version 1.0
 */
public class MyForumAdapter extends BaseAdapter{
    private List<String> list;
    private Context context;

    public MyForumAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
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
            convertView = inflater.inflate(R.layout.fragment_forum_item,parent,false);
            holder = new ViewHolder();
            holder.textView = (TextView) convertView.findViewById(R.id.fragment_forum_item_text_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        holder.textView.setText(list.get(position));
        return convertView;
    }

    public void notifyDataSetChanged(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    class ViewHolder{
        TextView textView;
    }
}
