package com.runtai.newdexintong.module.fenlei.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.module.fenlei.bean.SortLeftDataBean;

import java.util.List;

public class SortLeftListAdapter extends BaseAdapter {

    Context context;
    List<SortLeftDataBean.DataBean.LeftBean> list;

    private int GRAY = 0XCCF0F0F0;
    private int WHITE = 0XCCFFFFFF;

    public SortLeftListAdapter(Context context, List<SortLeftDataBean.DataBean.LeftBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list == null ? 0 : list.size();
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
    public View getView(int index, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.ceshi_text, null);
            holder.text = (TextView) view.findViewById(R.id.item_text);
            holder.item_view = (TextView) view.findViewById(R.id.item_view);
            holder.view_right_vetail = view.findViewById(R.id.view_right_vetail);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        if (index == defaultSelection) {
            holder.text.setTextColor(context.getResources().getColor(R.color.new_title_color));
//            holder.item_view.setVisibility(View.VISIBLE);
            holder.text.setBackgroundColor(context.getResources().getColor(R.color.entry_backgroud_color));
            holder.view_right_vetail.setVisibility(View.GONE);
        } else {
            holder.text.setTextColor(context.getResources().getColor(R.color.black_text));
//            holder.item_view.setVisibility(View.GONE);
            holder.text.setBackgroundColor(context.getResources().getColor(R.color.white));
            holder.view_right_vetail.setVisibility(View.VISIBLE);
        }
        SortLeftDataBean.DataBean.LeftBean leftBean = list.get(index);
        holder.text.setText(leftBean.getCateName());
        if (index == 0) {
            SPUtils.putInt(context,"left_default_value",leftBean.getId());
        }
        return view;
    }

    class ViewHolder {
        TextView text;
        TextView item_view;
        View view_right_vetail;
    }

    private int defaultSelection = -1;

    public void setSelectPosition(int position) {

        defaultSelection = position;
        notifyDataSetChanged();
       
    }

}
