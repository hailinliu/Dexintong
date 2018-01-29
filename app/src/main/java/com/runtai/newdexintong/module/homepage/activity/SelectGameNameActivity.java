package com.runtai.newdexintong.module.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.homepage.adapter.GameNameSectionedAdapter;
import com.runtai.newdexintong.module.homepage.view.BladeView;
import com.runtai.newdexintong.module.myorder.view.PinnedHeaderListView;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 选择游戏名称界面
 */
public class SelectGameNameActivity extends BaseActivity {

    private RelativeLayout head_back;
    private BladeView mLetterListView;
    // 首字母集
    private List<String> mSections;
    // 根据首字母存放数据
    private Map<String, List<String>> mMap;
    // 首字母位置集
    private List<Integer> mPositions;
    // 首字母对应的位置
    private Map<String, Integer> mIndexer;
    private PinnedHeaderListView pinnedListView;
    private String[] datas;
    private static final String FORMAT = "^[a-z,A-Z].*$";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_select_game_name);
        initData();
        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();

        pinnedListView = (PinnedHeaderListView) findViewById(R.id.pinnedListView);
        mLetterListView = (BladeView) findViewById(R.id.mLetterListView);
        GameNameSectionedAdapter sectionedAdapter = new GameNameSectionedAdapter(this);
        pinnedListView.setAdapter(sectionedAdapter);

        mLetterListView.setOnItemClickListener(new BladeView.OnItemClickListener() {

            @Override
            public void onItemClick(String s) {
                if (mIndexer.get(s) != null) {
                    pinnedListView.setSelection(mIndexer.get(s));
                }
            }
        });

    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        //添加条目点击事件
        pinnedListView.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

                Intent intent = new Intent(SelectGameNameActivity.this, GameCardsActivity.class);
                startActivityByIntent(intent);
            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

            }
        });
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("游戏充值");

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

    private void initData() {
        datas = getResources().getStringArray(R.array.countries);
        mSections = new ArrayList<String>();
        mMap = new HashMap<String, List<String>>();
        mPositions = new ArrayList<Integer>();
        mIndexer = new HashMap<String, Integer>();

        for (int i = 0; i < datas.length; i++) {
            String firstName = datas[i].substring(0, 1);
            if (firstName.matches(FORMAT)) {
                if (mSections.contains(firstName)) {
                    mMap.get(firstName).add(datas[i]);
                } else {
                    mSections.add(firstName);
                    List<String> list = new ArrayList<String>();
                    list.add(datas[i]);
                    mMap.put(firstName, list);
                }
            } else {
                if (mSections.contains("#")) {
                    mMap.get("#").add(datas[i]);
                } else {
                    mSections.add("#");
                    List<String> list = new ArrayList<String>();
                    list.add(datas[i]);
                    mMap.put("#", list);
                }
            }
        }

        Collections.sort(mSections);
        int position = 0;
        for (int i = 0; i < mSections.size(); i++) {
            mIndexer.put(mSections.get(i), position);// 存入map中，key为首字母字符串，value为首字母在listview中位置
            mPositions.add(position);// 首字母在listview中位置，存入list中
            position += mMap.get(mSections.get(i)).size();// 计算下一个首字母在listview的位置
        }
    }
}
