package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.LoginSelectCityBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/5.
 */
public class LoginSelectCityAdapter extends BaseAdapter {


    private Context context;
    private List<LoginSelectCityBean> mDatas;
    private final LayoutInflater mInflater;

    public LoginSelectCityAdapter(Context context, List<LoginSelectCityBean> mDatas) {
        
        this.context = context;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
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
            convertView = mInflater.inflate(R.layout.item_login_select_city, null);
            holder.tv_cityName = (TextView) convertView.findViewById(R.id.tv_cityName);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        LoginSelectCityBean bean = mDatas.get(position);
        holder.tv_cityName.setText(bean.getName());

        return convertView;
    }
    
    public class ViewHolder {
        TextView tv_cityName;
    }
}
