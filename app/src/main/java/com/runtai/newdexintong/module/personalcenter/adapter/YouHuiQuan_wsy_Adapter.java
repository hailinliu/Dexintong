package com.runtai.newdexintong.module.personalcenter.adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.homepage.activity.SpecialListActivity;
import com.runtai.newdexintong.module.personalcenter.bean.MyCouponBean;

import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/3/29时间08:40
 * @描述：优惠券适配器
 */
public class YouHuiQuan_wsy_Adapter extends BaseAdapter {

    private Context mContext;
    private List<MyCouponBean.DataBean.ListBean> list;
    private MyCouponBean.DataBean.ListBean bean;

    public YouHuiQuan_wsy_Adapter(Context mContext, List<MyCouponBean.DataBean.ListBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_youhuiquan_wsy, null);
            holder.tv_coupon_money = (TextView) view.findViewById(R.id.tv_coupon_money);
            holder.tv_limit_money = (TextView) view.findViewById(R.id.tv_limit_money);
            holder.yhq_xianzhi = (TextView) view.findViewById(R.id.yhq_xianzhi);
            holder.yhq_state = (TextView) view.findViewById(R.id.yhq_state);
            holder.yhq_bianhao = (TextView) view.findViewById(R.id.yhq_bianhao);
            holder.yhq_shiyong = (TextView) view.findViewById(R.id.yhq_shiyong);
//            holder.tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
            holder.tv_limit_time = (TextView) view.findViewById(R.id.tv_limit_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        bean = list.get(position);

        holder.tv_coupon_money.setText(String.valueOf(bean.getDenomination()));
        holder.tv_limit_money.setText(String.valueOf(bean.getLimitMoney()));
        holder.yhq_xianzhi.setText(bean.getName());
       
//        holder.tv_start_time.setText(MyDateUtils.getStringDateDay(bean.getCreateTime()));
        holder.tv_limit_time.setText(bean.getExpiryDate());
        holder.yhq_state.setText("未使用");
        holder.yhq_bianhao.setText(String.valueOf(bean.getId()));
        final int specialPerformanceId = bean.getSpecialPerformanceId();
        holder.yhq_shiyong.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (specialPerformanceId != -1) {
                    Intent intent_special_area = new Intent(mContext, SpecialListActivity.class);
                    intent_special_area.putExtra("mUrl", "api/promotion/list");
                    intent_special_area.putExtra("paramName0", "SpecialId");
                    intent_special_area.putExtra("paramValue0", String.valueOf(specialPerformanceId));
                    ((BaseCommonActivity)mContext).startActivityByIntent(intent_special_area);
                    ((Activity) mContext).finish();
                } else {
                    Intent intent = new Intent(mContext, MainActivity.class);
                    intent.putExtra("tag","sort");
                    ((BaseCommonActivity)mContext).startActivityByIntent(intent);
                    ((Activity) mContext).finish();
                } 
            
            }
        });
        return view;
    }

    static class ViewHolder {
        TextView tv_coupon_money;
        TextView tv_limit_money;
        TextView yhq_xianzhi;
        TextView yhq_state;
        TextView yhq_bianhao;
        TextView yhq_shiyong;
        TextView tv_start_time,tv_limit_time;
    }

}
