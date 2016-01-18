package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity._AddListData;
import com.sizhuo.ydxf.entity._OrgData;
import com.sizhuo.ydxf.util.ImageLoaderHelper;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * 项目名称: YDXF
 * 类描述:  便民114Adapter
 * Created by My灬xiao7
 * date: 2015/12/17
 *
 * @version 1.0
 */
public class MyBottomGridAdapter2 extends BaseAdapter{
    private List<_AddListData> list;
    private Context context;

    public MyBottomGridAdapter2(List<_AddListData> list, Context context) {
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
            convertView = inflater.inflate(R.layout.main_bottom_grid_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.imageView = (CircleImageView) convertView.findViewById(R.id.main_bottom_grid_img);
            viewHolder.textView = (TextView) convertView.findViewById(R.id.main_bottom_grid_title_tv);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        _AddListData addListData = list.get(position);
        if(!TextUtils.isEmpty(addListData.getPicture())){
            ImageLoaderHelper.getIstance().loadImg(addListData.getPicture() + "", viewHolder.imageView);
        }
        viewHolder.textView.setText(addListData.getName());
        return convertView;
    }

    class ViewHolder{
        CircleImageView imageView;
        TextView textView;
    }

    public void notifyDataSetChanged(List<_AddListData> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
