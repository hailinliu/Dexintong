package com.runtai.newdexintong.module.homepage.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

import com.runtai.newdexintong.comment.fragment.BaseFragment;

import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

	private List<BaseFragment> list;

	public ViewPagerAdapter(FragmentManager fm, List<BaseFragment> list) {
		super(fm);
		this.list = list;
	}

	public ViewPagerAdapter(FragmentManager fm) {
		super(fm);
	}

	@Override
	public Fragment getItem(int arg0) {
		return list.get(arg0);
	}

	@Override
	public int getCount() {
		if (list != null) {
			return list.size();
		}
		return 0;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		// TODO Auto-generated method stub
		// super.destroyItem(container, position, object);
	}
}
