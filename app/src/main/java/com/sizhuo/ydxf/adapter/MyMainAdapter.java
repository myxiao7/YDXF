package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.Forum;
import com.sizhuo.ydxf.Module01;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.RedMap;
import com.sizhuo.ydxf.VideoModule;
import com.sizhuo.ydxf.entity.MainBean;
import com.sizhuo.ydxf.entity._MainData;
import com.sizhuo.ydxf.entity._NewsData;
import com.sizhuo.ydxf.entity._PostDetailData;
import com.sizhuo.ydxf.util.ImageLoaderHelper;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  主菜单Adapter
 * Created by My灬xiao7
 * date: 2015/12/17
 *
 * @version 1.0
 */
public class MyMainAdapter extends BaseAdapter{
    private List<_MainData> list;
    private Context context;
    private final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_THREE = 2, TYPE_COUNT = 3;//子布局类型和个数

    public MyMainAdapter(List<_MainData> list, Context context) {
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return list.get(0).getNews().size()+list.get(0).getCard().size()+2;
    }

    @Override
    public Object getItem(int position) {
        return list.get(0);
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder01 viewHolder01= null;
        ViewHolder02 viewHolder02 = null;
        ViewHolder03 viewHolder03 = null;
        int type = getItemViewType(position);
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        if(convertView == null){
            switch (type){
                case TYPE_ONE:
                    viewHolder01 = new ViewHolder01();
                    convertView = layoutInflater.inflate(R.layout.main_list_item01, parent, false);
                    viewHolder01.icon = (ImageView) convertView.findViewById(R.id.main_list_item01_icon);
                    viewHolder01.columnTv = (TextView) convertView.findViewById(R.id.main_list_item01_column_tv);
                    viewHolder01.moreTv = (TextView) convertView.findViewById(R.id.main_list_item01_more_tv);
                    viewHolder01.moreRe = (RelativeLayout) convertView.findViewById(R.id.main_list_item01_re_more);
                    viewHolder01.imageView = (ImageView) convertView.findViewById(R.id.main_list_item01_img);
                    viewHolder01.titleTv = (TextView) convertView.findViewById(R.id.main_list_item01_title_tv);
                    viewHolder01.desTv = (TextView) convertView.findViewById(R.id.main_list_item01_des_tv);
                    viewHolder01.dateTv = (TextView) convertView.findViewById(R.id.main_list_item01_date_tv);
                    convertView.setTag(viewHolder01);
                    break;

                case TYPE_TWO:
                    viewHolder02 = new ViewHolder02();
                    convertView = layoutInflater.inflate(R.layout.main_list_item02, parent, false);
                    viewHolder02.menu01 = (LinearLayout) convertView.findViewById(R.id.main_list_item02_menu01_lin);
                    viewHolder02.menu02 = (LinearLayout) convertView.findViewById(R.id.main_list_item02_menu02_lin);
                    viewHolder02.menu03 = (LinearLayout) convertView.findViewById(R.id.main_list_item02_menu03_lin);
                    viewHolder02.menu04 = (LinearLayout) convertView.findViewById(R.id.main_list_item02_menu04_lin);
                    convertView.setTag(viewHolder02);
                    break;

                case TYPE_THREE:
                    viewHolder03 = new ViewHolder03();
                    convertView = layoutInflater.inflate(R.layout.main_list_item03, parent, false);
                    viewHolder03.img = (ImageView) convertView.findViewById(R.id.main_list_item03_img);
                    convertView.setTag(viewHolder03);
                    break;

            }
        }else{
            switch (type){
                case TYPE_ONE:
                    viewHolder01 = (ViewHolder01) convertView.getTag();
                    break;
                case TYPE_TWO:
                    viewHolder02 = (ViewHolder02) convertView.getTag();
                    break;
                case TYPE_THREE:
                    viewHolder03 = (ViewHolder03) convertView.getTag();
                    break;

            }
        }
        //设置数据

        switch (type){
            case TYPE_ONE:
                _NewsData newsData = null;
                _PostDetailData postDetailData = null;
                if(position == 0){
                    viewHolder01.icon.setBackgroundResource(R.mipmap.ic_m01);
                    viewHolder01.columnTv.setText("大事小情");
                }
                if(position == 1){
                    viewHolder01.icon.setBackgroundResource(R.mipmap.ic_m02);
                    viewHolder01.columnTv.setText("党务公开");
                }
                if(position == 3){
                    viewHolder01.icon.setBackgroundResource(R.mipmap.ic_m03);
                    viewHolder01.columnTv.setText("我有好点子");
                }
                if(position == 4){
                    viewHolder01.icon.setBackgroundResource(R.mipmap.ic_m04);
                    viewHolder01.columnTv.setText("活动专区");
                }

                if(position<2){
                    newsData = list.get(0).getNews().get(position);
                    if(!TextUtils.isEmpty(newsData.getUrl())){
                        ImageLoaderHelper.getIstance().loadImg(newsData.getUrl(),viewHolder01.imageView);
                    }
                    viewHolder01.titleTv.setText(newsData.getTitle());
                    viewHolder01.desTv.setText(newsData.getDigest());
                    viewHolder01.dateTv.setText(newsData.getPtime());
                }else if(position > 2){
                    postDetailData = list.get(0).getCard().get(position);
                    if(!TextUtils.isEmpty(newsData.getUrl())){
                        ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgsrc(),viewHolder01.imageView);
                    }
                    viewHolder01.titleTv.setText(postDetailData.getTitle());
                    viewHolder01.desTv.setText(postDetailData.getDigest());
                    viewHolder01.dateTv.setText(postDetailData.getPtime());
                }

                viewHolder01.moreRe.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(context, "posi" + position, Toast.LENGTH_SHORT).show();
                        switch (position){
                            case 0:
                                Intent intent = new Intent(context, Module01.class);
                                context.startActivity(intent);
                                break;
                            case 1:

                                break;
                            case 3:
                                Intent intent3 = new Intent(context, Forum.class);
                                context.startActivity(intent3);
                                break;
                        }
                    }
                });
                break;
            case TYPE_TWO:
                    viewHolder02.menu01.setOnClickListener(new MyOnClickListener());
                    viewHolder02.menu02.setOnClickListener(new MyOnClickListener());
                    viewHolder02.menu03.setOnClickListener(new MyOnClickListener());
                    viewHolder02.menu04.setOnClickListener(new MyOnClickListener());
                break;
            case TYPE_THREE:
                viewHolder03.img.setOnClickListener(new MyOnClickListener());
                break;
        }
        return convertView;
    }

    /**
     * 根据 position 返回响应的Item布局
     * @return 对应的布局
     */
    @Override
    public int getItemViewType(int position) {
        //在新闻数据后 设置布局为2
        if(position == list.get(0).getNews().size()){
            return TYPE_TWO;
        }else if(position == list.get(0).getNews().size()+list.get(0).getCard().size()+1){
            //在新闻数据后和论坛数据后+1（布局2）设置布局为3
            return TYPE_THREE;
        }else{
            return TYPE_ONE;
        }
    }

    /**
     * 有多少个布局
     * @return Item布局个数
     */
    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    public class ViewHolder01{
        public ImageView icon;//栏目图标
        public TextView columnTv;//栏目名
        public TextView moreTv;//更多
        public RelativeLayout moreRe;//更多
        public ImageView imageView;//新闻缩略图
        public TextView titleTv;//新闻标题
        public TextView desTv;//新闻简介
        public TextView dateTv;//新闻简介
    }

    public class ViewHolder02{
        public LinearLayout menu01;//菜单
        public LinearLayout menu02;
        public LinearLayout menu03;
        public LinearLayout menu04;
    }
    public class ViewHolder03 {
        public ImageView img;//菜单
    }

    class MyOnClickListener implements View.OnClickListener{
        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.main_list_item02_menu01_lin:
                    Toast.makeText(context, "上级精神", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.main_list_item02_menu02_lin:
                    Toast.makeText(context, "党建研究", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.main_list_item02_menu03_lin:
                    Toast.makeText(context, "党史博览", Toast.LENGTH_SHORT).show();
                    break;
                case R.id.main_list_item02_menu04_lin:
                    Toast.makeText(context, "电视栏目", Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, VideoModule.class);
                    context.startActivity(intent);
                    break;
                case R.id.main_list_item03_img:
                    Toast.makeText(context, "红色地图", Toast.LENGTH_SHORT).show();
                    Intent intent2 = new Intent(context, RedMap.class);
                    context.startActivity(intent2);
                    break;
            }
        }
    }
    public void notifyDataSetChanged(List<_MainData> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
