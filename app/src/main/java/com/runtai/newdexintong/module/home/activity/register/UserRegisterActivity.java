package com.runtai.newdexintong.module.home.activity.register;

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

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.StrUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.home.utils.CountDownButtonHelper;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;

/**
 * 用户注册界面
 */
public class UserRegisterActivity extends BaseActivity {

    private RelativeLayout head_back;
    private Button btn_register_success;
    private Button btn_get_code;
    private EditText et_username;
    private EditText et_phone;
    private EditText et_code;

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
                    btn_register_success.setEnabled(true);
                    btn_register_success.setTextColor(getResources().getColor(R.color.white));
                } else {
                    btn_register_success.setEnabled(false);
                    btn_register_success.setTextColor(getResources().getColor(R.color.text_gray));

                }
            }
        }
    };
    private ImageView iv_clear_phone;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_register);

        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        btn_register_success = (Button) findViewById(R.id.btn_register_success);
        btn_get_code = (Button) findViewById(R.id.btn_get_code);
        et_username = (EditText) findViewById(R.id.et_username);
        et_phone = (EditText) findViewById(R.id.et_phone);
        et_code = (EditText) findViewById(R.id.et_code);
        iv_clear_phone = (ImageView) findViewById(R.id.iv_clear_phone);
    }

    private void registerListener() {
        
        head_back.setOnClickListener(this);
        btn_register_success.setOnClickListener(this);
        btn_get_code.setOnClickListener(this);
        iv_clear_phone.setOnClickListener(this);
        et_phone.addTextChangedListener(phoneWatcher);
        et_code.addTextChangedListener(identifyCodeWatcher);
       
    }

    /**
     * 设置标题栏
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("注册");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;

            case R.id.btn_register_success://注册成功后弹出对话框提示
                String username = et_username.getText().toString().trim();
                String phone = et_phone.getText().toString().trim();
                String code = et_code.getText().toString().trim();
                
                if (TextUtils.isEmpty(username)) {
                    ToastUtil.showToast(this, "请输入用户名", Toast.LENGTH_SHORT);
                    return;
                }

                if (!StrUtil.isMobileNo(phone)) {
                    ToastUtil.showToast(this, "请输入正确的手机号", Toast.LENGTH_SHORT);
                    return;
                }
                if (TextUtils.isEmpty(code)) {
                    ToastUtil.showToast(this, "请输入验证码", Toast.LENGTH_SHORT);
                    return;
                }
                showMyDialog();
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
            case R.id.iv_clear_phone://清除电话号码
                et_phone.setText("");
                setGetCodeButton(false);
                break;
        }
    }

    private void showMyDialog() {
        new MyAlertDialog(this)
                .builder().setTitle("恭喜您，注册成功！")
                .setMsg("我们会在3个工作日内联系您")
                .setImageView(R.mipmap.register_success_icon)
                .setCancelable(false)
                .setPositiveButton("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        UserRegisterActivity.this.finish();
                    }
                }).show();
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
