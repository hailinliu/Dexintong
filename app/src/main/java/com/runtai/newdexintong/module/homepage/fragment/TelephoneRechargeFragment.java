package com.runtai.newdexintong.module.homepage.fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.comment.view.MyGridView;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.homepage.activity.FlowRechargeActivity;
import com.runtai.newdexintong.module.homepage.activity.MobilePackageActivity;
import com.runtai.newdexintong.module.homepage.activity.QbRechargeActivity;
import com.runtai.newdexintong.module.homepage.adapter.RechargeMoneyItemAdapter;
import com.runtai.newdexintong.module.homepage.bean.RechargeMoneyItemBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import okhttp3.Request;

/**
 * @author：rhf
 * @date：2017/7/29time15:58
 * @detail：固话充值
 */

public class TelephoneRechargeFragment extends BaseFragment {

    private RadioButton rb_unicom;//联通
    private RadioButton rb_telecom;//电信
    private RadioButton rb_railcom;//铁通
    private LinearLayout ll_q_money_recharge;
    private LinearLayout ll_mobile_package;
    private LinearLayout ll_flow_recharge;
    private LinearLayout ll_more;
    private MyGridView myGridView_telephone_recharge;
    private List<RechargeMoneyItemBean> mData;
    private EditText et_quhao;
    private EditText et_number;
    private int tagValue = 0;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_telephone_recharge);
        View view = getContentView();
        initView(view);
        initData();
        registerListener();
    }

    private void initData() {

        mData = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            RechargeMoneyItemBean bean = new RechargeMoneyItemBean();
            if (i == 0) {
                bean.denomination = "50";
            } else if (i == 1) {
                bean.denomination = "100";
            } else if (i == 2) {
                bean.denomination = "200";
            } else if (i == 3) {
                bean.denomination = "300";
            }

            mData.add(bean);
        }
        myGridView_telephone_recharge.setAdapter(new RechargeMoneyItemAdapter(getActivity(), mData));
    }

    private void initView(View view) {

        ll_q_money_recharge = (LinearLayout) view.findViewById(R.id.ll_q_money_recharge);
        ll_mobile_package = (LinearLayout) view.findViewById(R.id.ll_mobile_package);
        ll_flow_recharge = (LinearLayout) view.findViewById(R.id.ll_flow_recharge);
        ll_more = (LinearLayout) view.findViewById(R.id.ll_more);

        rb_unicom = (RadioButton) view.findViewById(R.id.rb_unicom);
        rb_telecom = (RadioButton) view.findViewById(R.id.rb_telecom);
        rb_railcom = (RadioButton) view.findViewById(R.id.rb_railcom);

        rb_unicom.setChecked(true);

        et_quhao = (EditText) view.findViewById(R.id.et_quhao);
        et_number = (EditText) view.findViewById(R.id.et_number);

        myGridView_telephone_recharge = (MyGridView) view.findViewById(R.id.myGridView_telephone_recharge);
    }

    private void registerListener() {
        rb_unicom.setOnClickListener(this);
        rb_telecom.setOnClickListener(this);
        rb_railcom.setOnClickListener(this);

        ll_q_money_recharge.setOnClickListener(this);
        ll_mobile_package.setOnClickListener(this);
        ll_flow_recharge.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        //条目点击事件
        myGridView_telephone_recharge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = et_quhao.getText().toString().trim() + "-" + et_number.getText().toString().trim();
                String cm = "^(0[0-9]{2,3}\\-)?([2-9][0-9]{6,7})$";
                RechargeMoneyItemBean rechargeMoneyItemBean = mData.get(position);
                String realmoney = rechargeMoneyItemBean.denomination;

                if (!Pattern.matches(cm, number)) {
                    ToastUtil.showCenterToast(getActivity(), "请仔细检查区号、号码是否正确", Toast.LENGTH_SHORT);
                    return;
                }
                String[] newnumber = number.split("-");
                showDialog(newnumber[0] + newnumber[1], realmoney);
            }
        });
    }

    /**
     * 获取区号
     *
     * @return
     */
    public String getQuHao() {
        return et_quhao.getText().toString().trim();
    }

    /**
     * 获取号码
     *
     * @return
     */
    public String getNumber() {
        return et_number.getText().toString().trim();
    }

    /**
     * 充值信息确认的对话框
     *
     * @param phoneNumber
     * @param paymoney
     */
    private void showDialog(final String phoneNumber, final String paymoney) {

        new MyAlertDialog(getActivity()).builder().setTitle("确认充值信息")
                .setMsg("确定为号码" + phoneNumber + "充值" + paymoney + "吗？")
                .setPositiveButton("立即充值", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        telephoneRecharge(phoneNumber, paymoney);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    /**
     * 固话充值
     */
    private void telephoneRecharge(String telePhoneNumber, String money) {

        String url = AppConstant.BASEURL2 + "api/phone/create";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("PhoneType", String.valueOf(tagValue));
        map.put("Amount", money);
        map.put("No", telePhoneNumber);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("PhoneType", String.valueOf(tagValue))
                .addParams("Amount", money)
                .addParams("No", telePhoneNumber)
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
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
                    } else if (code == 200) {
                        et_number.setText("");
                        et_quhao.setText("");
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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
            case R.id.rb_unicom://联通固话
                clearChecked();
                rb_unicom.setTextColor(getResources().getColor(R.color.new_title_color));
                tagValue = 0;

                break;
            case R.id.rb_telecom://电信固话
                clearChecked();
                rb_telecom.setTextColor(getResources().getColor(R.color.new_title_color));
                tagValue = 1;
                break;
            case R.id.rb_railcom://铁通固话
                tagValue = 2;
                clearChecked();
                rb_railcom.setTextColor(getResources().getColor(R.color.new_title_color));

                break;
        }
    }

    /**
     * 清除所有的选中状态
     */
    private void clearChecked() {
        rb_unicom.setTextColor(Color.parseColor(String.valueOf("#ffacacac")));
        rb_telecom.setTextColor(Color.parseColor(String.valueOf("#ffacacac")));
        rb_railcom.setTextColor(Color.parseColor(String.valueOf("#ffacacac")));
        et_number.setText("");
        et_quhao.setText("");
    }
}















