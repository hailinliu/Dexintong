package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.personalcenter.bean.ReplaceGoodsBean;

import java.util.List;

/**
 * @作者：rhf
 * @日期：2017/3/29时间15:53
 * @描述：调货对应的适配器
 */

public class ReplaceGoodsAdapter extends BaseAdapter {

    private Context mContext;
    private List<ReplaceGoodsBean.DataBean.ListBean> list;
    private String point_msg;
    private final LayoutInflater mInflater;

    public ReplaceGoodsAdapter(Context mContext, List<ReplaceGoodsBean.DataBean.ListBean> list) {
        this.mContext = mContext;
        this.list = list;
        mInflater = LayoutInflater.from(mContext);
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
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_replace_goods, null);
            //调退商品
            holder.iv_pre_sale_goods_pic = (ImageView) convertView.findViewById(R.id.iv_pre_sale_goods_pic);
            holder.tv_return_goods_name = (TextView) convertView.findViewById(R.id.tv_return_goods_name);
            holder.tv_return_goods_spec_number = (TextView) convertView.findViewById(R.id.tv_return_goods_spec_number);
            holder.tv_return_goods_spec_unit = (TextView) convertView.findViewById(R.id.tv_return_goods_spec_unit);
            holder.tv_return_goods_single_piece_price = (TextView) convertView.findViewById(R.id.tv_return_goods_single_piece_price);
            holder.tv_return_item_price = (TextView) convertView.findViewById(R.id.tv_return_item_price);
            holder.tv_replace_return_goods_status = (TextView) convertView.findViewById(R.id.tv_replace_return_goods_status);
            holder.tv_return_number = (TextView) convertView.findViewById(R.id.tv_return_number);
            holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);

            //调送商品
            holder.iv_delivery_pic = (ImageView) convertView.findViewById(R.id.iv_delivery_pic);
            holder.tv_deliver_goods_name = (TextView) convertView.findViewById(R.id.tv_deliver_goods_name);
            holder.tv_deliver_goods_number = (TextView) convertView.findViewById(R.id.tv_deliver_goods_number);
            holder.tv_delivery_spec_number = (TextView) convertView.findViewById(R.id.tv_delivery_spec_number);
            holder.tv_delivery_spec_unit = (TextView) convertView.findViewById(R.id.tv_delivery_spec_unit);
            holder.tv_item_price = (TextView) convertView.findViewById(R.id.tv_item_price);
            holder.tv_deliver_single_piece_price = (TextView) convertView.findViewById(R.id.tv_deliver_single_piece_price);
            holder.tv_status = (TextView) convertView.findViewById(R.id.tv_status);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReplaceGoodsBean.DataBean.ListBean listBean = list.get(position);
        holder.tv_return_goods_name.setText(listBean.getItemName());
        ImageLoader.getInstance().displayImage(listBean.getPhoto(),holder.iv_pre_sale_goods_pic, 
                ImageLoadUtil.getDefaultHeadPicOptions(),null);
        holder.tv_return_goods_spec_number.setText(String.valueOf(listBean.getSpec()));
        holder.tv_return_goods_spec_unit.setText(listBean.getUnit());
        holder.tv_replace_return_goods_status.setText(listBean.getStatusDes());
        holder.tv_return_goods_single_piece_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getBenefitPrice())));
        holder.tv_return_number.setText(String.valueOf(listBean.getReturnNum()));
        holder.tv_return_item_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getReturnMoney())));

        ImageLoader.getInstance().displayImage(listBean.getRsPhoto(),holder.iv_delivery_pic,
                ImageLoadUtil.getDefaultHeadPicOptions(),null);
        holder.tv_deliver_goods_name.setText(listBean.getRsItemName());
        holder.tv_delivery_spec_number.setText(String.valueOf(listBean.getRspec()));
        holder.tv_deliver_goods_number.setText(String.valueOf(listBean.getBuyNum()));
        holder.tv_delivery_spec_unit.setText(listBean.getRsUnit());
        holder.tv_deliver_single_piece_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getPrice())));
        holder.tv_item_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getDifference())));
        holder.tv_status.setText(listBean.getRstatusDes());

        return convertView;
    }


    private class ViewHolder {

        //调退商品
        ImageView iv_pre_sale_goods_pic;
        TextView tv_return_goods_name, tv_return_goods_spec_number, tv_return_goods_spec_unit;
        TextView tv_return_goods_single_piece_price, tv_return_item_price;
        TextView tv_replace_return_goods_status;
        TextView tv_return_number;
        TextView tv_order_time;

        //调换商品
        ImageView iv_delivery_pic;
        TextView tv_deliver_goods_name, tv_deliver_goods_number;
        TextView tv_delivery_spec_number, tv_delivery_spec_unit;
        TextView tv_item_price, tv_deliver_single_piece_price;
        TextView tv_status;
    }

}
