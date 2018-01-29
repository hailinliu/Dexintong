package com.runtai.newdexintong.module.personalcenter.activity;

import android.content.Intent;
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
import com.runtai.newdexintong.module.personalcenter.fragment.OrderAccountFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.RechargeAccountFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * @作者：rhf
 * @日期：2017/3/28时间16:07
 * @描述：我要加款
 */

public class AddMoneyActivity extends BaseActivity {

    private RelativeLayout head_back;
    private TabLayout jiakuan_tablayout;
    private ViewPager jiakuan_viewpager;

    private List<String> mTitle = new ArrayList<String>();
    private List<Fragment> mFragment = new ArrayList<Fragment>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_jiakuan);

        initView();
        initData();
        setDate();
    }

    private void initView() {
        setActivityTitle();
        jiakuan_tablayout = (TabLayout) findViewById(R.id.jiakuan_tablayout);
        jiakuan_viewpager = (ViewPager) findViewById(R.id.jiakuan_viewpager);
        jiakuan_viewpager.setOffscreenPageLimit(2);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我要加款");

        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("加款记录");
        tv_subtitle.setOnClickListener(this);
    }

    private void initData() {

        mTitle.add("订货账户");
        mTitle.add("充值账户");
        mFragment.add(new OrderAccountFragment());
        mFragment.add(new RechargeAccountFragment());
    }

    private void setDate() {
        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), mTitle, mFragment);
        jiakuan_viewpager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        jiakuan_tablayout.setupWithViewPager(jiakuan_viewpager);
        //使用ViewPager的适配器
        jiakuan_tablayout.setTabsFromPagerAdapter(adapter);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle://点击打开加款记录
                Intent intent = new Intent(this,AddMoneyRecordActivity.class);
                startActivityByIntent(intent);
                break;
            default:
                break;
        }
    }


}
