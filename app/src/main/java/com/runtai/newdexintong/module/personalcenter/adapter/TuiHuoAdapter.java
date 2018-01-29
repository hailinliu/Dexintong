package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.personalcenter.bean.OrderBeanItem;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/2/15时间15:48
 * @描述：申请退货适配器
 */
public class TuiHuoAdapter extends BaseAdapter {

    private Context mContext;
    private List<OrderBeanItem> list;
    private OrderBeanItem bean;
    private boolean isHide;
    private List<String> Raw_list;//原始数据

    public TuiHuoAdapter(Context mContext, List<OrderBeanItem> list, boolean isHide) {
        this.mContext = mContext;
        this.list = list;
        this.isHide = isHide;

        Raw_list = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            Raw_list.add(list.get(i).getShuliang());
        }
    }

    public void setData(List<OrderBeanItem> list, boolean isHide) {
        this.list = list;
        this.isHide = isHide;
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
            view = LayoutInflater.from(mContext).inflate(R.layout.item_shoppingcar, null);
            holder.cb_shopcar = (CheckBox) view.findViewById(R.id.cb_shopcar);
            holder.img_shoppingCarItem = (ImageView) view.findViewById(R.id.img_shoppingCarItem);
            holder.item_jian = (ImageView) view.findViewById(R.id.item_jian);
            holder.item_jia = (ImageView) view.findViewById(R.id.item_jia);
            holder.ll_edit_goosNumber = (LinearLayout) view.findViewById(R.id.ll_edit_goosNumber);
            holder.item_goodsNum = (TextView) view.findViewById(R.id.item_goodsNum);
            
            holder.item_goodsStanded = (TextView) view.findViewById(R.id.item_goodsStanded);
            holder.item_goodsPrice = (TextView) view.findViewById(R.id.item_goodsPrice);
            holder.tv_goods_sort = (TextView) view.findViewById(R.id.tv_goods_sort);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        bean = list.get(position);
        holder.cb_shopcar.setChecked(bean.isCheck());

//        Picasso.with(mContext)
//                .load(R.mipmap.jifen_bg)
//                .transform(new HalfTransformation())
//                .placeholder(R.drawable.loading_default)
//                .error(R.mipmap.no_net_show_icon)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                .into(holder.th_image);

        if (isHide) {
            holder.ll_edit_goosNumber.setVisibility(View.GONE);
            holder.cb_shopcar.setBackgroundResource(R.drawable.checkbox_bg);
        } else {
            holder.ll_edit_goosNumber.setVisibility(View.VISIBLE);
            holder.cb_shopcar.setBackgroundResource(R.drawable.checkbox_selector);
            holder.item_goodsNum.setText(list.get(position).getShuliang());

            holder.item_jia.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = Integer.parseInt(list.get(position).getShuliang());
                    if (Integer.parseInt(Raw_list.get(position)) <= Integer.parseInt(list.get(position).getShuliang())) {
                        ((BaseActivity) mContext).showToast("数量不能大于总数");
                    } else {
                        num++;
                        holder.item_goodsNum.setText("" + num);
                        list.get(position).setShuliang("" + num);
                        if (listener != null) {
                            if (list.get(position).isCheck()) {
                                listener.dataChange(list);
                            }
                        }
                    }
                }
            });
            holder.item_jian.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    int num = Integer.parseInt(list.get(position).getShuliang());
                    if (Integer.parseInt(holder.item_goodsNum.getText().toString()) - 1 < 1) {
                        ((BaseActivity) mContext).showToast("数量不能小于1");
                    } else {
                        num--;
                        holder.item_goodsNum.setText("" + num);
                        list.get(position).setShuliang("" + num);

                        Log.e("减", "Raw_list" + Raw_list.get(position));
                        if (listener != null) {
                            if (list.get(position).isCheck()) {
                                listener.dataChange(list);
                            }
                        }
                    }
                }
            });
        }
        return view;
    }

    static class ViewHolder {
        CheckBox cb_shopcar;
        ImageView img_shoppingCarItem;
        ImageView item_jian, item_jia;
        LinearLayout ll_edit_goosNumber;
        TextView item_goodsNum,item_goodsStanded,item_goodsPrice,tv_goods_sort;
    }

    public void setOnChangeData(onChangeData listener) {
        this.listener = listener;
    }

    public onChangeData listener;

    public interface onChangeData {
        void dataChange(List<OrderBeanItem> list);
    }

    private int defaultSelection = -1;

    public void setSelectPosition(int position) {
        if (!(position < 0 || position > list.size())) {
            defaultSelection = position;
            notifyDataSetChanged();
        }
    }
}
