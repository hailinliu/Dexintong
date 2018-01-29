package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.MobilePackageListBean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/8/1time15:00
 * @detail：
 */

public class SelectPackageAdapter extends BaseAdapter {


    private Context context;
    private List<MobilePackageListBean.DataBean> mData;
    private final LayoutInflater mInflater;

    public SelectPackageAdapter(Context context, List<MobilePackageListBean.DataBean> mData) {
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
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_select_denomination, null);
            holder.tv_demomination = (TextView) convertView.findViewById(R.id.tv_demomination);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        MobilePackageListBean.DataBean bean = mData.get(position);
        holder.tv_demomination.setText(bean.getName());

        return convertView;
    }

    public class ViewHolder {
        TextView tv_demomination;
    }
}
