package com.runtai.newdexintong.module.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.homepage.adapter.YuBianLiMessageAdapter;
import com.runtai.newdexintong.module.homepage.bean.YuBianLiMessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 豫便利消息界面
 */
public class YuBianLiMessageActivity extends BaseActivity {

    private RelativeLayout head_back;
    private ListView lv_yubainli_msg;
    private List<YuBianLiMessageBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_yu_bian_li_message);
        initData();
        initView();
        registerListener();
    }

    private void initData() {

        mDatas = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            YuBianLiMessageBean bean = new YuBianLiMessageBean();
            bean.title = "豫便利消息" + i;
            mDatas.add(bean);
        }
    }

    private void initView() {
        setActivityTitle();
        lv_yubainli_msg = (ListView) findViewById(R.id.lv_yubainli_msg);
        lv_yubainli_msg.setAdapter(new YuBianLiMessageAdapter(this, mDatas));
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
    }

    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("系统公告");
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
