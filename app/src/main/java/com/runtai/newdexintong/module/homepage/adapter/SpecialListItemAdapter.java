package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.homepage.bean.SpecialListDataBean;

import java.util.ArrayList;
import java.util.List;

import static com.runtai.newdexintong.R.id.fenlei_gridview_item_spec;
import static com.runtai.newdexintong.R.id.fenlei_gridview_item_spec_unit;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class SpecialListItemAdapter extends BaseAdapter {

    private Context context;
    private List<SpecialListDataBean.DataBean.ListBean.ActivitysBean> data;
    private final LayoutInflater mInflater;
    private int specialId;

    public SpecialListItemAdapter(Context context, List<SpecialListDataBean.DataBean.ListBean.ActivitysBean> data,int specialId) {
        this.context = context;
        this.data = data;
        mInflater = LayoutInflater.from(context);
        this.specialId = specialId;
    }

    @Override
    public int getCount() {
        return data.size();
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
            convertView = mInflater.inflate(R.layout.item_special_list_subitem, null);
            holder.iv_goods_pic = (ImageView) convertView.findViewById(R.id.iv_goods_pic);
            holder.tv_old_price = (TextView) convertView.findViewById(R.id.tv_old_price);
            holder.tv_old_price_text = (TextView) convertView.findViewById(R.id.tv_old_price_text);
            holder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.tv_remain_number = (TextView) convertView.findViewById(R.id.tv_remain_number);
            holder.tv_current_price = (TextView) convertView.findViewById(R.id.tv_current_price);
            holder.ll_subitem = (LinearLayout) convertView.findViewById(R.id.ll_subitem);

            holder.fenlei_gridview_item_spec = (TextView) convertView.findViewById(fenlei_gridview_item_spec);
            holder.fenlei_gridview_item_spec_unit = (TextView) convertView.findViewById(fenlei_gridview_item_spec_unit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        SpecialListDataBean.DataBean.ListBean.ActivitysBean activitysBean = data.get(position);
        List<String> new_imgList = new ArrayList<>();
        new_imgList.clear();
        for (int i = 0; i < data.size(); i++) {
            if (i < 3) {
                new_imgList.add(activitysBean.getPhoto());
            }
        }
        ImageLoader.getInstance().displayImage(new_imgList.get(position), holder.iv_goods_pic,
                ImageLoadUtil.getDefaultHeadPicOptions(), null);
        holder.tv_goods_name.setText(activitysBean.getItemName());
        holder.fenlei_gridview_item_spec_unit.setText(activitysBean.getUnit());
        holder.fenlei_gridview_item_spec.setText(String.valueOf(activitysBean.getSpec()));
        holder.tv_remain_number.setText(activitysBean.getLeftAlone());
        if (specialId == 6) {//只有特价专场显示原价
            holder.tv_old_price.setVisibility(View.VISIBLE);
            holder.tv_old_price_text.setVisibility(View.VISIBLE);
            holder.tv_current_price.setText(StringUtil.strToDouble_new(String.valueOf(activitysBean.getBarginPrice())));
            holder.tv_old_price.setText(StringUtil.strToDouble_new(String.valueOf(activitysBean.getOriginalPrice())));
            //原价上面画线
            holder.tv_old_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
            holder.tv_old_price_text.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        } else {
            holder.tv_current_price.setText(StringUtil.strToDouble_new(String.valueOf(activitysBean.getOriginalPrice())));
            holder.tv_old_price.setVisibility(View.GONE);
            holder.tv_old_price_text.setVisibility(View.GONE);
        } 
     
        return convertView;
    }

    public class ViewHolder {
        ImageView iv_goods_pic;
        TextView tv_old_price_text, tv_old_price;
        TextView tv_goods_name, tv_remain_number, tv_current_price;
        LinearLayout ll_subitem;
        TextView fenlei_gridview_item_spec_unit, fenlei_gridview_item_spec;
    }
}
