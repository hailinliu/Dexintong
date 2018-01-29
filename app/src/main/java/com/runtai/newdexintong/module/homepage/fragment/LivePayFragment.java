package com.runtai.newdexintong.module.homepage.fragment;

import android.os.Bundle;
import android.text.InputFilter;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StrUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.homepage.activity.FlowRechargeActivity;
import com.runtai.newdexintong.module.homepage.activity.MobilePackageActivity;
import com.runtai.newdexintong.module.homepage.activity.QbRechargeActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * @author：rhf
 * @date：2017/7/29time15:58
 * @detail：生活缴费
 */

public class LivePayFragment extends BaseFragment {

    private LinearLayout ll_water_charge;
    private LinearLayout ll_power_charge;
    private LinearLayout ll_gas_recharge;
    private TextView tv_recharge_now;
    private int recharge_sort_value = 3;
    private EditText et_user_number;
    private EditText et_pay_money;
    private EditText et_phone_number;
    private String userNumber;
    private String pay_money;
    private String phone_number;
    private LinearLayout ll_q_money_recharge;
    private LinearLayout ll_mobile_package;
    private LinearLayout ll_flow_recharge;
    private LinearLayout ll_more;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_live_pay);
        View view = getContentView();
        initView(view);
        registerListener();
    }

    private void initView(View view) {

        ll_q_money_recharge = (LinearLayout) view.findViewById(R.id.ll_q_money_recharge);
        ll_mobile_package = (LinearLayout) view.findViewById(R.id.ll_mobile_package);
        ll_flow_recharge = (LinearLayout) view.findViewById(R.id.ll_flow_recharge);
        ll_more = (LinearLayout) view.findViewById(R.id.ll_more);

        ll_water_charge = (LinearLayout) view.findViewById(R.id.ll_water_charge);
        ll_power_charge = (LinearLayout) view.findViewById(R.id.ll_power_charge);
        ll_gas_recharge = (LinearLayout) view.findViewById(R.id.ll_gas_recharge);

        tv_recharge_now = (TextView) view.findViewById(R.id.tv_recharge_now);

        et_user_number = (EditText) view.findViewById(R.id.et_user_number);
        et_pay_money = (EditText) view.findViewById(R.id.et_pay_money);
        et_phone_number = (EditText) view.findViewById(R.id.et_phone_number);

    }

    private void registerListener() {

        ll_water_charge.setOnClickListener(this);
        ll_power_charge.setOnClickListener(this);
        ll_gas_recharge.setOnClickListener(this);
        tv_recharge_now.setOnClickListener(this);

        ll_q_money_recharge.setOnClickListener(this);
        ll_mobile_package.setOnClickListener(this);
        ll_flow_recharge.setOnClickListener(this);
        ll_more.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_water_charge://选择水费
//                缴费类型：3：水费，4：电费，5：燃气费
                recharge_sort_value = 3;
                clearSelected();
                ll_water_charge.setBackgroundColor(getResources().getColor(R.color.live_recharge_background));
                et_user_number.setText("");
                et_pay_money.setText("");
                et_phone_number.setText("");
                et_user_number.setHint(getEdittextHintText("输入7位户号"));
                et_user_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(7)});//限制输入内容字数

                break;
            case R.id.ll_power_charge://电费充值
                recharge_sort_value = 4;
                clearSelected();
                ll_power_charge.setBackgroundColor(getResources().getColor(R.color.live_recharge_background));
                et_user_number.setText("");
                et_pay_money.setText("");
                et_phone_number.setText("");
                et_user_number.setHint(getEdittextHintText("输入10位户号"));
                et_user_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});//限制输入内容字数
                break;
            case R.id.ll_gas_recharge://燃气充值
                recharge_sort_value = 5;
                clearSelected();
                ll_gas_recharge.setBackgroundColor(getResources().getColor(R.color.live_recharge_background));
                et_user_number.setText("");
                et_pay_money.setText("");
                et_phone_number.setText("");
                et_user_number.setHint(getEdittextHintText("输入10位户号"));
                et_user_number.setFilters(new InputFilter[]{new InputFilter.LengthFilter(10)});//限制输入内容字数
                break;
            case R.id.tv_recharge_now://点击立即充值
                if (checkMessage()) {
                    showDialog(getPhoneNumber(), getRechargeMoney());
                }

                break;

            case R.id.ll_q_money_recharge://Q币充值
                startActivityByIntent(QbRechargeActivity.class);
                break;
            case R.id.ll_mobile_package://移动存送套餐
                startActivityByIntent(MobilePackageActivity.class);
                break;
            case R.id.ll_flow_recharge://流量充值
                startActivityByIntent(FlowRechargeActivity.class);
                break;
            case R.id.ll_more://更多
                ToastUtil.showToast(getActivity(), "更多功能敬请期待", Toast.LENGTH_SHORT);
//                startActivityByIntent(MoreRechargeActivity.class);
                break;

        }
    }

    /**
     * 充值信息确认的对话框
     *
     * @param phoneNumber
     * @param paymoney
     */
    private void showDialog(String phoneNumber, final String paymoney) {

        new MyAlertDialog(getActivity()).builder().setTitle("确认充值信息")
                .setMsg("确定为号码" + phoneNumber + "充值" + paymoney + "吗？")
                .setPositiveButton("立即充值", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rechargeByHttp();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    /**
     * 充值
     */
    private void rechargeByHttp() {

        String url = AppConstant.BASEURL2 + "api/wpg/create";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("No", getAccount());
        map.put("Amount", getRechargeMoney());
        map.put("Type", String.valueOf(recharge_sort_value));
        map.put("Mobile", getPhoneNumber());
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("No", getAccount())
                .addParams("Amount", getRechargeMoney())
                .addParams("Type", String.valueOf(recharge_sort_value))
                .addParams("Mobile", getPhoneNumber())
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
                        et_user_number.setText("");
                        et_pay_money.setText("");
                        et_phone_number.setText("");
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
                    }
                    
                    ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);

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

    /**
     * 检查充值信息
     */
    private boolean checkMessage() {

        userNumber = getAccount();
        pay_money = getRechargeMoney();
        phone_number = getPhoneNumber();
        if (recharge_sort_value == 3) {
            if (userNumber.length() < 7) {
                ToastUtil.showToast(getActivity(), "请输入正确的水费账户", Toast.LENGTH_SHORT);
                return false;
            }
        } else if (recharge_sort_value == 4) {
            if (userNumber.length() < 10) {
                ToastUtil.showToast(getActivity(), "请输入正确的电费账户", Toast.LENGTH_SHORT);
                return false;
            }
        } else {
            if (userNumber.length() < 10) {
                ToastUtil.showToast(getActivity(), "请输入正确的燃气费账户", Toast.LENGTH_SHORT);
                return false;
            }
        }

        if (TextUtils.isEmpty(pay_money)) {
            ToastUtil.showToast(getActivity(), "请输入充值金额", Toast.LENGTH_SHORT);
            return false;
        }

        if (!StrUtil.isMobileNo(phone_number)) {
            ToastUtil.showToast(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    /**
     * 清除所有选中状态
     */
    private void clearSelected() {
        ll_water_charge.setBackgroundColor(getResources().getColor(R.color.white));
        ll_power_charge.setBackgroundColor(getResources().getColor(R.color.white));
        ll_gas_recharge.setBackgroundColor(getResources().getColor(R.color.white));
    }

    /**
     * 为edittext通过代码设置内容
     *
     * @param str
     * @return
     */
    private SpannableString getEdittextHintText(String str) {

        return new SpannableString(str);
    }

    /**
     * 获取用户账户
     *
     * @return
     */
    public String getAccount() {
        return et_user_number.getText().toString().trim();
    }

    /**
     * 获取充值金额
     *
     * @return
     */
    public String getRechargeMoney() {
        return et_pay_money.getText().toString().trim();
    }

    /**
     * 获取用户预留电电话号码
     *
     * @return
     */
    public String getPhoneNumber() {
        return et_phone_number.getText().toString().trim();
    }
}













