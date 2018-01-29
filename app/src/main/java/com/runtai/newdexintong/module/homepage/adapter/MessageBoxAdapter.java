package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.MessageBoxBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/14.
 * 消息盒子对应的适配器
 */
public class MessageBoxAdapter extends BaseAdapter {

    private List<MessageBoxBean> mDatas;
    private LayoutInflater mInflater;

    public MessageBoxAdapter(Context context, List<MessageBoxBean> datas) {
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_message_box, null);
            holder.tv_message_name = (TextView) convertView.findViewById(R.id.tv_message_name);
            holder.tv_message_count = (TextView) convertView.findViewById(R.id.tv_message_count);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_message_point = (TextView) convertView.findViewById(R.id.tv_message_point);
            holder.iv_message_icon = (ImageView) convertView.findViewById(R.id.iv_message_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_message_name.setText(mDatas.get(position).name);
        if (position == 0) {
            holder.iv_message_icon.setImageResource(R.mipmap.order_message_icon);
        }else if (position == 1){
            holder.iv_message_icon.setImageResource(R.mipmap.reduce_message_icon);
        }else {
            holder.iv_message_icon.setImageResource(R.mipmap.yubianli_message_icon);
        }
        return convertView;
    }

    class ViewHolder {
        TextView tv_message_name,tv_time,tv_message_point,tv_message_count;
        ImageView iv_message_icon;
    }


}
