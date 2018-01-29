package com.runtai.newdexintong.module.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.personalcenter.adapter.TuiKuanYuanYinAdapter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * @作者：高炎鹏
 * @日期：2017/4/10时间09:52
 * @描述：退款原因
 */

public class RefundReasonActivity extends BaseActivity {

    private Intent intent;
    private RelativeLayout head_back;
    private TuiKuanYuanYinAdapter adapter;
    private ListView tkyy_list;
    private List<String> list;
    private String[] YUANYIN = {"7天无理由退货", "订错，多订", "错发，错配，错送", "包装/规格/与描述不符",
            "少货/漏发", "包装/商品破损/变形/污渍", "假货", "商品质量问题", "其他"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tuikuanyuanyin);
        initData();
        initView();
    }

    private void initData() {
        list = new ArrayList<>();
        list = Arrays.asList(YUANYIN);
    }

    private void initView() {
        setActivityTitle();
        tkyy_list = (ListView) findViewById(R.id.tkyy_list);
        adapter = new TuiKuanYuanYinAdapter(this, list);
        tkyy_list.setAdapter(adapter);
        tkyy_list.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                intent = new Intent();
                intent.putExtra("yuanyin", list.get(position));
                setResult(59, intent);
                finish();
            }
        });
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("退款原因");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                finish();
                break;
        }
    }

}
