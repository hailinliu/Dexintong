package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.personalcenter.bean.RechargeDayCollectBean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/11/12time16:49
 * @detail：
 */

public class RechargeDayCollectAdapter extends BaseAdapter {

    private Context mContext;
    private List<RechargeDayCollectBean.DataBean.ListBean> mData;
    private final LayoutInflater mInflater;

    public RechargeDayCollectAdapter(Context mContext, List<RechargeDayCollectBean.DataBean.ListBean> mData) {
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

        MyViewHolder holder = null;
        if (convertView == null) {
            holder = new MyViewHolder();
            convertView = mInflater.inflate(R.layout.item_recharge_daily_detail, null);
            holder.tv_type = (TextView) convertView.findViewById(R.id.tv_type);
            holder.tv_real_get = (TextView) convertView.findViewById(R.id.tv_real_get);
            holder.tv_real_remove = (TextView) convertView.findViewById(R.id.tv_real_remove);
            holder.tv_profit = (TextView) convertView.findViewById(R.id.tv_profit);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        RechargeDayCollectBean.DataBean.ListBean listBean = mData.get(position);
        String category = listBean.getCategory();
        if (category.contains("合计")) {
            holder.tv_type.setTextSize(16.0f);
            holder.tv_real_get.setTextSize(16.0f);
            holder.tv_real_remove.setTextSize(16.0f);
            holder.tv_profit.setTextSize(16.0f);
            
            holder.tv_type.setTextColor(mContext.getResources().getColor(R.color.new_title_color));
            holder.tv_real_get.setTextColor(mContext.getResources().getColor(R.color.new_title_color));
            holder.tv_real_remove.setTextColor(mContext.getResources().getColor(R.color.new_title_color));
            holder.tv_profit.setTextColor(mContext.getResources().getColor(R.color.new_title_color));
            
            holder.tv_type.setText(category);
            holder.tv_real_get.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getPrice())));
            holder.tv_real_remove.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getAmount())));
            holder.tv_profit.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getRebate())));
        } else {
            holder.tv_type.setTextSize(14.0f);
            holder.tv_real_get.setTextSize(14.0f);
            holder.tv_real_remove.setTextSize(14.0f);
            holder.tv_profit.setTextSize(14.0f);

            holder.tv_type.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            holder.tv_real_get.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            holder.tv_real_remove.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            holder.tv_profit.setTextColor(mContext.getResources().getColor(R.color.gray_text));
            holder.tv_type.setText(category);
            holder.tv_real_get.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getPrice())));
            holder.tv_real_remove.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getAmount())));
            holder.tv_profit.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getRebate())));
        }
        return convertView;
    }

    private class MyViewHolder {
        TextView tv_type, tv_real_get, tv_real_remove, tv_profit;
    }
}
