package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.daimajia.slider.library.Animations.DescriptionAnimation;
import com.daimajia.slider.library.SliderLayout;
import com.daimajia.slider.library.SliderTypes.BaseSliderView;
import com.daimajia.slider.library.SliderTypes.TextSliderView;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.bean.NewsBean;

import java.util.HashMap;
import java.util.LinkedHashMap;
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
    private HashMap<String,String> url_maps = new LinkedHashMap<String, String>();
    //轮播图
    private List<NewsBean> list;
    private Context context;
    private final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_THREE = 2, TYPE_FOUR = 3, TYPE_COUNT = 4;//子布局类型和个数

    public MyModule01Adapter(List<NewsBean> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.size()+1;
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
        ViewHolder04 holder04 = null;
        int type = getItemViewType(position);
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(convertView == null){
            switch (type){
                case TYPE_ONE:
                    convertView = inflater.inflate(R.layout.module01_list_item01, parent, false);
                    holder01 = new ViewHolder01();
                    holder01.sliderLayout = (SliderLayout) convertView.findViewById(R.id.module01_list_item01_slider);
                    convertView.setTag(holder01);
                    break;

                case TYPE_TWO:
                    convertView = inflater.inflate(R.layout.module01_list_item02, parent, false);
                    holder02 = new ViewHolder02();
                    holder02.imageView = (ImageView) convertView.findViewById(R.id.module01_list_item02_img);
                    holder02.titleTv = (TextView) convertView.findViewById(R.id.module01_list_item02_title_tv);
                    holder02.desTv = (TextView) convertView.findViewById(R.id.module01_list_item02_des_tv);
//                    holder02.dateTv = (ImageView) convertView.findViewById(R.id.module01_list_item02_img);
                    convertView.setTag(holder02);
                    break;

                case TYPE_THREE:
                    convertView = inflater.inflate(R.layout.module01_list_item03, parent, false);
                    holder03 = new ViewHolder03();
                    holder03.imageView01 = (ImageView) convertView.findViewById(R.id.module01_list_item03_img01);
                    holder03.imageView02 = (ImageView) convertView.findViewById(R.id.module01_list_item03_img02);
                    holder03.imageView03 = (ImageView) convertView.findViewById(R.id.module01_list_item03_img03);
                    holder03.titleTv = (TextView) convertView.findViewById(R.id.module01_list_item03_title_tv);
                    holder03.dateTv = (TextView) convertView.findViewById(R.id.module01_list_item03_date_tv);
                    convertView.setTag(holder03);
                    break;

                case TYPE_FOUR:
                    convertView = inflater.inflate(R.layout.module01_list_item04, parent, false);
                    holder04 = new ViewHolder04();
                    holder04.titleTv = (TextView) convertView.findViewById(R.id.module01_list_item04_title_tv);
                    holder04.desTv = (TextView) convertView.findViewById(R.id.module01_list_item04_des_tv);
                    holder04.dateTv = (TextView) convertView.findViewById(R.id.module01_list_item04_date_tv);
                    convertView.setTag(holder04);
                    break;
            }
        }else{
            switch (type){
                case TYPE_ONE:
                    holder01 = (ViewHolder01) convertView.getTag();
                    break;
                case TYPE_TWO:
                    holder02 = (ViewHolder02) convertView.getTag();
                    break;
                case TYPE_THREE:
                    holder03 = (ViewHolder03) convertView.getTag();
                    break;
                case TYPE_FOUR:
                    holder04 = (ViewHolder04) convertView.getTag();
                    break;

            }
        }
        //设置数据
        NewsBean newsBean = null;
        switch (type){
            case TYPE_ONE:
                holder01.sliderLayout.removeAllSliders();
                url_maps.put("测试01", "http://192.168.1.114:8080/xinwen/img/item01.jpg");
                url_maps.put("测试02", "http://192.168.1.114:8080/xinwen/img/item02.jpg");
                url_maps.put("测试03", "http://192.168.1.114:8080/xinwen/img/item03.jpg");
                url_maps.put("测试04", "http://192.168.1.114:8080/xinwen/img/item04.jpg");
                url_maps.put("小学足球联赛开幕 OMG 1:0 VG", "http://192.168.1.114:8080/xinwen/img/item05.jpg");
                for(String name : url_maps.keySet()){
                    TextSliderView textSliderView = new TextSliderView(context);
                    // initialize a SliderLayout
                    textSliderView
                            .description(name)
                            .image(url_maps.get(name))
                            .setScaleType(BaseSliderView.ScaleType.Fit)
                            ;

                    //add your extra information
                    textSliderView.bundle(new Bundle());
                    textSliderView.getBundle()
                            .putString("extra",name);

                    holder01.sliderLayout.addSlider(textSliderView);
                }
                holder01.sliderLayout.setPresetTransformer(SliderLayout.Transformer.Default);
                holder01.sliderLayout.setPresetIndicator(SliderLayout.PresetIndicators.Right_Bottom);
                holder01.sliderLayout.setCustomAnimation(new DescriptionAnimation());
                holder01.sliderLayout.startAutoCycle(2500, 4000, true);
                break;
            case TYPE_TWO:
                if(position!=0){
                    newsBean = list.get(position-1);
                }
                    holder02.imageView.setBackgroundResource(R.mipmap.ic_icon);
                    holder02.titleTv.setText(newsBean.getTitle());
                    holder02.desTv.setText(newsBean.getDes());
                break;
            case TYPE_THREE:
                if(position!=0){
                    newsBean = list.get(position-1);
                }
                holder03.imageView01.setBackgroundResource(R.mipmap.ic_icon);
                holder03.imageView02.setBackgroundResource(R.mipmap.ic_icon);
                holder03.imageView03.setBackgroundResource(R.mipmap.ic_icon);
                holder03.titleTv.setText(newsBean.getTitle());
                holder03.dateTv.setText(newsBean.getDate());
                break;
            case TYPE_FOUR:
                if(position!=0){
                    newsBean = list.get(position-1);
                }
                holder04.titleTv.setText(newsBean.getTitle());
                holder04.desTv.setText(newsBean.getDes());
                holder04.dateTv.setText(newsBean.getDate());
                break;
        }

        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        //轮播图
        if(position == 0){
            return TYPE_ONE;
            //在没有第2.3张图的时候
        }else{
            --position;
            if(list.get(position).getType().equals("1")){
                return TYPE_TWO;
            }else if(list.get(position).getType().equals("2")){
                return TYPE_THREE;
            }else{
                return TYPE_FOUR;
            }
        }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    class ViewHolder01{
        SliderLayout sliderLayout;
    }
    class ViewHolder02{
        ImageView imageView;
        TextView titleTv;
        TextView desTv;
        TextView dateTv;
    }
    class ViewHolder03{
        TextView titleTv;
        TextView dateTv;
        ImageView imageView01;
        ImageView imageView02;
        ImageView imageView03;
    }
    class ViewHolder04{
        TextView titleTv;
        TextView desTv;
        TextView dateTv;
    }

    public void notifyDataSetChanged(List<NewsBean> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
