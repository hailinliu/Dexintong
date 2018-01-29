package com.runtai.newdexintong.module.myorder.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.myorder.bean.OrderConfirmBean;

import java.util.List;

/**
 * Created by Administrator on 2017/2/15.
 * 选择优惠劵界面对应的适配器
 */

public class SelectCouponAdapter extends BaseAdapter {

    List<OrderConfirmBean.DataBean.CouponBean> mDatas;
    private Context context;
    private int temp = -1;

    public SelectCouponAdapter(Context context, List<OrderConfirmBean.DataBean.CouponBean> list) {
        this.mDatas = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return mDatas.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView,
                        final ViewGroup parent) {
        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_select_youhuiquan, null);
            holder.tv_coupon_number = (TextView) convertView.findViewById(R.id.tv_coupon_number);
            holder.tv_coupon_detail = (TextView) convertView.findViewById(R.id.tv_coupon_detail);
            holder.tv_state = (TextView) convertView.findViewById(R.id.tv_state);
            holder.tv_coupon_value = (TextView) convertView.findViewById(R.id.tv_coupon_value);
            holder.checkbox = (CheckBox) convertView.findViewById(R.id.checkbox);
            holder.tv_limit_money = (TextView) convertView.findViewById(R.id.tv_limit_money);
//            holder.tv_start_time = (TextView) convertView.findViewById(R.id.tv_start_time);
            holder.tv_limit_time = (TextView) convertView.findViewById(R.id.tv_limit_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final OrderConfirmBean.DataBean.CouponBean listBean = mDatas.get(0);
        holder.tv_coupon_value.setText(String.valueOf(listBean.getCouponBenefit()));
        holder.tv_limit_money.setText(String.valueOf(listBean.getCouponLimit()));
        holder.tv_coupon_detail.setText(listBean.getCouponName());
        holder.tv_coupon_number.setText(String.valueOf(listBean.getCouponId()));
//        holder.tv_start_time.setText(MyDateUtils.getStringDateDay(listBean.getCreateTime()));
        holder.tv_limit_time.setText(listBean.getCouponExpiry());
        holder.checkbox.setId(position);
        holder.checkbox.setChecked(listBean.isSelected);
//        holder.checkbox.setOnCheckedChangeListener(
//                new CompoundButton.OnCheckedChangeListener() {
//                    @Override
//                    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
//                        if (isChecked) {
//                            if (temp != -1) {
//                                //找到上次点击的checkbox,并把它设置为false,对重新选择时可以将以前的关掉      
//                                CheckBox tempCheckBox = (CheckBox) ((SelectCouponActivity) context).findViewById(temp);
//                                if (tempCheckBox != null) {
//                                    tempCheckBox.setChecked(false);
//                                    
//                                }
//                            }
//                            temp = buttonView.getId();//保存当前选中的checkbox的id值
//                        }
//                    }
//                }
//        );
//
//        if (position == temp) {//比对position和当前的temp是否一致  
//            holder.checkbox.setChecked(true);
//            
//        } else {
//            holder.checkbox.setChecked(false);
//        }

        return convertView;
    }

    public class ViewHolder {
        public TextView tv_coupon_number, tv_coupon_detail, tv_state, tv_coupon_value;
        public CheckBox checkbox;
        TextView tv_limit_money;
        TextView tv_limit_time;
    }
}
