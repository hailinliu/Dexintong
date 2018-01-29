package com.runtai.newdexintong.module.personalcenter.activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.personalcenter.adapter.CommonTabPagerAdapter;
import com.runtai.newdexintong.module.personalcenter.fragment.PreSaleOrderFragment;
import com.runtai.newdexintong.module.personalcenter.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * 我的订货会
 */
public class PreSaleOrdersActivity extends BaseActivity implements CommonTabPagerAdapter.TabPagerListener {

    private RelativeLayout head_back;
    private CommonTabPagerAdapter adapter;


    private SlidingTabLayout preSale_tablayout;
    private ViewPager preSaleViewPager;
    private List<String> mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_sale_orders);

        initData();
        initView();
        registerListener();
    }

    private void initData() {

        mTitle = new ArrayList<String>();
        mTitle.clear();
        mTitle.add("下单成功");
        mTitle.add("进行中");
        mTitle.add("已发货");
        mTitle.add("拒绝/退单");

        if (adapter == null) {
            adapter = new CommonTabPagerAdapter(getSupportFragmentManager(),
                    mTitle.size(), mTitle, this);
        }
    }

    private void initView() {
        setActivityTitle();
//        pullToRefresh_pre_orders = (PullToRefreshListView) findViewById(R.id.pullToRefresh_pre_orders);
//        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
//        pullToRefresh_pre_orders.setAdapter(new PreSaleOrdersAdapter(PreSaleOrdersActivity.this, mData));

        preSale_tablayout = (SlidingTabLayout) findViewById(R.id.preSale_tablayout);
        setSlideTabLayout();
        preSaleViewPager = (ViewPager) findViewById(R.id.preSaleViewPager);
        adapter.setListener(this);
        preSaleViewPager.setAdapter(adapter);
        preSale_tablayout.setViewPager(preSaleViewPager);//最后调用此方法


    }

    /**
     * 设置我的订单页面的slideTablayout
     */
    private void setSlideTabLayout() {
        preSale_tablayout.setSelectedIndicatorColors(getResources().getColor(R.color.new_title_color));//滑动条颜色
        preSale_tablayout.setTabTitleTextSize(14);
        preSale_tablayout.setDividerColors(Color.parseColor("#ffececec"));//设置分割线颜色
        preSale_tablayout.setTitleTextColor(getResources().getColor(R.color.new_title_color),
                Color.parseColor("#ff4d4b4d"));//标题字体颜色
        preSale_tablayout.setDistributeEvenly(true); //均匀平铺选项卡
    }

    private void registerListener() {
        head_back.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的订货会");
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

    @Override
    public Fragment getFragment(int position) {
        return PreSaleOrderFragment.newInstance(position);
    }
}
