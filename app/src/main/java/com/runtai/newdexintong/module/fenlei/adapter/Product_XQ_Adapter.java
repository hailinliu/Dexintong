package com.runtai.newdexintong.module.fenlei.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.os.Handler;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.fenlei.bean.ChaoShiModelBean;

import java.text.DecimalFormat;
import java.util.List;

public class Product_XQ_Adapter extends BaseAdapter {

    private Context context;
    private Handler handler;
    private List<ChaoShiModelBean> list;
    private ChaoShiModelBean bean;


    public Product_XQ_Adapter(Context context, List<ChaoShiModelBean> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        // TODO Auto-generated method stub
        return position;
    }

    @Override
    public View getView(final int index, View view, ViewGroup parent) {
        // TODO Auto-generated method stub
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.item_product_xq_gridview, null);
            holder.fenlei_gridview_item_image = (ImageView) view.findViewById(R.id.fenlei_gridview_item_image);
            holder.fenlei_gridview_item_name = (TextView) view.findViewById(R.id.fenlei_gridview_item_name);
            holder.fenlei_gridview_item_spec = (TextView) view.findViewById(R.id.fenlei_gridview_item_spec);
            holder.fenlei_gridview_item_price = (TextView) view.findViewById(R.id.fenlei_gridview_item_price);
            holder.fenlei_gridview_item_car = (ImageView) view.findViewById(R.id.fenlei_gridview_item_car);
            holder.pro_xq_item_tv1 = (TextView) view.findViewById(R.id.pro_xq_item_tv1);
            holder.pro_xq_item_tv2 = (TextView) view.findViewById(R.id.pro_xq_item_tv2);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        /*添加删除线*/
        holder.pro_xq_item_tv1.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        holder.pro_xq_item_tv2.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        bean = list.get(index);
//        Picasso.with(context)
//                .load(bean.getMinpic())
//                .transform(new HalfTransformation())
//                .placeholder(R.drawable.loading_default)
//                .error(R.mipmap.no_net_show_icon)
//                .memoryPolicy(MemoryPolicy.NO_CACHE, MemoryPolicy.NO_STORE)
//                .into(holder.fenlei_gridview_item_image);
        holder.fenlei_gridview_item_name.setText(bean.getName());
        holder.fenlei_gridview_item_spec.setText(bean.getSpec());
        holder.fenlei_gridview_item_price.setText(String.valueOf(new DecimalFormat("##0.00").format(bean.getPrice())));
        holder.fenlei_gridview_item_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ToastUtil.showCenterToast(context, list.get(index).getName() + "购物车", 0);
            }
        });
        return view;
    }

    class ViewHolder {
        ImageView fenlei_gridview_item_image;
        TextView fenlei_gridview_item_name;
        TextView fenlei_gridview_item_spec;
        TextView fenlei_gridview_item_price;
        ImageView fenlei_gridview_item_car;
        TextView pro_xq_item_tv1;
        TextView pro_xq_item_tv2;
    }
}
