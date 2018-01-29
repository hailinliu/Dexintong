package com.runtai.newdexintong.comment.utils;

import android.Manifest;
import android.content.Context;
import android.os.Build;
import android.telephony.TelephonyManager;

import com.runtai.newdexintong.comment.utils.checkpermission.CheckPermission;
import com.runtai.newdexintong.comment.utils.checkpermission.listeners.PermissionListener;
import com.runtai.newdexintong.comment.utils.checkpermission.utils.PermissionUtils;

import java.util.UUID;

public class GetUUID {

    private static String uniqueId;

    public static String getUUID(final Context context) {
        
        final TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        
        String[] LocationPM = new String[]{Manifest.permission.READ_PHONE_STATE};
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (!PermissionUtils.hasSelfPermissions(context, LocationPM)) {
                CheckPermission
                        .from(context)
                        .setPermissions(LocationPM)
                        .setRationaleMsg("该功能需要赋予读取手机状态权限，不开启将无法正常工作！")
                        .setPermissionListener(new PermissionListener() {
                            @Override
                            public void permissionGranted() {
                                LogUtil.e("6.0--权限", "开启权限成功");
                                getPhoneId(tm, context);
                            }

                            @Override
                            public void permissionDenied() {
                                LogUtil.e("6.0--权限", "开启权限失败");
                            }
                        })
                        .check();
            } else {
                getPhoneId(tm, context);
            }
        } else {
            getPhoneId(tm, context);
        }

        return uniqueId;

    }

    /**
     * 权限通过后获取
     *
     * @param tm
     * @param context
     */
    private static void getPhoneId(TelephonyManager tm, Context context) {
        final String tmDevice, tmSerial, tmPhone, androidId;
        tmDevice = "" + tm.getDeviceId();
        tmSerial = "" + tm.getSimSerialNumber();
        androidId = "" + android.provider.Settings.Secure.getString(context.getContentResolver(), android.provider.Settings.Secure.ANDROID_ID);

        final UUID deviceUuid = new UUID(androidId.hashCode(), ((long) tmDevice.hashCode() << 32) | tmSerial.hashCode());
        uniqueId = deviceUuid.toString().replaceAll("\\-", "");
    }


}
