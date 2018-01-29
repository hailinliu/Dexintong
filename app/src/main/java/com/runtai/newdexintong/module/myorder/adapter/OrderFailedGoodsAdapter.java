package com.runtai.newdexintong.module.myorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/11/2time14:26
 * @detail：
 */

public class OrderFailedGoodsAdapter extends BaseAdapter {


    private Context mContext;
    private List<String> mData;
    private final LayoutInflater mInflater;

    public OrderFailedGoodsAdapter(Context mContext, List<String> mData) {
        this.mContext = mContext;
        this.mData = mData;
        mInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
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
            convertView = mInflater.inflate(R.layout.item_order_failed_goods, null);
            holder.tv_order_failed_goods_name = (TextView) convertView.findViewById(R.id.tv_order_failed_goods_name);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.tv_order_failed_goods_name.setText(mData.get(position));
        return convertView;
    }
    
    private class ViewHolder {
        TextView tv_order_failed_goods_name;
    }
}
