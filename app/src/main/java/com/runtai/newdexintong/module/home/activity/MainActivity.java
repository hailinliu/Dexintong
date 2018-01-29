package com.runtai.newdexintong.module.home.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.comment.view.NoScrollViewPager;
import com.runtai.newdexintong.module.home.adapter.HomeTabAdapter;

public class MainActivity extends BaseActivity {

    private NoScrollViewPager vp_main;
    private LinearLayout ll_tab1, ll_tab2, ll_tab3, ll_tab4;
    private ImageView iv_tab1, iv_tab2, iv_tab3, iv_tab4;
    private HomeTabAdapter adapter;
    private TextView tv_tab1, tv_tab2, tv_tab3, tv_tab4;
    private int oldPosition = -1;

    private Handler handler = new Handler();
    private boolean isShowFinish = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initView();
    }

    private void initView() {

        ll_tab1 = (LinearLayout) findViewById(R.id.ll_tab1);
        ll_tab2 = (LinearLayout) findViewById(R.id.ll_tab2);
        ll_tab3 = (LinearLayout) findViewById(R.id.ll_tab3);
        ll_tab4 = (LinearLayout) findViewById(R.id.ll_tab4);

        iv_tab1 = (ImageView) findViewById(R.id.iv_tab1);
        iv_tab2 = (ImageView) findViewById(R.id.iv_tab2);
        iv_tab3 = (ImageView) findViewById(R.id.iv_tab3);
        iv_tab4 = (ImageView) findViewById(R.id.iv_tab4);

        tv_tab1 = (TextView) findViewById(R.id.tv_tab1);
        tv_tab2 = (TextView) findViewById(R.id.tv_tab2);
        tv_tab3 = (TextView) findViewById(R.id.tv_tab3);
        tv_tab4 = (TextView) findViewById(R.id.tv_tab4);

        vp_main = (NoScrollViewPager) findViewById(R.id.vp_main);

        ll_tab1.setOnClickListener(this);
        ll_tab2.setOnClickListener(this);
        ll_tab3.setOnClickListener(this);
        ll_tab4.setOnClickListener(this);

        adapter = new HomeTabAdapter(getSupportFragmentManager());

        adapter.addHomeTab();

        vp_main.setOffscreenPageLimit(4);
        vp_main.setAdapter(adapter);

        //点击底部导航按钮,默认选择首页
        onClick(ll_tab1);
    }


    private void changeButtomStates(int position) {
        int grayColor = getResources().getColor(R.color.gray_text);
        int redColor = getResources().getColor(R.color.new_title_color);

        switch (position) {
            case 0:
                iv_tab1.setImageResource(R.mipmap.home_icon_checked);
                iv_tab2.setImageResource(R.mipmap.sort_icon_unchecked);
                iv_tab3.setImageResource(R.mipmap.order_icon_unchecked);
                iv_tab4.setImageResource(R.mipmap.personal_icon_unchecked);

                tv_tab1.setTextColor(redColor);
                tv_tab2.setTextColor(grayColor);
                tv_tab3.setTextColor(grayColor);
                tv_tab4.setTextColor(grayColor);

                break;
            case 1:
                iv_tab1.setImageResource(R.mipmap.home_icon_unchecked);
                iv_tab2.setImageResource(R.mipmap.sort_icon_checked);
                iv_tab3.setImageResource(R.mipmap.order_icon_unchecked);
                iv_tab4.setImageResource(R.mipmap.personal_icon_unchecked);

                tv_tab1.setTextColor(grayColor);
                tv_tab2.setTextColor(redColor);
                tv_tab3.setTextColor(grayColor);
                tv_tab4.setTextColor(grayColor);
                break;
            case 2:
                iv_tab1.setImageResource(R.mipmap.home_icon_unchecked);
                iv_tab2.setImageResource(R.mipmap.sort_icon_unchecked);
                iv_tab3.setImageResource(R.mipmap.order_icon_checked);
                iv_tab4.setImageResource(R.mipmap.personal_icon_unchecked);

                tv_tab1.setTextColor(grayColor);
                tv_tab2.setTextColor(grayColor);
                tv_tab3.setTextColor(redColor);
                tv_tab4.setTextColor(grayColor);
                break;
            case 3:
                iv_tab1.setImageResource(R.mipmap.home_icon_unchecked);
                iv_tab2.setImageResource(R.mipmap.sort_icon_unchecked);
                iv_tab3.setImageResource(R.mipmap.order_icon_unchecked);
                iv_tab4.setImageResource(R.mipmap.personal_icon_checked);

                tv_tab1.setTextColor(grayColor);
                tv_tab2.setTextColor(grayColor);
                tv_tab3.setTextColor(grayColor);
                tv_tab4.setTextColor(redColor);
                break;
            default:
                break;
        }

        notifyChildFragment(position);
    }


    private void notifyChildFragment(int position) {

        if (oldPosition == position) {
            return;
        }

        ((BaseFragment) adapter.getItem(position)).onFragmentStart();
        if (oldPosition != -1) {
            ((BaseFragment) adapter.getItem(oldPosition)).onFragmentStop();
        }

        oldPosition = position;
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.ll_tab1:
                changeButtomStates(0);
                changeTab(0);
                break;
            case R.id.ll_tab2:
                changeButtomStates(1);
                changeTab(1);
                break;
            case R.id.ll_tab3:
                changeButtomStates(2);
                changeTab(2);
                break;
            case R.id.ll_tab4:
                changeButtomStates(3);
                changeTab(3);
                break;
        }

    }

    public void changeTab(int position) {
        if (position < 0 || position > 3 || adapter.getList().size() == 0) {
            return;
        }
        vp_main.setCurrentItem(position, false);
    }

    @Override
    public void onBackPressed() {

        if (!isShowFinish) {
            isShowFinish = true;
            showToast("再按一次退出");
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    isShowFinish = false;
                }
            }, 2000);
        } else {
            handler.removeCallbacksAndMessages(null);
            finish();
//            super.onBackPressed();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
//        if (adapter != null) {
//            adapter.clearHomeTab();
//        }
        dismissLoading();
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
//        initView();
//        setIntent(intent);
        System.out.println(TAG + "onNewIntent");
        if ("SearchResultAcivity".equals(intent.getStringExtra("tag"))) {
            onClick(ll_tab3);
        } else if ("selected_homePage".equals(intent.getStringExtra("tag"))) {
            onClick(ll_tab1);
        } else if ("sort".equals(intent.getStringExtra("tag"))) {
            onClick(ll_tab2);
        }

    }

    public void jumpToFenlei() {
        onClick(ll_tab2);
    }



}
