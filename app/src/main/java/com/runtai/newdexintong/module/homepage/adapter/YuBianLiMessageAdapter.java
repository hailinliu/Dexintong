package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.YuBianLiMessageBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/31.
 */
public class YuBianLiMessageAdapter extends BaseAdapter {

    private Context context;
    private List<YuBianLiMessageBean> mDatas;
    private final LayoutInflater mInflater;

    public YuBianLiMessageAdapter(Context context, List<YuBianLiMessageBean> data) {
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
            convertView = mInflater.inflate(R.layout.item_yubianli_message, null);
            holder.tv_yubianli_msg_time = (TextView) convertView.findViewById(R.id.tv_yubianli_msg_time);
            holder.tv_yubianli_msg_detail = (TextView) convertView.findViewById(R.id.tv_yubianli_msg_detail);
            holder.iv_yubianli_message_icon = (ImageView) convertView.findViewById(R.id.iv_yubianli_message_icon);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        return convertView;
    }
    
    class ViewHolder {
        ImageView iv_yubianli_message_icon;
        TextView tv_yubianli_msg_detail,tv_yubianli_msg_time;
    }
}
