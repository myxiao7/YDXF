package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.bean.Module01Bean;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  栏目一adapter（大事小情）
 * Created by My灬xiao7
 * date: 2015/12/18
 *
 * @version 1.0
 */
public class MyModule01Adapter extends BaseAdapter{
    private List<Module01Bean> list;
    private Context context;

    public MyModule01Adapter(List<Module01Bean> list, Context context) {
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
            convertView = inflater.inflate(R.layout.module01_list_item, parent, false);
            holder= new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.module01_list_item_img);
            holder.titleTv = (TextView) convertView.findViewById(R.id.module01_list_item_title_tv);
            holder.desTv = (TextView) convertView.findViewById(R.id.module01_list_item_des_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        Module01Bean module01Bean = list.get(position);
        holder.imageView.setBackgroundResource(R.mipmap.ic_launcher);
        holder.titleTv.setText(module01Bean.getTitle());
        holder.desTv.setText(module01Bean.getDes());
        return convertView;
    }

    class ViewHolder{
        ImageView imageView;
        TextView titleTv;
        TextView desTv;
        TextView dateTv;
    }
}
