package com.runtai.newdexintong.comment.utils;

import android.content.Context;
import android.view.Gravity;
import android.widget.Toast;

/**
 * @作者：王毅
 * @时间：2016-1-12下午4:02:42
 * @类描述：
 * @版本号：
 * @修改人：
 * @修改地址
 */
public class ToastUtil {

    private static Toast mToast;

    public static void showToast(Context context, int resId, int duration) {
        showToast(context, context.getString(resId), duration);
    }

    public static void showCenterToast(Context context, String msg, int duration) {
        if (mToast == null && context != null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
            mToast.setDuration(duration); // 避免toase重复出现
        }
        mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    public static void showToast(Context context, String msg, int duration) {
        if (mToast == null) {
            mToast = Toast.makeText(context, msg, duration);
        } else {
            mToast.setText(msg);
            mToast.setDuration(duration);// 避免toase重复出现
        }
        // mToast.setGravity(Gravity.CENTER, 0, 0);
        mToast.show();
    }

    /**
     * 使toast不在显示
     */
    public static void cancleMyToast() {
        if (mToast != null) {
            mToast.cancel();
        }
    }

}
