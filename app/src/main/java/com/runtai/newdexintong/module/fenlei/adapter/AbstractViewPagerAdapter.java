package com.runtai.newdexintong.module.fenlei.adapter;

import android.support.v4.view.PagerAdapter;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;

import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/4/1时间13:53
 * @描述：抽象的PagerAdapter实现类,封装了内容为View的公共操作.
 */
public abstract class AbstractViewPagerAdapter<T> extends PagerAdapter {
    protected List<T> mData;
    private SparseArray<View> mViews;

    public AbstractViewPagerAdapter(List<T> data) {
        mData = data;
        mViews = new SparseArray<View>(data.size());
    }

    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public boolean isViewFromObject(View view, Object object) {
        return view == object;
    }

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        View view = mViews.get(position);
        if (view == null) {
            view = newView(position);
            mViews.put(position, view);
        }
        container.addView(view);
        return view;
    }

    public abstract View newView(int position);

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        container.removeView(mViews.get(position));
    }

    public T getItem(int position) {
        return mData.get(position);
    }
}
