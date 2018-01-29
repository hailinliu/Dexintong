package com.runtai.newdexintong.module.personalcenter.adapter;

import java.util.List;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;
import android.view.ViewGroup;

public class ViewPageAdapter extends FragmentStatePagerAdapter {

	private List<Fragment> list;

	public ViewPageAdapter(FragmentManager fm, List<Fragment> list) {
		super(fm);
		this.list = list;
	}

	public ViewPageAdapter(FragmentManager fm) {
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
