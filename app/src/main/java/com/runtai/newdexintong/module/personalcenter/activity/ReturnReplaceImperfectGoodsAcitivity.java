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
import com.runtai.newdexintong.module.personalcenter.fragment.ImperfectGoodsFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.ReplaceGoodsFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.ReturnGoodsFragment;

import java.util.ArrayList;
import java.util.List;

/**
 * 退货、调货、残次界面
 */
public class ReturnReplaceImperfectGoodsAcitivity extends BaseActivity {

    private RelativeLayout head_back;
    private TabLayout mTablayout;
    private ViewPager rrg_viewpager;
    private List<String> rrg_title;
    private List<Fragment> mFragment = new ArrayList<Fragment>();

    @Override
    protected void onSaveInstanceState(Bundle arg0) {
       // super.onSaveInstanceState(arg0);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        if (savedInstanceState != null) {
            savedInstanceState.remove("android:support:fragments");  //注意：基类是Activity时参数为android:fragments， 一定要在super.onCreate函数前执行！！！
        }
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_return_and_replace_goods);
        initView();
        initData();
        
        registerListener();
    }

    private void initData() {

        rrg_title = new ArrayList<String>();
        rrg_title.clear();
        rrg_title.add("退货");
        rrg_title.add("调货");
        rrg_title.add("残次");
        mFragment.add(new ReturnGoodsFragment());
        mFragment.add(new ReplaceGoodsFragment());
        mFragment.add(new ImperfectGoodsFragment());
        rrg_viewpager.setOffscreenPageLimit(mFragment.size());

        MyAdapter adapter = new MyAdapter(getSupportFragmentManager(), rrg_title, mFragment);
        rrg_viewpager.setAdapter(adapter);
        //为TabLayout设置ViewPager
        mTablayout.setupWithViewPager(rrg_viewpager);
        //使用ViewPager的适配器
        mTablayout.setTabsFromPagerAdapter(adapter);
    }

    private void initView() {
        setActivityTitle();
        mTablayout = (TabLayout) findViewById(R.id.mTablayout);
        rrg_viewpager = (ViewPager) findViewById(R.id.rrg_viewpager);
    }

    private void registerListener() {
        
        head_back.setOnClickListener(this);
    }
    

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("退货调货");
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














