package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.ForumData;
import com.sizhuo.ydxf.entity.imgextra;
import com.sizhuo.ydxf.util.ImageLoaderHelper;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  论坛Adapter
 * Created by My灬xiao7
 * date: 2015/12/29
 *
 * @version 1.0
 */
public class MyForumAdapter extends BaseAdapter implements View.OnClickListener {
    private List<ForumData> list;
    private Context context;
    private final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_THREE = 2, TYPE_COUNT = 3;

    public MyForumAdapter(List<ForumData> list,Context context) {
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
        if(convertView == null){
            switch (type){
                case TYPE_ONE:
                    convertView = inflater.inflate(R.layout.fragment_forum_item01, parent, false);
                    holder01 = new ViewHolder01();
                    holder01.iconImg = (ImageView) convertView.findViewById(R.id.frag_forum_item01_icon_img);
                    holder01.idTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_id_tv);
                    holder01.dataTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_date_tv);
                    holder01.likeTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_likecount_tv);
                    holder01.replyTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_replycount_tv);
                    holder01.titleTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_title_tv);
                    holder01.desTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_des_tv);
                    holder01.img = (ImageView) convertView.findViewById(R.id.frag_forum_item01_img);
                    convertView.setTag(holder01);
                    break;

                case TYPE_TWO:
                    convertView = inflater.inflate(R.layout.fragment_forum_item02, parent, false);
                    holder02 = new ViewHolder02();
                    holder02.iconImg = (ImageView) convertView.findViewById(R.id.frag_forum_item02_icon_img);
                    holder02.idTv = (TextView) convertView.findViewById(R.id.frag_forum_item02_id_tv);
                    holder02.dataTv = (TextView) convertView.findViewById(R.id.frag_forum_item02_date_tv);
                    holder02.likeTv = (TextView) convertView.findViewById(R.id.frag_forum_item02_likecount_tv);
                    holder02.replyTv = (TextView) convertView.findViewById(R.id.frag_forum_item02_replycount_tv);
                    holder02.titleTv = (TextView) convertView.findViewById(R.id.frag_forum_item02_title_tv);
                    holder02.desTv = (TextView) convertView.findViewById(R.id.frag_forum_item02_des_tv);
                    holder02.img01 = (ImageView) convertView.findViewById(R.id.frag_forum_item02_img01);
                    holder02.img02 = (ImageView) convertView.findViewById(R.id.frag_forum_item02_img02);
                    holder02.img03 = (ImageView) convertView.findViewById(R.id.frag_forum_item02_img03);
                    convertView.setTag(holder02);
                    break;

                case TYPE_THREE:
                    holder03 = new ViewHolder03();
                    convertView = inflater.inflate(R.layout.fragment_forum_item03, parent, false);
                    holder03.iconImg = (ImageView) convertView.findViewById(R.id.frag_forum_item03_icon_img);
                    holder03.idTv = (TextView) convertView.findViewById(R.id.frag_forum_item03_id_tv);
                    holder03.dataTv = (TextView) convertView.findViewById(R.id.frag_forum_item03_date_tv);
                    holder03.likeTv = (TextView) convertView.findViewById(R.id.frag_forum_item03_likecount_tv);
                    holder03.replyTv = (TextView) convertView.findViewById(R.id.frag_forum_item03_replycount_tv);
                    holder03.titleTv = (TextView) convertView.findViewById(R.id.frag_forum_item03_title_tv);
                    holder03.desTv = (TextView) convertView.findViewById(R.id.frag_forum_item03_des_tv);
                    convertView.setTag(holder03);
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
            }
        }
        ForumData forumData = list.get(position);
        switch (type){
            case TYPE_ONE:
                ImageLoaderHelper.getIstance().loadImg(forumData.getIconUrl(), holder01.iconImg);
                holder01.idTv.setText(forumData.getId());
                holder01.dataTv.setText(forumData.getDate());
                holder01.likeTv.setText(forumData.getLikeCount());
                holder01.replyTv.setText(forumData.getReplyCount());
                holder01.titleTv.setText(forumData.getTitle());
                holder01.desTv.setText(forumData.getDes());
                ImageLoaderHelper.getIstance().loadImg(forumData.getImgUrl(), holder01.img);
                holder01.iconImg.setOnClickListener(this);
                break;

            case TYPE_TWO:
                List<imgextra> imgExtrases = forumData.getImgextra();
                ImageLoaderHelper.getIstance().loadImg(forumData.getIconUrl(), holder02.iconImg);
                holder02.idTv.setText(forumData.getId());
                holder02.dataTv.setText(forumData.getDate());
                holder02.likeTv.setText(forumData.getLikeCount());
                holder02.replyTv.setText(forumData.getReplyCount());
                holder02.titleTv.setText(forumData.getTitle());
                holder02.desTv.setText(forumData.getDes());
                ImageLoaderHelper.getIstance().loadImg(imgExtrases.get(0).getUrl(), holder02.img01);
                ImageLoaderHelper.getIstance().loadImg(imgExtrases.get(1).getUrl(), holder02.img02);
                ImageLoaderHelper.getIstance().loadImg(imgExtrases.get(2).getUrl(), holder02.img03);
                holder02.img01.setOnClickListener(this);
                holder02.img02.setOnClickListener(this);
                holder02.img03.setOnClickListener(this);
                break;

            case TYPE_THREE:
                ImageLoaderHelper.getIstance().loadImg(forumData.getIconUrl(), holder03.iconImg);
                holder03.idTv.setText(forumData.getId());
                holder03.dataTv.setText(forumData.getDate());
                holder03.likeTv.setText(forumData.getLikeCount());
                holder03.replyTv.setText(forumData.getReplyCount());
                holder03.titleTv.setText(forumData.getTitle());
                holder03.desTv.setText(forumData.getDes());
                break;
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {
        //单图
       if(list.get(position).getType().equals("1")){
           return TYPE_ONE;
           //多图
       }else if (list.get(position).getType().equals("2")) {
           return TYPE_TWO;
           //无图
       } else {
           return TYPE_THREE;
       }
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }

    public void notifyDataSetChanged(List<ForumData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.frag_forum_item01_img:
                break;

            case R.id.frag_forum_item02_img01:
                break;

            case R.id.frag_forum_item02_img02:
                break;

            case R.id.frag_forum_item02_img03:
                break;

        }
    }

    //单图
    class ViewHolder01{
        ImageView iconImg;//头像
        TextView idTv;//ID
        TextView dataTv;//日期
        TextView likeTv;//赞
        TextView replyTv;//回复
        TextView titleTv;//标题
        TextView desTv;//描述
        ImageView img;//图
    }
    //三图
    class ViewHolder02{
        ImageView iconImg;//头像
        TextView idTv;//ID
        TextView dataTv;//日期
        TextView likeTv;//赞
        TextView replyTv;//回复
        TextView titleTv;//标题
        TextView desTv;//描述
        ImageView img01;//图
        ImageView img02;//图
        ImageView img03;//图
    }
    //无图
    class ViewHolder03{
        ImageView iconImg;//头像
        TextView idTv;//ID
        TextView dataTv;//日期
        TextView likeTv;//赞
        TextView replyTv;//回复
        TextView titleTv;//标题
        TextView desTv;//描述
    }
}
