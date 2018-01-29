package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.ReductionMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
public class ReductionMessageAdapter extends BaseAdapter {

    private Context context;
    private List<ReductionMessageBean> mDatas;
    private final LayoutInflater mInflater;

    public ReductionMessageAdapter(Context context, List<ReductionMessageBean> data) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mDatas = data;
    }
    
    @Override
    public int getCount() {
        return mDatas.size();
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
            convertView = mInflater.inflate(R.layout.item_reduction_message, null);
            holder.tv_reduction_msg_time = (TextView) convertView.findViewById(R.id.tv_reduction_msg_time);
            holder.tv_reduction_msg_detail = (TextView) convertView.findViewById(R.id.tv_reduction_msg_detail);
            holder.iv_reduction_message_icon = (ImageView) convertView.findViewById(R.id.iv_reduction_message_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    
    class ViewHolder {
        ImageView iv_reduction_message_icon;
        TextView tv_reduction_msg_detail,tv_reduction_msg_time;
    }
    
}
