package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.OrderMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
public class OrderMessageAdapter extends BaseAdapter {


    private Context context;
    private List<OrderMessageBean> mDatas;
    private final LayoutInflater mInflater;

    public OrderMessageAdapter(Context context, List<OrderMessageBean> data) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = data;
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
            convertView = mInflater.inflate(R.layout.item_order_message, null);
            holder.tv_order_msg_detail = (TextView) convertView.findViewById(R.id.tv_order_msg_detail);
            holder.tv_order_msg_time = (TextView) convertView.findViewById(R.id.tv_order_msg_time);
            holder.tv_order_msg_state = (TextView) convertView.findViewById(R.id.tv_order_msg_state);
            holder.iv_order_message_icon = (ImageView) convertView.findViewById(R.id.iv_order_message_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }

    class ViewHolder {
        TextView tv_order_msg_detail, tv_order_msg_time, tv_order_msg_state;
        ImageView iv_order_message_icon;
    }
}
