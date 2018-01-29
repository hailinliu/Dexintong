package com.runtai.newdexintong.module.home.jpush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.module.home.activity.login.LoginActivity;


/**
 * @author
 * @version V1.0
 * @Title: NotificationClickReceiver.java
 * @Description: 推送通知栏点击广播接收器
 * @date 2016-3-4 上午9:24:18
 */
public class NotificationClickReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {

        String token = SPUtils.getString(context, "token", "");

        if ("".equals(token) || token.length() == 0) {// 没有登录
            Intent loginIntent = new Intent(context, LoginActivity.class)
                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            context.startActivity(loginIntent);

        } else {// 登录过

            // String currentDepart = context.getSharedPreferences(
//                    AppConstant.SHARE_FILE_NAME, context.MODE_PRIVATE)
//                    .getString(AppConstant.DEPART_ID, "");

//            Intent detailIntent = new Intent(context,
//                    CaseListActivity.class)
//                    .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//
//            context.startActivity(detailIntent);

        }

    }

}
