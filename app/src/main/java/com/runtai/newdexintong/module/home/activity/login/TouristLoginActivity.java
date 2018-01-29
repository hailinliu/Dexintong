package com.runtai.newdexintong.module.home.activity.login;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.ActivityStack;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StrUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.utils.CountDownButtonHelper;

/**
 * 游客登录界面
 */
public class TouristLoginActivity extends BaseActivity {

    private RelativeLayout head_back;
    private EditText et_phone;
    private EditText et_code;
    private Button btn_get_code;
    private Button btn_tourist_login;

    TextWatcher phone_number_watcher = new TextWatcher() {

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

    TextWatcher code_watcher = new TextWatcher() {

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
                    btn_tourist_login.setEnabled(true);
                    btn_tourist_login.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btn_tourist_login.setEnabled(false);
                    btn_tourist_login.setTextColor(getResources().getColor(R.color.text_gray));
                }
            }
        }
    };
    private ImageView iv_clear_phone;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tourist_login);

        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        btn_get_code = (Button) findViewById(R.id.btn_get_code);
        btn_tourist_login = (Button) findViewById(R.id.btn_tourist_login);
        iv_clear_phone = (ImageView) findViewById(R.id.iv_clear_phone);

        et_code.setImeOptions(EditorInfo.IME_ACTION_SEND);
    }

    private void registerListener() {
        head_back.setOnClickListener(this);
        btn_get_code.setOnClickListener(this);
        btn_tourist_login.setOnClickListener(this);
        iv_clear_phone.setOnClickListener(this);
        et_phone.addTextChangedListener(phone_number_watcher);
        et_code.addTextChangedListener(code_watcher);

        et_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    touristLogin();
                }
                return false;
            }
        });
    }

    /**
     * 设置标题栏
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("游客登录");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.btn_get_code://获取验证码
                if (!StrUtil.isMobileNo(et_phone.getText().toString().trim())) {
                    ToastUtil.showToast(this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    return;
                }
//                getIdentifyCode();
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
            case R.id.btn_tourist_login://登录
                touristLogin();
                break;

            case R.id.iv_clear_phone://清除电话号码
                et_phone.setText("");
                setGetCodeButton(false);
                break;
        }
    }

    /**
     * 点击游客登录按钮
     */
    private void touristLogin() {
        String phone = et_phone.getText().toString().trim();
        String code = et_code.getText().toString().trim();
        if (!StrUtil.isMobileNo(phone)) {
            ToastUtil.showToast(this, "请输入正确的手机号码", Toast.LENGTH_SHORT);
            return;
        }
        if (TextUtils.isEmpty(code)) {
            ToastUtil.showToast(this, "请输入验证码", Toast.LENGTH_SHORT);
            return;
        }
        Intent intent = new Intent(this, MainActivity.class);
        intent.putExtra("tag", "selected_homePage");
        startActivityByIntent(intent);
        SPUtils.putString(this, "userid", phone);
        SPUtils.putBoolean(this, "isTourist", true);
        finish();
        ActivityStack.getInstance().finishActivity(LoginActivity.class);
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
}
