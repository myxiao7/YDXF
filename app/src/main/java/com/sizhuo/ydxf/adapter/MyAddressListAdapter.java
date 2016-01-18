package com.sizhuo.ydxf.adapter;

import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.sizhuo.ydxf.R;
import com.sizhuo.ydxf.entity.AddressListData;
import com.sizhuo.ydxf.entity._AddListData;
import com.sizhuo.ydxf.util.ImageLoaderHelper;

import java.net.URI;
import java.util.List;

/**
 * 项目名称: YDXF
 * 类描述:  des
 * Created by My灬xiao7
 * date: 2016/1/9
 *
 * @version 1.0
 */
public class MyAddressListAdapter extends BaseAdapter{
    private List<_AddListData> list;
    private Context context;

    public MyAddressListAdapter(List<_AddListData> list, Context context) {
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
            convertView = inflater.inflate(R.layout.fragment_addresslistlist_item, parent, false);
            holder =new ViewHolder();
            holder.img = (ImageView) convertView.findViewById(R.id.frag_addresslist_list_item_img);
            holder.nameTv = (TextView) convertView.findViewById(R.id.frag_addresslist_list_item_name_tv);
            holder.phoneTv = (TextView) convertView.findViewById(R.id.frag_addresslist_list_item_phone_tv);
            holder.callBtn = (ImageView) convertView.findViewById(R.id.frag_addresslist_list_item_call_btn);
            holder.addTv = (TextView) convertView.findViewById(R.id.frag_addresslist_list_item_add_tv);
            holder.saveBtn = (LinearLayout) convertView.findViewById(R.id.frag_addresslist_list_item_save_lin);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }
        _AddListData addListData = list.get(position);
        if(!TextUtils.isEmpty(addListData.getPicture())){
            ImageLoaderHelper.getIstance().loadImg(addListData.getPicture(), holder.img);
        }
        holder.nameTv.setText(addListData.getName());
        holder.phoneTv.setText(addListData.getTelephone());
        holder.addTv.setText(addListData.getAdd());
        holder.callBtn.setOnClickListener(new MyAddOnClickListener(addListData));
        return convertView;
    }

    class MyAddOnClickListener implements View.OnClickListener{
        private _AddListData data;

        public MyAddOnClickListener(_AddListData data) {
            this.data = data;
        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.frag_addresslist_list_item_call_btn:
                    Uri uri = Uri.parse("tel:"+data.getTelephone());
                    Intent intent = new Intent(Intent.ACTION_DIAL,uri);
                    context.startActivity(intent);
                    break;
            }
        }
    }


    class ViewHolder{
        ImageView img;
        TextView nameTv;
        TextView phoneTv;
        TextView addTv;
        ImageView callBtn;
        LinearLayout saveBtn;
    }

    public void notifyDataSetChanged(List<_AddListData> list) {
        this.list = list;
        notifyDataSetChanged();
    }

}
