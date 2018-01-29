package com.runtai.newdexintong.module.myorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.myorder.bean.ShoppingCarNoDataButtonBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/15.
 */
public class ShoppingCarNoDataButtonAdapter extends BaseAdapter {


    private Context context;
    private List<ShoppingCarNoDataButtonBean> mData;

    public ShoppingCarNoDataButtonAdapter(Context context, List<ShoppingCarNoDataButtonBean> mData) {
        this.context = context;
        this.mData = mData;
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
            convertView = LayoutInflater.from(context)
                    .inflate(R.layout.item_shoppingcar_nodata_special_button,null);
            holder.btn_goto_special = (Button) convertView.findViewById(R.id.btn_goto_special);
            convertView.setTag(holder);
        }else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.btn_goto_special.setText(mData.get(position).buttonname);
        return convertView;
    }
    
    private class ViewHolder {
        Button btn_goto_special;
    }
}
