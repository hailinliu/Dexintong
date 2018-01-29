package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.personalcenter.bean.ApplyReplaceGoodsBean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/7time09:11
 * @detail：
 */

public class ApplyReplaceGoodsAdapter extends BaseAdapter {

    private Context mContext;
    private List<ApplyReplaceGoodsBean> mData;

    public ApplyReplaceGoodsAdapter(Context mContext, List<ApplyReplaceGoodsBean> mData) {

        this.mContext = mContext;
        this.mData = mData;
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(mContext).inflate(R.layout.item_apply_replace_goods, null);
            holder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.ll_edit_goosNumber = (LinearLayout) convertView.findViewById(R.id.ll_edit_goosNumber);
            holder.iv_add = (ImageView) convertView.findViewById(R.id.iv_add);
            holder.iv_reduce = (ImageView) convertView.findViewById(R.id.iv_reduce);
            holder.tv_edit_goods_number = (TextView) convertView.findViewById(R.id.tv_edit_goods_number);
            holder.tv_goods_number = (TextView) convertView.findViewById(R.id.tv_goods_number);

            holder.iv_goods_icon = (ImageView) convertView.findViewById(R.id.iv_goods_icon);//商品图片
            holder.tv_goods_standard = (TextView) convertView.findViewById(R.id.tv_goods_standard);//商品规格
            holder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);//商品价格
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final ApplyReplaceGoodsBean bean = mData.get(position);
//        holder.tv_goods_name.setText(bean.name);
//        if (bean.tag == 1) {
//            holder.ll_edit_goosNumber.setVisibility(View.GONE);
//        } else {
//            holder.ll_edit_goosNumber.setVisibility(View.VISIBLE);
//        }

        //点击加号
        holder.iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int goodsNumber = bean.goodsNumber;
//                if (goodsNumber < 55) {
//
//                    bean.goodsNumber = goodsNumber + 1;
//                    holder.tv_edit_goods_number.setText(String.valueOf(bean.goodsNumber));
//                } else {
//                    ((BaseCommonActivity) mContext).showToast("数量不能大于该订单总数量");
//                }
//                notifyDataSetChanged();
            }
        });
        //点击减号
        holder.iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                int goodsNumber = bean.goodsNumber;
//                if (goodsNumber > 0) {
//
//                    bean.goodsNumber = goodsNumber - 1;
//                    holder.tv_edit_goods_number.setText(String.valueOf(bean.goodsNumber));
//                } else {
//                    ((BaseCommonActivity) mContext).showToast("数量不能小于0");
//                }
//                notifyDataSetChanged();

            }
        });


        return convertView;
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    class ViewHolder {
        TextView tv_goods_name;
        LinearLayout ll_edit_goosNumber;
        ImageView iv_reduce, iv_add;
        TextView tv_edit_goods_number, tv_goods_number;
        ImageView iv_goods_icon;
        TextView tv_goods_standard, tv_goods_price;
    }
}
