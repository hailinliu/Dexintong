package com.runtai.newdexintong.module.home.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
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
import com.runtai.newdexintong.comment.utils.StrUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.bean.FindBackPasswordBean;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.CountDownButtonHelper;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * 找回密码界面
 */
public class FindPassWordActivity extends BaseActivity {

    private RelativeLayout head_back;
    private Button btn_next;
    private EditText et_phone;
    private EditText et_identify_code;
    private ImageView iv_clear_phone;
    private Button btn_get_code;

    TextWatcher phoneWatcher = new TextWatcher() {
        private CharSequence temp_phone;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp_phone = s;
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s != null) {
                if (temp_phone.length() > 0 && temp_phone != null) {
                    phoneNumber = temp_phone.toString();
                    if (temp_phone.length() == 11) {

                        setGetCodeButton(true);
                    } else {
                        setGetCodeButton(false);
                    }
                    iv_clear_phone.setVisibility(View.VISIBLE);
                } else {
                    iv_clear_phone.setVisibility(View.GONE);
                }

            }
        }
    };

    TextWatcher identifyCodeWatcher = new TextWatcher() {
        private CharSequence temp_code;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp_code = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            if (s != null && StrUtil.isMobileNo(et_phone.getText().toString().trim())) {
                if (temp_code.length() > 5) {
                    identifycode = temp_code.toString();
                    btn_next.setEnabled(true);
                    btn_next.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btn_next.setEnabled(false);
                    btn_next.setTextColor(getResources().getColor(R.color.text_gray));
                }
            }
        }
    };
    private String phoneNumber;
    private String identifycode;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_pass_word);

        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        btn_next = (Button) findViewById(R.id.btn_next);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_identify_code = (EditText) findViewById(R.id.et_identify_code);
        iv_clear_phone = (ImageView) findViewById(R.id.iv_clear_phone);
        btn_get_code = (Button) findViewById(R.id.btn_get_code);
        //找回密码的账号
        String login_account = getIntent().getStringExtra("login_account");
        if (login_account.length() == 11) {
            et_phone.setText(login_account);
            setGetCodeButton(true);
            StrUtil.setCursorAfter(et_phone);
            iv_clear_phone.setVisibility(View.VISIBLE);
        }
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        iv_clear_phone.setOnClickListener(this);
        btn_get_code.setOnClickListener(this);
        et_phone.addTextChangedListener(phoneWatcher);
        et_identify_code.addTextChangedListener(identifyCodeWatcher);

    }

    /**
     * 设置标题栏
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("找回密码(1/2)");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.btn_next://点击下一步

                if (!StrUtil.isMobileNo(getPhoneNumber())) {
                    ToastUtil.showToast(this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(getCode())) {
                    ToastUtil.showToast(this, "请输入验证码", Toast.LENGTH_SHORT);
                    return;
                }
                
                confirmByHttp();

                break;
            case R.id.iv_clear_phone://清除电话号码
                et_phone.setText("");
                setGetCodeButton(false);
                break;
            case R.id.btn_get_code://获取验证码

                if (!NetUtil.isNetworkAvailable(this)) {
                    ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
                    return;
                }
                if (!StrUtil.isMobileNo(et_phone.getText().toString().trim())) {
                    ToastUtil.showToast(this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    return;
                }
                getIdentifyCode();
                //获取验证码成功
                btn_get_code.setEnabled(false);
                CountDownButtonHelper helper = new CountDownButtonHelper(
                        btn_get_code, "重发", 60, 1);
                helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

                    @Override
                    public void finish() {
                        et_phone.setEnabled(true);
                        iv_clear_phone.setEnabled(true);
//                        ToastUtil.showToast(FindPassWordActivity.this, "请重新输入验证码", Toast.LENGTH_SHORT);
                        setGetCodeButton(true);
                    }
                });

                helper.start();
                setGetCodeButton(false);
                iv_clear_phone.setEnabled(false);
                et_phone.setEnabled(false);
                break;
        }

    }

    /**
     * 通过接口验证账号和短信验证码
     */
    private void confirmByHttp() {

        if (!NetUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/login/vailcaptcha";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Number", getPhoneNumber());
        map.put("Captcha", getCode());
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addParams("Number", getPhoneNumber())
                .addParams("Captcha", getCode())
                .addParams("Sign", signValue)
                .addParams("Timestamp", timeStamp)
                .addParams("Nonce", randomNumberTen)
                .addParams("AppId", AppConstant.appid_value)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {
                byte[] decode = Base64.decode(response);//base64解码
                String strJson = UTF8Util.getString(decode);
                Gson gson = GsonUtil.buildGson();
                FindBackPasswordBean bean = gson.fromJson(strJson, FindBackPasswordBean.class);
                if (bean.getCode() == 200) {
                    Intent intent = new Intent(FindPassWordActivity.this, FindSetNewPassWordActivity.class);
                    intent.putExtra("account",getPhoneNumber());      
                    startActivityByIntent(intent);
                }
                showToast(bean.getMsg());
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("confirm_message", e.toString());
            }
        });
    }

    /**
     * 设置获取验证码的button
     *
     * @param flag
     */
    public void setGetCodeButton(boolean flag) {
        if (flag) {
            btn_get_code.setBackgroundResource(R.drawable.shape_button_red_edge_bg);
            btn_get_code.setTextColor(getResources().getColor(R.color.new_title_color));
        } else {
            btn_get_code.setBackgroundResource(R.drawable.shape_button_gray_edge_bg);
            btn_get_code.setTextColor(getResources().getColor(R.color.text_gray));

        }
        btn_get_code.setEnabled(flag);
    }

    /**
     * 联网请求获取验证码
     */
    private void getIdentifyCode() {

     
        String url = AppConstant.BASEURL2 + "api/login/forgetsend";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("AppId", AppConstant.appid_value);
        map.put("Number", getPhoneNumber());
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url)
                .addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addParams("AppId", AppConstant.appid_value)
                .addParams("Number", getPhoneNumber())
                .addParams("Timestamp", timeStamp)
                .addParams("Nonce", randomNumberTen)
                .addParams("Sign", signValue)

                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {
                byte[] decode = Base64.decode(response);//base64解码
                String strJson = UTF8Util.getString(decode);
                Gson gson = GsonUtil.buildGson();
                FindBackPasswordBean bean = gson.fromJson(strJson, FindBackPasswordBean.class);
                int code = bean.getCode();
                String msg = bean.getMsg();
                showToast(msg);

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("findpassword_getmsg", e.toString());
            }
        });
    }

    /**
     * 获取输入的电话号码
     *
     * @return
     */
    private String getPhoneNumber() {
        return et_phone.getText().toString().trim();
    }

    /**
     * 获取输入的验证码
     *
     * @return
     */
    private String getCode() {
        return et_identify_code.getText().toString().trim();
    }


}
