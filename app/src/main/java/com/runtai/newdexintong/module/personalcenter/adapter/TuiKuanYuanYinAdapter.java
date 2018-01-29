package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.personalcenter.bean.OrderBeanItem;

import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/2/15时间15:48
 * @描述：申请退货适配器
 */
public class TuiKuanYuanYinAdapter extends BaseAdapter {

    private Context mContext;
    private List<String> list;
    private OrderBeanItem bean;

    public TuiKuanYuanYinAdapter(Context mContext, List<String> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setData(List<String> list) {
        this.list = list;
        notifyDataSetChanged();
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
        final ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_tuikuanyuanyin, null);
            holder.item_tkyy_tv = (TextView) view.findViewById(R.id.item_tkyy_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        holder.item_tkyy_tv.setText(list.get(position));
        return view;
    }

    static class ViewHolder {
        TextView item_tkyy_tv;
    }

}
