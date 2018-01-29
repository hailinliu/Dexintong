package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.text.TextUtils;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
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
import com.runtai.newdexintong.module.homepage.bean.ExchangePrizeBean;
import com.runtai.newdexintong.module.personalcenter.bean.ExchangeDetailBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/3/30.
 * 我要兑奖对应的adapter
 */
public class ExchangePrizeAdapter extends BaseAdapter {


    private Context mContext;
    private List<ExchangePrizeBean.DataBean.ListBean> mDatas;
    private final LayoutInflater mInflater;
    private PopupWindow popupWindow;
    private EditText et_number;
    private LinearLayout ll_popuwindow_content;
    private int nums;
    private int orders;
    private String item_goods_name;
    private int spec;
    private int aid_value;
    private int pid_value;
    private String exchange_number;
    private String award_name;


    public ExchangePrizeAdapter(Context context, List<ExchangePrizeBean.DataBean.ListBean> mDatas) {
        this.mContext = context;
        this.mDatas = mDatas;
        mInflater = LayoutInflater.from(context);
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {

        ViewHoler holder = null;
        if (convertView == null) {
            holder = new ViewHoler();
            convertView = mInflater.inflate(R.layout.item_exchange_prize, null);
            holder.tv_name = (TextView) convertView.findViewById(R.id.tv_name);
            holder.tv_now_exchange = (TextView) convertView.findViewById(R.id.tv_now_exchange);
            holder.tv_award_name = (TextView) convertView.findViewById(R.id.tv_award_name);
            holder.tv_spec = (TextView) convertView.findViewById(R.id.tv_spec);
            holder.tv_price = (TextView) convertView.findViewById(R.id.tv_price);
            holder.tv_start_time = (TextView) convertView.findViewById(R.id.tv_start_time);
            holder.tv_end_time = (TextView) convertView.findViewById(R.id.tv_end_time);
            convertView.setTag(holder);

        } else {
            holder = (ViewHoler) convertView.getTag();
        }
        final ExchangePrizeBean.DataBean.ListBean bean = mDatas.get(position);
        item_goods_name = bean.getItem();
        spec = bean.getSpec();
        aid_value = bean.getId();
        pid_value = bean.getItemId();
        award_name = bean.getName();
        holder.tv_name.setText(item_goods_name);//商品名称
        holder.tv_award_name.setText(award_name);//奖项名称
        holder.tv_spec.setText(String.valueOf(spec));
        holder.tv_price.setText(StringUtil.strToDouble_new(String.valueOf(bean.getAmount())));
        String time = bean.getTime();
        String[] split = time.split("-");
        holder.tv_start_time.setText(split[0]);
        holder.tv_end_time.setText(split[1]);

        holder.tv_now_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                httpExchangeDetail(position, bean.getId(), bean.getItemId());

            }
        });

        return convertView;
    }


    class ViewHoler {

        TextView tv_name, tv_spec, tv_now_exchange,
                tv_award_name, tv_price, tv_start_time, tv_end_time;
    }


    /**
     * 弹出popuWindow
     */
    private void showPopuWindow(int clicked_position, int total_numbers, int orders_number, final int idVa, final int itemVa) {

        closePopupWindow();
        View popupWindowView = mInflater.inflate(R.layout.popuwindow_exchange_prize,
                null);
        popupWindow = new PopupWindow(popupWindowView,
                WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT, true);

        TextView tv_exchange_name = (TextView) popupWindowView.findViewById(R.id.tv_exchange_name);
        TextView tv_exchange_spec = (TextView) popupWindowView.findViewById(R.id.tv_exchange_spec);
        TextView tv_order_number = (TextView) popupWindowView.findViewById(R.id.tv_order_number);
        TextView tv_can_use_number = (TextView) popupWindowView.findViewById(R.id.tv_can_use_number);
        et_number = (EditText) popupWindowView.findViewById(R.id.et_number);
        Button btn_confirm_exchange = (Button) popupWindowView.findViewById(R.id.btn_confirm_exchange);
        View view_popuwindow_bg_color = popupWindowView.findViewById(R.id.view_popuwindow_bg_color);
        ll_popuwindow_content = (LinearLayout) popupWindowView.findViewById(R.id.ll_popuwindow_content);
        TextView tv_can_exchange_number = (TextView) popupWindowView.findViewById(R.id.tv_can_exchange_number);
        ll_popuwindow_content.setVisibility(View.VISIBLE);
        //点击确定按钮
        btn_confirm_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                exchange_number = et_number.getText().toString().trim();
                if (TextUtils.isEmpty(exchange_number)) {
                    ToastUtil.showToast(mContext, "请输入兑奖的数量", Toast.LENGTH_SHORT);
                    return;
                }
                submitExchage(idVa, itemVa);
                closePopupWindow();
            }
        });

        ExchangePrizeBean.DataBean.ListBean listBean = mDatas.get(clicked_position);

        //赋值
        tv_order_number.setText(String.valueOf(orders_number));
        tv_can_use_number.setText(String.valueOf(total_numbers));
        tv_exchange_spec.setText(String.valueOf(listBean.getSpec()));
        tv_exchange_name.setText(listBean.getItem());
        tv_can_exchange_number.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getAmount())));

        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
        popupWindow.setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_ADJUST_RESIZE);

        //显示PopupWindow
        popupWindow.showAtLocation(popupWindowView, Gravity.BOTTOM, 0, 0);

        //点击周边消失
        view_popuwindow_bg_color.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closePopupWindow();
                return false;
            }
        });
//        popupWindowView.setFocusable(true);
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
    }

    /**
     * 读取兑奖详细
     */
    private void httpExchangeDetail(final int pos, final int idValue, final int itemIdValue) {
        if (!NetUtil.isNetworkAvailable(mContext)) {
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/change/get";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", String.valueOf(idValue));
        map.put("ItemId", String.valueOf(itemIdValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Id", String.valueOf(idValue))
                .addParams("ItemId", String.valueOf(itemIdValue))
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
                        ExchangeDetailBean exchangeDetailBean = gson.fromJson(strJson, ExchangeDetailBean.class);
                        ExchangeDetailBean.DataBean data = exchangeDetailBean.getData();
                        nums = data.getNum();
                        orders = data.getOrders();
                        showPopuWindow(pos, nums, orders, idValue, itemIdValue);
                    } else {
                        ToastUtil.showToast(mContext, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("ExchangeDetail", e.toString());
            }
        });
    }


    /**
     * 点击确定按钮后提交订单
     */
    private void submitExchage(final int id_Value, int itemId_Value) {
        if (!NetUtil.isNetworkAvailable(mContext)) {
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/change/submit";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Qty", String.valueOf(exchange_number));
        map.put("Id", String.valueOf(id_Value));
        map.put("ItemId", String.valueOf(itemId_Value));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Qty", String.valueOf(exchange_number))
                .addParams("Id", String.valueOf(id_Value))
                .addParams("ItemId", String.valueOf(itemId_Value))
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
                    ToastUtil.showToast(mContext, msg, Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("submitExchage", e.toString());
            }
        });
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
