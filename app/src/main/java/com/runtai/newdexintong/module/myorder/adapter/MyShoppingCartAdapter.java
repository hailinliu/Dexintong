package com.runtai.newdexintong.module.myorder.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.NetUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.fenlei.bean.EditShoppingCartGoodsBean;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.homepage.activity.SpecialSaleActivity;
import com.runtai.newdexintong.module.myorder.bean.ShoppingCartBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static com.runtai.newdexintong.R.id.item_goodsName;
import static com.runtai.newdexintong.R.id.item_goodsPrice;

/**
 * Created by Administrator on 2017/2/8.
 */

public class MyShoppingCartAdapter extends SectionedBaseAdapter implements View.OnClickListener {

    private Map<Integer, ShoppingCartBean.DataBean> mData;
    private Context context;
    private List<ShoppingCartBean.DataBean> present_data;
    ViewHoder vholder;
    private int num;
    public static HashMap<Integer, Boolean> isShow;
    public static HashMap<Integer, Boolean> isGroupChecked;
    //    public static HashMap<Integer, Boolean> isRed;
//    public static HashMap<Integer, HashMap<Integer, Boolean>> isRedGroup;
    private final LayoutInflater mInflater;
    private int section;
    private int present_id;
    private boolean isClicked = false;
    private double giftPrice;
    private int minNum;


    public MyShoppingCartAdapter(Map<Integer, ShoppingCartBean.DataBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
        mInflater = LayoutInflater.from(context);
        isShow = new HashMap<>();
        isGroupChecked = new HashMap<>();

        present_data = new ArrayList<>();
        initData();
    }

    public void setNewData(Map<Integer, ShoppingCartBean.DataBean> mData, Context context) {
        this.mData = mData;
        this.context = context;
//        callBack.refreshAdapter();
    }

    private void initData() {
        for (int i = 0; i < mData.size(); i++) {

            isGroupChecked.put(i, false);
            setIsGroupChecked(isGroupChecked);

            for (int j = 0; j < mData.get(i).getItems().size(); j++) {
                isShow.put(j, true);
                setIsShow(isShow);

            }
        }
    }

    @Override
    public int getSectionCount() {
        return mData.size();
    }

    @Override
    public int getCountForSection(int section) {

        return mData.get(section).getItems().size();
    }


    @Override
    public View getItemView(final int section, final int position, View convertView, ViewGroup parent) {

        this.section = section;
        if (convertView == null) {

            vholder = new ViewHoder();
            convertView = mInflater.inflate(R.layout.item_shoppingcar, null);

            vholder.item_jia = (ImageView) convertView
                    .findViewById(R.id.item_jia);
            vholder.item_jian = (ImageView) convertView
                    .findViewById(R.id.item_jian);
            vholder.name = (TextView) convertView
                    .findViewById(item_goodsName);
            vholder.cb_shopcar = (CheckBox) convertView
                    .findViewById(R.id.cb_shopcar);
//            vholder.standard = (TextView) convertView
//                    .findViewById(R.id.item_goodsStanded);
            vholder.item_goodsPrice = (TextView) convertView
                    .findViewById(item_goodsPrice);
            vholder.num = (TextView) convertView
                    .findViewById(R.id.item_goodsNum);
            vholder.img_shoppingCarItem = (ImageView) convertView
                    .findViewById(R.id.img_shoppingCarItem);
            vholder.ll_edit_goosNumber = (LinearLayout) convertView
                    .findViewById(R.id.ll_edit_goosNumber);
            vholder.hasNoGood = (ImageView) convertView
                    .findViewById(R.id.hasNoGood);
            vholder.tv_item_delete = (TextView) convertView.findViewById(R.id.tv_item_delete);
            vholder.tv_number_icon = (TextView) convertView.findViewById(R.id.tv_number_icon);
            vholder.tv_number = (TextView) convertView.findViewById(R.id.tv_number);
            vholder.tv_item_totalPrice = (TextView) convertView.findViewById(R.id.tv_item_totalPrice);
            vholder.tv_prsent_flag = (TextView) convertView.findViewById(R.id.tv_prsent_flag);
            vholder.tv_spec_number = (TextView) convertView.findViewById(R.id.tv_spec_number);
            vholder.tv_spec_unit = (TextView) convertView.findViewById(R.id.tv_spec_unit);
            vholder.tv_present = (TextView) convertView.findViewById(R.id.tv_present);
            vholder.tv_present_number = (TextView) convertView.findViewById(R.id.tv_present_number);
            vholder.tv_present_number_unit = (TextView) convertView.findViewById(R.id.tv_present_number_unit);
            vholder.tv_isdirect = (TextView) convertView.findViewById(R.id.tv_isdirect);
            convertView.setTag(vholder);

        } else {
            vholder = (ViewHoder) convertView.getTag();
        }
        final ShoppingCartBean.DataBean dataBean = mData.get(section);
        final ShoppingCartBean.DataBean.ItemsBean itemsBean = mData.get(section).getItems().get(position);
        vholder.name.setText(itemsBean.getItemName());
        vholder.tv_spec_number.setText(String.valueOf(itemsBean.getSpec()));
        vholder.tv_spec_unit.setText(itemsBean.getUnit());
        vholder.item_goodsPrice.setText(StringUtil.strToDouble_new(String.valueOf(itemsBean.getOriginalPrice())));
        ImageLoader.getInstance().displayImage(itemsBean.getPhoto(), vholder.img_shoppingCarItem,
                ImageLoadUtil.getDefaultHeadPicOptions(), null);
        vholder.num.setText(String.valueOf(itemsBean.getBuyNum()));
        vholder.cb_shopcar.setChecked(itemsBean.isCheck);
        if (itemsBean.isIsDirect()) {
            //直配商品
            vholder.tv_isdirect.setVisibility(View.VISIBLE);
        } else {
            vholder.tv_isdirect.setVisibility(View.GONE);
        }
        boolean isSend = itemsBean.isIsSend();
        if (isSend) {
            //如果是赠品
            vholder.item_jia.setVisibility(View.GONE);
            vholder.item_jian.setVisibility(View.GONE);
            vholder.num.setVisibility(View.GONE);
            vholder.tv_present_number.setVisibility(View.VISIBLE);
            vholder.tv_present_number_unit.setVisibility(View.VISIBLE);
            vholder.tv_present_number.setText(String.valueOf(itemsBean.getBuyNum()));
            vholder.tv_present.setVisibility(View.VISIBLE);
            giftPrice = itemsBean.getBuyNum() * itemsBean.getOriginalPrice();
        } else {
            vholder.num.setVisibility(View.VISIBLE);
            vholder.tv_present_number.setVisibility(View.GONE);
            vholder.tv_present_number_unit.setVisibility(View.GONE);
            vholder.item_jia.setVisibility(View.VISIBLE);
            vholder.item_jian.setVisibility(View.VISIBLE);
            vholder.tv_present.setVisibility(View.GONE);
        }

//        //设置赠品条目,限制请求后续以接口提供的条件为准

        vholder.ll_edit_goosNumber.setVisibility(View.VISIBLE);

        vholder.item_jia.setOnClickListener(new MyAdapterListener(section, position, true));
        vholder.item_jian.setOnClickListener(new MyAdapterListener(section, position, false));


        if (getIsShow().get(position)) {
            vholder.ll_edit_goosNumber.setVisibility(View.VISIBLE);
            vholder.tv_item_delete.setVisibility(View.GONE);
            vholder.tv_number_icon.setVisibility(View.GONE);
            vholder.tv_number.setVisibility(View.GONE);
            vholder.tv_item_totalPrice.setVisibility(View.GONE);

        } else {
            vholder.ll_edit_goosNumber.setVisibility(View.GONE);
            if (isSend) {
                vholder.tv_item_delete.setVisibility(View.GONE);
            } else {
                vholder.tv_item_delete.setVisibility(View.VISIBLE);
            }
            vholder.tv_number_icon.setVisibility(View.VISIBLE);
            vholder.tv_number.setVisibility(View.VISIBLE);
            vholder.tv_item_totalPrice.setVisibility(View.VISIBLE);
            String number = vholder.num.getText().toString();
            vholder.tv_number.setText(number);
            String goodsPrice = String.valueOf(itemsBean.getOriginalPrice());
            double item_total_price = Double.parseDouble(number) * Double.parseDouble(goodsPrice);
            vholder.tv_item_totalPrice.setText("￥" + StringUtil.strToDouble_new(String.valueOf(item_total_price)));

        }

        //点击单条目数据删除
        vholder.tv_item_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showDeleteWarning(itemsBean.getItemId(), dataBean.getSpecialId(), dataBean.getActivityId());
            }
        });

        return convertView;
    }


    class MyAdapterListener implements View.OnClickListener {

        private final ShoppingCartBean.DataBean.ItemsBean cart_itemsBean;
        private int section_clicked;
        private int position_clicked;
        private boolean isAdd;
        private int itemId;
        private int actId;
        private int specId;
        /**
         * 判断是否是赠品
         */
        private boolean isSendBoolean;

        public MyAdapterListener(int sec, int pos, boolean isAdd) {

            this.section_clicked = sec;
            position_clicked = pos;
            this.isAdd = isAdd;
            cart_itemsBean = mData.get(sec).getItems().get(pos);
//            int saleIncreaseCount = cart_itemsBean.getSaleIncreaseCount();
            itemId = cart_itemsBean.getItemId();
            actId = mData.get(sec).getActivityId();
            specId = mData.get(sec).getSpecialId();
            isSendBoolean = cart_itemsBean.isIsSend();

        }


        @Override
        public void onClick(View v) {
            if (isAdd && !isSendBoolean) {
                editShoppingCartByHttp(1, itemId, actId, specId, cart_itemsBean, section_clicked, position_clicked);
//                if (cart_itemsBean.isCheck) {
//                    callBack.totalPriceAddOne(section_clicked, position_clicked);
//                }

            } else {
                if (!isSendBoolean) {
                    if (cart_itemsBean.getSaleIncreaseCount() > cart_itemsBean.getSaleFromCount()) {
                        minNum = cart_itemsBean.getSaleIncreaseCount();
                    } else {
                        minNum = cart_itemsBean.getSaleFromCount();
                    }

                    if (cart_itemsBean.getBuyNum() > minNum) {
                        editShoppingCartByHttp(0, itemId, actId, specId, cart_itemsBean, section_clicked, position_clicked);
                    } else {
                        showEditDeletePoint(0, itemId, actId, specId, cart_itemsBean
                                , section_clicked, position_clicked);
                    }
                }
//                if (cart_itemsBean.isCheck) {
//                    callBack.totalPriceRemoveOne(section_clicked, position_clicked);
//                }

            }
//            callBack.refreshAdapter();
        }
    }


    public class ViewHoder {

        public CheckBox cb_shopcar;
        public ImageView img_shoppingCarItem, item_jia, item_jian;
        public TextView name, num, item_goodsPrice;
        public LinearLayout ll_edit_goosNumber;
        public ImageView hasNoGood;
        public TextView tv_item_delete, tv_number_icon, tv_number,
                tv_item_totalPrice, tv_prsent_flag;
        private TextView tv_spec_number, tv_spec_unit;
        TextView tv_present;
        TextView tv_present_number, tv_present_number_unit;
        TextView tv_isdirect;
    }


    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {

        SectionHeaderViewHolder holder = null;
        if (convertView == null) {
            holder = new SectionHeaderViewHolder();
            convertView = mInflater.inflate(R.layout.layout_shoppingcar_group_title, null);
            holder.tv_goods_detail_title = (TextView) convertView.findViewById(R.id.tv_goods_detail_title);
            holder.tv_goods_describe = (TextView) convertView.findViewById(R.id.tv_goods_describe);
            holder.tv_already_reduce = (TextView) convertView.findViewById(R.id.tv_already_reduce);
            holder.tv_cut_or_present = (TextView) convertView.findViewById(R.id.tv_cut_or_present);
            holder.tv_reduce_money = (TextView) convertView.findViewById(R.id.tv_reduce_money);
            holder.tv_money_unit = (TextView) convertView.findViewById(R.id.tv_money_unit);

            holder.tv_special_limit_price = (TextView) convertView.findViewById(R.id.tv_special_limit_price);
            holder.tv_reduce = (TextView) convertView.findViewById(R.id.tv_reduce);
            holder.tv_fullcut_money = (TextView) convertView.findViewById(R.id.tv_fullcut_money);
            holder.tv_reduce_money_unit = (TextView) convertView.findViewById(R.id.tv_reduce_money_unit);
            holder.tv_reduction_limit = (TextView) convertView.findViewById(R.id.tv_reduction_limit);
            holder.ll_full_cut_desribe = (LinearLayout) convertView.findViewById(R.id.ll_full_cut_desribe);
            holder.ll_goto_single = (LinearLayout) convertView.findViewById(R.id.ll_goto_single);
            holder.ll_special_condition = (LinearLayout) convertView.findViewById(R.id.ll_special_condition);
            holder.rl_shoppingcar_item_title = (RelativeLayout) convertView.findViewById(R.id.rl_shoppingcar_item_title);
            holder.tv_already_present = (TextView) convertView.findViewById(R.id.tv_already_present);
            holder.tv_watch_present = (TextView) convertView.findViewById(R.id.tv_watch_present);
            holder.rl_watch_present = (RelativeLayout) convertView.findViewById(R.id.rl_watch_present);
            holder.checkbox_group = (CheckBox) convertView.findViewById(R.id.checkbox_group);
            convertView.setTag(holder);
        } else {
            holder = (SectionHeaderViewHolder) convertView.getTag();
        }

        final ShoppingCartBean.DataBean dataBean = mData.get(section);
        final int specialId = dataBean.getSpecialId();
        final double limit = dataBean.getLimit();
        final String activityName = dataBean.getActivityName();
        int activityId = dataBean.getActivityId();
        String giftName = dataBean.getGiftName();
        double benefitMoney = dataBean.getBenefitMoney();
        double fullCutTotalMoney = callBack.getFullCutTotalMoney();
//        double fullPresentTotalMoney = callBack.getFullPresentTotalMoney();
//        if (fullCutTotalMoney < limit) {
//            callBack.setTotalAndReduceMoney(0.00);
//        } else {
//            callBack.setTotalAndReduceMoney(dataBean.getBenefit());
//        }

//        专场ID (0普场；1满减；2满赠；3折扣；4限时秒杀；5订货会预售)
        /**
         * 0普场；1满减；2满赠；3折扣；6天天特价
         */

        if (specialId == 2) {
            holder.rl_shoppingcar_item_title.setVisibility(View.VISIBLE);
            if (dataBean.getOriginalMoney() < limit) {
                holder.ll_goto_single.setVisibility(View.VISIBLE);
                holder.ll_full_cut_desribe.setVisibility(View.VISIBLE);
                holder.tv_money_unit.setVisibility(View.GONE);
                holder.tv_reduce_money.setVisibility(View.GONE);
                holder.tv_cut_or_present.setText("元，立即获取赠品");
                if (limit - dataBean.getOriginalMoney() > 0) {
                    holder.tv_reduction_limit.setText(StringUtil.strToDouble_new(String.valueOf(limit - dataBean.getOriginalMoney())));
                }
                holder.ll_goto_single.setVisibility(View.VISIBLE);
                holder.ll_full_cut_desribe.setVisibility(View.VISIBLE);
                holder.tv_already_present.setVisibility(View.GONE);
                callBack.setIsCanClick(true);
            } else {
                holder.ll_goto_single.setVisibility(View.INVISIBLE);
                holder.ll_full_cut_desribe.setVisibility(View.GONE);
                holder.tv_already_present.setVisibility(View.VISIBLE);
                callBack.setIsCanClick(false);
            }

            holder.tv_goods_detail_title.setText(activityName);
            holder.tv_goods_describe.setVisibility(View.GONE);
            holder.ll_special_condition.setVisibility(View.VISIBLE);
            holder.tv_special_limit_price.setText(limit + "赠");
            holder.tv_reduce.setText(giftName);
            holder.tv_reduce.setTextColor(context.getResources().getColor(R.color.new_title_color));
            holder.tv_fullcut_money.setVisibility(View.GONE);
            holder.tv_reduce_money_unit.setVisibility(View.GONE);
            holder.tv_already_reduce.setVisibility(View.GONE);
            holder.rl_watch_present.setVisibility(View.GONE);

        } else if (specialId == 1) {
            holder.rl_shoppingcar_item_title.setVisibility(View.VISIBLE);
            if (dataBean.getOriginalMoney() < limit) {
                holder.ll_goto_single.setVisibility(View.VISIBLE);
                holder.ll_full_cut_desribe.setVisibility(View.VISIBLE);
                holder.tv_money_unit.setVisibility(View.VISIBLE);
                holder.tv_reduce_money.setVisibility(View.VISIBLE);
                holder.tv_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(dataBean.getBenefit())));
                holder.tv_cut_or_present.setText("元，立即减");
                if (limit - dataBean.getOriginalMoney() > 0) {
                    holder.tv_reduction_limit.setText(StringUtil.strToDouble_new(String.valueOf(limit - dataBean.getOriginalMoney())));
                }
                holder.tv_already_reduce.setVisibility(View.GONE);
                callBack.setIsCanClick(true);
//                callBack.setTotalAndReduceMoney(0.00);
            } else {
                holder.ll_goto_single.setVisibility(View.INVISIBLE);
                holder.ll_full_cut_desribe.setVisibility(View.GONE);
                holder.tv_already_reduce.setVisibility(View.VISIBLE);
                callBack.setIsCanClick(false);
//                callBack.setTotalAndReduceMoney(dataBean.getBenefit());
            }

            holder.tv_goods_detail_title.setText(activityName);
            holder.tv_goods_describe.setVisibility(View.GONE);
            holder.ll_special_condition.setVisibility(View.VISIBLE);
            holder.tv_special_limit_price.setText(StringUtil.strToDouble_new(String.valueOf(limit)));
            holder.tv_reduce.setText("减");
            holder.tv_reduce.setTextColor(context.getResources().getColor(R.color.black_text));
            holder.tv_fullcut_money.setVisibility(View.VISIBLE);
            holder.tv_fullcut_money.setText(StringUtil.strToDouble_new(String.valueOf(dataBean.getBenefit())));
            holder.tv_reduce_money_unit.setVisibility(View.VISIBLE);
            holder.tv_already_present.setVisibility(View.GONE);
            holder.rl_watch_present.setVisibility(View.GONE);
        } else if (specialId == 0 || specialId == 6) {//0平价商品 6天天特价
            holder.rl_shoppingcar_item_title.setVisibility(View.VISIBLE);
            holder.tv_goods_detail_title.setText(activityName);
            holder.ll_goto_single.setVisibility(View.INVISIBLE);
            holder.ll_full_cut_desribe.setVisibility(View.INVISIBLE);
            holder.tv_goods_describe.setVisibility(View.VISIBLE);
            holder.tv_goods_describe.setText(dataBean.getTipMsg());
            holder.ll_special_condition.setVisibility(View.INVISIBLE);
            holder.tv_already_reduce.setVisibility(View.GONE);
            holder.tv_already_present.setVisibility(View.GONE);
            holder.rl_watch_present.setVisibility(View.GONE);
            callBack.setIsCanClick(false);
        } else if (specialId == 3) {
            holder.rl_shoppingcar_item_title.setVisibility(View.VISIBLE);
            holder.tv_goods_detail_title.setText(activityName);
            holder.ll_goto_single.setVisibility(View.INVISIBLE);
            holder.ll_full_cut_desribe.setVisibility(View.INVISIBLE);
            holder.tv_goods_describe.setVisibility(View.VISIBLE);
            String[] special_discount_describe = dataBean.getTipMsg().split("，");
            holder.tv_goods_describe.setText(special_discount_describe[0]);
            holder.ll_special_condition.setVisibility(View.INVISIBLE);
            holder.tv_already_reduce.setVisibility(View.GONE);
            holder.tv_already_present.setVisibility(View.GONE);
            holder.rl_watch_present.setVisibility(View.GONE);
            callBack.setIsCanClick(false);
        } else if (specialId == 4) {
            holder.rl_shoppingcar_item_title.setVisibility(View.VISIBLE);
            holder.tv_goods_detail_title.setText(activityName);
            holder.ll_goto_single.setVisibility(View.INVISIBLE);
            holder.ll_full_cut_desribe.setVisibility(View.INVISIBLE);
            holder.tv_goods_describe.setVisibility(View.VISIBLE);
            holder.tv_goods_describe.setText(dataBean.getTipMsg());
            holder.ll_special_condition.setVisibility(View.INVISIBLE);
            holder.tv_already_reduce.setVisibility(View.GONE);
            holder.tv_already_present.setVisibility(View.GONE);
            holder.rl_watch_present.setVisibility(View.GONE);
            callBack.setIsCanClick(false);
        } else if (specialId == 5) {
            holder.rl_shoppingcar_item_title.setVisibility(View.VISIBLE);
            holder.tv_goods_detail_title.setText(activityName);
            holder.ll_goto_single.setVisibility(View.INVISIBLE);
            holder.ll_full_cut_desribe.setVisibility(View.INVISIBLE);
            holder.tv_goods_describe.setVisibility(View.VISIBLE);
            holder.tv_goods_describe.setText(dataBean.getTipMsg());
            holder.ll_special_condition.setVisibility(View.INVISIBLE);
            holder.tv_already_reduce.setVisibility(View.GONE);
            holder.tv_already_present.setVisibility(View.GONE);
            holder.rl_watch_present.setVisibility(View.GONE);
            callBack.setIsCanClick(false);
        }


        Boolean isSelected = getIsGroupChecked().get(section);
        if (isSelected != null) {
            if (isSelected) {
                holder.checkbox_group.setChecked(true);
            } else {
                holder.checkbox_group.setChecked(false);
            }
        } else {
            holder.checkbox_group.setChecked(false);
        }

        //点击去凑单，跳转到相应的专场
        holder.ll_goto_single.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, SpecialSaleActivity.class);
                intent.putExtra("mUrl", "api/promotion/activity");
                intent.putExtra("paramName0", "SpecialId");
                intent.putExtra("paramValue0", String.valueOf(dataBean.getSpecialId()));
                intent.putExtra("paramName1", "ActivityId");
                intent.putExtra("paramValue1", String.valueOf(dataBean.getActivityId()));
                intent.putExtra("cart", "isRefresh");
                ((BaseCommonActivity) context).startActivityByIntent(intent);
            }
        });

        return convertView;
    }


    public class SectionHeaderViewHolder {

        TextView tv_goods_detail_title, tv_cut_or_present, tv_reduction_limit, tv_already_present,
                tv_goods_describe, tv_already_reduce, tv_reduce_money, tv_money_unit;
        LinearLayout ll_full_cut_desribe, ll_goto_single, ll_special_condition;
        TextView tv_special_limit_price, tv_reduce, tv_fullcut_money, tv_reduce_money_unit;
        RelativeLayout rl_shoppingcar_item_title;
        public TextView tv_watch_present;
        RelativeLayout rl_watch_present;
        CheckBox checkbox_group;

    }

    private CallBack callBack;

    public void setCallBack(CallBack callBack) {
        this.callBack = callBack;
    }

    public interface CallBack {

        void refreshAdapter();

        void totalPriceAddOne(int section, int position);

        void totalPriceRemoveOne(int section, int position);

        /**
         * 获取满赠分组里面选中的总价格
         *
         * @return
         */
        double getFullPresentTotalMoney();

        /**
         * 获取满减分组里面选中的总价格
         *
         * @return
         */
        double getFullCutTotalMoney();

        /**
         * 设置分类小标题是否可以点击
         */
        void setIsCanClick(boolean isCanClick);

        /**
         * 满足满减条件的时候，总价和优惠价格的设置
         *
         * @param reduceMoney
         */
        void setTotalAndReduceMoney(double reduceMoney);


        /**
         * 刷新购物车中数据列表
         */
        void refreshShoppingCartData();

    }


    /**
     * 是否显示
     *
     * @return
     */
    public static HashMap<Integer, Boolean> getIsShow() {
        return MyShoppingCartAdapter.isShow;
    }

    public static void setIsShow(HashMap<Integer, Boolean> isShow) {
        MyShoppingCartAdapter.isShow = isShow;
    }


    /**
     * 设置每个分组的那个checkbox是否选中
     *
     * @param isGroupChecked
     */
    public static void setIsGroupChecked(HashMap<Integer, Boolean> isGroupChecked) {

        MyShoppingCartAdapter.isGroupChecked = isGroupChecked;
    }

    /**
     * 获取每个分组checkbox的选中状态
     *
     * @return
     */
    public static HashMap<Integer, Boolean> getIsGroupChecked() {
        return MyShoppingCartAdapter.isGroupChecked;
    }


    /**
     * 给出删除提示，确定删除，取消不操作
     */
    private void showDeleteWarning(final int goodsItemId, final int specialId_value, final int activityId_value) {

        new MyAlertDialog(context).builder()
                .setTitle("您确定要删除所选商品吗？")
                .setPositiveButton("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        deleteGoodsFromShoppingCart(goodsItemId, specialId_value, activityId_value);
//                        ((BaseCommonActivity) (context)).showTips(context, R.mipmap.toast_right, "删除成功");
                    }
                }).setNegativeButton("留在购物车中", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    /**
     * 点击减号删除提醒
     */
    private void showEditDeletePoint(final int ActionValue, final int itemIdValue, final int actIdValue, final int spcialIdValue,
                                     final ShoppingCartBean.DataBean.ItemsBean cartItemBean,
                                     final int sectionVal, final int positionVal) {
        new MyAlertDialog(context).builder()
                .setCancelable(false)
                .setTitle("您确定要删除此商品吗？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        editShoppingCartByHttp(ActionValue, itemIdValue, actIdValue, spcialIdValue,
                                cartItemBean, sectionVal, positionVal);
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    /**
     * 从购物车中删除商品
     */
    private void deleteGoodsFromShoppingCart(int itemId, int specialIdValue, int activityIdValue) {

        if (!NetUtil.isNetworkAvailable(context)) {
            ToastUtil.showToast(context, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }

        String url = AppConstant.BASEURL2 + "api/cart/delete";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(context, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("ItemId", String.valueOf(itemId));
        map.put("SpecialId", String.valueOf(specialIdValue));
        map.put("ActivityId", String.valueOf(activityIdValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("ItemId", String.valueOf(itemId))
                .addParams("SpecialId", String.valueOf(specialIdValue))
                .addParams("ActivityId", String.valueOf(activityIdValue))
                .addParams("Sign", signValue)
                .addParams("Timestamp", timeStamp)
                .addParams("Nonce", randomNumberTen)
                .addParams("AppId", AppConstant.appid_value)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {

                try {
                    byte[] decode = Base64.decode(response);//base64解码
                    String strJson = UTF8Util.getString(decode);
                    JSONObject jsonObject = new JSONObject(strJson);
                    int code = Integer.parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 403) {
                        DialogUtil.showDialog(context, context.getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(context, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                callBack.refreshShoppingCartData();
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("delete_shoppingcar_data", e.toString());
            }
        });

    }


    @Override
    public Object getItem(int section, int position) {
        return mData.get(section).getItems().get(position);
    }

    @Override
    public long getItemId(int section, int position) {
        return 0;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_goods_detail_title://查看赠品
                break;

        }
    }

    /**
     * 编辑购物车中商品数量
     *
     * @param ActionValue 1添加、0减少
     */
    private void editShoppingCartByHttp(final int ActionValue, int itemIdValue, int actIdValue, int spcialIdValue,
                                        final ShoppingCartBean.DataBean.ItemsBean cartItemBean,
                                        final int sectionValue, final int positionValue) {
        if (!NetUtil.isNetworkAvailable(context)) {
            ToastUtil.showToast(context, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/cart/atc";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(context, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("ItemId", String.valueOf(itemIdValue));
        map.put("SpecialId", String.valueOf(spcialIdValue));
        map.put("ActivityId", String.valueOf(actIdValue));
        map.put("Action", String.valueOf(ActionValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("ItemId", String.valueOf(itemIdValue))
                .addParams("SpecialId", String.valueOf(spcialIdValue))//不是专场里面点击编辑购物车此处都为0
                .addParams("ActivityId", String.valueOf(actIdValue))//不是专场里面点击编辑购物车此处都为0
                .addParams("Action", String.valueOf(ActionValue))
                .addParams("Sign", signValue)
                .addParams("Timestamp", timeStamp)
                .addParams("Nonce", randomNumberTen)
                .addParams("AppId", AppConstant.appid_value)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {
                try {
                    byte[] decode = Base64.decode(response);//base64解码
                    String strJson = UTF8Util.getString(decode);
                    JSONObject jsonObject = new JSONObject(strJson);
                    int code = Integer.parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 200) {
                        Gson gson = GsonUtil.buildGson();
                        EditShoppingCartGoodsBean bean = gson.fromJson(strJson,
                                EditShoppingCartGoodsBean.class);
                        EditShoppingCartGoodsBean.DataBean data = bean.getData();
//                        double benefitMoney = data.getBenefitMoney();
//                        double paidMoney = data.getPaidMoney();
//                        cartItemBean.setBuyNum(data.getQty());
                        int num = data.getNum();
                        int kind = data.getKind();
                        SPUtils.putString(context, "kindNumber", String.valueOf(kind));
                        SPUtils.putString(context, "TotalNumber", String.valueOf(num));
//                        if (cartItemBean.isCheck) {
////                            callBack.editedUpdateData(paidMoney, num, kind, benefitMoney,ActionValue);
//                            if (ActionValue == 1) {
//                                //点击加号
//                                callBack.totalPriceAddOne(sectionValue, positionValue);
//                            } else {
//                                //点击减号
//                                callBack.totalPriceRemoveOne(sectionValue, positionValue);
//                            }
//
//                        }
                        callBack.refreshShoppingCartData();

                    } else if (code == 403) {
                        DialogUtil.showDialog(context, context.getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(context, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("addShoppingCar", e.toString());
            }
        });
    }
}
