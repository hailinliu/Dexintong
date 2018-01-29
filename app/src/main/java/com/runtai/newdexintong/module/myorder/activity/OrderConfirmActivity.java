package com.runtai.newdexintong.module.myorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
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
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.myorder.adapter.OrderConfirmAdapter;
import com.runtai.newdexintong.module.myorder.bean.OrderConfirmBean;
import com.runtai.newdexintong.module.myorder.view.PinnedHeaderListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * 订单确认页面
 */
public class OrderConfirmActivity extends BaseActivity {

    private PinnedHeaderListView order_confirm_listview;
    private RelativeLayout rl_youhuiquan;
    private RelativeLayout head_back;
    private Button btn_goto_pay;
    private String ids_selected;
    private TextView tv_reduce_money;
    private TextView tv_original_price;
    private TextView tv_realpay_money;
    private List<OrderConfirmBean.DataBean.ListBean> mData;

    private String ids_selected_str;
    private TextView tv_coupon_number;
    private int couponListSize;
    private double originalMoney;
    private double paidMoney;
    private double benefit;
    private TextView tv_selected_coupon_count;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;
    private LinearLayout ll_lay;
    private LinearLayout ll_have_data;
    private LinearLayout ll_order_confirm_bottom;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_confirm);
        showLoading();
        ids_selected_str = getIntent().getStringExtra("ids_selected");
        ids_selected = StringUtil.getUrlEncodeResult(ids_selected_str);
        initView();
        httpData();
        registerListener();
    }


    private void initView() {

        setActivityTitle();

        order_confirm_listview = (PinnedHeaderListView) findViewById(R.id.order_confirm_listview);
        addFooter(R.layout.layout_order_confirm_footer);

        rl_youhuiquan = (RelativeLayout) findViewById(R.id.rl_youhuiquan);
        btn_goto_pay = (Button) findViewById(R.id.btn_goto_pay);

        tv_reduce_money = (TextView) findViewById(R.id.tv_reduce_money);
        tv_original_price = (TextView) findViewById(R.id.tv_original_price);
        tv_realpay_money = (TextView) findViewById(R.id.tv_realpay_money);
        tv_coupon_number = (TextView) findViewById(R.id.tv_coupon_number);
        tv_selected_coupon_count = (TextView) findViewById(R.id.tv_selected_coupon_count);

        rl_no_net = (RelativeLayout)findViewById(R.id.rl_no_net);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        ll_lay = (LinearLayout) findViewById(R.id.ll_lay);
        ll_have_data = (LinearLayout) findViewById(R.id.ll_have_data);
        ll_order_confirm_bottom = (LinearLayout) findViewById(R.id.ll_order_confirm_bottom);

    }

    private void registerListener() {

        rl_youhuiquan.setOnClickListener(this);
        head_back.setOnClickListener(this);
        btn_goto_pay.setOnClickListener(this);
        tv_reload.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("订单确认");
    }

    /**
     * 添加脚布局
     *
     * @param resId 布局资源文件id
     */
    private void addFooter(int resId) {
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout footer = (LinearLayout) inflator.inflate(resId, null);
        order_confirm_listview.addFooterView(footer);
    }

    /**
     * 获取订单确认页面的数据
     */
    private void httpData() {

        if (!NetUtil.isNetworkAvailable(this)) {
            ll_lay.setVisibility(View.GONE);
            ll_have_data.setVisibility(View.GONE);
            ll_order_confirm_bottom.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            ll_lay.setVisibility(View.VISIBLE);
            ll_have_data.setVisibility(View.VISIBLE);
            ll_order_confirm_bottom.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }
        String url = AppConstant.BASEURL2 + "api/cart/confirm";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Ids", ids_selected);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Ids", ids_selected_str)
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
                        OrderConfirmBean orderConfirmBean = gson.fromJson(strJson, OrderConfirmBean.class);
                        OrderConfirmBean.DataBean data = orderConfirmBean.getData();
                        mData = new ArrayList<OrderConfirmBean.DataBean.ListBean>();
                        mData.clear();
                        mData.addAll(data.getList());
                        SPUtils.putString(OrderConfirmActivity.this, "couponData", gson.toJson(data));
                        List<OrderConfirmBean.DataBean.CouponBean> coupon = data.getCoupon();
                        if (coupon.size() == 0) {
                            tv_coupon_number.setText(String.valueOf(0));
                            couponListSize = 0;
                        }else{
                            tv_coupon_number.setText(String.valueOf(1));
                            couponListSize = 1;
                        }
                        order_confirm_listview.setAdapter(new OrderConfirmAdapter(mData, OrderConfirmActivity.this));
                        originalMoney = data.getOriginalMoney();
                        paidMoney = data.getPaidMoney();
                        benefit = data.getBenefit();
                        double benefitMoney = data.getBenefitMoney();
                        int couponBenefit = data.getCouponBenefit();
                        tv_original_price.setText(StringUtil.strToDouble_new(String.valueOf(originalMoney)));
                        tv_realpay_money.setText(StringUtil.strToDouble_new(String.valueOf(paidMoney)));
                        tv_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(benefitMoney)));
                        if (coupon != null && coupon.size() > 0) {
                            if (coupon.get(0).isIsUseCoupon()) {
                                tv_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(benefitMoney)));
                                tv_selected_coupon_count.setText("1");
                            } else {
                                tv_selected_coupon_count.setText("0");
                                tv_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(benefitMoney)));
                            }
                        } else {
                            tv_selected_coupon_count.setText("0");
                            tv_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(benefitMoney)));
                        }

                    } else if (code == 403) {
                        DialogUtil.showDialog(OrderConfirmActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(OrderConfirmActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("order_confirm", e.toString());
                dismissLoading();
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.rl_youhuiquan://打开选择优惠劵的页面
                if (couponListSize > 0) {
                    Intent intent_select_counpon = new Intent(this, SelectCouponActivity.class);
                    startActivityForResultByIntent(intent_select_counpon, 66);
                } else {
                    ToastUtil.showToast(OrderConfirmActivity.this, "暂时没有可用的优惠券", Toast.LENGTH_SHORT);
                }
                break;

            case R.id.btn_goto_pay://去支付

                Intent intent = new Intent(this, ConfirmPayActivity.class);
                intent.putExtra("ids_selected", ids_selected_str);
                intent.putExtra("realpay_money", getRealPay());
                startActivityByIntent(intent);
                finish();
                break;
            case R.id.tv_reload://重新加载
                httpData();
                break;


        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (data == null) {
            return;
        }
        if (requestCode == 66) {
            switch (resultCode) {
                case 33://使用优惠劵
//                    double couponMoney = Double.parseDouble(data.getStringExtra("couponMoney"));
//                    tv_original_price.setText(StringUtil.strToDouble_new(String.valueOf(originalMoney)));
//                    double v = Double.parseDouble(getRealPay()) - getReduceMoney();
//                    tv_realpay_money.setText(StringUtil.strToDouble_new(String.valueOf(Double.parseDouble(getRealPay()) - couponMoney)));
//                    tv_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(benefit + couponMoney))); 
                    httpData();
                    break;
                case 55://不使用优惠劵
                    httpData();
//                    tv_original_price.setText(StringUtil.strToDouble_new(String.valueOf(originalMoney)));
//                    tv_realpay_money.setText(StringUtil.strToDouble_new(String.valueOf(paidMoney)));
//                    tv_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(benefit)));
                    break;
            }
        }
    }

    /**
     * 获得实际支付的价钱
     *
     * @return
     */
    public String getRealPay() {
        return tv_realpay_money.getText().toString().trim();
    }

    /**
     * 获取优惠的金额
     *
     * @return
     */
    public double getReduceMoney() {
        return Double.parseDouble(tv_reduce_money.getText().toString().trim());
    }

}
