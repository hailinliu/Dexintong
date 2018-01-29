package com.runtai.newdexintong.module.homepage.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.CountDownButtonHelper;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.homepage.adapter.SelectDenominationAdapter;
import com.runtai.newdexintong.module.homepage.bean.SelectDenominationBean;
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
 * Q币充值
 */
public class QbRechargeActivity extends BaseActivity {

    private RelativeLayout head_back;
    private EditText et_qq_account;
    private TextView tv_select_money;
    private TextView et_real_money;
    private EditText et_code;
    private Button btn_recharge_now;
    private String qq_account;
    private String real_pay_money;
    private String code;
    private PopupWindow popupWindow;
    private ListView lv_select;
    private List<SelectDenominationBean> mData;
    private Button btn_get_msg_code;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_qbi_recharge);

        initData();
        initView();
        registerListener();
    }

    private void initData() {
        mData = new ArrayList<>();
        mData.clear();
//        ^(10|20|30|50|100|200|300|500)
        for (int i = 0; i < 8; i++) {
            SelectDenominationBean bean = new SelectDenominationBean();
            if (i == 0) {
                bean.money = "10";
            } else if (i == 1) {
                bean.money = "20";
            } else if (i == 2) {
                bean.money = "30";
            } else if (i == 3) {
                bean.money = "50";
            } else if (i == 4) {
                bean.money = "100";
            } else if (i == 5) {
                bean.money = "200";
            } else if (i == 6) {
                bean.money = "300";
            } else if (i == 7) {
                bean.money = "500";
            }

            mData.add(bean);
        }

    }


    private void initView() {
        setActivityTitle();
        et_qq_account = (EditText) findViewById(R.id.et_qq_account);
        tv_select_money = (TextView) findViewById(R.id.tv_select_money);
        et_real_money = (EditText) findViewById(R.id.et_real_money);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_recharge_now = (Button) findViewById(R.id.btn_recharge_now);
        btn_get_msg_code = (Button) findViewById(R.id.btn_get_msg_code);
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        tv_select_money.setOnClickListener(this);
        btn_recharge_now.setOnClickListener(this);
        btn_get_msg_code.setOnClickListener(this);
        et_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    if (check("0")) {
                        //通过接口充值
                        showDialog(getQQAccount(), getPayMoney());

                    }
                }
                return false;
            }
        });
    }

    /**
     * 充值信息确认的对话框
     *
     * @param phoneNumber
     * @param paymoney
     */
    private void showDialog(String phoneNumber, final String paymoney) {

        new MyAlertDialog(this).builder().setTitle("确认充值信息")
                .setMsg("确定为号码" + phoneNumber + "充值" + paymoney + "吗？")
                .setPositiveButton("立即充值", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        rechargeQb();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("Q币充值");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_select_money://选择充值的金额
                showPopuwindow();
                break;
            case R.id.btn_recharge_now://点击立即充值
                if (check("0")) {
                    //通过接口充值
                    showDialog(getQQAccount(), getPayMoney());
                }
                break;
            case R.id.btn_get_msg_code://获取验证码
                if (check("1")) {
                    //获取验证码成功
                    btn_get_msg_code.setEnabled(false);
                    getCodeHttp();
                    CountDownButtonHelper helper = new CountDownButtonHelper(
                            btn_get_msg_code, "重发", 60, 1);
                    helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

                        @Override
                        public void finish() {
                            et_qq_account.setEnabled(true);
                            setGetCodeButton(true);
                        }
                    });

                    helper.start();
                    setGetCodeButton(false);
                    et_qq_account.setEnabled(false);

                }
                break;
        }
    }

    /**
     * 设置获取验证码的button
     *
     * @param flag
     */
    public void setGetCodeButton(boolean flag) {
        if (flag) {
            btn_get_msg_code.setBackgroundResource(R.drawable.shape_button_red_edge_bg);
            btn_get_msg_code.setTextColor(getResources().getColor(R.color.new_title_color));
        } else {
            btn_get_msg_code.setBackgroundResource(R.drawable.shape_button_gray_edge_bg);
            btn_get_msg_code.setTextColor(getResources().getColor(R.color.text_gray));
        }
        btn_get_msg_code.setEnabled(flag);
    }

    /**
     * 获取验证码
     */
    private void getCodeHttp() {

        String url = AppConstant.BASEURL2 + "api/captcha/sms";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");
        String account = SPUtils.getString(this, "login", "");
        String pwd = SPUtils.getString(this, "password", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Login", account);
        map.put("Pwd", pwd);
        map.put("Vt", "1");
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Login", account)
                .addParams("Pwd", pwd)
                .addParams("Vt", "1")
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
                    } else if (code == 403) {
                        DialogUtil.showDialog(QbRechargeActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(QbRechargeActivity.this, msg, Toast.LENGTH_SHORT);
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

    /**
     * 充值Q币
     */
    private void rechargeQb() {

        String url = AppConstant.BASEURL2 + "api/qb/create";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Amount", getPayMoney());
        map.put("Otp", getCode());
        map.put("No", getQQAccount());
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Amount", getPayMoney())
                .addParams("Otp", getCode())
                .addParams("No", getQQAccount())
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
                        et_qq_account.setText("");
                        tv_select_money.setText("请选择推荐金额");
                        et_code.setText("");
                        et_real_money.setText("");
                    } else if (code == 403) {
                        DialogUtil.showDialog(QbRechargeActivity.this, getResources().getString(R.string.need_login_again));
                    }
                    ToastUtil.showToast(QbRechargeActivity.this, msg, Toast.LENGTH_SHORT);

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
     * 充值前检查充值信息
     *
     * @return
     */
    private boolean check(String flagValue) {
        qq_account = getQQAccount();
        real_pay_money = getPayMoney();
        code = getCode();

        if (TextUtils.isEmpty(qq_account)) {
            ToastUtil.showToast(QbRechargeActivity.this, "请输入QQ账号", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(real_pay_money)) {
            ToastUtil.showToast(QbRechargeActivity.this, "请选择或者输入充值金额", Toast.LENGTH_SHORT);
            return false;
        }

        if ("0".equals(flagValue)) {
            if (TextUtils.isEmpty(code)) {
                ToastUtil.showToast(QbRechargeActivity.this, "请输入短信验证码", Toast.LENGTH_SHORT);
                return false;
            }
        }

        return true;
    }

    /**
     * 获取qq账户
     *
     * @return
     */
    public String getQQAccount() {
        return et_qq_account.getText().toString().trim();
    }

    /**
     * 获取充值金额
     *
     * @return
     */
    public String getPayMoney() {
        return et_real_money.getText().toString().trim();
    }

    /**
     * 获取输入的短信验证码
     *
     * @return
     */
    public String getCode() {
        return et_code.getText().toString().trim();
    }

    /**
     * 弹出显示选择城市列表的popuwindow
     */
    private void showPopuwindow() {
        closePopupWindow();
        View popupWindowView = LayoutInflater.from(this).inflate(R.layout.popuwindow_select_package_or_denomination,
                null);
        popupWindow = new PopupWindow(popupWindowView,
                250, WindowManager.LayoutParams.WRAP_CONTENT, true);
        lv_select = (ListView) popupWindowView.findViewById(R.id.lv_select_package);

        lv_select.setAdapter(new SelectDenominationAdapter(this, mData));
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        //获取xoff
//                int xpos = -popupWindow.getWidth() / 2;
                int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;

        lv_select.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                closePopupWindow();
                tv_select_money.setText(mData.get(position).money);
                et_real_money.setText(mData.get(position).money);
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    closePopupWindow();
                    return true;
                }

                return false;
            }
        });

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
        //显示PopupWindow
        popupWindow.showAsDropDown(tv_select_money, 0, 0);
    }

    /**
     * 关闭popuwindow窗口
     */
    private void closePopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }
}
