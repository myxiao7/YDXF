package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.util.ImageLoaderHelper;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  选择图片
 * Created by My灬xiao7
 * date: 2015/12/31
 *
 * @version 1.0
 */
public class MyPhotoAdapter extends BaseAdapter {
    List<String> list;
    Context context;

    public MyPhotoAdapter(Context context, List<String> list) {
        this.context = context;
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
            convertView = inflater.inflate(R.layout.selectphoto_grid_item, parent, false);
            holder = new ViewHolder();
            holder.imageView = (ImageView) convertView.findViewById(R.id.selectphoto_grid_item_img);
            holder.checkBox = (CheckBox) convertView.findViewById(R.id.selectphoto_grid_item_check);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        ImageLoaderHelper.getIstance().loadImg(list.get(position), holder.imageView);
        return convertView;
    }

    class ViewHolder{
        public ImageView imageView;
        public CheckBox checkBox;
    }
}
