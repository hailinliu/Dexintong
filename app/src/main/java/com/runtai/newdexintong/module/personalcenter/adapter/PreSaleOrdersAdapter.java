package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
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
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.view.RoundImageView;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.personalcenter.bean.DhhGoodsBean;
import com.runtai.newdexintong.module.personalcenter.bean.PreSaleOrdersBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static com.runtai.newdexintong.R.id.iv_pre_sale_goods_pic;

/**
 * @author：rhf
 * @date：2017/8/3time19:00
 * @detail：预售订单对应的适配器
 */

public class PreSaleOrdersAdapter extends BaseAdapter {


    private Context mContext;
    private List<PreSaleOrdersBean.DataBean.ListBean> mData;
    private final LayoutInflater mInflater;
    private PopupWindow popupWindow;
    private View popupWindowView;
    private DhhGoodsBean.DataBean dhhgoodsData;
    
    private TextView tv_edit_goods_number;

    public PreSaleOrdersAdapter(Context context, List<PreSaleOrdersBean.DataBean.ListBean> mData) {
        this.mContext = context;
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
    public View getView(final int position, View convertView, ViewGroup parent) {
        
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_presale_orders, null);
            holder.tv_order_number = (TextView) convertView.findViewById(R.id.tv_order_number);
            holder.tv_order_time = (TextView) convertView.findViewById(R.id.tv_order_time);

            holder.iv_pre_sale_goods_pic = (ImageView) convertView.findViewById(iv_pre_sale_goods_pic);
            holder.tv_preorder_goods_name = (TextView) convertView.findViewById(R.id.tv_preorder_goods_name);
            holder.tv_preorder_goods_price = (TextView) convertView.findViewById(R.id.tv_preorder_goods_price);
            holder.tv_spec = (TextView) convertView.findViewById(R.id.tv_spec);
            holder.tv_spec_unit = (TextView) convertView.findViewById(R.id.tv_spec_unit);
            holder.tv_item_number_total = (TextView) convertView.findViewById(R.id.tv_item_number_total);
            holder.tv_item_number_text = (TextView)convertView.findViewById(R.id.tv_item_number_text);
            holder.tv_item_number = (TextView) convertView.findViewById(R.id.tv_item_number);
            holder.tv_item_price = (TextView) convertView.findViewById(R.id.tv_item_price);

            holder.tv_real_pay_money = (TextView) convertView.findViewById(R.id.tv_real_pay_money);
            holder.btn_delivery = (Button) convertView.findViewById(R.id.btn_delivery);
            holder.ll_pre_sale_orders_status2 = (LinearLayout) convertView.findViewById(R.id.ll_pre_sale_orders_status2);
            holder.view_line = convertView.findViewById(R.id.view_line);

            convertView.setTag(holder);

        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        final PreSaleOrdersBean.DataBean.ListBean listBean = mData.get(position);
        ImageLoader.getInstance().displayImage(listBean.getPhoto(), holder.iv_pre_sale_goods_pic,
                ImageLoadUtil.getDefaultHeadPicOptions(), null);
        holder.tv_preorder_goods_name.setText(listBean.getItemName());
        holder.tv_spec.setText(listBean.getSpecDesc());
        holder.tv_preorder_goods_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getPrice())));


        int status = listBean.getStatus();
        if (status == 2) {
            //发货总量
           // holder.tv_item_number_total.setText("总量为:"+String.valueOf(listBean.getBuyNum()));
            //已发货数量
            holder.tv_item_number.setText(String.valueOf(listBean.getBuyNum()));
            holder.tv_item_price.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getPrice() * listBean.getBuyNum())));
        } else {
            if(status == 1){
                //发货总量
                holder.tv_item_number_total.setText("总量为:"+String.valueOf(listBean.getBuyNum())); 
            }
           if(status == 0){
               holder.tv_item_number_text.setText("总量为:");
           }
            int transfromingNum = listBean.getBuyNum() - listBean.getTransformedNum();//正在转单的数量
            holder.tv_item_number.setText(String.valueOf(transfromingNum));
            double itemPrice = listBean.getPrice() * transfromingNum;
            holder.tv_item_price.setText(StringUtil.strToDouble_new(String.valueOf(itemPrice)));
        }
        if (status == 0 || status == 1) {
            holder.ll_pre_sale_orders_status2.setVisibility(View.VISIBLE);
            holder.view_line.setVisibility(View.VISIBLE);
        } else {
            holder.ll_pre_sale_orders_status2.setVisibility(View.GONE);
            holder.view_line.setVisibility(View.GONE);
        }

        //点击发货按钮
        holder.btn_delivery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGoodsDetail(listBean.getId(),position);

            }
        });
        return convertView;
    }

    /**
     * 获取订货会订单详情
     */
    private void getGoodsDetail(int idValue, final int clickedPos) {
        
        if (!NetUtil.isNetworkAvailable(mContext)) {
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }

        String url = AppConstant.BASEURL2 + "api/dhh/item";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", String.valueOf(idValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
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
                    int code = Integer.parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 200) {
                        Gson gson = GsonUtil.buildGson();
                        DhhGoodsBean dhhGoodsBean = gson.fromJson(strJson, DhhGoodsBean.class);
                        dhhgoodsData = dhhGoodsBean.getData();
                        showPopopwindow(dhhgoodsData,clickedPos);
                    } else if (code == 403) {
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

    private class ViewHolder {

        TextView tv_order_number, tv_order_time;
        ImageView iv_pre_sale_goods_pic;
        TextView tv_preorder_goods_name, tv_preorder_goods_price, tv_spec, tv_spec_unit;
        TextView tv_item_number_total, tv_item_number_text,tv_item_number,tv_item_price;
        TextView tv_real_pay_money;
        TextView tv_status;
        Button btn_delivery;
        LinearLayout ll_pre_sale_orders_status2;
        View view_line;

    }

    /**
     * 显示PopWindow
     */
    private void showPopopwindow(final DhhGoodsBean.DataBean bean, final int pos) {

        closePopupWindow();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.popuwindow_goods_detail_dhh, null);

        popupWindow = new PopupWindow(popupWindowView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        RoundImageView iv_dhh_goods_picture = (RoundImageView) popupWindowView.findViewById(R.id.iv_dhh_goods_picture);
        TextView tv_dhh_goods_name = (TextView) popupWindowView.findViewById(R.id.tv_dhh_goods_name);
        TextView tv_dhh_goods_spec_num = (TextView) popupWindowView.findViewById(R.id.tv_dhh_goods_spec_num);
        TextView tv_goods_spec_unit = (TextView) popupWindowView.findViewById(R.id.tv_goods_spec_unit);
        TextView tv_limit_num = (TextView) popupWindowView.findViewById(R.id.tv_limit_num);
        tv_edit_goods_number = (TextView) popupWindowView.findViewById(R.id.tv_edit_goods_number);
        ImageView iv_reduce = (ImageView) popupWindowView.findViewById(R.id.iv_reduce);
        ImageView iv_add = (ImageView) popupWindowView.findViewById(R.id.iv_add);
        Button btn_confirm_add = (Button) popupWindowView.findViewById(R.id.btn_confirm_add);
        TextView tv_total_order_num = (TextView) popupWindowView.findViewById(R.id.tv_total_order_num);

        ImageLoader.getInstance().displayImage(bean.getPhoto(), iv_dhh_goods_picture, ImageLoadUtil.getDefaultHeadPicOptions(), null);
        tv_dhh_goods_name.setText(bean.getItemName());
        tv_dhh_goods_spec_num.setText(String.valueOf(bean.getSpec()));
        tv_goods_spec_unit.setText(bean.getUnit());
        tv_limit_num.setText(String.valueOf(bean.getStock()));
        tv_total_order_num.setText(String.valueOf(bean.getBuyNum()));

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
                    ToastUtil.showToast(mContext, "发货数量不能小于1", Toast.LENGTH_SHORT);
                }

            }
        });
        //点击加号
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int editedNum = getEditedNum();
                if (editedNum < bean.getStock()) {
                    ++editedNum;
                    tv_edit_goods_number.setText(String.valueOf(editedNum));
                } else {
                    ToastUtil.showToast(mContext, "订货会转单的数量不能超过最大数量", Toast.LENGTH_SHORT);
                }
            }
        });
        //点击确认
        btn_confirm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                confirmHttp(bean.getId(),pos);


            }
        });
        //显示PopupWindow
        popupWindow.showAtLocation(popupWindowView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 订货会转单
     */
    private void confirmHttp(int idVal, final int confirmPos) {

        if (!NetUtil.isNetworkAvailable(mContext)) {
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/dhh/order";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Qty", String.valueOf(getEditedNum()));
        map.put("Id", String.valueOf(idVal));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Qty", String.valueOf(getEditedNum()))
                .addParams("Id", String.valueOf(idVal))
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
                        mData.remove(mData.get(confirmPos));
                        notifyDataSetChanged();
                        closePopupWindow();
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
     * 获取当前编辑框中商品数量
     *
     * @return
     */
    public int getEditedNum() {
        return Integer.parseInt(tv_edit_goods_number.getText().toString().trim());
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


}
