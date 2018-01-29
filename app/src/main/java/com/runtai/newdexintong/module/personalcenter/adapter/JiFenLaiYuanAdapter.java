package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.personalcenter.bean.PointsOriginDataBean;

import java.util.List;

/**
 * @作者：rhf
 * @日期：2017/3/29时间08:40
 * @描述：积分记录--积分来源
 */

public class JiFenLaiYuanAdapter extends BaseAdapter {

    private Context mContext;
    private List<PointsOriginDataBean.DataBean.ListBean> list;
    private PointsOriginDataBean.DataBean.ListBean bean;

    public JiFenLaiYuanAdapter(Context mContext, List<PointsOriginDataBean.DataBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
    }
    
    public void setData(List<PointsOriginDataBean.DataBean.ListBean> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_jifenjilu_laiyuan, null);
            holder.jifenjilu_laiyuan_tv = (TextView) view.findViewById(R.id.jifenjilu_laiyuan_tv);
            holder.jifenjilu_laiyuan_time = (TextView) view.findViewById(R.id.jifenjilu_laiyuan_time);
            holder.jifenjilu_laiyuan_jifen = (TextView) view.findViewById(R.id.jifenjilu_laiyuan_jifen);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        bean = list.get(position);

        holder.jifenjilu_laiyuan_tv.setText(bean.getRemark());
        holder.jifenjilu_laiyuan_time.setText(bean.getTransTime());
        holder.jifenjilu_laiyuan_jifen.setText(String.valueOf(bean.getIncome()));
        return view;
    }

    static class ViewHolder {
        TextView jifenjilu_laiyuan_tv;//积分来源
        TextView jifenjilu_laiyuan_time;//获取时间
        TextView jifenjilu_laiyuan_jifen;//积分数量
    }

}
