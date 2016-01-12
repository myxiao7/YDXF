package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.GridBean;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  便民服务Adapter
 * Created by My灬xiao7
 * date: 2015/12/17
 *
 * @version 1.0
 */
public class MyOrgGridAdapter extends BaseAdapter{
    private List<GridBean> list;
    private Context context;

    public MyOrgGridAdapter(List<GridBean> list, Context context) {
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
            convertView = inflater.inflate(R.layout.org_grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (ImageView) convertView.findViewById(R.id.org_grid_img);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.org_grid_title_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        GridBean gridBean = list.get(position);
        viewHolder.imageView.setBackgroundResource(gridBean.getImg());
        viewHolder.textView.setText(gridBean.getTitle());
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView textView;
    }
}