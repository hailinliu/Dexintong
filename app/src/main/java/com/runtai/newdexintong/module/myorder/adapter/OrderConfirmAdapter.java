package com.runtai.newdexintong.module.myorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.myorder.bean.OrderConfirmBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/8.
 */

public class OrderConfirmAdapter extends SectionedBaseAdapter {

    private List<OrderConfirmBean.DataBean.ListBean> mData;
    private Context context;
    private OrderConfirmBean bean;
    private final LayoutInflater mInflater;

    public OrderConfirmAdapter(List<OrderConfirmBean.DataBean.ListBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
        mInflater = LayoutInflater.from(context);

    }

    @Override
    public Object getItem(int section, int position) {
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public int getSectionCount() {
        return mData.size();
    }

    @Override
    public int getCountForSection(int section) {
        return mData.get(section).getItems().size();
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_order_confirm, null);
            holder.item_goodsName = (TextView) convertView
                    .findViewById(R.id.item_goodsName);

            holder.img_shoppingCarItem = (ImageView) convertView
                    .findViewById(R.id.img_shoppingCarItem);
            holder.tv_spec_number = (TextView) convertView.findViewById(R.id.tv_spec_number);
            holder.tv_spec_unit = (TextView) convertView.findViewById(R.id.tv_spec_unit);
            holder.tv_piece_goods_price = (TextView) convertView.findViewById(R.id.tv_piece_goods_price);
            holder.tv_item_number = (TextView) convertView.findViewById(R.id.tv_item_number);
            holder.tv_item_price = (TextView) convertView.findViewById(R.id.tv_item_price);
            holder.tv_present = (TextView) convertView.findViewById(R.id.tv_present);
            holder.tv_isdirect = (TextView) convertView.findViewById(R.id.tv_isdirect);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OrderConfirmBean.DataBean.ListBean.ItemsBean itemsBean = mData.get(section).getItems().get(position);
        ImageLoader.getInstance().displayImage(itemsBean.getPhoto(), holder.img_shoppingCarItem,
                ImageLoadUtil.getDefaultHeadPicOptions(), null);
        holder.item_goodsName.setText(itemsBean.getItemName());
        holder.tv_spec_number.setText(String.valueOf(itemsBean.getSpec()));
        holder.tv_spec_unit.setText(itemsBean.getUnit());
        holder.tv_piece_goods_price.setText(StringUtil.strToDouble_new(String.valueOf(itemsBean.getOriginalPrice())));
        holder.tv_item_number.setText(String.valueOf(itemsBean.getBuyNum()));
        holder.tv_item_price.setText(StringUtil.strToDouble_new(String.valueOf(itemsBean.getBenefitLittleSum())));
        if (itemsBean.isIsDirect()) {
            //直配商品
            holder.tv_isdirect.setVisibility(View.VISIBLE);
        }else {
            holder.tv_isdirect.setVisibility(View.GONE);
        }
        if (itemsBean.isIsSend()) {
            //是赠品
            holder.tv_present.setVisibility(View.VISIBLE);
        } else {
            holder.tv_present.setVisibility(View.GONE);
        }
        return convertView;
    }

    private class ViewHolder {
        ImageView img_shoppingCarItem;
        TextView item_goodsName;
        TextView tv_spec_number, tv_spec_unit,
                tv_piece_goods_price, tv_item_number, tv_item_price;
        TextView tv_present;
        TextView tv_isdirect;

    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

        SectionHeaderViewHolder headerViewHolder = null;
        if (convertView == null) {
            headerViewHolder = new SectionHeaderViewHolder();
            convertView = mInflater.inflate(R.layout.layout_order_detail_sort_title, null);
            headerViewHolder.tv_sort_title = (TextView) convertView.findViewById(R.id.tv_sort_title);
            headerViewHolder.tv_sort_detail = (TextView) convertView.findViewById(R.id.tv_sort_detail);
            convertView.setTag(headerViewHolder);
        } else {
            headerViewHolder = (SectionHeaderViewHolder) convertView.getTag();
        }
        OrderConfirmBean.DataBean.ListBean listBean = mData.get(section);
        headerViewHolder.tv_sort_title.setText(listBean.getActivityName());
        
        if (listBean.getSpecialId() == 2) {
            //满赠专场
//            headerViewHolder.tv_sort_detail.setText("满"+listBean.get);
            headerViewHolder.tv_sort_detail.setText(listBean.getTipMsg());
        } else {
            headerViewHolder.tv_sort_detail.setText(listBean.getTipMsg());
        }
        return convertView;
    }

    private class SectionHeaderViewHolder {
        TextView tv_sort_detail, tv_sort_title;
    }

}
