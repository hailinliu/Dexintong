package com.runtai.newdexintong.comment.activity;

import android.app.AlertDialog;
import android.content.Context;
import android.os.Bundle;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Window;
import android.widget.LinearLayout;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.DisplayUtil;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;

public  class BaseActivity extends BaseCommonActivity {

    public final String TAG = getClass().getSimpleName();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        DisplayUtil.initSystemBar(this, R.color.new_title_color);
        super.onCreate(savedInstanceState);
//        setContentView(getLayoutResource());
    }

    /**
     * 获取资源文件
     * 使用butterKnife的时候可以这样设置
     *
     * @return
     */
//    public abstract int getLayoutResource();
    
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
        MobclickAgent.onPageStart(TAG + "onPageStart");
    }

    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
        MobclickAgent.onPageEnd(TAG + "onPageEnd");
    }

    @Override
    protected void onDestroy() {
        // TODO Auto-generated method stub
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
    }

    public void showLog(String msg) {
        LogUtil.i(TAG, msg);
    }

    @Override
    protected void onSaveInstanceState(Bundle arg0) {
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {

    }

    protected LinearLayout dialog_layout;
    protected AlertDialog dialog;

    /**
     * 创建自定义对话框
     *
     * @param id
     * @param ctx
     * @return
     */
    public Window createDialog(int id, Context ctx) {
        dialog_layout = (LinearLayout) LayoutInflater.from(
                ctx).inflate(id, null);

        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = new AlertDialog.Builder(
                ctx).create();
        dialog.show();

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
//        dialogWindow.setWindowAnimations(R.style.myDialogStyle);
        dialogWindow.setContentView(dialog_layout);
        dialogWindow.setBackgroundDrawableResource(R.drawable.shape_bg_white_corner_ten);
        return dialogWindow;
    }

    /**
     * 关闭自定义对话框
     */
    public void closeDialog() {
        if (dialog != null) {
            dialog.dismiss();
        }
    }

}
