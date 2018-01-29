package com.runtai.newdexintong.module.fenlei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;

import java.util.List;

public class FenleiShaiXuanAdapter extends BaseAdapter {
    private List<String> list;
    private Context context;

    public FenleiShaiXuanAdapter(List<String> list, Context context) {
        super();
        this.list = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int arg0) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int arg0) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public View getView(int index, View view, ViewGroup arg2) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.pop_menuitem, null);
            holder.show_tv = (TextView) view.findViewById(R.id.item_text_grid);
            holder.item_text_ll = (LinearLayout) view.findViewById(R.id.item_text_ll);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        if (index == defaultSelection) {
//            holder.show_tv.setTextColor(context.getResources().getColor(R.color.white));
//            holder.item_text_ll.setBackground(context.getResources().getDrawable(R.drawable.chaoshi_gv_bg_color));
            holder.show_tv.setEnabled(true);
            holder.item_text_ll.setEnabled(true);
        } else{
//            holder.show_tv.setTextColor(context.getResources().getColor(R.color.black));
//            holder.item_text_ll.setBackground(context.getResources().getDrawable(R.drawable.chaoshi_tv_bg));
            holder.show_tv.setEnabled(false);
            holder.item_text_ll.setEnabled(false);
        }

        holder.show_tv.setText(list.get(index));
        return view;
    }

    class ViewHolder {
        TextView show_tv;
        LinearLayout item_text_ll;
    }

    private int defaultSelection = -1;
    public void setSelectPosition(int position) {
        if (!(position < 0 || position > list.size())) {
            defaultSelection = position;
            notifyDataSetChanged();
        }
    }
}
