package com.runtai.newdexintong.module.myorder.activity;

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
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StrUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.CountDownButtonHelper;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Request;

/**
 * 找回账户支付密码界面
 */
public class FindPayPassWordActivity extends BaseActivity {

    private RelativeLayout head_back;
    private Button btn_next;
    private TextView tv_phone_number;
    private EditText et_identify_code;
    private ImageView iv_clear_phone;
    private Button btn_get_code;

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
            if (s != null && StrUtil.isMobileNo(tv_phone_number.getText().toString().trim())) {
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
        setContentView(R.layout.activity_find_pay_password);

        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        btn_next = (Button) findViewById(R.id.btn_next);
        tv_phone_number = (TextView) findViewById(R.id.tv_phone_number);
        et_identify_code = (EditText) findViewById(R.id.et_identify_code);
        iv_clear_phone = (ImageView) findViewById(R.id.iv_clear_phone);
        btn_get_code = (Button) findViewById(R.id.btn_get_code);
        //找回密码的账号
        String login_account = SPUtils.getString(this, "login", "");
        if (login_account != null && login_account.length() == 11) {
            tv_phone_number.setText(login_account);
            setGetCodeButton(true);
//            iv_clear_phone.setVisibility(View.VISIBLE);
        }
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        btn_next.setOnClickListener(this);
        iv_clear_phone.setOnClickListener(this);
        btn_get_code.setOnClickListener(this);
        et_identify_code.addTextChangedListener(identifyCodeWatcher);

    }

    /**
     * 设置标题栏
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("找回支付密码(1/2)");
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
                startActivityByIntent(FindSetNewPayPassWordActivity.class);
                finish();
                break;
            case R.id.iv_clear_phone://清除电话号码
//                et_phone.setText("");
//                setGetCodeButton(false);
                break;
            case R.id.btn_get_code://获取验证码
//                ToastUtil.showToast(this, "获取验证码", Toast.LENGTH_SHORT);
                if (!StrUtil.isMobileNo(getPhoneNumber())) {
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
//                        et_phone.setEnabled(true);
                        iv_clear_phone.setEnabled(true);
//                        ToastUtil.showToast(FindPassWordActivity.this, "请重新输入验证码", Toast.LENGTH_SHORT);
                        setGetCodeButton(true);
                    }
                });

                helper.start();
                setGetCodeButton(false);
                iv_clear_phone.setEnabled(false);
//                et_phone.setEnabled(false);
                break;
        }

    }

    /**
     * 通过接口验证账号和短信验证码
     */
    private void confirmByHttp() {

        String url = AppConstant.BASEURL + "login/vail";
        OkHttpUtils.post().url(url).addParams("number", getPhoneNumber())
                .addParams("captcha", getCode())
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {
                Gson gson = GsonUtil.buildGson();
                        
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

        String url = AppConstant.BASEURL + "login/send";
        OkHttpUtils.post().url(url).addParams("number", getPhoneNumber())
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {
                Gson gson = new Gson();
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
        
        return tv_phone_number.getText().toString().trim();
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
