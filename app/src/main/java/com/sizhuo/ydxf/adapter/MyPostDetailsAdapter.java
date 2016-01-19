package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.ReplyData;
import com.sizhuo.ydxf.entity._ReplyData;
import com.sizhuo.ydxf.util.ImageLoaderHelper;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  回复详情adapter
 * Created by My灬xiao7
 * date: 2016/1/7
 *
 * @version 1.0
 */
public class MyPostDetailsAdapter extends BaseAdapter {
    private List<_ReplyData> list;
    private Context context;

    public MyPostDetailsAdapter(List<_ReplyData> list, Context context) {
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
        ViewHolder holder =null;
        if(convertView==null){
            LayoutInflater inflater = LayoutInflater.from(parent.getContext());
            convertView = inflater.inflate(R.layout.postdetails_list_item, parent, false);
            holder =new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.postdetails_list_item_icon_img);
            holder.nameTv = (TextView) convertView.findViewById(R.id.postdetails_list_item_name_tv);
            holder.floorTv = (TextView) convertView.findViewById(R.id.postdetails_list_item_floor_tv);
            holder.contentTv = (TextView) convertView.findViewById(R.id.postdetails_list_item_content_tv);
            holder.dateTv = (TextView) convertView.findViewById(R.id.postdetails_list_item_date_tv);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        _ReplyData replyData = list.get(position);
        ImageLoaderHelper.getIstance().loadImg(replyData.getPortrait(), holder.img);
        holder.nameTv.setText(replyData.getNickName());
        holder.floorTv.setText((position+1)+"楼");
        holder.contentTv.setText(replyData.getContent());
        holder.dateTv.setText(replyData.getpTime());
        return convertView;
    }

    class ViewHolder{
        ImageView img;
        TextView nameTv;
        TextView floorTv;
        TextView contentTv;
        TextView dateTv;
    }

    public void notifyDataSetChanged(List<_ReplyData> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
