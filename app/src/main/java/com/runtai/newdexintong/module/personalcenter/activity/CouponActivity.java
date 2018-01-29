package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.personalcenter.adapter.MyAdapter;
import com.runtai.newdexintong.module.personalcenter.fragment.CancledCouponFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.UnUsedCouponFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.UsedCouponFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/4/2时间09:09
 * @描述：优惠券
 */

public class CouponActivity extends BaseActivity {

    private RelativeLayout head_back;
    private TabLayout wdyhq_tablayout;
    private ViewPager wdyhq_viewpager;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_coupon);

        initView();
        http();
    }

    private void initView() {
        setActivityTitle();
        wdyhq_tablayout = (TabLayout) findViewById(R.id.wdyhq_tablayout);
        wdyhq_viewpager = (ViewPager) findViewById(R.id.wdyhq_viewpager);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的优惠券");
    }

    private void http() {

        initData();
        setData();
    }

    private void initData() {
        mTitle.add("未使用");
        mTitle.add("已使用");
        mTitle.add("已作废");

        mFragment.add(new UnUsedCouponFragment());
        mFragment.add(new UsedCouponFragment());
        mFragment.add(new CancledCouponFragment());

        wdyhq_viewpager.setOffscreenPageLimit(mTitle.size());
    }
    
    private void setData() {
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mTitle, mFragment);
        wdyhq_viewpager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        wdyhq_tablayout.setupWithViewPager(wdyhq_viewpager);
        //使用ViewPager的适配器
        wdyhq_tablayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            default:
                break;
        }
    }
    
}
