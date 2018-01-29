package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.text.method.DigitsKeyListener;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.ToastUtil;


/**
 * @作者：高炎鹏
 * @日期：2017/3/28时间14:40
 * @描述：修改密码
 */

public class ModifyPwdActivity extends BaseActivity {

    private RelativeLayout head_back;
    private EditText et_old_pwd, et_new_pwd;
    private TextView tv_confirm;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_xiugaimima);

        initView();
        registerListener();
    }

    private void registerListener() {
        tv_confirm.setOnClickListener(this);
        head_back.setOnClickListener(this);
    }

    private void initView() {
        String digits = getResources().getString(R.string.filter_vcode);
        et_old_pwd = (EditText) findViewById(R.id.et_old_pwd);
        et_new_pwd = (EditText) findViewById(R.id.et_new_pwd);

        et_old_pwd.setKeyListener(DigitsKeyListener.getInstance(digits));
        et_new_pwd.setKeyListener(DigitsKeyListener.getInstance(digits));
        tv_confirm = (TextView) findViewById(R.id.tv_confirm);

        setActivityTitle();
//        et_new_pwd.setOnKeyListener(new View.OnKeyListener() {
//            @Override
//            public boolean onKey(View v, int keyCode, KeyEvent event) {
//                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
//                    /*隐藏软键盘*/
//                    KeyboardUtil.hintKeyBoard(ModifyPwdActivity.this, et_new_pwd);
//                    checkPassword();
//                    return true;
//                }
//                return false;
//            }
//        });
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("修改密码");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_confirm://确定修改密码
                if (checkPassword()) {

                }

                break;
            default:
                break;
        }
    }

    /**
     * 修改密码确认前检查信息
     */
    public boolean checkPassword() {

        String old_pwd_text = et_old_pwd.getText().toString().trim();
        String new_pwd_text = et_new_pwd.getText().toString().trim();
        String password = SPUtils.getString(this, "password", "");
        if (TextUtils.isEmpty(old_pwd_text)) {
            ToastUtil.showToast(this, "请输入原密码", Toast.LENGTH_SHORT);
            return false;
        }

        if (TextUtils.isEmpty(new_pwd_text)) {
            ToastUtil.showToast(this, "请输入新密码", Toast.LENGTH_SHORT);
            return false;
        }

        if (!password.equals(old_pwd_text)) {
            ToastUtil.showToast(this, "原密码输入有误", Toast.LENGTH_SHORT);
            et_new_pwd.setText("");
            et_old_pwd.setText("");
            return false;
        }

        if (!new_pwd_text.equals(old_pwd_text)) {
            ToastUtil.showToast(this, "两次输入的密码不一致", Toast.LENGTH_SHORT);
            et_new_pwd.setText("");
            et_old_pwd.setText("");
            return false;
        }

        return true;

    }
}
