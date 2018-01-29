package com.runtai.newdexintong.module.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;

/**
 * 游戏点卡充值
 */
public class GameCardsActivity extends BaseActivity {

    private RelativeLayout head_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game_cards);

        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
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
}
