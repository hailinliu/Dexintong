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
import com.runtai.newdexintong.module.personalcenter.bean.ReturnGoodsBean;

import java.util.List;

/**
 * @作者：rhf
 * @日期：2017/3/29时间15:53
 * @描述：退货对应的适配器
 */

public class ReturnGoodsAdapter extends BaseAdapter {

    private Context mContext;
    private List<ReturnGoodsBean.DataBean.ListBean> list;
    private String point_msg;
    private final LayoutInflater mInflater;

    public ReturnGoodsAdapter(Context mContext, List<ReturnGoodsBean.DataBean.ListBean> list) {
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
            convertView = mInflater.inflate(R.layout.item_return_goods, null);
            //调退商品
            holder.iv_pre_sale_goods_pic = (ImageView) convertView.findViewById(R.id.iv_pre_sale_goods_pic);
            holder.tv_return_goods_name = (TextView) convertView.findViewById(R.id.tv_return_goods_name);
            holder.tv_return_goods_spec_number = (TextView) convertView.findViewById(R.id.tv_return_goods_spec_number);
            holder.tv_return_goods_spec_unit = (TextView) convertView.findViewById(R.id.tv_return_goods_spec_unit);
            holder.tv_return_goods_single_piece_price = (TextView) convertView.findViewById(R.id.tv_return_goods_single_piece_price);
            holder.tv_return_item_price = (TextView) convertView.findViewById(R.id.tv_return_item_price);
            holder.tv_replace_return_goods_status = (TextView) convertView.findViewById(R.id.tv_replace_return_goods_status);
            holder.tv_return_number = (TextView) convertView.findViewById(R.id.tv_return_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ReturnGoodsBean.DataBean.ListBean listBean = list.get(position);
        holder.tv_return_goods_name.setText(listBean.getItemName());
        ImageLoader.getInstance().displayImage(listBean.getPhoto(),holder.iv_pre_sale_goods_pic, 
                ImageLoadUtil.getDefaultHeadPicOptions(),null);
        holder.tv_return_goods_spec_number.setText(String.valueOf(listBean.getSpec()));
        holder.tv_return_goods_spec_unit.setText(listBean.getUnit());
        holder.tv_replace_return_goods_status.setText(listBean.getStatusDes());
        holder.tv_return_goods_single_piece_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getBenefitPrice())));
        holder.tv_return_number.setText(String.valueOf(listBean.getReturnNum()));
        double itemTotalPrice = listBean.getBenefitPrice() * listBean.getReturnNum();
        holder.tv_return_item_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getReturnMoney())));

        return convertView;
    }


    private class ViewHolder {

        //调退商品
        ImageView iv_pre_sale_goods_pic;
        TextView tv_return_goods_name, tv_return_goods_spec_number, tv_return_goods_spec_unit;
        TextView tv_return_goods_single_piece_price, tv_return_item_price;
        TextView tv_replace_return_goods_status;
        TextView tv_return_number;
    }

}
