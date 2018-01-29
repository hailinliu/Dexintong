package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.graphics.Color;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.homepage.bean.SpecialSaleTitleBean;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/10.
 * 专场对应的适配器
 */
public class SpecialSaleTitleAdapter extends RecyclerView.Adapter<SpecialSaleTitleAdapter.MyViewHolder>
         {


    private Context context;
    private List<SpecialSaleTitleBean> mData;
    private final LayoutInflater mInflater;
             private int clickedPosition;
             private List<Boolean> isClicks;//控件是否被点击,默认为false，如果被点击，改变值，控件根据值改变自身颜色
    private int checkedColor = Color.parseColor("#FF2D48");
    private int uncheckedColor = Color.parseColor("#333333");


    public interface OnItemClickLitener {
        void onItemClick(View view, int position);

    }

    private OnItemClickLitener mOnItemClickLitener;

    public void setOnItemClickLitener(OnItemClickLitener mOnItemClickLitener) {
        this.mOnItemClickLitener = mOnItemClickLitener;
    }

    public SpecialSaleTitleAdapter(Context context, List<SpecialSaleTitleBean> datas,int clickedPosition) {

        this.context = context;
        this.mData = datas;
        mInflater = LayoutInflater.from(context);
        this.clickedPosition = clickedPosition;
        isClicks = new ArrayList<>();
        initData();
    }

    private void initData() {
        for (int i = 0; i < mData.size(); i++) {
            if (i == clickedPosition) {
                isClicks.add(true);
            } else {
                isClicks.add(false);
            }
        }
    }

    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = mInflater.inflate(
                R.layout.item_special_title, parent, false);
        MyViewHolder holder = new MyViewHolder(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(final MyViewHolder holder, final int position) {

        if (position < mData.size()) {
            holder.tv_special_title.setText(mData.get(position).getTitle());
            //将数据保存在itemView的Tag中，以便点击时进行获取
            holder.itemView.setTag(mData.get(position));

            if (isClicks.get(position)) {
                holder.tv_special_title.setTextColor(checkedColor);
                holder.view_selected.setVisibility(View.VISIBLE);
            } else {
                holder.tv_special_title.setTextColor(uncheckedColor);
                holder.view_selected.setVisibility(View.GONE);
            }

            // 如果设置了回调，则设置点击事件
            if (mOnItemClickLitener != null) {
                holder.itemView.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        int layoutPosition = holder.getLayoutPosition();
                        for (int i = 0; i < isClicks.size(); i++) {
                            isClicks.set(i, false);
                        }

                        isClicks.set(position, true);
                        mOnItemClickLitener.onItemClick(holder.itemView, layoutPosition);
                        notifyDataSetChanged();
                    }
                });
            }
        }
    }

    @Override
    public int getItemCount() {

        return mData.size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {

        TextView tv_special_title;
        View view_selected;

        public MyViewHolder(View itemView) {
            super(itemView);
            tv_special_title = (TextView) itemView.findViewById(R.id.tv_special_title);
            view_selected = itemView.findViewById(R.id.view_selected);
        }

    }

//    @Override
//    public void onClick(View v) {
//        if (mOnItemClickLitener != null) {
//            //注意这里使用getTag方法获取数据
//            mOnItemClickLitener.onItemClick(v,(SpecialSaleTitleBean) v.getTag());
//        }
//    }

}
