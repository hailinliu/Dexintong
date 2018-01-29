package com.runtai.newdexintong.module.personalcenter.activity;

import android.content.Intent;
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
import com.runtai.newdexintong.module.personalcenter.fragment.OrderStatusFragment;
import com.runtai.newdexintong.module.personalcenter.view.SlidingTabLayout;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者：rhf
 * @日期：2017/2/14时间17:02
 * @描述：我的订单
 */

public class MyOrderActivity extends BaseActivity implements CommonTabPagerAdapter.TabPagerListener {

    private SlidingTabLayout wddd_tablayout;
    private ViewPager wddd_viewpager;

    private List<String> mTitle;

    private int status = 0;
    private Intent intent;

    private RelativeLayout head_back;
    private CommonTabPagerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_order);
        intent = this.getIntent();
        status = intent.getIntExtra("status", 0);
        initDate();
        initView();
        wddd_viewpager.setCurrentItem(status);
    }

    private void initDate() {

        mTitle = new ArrayList<String>();
        mTitle.clear();
        mTitle.add("全部");
        mTitle.add("下单成功");
        mTitle.add("配货中");
        mTitle.add("配送中");
        mTitle.add("配送完成");
        mTitle.add("已确认");

        if (adapter == null) {
            adapter = new CommonTabPagerAdapter(getSupportFragmentManager(),
                    mTitle.size(), mTitle, this);
        }
        
    }

    private void initView() {
        setActivityTitle();
        wddd_tablayout = (SlidingTabLayout) findViewById(R.id.wddd_tablayout);
        setSlideTabLayout();
        wddd_viewpager = (ViewPager) findViewById(R.id.wddd_viewpager);
        adapter.setListener(this);
        wddd_viewpager.setAdapter(adapter);
        wddd_tablayout.setViewPager(wddd_viewpager);//最后调用此方法

    }

    /**
     * 设置我的订单页面的slideTablayout
     */
    private void setSlideTabLayout() {
        wddd_tablayout.setSelectedIndicatorColors(getResources().getColor(R.color.new_title_color));//滑动条颜色
        wddd_tablayout.setTabTitleTextSize(14);
        wddd_tablayout.setDividerColors(Color.parseColor("#ffececec"));//设置分割线颜色
        wddd_tablayout.setTitleTextColor(getResources().getColor(R.color.new_title_color),
                Color.parseColor("#ff4d4b4d"));//标题字体颜色
        wddd_tablayout.setDistributeEvenly(true); //均匀平铺选项卡
//        sliding_tabs.setViewPager(viewpager);//最后调用此方法
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的订单");
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
    

    @Override
    public Fragment getFragment(int position) {

        return OrderStatusFragment.newInstance(position);

    }
    
}
