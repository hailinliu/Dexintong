package com.runtai.newdexintong.module.home.jpush.command;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.module.home.jpush.receiver.NotificationClickReceiver;


public class EventCommand extends JPushCommand {

    private static final String TAG = "EventCommand";

    @Override
    public void execute(Context context, String json, String title) {
        Log.d(TAG, "自定义消息");
        if (json != null && !json.equals("")) {
//                JSONObject jsonObject = new JSONObject(json);
//                String eventId = (String) jsonObject.get("eventId");
//                String content = (String) jsonObject.get("content");
//                content = "您有" + "'" + content + "'" + "事件需要处理";
//                String departs = (String) jsonObject.get("departs");
//                String[] departsArray = departs.split("\\|");

            showNotification(context, json, title);

        }
    }

    private void showNotification(Context context, String content, String title) {
        
        Intent clickIntent = new Intent(context, NotificationClickReceiver.class); //点击通知之后要发送的广播
        int id = (int) (System.currentTimeMillis() / 1000);

        PendingIntent contentIntent = PendingIntent.getBroadcast(context, id, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT);
        // 创建一个NotificationManager的引用
        NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);

        NotificationCompat.Builder builder = new NotificationCompat.Builder(context)
                .setWhen(System.currentTimeMillis())
                .setSmallIcon(R.mipmap.ic_launcher)
                .setTicker(title)
                .setContentTitle(context.getString(R.string.app_name))
                .setContentText(content)
                .setAutoCancel(true);
               // .setContentIntent(contentIntent);

        Notification noti = builder.build();

        noti.flags = Notification.FLAG_AUTO_CANCEL;
        noti.defaults = Notification.DEFAULT_SOUND | Notification.DEFAULT_LIGHTS;

        notificationManager.notify(id, noti);
    }
}
