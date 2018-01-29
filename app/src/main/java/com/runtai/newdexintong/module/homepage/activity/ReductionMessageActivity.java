package com.runtai.newdexintong.module.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.homepage.adapter.ReductionMessageAdapter;
import com.runtai.newdexintong.module.homepage.bean.ReductionMessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 优惠消息界面
 */
public class ReductionMessageActivity extends BaseActivity {

    private RelativeLayout head_back;
    private ListView lv_reduction_msg;
    private List<ReductionMessageBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reduction_message);
        initData();
        initView();
        registerListener();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            ReductionMessageBean bean = new ReductionMessageBean();
            bean.title = "优惠消息" + i;
            mDatas.add(bean);
        }
        
    }

    private void initView() {
        setActivityTitle();
        lv_reduction_msg = (ListView) findViewById(R.id.lv_reduction_msg);
        lv_reduction_msg.setAdapter(new ReductionMessageAdapter(this,mDatas));
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
    }

    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("优惠消息");
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
