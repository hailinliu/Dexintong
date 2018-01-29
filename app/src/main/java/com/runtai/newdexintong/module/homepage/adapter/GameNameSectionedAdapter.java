package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.myorder.adapter.SectionedBaseAdapter;

/**
 * 选择游戏名称对应的适配器
 */
public class GameNameSectionedAdapter extends SectionedBaseAdapter {


    private Context mContext;

    public GameNameSectionedAdapter(Context mContext) {

        this.mContext = mContext;
    }

    @Override
    public Object getItem(int section, int position) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public long getItemId(int section, int position) {
        // TODO Auto-generated method stub
        return 0;
    }

    @Override
    public int getSectionCount() {
        return 7;
    }

    @Override
    public int getCountForSection(int section) {
        return 15;
    }

    @Override
    public View getItemView(int section, int position, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.list_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText("Section " + section + " Item " + position);
        return layout;
    }

    @Override
    public View getSectionHeaderView(int section, View convertView, ViewGroup parent) {
        LinearLayout layout = null;
        if (convertView == null) {
            LayoutInflater inflator = (LayoutInflater) parent.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            layout = (LinearLayout) inflator.inflate(R.layout.header_item, null);
        } else {
            layout = (LinearLayout) convertView;
        }
        ((TextView) layout.findViewById(R.id.textItem)).setText("A" + section);
        return layout;
    }


}
