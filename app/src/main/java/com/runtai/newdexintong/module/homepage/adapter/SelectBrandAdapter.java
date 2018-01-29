package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.SearchResultBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/6.
 * 搜索结果页面中点击品牌选择弹出popuwindow中数据对应的适配器
 */
public class SelectBrandAdapter extends BaseAdapter {

    private Context context;
    private List<SearchResultBean.DataBean.BrandsBean> mData;
    private final LayoutInflater mInflater;
    private int defaultSelection = -1;

    public SelectBrandAdapter(Context context, List<SearchResultBean.DataBean.BrandsBean> mData) {

        this.context = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_select_brand, null);
            holder.tv_brand_name = (TextView) convertView.findViewById(R.id.tv_brand_name);
            holder.ll_brand_name = (LinearLayout) convertView.findViewById(R.id.ll_brand_name);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        if (position == defaultSelection) {
            holder.tv_brand_name.setEnabled(true);
            holder.ll_brand_name.setEnabled(true);
        } else {
            holder.tv_brand_name.setEnabled(false);
            holder.ll_brand_name.setEnabled(false);
        }
        SearchResultBean.DataBean.BrandsBean brandsBean = mData.get(position);
        holder.tv_brand_name.setText(brandsBean.getBrand());
        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        TextView tv_brand_name;
        LinearLayout ll_brand_name;
    }

    

    public void setSelectPosition(int position) {
        if (!(position < 0 || position > mData.size())) {
            defaultSelection = position;
            notifyDataSetChanged();
        }
    }

}
