package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.ShoppingCarPresentBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/14.
 * 购物车中赠品对应的适配器
 */
public class ShoppingCarPresentAdapter extends RecyclerView.Adapter<ShoppingCarPresentAdapter.MyViewHolder> {


    private Context context;
    private List<ShoppingCarPresentBean> data;
    private final LayoutInflater mInflater;

    public ShoppingCarPresentAdapter(Context context, List<ShoppingCarPresentBean> data) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.data = data;
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        MyViewHolder holder = new MyViewHolder(mInflater.inflate(
                R.layout.item_shoppingcar_present, parent, false));
        return holder;
    }

    @Override
    public void onBindViewHolder(MyViewHolder holder, int position) {
        holder.tv_present_name.setText(data.get(position).presentName);
    }

    @Override
    public int getItemCount() {
        return data.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_present_name;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_present_name = (TextView) itemView.findViewById(R.id.tv_present_name);
        }
    }
}
