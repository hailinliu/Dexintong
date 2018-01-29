package com.runtai.newdexintong.module.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.module.home.activity.login.FindPassWordActivity;
import com.runtai.newdexintong.module.home.bean.LoginBean;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.myorder.activity.FindPayPassWordActivity;


/**
 * @作者：rhf
 * @日期：2017/2/15时间09:08
 * @描述：店铺资料
 */

public class StoreInfoActivity extends BaseActivity {

    private RelativeLayout dpzl_dlzh_rl;
    private RelativeLayout dpzl_xgdlmm_rl;
    /*店铺老板，老板电话，店铺名称，店铺编号，店铺区域，店铺地址，登陆账号*/
    private TextView tv_boss_name, dpzl_lxdh_tv, dpzl_dpmc_tv, dpzl_dpbh_tv,
            dpzl_dpqy_tv, dpzl_dpdz_tv, ddzl_dlzh_tv;

    private RelativeLayout dpzl_sfz_rl, dpzl_yyzz_rl;
    private RelativeLayout head_back;
    private Intent intent;
    private RelativeLayout rl_pay_pwd;
    private LoginBean.DataBean.MerchantBean merchantBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_store_info);
        initView();
        registerListener();
        setData();

    }

    private void initView() {

        dpzl_dlzh_rl = (RelativeLayout) findViewById(R.id.dpzl_dlzh_rl);
        dpzl_xgdlmm_rl = (RelativeLayout) findViewById(R.id.dpzl_xgdlmm_rl);
        dpzl_sfz_rl = (RelativeLayout) findViewById(R.id.dpzl_sfz_rl);
        dpzl_yyzz_rl = (RelativeLayout) findViewById(R.id.dpzl_yyzz_rl);

        tv_boss_name = (TextView) findViewById(R.id.tv_boss_name);
        dpzl_lxdh_tv = (TextView) findViewById(R.id.dpzl_lxdh_tv);
        dpzl_dpmc_tv = (TextView) findViewById(R.id.dpzl_dpmc_tv);
        dpzl_dpbh_tv = (TextView) findViewById(R.id.dpzl_dpbh_tv);

        dpzl_dpqy_tv = (TextView) findViewById(R.id.dpzl_dpqy_tv);
        dpzl_dpdz_tv = (TextView) findViewById(R.id.dpzl_dpdz_tv);
        ddzl_dlzh_tv = (TextView) findViewById(R.id.ddzl_dlzh_tv);
        rl_pay_pwd = (RelativeLayout) findViewById(R.id.rl_pay_pwd);

        setActivityTitle();
    }

    /**
     * 为店铺资料中各个控件赋值
     */
    private void setData() {
        merchantBean = GsonUtil.buildGson().fromJson(SPUtils.getString(this, "merchant", ""),
                LoginBean.DataBean.MerchantBean.class);
        if (merchantBean != null) {
            tv_boss_name.setText(merchantBean.getBoss());
            dpzl_lxdh_tv.setText(merchantBean.getPhone());
            dpzl_dpmc_tv.setText(merchantBean.getName());
            dpzl_dpbh_tv.setText(String.valueOf(merchantBean.getId()));
            dpzl_dpqy_tv.setText(merchantBean.getAreaName().replace("|", ""));
            dpzl_dpdz_tv.setText(merchantBean.getAddr());
            ddzl_dlzh_tv.setText(merchantBean.getLogin().trim().substring(0, 11));
        } else {
            DialogUtil.showDialog(this, getResources().getString(R.string.need_login_again));
        }

    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        rl_pay_pwd.setOnClickListener(this);
        dpzl_dlzh_rl.setOnClickListener(this);
        dpzl_xgdlmm_rl.setOnClickListener(this);
        dpzl_sfz_rl.setOnClickListener(this);
        dpzl_yyzz_rl.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);

        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("店铺资料");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.dpzl_sfz_rl:
                /*身份证*/
                intent = new Intent(this, UploadActivity.class);
                intent.putExtra("zhengjian", "sfz");
                startActivityByIntent(intent);
                break;
            case R.id.dpzl_yyzz_rl:
                /*营业执照*/
                intent = new Intent(this, UploadActivity.class);
                intent.putExtra("zhengjian", "yyzz");
                startActivityByIntent(intent);
                break;
//            case R.id.tv_subtitle://完成
//
//                break;
            case R.id.dpzl_dlzh_rl://登录账号

                break;
            case R.id.dpzl_xgdlmm_rl://修改登录密码
                Intent intent_login = new Intent(StoreInfoActivity.this, FindPassWordActivity.class);
                if (merchantBean != null) {
                    intent_login.putExtra("login_account", merchantBean.getPhone());
                    startActivityByIntent(intent_login);
                } else {
                    DialogUtil.showDialog(this, getResources().getString(R.string.need_login_again));
                }

                break;
            case R.id.rl_pay_pwd://修改支付密码
                Intent intent2 = new Intent(this, FindPayPassWordActivity.class);
                startActivityByIntent(intent2);
                break;
            default:
                break;
        }
    }

}
