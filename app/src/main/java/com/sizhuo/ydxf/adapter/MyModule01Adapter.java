package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.imgextra;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.util.ImageLoaderHelper;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  栏目一adapter（大事小情）
 * Created by My灬xiao7
 * date: 2015/12/18
 *
 * @version 1.0
 */
public class MyModule01Adapter extends BaseAdapter {
    private List<_NewsData> list;
    private Context context;
    private final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_THREE = 2, TYPE_COUNT = 3;//子布局类型和个数

    public MyModule01Adapter(List<_NewsData> list, Context context) {
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
        ViewHolder01 holder01 = null;
        ViewHolder02 holder02 = null;
        ViewHolder03 holder03 = null;
        int type = getItemViewType(position);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if (convertView == null) {
            switch (type) {
                case TYPE_ONE:
                    convertView = inflater.inflate(R.layout.module01_list_item01, parent, false);
                    holder01 = new ViewHolder01();
                    holder01.imageView = (ImageView) convertView.findViewById(R.id.module01_list_item01_img);
                    holder01.titleTv = (TextView) convertView.findViewById(R.id.module01_list_item01_title_tv);
                    holder01.desTv = (TextView) convertView.findViewById(R.id.module01_list_item01_des_tv);
                    holder01.dateTv = (TextView) convertView.findViewById(R.id.module01_list_item01_date_tv);
                    convertView.setTag(holder01);
                    break;

                case TYPE_TWO:
                    convertView = inflater.inflate(R.layout.module01_list_item02, parent, false);
                    holder02 = new ViewHolder02();
                    holder02.imageView01 = (ImageView) convertView.findViewById(R.id.module01_list_item02_img01);
                    holder02.imageView02 = (ImageView) convertView.findViewById(R.id.module01_list_item02_img02);
                    holder02.imageView03 = (ImageView) convertView.findViewById(R.id.module01_list_item02_img03);
                    holder02.titleTv = (TextView) convertView.findViewById(R.id.module01_list_item02_title_tv);
                    holder02.dateTv = (TextView) convertView.findViewById(R.id.module01_list_item02_date_tv);
                    convertView.setTag(holder02);
                    break;

                case TYPE_THREE:
                    convertView = inflater.inflate(R.layout.module01_list_item03, parent, false);
                    holder03 = new ViewHolder03();
                    holder03.titleTv = (TextView) convertView.findViewById(R.id.module01_list_item03_title_tv);
                    holder03.desTv = (TextView) convertView.findViewById(R.id.module01_list_item03_des_tv);
                    holder03.dateTv = (TextView) convertView.findViewById(R.id.module01_list_item03_date_tv);
                    convertView.setTag(holder03);
                    break;
            }
        } else {
            switch (type) {
                case TYPE_ONE:
                    holder01 = (ViewHolder01) convertView.getTag();
                    break;
                case TYPE_TWO:
                    holder02 = (ViewHolder02) convertView.getTag();
                    break;
                case TYPE_THREE:
                    holder03 = (ViewHolder03) convertView.getTag();
                    break;
            }
        }
        //设置数据
        _NewsData newsData = list.get(position);
        switch (type) {
            case TYPE_ONE:
                if(!TextUtils.isEmpty(newsData.getImgsrc())){
                    ImageLoaderHelper.getIstance().loadImg(newsData.getImgsrc(),holder01.imageView);
                }
                holder01.titleTv.setText(newsData.getTitle());
                holder01.desTv.setText(newsData.getDigest());
                holder01.dateTv.setText(newsData.getPtime());
                break;
            case TYPE_TWO:
                List<imgextra> imgExtrases = newsData.getImgextra();
                if(!TextUtils.isEmpty(imgExtrases.get(0).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(imgExtrases.get(0).getUrl(), holder02.imageView01);
                }
                if(!TextUtils.isEmpty(imgExtrases.get(1).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(imgExtrases.get(1).getUrl(),holder02.imageView02);
                }
                if(!TextUtils.isEmpty(imgExtrases.get(2).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(imgExtrases.get(2).getUrl(),holder02.imageView03);
                }
                holder02.titleTv.setText(newsData.getTitle());
                holder02.dateTv.setText(newsData.getPtime());
                break;
            case TYPE_THREE:
                holder03.titleTv.setText(newsData.getTitle());
                holder03.desTv.setText(newsData.getDigest());
                holder03.dateTv.setText(newsData.getPtime());
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        if (list.get(position).getType().equals("1")) {
            return TYPE_ONE;
        } else if (list.get(position).getType().equals("3")) {
            return TYPE_TWO;
        } else {
            return TYPE_THREE;
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    class ViewHolder01 {
        ImageView imageView;
        TextView titleTv;
        TextView desTv;
        TextView dateTv;
    }

    class ViewHolder02 {
        TextView titleTv;
        TextView dateTv;
        ImageView imageView01;
        ImageView imageView02;
        ImageView imageView03;
    }

    class ViewHolder03 {
        TextView titleTv;
        TextView desTv;
        TextView dateTv;
    }

    public void notifyDataSetChanged(List<_NewsData> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
