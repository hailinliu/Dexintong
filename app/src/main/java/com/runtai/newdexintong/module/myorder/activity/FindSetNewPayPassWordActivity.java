package com.runtai.newdexintong.module.myorder.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.home.activity.login.LoginActivity;
import com.runtai.newdexintong.module.home.bean.FindBackPasswordBean;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Request;

/**
 * 找回设置账号支付的新密码
 */
public class FindSetNewPayPassWordActivity extends BaseActivity {

    private RelativeLayout head_back;
    private EditText et_new_password;
    private EditText et_confirm_new_password;
    private Button btn_confirm;

    TextWatcher confirm_new_password_watcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            String new_password = et_new_password.getText().toString().trim();
            if (s != null) {
                String confirm_password = s.toString();
                if (new_password.length() == confirm_password.length()) {
                    btn_confirm.setEnabled(true);
                    btn_confirm.setTextColor(getResources().getColor(R.color.white));
                }

            }
        }
    };
    private String tag;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_find_set_pay_new_password);
        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        et_new_password = (EditText) findViewById(R.id.et_new_password);
        String digits = getResources().getString(R.string.filter_vcode);
        et_new_password.setKeyListener(DigitsKeyListener.getInstance(digits));
        et_confirm_new_password = (EditText) findViewById(R.id.et_confirm_new_password);
        et_confirm_new_password.setKeyListener(DigitsKeyListener.getInstance(digits));
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
    }

    private void registerListener() {
        head_back.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        et_confirm_new_password.addTextChangedListener(confirm_new_password_watcher);
    }

    /**
     * 设置标题栏
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("找回支付密码(2/2)");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;

            case R.id.btn_confirm://确定按钮
                if (checkPassword()) {
                    //此处网路走网络请求
                    confirmNewPasswordByHttp();
                }
                break;
        }
    }

    /**
     * 通过接口验证新的密码
     */
    private void confirmNewPasswordByHttp() {

        String url = AppConstant.BASEURL + "login/pass";
        OkHttpUtils.post().url(url).addParams("login", "13523573400")
                .addParams("pass", getNewPassword()).addParams("passagain", getNewPasswordAgain())
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {
                Gson gson = GsonUtil.buildGson();
                FindBackPasswordBean findBackPasswordBean = gson.fromJson(response, FindBackPasswordBean.class);
                if (findBackPasswordBean != null) {

                    int code = findBackPasswordBean.getCode();
                    String msg = findBackPasswordBean.getMsg();
                    //弹出提示信息
                    showToast(msg);
                    if (code == 1000) {
                        //修改成功的时候
                        Intent intent = new Intent(FindSetNewPayPassWordActivity.this, LoginActivity.class);
                        startActivityByIntent(intent);
                        onBackPressed();
                    }
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("confirm_message", e.toString());
            }
        });
    }

    /**
     * 检查信息
     *
     * @return
     */
    private boolean checkPassword() {
        String new_password = et_new_password.getText().toString().trim();
        String confirm_new_password = et_confirm_new_password.getText().toString().trim();
        if (TextUtils.isEmpty(new_password)) {
            ToastUtil.showToast(this, "请输入新密码", Toast.LENGTH_SHORT);
            return false;
        }
        if (TextUtils.isEmpty(confirm_new_password)) {
            ToastUtil.showToast(this, "请输入确认的新密码", Toast.LENGTH_SHORT);
            return false;
        }

        if (!new_password.equals(confirm_new_password)) {
            ToastUtil.showToast(this, "两次输入的密码不一致，请重新输入", Toast.LENGTH_SHORT);
            return false;
        }

        if (confirm_new_password.length() < 6 || confirm_new_password.length() > 32) {
            ToastUtil.showToast(FindSetNewPayPassWordActivity.this, "密码须有6-32位字母或数字组成", Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    /**
     * 获取输入的新密码
     *
     * @return
     */
    private String getNewPassword() {
        return et_new_password.getText().toString().trim();
    }

    /**
     * 获取再次输入的新密码
     *
     * @return
     */
    private String getNewPasswordAgain() {
        return et_confirm_new_password.getText().toString().trim();
    }
}
