package com.runtai.newdexintong.module.home.jpush.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.util.ArrayMap;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.runtai.newdexintong.module.home.jpush.bean.PushBean;
import com.runtai.newdexintong.module.home.jpush.command.EventCommand;
import com.runtai.newdexintong.module.home.jpush.command.JPushCommand;

import java.util.Collections;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;

/**
 * @author 路鹏飞
 * @version V1.0
 * @Title: JPushReceiver.java
 * @Description: 自定义广播接收器，用来接收Jpush推送下来的消息
 * @date 2014-12-31 下午1:44:38
 */
public class JPushReceiver extends BroadcastReceiver {

    private static final String TAG = "JPush";
    private static final Map<String, JPushCommand> MESSAGE_RECEIVERS;

    //初始化状态就将所需要的自定义命令注册到这个接收器中
    static {
        Map<String, JPushCommand> receivers = new ArrayMap<String, JPushCommand>();
        receivers.put("xcxf", new EventCommand());
        receivers.put("wsqs", new EventCommand());
        //receivers.put("yq", new EventCommand());
        MESSAGE_RECEIVERS = Collections.unmodifiableMap(receivers);
    }

    private Context mContext;

    @Override
    public void onReceive(Context context, Intent intent) {

        this.mContext = context;
        Bundle bundle = intent.getExtras();

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            String regId = bundle.getString(JPushInterface.EXTRA_REGISTRATION_ID);
            Log.d(TAG, "regId=" + regId);
            // 需要把regId发送给服务器，服务器根据这个id进行推送
//			saveRegId(context,regId);
        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            // 处理自定义消息
            processCustomMessage(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            int notifactionId = bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
            Log.d(TAG, "[MyReceiver] 接收到推送下来的通知的ID: " + notifactionId);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户点击打开了通知");

//			JPushInterface.reportNotificationOpened(context, bundle.getString(JPushInterface.EXTRA_MSG_ID));
//
//			// 打开自定义的Activity
//			Intent i = new Intent(context, LoginActivity.class);
//			i.putExtras(bundle);
//			i.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//			context.startActivity(i);

        } else if (JPushInterface.ACTION_RICHPUSH_CALLBACK.equals(intent.getAction())) {
            Log.d(TAG, "[MyReceiver] 用户收到到RICH PUSH CALLBACK: " + bundle.getString(JPushInterface.EXTRA_EXTRA));
            // 在这里根据 JPushInterface.EXTRA_EXTRA 的内容处理代码，比如打开新的Activity，
            // 打开一个网页等..

        } else {
            Log.d(TAG, "[MyReceiver] Unhandled intent - " + intent.getAction());
        }
    }

    @SuppressWarnings("unused")
    private void saveRegId(Context context, String regId) {
        Log.d(TAG, "sendRegId:" + regId);
        if (TextUtils.isEmpty(regId)) {
            return;
        }
//        context.getSharedPreferences(AppConstant.SHARE_FILE_NAME, Context.MODE_PRIVATE)
//                .edit().putString("", regId).commit();
    }

    @SuppressWarnings("unused")
    private void processCustomMessage(Context context, Bundle bundle) {

        String json = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String title = bundle.getString(JPushInterface.EXTRA_TITLE);

        PushBean msg = null;
        if (extras != null && !"".equals(extras)) {
            msg = new Gson().fromJson(extras, PushBean.class);
        }
        String action = msg.type;
        if (action == null && "".equals(action)) {
            Log.e(TAG, "Message received without command action");
            return;
        }

        JPushCommand command = MESSAGE_RECEIVERS.get(action);
        if (command == null) {
            Log.e(TAG, "Unknown command received: " + action);
        } else {
            Log.i(TAG, "EXECUTE......");

            command.execute(mContext, json, title);
        }
    }


}
