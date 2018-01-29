package com.runtai.newdexintong.module.personalcenter.fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
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
import com.runtai.newdexintong.module.myorder.alipay.AuthResult;
import com.runtai.newdexintong.module.myorder.alipay.PayResult;
import com.tencent.mm.opensdk.constants.Build;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * @作者：rhf
 * @日期：2017/3/28时间16:22
 * @描述：充值账户Fragment
 */
public class RechargeAccountFragment extends BaseFragment {

    private EditText edit_text;
    private RadioGroup radio_group;
    private RelativeLayout rl_zhifubao;
    private RelativeLayout rl_wangyin;
    private RadioButton pay_zhifubao;
    private RadioButton pay_wangyin;
    private TextView jiakuan_confirm;
    private int pay_sort_vlaue = 1;

    private static final int SDK_PAY_FLAG = 1;
    private static final int SDK_AUTH_FLAG = 2;

    private double amount;
    private String Name = "豫便利平台商品";
    private String GoodsSay = "豫便利平台商品";
    private String paysign;

    private IWXAPI api = WXAPIFactory.createWXAPI(getActivity(), null);
    PayReq req;
    String noncestr, prepayid, wxSign, timestamp;
    /**
     * 网络请求获取到的订单id
     */
    private String orderId;

    @SuppressLint("HandlerLeak")
    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult((Map<String, String>) msg.obj);
                    /**
                     对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "支付成功", Toast.LENGTH_SHORT).show();
                        SPUtils.putBoolean(getActivity(),"isAliPaySuccess",true);
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(getActivity(), "支付失败", Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
                case SDK_AUTH_FLAG: {
                    @SuppressWarnings("unchecked")
                    AuthResult authResult = new AuthResult((Map<String, String>) msg.obj, true);
                    String resultStatus = authResult.getResultStatus();

                    // 判断resultStatus 为“9000”且result_code
                    // 为“200”则代表授权成功，具体状态码代表含义可参考授权接口文档
                    if (TextUtils.equals(resultStatus, "9000") && TextUtils.equals(authResult.getResultCode(), "200")) {
                        // 获取alipay_open_id，调支付时作为参数extern_token 的value
                        // 传入，则支付账户为该授权账户
                        Toast.makeText(getActivity(),
                                "授权成功\n" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        // 其他状态值则为授权失败
                        Toast.makeText(getActivity(),
                                "授权失败" + String.format("authCode:%s", authResult.getAuthCode()), Toast.LENGTH_SHORT).show();

                    }
                    break;
                }
                default:
                    break;
            }
        }

        ;
    };
    private RelativeLayout rl_wx_pay;
    private RadioButton radioBtn_wx_pay;
    private String recharge_amount;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_chongzhi);

        initView();
        registerListener();
    }

    public void initView() {
        req = new PayReq();
        api = WXAPIFactory.createWXAPI(getActivity(), AppConstant.APP_ID);

        edit_text = (EditText) findViewById(R.id.edit_text);

        radio_group = (RadioGroup) findViewById(R.id.radio_group);
        radio_group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                RadioButton radiobutton = (RadioButton) findViewById(group.getCheckedRadioButtonId());
                String str = radiobutton.getText().toString().trim();
                edit_text.setText(str.substring(0, str.length() - 1));
            }
        });

        rl_zhifubao = (RelativeLayout) findViewById(R.id.rl_zhifubao);
        rl_wangyin = (RelativeLayout) findViewById(R.id.rl_wangyin);
        pay_zhifubao = (RadioButton) findViewById(R.id.pay_zhifubao);
        pay_wangyin = (RadioButton) findViewById(R.id.pay_wangyin);

        rl_wx_pay = (RelativeLayout) findViewById(R.id.rl_wx_pay);
        radioBtn_wx_pay = (RadioButton) findViewById(R.id.radioBtn_wx_pay);

        pay_zhifubao.setChecked(true);// 默认选中支付宝支付

        jiakuan_confirm = (TextView) findViewById(R.id.jiakuan_confirm);

    }

    private void registerListener() {
        rl_zhifubao.setOnClickListener(this);
        rl_wangyin.setOnClickListener(this);
        pay_zhifubao.setOnClickListener(this);
        pay_wangyin.setOnClickListener(this);
        rl_wx_pay.setOnClickListener(this);
        radioBtn_wx_pay.setOnClickListener(this);
        jiakuan_confirm.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_zhifubao://支付宝支付
            case R.id.pay_zhifubao:
                clearChoice();
                pay_zhifubao.setChecked(true);
                pay_sort_vlaue = 1;
                break;

            case R.id.rl_wx_pay://微信支付
            case R.id.radioBtn_wx_pay:
                clearChoice();
                radioBtn_wx_pay.setChecked(true);
                pay_sort_vlaue = 0;
                break;
//            case R.id.rl_wangyin://网银支付
//            case R.id.pay_wangyin:
//                clearChoice();
//                pay_wangyin.setChecked(true);
//                pay_sort_vlaue = 2;
//                break;
            case R.id.jiakuan_confirm:
                /*确认付款*/
                recharge_amount = edit_text.getText().toString().trim();
                if (TextUtils.isEmpty(recharge_amount)) {
                    ToastUtil.showToast(getActivity(), "请选择或输入加款金额", Toast.LENGTH_SHORT);
                    return;
                }
                if (Integer.parseInt(recharge_amount) < 100) {
                    ToastUtil.showToast(getActivity(), "加款金额应不小于100元", Toast.LENGTH_SHORT);
                    return;
                }
                doHttp();
                break;
            default:
                break;
        }
    }

    /**
     * 加款
     */
    private void doHttp() {

        if (!NetUtil.isNetworkAvailable(getActivity())) {
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }
        String url = AppConstant.BASEURL2 + "api/payment/transfer";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Acct", "0");
        map.put("At", String.valueOf(pay_sort_vlaue));
        map.put("Amount", recharge_amount);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Acct", "0")
                .addParams("At", String.valueOf(pay_sort_vlaue))
                .addParams("Amount", recharge_amount)
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
                    String str_data = jsonObject.getString("Data");
                    JSONObject json = new JSONObject(str_data);
                    if (code == 200) {
                        if (pay_sort_vlaue == 1) {
                            //支付宝支付
                            amount = json.getDouble("a");
                            orderId = json.getInt("o") + "";
//                            sp.edit().putString("my_orderid", orderId)
//                                    .commit();
                            paysign = json.getString("s");
                            aliPay();
                        } else if (pay_sort_vlaue == 0) {
                            //微信支付
                            wxSign = json.getString("s");// 签名
                            prepayid = json.getString("p");// 支付id
                            noncestr = json.getString("n");// 随机字符串
                            timestamp = json.getString("t");// 时间戳
                            boolean isPaySupported = api.getWXAppSupportAPI() >= Build.PAY_SUPPORTED_SDK_INT;
                            if (isPaySupported) {
                                // 生成签名参数
                                genPayReq();
                            } else {
                                ToastUtil.showToast(getActivity(),
                                        "未检测到微信", Toast.LENGTH_SHORT);
                            }
                        }
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    LogUtil.e("payResultException", e.getMessage());
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("unusedcoupon", e.toString());
            }
        });
    }

    /**
     * 清除已经选择的
     */
    private void clearChoice() {
        pay_zhifubao.setChecked(false);
        radioBtn_wx_pay.setChecked(false);
        pay_wangyin.setChecked(false);
    }

    /**
     * 支付宝支付
     */
    private void aliPay() {

        Runnable payRunnable = new Runnable() {

            @Override
            public void run() {
                PayTask alipay = new PayTask(getActivity());
                Map<String, String> result = alipay.payV2(paysign, true);
                Log.i("msp", result.toString());

                Message msg = new Message();
                msg.what = SDK_PAY_FLAG;
                msg.obj = result;
                mHandler.sendMessage(msg);
            }
        };

        Thread payThread = new Thread(payRunnable);
        payThread.start();
    }

    // 微信第二次签名
    private void genPayReq() {
        req.appId = AppConstant.APP_ID;
        req.partnerId = AppConstant.MCH_ID;
        req.prepayId = prepayid;
        req.packageValue = "Sign=WXPay";
        req.nonceStr = noncestr;
        req.timeStamp = timestamp;
        req.sign = wxSign;
        api.sendReq(req);

    }


}
