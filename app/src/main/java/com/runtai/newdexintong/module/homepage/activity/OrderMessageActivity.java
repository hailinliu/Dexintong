package com.runtai.newdexintong.module.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.homepage.adapter.OrderMessageAdapter;
import com.runtai.newdexintong.module.homepage.bean.OrderMessageBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单消息界面
 */
public class OrderMessageActivity extends BaseActivity {

    private RelativeLayout head_back;
    private ListView lv_order_message;
    private List<OrderMessageBean> mDatas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_message);
        initData();
        initView();
        registerListener();
    }

    private void initData() {
        mDatas = new ArrayList<>();
        for (int i = 0; i < 9; i++) {
            OrderMessageBean bean = new OrderMessageBean();
            bean.title = "订单消息" + i;
            mDatas.add(bean);
        }

    }

    private void initView() {
        setActivityTitle();
        lv_order_message = (ListView) findViewById(R.id.lv_order_message);
        lv_order_message.setAdapter(new OrderMessageAdapter(this,mDatas));
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
    }

    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("订单消息");
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
