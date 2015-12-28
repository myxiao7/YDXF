package com.sizhuo.ydxf.adapter;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import com.sizhuo.ydxf.R;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2015/12/28
 *
 * @version 1.0
 */
public class MyViewHoler extends RecyclerView.ViewHolder{
    public TextView textView;
    public MyViewHoler(View itemView) {
        super(itemView);
        textView = (TextView) itemView.findViewById(R.id.fragment_forum_item_text_tv);
    }
}