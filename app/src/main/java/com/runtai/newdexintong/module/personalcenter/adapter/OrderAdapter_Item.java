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
 * @日期：2017/3/29时间15:53
 * @描述：
 */

public class OrderAdapter_Item extends BaseAdapter {

    private Context mContext;
    private List<OrderBeanItem> list;

    public OrderAdapter_Item(Context mContext, List<OrderBeanItem> list) {
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
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.item_order_detail, null);
            holder.item_order_iv = (ImageView) view.findViewById(R.id.item_order_iv);//图片
            holder.item_order_name = (TextView) view.findViewById(R.id.item_order_name);//名称
            holder.item_order_guige = (TextView) view.findViewById(R.id.item_order_guige);//规格
            holder.item_order_danjia = (TextView) view.findViewById(R.id.item_order_danjia);//单价
            holder.item_order_shuliang = (TextView) view.findViewById(R.id.item_order_shuliang);//数量
            holder.item_order_zonge = (TextView) view.findViewById(R.id.item_order_zonge);//总额

            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }

        OrderBeanItem bean = list.get(position);
//        /*商品图片*/
//        Picasso.with(mContext)
//                .load(bean.getImage())
//                .transform(new HalfTransformation())
//                .placeholder(R.drawable.loading_default)
//                .error(R.mipmap.no_net_show_icon)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                .into(holder.item_order_iv);
        /*商品名称*/
        holder.item_order_name.setText(bean.getName());
        /*商品规格*/
        holder.item_order_guige.setText(bean.getGuige());
        /*商品单价*/
        holder.item_order_danjia.setText(bean.getDanjia());
        /*商品数量*/
        holder.item_order_shuliang.setText(bean.getShuliang());
        /*商品总额*/
        holder.item_order_zonge.setText(bean.getZonge());
        return view;
    }

    static class ViewHolder {
        ImageView item_order_iv;//图片
        TextView item_order_name;//名称
        TextView item_order_guige;//规格
        TextView item_order_danjia;//单价
        TextView item_order_shuliang;//数量
        TextView item_order_zonge;//总额
    }

}
