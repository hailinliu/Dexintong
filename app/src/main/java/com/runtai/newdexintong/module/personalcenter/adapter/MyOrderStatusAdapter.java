package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.personalcenter.activity.EvaluateGoodsActivity;
import com.runtai.newdexintong.module.personalcenter.bean.MyOrderBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * @author：rhf
 * @date：2017/7/6time14:43
 * @detail：我的订单状态对应的适配器
 */

public class MyOrderStatusAdapter extends BaseAdapter {


    private Context mContext;
    private List<MyOrderBean.DataBean.ListBean> mData;
    private final LayoutInflater myInflater;


    public MyOrderStatusAdapter(Context mContext, List<MyOrderBean.DataBean.ListBean> list) {
        this.mContext = mContext;
        this.mData = list;
        myInflater = LayoutInflater.from(mContext);
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        MyViewHolder holder;
        if (convertView == null) {
            holder = new MyViewHolder();
            convertView = myInflater.inflate(R.layout.item_myorder_goods, null);
            holder.tv_order_state = (TextView) convertView.findViewById(R.id.tv_order_state);
            holder.tv_completed = (TextView) convertView.findViewById(R.id.tv_completed);
            holder.tv_cancle_order = (TextView) convertView.findViewById(R.id.tv_cancle_order);
            holder.rl_bottom_button = (RelativeLayout) convertView.findViewById(R.id.rl_bottom_button);

            holder.ll_delievry = (LinearLayout) convertView.findViewById(R.id.ll_delievry);

            holder.ll_no_more = (LinearLayout) convertView.findViewById(R.id.ll_no_more);
            holder.iv_goods_icon1 = (ImageView) convertView.findViewById(R.id.iv_goods_icon1);
            holder.iv_goods_icon2 = (ImageView) convertView.findViewById(R.id.iv_goods_icon2);
            holder.iv_goods_icon3 = (ImageView) convertView.findViewById(R.id.iv_goods_icon3);

            holder.tv_order_goods_number = (TextView) convertView.findViewById(R.id.tv_order_goods_number);
            holder.tv_confirmed = (TextView) convertView.findViewById(R.id.tv_confirmed);

            holder.tv_order_number = (TextView) convertView.findViewById(R.id.tv_order_number);//订单号
            holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);//订单时间
            holder.tv_special_area_reduce_money = (TextView) convertView.findViewById(R.id.tv_special_area_reduce_money);//专场优惠
            holder.tv_coupon_money = (TextView) convertView.findViewById(R.id.tv_coupon_money);//优惠券金额
            holder.tv_order_total_money = (TextView) convertView.findViewById(R.id.tv_order_total_money);//订单总金额
            holder.tv_real_pay_money = (TextView) convertView.findViewById(R.id.tv_real_pay_money);//实际支付价格

            holder.rl_evaluate = (RelativeLayout) convertView.findViewById(R.id.rl_evaluate);
            holder.tv_go_to_evaluate = (TextView) convertView.findViewById(R.id.tv_go_to_evaluate);

            holder.tv_order_pay_style = (TextView) convertView.findViewById(R.id.tv_order_pay_style);
            holder.tv_dispatching = (TextView) convertView.findViewById(R.id.tv_dispatching);
            convertView.setTag(holder);
        } else {
            holder = (MyViewHolder) convertView.getTag();
        }
        final MyOrderBean.DataBean.ListBean listBean = mData.get(position);

        holder.tv_order_number.setText(listBean.getOrder().getOrderNum());
        holder.tv_special_area_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getOrder().getBenefitMoney())));
        holder.tv_coupon_money.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getOrder().getCouponBenefit())));
        holder.tv_order_total_money.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getOrder().getOriginalMoney())));
        holder.tv_real_pay_money.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getOrder().getPaidMoney())));
        holder.tv_order_time.setText(listBean.getOrder().getCreateTime());
//        0到付，1订货账户余额支付，2返利账户余额支付

        int isPrePaid = listBean.getOrder().getIsPrePaid();
        if (isPrePaid == 0) {
            holder.tv_order_pay_style.setText("到付订单");
        } else if (isPrePaid == 1){
            holder.tv_order_pay_style.setText("订货账户预付订单");
        }else {
            holder.tv_order_pay_style.setText("返利账户预付订单");
        }
        List<String> detailPic = listBean.getOrder().getPhoto();
//        List<String> detailPic = listBean.getDetail();
        int goodsNumber = detailPic.size();
        holder.tv_order_goods_number.setText(String.valueOf(goodsNumber));
        if (goodsNumber > 3) {
            holder.ll_no_more.setVisibility(View.VISIBLE);
            holder.iv_goods_icon1.setVisibility(View.VISIBLE);
            holder.iv_goods_icon2.setVisibility(View.VISIBLE);
            holder.iv_goods_icon3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(detailPic.get(0), holder.iv_goods_icon1,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
            ImageLoader.getInstance().displayImage(detailPic.get(1), holder.iv_goods_icon2,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
            ImageLoader.getInstance().displayImage(detailPic.get(2), holder.iv_goods_icon3,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
        } else if (goodsNumber == 1) {
            holder.ll_no_more.setVisibility(View.GONE);
            holder.iv_goods_icon1.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(detailPic.get(0), holder.iv_goods_icon1,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
            holder.iv_goods_icon2.setVisibility(View.GONE);
            holder.iv_goods_icon3.setVisibility(View.GONE);
        } else if (goodsNumber == 2) {
            holder.ll_no_more.setVisibility(View.GONE);
            holder.iv_goods_icon1.setVisibility(View.VISIBLE);
            holder.iv_goods_icon2.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(detailPic.get(0), holder.iv_goods_icon1,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
            ImageLoader.getInstance().displayImage(detailPic.get(1), holder.iv_goods_icon2,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
            holder.iv_goods_icon3.setVisibility(View.GONE);
        } else if (goodsNumber == 3) {
            holder.ll_no_more.setVisibility(View.GONE);
            holder.iv_goods_icon1.setVisibility(View.VISIBLE);
            holder.iv_goods_icon2.setVisibility(View.VISIBLE);
            holder.iv_goods_icon3.setVisibility(View.VISIBLE);
            ImageLoader.getInstance().displayImage(detailPic.get(0), holder.iv_goods_icon1,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
            ImageLoader.getInstance().displayImage(detailPic.get(1), holder.iv_goods_icon2,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
            ImageLoader.getInstance().displayImage(detailPic.get(2), holder.iv_goods_icon3,
                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
        }

        int status = listBean.getOrder().getStatus();
        int specialId = listBean.getOrder().getSpecialId();
        if (status == 1) {
            holder.tv_order_state.setText("下单成功");

            if (specialId == 0 || specialId == 1 || specialId == 2 || specialId == 3 || specialId == 6) {//specialId等于4或者5的时候表示是订货会转单过来的订单
                holder.rl_bottom_button.setVisibility(View.VISIBLE);
                holder.tv_cancle_order.setVisibility(View.VISIBLE);
            } else {
                holder.rl_bottom_button.setVisibility(View.GONE);
                holder.tv_cancle_order.setVisibility(View.GONE);
            }
            holder.tv_completed.setVisibility(View.GONE);
            holder.ll_delievry.setVisibility(View.GONE);
            holder.tv_dispatching.setVisibility(View.GONE);
            holder.rl_evaluate.setVisibility(View.GONE);
        } else if (status == 2) {
            holder.tv_order_state.setText("配货中");
            holder.rl_bottom_button.setVisibility(View.VISIBLE);
            holder.tv_cancle_order.setVisibility(View.GONE);
            holder.tv_completed.setVisibility(View.VISIBLE);
            holder.tv_completed.setText("等待配送...");
            holder.ll_delievry.setVisibility(View.GONE);
            holder.tv_dispatching.setVisibility(View.GONE);
            holder.rl_evaluate.setVisibility(View.GONE);
        } else if (status == 5) {
            holder.tv_order_state.setText("配送中");
            holder.tv_dispatching.setVisibility(View.VISIBLE);
            holder.rl_bottom_button.setVisibility(View.GONE);
            holder.tv_cancle_order.setVisibility(View.GONE);
            holder.tv_completed.setVisibility(View.GONE);
            holder.ll_delievry.setVisibility(View.GONE);
            holder.tv_dispatching.setVisibility(View.VISIBLE);

            holder.rl_evaluate.setVisibility(View.GONE);
        } else if (status == 6) {
            holder.tv_order_state.setText("配送完成");
            holder.rl_bottom_button.setVisibility(View.GONE);
            holder.tv_cancle_order.setVisibility(View.GONE);
            holder.tv_completed.setVisibility(View.GONE);
            holder.ll_delievry.setVisibility(View.VISIBLE);
            holder.tv_dispatching.setVisibility(View.GONE);
            holder.rl_evaluate.setVisibility(View.GONE);
        } else if (status == 7) {
            holder.tv_order_state.setText("已确认");
            holder.rl_bottom_button.setVisibility(View.GONE);
            holder.tv_cancle_order.setVisibility(View.GONE);
            holder.tv_completed.setVisibility(View.GONE);
            holder.ll_delievry.setVisibility(View.GONE);
            holder.tv_dispatching.setVisibility(View.GONE);
            holder.rl_evaluate.setVisibility(View.VISIBLE);
        }


        /**
         * 取消订单
         */
        holder.tv_cancle_order.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showCancleOrderDialog(mData.get(position).getOrder().getId(), listBean);
            }
        });
        //确认订单
        holder.tv_confirmed.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showConfirmDialog(mData.get(position).getOrder().getId(), listBean);
            }
        });
        //去评价
        holder.tv_go_to_evaluate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext, EvaluateGoodsActivity.class);
                intent.putExtra("orderId", String.valueOf(mData.get(position).getOrder().getId()));
                ((BaseCommonActivity) mContext).startActivityByIntent(intent);
            }
        });

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

    /**
     * 弹出确认收货的对话框
     */
    private void showConfirmDialog(final int orderId, final MyOrderBean.DataBean.ListBean bean) {
        new MyAlertDialog(mContext).builder()
                .setTitle("请点清楚商品数量，检查质量无误!")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        confirmOrder(orderId, bean);
//                        ((BaseCommonActivity) (mContext)).showTips(mContext, R.mipmap.toast_right, "确认成功");
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    /**
     * 确认订单
     *
     * @param order_Id
     */
    private void confirmOrder(int order_Id, final MyOrderBean.DataBean.ListBean dataBean) {

        if (!NetUtil.isNetworkAvailable(mContext)) {
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }

        String url = AppConstant.BASEURL2 + "api/order/confirms";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", String.valueOf(order_Id));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Id", String.valueOf(order_Id))
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
                        mData.remove(dataBean);
                        notifyDataSetChanged();
//                        callBack.refreshOrderData(SPUtils.getString(mContext, "mPageValue", ""));
                    }
                    if (code == 403) {
                        DialogUtil.showDialog(mContext, mContext.getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(mContext, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
            }
        });
    }

    /**
     * 弹出取消订单的对话框
     */
    private void showCancleOrderDialog(final int orderIdValue, final MyOrderBean.DataBean.ListBean bean) {
        new MyAlertDialog(mContext).builder()
                .setTitle("您确定要取消订单吗？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        cancleOrderByHttp(orderIdValue, bean);

                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    /**
     * 通过接口取消订单
     */
    private void cancleOrderByHttp(int orderId_value, final MyOrderBean.DataBean.ListBean dataBean) {

        if (!NetUtil.isNetworkAvailable(mContext)) {
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/order/cancel";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", String.valueOf(orderId_value));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Id", String.valueOf(orderId_value))
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
//                        callBack.refreshOrderData(SPUtils.getString(mContext, "mPageValue", ""));
                        mData.remove(dataBean);
                        notifyDataSetChanged();
                    }
                    if (code == 403) {
                        DialogUtil.showDialog(mContext, mContext.getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(mContext, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
            }
        });
    }


    class MyViewHolder {
        /**
         * 订单状态
         */
        TextView tv_order_state;
        TextView tv_completed;//状态描述：已完成，配送中等
        /**
         * 取消订单按钮
         */
        TextView tv_cancle_order;//取消订单按钮
        LinearLayout ll_delievry;
        RelativeLayout rl_bottom_button, rl_evaluate;
        ImageView iv_goods_icon1, iv_goods_icon2, iv_goods_icon3;
        /**
         * 存放省略号
         */
        LinearLayout ll_no_more;
        TextView tv_order_goods_number;

        TextView tv_confirmed, tv_dispatching;
        TextView tv_order_number, tv_order_time;
        TextView tv_special_area_reduce_money, tv_coupon_money, tv_order_total_money, tv_real_pay_money;
        TextView tv_go_to_evaluate;
        TextView tv_order_pay_style;

    }


}
