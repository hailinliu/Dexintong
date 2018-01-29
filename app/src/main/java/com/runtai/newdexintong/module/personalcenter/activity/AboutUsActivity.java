package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.MyCommUtil;

/**
 * @作者：rhf
 * @日期：2017/5/8时间14:54
 * @描述：关于软件
 */

public class AboutUsActivity extends BaseActivity {

    private RelativeLayout head_back;
    private TextView version_tv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_aboutus);
        initView();
        registerListener();
    }

    private void registerListener() {
        head_back.setOnClickListener(this);
    }

    private void initView() {
        setActivityTitle();
        version_tv = (TextView) findViewById(R.id.version_tv);
        //设置版本号码
        version_tv.setText(MyCommUtil.getVersionName(this));
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("关于软件");
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
