package com.runtai.newdexintong.module.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
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
import com.runtai.newdexintong.module.personalcenter.adapter.OrderDetailAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.OrderBeanItem;
import com.runtai.newdexintong.module.personalcenter.bean.OrderDetailBean;
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
 * @作者：gyp
 * @日期：2017/4/15时间08:43
 * @描述：订单详情
 */

public class OrderDetailActivity extends BaseActivity implements OrderDetailAdapter.IRefreshOrdetailCallBack {

    private List<OrderBeanItem> list;
    private RelativeLayout head_back;
    private ListView lv_order_detail;
    /**
     * 存赠品的数据
     */
    List<OrderBeanItem> presentData;
    private Map<Integer, List<OrderBeanItem>> mapdata;
    private String orderId;
    private OrderDetailBean.DataBean.OrderBean order;
    private List<OrderDetailBean.DataBean.DetailBean> detail;
    private String ordertime;
    private String ordernumber;

    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    //设置值
                    tv_realPay.setText(StringUtil.strToDouble_new(String.valueOf(order.getPaidMoney())));//实际支付
                    tv_total_money.setText(StringUtil.strToDouble_new(String.valueOf(order.getOriginalMoney())));//总价格
                    tv_reduce_money.setText(StringUtil.strToDouble_new(String.valueOf(order.getBenefitMoney())));//优惠的价格
                    tv_order_number.setText(ordernumber);
                    tv_order_time.setText(ordertime);
                    if ("".equals(order.getActivityName())) {
                        tv_activity_name.setText("平价商品");
                    } else {
                        tv_activity_name.setText(order.getActivityName());
                    }
                    int status = order.getStatus();
                    if (status == 1) {
                        tv_order_state.setText("下单成功");
                    } else if (status == 2) {
                        tv_order_state.setText("配货中");
                    } else if (status == 5) {
                        tv_order_state.setText("配送中");
                    } else if (status == 6) {
                        tv_order_state.setText("配送完成");
                    } else if (status == 7) {
                        tv_order_state.setText("已确认");
                    } 
                    break;
            }
        }
    };
    private TextView tv_realPay;
    private TextView tv_total_money;
    private TextView tv_reduce_money;
    private TextView tv_order_state;
    private TextView tv_order_number;
    private TextView tv_order_time;
    private TextView tv_activity_name;
    private TextView tv_reload;
    private RelativeLayout rl_no_net;
    private View view_line;
    private RelativeLayout rl_order_detail_bottom;
    private LinearLayout ll_orderdetail_header;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_detail);
        showLoading();
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        ordertime = intent.getStringExtra("ordertime");
        ordernumber = intent.getStringExtra("ordernumber");
        initView();
        httpData();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        lv_order_detail = (ListView) findViewById(R.id.lv_order_detail);
        View headerView = LayoutInflater.from(this).inflate(R.layout.header_order_detail, null);
        lv_order_detail.addHeaderView(headerView);
        tv_realPay = (TextView) findViewById(R.id.tv_realPay);
        tv_total_money = (TextView) findViewById(R.id.tv_total_money);
        tv_reduce_money = (TextView) findViewById(R.id.tv_reduce_money);
        tv_order_state = (TextView) findViewById(R.id.tv_order_state);
        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        tv_order_time = (TextView) findViewById(R.id.tv_order_time);
        tv_activity_name = (TextView) findViewById(R.id.tv_activity_name);
        
        view_line = findViewById(R.id.view_line);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        rl_order_detail_bottom = (RelativeLayout) findViewById(R.id.rl_order_detail_bottom);
        ll_orderdetail_header = (LinearLayout) findViewById(R.id.ll_orderdetail_header);

    }


    private void registerListener() {

        head_back.setOnClickListener(this);
        tv_reload.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("订单详情");
    }
    
    /**
     * 请求数据
     */
    private void httpData() {
        
        if (!NetUtil.isNetworkAvailable(this)) {
            lv_order_detail.setVisibility(View.GONE);
            rl_order_detail_bottom.setVisibility(View.GONE);
            ll_orderdetail_header.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            lv_order_detail.setVisibility(View.VISIBLE);
            rl_order_detail_bottom.setVisibility(View.VISIBLE);
            ll_orderdetail_header.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }
        
        String url = AppConstant.BASEURL2 + "api/order/detail";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", orderId);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Id", orderId)
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
                        OrderDetailBean orderDetailBean = gson.fromJson(strJson, OrderDetailBean.class);
                        OrderDetailBean.DataBean data = orderDetailBean.getData();
                        if (detail == null) {
                            detail = new ArrayList<OrderDetailBean.DataBean.DetailBean>();
                        }
                        detail.clear();
                        detail = data.getDetail();
                        order = data.getOrder();
                        OrderDetailAdapter orderDetailAdapter = new OrderDetailAdapter(OrderDetailActivity.this, detail, order.getId());
                        orderDetailAdapter.setRefreshOrderDetailCallBack(OrderDetailActivity.this);
                        lv_order_detail.setAdapter(orderDetailAdapter);
                        mHandler.sendEmptyMessage(10);
                    } else if (code == 403) {
                        DialogUtil.showDialog(OrderDetailActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(OrderDetailActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismissLoading();

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
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
            case R.id.tv_reload://重新加载
                httpData();
                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }

    @Override
    public void refreshOrderData() {
        httpData();
    }
}
