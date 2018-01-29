/*
 * Copyright 2014 Google Inc. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.runtai.newdexintong.module.personalcenter.view;

import android.content.Context;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.SparseArray;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

/**
 * 这个滑动条控件通过ViewPager被用来提供一个选项卡指示功能，当用户滑动时提供反馈。
 * 使用这个组件时，把它加到视图层即可。
 * 然后在你的 {@link android.app.Activity} 或 {@link android.support.v4.app.Fragment} 中
 * 调用 {@link #setViewPager(ViewPager)} 给它提供这个布局中所使用的ViewPager。
 * <p/>
 * 其颜色可通过两种方法定义。
 * 第一种是通过 {@link #setSelectedIndicatorColors(int...)} 提供一个颜色数组。
 * 另一种是通过 {@link TabColorizer} 接口进行设置·，你可以完全控制位置与滑动条颜色的对应关系。
 * <p/>
 * 这个选项卡的视图样式可通过 {@link #setCustomTabView(int, int)} 提供Layout的Id进行定义。
 */
public class SlidingTabLayout extends HorizontalScrollView {
    /**
     * 控制选项卡下划线颜色。
     * 通过{@link #setCustomTabColorizer(TabColorizer)}来设置。
     */
    public interface TabColorizer {
        /**
         * @return 当指定位置 {@code position} 被选中时返回所设置的颜色。
         */
        int getIndicatorColor(int position);

        /**
         * @return return the color of the divider drawn to the right of {@code position}.
         */
        int getDividerColor(int position);

    }

    //选项卡的位移长度
    private static final int TITLE_OFFSET_DIPS = 84;
    //选项卡中textview的间距
    private static final int TAB_VIEW_PADDING_DIPS = 16;
    private static final int TAB_VIEW_TEXT_SIZE_SP = 12;

    private int mTitleOffset;

    private int mTabViewLayoutId;
    private int mTabViewTextViewId;
    private boolean mDistributeEvenly;

    private ViewPager mViewPager;
    private SparseArray<String> mContentDescriptions = new SparseArray<String>();
    private ViewPager.OnPageChangeListener mViewPagerPageChangeListener;

    private final SlidingTabStrip mTabStrip;

    private int frgCount;
    private List<TextView> textViewList = new ArrayList<>();
    private int tabTitleTextSize = 12;
    private int selectedTitleTextColor = Color.BLACK;
    private int unselectedTitleTextColor = Color.BLACK;

    public SlidingTabLayout(Context context) {
        this(context, null);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public SlidingTabLayout(Context context, AttributeSet attrs, int defStyle) {
        super(context, attrs, defStyle);

        // Disable the Scroll Bar
        setHorizontalScrollBarEnabled(false);
        // Make sure that the Tab Strips fills this View
        setFillViewport(true);

        mTitleOffset = (int) (TITLE_OFFSET_DIPS * getResources().getDisplayMetrics().density);

        mTabStrip = new SlidingTabStrip(context);
        addView(mTabStrip, LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
    }

    public void setTitleTextColor(int selectedTitleTextColor, int unselectedTitleTextColor) {
        this.selectedTitleTextColor = selectedTitleTextColor;
        this.unselectedTitleTextColor = unselectedTitleTextColor;
    }

    public void setTabTitleTextSize(int tabTitleTextSize) {
        this.tabTitleTextSize = tabTitleTextSize;
    }

    /**
     * 设置自定义下划线颜色 {@link TabColorizer}
     * 如果你只想定制颜色那么
     * 通过{@link #setSelectedIndicatorColors(int...)}
     * 就可以达到类似的结果
     */
    public void setCustomTabColorizer(TabColorizer tabColorizer) {
        mTabStrip.setCustomTabColorizer(tabColorizer);
    }

    /**
     * 均匀平铺选项卡
     */
    public void setDistributeEvenly(boolean distributeEvenly) {
        mDistributeEvenly = distributeEvenly;
    }

    /**
     * 设置颜色用于指明选中的选项卡。
     * 这些颜色被视为一个循环数组。
     * 如果只设置一种颜色则所有下划线都用同一种颜色。
     */
    public void setSelectedIndicatorColors(int... colors) {
        mTabStrip.setSelectedIndicatorColors(colors);
    }

    /**
     * Sets the colors to be used for tab dividers. These colors are treated as a circular array.
     * Providing one color will mean that all tabs are indicated with the same color.
     * 设置分割线的颜色
     */
    public void setDividerColors(int... colors) {
        mTabStrip.setDividerColors(colors);
    }


    /**
     * Set the {@link ViewPager.OnPageChangeListener}. When using {@link SlidingTabLayout} you are
     * required to set any {@link ViewPager.OnPageChangeListener} through this method. This is so
     * that the layout_select_time_section can update it's scroll position correctly.
     *
     * @see ViewPager#setOnPageChangeListener(ViewPager.OnPageChangeListener)
     */
    public void setOnPageChangeListener(ViewPager.OnPageChangeListener listener) {
        mViewPagerPageChangeListener = listener;
    }

    /**
     * Set the custom layout_select_time_section to be inflated for the tab views.
     *
     * @param layoutResId Layout id to be inflated
     * @param textViewId  id of the {@link TextView} in the inflated view
     */
    public void setCustomTabView(int layoutResId, int textViewId) {
        mTabViewLayoutId = layoutResId;
        mTabViewTextViewId = textViewId;
    }

    /**
     * Sets the associated view pager. Note that the assumption here is that the pager content
     * (number of tabs and tab titles) does not change after this call has been made.
     */
    public void setViewPager(ViewPager viewPager) {
        mTabStrip.removeAllViews();

        mViewPager = viewPager;
        if (viewPager != null) {
            populateTabStrip();
            viewPager.addOnPageChangeListener(new InternalViewPagerListener());
        }
    }

    /**
     * Create a default view to be used for tabs. This is called if a custom tab view is not set via
     * {@link #setCustomTabView(int, int)}.
     */
    protected TextView createDefaultTabView(Context context) {
        TextView textView = new TextView(context);
//        textView.setGravity(Gravity.CENTER);
        textView.setGravity(17);
//        textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, TAB_VIEW_TEXT_SIZE_SP);
        textView.setTextSize(2, 12.0F);
        textView.setTypeface(Typeface.DEFAULT);
        textView.setLayoutParams(new LinearLayout.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));

//        TypedValue outValue = new TypedValue();
//        getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground,
//                outValue, true);
//        textView.setBackgroundResource(outValue.resourceId);
//        textView.setAllCaps(true);
//
//        int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
//        textView.setPadding(padding, padding, padding, padding);
        if (Build.VERSION.SDK_INT >= 11) {
            TypedValue localTypedValue = new TypedValue();
            getContext().getTheme().resolveAttribute(android.R.attr.selectableItemBackground, localTypedValue, true);
            textView.setBackgroundResource(localTypedValue.resourceId);
        }
        if (Build.VERSION.SDK_INT >= 14) {
            textView.setAllCaps(true);
            int padding = (int) (TAB_VIEW_PADDING_DIPS * getResources().getDisplayMetrics().density);
            textView.setPadding(padding+30, padding, padding+30, padding);
        }
        return textView;
    }

    private void populateTabStrip() {
        final PagerAdapter adapter = mViewPager.getAdapter();
        frgCount = adapter.getCount();

        for (int i = 0; i < adapter.getCount(); i++) {
            View tabView = null;
            TextView tabTitleView = null;

            if (mTabViewLayoutId != 0) {
                // If there is a custom tab view layout_select_time_section id set, try and inflate it
                tabView = LayoutInflater.from(getContext()).inflate(mTabViewLayoutId, mTabStrip,
                        false);
                tabTitleView = (TextView) tabView.findViewById(mTabViewTextViewId);
            }

            if (tabView == null) {
                tabView = createDefaultTabView(getContext());
            }

            if (tabTitleView == null && TextView.class.isInstance(tabView)) {
                tabTitleView = (TextView) tabView;
            }

            if (mDistributeEvenly) {
                LinearLayout.LayoutParams lp = (LinearLayout.LayoutParams) tabView.getLayoutParams();
                lp.width = 0;
                lp.weight = 1;
            }
            tabTitleView.setText(adapter.getPageTitle(i));
            tabTitleView.setTextSize(tabTitleTextSize);
            tabTitleView.setMaxLines(1);
            tabView.setOnClickListener(new TabClickListener());
            String desc = mContentDescriptions.get(i, null);
            if (desc != null) {
                tabView.setContentDescription(desc);
            }

            mTabStrip.addView(tabView);
            if (i == mViewPager.getCurrentItem()) {
                tabView.setSelected(true);
            }
            textViewList.add(tabTitleView);
        }

        textViewList.get(0).setTextColor(selectedTitleTextColor);
        for (int i = 1; i < frgCount; i++) {
            textViewList.get(i).setTextColor(unselectedTitleTextColor);
        }
    }

    public void setContentDescription(int i, String desc) {
        mContentDescriptions.put(i, desc);
    }

    @Override
    protected void onAttachedToWindow() {
        super.onAttachedToWindow();

        if (mViewPager != null) {
            scrollToTab(mViewPager.getCurrentItem(), 0);
        }
    }

    private void scrollToTab(int tabIndex, int positionOffset) {
        final int tabStripChildCount = mTabStrip.getChildCount();
        if (tabStripChildCount == 0 || tabIndex < 0 || tabIndex >= tabStripChildCount) {
            return;
        }

        View selectedChild = mTabStrip.getChildAt(tabIndex);
        if (selectedChild != null) {
            int targetScrollX = selectedChild.getLeft() + positionOffset;

            if (tabIndex > 0 || positionOffset > 0) {
                // If we're not at the first child and are mid-scroll, make sure we obey the offset
                targetScrollX -= mTitleOffset;
            }

            scrollTo(targetScrollX, 0);
        }
    }

    private class InternalViewPagerListener implements ViewPager.OnPageChangeListener {
        private int mScrollState;

        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            int tabStripChildCount = mTabStrip.getChildCount();
            if ((tabStripChildCount == 0) || (position < 0) || (position >= tabStripChildCount)) {
                return;
            }

            mTabStrip.onViewPagerPageChanged(position, positionOffset);

            View selectedTitle = mTabStrip.getChildAt(position);
            int extraOffset = (selectedTitle != null)
                    ? (int) (positionOffset * selectedTitle.getWidth())
                    : 0;
            scrollToTab(position, extraOffset);

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrolled(position, positionOffset,
                        positionOffsetPixels);
            }
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            mScrollState = state;

            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageScrollStateChanged(state);
            }
        }

        @Override
        public void onPageSelected(int position) {
            for (int i = 0; i < frgCount; i++) {
                if (position == i) {
                    textViewList.get(i).setTextColor(selectedTitleTextColor);
                } else {
                    textViewList.get(i).setTextColor(unselectedTitleTextColor);
                }
            }
            if (mScrollState == ViewPager.SCROLL_STATE_IDLE) {
                mTabStrip.onViewPagerPageChanged(position, 0f);
                scrollToTab(position, 0);
            }
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                mTabStrip.getChildAt(i).setSelected(position == i);
            }
            if (mViewPagerPageChangeListener != null) {
                mViewPagerPageChangeListener.onPageSelected(position);
            }
        }

    }

    private class TabClickListener implements OnClickListener {
        @Override
        public void onClick(View v) {
            for (int i = 0; i < mTabStrip.getChildCount(); i++) {
                if (v == mTabStrip.getChildAt(i)) {
                    mViewPager.setCurrentItem(i);
                    return;
                }
            }
        }
    }

}
