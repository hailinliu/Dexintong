package com.runtai.newdexintong.module.myorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
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
import com.runtai.newdexintong.module.myorder.adapter.SelectCouponAdapter;
import com.runtai.newdexintong.module.myorder.bean.OrderConfirmBean;
import com.runtai.newdexintong.module.personalcenter.bean.MyCouponBean;
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
 * 选择优惠劵界面
 */
public class SelectCouponActivity extends BaseActivity {

    private ListView lv_select_coupon;
    private SelectCouponAdapter adapter;
    private RelativeLayout head_back;
    private TextView tv_subtitle;
    private boolean isFirst = true;
    private List<MyCouponBean.DataBean.ListBean> listData;
    private TextView tv_no_data;
    private int pages;
    private int total;
    private int currentPage = 2;
    /**
     * 每页显示的条目数量
     */
    private int pageItemCount = 20;
    private List<OrderConfirmBean.DataBean.CouponBean> couponList;
    private List<OrderConfirmBean.DataBean.CouponBean> newCouponList;
    private int couponId;
    private int tagValue = 100;
    private boolean ischecked = false;
    private int selectedPosition;
    private SelectCouponAdapter selectCouponAdapter;
    private int couponBenefit;
    private boolean isSelected;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_youhuiquan);

        initView();
        setData();
//        httpCouponData(1);
        registerListener();
    }

    private void setData() {

        String couponData = SPUtils.getString(SelectCouponActivity.this, "couponData", "");
        Gson gson = GsonUtil.buildGson();
        OrderConfirmBean.DataBean dataBean = gson.fromJson(couponData, OrderConfirmBean.DataBean.class);

        if (dataBean != null) {
            if (couponList == null) {
                couponList = new ArrayList<>();

            }
            if (newCouponList == null) {
                newCouponList = new ArrayList<>();
            }
            newCouponList.clear();
            couponList.clear();
            couponList = dataBean.getCoupon();
            for (int i = 0; i < 1; i++) {
                //只取优惠券第一张
                newCouponList.add(couponList.get(0));
            }

            couponId = newCouponList.get(0).getCouponId();
            couponBenefit = newCouponList.get(0).getCouponBenefit();
            if (newCouponList.get(0).isIsUseCoupon()) {
                newCouponList.get(0).isSelected = true;
            } else {
                newCouponList.get(0).isSelected = false;
            }
            selectCouponAdapter = new SelectCouponAdapter(SelectCouponActivity.this, newCouponList);
            lv_select_coupon.setAdapter(selectCouponAdapter);

        } else {
            DialogUtil.showDialog(this, getResources().getString(R.string.need_login_again));
        }
    }

    private void initView() {
        setActivityTitle();

        lv_select_coupon = (ListView) findViewById(R.id.lv_select_coupon);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }


    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("选择优惠劵");

        tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("确定");
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        tv_subtitle.setOnClickListener(this);
        lv_select_coupon.setOnItemClickListener(new AdapterView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                //为了实现listview条目的单选
                SelectCouponAdapter.ViewHolder holder = (SelectCouponAdapter.ViewHolder) view.getTag();
                holder.checkbox.toggle();
                selectedPosition = position;
                couponId = newCouponList.get(0).getCouponId();
                isSelected = newCouponList.get(0).isSelected;
                
                if (isSelected) {
                    newCouponList.get(0).isSelected = false;
                } else {
                    newCouponList.get(0).isSelected = true;
                }

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
            case R.id.tv_subtitle://点击确定按钮
                if (!isSelected) {
                    useCouponByHttp("1");
                } else {
                    useCouponByHttp("0");
                }
                break;

        }
    }


    /**
     * 通过联网走接口去使用优惠券
     */
    private void useCouponByHttp(final String flagValue) {

        if (!NetUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
//        1使用，0不使用
//        ids string 专场编号:活动编号_专场编号:活动编号，如：1:2_1:2
        String url = AppConstant.BASEURL2 + "api/cart/voucher";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", String.valueOf(couponId));
        map.put("Action", flagValue);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Id", String.valueOf(couponId))
                .addParams("Action", flagValue)
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
                    ToastUtil.showToast(SelectCouponActivity.this, msg, Toast.LENGTH_SHORT);
                    if (code == 200) {
                        if ("1".equals(flagValue)) {
                            Intent intent = new Intent();
                            intent.putExtra("isUseCoupon", true);
                            intent.putExtra("couponMoney", String.valueOf(couponBenefit));
                            setResult(33, intent);
                        } else if ("0".equals(flagValue)) {
                            Intent intent2 = new Intent();
                            intent2.putExtra("isUseCoupon", false);
                            setResult(55, intent2);
                        }
                        finish();
                    } else if (code == 403) {
                        DialogUtil.showDialog(SelectCouponActivity.this, getResources().getString(R.string.need_login_again));
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("unusedcoupon", e.toString());
            }
        });

    }

}
