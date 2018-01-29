package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.personalcenter.bean.FinancialDetailsBean;

import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/2/14时间10:08
 * @描述：财务明细适配器
 */

@SuppressWarnings("ALL")
public class FinancialDetailsAdapter extends BaseAdapter {

    private List<FinancialDetailsBean.DataBean.ListBean> list;
    private Context context;
    private FinancialDetailsBean.DataBean.ListBean bean;

    public FinancialDetailsAdapter(Context context, List<FinancialDetailsBean.DataBean.ListBean> list) {
        this.context = context;
        this.list = list;
    }

//    public void setData(List<FinancialDetailsBean.DataBean.ListBean> list){
//        this.list = list;
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
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
        return position;
    }

    @Override
    public View getView(int position, View view, ViewGroup parent) {
        ViewHolder holder = null;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(context).inflate(R.layout.list_item_cwmx, null);
            holder.cwmx_date = (TextView) view.findViewById(R.id.cwmx_date);
            holder.cwmx_time = (TextView) view.findViewById(R.id.cwmx_time);
            holder.cwmx_image = (ImageView) view.findViewById(R.id.cwmx_image);
            holder.cwmx_type = (TextView) view.findViewById(R.id.cwmx_type);
            holder.tv_pay_type = (TextView) view.findViewById(R.id.tv_pay_type);
            holder.cwmx_account_num = (TextView) view.findViewById(R.id.cwmx_account_num);
            holder.cwmx_money = (TextView) view.findViewById(R.id.cwmx_money);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        bean = list.get(position);
        String transTime = bean.getTransTime();
        String[] splitTime = transTime.split(" ");
        holder.cwmx_date.setText(splitTime[0]);
        holder.cwmx_time.setText(splitTime[1]);

        String typeStr = bean.getDesc();
        holder.cwmx_type.setText(typeStr);
        holder.tv_pay_type.setText(bean.getDepositType());
        holder.cwmx_account_num.setText(bean.getRemark());
        double incomeMoney = Double.parseDouble(bean.getInCome());
        double expenseMoney = Double.parseDouble(bean.getExpense());

        if (incomeMoney > 0) {
            holder.cwmx_money.setText("+"+StringUtil.strToDouble_new(bean.getInCome()));
        } else if (expenseMoney>0){
            holder.cwmx_money.setText("-"+StringUtil.strToDouble_new(bean.getExpense()));
        }

        //        0加款，1退款，2进货，3移动，4联通，5电信，6存送套餐，7固话，8流量，9Q币，11水电燃
        if ("加款".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.caiwu_jiakuan);
        } else if ("退款".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.caiwu_tuikuan);
        } else if ("进货".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.caiwu_jinhuo);
        } else if ("移动".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.caiwu_huafei);
        } else if ("联通".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.liantong_icon);
        } else if ("电信".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.dianxin_icon);
        } else if ("存送套餐".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.package_icon);
        } else if ("固话".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.guhua_icon);
        } else if ("流量".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.liuliang_icon);
        } else if ("Q币".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.q_icon);
        } else if ("水电燃".equals(typeStr)) {
            holder.cwmx_image.setImageResource(R.mipmap.life_recharge_icon);
        }

//        holder.cwmx_date.setText(bean.getCwmx_time());
//        holder.cwmx_type.setText(type);//类型
//        holder.cwmx_account.setText(bean.getCwmx_account());
//        holder.cwmx_account_num.setText(bean.getCwmx_account_num());
//        holder.cwmx_money.setText(bean.getCwmx_money());
        return view;
    }

    static class ViewHolder {
        TextView cwmx_date;// 财务明细日期
        TextView cwmx_time;// 财务明细时间
        ImageView cwmx_image;// 财务明细图片
        TextView cwmx_type;// 财务明细类型
        TextView tv_pay_type;// 财务明细支付类型
        TextView cwmx_account_num;// 财务明细支付账号
        TextView cwmx_money;// 财务明细收入

    }
}
