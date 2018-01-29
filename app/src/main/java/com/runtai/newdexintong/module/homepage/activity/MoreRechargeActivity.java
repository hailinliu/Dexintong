package com.runtai.newdexintong.module.homepage.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;

/**
 * 更多充值
 */
public class MoreRechargeActivity extends BaseActivity {

    private RelativeLayout head_back;
    private RelativeLayout rl_one_card_solution;
    private RelativeLayout rl_game_cards;
    private RelativeLayout rl_air_ticket;
    private RelativeLayout rl_train_ticket;
    private RelativeLayout rl_lottery_ticket;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_more_recharge);

        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        rl_one_card_solution = (RelativeLayout) findViewById(R.id.rl_one_card_solution);
        rl_game_cards = (RelativeLayout) findViewById(R.id.rl_game_cards);
        rl_air_ticket = (RelativeLayout) findViewById(R.id.rl_air_ticket);
        rl_train_ticket = (RelativeLayout) findViewById(R.id.rl_train_ticket);
        rl_lottery_ticket = (RelativeLayout) findViewById(R.id.rl_lottery_ticket);
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        rl_one_card_solution.setOnClickListener(this);
        rl_game_cards.setOnClickListener(this);
        rl_air_ticket.setOnClickListener(this);
        rl_train_ticket.setOnClickListener(this);
        rl_lottery_ticket.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("更多充值");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.rl_one_card_solution://一卡通充值
                startActivityByIntent(OneCardSolutionActivity.class);
                break;
            case R.id.rl_game_cards://游戏充值
                startActivityByIntent(SelectGameNameActivity.class);
                break;
            case R.id.rl_air_ticket://购买飞机票
                break;
            case R.id.rl_train_ticket://购买火车票
                break;
            case R.id.rl_lottery_ticket://购买彩票
                break;

        }
    }
}
