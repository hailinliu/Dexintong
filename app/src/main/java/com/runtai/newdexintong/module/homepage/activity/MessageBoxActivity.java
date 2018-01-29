package com.runtai.newdexintong.module.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.homepage.adapter.MessageBoxAdapter;
import com.runtai.newdexintong.module.homepage.bean.MessageBoxBean;

import java.util.ArrayList;
import java.util.List;

/**
 * 消息盒子界面
 */
public class MessageBoxActivity extends BaseActivity {

    private TextView tv_title;
    private RelativeLayout head_back;
    private List<MessageBoxBean> data;
    private ListView lv_message_box;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        initView();
        registerListener();
    }

    private void initView() {
        data = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            MessageBoxBean bean = new MessageBoxBean();
            if (i == 0) {
                bean.name = "订单消息";
            } else if (i == 1) {
                bean.name = "优惠消息";
            } else if (i == 2) {
                bean.name = "豫便利消息";
            }
            data.add(bean);
        }
        setActivityTitle();
        lv_message_box = (ListView) findViewById(R.id.lv_message_box);
        lv_message_box.setAdapter(new MessageBoxAdapter(this,data));
        
    }

    private void registerListener() {
        head_back.setOnClickListener(this);
        
        lv_message_box.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                MessageBoxBean messageBoxBean = data.get(position);
                if (position == 0) {
                    Intent intent = new Intent(MessageBoxActivity.this,OrderMessageActivity.class);
                    startActivityByIntent(intent);
                } else if (position == 1) {
                    Intent intent = new Intent(MessageBoxActivity.this,ReductionMessageActivity.class);
                    startActivityByIntent(intent);
                } else if (position == 2) {
                    Intent intent = new Intent(MessageBoxActivity.this,YuBianLiMessageActivity.class);
                    startActivityByIntent(intent);
                }
                
            }
        });
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("消息盒子");
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
