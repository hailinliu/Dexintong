package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.homepage.bean.ExchangePrizeRecordBean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class ExchangePrizeRecordAdapter extends BaseAdapter {

    private Context context;
    private List<ExchangePrizeRecordBean.DataBean.ListBean> mData;
    private final LayoutInflater mInflater;

    public ExchangePrizeRecordAdapter(Context context, List<ExchangePrizeRecordBean.DataBean.ListBean> mData) {
        this.context = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_exchange_prize_record, null);
            holder.tv_time = (TextView) convertView.findViewById(R.id.tv_time);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);

            holder.tv_spec = (TextView) convertView.findViewById(R.id.tv_spec);
            holder.tv_award_name = (TextView) convertView.findViewById(R.id.tv_award_name);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_record_number = (TextView) convertView.findViewById(R.id.tv_record_number);
            holder.tv_exchange_state = (TextView) convertView.findViewById(R.id.tv_exchange_state);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        ExchangePrizeRecordBean.DataBean.ListBean listBean = mData.get(position);

        holder.tv_time.setText(listBean.getCreateTime());
        holder.tv_name.setText(listBean.getItemName());
        holder.tv_record_number.setText(String.valueOf(listBean.getQty()));
        holder.tv_award_name.setText(listBean.getAward());
        holder.tv_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getAmount())));
        holder.tv_exchange_state.setText(listBean.getStatusDesc());
        return convertView;
    }

    class ViewHolder {

        TextView tv_time, tv_name, tv_spec, tv_award_name, tv_price,
                tv_record_number, tv_exchange_state;
    }
}
