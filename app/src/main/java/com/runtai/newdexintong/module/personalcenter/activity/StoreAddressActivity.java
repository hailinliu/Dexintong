package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.module.home.bean.LoginBean;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import okhttp3.Request;


/**
 * @作者：gyp
 * @日期：2017/3/29时间11:49
 * @描述：店铺地址
 */

public class StoreAddressActivity extends BaseActivity {

    private TextView dpdz_xingming, dpdz_dianhua, dpdz_dizhi, dpdz_xiangxidizhi;
    private RelativeLayout head_back;
    private LoginBean.DataBean.MerchantBean merchantBean;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dianpudizhi);
        initView();

        setData();
    }

    private void initView() {
        dpdz_xingming = (TextView) findViewById(R.id.dpdz_xingming);
        dpdz_dianhua = (TextView) findViewById(R.id.dpdz_dianhua);
        dpdz_dizhi = (TextView) findViewById(R.id.dpdz_dizhi);
        dpdz_xiangxidizhi = (TextView) findViewById(R.id.dpdz_xiangxidizhi);

        setActivityTitle();
    }

    /**
     * 为店铺地址中各个控件赋值
     */
    private void setData() {

        merchantBean = GsonUtil.buildGson().fromJson(SPUtils.getString(this, "merchant", ""),
                LoginBean.DataBean.MerchantBean.class);
        if (merchantBean != null) {
            dpdz_xingming.setText(merchantBean.getBoss());
            dpdz_dianhua.setText(merchantBean.getPhone());
            dpdz_xiangxidizhi.setText(merchantBean.getAddr());
            dpdz_dizhi.setText(merchantBean.getAreaName().replace("|", ""));
        } else {
            DialogUtil.showDialog(this, getResources().getString(R.string.need_login_again));
        }
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("店铺地址");

        //店铺地址不支持修改
        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setText("修改");
        tv_subtitle.setVisibility(View.GONE);
        tv_subtitle.setOnClickListener(this);
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.e("onStart", "网络请求");
//        okHttp();
    }

    /**
     * 网络请求
     */
    public void okHttp() {
        String url = "";
        String f = "";
        String token = "";
        OkHttpUtils.post().url(url).addParams("f", f).addParams("token", token)
                .build().execute(new StringCallback() {
            @Override
            public void onError(Request request, Exception e) {

            }

            @Override
            public void onResponse(String response) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle://编辑
                startActivityByIntent(ModifyStoreAddressActivity.class);
                break;
            default:
                break;
        }
    }

}
