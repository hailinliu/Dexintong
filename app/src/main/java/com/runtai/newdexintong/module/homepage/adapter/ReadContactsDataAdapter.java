package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/31time19:26
 * @detail：读取联系人列表对应的适配器
 */

public class ReadContactsDataAdapter extends BaseAdapter {


    private Context context;
    private List<String> mData;
    private String contact_name;

    public ReadContactsDataAdapter(Context context, List<String> mData,String contact_name) {
        this.context = context;
        this.mData = mData;
        this.contact_name = contact_name;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
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
            convertView = LinearLayout.inflate(context,
                    R.layout.item_dialog_contacts, null);
            holder.name = (TextView) convertView.findViewById(R.id.name);
            holder.number = (TextView) convertView
                    .findViewById(R.id.number);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        holder.name.setText("(" + contact_name + ")");
        holder.number.setText(mData.get(position).toString());

        return convertView;
    }

    class ViewHolder {
        TextView name, number;
    }
}
