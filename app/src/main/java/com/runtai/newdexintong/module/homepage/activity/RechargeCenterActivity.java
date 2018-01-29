package com.runtai.newdexintong.module.homepage.activity;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.homepage.fragment.LivePayFragment;
import com.runtai.newdexintong.module.homepage.fragment.MobileRechargeFragment;
import com.runtai.newdexintong.module.homepage.fragment.TelephoneRechargeFragment;
import com.runtai.newdexintong.module.personalcenter.adapter.ViewPageAdapter;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值中心界面
 */
public class RechargeCenterActivity extends BaseActivity {

    private RelativeLayout head_back;
    private FragmentManager manager;
    private List<Fragment> list;
    private ViewPageAdapter viewPageAdapter;
    private TelephoneRechargeFragment telephoneRechargeFragment;
    private LivePayFragment livePayFragment;
    private MobileRechargeFragment mobileRechargeFragment;
    private TextView tv_line1;
    private TextView tv_line2;
    private TextView tv_line3;
    private RelativeLayout rl_mobile_recharge;
    private RelativeLayout rl_live_pay;
    private RelativeLayout rl_telephone_recharge;
    private TextView tv_mobile_recharge;
    private TextView tv_live_pay;
    private TextView tv_telephone_recharge;

    private int main_color = 0XCCff2d48;
    private int black = 0XCC000000;
    private ViewPager viewPager;
    private MyTitleClickListener titelClickListener;
    private ViewPagerListener pagerListener;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_center);
        manager = getSupportFragmentManager();
        initViewPager();
        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        tv_line1 = (TextView) findViewById(R.id.tv_line1);
        tv_line2 = (TextView) findViewById(R.id.tv_line2);
        tv_line3 = (TextView) findViewById(R.id.tv_line3);

        rl_mobile_recharge = (RelativeLayout) findViewById(R.id.rl_mobile_recharge);
        rl_live_pay = (RelativeLayout) findViewById(R.id.rl_live_pay);
        rl_telephone_recharge = (RelativeLayout) findViewById(R.id.rl_telephone_recharge);

        tv_mobile_recharge = (TextView) findViewById(R.id.tv_mobile_recharge);
        tv_live_pay = (TextView) findViewById(R.id.tv_live_pay);
        tv_telephone_recharge = (TextView) findViewById(R.id.tv_telephone_recharge);

        titelClickListener = new MyTitleClickListener();
        pagerListener = new ViewPagerListener();


        viewPager = (ViewPager) findViewById(R.id.dd_viewPager);
        viewPager.setAdapter(viewPageAdapter);
        viewPager.setOffscreenPageLimit(2);

        viewPager.setOnPageChangeListener(pagerListener);
        rl_mobile_recharge.setOnClickListener(titelClickListener);
        rl_live_pay.setOnClickListener(titelClickListener);
        rl_telephone_recharge.setOnClickListener(titelClickListener);

        titleSelectedChange(0);

    }

    private void registerListener() {
        head_back.setOnClickListener(this);
    }

    private void initViewPager() {
        list = new ArrayList<Fragment>();
        // 手机充值
        mobileRechargeFragment = new MobileRechargeFragment();
        // 生活缴费
        livePayFragment = new LivePayFragment();
        //固话充值
        telephoneRechargeFragment = new TelephoneRechargeFragment();

        list.clear();
        list.add(mobileRechargeFragment);
        list.add(livePayFragment);
        list.add(telephoneRechargeFragment);

        viewPageAdapter = new ViewPageAdapter(manager, list);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("充值缴费");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
        }
    }

    private class MyTitleClickListener implements View.OnClickListener {

        @Override
        public void onClick(View v) {
            clearChoise();
            titleSelectedChange(v.getId());
        }
    }

    private class ViewPagerListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrollStateChanged(int arg0) {
            if (arg0 == 2) {
                clearChoise();
                titleSelectedChange(viewPager.getCurrentItem());
            }
        }

        @Override
        public void onPageScrolled(int arg0, float arg1, int arg2) {
        }

        @Override
        public void onPageSelected(int arg0) {
        }
    }

    private void titleSelectedChange(int num) {
        switch (num) {
            case R.id.rl_mobile_recharge://手机充值
            case 0:
                tv_mobile_recharge.setTextColor(main_color);
                tv_line1.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(0);
                break;
            case R.id.rl_live_pay://生活缴费
            case 1:
                tv_live_pay.setTextColor(main_color);
                tv_line2.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(1);
                break;
            case R.id.rl_telephone_recharge://固话充值
            case 2:
                tv_telephone_recharge.setTextColor(main_color);
                tv_line3.setVisibility(View.VISIBLE);
                viewPager.setCurrentItem(2);
                break;

            default:
                break;
        }
    }

    /**
     * 清除所有的选中状态
     */
    private void clearChoise() {
        tv_mobile_recharge.setTextColor(black);
        tv_line1.setVisibility(View.GONE);
        tv_live_pay.setTextColor(black);
        tv_line2.setVisibility(View.GONE);
        tv_telephone_recharge.setTextColor(black);
        tv_line3.setVisibility(View.GONE);

    }
}
