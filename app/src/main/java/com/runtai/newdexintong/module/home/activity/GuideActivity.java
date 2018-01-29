package com.runtai.newdexintong.module.home.activity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Parcelable;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;


import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.module.home.activity.login.LoginActivity;

import java.util.ArrayList;

/**
 * 引导页面
 */
public class GuideActivity extends BaseCommonActivity {

    private SharedPreferences sp;
    private ArrayList<View> pageViews;
    private ImageView[] imageViews;

    // 初始化背景图片
    private int[] ICON_RES = new int[]{R.mipmap.help1,
            R.mipmap.help2, R.mipmap.help3};
    private ViewGroup main;
    private ViewGroup group;
    private ViewPager viewPager;
    private ImageView iv_goto;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        
        setGuideImage();
        //满屏幕显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
    }

    /**
     * 设置引导页图片
     */
    private void setGuideImage() {
        LayoutInflater inflater = getLayoutInflater();
        /**
         * 以后如果要增加一个引导页面，直接在这里添加
         */
        pageViews = new ArrayList<View>();
        pageViews.clear();
        for (int i = 0; i < ICON_RES.length; i++) {
            View itemview = getLayoutInflater().inflate(R.layout.item_view_guide_viewpager, null);
            ImageView mImageView = (ImageView) itemview.findViewById(R.id.iv_pic);
            // 设置背景图片
            mImageView.setImageResource(ICON_RES[i]);
            // 添加到背景图片集合
            pageViews.add(itemview);
        }
        imageViews = new ImageView[pageViews.size()];
        main = (ViewGroup) inflater.inflate(R.layout.activity_guide, null);
        group = (ViewGroup) main.findViewById(R.id.viewGroup);
        viewPager = (ViewPager) main.findViewById(R.id.guidePages);
        iv_goto = (ImageView) main.findViewById(R.id.iv_goto);
        iv_goto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivityByIntent(LoginActivity.class);
                SPUtils.putBoolean(GuideActivity.this,"isFirst", false);
                finish();
            }
        });

        for (int i = 0; i < pageViews.size(); i++) {
            imageView = new ImageView(this);
            LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                    ViewGroup.LayoutParams.WRAP_CONTENT);
            params.setMargins(20, 0, 20, 0);
            imageView.setLayoutParams(params);
            imageViews[i] = imageView;
            if (i == 0) {
                //默认选中第一张图片
                imageViews[i].setBackgroundResource(R.mipmap.dian_2);
            } else {
                imageViews[i].setBackgroundResource(R.mipmap.dian_1);
            }
            group.addView(imageViews[i]);
        }

        setContentView(main);

        viewPager.setAdapter(new GuidePageAdapter());
        viewPager.setOnPageChangeListener(new GuidePageChangeListener());
    }

    // 指引页面数据适配器
    public class GuidePageAdapter extends PagerAdapter {

        @Override
        public int getCount() {
            return pageViews.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public int getItemPosition(Object object) {
            return super.getItemPosition(object);
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            ((ViewPager) container).removeView(pageViews.get(position));
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ((ViewPager) container).addView(pageViews.get(position));
            return pageViews.get(position);
        }

        @Override
        public Parcelable saveState() {
            return null;
        }
    }

    // 指引页面更改事件监听器
    class GuidePageChangeListener implements ViewPager.OnPageChangeListener {

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            for (int i = 0; i < imageViews.length; i++) {
                imageViews[position].setBackgroundResource(R.mipmap.dian_2);

                if (position != i) {
                    imageViews[i].setBackgroundResource(R.mipmap.dian_1);
                }
            }

            if (imageViews.length - 1 == position) {
                iv_goto.setVisibility(View.VISIBLE);

            } else {

                iv_goto.setVisibility(View.GONE);
            }

        }

        @Override
        public void onPageScrollStateChanged(int state) {

        }
    }
}
