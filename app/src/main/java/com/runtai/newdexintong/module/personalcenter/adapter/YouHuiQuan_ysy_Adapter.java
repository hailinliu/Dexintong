package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.personalcenter.bean.MyCouponBean;

import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/3/29时间08:40
 * @描述：优惠券适配器
 */
public class YouHuiQuan_ysy_Adapter extends BaseAdapter {

    private Context mContext;
    private List<MyCouponBean.DataBean.ListBean> list;
    private MyCouponBean.DataBean.ListBean bean;

    public YouHuiQuan_ysy_Adapter(Context mContext, List<MyCouponBean.DataBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_youhuiquan_ysy, null);
            holder.yhq_jine = (TextView) view.findViewById(R.id.yhq_jine);
            holder.yhq_xiane = (TextView) view.findViewById(R.id.yhq_xiane);
            holder.tv_coupon_name = (TextView) view.findViewById(R.id.tv_coupon_name);
            holder.yhq_state = (TextView) view.findViewById(R.id.yhq_state);
            holder.yhq_bianhao = (TextView) view.findViewById(R.id.yhq_bianhao);
            holder.yhq_shiyong = (Button) view.findViewById(R.id.yhq_shiyong);
            holder.tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
            holder.tv_limit_time = (TextView) view.findViewById(R.id.tv_limit_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        bean = list.get(position);

        holder.yhq_jine.setText(String.valueOf(bean.getDenomination()));
        holder.yhq_xiane.setText(String.valueOf(bean.getLimitMoney()));
        holder.tv_coupon_name.setText(bean.getName());
        holder.yhq_bianhao.setText(String.valueOf(bean.getId()));
//        holder.tv_start_time.setText(MyDateUtils.getStringDateDay(bean.getCreateTime()));
        holder.tv_limit_time.setText(bean.getExpiryDate());
        return view;
    }

    static class ViewHolder {
        TextView yhq_jine;
        TextView yhq_xiane;
        TextView tv_coupon_name;
        TextView yhq_state;
        TextView yhq_bianhao;
        Button yhq_shiyong;
        TextView tv_start_time, tv_limit_time;
    }

}
