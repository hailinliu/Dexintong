package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.view.RoundImageView;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.personalcenter.activity.ApplyReplaceGoodsActivity;
import com.runtai.newdexintong.module.personalcenter.activity.ApplyReturnGoodsActivity;
import com.runtai.newdexintong.module.personalcenter.bean.ApplyImperfectGoodsBean;
import com.runtai.newdexintong.module.personalcenter.bean.OrderDetailBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static com.runtai.newdexintong.R.id.ll_wait_receive_goods;
import static java.lang.Integer.parseInt;

/**
 * @author：rhf
 * @date：2017/6/8time13:36
 * @detail：订单详情对应的adapter
 */

public class OrderDetailAdapter extends BaseAdapter {

    private Context context;
    private final LayoutInflater mInflater;
    private List<OrderDetailBean.DataBean.DetailBean> mData;
    private int idValue;
    private int orderId;

    private View popupWindowView;
    private PopupWindow popupWindow;
    private TextView tv_edit_goods_number;
    private int qty;


    public OrderDetailAdapter(Context context, List<OrderDetailBean.DataBean.DetailBean> data, int orderId) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        this.mData = data;
        this.orderId = orderId;
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
            convertView = mInflater.inflate(R.layout.item_order_detail, null);
            holder.item_order_name = (TextView) convertView.findViewById(R.id.item_order_name);
            holder.item_order_guige = (TextView) convertView.findViewById(R.id.item_order_guige);
            holder.item_order_danjia = (TextView) convertView.findViewById(R.id.item_order_danjia);
            holder.item_order_shuliang = (TextView) convertView.findViewById(R.id.item_order_shuliang);
            holder.item_order_zonge = (TextView) convertView.findViewById(R.id.item_order_zonge);

            holder.tv_apply_replace_goods = (TextView) convertView.findViewById(R.id.tv_apply_replace_goods);
            holder.tv_apply_return_goods = (TextView) convertView.findViewById(R.id.tv_apply_return_goods);
            holder.tv_apply_bad_goods = (TextView) convertView.findViewById(R.id.tv_apply_bad_goods);
            holder.ll_apply_order = (LinearLayout) convertView.findViewById(R.id.ll_apply_order);
            holder.ll_dispatching_show = (LinearLayout) convertView.findViewById(R.id.ll_dispatching_show);
            holder.ll_wait_receive_goods = (LinearLayout) convertView.findViewById(ll_wait_receive_goods);
            holder.ll_wait_confirm_receive_goods = (LinearLayout) convertView.findViewById(R.id.ll_wait_confirm_receive_goods);
            holder.ll_wait_confirm = (LinearLayout) convertView.findViewById(R.id.ll_wait_confirm);
            holder.view_line = convertView.findViewById(R.id.view_line);
            holder.item_order_iv = (ImageView) convertView.findViewById(R.id.item_order_iv);
            holder.ll_return_isbycar = (LinearLayout) convertView.findViewById(R.id.ll_return_isbycar);
            holder.ll_dispatching_wait_confirm = (LinearLayout) convertView.findViewById(R.id.ll_dispatching_wait_confirm);
            holder.tv_return_by_car = (TextView) convertView.findViewById(R.id.tv_return_by_car);
            holder.tv_no_return_by_car = (TextView) convertView.findViewById(R.id.tv_no_return_by_car);
            holder.tv_driver_name = (TextView) convertView.findViewById(R.id.tv_driver_name);
            holder.tv_isdirect = (TextView) convertView.findViewById(R.id.tv_isdirect);
            holder.tv_isPay = (TextView) convertView.findViewById(R.id.tv_isPay);
            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final OrderDetailBean.DataBean.DetailBean detailBean = mData.get(position);
        holder.item_order_name.setText(detailBean.getItemName());
        holder.item_order_guige.setText(detailBean.getSpecUnit());
        ImageLoader.getInstance().displayImage(detailBean.getPhoto(), holder.item_order_iv,
                ImageLoadUtil.getDefaultHeadPicOptions(), null);
        holder.item_order_danjia.setText(StringUtil.strToDouble_new(String.valueOf(detailBean.getPrice())));
        holder.item_order_shuliang.setText(String.valueOf(detailBean.getQty()));
        String itemTotalPrice = String.valueOf(detailBean.getQty() * detailBean.getPrice());
        holder.item_order_zonge.setText(StringUtil.strToDouble_new(itemTotalPrice));
        holder.tv_isPay.setText(detailBean.getPayable());

        if (detailBean.isIsDirect()) {
            //是直配商品
            holder.tv_isdirect.setVisibility(View.VISIBLE);
        } else {
            holder.tv_isdirect.setVisibility(View.GONE);
        }

        int qty = detailBean.getQty();
        String[] split = detailBean.getSpecUnit().split("/");
        final int warnNumber = qty * Integer.parseInt(split[0]);
        String operate = detailBean.getOperate();
        if ("123".equals(operate)) {
            holder.view_line.setVisibility(View.VISIBLE);
            holder.tv_apply_bad_goods.setVisibility(View.VISIBLE);
            holder.tv_apply_replace_goods.setVisibility(View.VISIBLE);
            holder.tv_apply_return_goods.setVisibility(View.VISIBLE);
            holder.ll_apply_order.setVisibility(View.VISIBLE);
        } else if ("12".equals(operate)) {
            holder.view_line.setVisibility(View.VISIBLE);
            holder.ll_apply_order.setVisibility(View.VISIBLE);
            holder.tv_apply_bad_goods.setVisibility(View.GONE);
            holder.tv_apply_replace_goods.setVisibility(View.VISIBLE);
            holder.tv_apply_return_goods.setVisibility(View.VISIBLE);
        } else if ("3".equals(operate)) {
            holder.view_line.setVisibility(View.VISIBLE);
            holder.ll_apply_order.setVisibility(View.VISIBLE);
            holder.tv_apply_bad_goods.setVisibility(View.VISIBLE);
            holder.tv_apply_replace_goods.setVisibility(View.GONE);
            holder.tv_apply_return_goods.setVisibility(View.GONE);
        } else {
            holder.view_line.setVisibility(View.GONE);
            holder.ll_apply_order.setVisibility(View.GONE);
            holder.tv_apply_bad_goods.setVisibility(View.GONE);
            holder.tv_apply_replace_goods.setVisibility(View.GONE);
            holder.tv_apply_return_goods.setVisibility(View.GONE);
        }
        int mPage = SPUtils.getInt(context, "mPageValue", 0);
        if (mPage == 1) {//下单成功
            holder.ll_wait_receive_goods.setVisibility(View.GONE);
            holder.ll_wait_confirm_receive_goods.setVisibility(View.GONE);
            holder.ll_apply_order.setVisibility(View.GONE);
            holder.ll_dispatching_show.setVisibility(View.GONE);
            holder.ll_wait_confirm.setVisibility(View.GONE);
        } else if (mPage == 2) {//配货中
            holder.view_line.setVisibility(View.VISIBLE);
            holder.ll_wait_receive_goods.setVisibility(View.VISIBLE);
            holder.ll_wait_confirm_receive_goods.setVisibility(View.GONE);
            holder.ll_apply_order.setVisibility(View.GONE);
            holder.ll_dispatching_show.setVisibility(View.GONE);
            holder.ll_wait_confirm.setVisibility(View.GONE);
        } else if (mPage == 3) {//配送中
            holder.view_line.setVisibility(View.VISIBLE);
            holder.ll_wait_receive_goods.setVisibility(View.GONE);
            holder.ll_wait_confirm_receive_goods.setVisibility(View.VISIBLE);
            holder.ll_dispatching_show.setVisibility(View.VISIBLE);
            holder.ll_wait_confirm.setVisibility(View.GONE);
            holder.tv_driver_name.setText(detailBean.getStausDes());
            if (detailBean.isIsSend()) {
                holder.ll_return_isbycar.setVisibility(View.GONE);
                holder.ll_dispatching_wait_confirm.setVisibility(View.GONE);

            } else {
                if (detailBean.getStaus() == 8) {
                    holder.ll_return_isbycar.setVisibility(View.GONE);
                    holder.ll_dispatching_wait_confirm.setVisibility(View.VISIBLE);
                } else {
                    holder.ll_return_isbycar.setVisibility(View.VISIBLE);
                    holder.ll_dispatching_wait_confirm.setVisibility(View.GONE);
                }
            }

            holder.ll_apply_order.setVisibility(View.GONE);

        } else if (mPage == 4) {//配送完成
            holder.view_line.setVisibility(View.VISIBLE);
            holder.ll_wait_receive_goods.setVisibility(View.GONE);
            holder.ll_wait_confirm_receive_goods.setVisibility(View.GONE);
            holder.ll_dispatching_show.setVisibility(View.GONE);
            holder.ll_wait_confirm.setVisibility(View.VISIBLE);
            holder.ll_apply_order.setVisibility(View.GONE);
        } else if (mPage == 5) {//已确认
            holder.ll_wait_receive_goods.setVisibility(View.GONE);
            holder.ll_wait_confirm_receive_goods.setVisibility(View.GONE);
            holder.ll_apply_order.setVisibility(View.VISIBLE);
            holder.ll_dispatching_show.setVisibility(View.GONE);
            holder.ll_wait_confirm.setVisibility(View.GONE);

        } else if (mPage == 0) {
            holder.view_line.setVisibility(View.VISIBLE);
            int statusValue = SPUtils.getInt(context, "statusValue", 0);
            if (statusValue == 1) {
                holder.view_line.setVisibility(View.GONE);
                holder.ll_wait_receive_goods.setVisibility(View.GONE);
                holder.ll_wait_confirm_receive_goods.setVisibility(View.GONE);
                holder.ll_apply_order.setVisibility(View.GONE);
                holder.ll_dispatching_show.setVisibility(View.GONE);
                holder.ll_wait_confirm.setVisibility(View.GONE);
            } else if (statusValue == 2) {
                holder.ll_wait_receive_goods.setVisibility(View.VISIBLE);
                holder.ll_wait_confirm_receive_goods.setVisibility(View.GONE);
                holder.ll_apply_order.setVisibility(View.GONE);
                holder.ll_dispatching_show.setVisibility(View.GONE);
                holder.ll_wait_confirm.setVisibility(View.GONE);
            } else if (statusValue == 5) {
                holder.ll_wait_receive_goods.setVisibility(View.GONE);
                holder.ll_wait_confirm_receive_goods.setVisibility(View.VISIBLE);
                holder.ll_apply_order.setVisibility(View.GONE);
                holder.ll_dispatching_show.setVisibility(View.VISIBLE);
                holder.ll_wait_confirm.setVisibility(View.GONE);
            } else if (statusValue == 6) {
                holder.ll_wait_receive_goods.setVisibility(View.GONE);
                holder.ll_wait_confirm_receive_goods.setVisibility(View.GONE);
                holder.ll_apply_order.setVisibility(View.GONE);
                holder.ll_dispatching_show.setVisibility(View.GONE);
                holder.ll_wait_confirm.setVisibility(View.VISIBLE);
            } else if (statusValue == 7) {
                holder.ll_wait_receive_goods.setVisibility(View.GONE);
                holder.ll_wait_confirm_receive_goods.setVisibility(View.GONE);
                holder.ll_apply_order.setVisibility(View.VISIBLE);
                holder.ll_dispatching_show.setVisibility(View.GONE);
                holder.ll_wait_confirm.setVisibility(View.GONE);
            }
        }


        //申请调货
        holder.tv_apply_replace_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApplyReplaceGoodsActivity.class);
                intent.putExtra("orderId", String.valueOf(orderId));
                intent.putExtra("orderDetailId", String.valueOf(detailBean.getId()));
                ((BaseCommonActivity) context).startActivityByIntent(intent);
            }
        });

        //申请退货
        holder.tv_apply_return_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, ApplyReturnGoodsActivity.class);
                intent.putExtra("orderId", String.valueOf(orderId));
                intent.putExtra("orderDetailId", String.valueOf(detailBean.getId()));
                ((BaseCommonActivity) context).startActivityByIntent(intent);
            }
        });
        //申请残次
        holder.tv_apply_bad_goods.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getImperfect(detailBean.getId(), detailBean.getQty());
            }
        });

        //有随车退
        holder.tv_return_by_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReturnByCarMsgDialog();
            }
        });

        //无随车退
        holder.tv_no_return_by_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showReturnByNoCarMsgDialog(warnNumber, detailBean.getId());
            }
        });

        return convertView;
    }

    /**
     * 无随车退弹框提醒
     */
    private void showReturnByNoCarMsgDialog(int wranNum, final int idVal) {

        new MyAlertDialog(context).builder()
                .setTitle("确定收到商品" + wranNum + "个吗？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        returnByNoCar(idVal);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    /**
     * 有随车退
     */
    private void showReturnByCarMsgDialog() {

        new MyAlertDialog(context).builder()
                .setTitle("您的退货将在24小时内处理并退款，请注意您的账户余额变动")
                .setPositiveButton("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    /**
     * 无随车退
     */
    private void returnByNoCar(int goodsDetailId) {

        String url = AppConstant.BASEURL2 + "api/order/retreat";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(context, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("OrderId", String.valueOf(orderId));
        map.put("Id", String.valueOf(goodsDetailId));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("OrderId", String.valueOf(orderId))
                .addParams("Id", String.valueOf(goodsDetailId))
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
                    int code = parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 200) {
                        callBack.refreshOrderData();
                    } else if (code == 403) {
                        DialogUtil.showDialog(context, context.getResources().getString(R.string.need_login_again));
                    }
                    ToastUtil.showToast(context, msg, Toast.LENGTH_SHORT);
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

    public class ViewHolder {
        TextView item_order_name, item_order_guige,
                item_order_danjia, item_order_shuliang, item_order_zonge;
        TextView tv_apply_replace_goods, tv_apply_return_goods, tv_apply_bad_goods;
        LinearLayout ll_wait_receive_goods,
                ll_dispatching_show, ll_apply_order, ll_wait_confirm_receive_goods;
        LinearLayout ll_wait_confirm;
        View view_line;
        ImageView item_order_iv;
        LinearLayout ll_return_isbycar;
        LinearLayout ll_dispatching_wait_confirm;
        TextView tv_return_by_car, tv_no_return_by_car;
        TextView tv_driver_name;
        TextView tv_isPay, tv_isdirect;
    }

    /**
     * 显示PopWindow
     */
    private void showPopopwindow(final ApplyImperfectGoodsBean.DataBean mDataBean, final int qtyVal) {

        closePopupWindow();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.popuwindow_goods_imperfect, null);

        popupWindow = new PopupWindow(popupWindowView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        RoundImageView iv_dhh_goods_picture = (RoundImageView) popupWindowView.findViewById(R.id.iv_dhh_goods_picture);
        TextView tv_dhh_goods_name = (TextView) popupWindowView.findViewById(R.id.tv_dhh_goods_name);
        TextView tv_goods_spec_num = (TextView) popupWindowView.findViewById(R.id.tv_goods_spec_num);
        TextView tv_goods_spec_unit = (TextView) popupWindowView.findViewById(R.id.tv_goods_spec_unit);
        TextView tv_limit_num = (TextView) popupWindowView.findViewById(R.id.tv_limit_num);
        tv_edit_goods_number = (TextView) popupWindowView.findViewById(R.id.tv_edit_goods_number);
        ImageView iv_reduce = (ImageView) popupWindowView.findViewById(R.id.iv_reduce);
        ImageView iv_add = (ImageView) popupWindowView.findViewById(R.id.iv_add);
        Button btn_confirm_add = (Button) popupWindowView.findViewById(R.id.btn_confirm_add);
        TextView tv_spec_num = (TextView) popupWindowView.findViewById(R.id.tv_spec_num);
        TextView tv_spec_unit = (TextView) popupWindowView.findViewById(R.id.tv_spec_unit);

        ImageLoader.getInstance().displayImage(mDataBean.getPhoto(), iv_dhh_goods_picture, ImageLoadUtil.getDefaultHeadPicOptions(), null);
        tv_dhh_goods_name.setText(mDataBean.getItemName());
        tv_goods_spec_num.setText(String.valueOf(mDataBean.getSpec()));
        tv_goods_spec_unit.setText(mDataBean.getUnit());
        final int limit_num = mDataBean.getConfirmNum() - mDataBean.getReturnNum();
        tv_limit_num.setText(String.valueOf(limit_num));
        tv_spec_num.setText(String.valueOf(mDataBean.getSpec()));
        tv_spec_unit.setText(mDataBean.getUnit());

        View view_popuwindow_bg_color = popupWindowView.findViewById(R.id.view_popuwindow_bg_color);
        //点击周边关闭popuwindow
        view_popuwindow_bg_color.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closePopupWindow();
                return false;
            }
        });

        popupWindowView.setFocusable(true);
        popupWindowView.setFocusableInTouchMode(true);
        popupWindowView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePopupWindow();
                }
                return false;
            }
        });
        //点击减号
        iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int editedNum = getEditedNum();
                if (editedNum > 1) {
                    --editedNum;
                    tv_edit_goods_number.setText(String.valueOf(editedNum));
                } else {
                    ToastUtil.showToast(context, "残次申请数量不能小于1", Toast.LENGTH_SHORT);
                }

            }
        });
        //点击加号
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int editedNum = getEditedNum();
                if (editedNum < limit_num) {
                    ++editedNum;
                    tv_edit_goods_number.setText(String.valueOf(editedNum));
                } else {
                    ToastUtil.showToast(context, "残次申请不能超过最大数量", Toast.LENGTH_SHORT);
                }
            }
        });
        //点击确认
        btn_confirm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                confirmImperfect(mDataBean.getId());
//               
                closePopupWindow();
            }
        });
        //显示PopupWindow
        popupWindow.showAtLocation(popupWindowView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 关闭窗口
     */
    private void closePopupWindow() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 获取残次商品信息
     */
    public void getImperfect(int idValue, final int qtyValue) {

        String url = AppConstant.BASEURL2 + "api/order/imperfect";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(context, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("OrderId", String.valueOf(orderId));
        map.put("Id", String.valueOf(idValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("OrderId", String.valueOf(orderId))
                .addParams("Id", String.valueOf(idValue))
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
                    int code = parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 200) {
                        Gson gson = GsonUtil.buildGson();
                        ApplyImperfectGoodsBean applyImperfectGoodsBean = gson.fromJson(strJson, ApplyImperfectGoodsBean.class);
                        ApplyImperfectGoodsBean.DataBean data = applyImperfectGoodsBean.getData();
                        showPopopwindow(data, qtyValue);
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
                LogUtil.e("search_result", e.toString());
            }
        });
    }

    /**
     * 残次处理
     */
    public void confirmImperfect(int idVa) {

        String url = AppConstant.BASEURL2 + "api/order/imperfectcreate";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(context, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("OrderId", String.valueOf(orderId));
        map.put("Id", String.valueOf(idVa));
        map.put("Num", String.valueOf(getEditedNum()));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("OrderId", String.valueOf(orderId))
                .addParams("Id", String.valueOf(idVa))
                .addParams("Num", String.valueOf(getEditedNum()))
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
                    int code = parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 200) {
                        ToastUtil.showToast(context, "成功申请残次", Toast.LENGTH_SHORT);
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
                LogUtil.e("search_result", e.toString());
            }
        });
    }

    public int getEditedNum() {
        return parseInt(tv_edit_goods_number.getText().toString().trim());
    }

    private IRefreshOrdetailCallBack callBack;

    public interface IRefreshOrdetailCallBack {

        /**
         * 取消订单或者确认收货后刷新数据
         */
        void refreshOrderData();

    }

    public void setRefreshOrderDetailCallBack(IRefreshOrdetailCallBack callBack) {

        this.callBack = callBack;
    }
}
