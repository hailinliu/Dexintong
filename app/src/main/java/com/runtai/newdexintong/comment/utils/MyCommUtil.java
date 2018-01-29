package com.runtai.newdexintong.comment.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.app.ActivityManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.text.ClipboardManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.view.inputmethod.InputMethodManager;

import com.runtai.newdexintong.module.home.utils.AppConstant;

import java.io.File;


public class MyCommUtil {


    /**
     * 打开打电话界面
     *
     * @param ctx
     * @param phoneUrl
     */
    public static void callPhone(Context ctx, String phoneUrl) {
        Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneUrl));
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        ctx.startActivity(intent);
    }

    /**
     * 查询当前设备是否显示了虚拟按键
     * <p/>
     * api17及以上才会调用，17一下return false
     *
     * @param windowManager
     * @return
     */
    @TargetApi(Build.VERSION_CODES.JELLY_BEAN_MR1)
    public static boolean hasSoftKeys(WindowManager windowManager) {

        if (Build.VERSION.SDK_INT < 17) {
            return false;
        }

        Display d = windowManager.getDefaultDisplay();

        DisplayMetrics realDisplayMetrics = new DisplayMetrics();
        d.getRealMetrics(realDisplayMetrics);

        int realHeight = realDisplayMetrics.heightPixels;
        int realWidth = realDisplayMetrics.widthPixels;

        DisplayMetrics displayMetrics = new DisplayMetrics();
        d.getMetrics(displayMetrics);

        int displayHeight = displayMetrics.heightPixels;
        int displayWidth = displayMetrics.widthPixels;

        return (realWidth - displayWidth) > 0
                || (realHeight - displayHeight) > 0;
    }


    public static void dismissInput(Activity act) {

        InputMethodManager systemService = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = act.getCurrentFocus();

        if (currentFocus != null) {
            systemService.hideSoftInputFromWindow(currentFocus.getWindowToken(), InputMethodManager.HIDE_NOT_ALWAYS);

        }

    }

    public static void showInput(Activity act) {

        InputMethodManager systemService = (InputMethodManager) act.getSystemService(Context.INPUT_METHOD_SERVICE);

        View currentFocus = act.getCurrentFocus();

        if (currentFocus != null) {
            systemService.showSoftInput(currentFocus, 0);

        }

    }

    /**
     * 判断wifi网络是否可用
     */

    public static boolean isWifiDataEnable(Context context) {
        try {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            boolean isWifiDataEnable = false;
            isWifiDataEnable = connectivityManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).isConnectedOrConnecting();
            return isWifiDataEnable;

        } catch (Exception e) {
            // TODO Auto-generated catch block
            return false;
        }
    }

    public static String getMac(Context ctx) {

        String macAddress = "0";

        WifiManager wifi = (WifiManager) ctx.getSystemService(Context.WIFI_SERVICE);

        WifiInfo info = wifi.getConnectionInfo();

        macAddress = info.getMacAddress();

        return macAddress;

    }

    /**
     * 获取包名
     *
     * @param act
     * @return
     */
    public static String getPackageName(Context act) {
        PackageManager pm = act.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(act.getPackageName(), 0);
            return packInfo.packageName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本名称
     *
     * @param act
     * @return
     */
    public static String getVersionName(Context act) {
        PackageManager pm = act.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(act.getPackageName(), 0);
            return packInfo.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    /**
     * 获取版本号码
     *
     * @param act
     * @return
     */
    public static int getVersionCode(Context act) {
        PackageManager pm = act.getPackageManager();
        try {
            PackageInfo packInfo = pm.getPackageInfo(act.getPackageName(), 0);
            return packInfo.versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return 1;
        }
    }

    @SuppressWarnings("deprecation")
    public static File getUriToFile(Activity ctx, Uri uri) {
        File file = null;
        try {
            String[] proj = {MediaStore.Images.Media.DATA};

            Cursor actualimagecursor = ctx.managedQuery(uri, proj, null, null, null);

            int actual_image_column_index = actualimagecursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);

            actualimagecursor.moveToFirst();

            String img_path = actualimagecursor.getString(actual_image_column_index);

            file = new File(img_path);
        } catch (Exception e) {
            e.printStackTrace();
        }

        return file;
    }

    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * get Screen metrics
     *
     * @return
     */
    public static DisplayMetrics getScreenMetrics(Activity context) {
        DisplayMetrics dm = new DisplayMetrics();
        context.getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm;
    }


    public static File getImgPath() {

        if (isSDcardExist()) {
            File sdCard = MyCommUtil.getSDCard();
            return new File(sdCard + "/" + AppConstant.APP_ROOT_PATH + AppConstant.APP_IMAGE_LOADER);
        }

        return null;
    }

    /**
     * 判断是否有sd卡
     *
     * @return
     */
    public static boolean isSDcardExist() {

        return Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED);
    }

    /**
     * 获取sd卡路径
     *
     * @return
     */
    public static File getSDCard() {

        return Environment.getExternalStorageDirectory();

    }

    /**
     * 获得当前进程的名字
     *
     * @param context
     * @return 进程号
     */
    public static String getCurProcessName(Context context) {

        int pid = android.os.Process.myPid();

        ActivityManager activityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);

        for (ActivityManager.RunningAppProcessInfo appProcess : activityManager.getRunningAppProcesses()) {

            if (appProcess.pid == pid) {
                return appProcess.processName;
            }
        }
        return null;
    }

    /**
     * 判断当前网络是否可用
     *
     * @param context
     * @return
     */
    public static boolean isNetworkConnected(Context context) {
        if (context != null) {
            ConnectivityManager mConnectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo mNetworkInfo = mConnectivityManager.getActiveNetworkInfo();
            if (mNetworkInfo != null) {
                return mNetworkInfo.isAvailable();
            }
        }
        return false;
    }

    public static void copyText(String paramString, Context paramContext) {
        ((ClipboardManager) paramContext.getSystemService("clipboard")).setText(paramString.trim());
    }


    /**
     * 获取屏幕高度
     *
     * @param context
     * @return
     */
    public static int getScreenHeight(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();

        int width = wm.getDefaultDisplay().getWidth();
        return wm.getDefaultDisplay().getHeight();
    }

    /**
     * 获取屏幕宽度
     *
     * @param context
     * @return
     */
    public static int getScreenWidth(Context context) {
        WindowManager wm = ((Activity) context).getWindowManager();

        return wm.getDefaultDisplay().getWidth();
    }

    /**
     * 根据uri获取路径
     * @param context
     * @param uri
     * @return
     */
    public static String getRealFilePath( final Context context, final Uri uri ) {
        if ( null == uri ) return null;
        final String scheme = uri.getScheme();
        String data = null;
        if ( scheme == null )
            data = uri.getPath();
        else if ( ContentResolver.SCHEME_FILE.equals( scheme ) ) {
            data = uri.getPath();
        } else if ( ContentResolver.SCHEME_CONTENT.equals( scheme ) ) {
            Cursor cursor = context.getContentResolver().query( uri, new String[] { MediaStore.Images.ImageColumns.DATA }, null, null, null );
            if ( null != cursor ) {
                if ( cursor.moveToFirst() ) {
                    int index = cursor.getColumnIndex( MediaStore.Images.ImageColumns.DATA );
                    if ( index > -1 ) {
                        data = cursor.getString( index );
                    }
                }
                cursor.close();
            }
        }
        return data;
    }
}
