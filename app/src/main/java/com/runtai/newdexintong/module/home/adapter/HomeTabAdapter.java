package com.runtai.newdexintong.module.home.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerAdapter;

import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.module.fenlei.fragment.FenleiFragment;
import com.runtai.newdexintong.module.homepage.fragment.HomePageFragment;
import com.runtai.newdexintong.module.myorder.fragment.ShoppingCartFragment;
import com.runtai.newdexintong.module.personalcenter.fragment.PersonalCenterFragment;

import java.util.ArrayList;
import java.util.List;

public class HomeTabAdapter extends FragmentPagerAdapter {

    private List<BaseFragment> list;

    public HomeTabAdapter(FragmentManager fm) {
        super(fm);
        // TODO Auto-generated constructor stub
        list = new ArrayList<BaseFragment>();
    }

    public void clearHomeTab() {
        list.clear();
    }

    public void addHomeTab() {
        list.add(new HomePageFragment());
        list.add(new FenleiFragment());
        list.add(new ShoppingCartFragment());
        list.add(new PersonalCenterFragment());
    }

    @Override
    public Fragment getItem(int location) {
        // TODO Auto-generated method stub
        return list.get(location);
    }

    @Override
    public int getCount() {
        // TODO Auto-generated method stub
        return list.size();
    }

    public List<BaseFragment> getList() {
        return list;
    }

    public void setList(List<BaseFragment> list) {
        this.list = list;
    }

    @Override
    public int getItemPosition(Object object) {
        return PagerAdapter.POSITION_NONE;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return super.getPageTitle(position);
    }
}
