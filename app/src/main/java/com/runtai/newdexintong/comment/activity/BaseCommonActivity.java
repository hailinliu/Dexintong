package com.runtai.newdexintong.comment.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnKeyListener;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ActivityStack;
import com.runtai.newdexintong.module.home.view.TipsToast;

public class BaseCommonActivity extends FragmentActivity implements OnClickListener {

    private ProgressDialog mLoading;
    private TipsToast tipsToast;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityStack.getInstance().addActivity(this);
    }

    @Override
    protected void onPostCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onPostCreate(savedInstanceState);
    }


    @Override
    protected void onSaveInstanceState(Bundle arg0) {
        // TODO Auto-generated method stub
        super.onSaveInstanceState(arg0);
    }

    /**
     * 带有动画的Intent跳转
     *
     * @param
     * @param
     */
    public void startActivityByIntent(Class<? extends BaseCommonActivity> clazz) {
        startActivity(new Intent(this, clazz));
        /** <<<------右入左出 */
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void startActivityByIntent(Intent intent) {
        startActivity(intent);
        overridePendingTransition(R.anim.base_slide_right_in,
                R.anim.base_slide_remain);
    }


    /**
     * 带有动画的IntentResult跳转
     *
     * @param context
     * @param cl
     * @param requestCode
     */
    public void startActivityForResultByIntent(Context context, Class cl, int requestCode) {
        Intent intent = new Intent(context, cl);
        startActivityForResult(intent, requestCode);
        /** <<<------右入左出 */
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    /**
     * 带有动画的IntentResult跳转
     *
     * @param requestCode
     */
    public void startActivityForResultByIntent(Intent intent, int requestCode) {
        startActivityForResult(intent, requestCode);
        /** <<<------右入左出 */
        overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }


    @Override
    public void onClick(View v) {
        // TODO Auto-generated method stub

    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        
//        if (!NetUtil.isNetworkAvailable(this)) {
//            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
//        }

    }

    @Override
    protected void onStop() {
        // TODO Auto-generated method stub
        super.onStop();
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
        ActivityStack.getInstance().removeActivity(this);

    }

    public void showToast(String toast) {
        Toast.makeText(this, toast, Toast.LENGTH_SHORT).show();
    }

    @Override
    public void finish() {
        // TODO Auto-generated method stub
        super.finish();
    }

    protected void showLoading(String msg) {
        if (mLoading != null) {
            mLoading.dismiss();
        }
        mLoading = new ProgressDialog(this);
        mLoading.setCancelable(false);
        mLoading.setMessage(msg);
        mLoading.show();
        mLoading.setOnKeyListener(onKeyListener);
    }

    /**
     * 显示加载框
     */
    public void showLoading() {
        showLoading("正在加载中，请稍候...");
    }

    /**
     * 隐藏加载框
     */
    public void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }

    /**
     * add a keylistener for progress dialog
     */
    private OnKeyListener onKeyListener = new OnKeyListener() {
        @Override
        public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
            if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN) {
                dismissLoading();
            }
            return false;
        }
    };


    @Override
    public void onBackPressed() {
        finish();
        BaseCommonActivity.this.overridePendingTransition(R.anim.slide_in_left,
                R.anim.slide_out_right);
    }

    /**
     * 拨打客服电话
     *
     * @param paramContext
     */
    public void callPhone(Context paramContext) {
        paramContext.startActivity(new Intent("android.intent.action.CALL", Uri.parse("tel:4006600777")));
    }

    /**
     * 自定义吐司
     *
     * @param paramContext
     * @param paramInt
     * @param paramString
     */
    public void showTips(Context paramContext, int paramInt, String paramString) {

        if (tipsToast == null) {
            tipsToast = TipsToast.makeText(paramContext, paramString, Toast.LENGTH_SHORT);
        }

        if (Build.VERSION.SDK_INT < 14) {
            tipsToast.cancel();
        }
        tipsToast.show();
        tipsToast.setIcon(paramInt);
        tipsToast.setText(paramString);
    }

}
