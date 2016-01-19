package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sizhuo.ydxf.PhotoWatch;
import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.ForumData;
import com.sizhuo.ydxf.entity.PostDetailData;
import com.sizhuo.ydxf.entity._PostDetailData;
import com.sizhuo.ydxf.entity.imgextra;
import com.sizhuo.ydxf.util.ImageLoaderHelper;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 项目名称: YDXF
 * 类描述:  论坛Adapter
 * Created by My灬xiao7
 * date: 2015/12/29
 *
 * @version 1.0
 */
public class MyForumAdapter extends BaseAdapter{
    private List<_PostDetailData> list;
    private Context context;
    private Map<Integer,Integer> replyMap = new HashMap<>();//记录留言技计数位置
    private final int TYPE_ONE = 0, TYPE_TWO = 1, TYPE_THREE = 2, TYPE_COUNT = 3;

    public MyForumAdapter(List<_PostDetailData> list,Context context) {
        this.list = list;
        this.context = context;
    }

    /**
     *  保存留言计数的位置
     * @param visi
     */
    public void configReplyMap(Integer visi){
        for (int i = 0; i < list.size(); i++) {
            replyMap.put(i,visi);
        }
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder01 holder01 = null;
        ViewHolder02 holder02 = null;
        ViewHolder03 holder03 = null;
        int type = getItemViewType(position);
        final LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        if(convertView == null){
            switch (type){
                case TYPE_ONE:
                    convertView = inflater.inflate(R.layout.fragment_forum_item01, parent, false);
                    holder01 = new ViewHolder01();
                    holder01.iconImg = (ImageView) convertView.findViewById(R.id.frag_forum_item01_icon_img);
                    holder01.idTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_id_tv);
                    holder01.dataTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_date_tv);
                    holder01.likeTv = (TextView) convertView.findViewById(R.id.frag_forum_item01_likecount_tv);
                    holder01.replyLin = (LinearLayout) convertView.findViewById(R.id.frag_forum_item01_replycount_lin);
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
                    holder02.replyLin = (LinearLayout) convertView.findViewById(R.id.frag_forum_item02_replycount_lin);
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
                    holder03.replyLin = (LinearLayout) convertView.findViewById(R.id.frag_forum_item03_replycount_lin);
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
        _PostDetailData postDetailData = list.get(position);
        switch (type){
            case TYPE_ONE:
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(0).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(0).getUrl(), holder01.img);
                    holder01.img.setOnClickListener(new WatchPhoto(position, 0));
                }
                if(!TextUtils.isEmpty(postDetailData.getPortrait())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getPortrait(), holder01.iconImg);
                }
                holder01.idTv.setText(postDetailData.getNickName());
                holder01.dataTv.setText(postDetailData.getPtime());
                if(TextUtils.isEmpty(postDetailData.getReplyCount())){
                    replyMap.put(position, View.GONE);
                }else{
                    replyMap.put(position, View.VISIBLE);
                }
                holder01.replyLin.setVisibility(replyMap.get(position));
                holder01.replyTv.setText(postDetailData.getReplyCount());
                holder01.titleTv.setText(postDetailData.getTitle());
                holder01.desTv.setText(postDetailData.getDigest());
                break;

            case TYPE_TWO:
                List<imgextra> imgExtrases = postDetailData.getImgextra();
                if(!TextUtils.isEmpty(postDetailData.getPortrait())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getPortrait(), holder02.iconImg);
                }
                holder02.idTv.setText(postDetailData.getNickName());
                holder02.dataTv.setText(postDetailData.getPtime());
                if(TextUtils.isEmpty(postDetailData.getReplyCount())){
                    replyMap.put(position, View.GONE);
                }else{
                    replyMap.put(position, View.VISIBLE);
                }
                holder02.replyLin.setVisibility(replyMap.get(position));
                holder02.replyTv.setText(postDetailData.getReplyCount());
                holder02.titleTv.setText(postDetailData.getTitle());
                holder02.desTv.setText(postDetailData.getDigest());

                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(0).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(0).getUrl(), holder02.img01);
                    holder02.img01.setOnClickListener(new WatchPhoto(position,0));
                }
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(1).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(1).getUrl(), holder02.img02);
                    holder02.img02.setOnClickListener(new WatchPhoto(position, 1));
                }
                if(!TextUtils.isEmpty(postDetailData.getImgextra().get(2).getUrl())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getImgextra().get(2).getUrl(), holder02.img03);
                    holder02.img03.setOnClickListener(new WatchPhoto(position,2));
                }
                break;

            case TYPE_THREE:
                if(!TextUtils.isEmpty(postDetailData.getPortrait())){
                    ImageLoaderHelper.getIstance().loadImg(postDetailData.getPortrait(), holder03.iconImg);
                }
                holder03.idTv.setText(postDetailData.getNickName());
                holder03.dataTv.setText(postDetailData.getPtime());
                if(TextUtils.isEmpty(postDetailData.getReplyCount())){
                    replyMap.put(position, View.GONE);
                }else{
                    replyMap.put(position, View.VISIBLE);
                }
                holder03.replyLin.setVisibility(replyMap.get(position));
                holder03.replyTv.setText(postDetailData.getReplyCount());
                holder03.titleTv.setText(postDetailData.getTitle());
                holder03.desTv.setText(postDetailData.getDigest());
                break;
        }
        return convertView;
    }

    @Override
    public int getItemViewType(int position) {

       if(list.get(position).getImgextra()!=null){
           if(list.get(position).getImgextra().size()==1){
               //单图
               return TYPE_ONE;
           }
            if(list.get(position).getImgextra().size()==3){
                //多图
                return TYPE_TWO;
           }
       }else{
           //无图
           return TYPE_THREE;
       }
        return TYPE_THREE;
    }

    @Override
    public int getViewTypeCount() {
        return TYPE_COUNT;
    }


    //单图
    class ViewHolder01{
        ImageView iconImg;//头像
        TextView idTv;//ID
        TextView dataTv;//日期
        TextView likeTv;//赞
        LinearLayout replyLin;//回复..
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
        LinearLayout replyLin;//回复..
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
        LinearLayout replyLin;//回复..
        TextView replyTv;//回复
        TextView titleTv;//标题
        TextView desTv;//描述
    }

    class WatchPhoto implements View.OnClickListener{
        int position;
        int index;

        public WatchPhoto(int position, int index) {
            this.position = position;
            this.index = index;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.frag_forum_item01_img:
                    startPhotoWatch();
                    break;

                case R.id.frag_forum_item02_img01:
                    startPhotoWatch();
                    break;

                case R.id.frag_forum_item02_img02:
                    startPhotoWatch();
                    break;

                case R.id.frag_forum_item02_img03:
                    startPhotoWatch();
                    break;

            }
        }

        private void startPhotoWatch() {
            Intent intent = new Intent(context, PhotoWatch.class);
            intent.putExtra("data",list.get(position));
            intent.putExtra("index",index);
            context.startActivity(intent);
        }
    }

    public void notifyDataSetChanged(List<_PostDetailData> list) {
        this.list = list;
        notifyDataSetChanged();
    }
}
