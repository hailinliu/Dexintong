package com.runtai.newdexintong.module.home.widget;

import android.content.Context;
import android.view.View;

import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.utils.ActivityStack;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.activity.login.LoginActivity;

/**
 * Created by Administrator on 2017/1/14.
 * 提示信息的对话框
 */
public class DialogUtil {

    /**
     * 提示信息的对话框
     *
     * @param context 上下文
     * @param title   对话框标题
     */
    public static void showPointDialog(Context context, String title) {

        new MyAlertDialog(context)
                .builder().setTitle(title)
                .setCancelable(false)
                .setNegativeButton("知道了", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
    }

    /**
     * token失效的时候弹出去登录的对话框
     *
     * @param context
     * @param msg
     * @return
     */
    public static void showDialog(final Context context, String msg) {

        new MyAlertDialog(
                context).builder()
                .setTitle(msg)
                .setCancelable(false)
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        // 跳转到登录页面
                        SPUtils.putString(context, "accessToken", "");
                        SPUtils.putString(context, "login_success", "");
                        
                        new MyAlertDialog(context).dimissDialog();
                        ActivityStack.getInstance().finishActivity(MainActivity.class);
                        ((BaseCommonActivity) context).startActivityByIntent(LoginActivity.class);
                    }
                }).show();

    }

   

}
