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
import com.runtai.newdexintong.module.personalcenter.fragment.PointsExchangeFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.PointsOriginFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者：rhf
 * @日期：2017/3/28时间16:07
 * @描述：积分记录
 */

public class PointsRecordActivity extends BaseActivity {

    private RelativeLayout head_back;
    private TabLayout jfdh_tablayout;
    private ViewPager jfdh_viewpager;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jifenjilu);
        initView();
        initData();
        setDate();
    }

    private void initView() {
        setActivityTitle();
        jfdh_tablayout = (TabLayout) findViewById(R.id.jfdh_tablayout);
        jfdh_viewpager = (ViewPager) findViewById(R.id.jfdh_viewpager);
        jfdh_viewpager.setOffscreenPageLimit(2);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("积分记录");
    }

    private void initData() {
        mTitle.add("积分兑换");
        mTitle.add("积分来源");

        mFragment.add(new PointsExchangeFragment());
        mFragment.add(new PointsOriginFragment());
    }

    private void setDate() {
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mTitle, mFragment);
        jfdh_viewpager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        jfdh_tablayout.setupWithViewPager(jfdh_viewpager);
        //使用ViewPager的适配器
        jfdh_tablayout.setTabsFromPagerAdapter(adapter);
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
