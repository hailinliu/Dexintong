package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.personalcenter.bean.OrderBeanItem;

import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/2/15时间15:48
 * @描述：申请退货适配器
 */
public class TuiHuo2Adapter extends BaseAdapter {

    private Context mContext;
    private List<OrderBeanItem> list;
    private OrderBeanItem bean;

    public TuiHuo2Adapter(Context mContext, List<OrderBeanItem> list) {
        this.mContext = mContext;
        this.list = list;
    }

    public void setData(List<OrderBeanItem> list) {
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
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_tuihuo2_item, null);
            holder.good_image = (ImageView) view.findViewById(R.id.good_image);
            holder.name_tv = (TextView) view.findViewById(R.id.name_tv);
            holder.guige_tv = (TextView) view.findViewById(R.id.guige_tv);
            holder.danjia_tv = (TextView) view.findViewById(R.id.danjia_tv);
            holder.th_shuliang_tv = (TextView) view.findViewById(R.id.th_shuliang_tv);
            holder.th_jine_tv = (TextView) view.findViewById(R.id.th_jine_tv);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        bean = list.get(position);
//        Picasso.with(mContext)
//                .load(bean.getImage())
//                .transform(new HalfTransformation())
//                .placeholder(R.drawable.loading_default)
//                .error(R.mipmap.no_net_show_icon)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                .into(holder.good_image);

        holder.name_tv.setText(bean.getName());
        holder.guige_tv.setText(bean.getGuige());
        holder.danjia_tv.setText(bean.getDanjia());
        holder.th_shuliang_tv.setText(bean.getShuliang());
        holder.th_jine_tv.setText(String.valueOf(Integer.parseInt(bean.getShuliang()) * Integer.parseInt(bean.getDanjia())));
        return view;
    }

    static class ViewHolder {
        ImageView good_image;
        TextView name_tv;
        TextView guige_tv;
        TextView danjia_tv;
        TextView th_shuliang_tv;
        TextView th_jine_tv;
    }
}
