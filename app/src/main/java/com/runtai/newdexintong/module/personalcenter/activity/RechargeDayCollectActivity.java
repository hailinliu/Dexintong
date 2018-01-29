package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.view.Gravity;
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
import com.runtai.newdexintong.module.personalcenter.adapter.RechargeDayCollectAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.RechargeDayCollectBean;
import com.runtai.newdexintong.module.personalcenter.view.timepickerview.OptionsPopupWindow;
import com.runtai.newdexintong.module.personalcenter.view.timepickerview.TimePopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;


/**
 * 商户充值日汇总
 */
public class RechargeDayCollectActivity extends BaseActivity {

    private RelativeLayout rl_have_net;
    private RelativeLayout rl_no_data_show;
    private ListView mListView;
    private RelativeLayout rl_no_net;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private TextView tv_search;
    private int flagValue;
    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private TextView tvOptions;
    private TimePopupWindow pwTime;
    private List<RechargeDayCollectBean.DataBean.ListBean> mData;
    private LinearLayout ll_select_time_section;
    private LinearLayout ll_recharge_title;
    private TextView tv_reload;
    private TextView tv_recharge_account_money;
    private TextView tv_order_account_money;
    private TextView tv_order_account_rebate_money;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_day_collect);
        initView();
        registerListener();
        httpData();
    }

    private void registerListener() {

        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_search.setOnClickListener(this);
        tv_reload.setOnClickListener(this);
    }


    private void initView() {

        setActivityTitle();
        ll_select_time_section = (LinearLayout) findViewById(R.id.ll_select_time_section);
        ll_recharge_title = (LinearLayout) findViewById(R.id.ll_recharge_title);
        rl_have_net = (RelativeLayout) findViewById(R.id.rl_have_net);
        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        mListView = (ListView) findViewById(R.id.mListView);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);

        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_start_time.setText(MyDateUtils.getTodayTime());
        tv_end_time.setText(MyDateUtils.getNextDateTime(new Date()));
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        tv_recharge_account_money = (TextView) findViewById(R.id.tv_recharge_account_money);
        tv_order_account_money = (TextView) findViewById(R.id.tv_order_account_money);
        tv_order_account_rebate_money = (TextView) findViewById(R.id.tv_order_account_rebate_money);

        // 时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.ALL);
        // 时间选择后回调
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if (flagValue == 10) {
                    tv_start_time.setText(getTime(date));
                } else {
                    tv_end_time.setText(getTime(date));
                }

            }
        });
        // 选项选择器
        OptionsPopupWindow pwOptions = new OptionsPopupWindow(this);

        // 监听确定选择按钮
        pwOptions
                .setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2,
                                                int options3) {
                        // 返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1)
                                + options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2)
                                .get(options3);
                        tvOptions.setText(tx);
                    }
                });


    }

    private void setActivityTitle() {

        RelativeLayout head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("充值日汇总");
    }

    /**
     * 请求网络数据
     */
    private void httpData() {

        if (!NetUtil.isNetworkAvailable(this)) {
            rl_have_net.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ll_select_time_section.setVisibility(View.GONE);
            ll_recharge_title.setVisibility(View.GONE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            ll_select_time_section.setVisibility(View.VISIBLE);
            ll_recharge_title.setVisibility(View.VISIBLE);
            rl_have_net.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/store/finance";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Star", StringUtil.getUrlEncodeResult(getStartTime()));
        map.put("End", StringUtil.getUrlEncodeResult(getEndTime()));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Star", getStartTime())
                .addParams("End", getEndTime())
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
                        RechargeDayCollectBean rechargeDayCollectBean = gson.fromJson(strJson, RechargeDayCollectBean.class);
                        RechargeDayCollectBean.DataBean data = rechargeDayCollectBean.getData();
                        tv_recharge_account_money.setText(StringUtil.strToDouble_new(String.valueOf(data.getRBalance())));
                        tv_order_account_money.setText(StringUtil.strToDouble_new(String.valueOf(data.getOBalance())));
                        tv_order_account_rebate_money.setText(StringUtil.strToDouble_new(String.valueOf(data.getORBalance())));
                        if (mData == null) {
                            mData = new ArrayList<RechargeDayCollectBean.DataBean.ListBean>();
                        }
                        mData = data.getList();

                        if (mData.size() == 0) {
                            mListView.setVisibility(View.GONE);
                            rl_no_data_show.setVisibility(View.VISIBLE);
                        } else {
                            mListView.setVisibility(View.VISIBLE);
                            rl_no_data_show.setVisibility(View.GONE);
                        }
                        RechargeDayCollectAdapter rechargeDayCollectAdapter = new RechargeDayCollectAdapter(RechargeDayCollectActivity.this, mData);
                        mListView.setAdapter(rechargeDayCollectAdapter);
                    } else if (code == 403) {
                        DialogUtil.showDialog(RechargeDayCollectActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(RechargeDayCollectActivity.this, msg, Toast.LENGTH_SHORT);
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
            case R.id.tv_start_time:
                flagValue = 10;
                pwTime.showAtLocation(tv_start_time, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.tv_end_time:
                flagValue = 20;
                pwTime.showAtLocation(tv_start_time, Gravity.BOTTOM, 0, 0, MyDateUtils.getNextDate(new Date()));
                break;
            case R.id.tv_search://按时间段检索
                mData.clear();
                httpData();
                break;
            case R.id.tv_reload://重新加载
                httpData();
                break;

        }
    }

    /**
     * 获取搜索的开始时间
     *
     * @return
     */
    public String getStartTime() {

        return tv_start_time.getText().toString().trim();
    }

    /**
     * 获取搜索的结束时间
     *
     * @return
     */
    public String getEndTime() {

        return tv_end_time.getText().toString().trim();
    }

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }
}
