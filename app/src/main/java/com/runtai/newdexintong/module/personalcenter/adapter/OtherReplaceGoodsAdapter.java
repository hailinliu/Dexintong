package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.TextView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.module.personalcenter.bean.OtherReplaceGoodsBean;

import java.util.HashMap;
import java.util.List;

/**
 * @author：rhf
 * @date：2017/8/3time14:57
 * @detail：其他条换页面对应的适配器
 */

public class OtherReplaceGoodsAdapter extends BaseAdapter {


    private Context context;
    private List<OtherReplaceGoodsBean.DataBean> mData;
    private final LayoutInflater mInflater;
    public static HashMap<Integer, Boolean> isChecked;

    public OtherReplaceGoodsAdapter(Context context, List<OtherReplaceGoodsBean.DataBean> mData) {
        this.context = context;
        this.mData = mData;
        mInflater = LayoutInflater.from(context);
        initData();
    }

    private void initData() {
        isChecked = new HashMap<>();
        for (int i = 0; i < mData.size(); i++) {
            isChecked.put(i, false);
            setIsChecked(isChecked);
        }
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
            convertView = mInflater.inflate(R.layout.item_other_replace_goods, null);
            holder.tv_goods_name = (TextView) convertView.findViewById(R.id.tv_goods_name);
            holder.tv_goods_price = (TextView) convertView.findViewById(R.id.tv_goods_price);
            holder.iv_goods_picture = (ImageView) convertView.findViewById(R.id.iv_goods_picture);
            holder.checkbox_select = (CheckBox) convertView.findViewById(R.id.checkbox_select);
            holder.tv_spec_number = (TextView) convertView.findViewById(R.id.tv_spec_number);
            holder.tv_spec_unit = (TextView) convertView.findViewById(R.id.tv_spec_unit);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        OtherReplaceGoodsBean.DataBean dataBean = mData.get(position);
        holder.tv_goods_name.setText(dataBean.getItemName());
        holder.checkbox_select.setChecked(dataBean.isChecked);

        Boolean item_isChecked = getIsChecked().get(position);
        if (item_isChecked != null) {
            if (item_isChecked) {
                holder.checkbox_select.setChecked(true);
            } else {
                holder.checkbox_select.setChecked(false);
            }
        }
        holder.tv_goods_price.setText(StringUtil.strToDouble_new(String.valueOf(dataBean.getOriginalPrice())));
        ImageLoader.getInstance().displayImage(dataBean.getPhoto(), holder.iv_goods_picture,
                ImageLoadUtil.getDefaultHeadPicOptions(), null);
        holder.tv_spec_number.setText(String.valueOf(dataBean.getSpec()));
        holder.tv_spec_unit.setText(dataBean.getUnit());
        
        return convertView;
    }

    class ViewHolder {
        TextView tv_goods_name, tv_goods_price,tv_spec_number,tv_spec_unit;
        ImageView iv_goods_picture;
        CheckBox checkbox_select;
    }

    /**
     * 设置每个分组的那个checkbox是否选中
     *
     * @param isChecked
     */
    public static void setIsChecked(HashMap<Integer, Boolean> isChecked) {

        OtherReplaceGoodsAdapter.isChecked = isChecked;
    }

    /**
     * 获取每个分组checkbox的选中状态
     *
     * @return
     */
    public static HashMap<Integer, Boolean> getIsChecked() {
        return OtherReplaceGoodsAdapter.isChecked;
    }
}
