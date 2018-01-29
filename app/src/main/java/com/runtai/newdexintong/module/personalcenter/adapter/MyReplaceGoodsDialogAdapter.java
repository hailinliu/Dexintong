package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.personalcenter.bean.ApplyReplaceGoodsBean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/7time11:43
 * @detail：确认退货调货对话框对应的数据适配器
 */

public class MyReplaceGoodsDialogAdapter extends BaseAdapter {

    private Context context;
    private List<ApplyReplaceGoodsBean> mData;
    private final LayoutInflater myInflater;

    public MyReplaceGoodsDialogAdapter(Context context, List<ApplyReplaceGoodsBean> mData) {
        this.context = context;
        this.mData = mData;
        myInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = myInflater.inflate(R.layout.item_dialog_replace_goods, null);
            holder.tv_replace_goods_name = (TextView) convertView.findViewById(R.id.tv_replace_goods_name);//商品名称
            holder.tv_goods_standard = (TextView) convertView.findViewById(R.id.tv_goods_standard);//商品规格
            holder.tv_goods_num = (TextView) convertView.findViewById(R.id.tv_goods_num);//商品数量
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        ApplyReplaceGoodsBean bean = mData.get(position);
//        holder.tv_replace_goods_name.setText(bean.name);
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
        TextView tv_replace_goods_name, tv_goods_standard, tv_goods_num;
    }
}
