package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.personalcenter.bean.AddMoneyRecordBean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/11/12time16:49
 * @detail：
 */

public class AddMoneyRecordAdapter extends BaseAdapter {

    private Context mContext;
    private List<AddMoneyRecordBean.DataBean.ListBean> mData;
    private final LayoutInflater mInflater;

    public AddMoneyRecordAdapter(Context mContext, List<AddMoneyRecordBean.DataBean.ListBean> mData) {
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
            convertView = mInflater.inflate(R.layout.item_add_money_record, null);
            holder.tv_add_type = (TextView) convertView.findViewById(R.id.tv_add_type);
            holder.tv_account_type = (TextView) convertView.findViewById(R.id.tv_account_type);
            holder.tv_add_money = (TextView) convertView.findViewById(R.id.tv_add_money);
            holder.tv_charge_money = (TextView) convertView.findViewById(R.id.tv_charge_money);

            holder.tv_fanli = (TextView) convertView.findViewById(R.id.tv_fanli);
            holder.tv_fanli_type = (TextView) convertView.findViewById(R.id.tv_fanli_type);
            holder.tv_commit_time = (TextView) convertView.findViewById(R.id.tv_commit_time);
            holder.tv_complete_time = (TextView) convertView.findViewById(R.id.tv_complete_time);
            holder.tv_beizhu_time = (TextView) convertView.findViewById(R.id.tv_beizhu_time);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }

        AddMoneyRecordBean.DataBean.ListBean listBean = mData.get(position);
        int accountType = listBean.getAccountType();
        if (accountType == 0) {
            holder.tv_account_type.setText("充值账户");
        } else {
            holder.tv_account_type.setText("订货账户");
        }

        holder.tv_add_type.setText(listBean.getDepositType());
        holder.tv_add_money.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getAmount())));
        holder.tv_charge_money.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getFee())));
        holder.tv_fanli.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getRebate())));
        if (listBean.isRebateIsCash()) {

            holder.tv_fanli_type.setText("现金");
        }else {
            holder.tv_fanli_type.setText("代金劵");
        }
        holder.tv_commit_time.setText(listBean.getCreateTime());
        holder.tv_complete_time.setText(listBean.getArriveTime());
        holder.tv_beizhu_time.setText(listBean.getRemark());
        holder.tv_status.setText(listBean.getStatus());
        return convertView;
    }

    private class MyViewHolder {
        TextView tv_add_type, tv_account_type, tv_add_money, tv_charge_money;
        TextView tv_fanli, tv_fanli_type, tv_commit_time, tv_complete_time, tv_beizhu_time, tv_status;
    }
}
