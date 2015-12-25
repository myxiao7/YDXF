package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.GridBean2;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  便民114Adapter
 * Created by My灬xiao7
 * date: 2015/12/17
 *
 * @version 1.0
 */
public class MyBottomGridAdapter2 extends BaseAdapter{
    private List<GridBean2> list;
    private Context context;

    public MyBottomGridAdapter2(List<GridBean2> list, Context context) {
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
        ViewHolder viewHolder = null;
        if(convertView == null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.main_bottom_grid_item2, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textView = (TextView) convertView.findViewById(R.id.main_bottom_grid_item2_tv01);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GridBean2 gridBean2 = list.get(position);
        viewHolder.textView.setText(gridBean2.getItem());
        return convertView;
    }

    class ViewHolder{
        TextView textView;
    }

    public void notifyDataSetChanged(List<GridBean2> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
