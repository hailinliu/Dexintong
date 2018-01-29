package com.runtai.newdexintong.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


public class WXPayEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private static final String TAG = "WXPayEntryActivity";

    private IWXAPI api;
    TextView jieguo;
    RelativeLayout head_back;
    private TextView tv_title;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//		requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.pay_result);
        jieguo = (TextView) findViewById(R.id.jieguo);

        setActivityTitle();
        api = WXAPIFactory.createWXAPI(this, AppConstant.APP_ID);
        api.handleIntent(getIntent(), this);
    }

    /**
     * 设置界面标题栏
     */
    private void setActivityTitle() {
        
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("支付结果");
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    @Override
    public void onReq(BaseReq req) {
    }

    @Override
    public void onResp(BaseResp resp) {
        LogUtil.d(TAG, "onPayFinish, errCode = " + resp.errCode);
        
        String msg = "";

        if (resp.errCode == 0) {

            msg = "支付成功";

        } else if (resp.errCode == -1) {
            msg = "支付失败";

        } else if (resp.errCode == -2) {
            msg = "已取消支付";
        }

        if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {

            jieguo.setText(msg);
            SPUtils.putBoolean(this,"wxPaySuccess",true);
            if ("支付成功".equals(msg)) {
                ToastUtil.showToast(WXPayEntryActivity.this,"支付成功", Toast.LENGTH_SHORT);
            }
          
        }

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