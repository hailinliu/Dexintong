package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.personalcenter.bean.OrderAccountQueryBean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/11/12time16:49
 * @detail：
 */

public class OrderAccountQueryAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderAccountQueryBean.DataBean.ListBean> mData;
    private final LayoutInflater mInflater;

    public OrderAccountQueryAdapter(Context mContext, List<OrderAccountQueryBean.DataBean.ListBean> mData) {
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
            convertView = mInflater.inflate(R.layout.item_account_query, null);
            holder.tv_deal_time = (TextView) convertView.findViewById(R.id.tv_deal_time);
            holder.tv_deal_type = (TextView) convertView.findViewById(R.id.tv_deal_type);
            holder.tv_income = (TextView) convertView.findViewById(R.id.tv_income);
            holder.tv_expanse = (TextView) convertView.findViewById(R.id.tv_expanse);
            holder.tv_account_balance = (TextView) convertView.findViewById(R.id.tv_account_balance);
            holder.tv_remark = (TextView) convertView.findViewById(R.id.tv_remark);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        OrderAccountQueryBean.DataBean.ListBean listBean = mData.get(position);
        holder.tv_deal_time.setText(listBean.getTransTime());
        holder.tv_deal_type.setText(listBean.getTransType());
        holder.tv_income.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getInCome())));
        holder.tv_expanse.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getExpense())));
        holder.tv_account_balance.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getBalance())));
        holder.tv_remark.setText(listBean.getRemark());
        return convertView;
    }

    private class MyViewHolder {
        TextView tv_deal_time,tv_deal_type,tv_income,tv_expanse,tv_account_balance ;
        TextView tv_remark;
        
    }
}
