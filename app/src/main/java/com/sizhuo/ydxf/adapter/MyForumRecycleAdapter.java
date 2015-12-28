package com.sizhuo.ydxf.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.Test;

import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:   论坛Adapter
 * Created by My灬xiao7
 * date: 2015/12/28
 *
 * @version 1.0
 */
public class MyForumRecycleAdapter extends RecyclerView.Adapter<MyViewHoler>{
    private List<String> list;
    private Context context;
    private LayoutInflater inflater;

    public MyForumRecycleAdapter(List<String> list, Context context) {
        this.list = list;
        this.context = context;
        inflater = LayoutInflater.from(context);
    }


    @Override
    public MyViewHoler onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.fragment_forum_item, parent, false);
        MyViewHoler myViewHoler = new MyViewHoler(view);
        return myViewHoler;
    }

    @Override
    public void onBindViewHolder(MyViewHoler holder, int position) {
        holder.textView.setText(list.get(position));
    }

    @Override
    public int getItemCount() {
        return list.size();
    }

}
