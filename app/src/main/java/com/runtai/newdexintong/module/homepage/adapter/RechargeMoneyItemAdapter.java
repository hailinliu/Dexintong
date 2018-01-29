package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.RechargeMoneyItemBean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/31time18:40
 * @detail：
 */

public class RechargeMoneyItemAdapter extends BaseAdapter {


    private Context context;
    private List<RechargeMoneyItemBean> mData;
    private final LayoutInflater mInflater;

    public RechargeMoneyItemAdapter(Context context, List<RechargeMoneyItemBean> mData) {
        this.context = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.item_recharge_money, null);
            holder = new ViewHolder();
            holder.tv_denomination = (TextView) convertView.findViewById(R.id.tv_denomination);
            holder.tv_sale_money = (TextView) convertView.findViewById(R.id.tv_sale_money);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        RechargeMoneyItemBean rechargeMoneyItemBean = mData.get(position);
        holder.tv_denomination.setText(rechargeMoneyItemBean.denomination);
        return convertView;
    }

    private class ViewHolder {

        TextView tv_denomination, tv_sale_money;

    }

}
