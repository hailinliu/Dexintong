package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;


/**
 * @作者：高炎鹏
 * @日期：2017/3/29时间11:49
 * @描述：店铺地址
 */

public class ModifyStoreAddressActivity extends BaseActivity {

    private EditText dpdzxg_xingming, dpdzxg_dianhua, dpdzxg_dizhi, dpdzxg_xiangxidizhi;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianpudizhixiugai);
        initView();
    }

    private void initView() {
        dpdzxg_xingming = (EditText) findViewById(R.id.dpdzxg_xingming);
        dpdzxg_dianhua = (EditText) findViewById(R.id.dpdzxg_dianhua);
        dpdzxg_dizhi = (EditText) findViewById(R.id.dpdzxg_dizhi);
        dpdzxg_xiangxidizhi = (EditText) findViewById(R.id.dpdzxg_xiangxidizhi);

        setActivityTitle();
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        RelativeLayout head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("店铺地址");

        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("完成");
        tv_subtitle.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart", "网络请求");
//        okHttp();
    }
    

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                finish();
                break;
            case R.id.tv_subtitle://完成
                new MyAlertDialog(ModifyStoreAddressActivity.this)
                        .builder()
                        .setMsg("为保证您的账户安全，地址修改需审核，请耐心等待！")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                showTips(ModifyStoreAddressActivity.this, R.mipmap.toast_right, "提交成功");
                            }
                        }).show();
                break;
            default:
                break;
        }
    }

}
