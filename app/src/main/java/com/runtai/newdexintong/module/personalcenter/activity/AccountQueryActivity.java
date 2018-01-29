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
import com.runtai.newdexintong.module.personalcenter.fragment.OrderAccountQueryFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.OrderAccountRebateFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.RechargeAccountQueryFragment;

import java.util.ArrayList;
import java.util.List;

public class AccountQueryActivity extends BaseActivity {

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();
    private ViewPager mViewPager;
    private TabLayout mTabLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_query);

        initView();
        setDate();
        initData();
    }


    private void initView() {
        setActivityTitle();
        mTabLayout = (TabLayout) findViewById(R.id.mTabLayout);
        mViewPager = (ViewPager) findViewById(R.id.mViewPager);
        mViewPager.setOffscreenPageLimit(2);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        RelativeLayout head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("账户查询");
    }

    private void setDate() {

        mTitle.add("充值账户");
        mTitle.add("订货账户");
        mTitle.add("订货返利账户");
        mFragment.add(new RechargeAccountQueryFragment());
        mFragment.add(new OrderAccountQueryFragment());
        mFragment.add(new OrderAccountRebateFragment());
    }

    private void initData() {

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mTitle, mFragment);
        mViewPager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        mTabLayout.setupWithViewPager(mViewPager);
        //使用ViewPager的适配器
        mTabLayout.setTabsFromPagerAdapter(adapter);
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
}
