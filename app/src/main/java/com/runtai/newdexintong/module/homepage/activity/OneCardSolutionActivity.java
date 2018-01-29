package com.runtai.newdexintong.module.homepage.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.ToastUtil;

/**
 * 一卡通充值
 */
public class OneCardSolutionActivity extends BaseActivity {

    private RelativeLayout head_back;
    private EditText et_recharge_account;
    private EditText et_recharge_money;
    private Button btn_recharge_now;
    private TextView tv_denomation;
    private String recharge_money;
    private String account;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_one_card_solution);


        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        et_recharge_account = (EditText) findViewById(R.id.et_recharge_account);
        et_recharge_money = (EditText) findViewById(R.id.et_recharge_money);
        btn_recharge_now = (Button) findViewById(R.id.btn_recharge_now);
        tv_denomation = (TextView) findViewById(R.id.tv_denomation);
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        btn_recharge_now.setOnClickListener(this);
        tv_denomation.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("一卡通充值");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.btn_recharge_now://点击立即充值
                if (check()) {

                }
                break;
            case R.id.tv_denomation://点击选择充值面额

                break;

        }
    }

    private boolean check() {
        account = et_recharge_account.getText().toString().trim();
        recharge_money = et_recharge_money.getText().toString().trim();
        if (TextUtils.isEmpty(account)) {
            ToastUtil.showToast(OneCardSolutionActivity.this, "请输入充值号码", Toast.LENGTH_SHORT);
            return false;
        }

        if (TextUtils.isEmpty(recharge_money)) {
            ToastUtil.showToast(OneCardSolutionActivity.this, "请输入充值金额", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }
}
