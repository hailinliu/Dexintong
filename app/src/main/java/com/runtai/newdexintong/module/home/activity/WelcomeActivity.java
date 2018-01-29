package com.runtai.newdexintong.module.home.activity;

import android.Manifest;
import android.annotation.TargetApi;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.module.home.activity.login.LoginActivity;

import java.util.ArrayList;

import cn.jpush.android.api.JPushInterface;

/**
 * 欢迎界面
 */
public class WelcomeActivity extends BaseCommonActivity {

    private View view;
    private Boolean isFirstLogin;
    private String permissionInfo;
    
    private String login_account;
    private final int SDK_PERMISSION_REQUEST = 127;
    private Handler mHandler = new Handler();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        //满屏幕显示
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
                WindowManager.LayoutParams.FLAG_FULLSCREEN);
        isFirstLogin = SPUtils.getBoolean(this, "isFirst", true);
        login_account = SPUtils.getString(WelcomeActivity.this, "login_success", "");

        //初始化jpush
        JPushInterface.init(getApplicationContext());

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                if (isFirstLogin) {
                    Intent intent = new Intent(WelcomeActivity.this, SplashActivity.class);
                    startActivityByIntent(intent);
                    finish();
                } else {
                    String token = SPUtils.getString(WelcomeActivity.this, "token", "");
//                    if ("".equals(token) || token.length() == 0) {
                    if ("".equals(login_account) || login_account.length() == 0) {

                        startActivityByIntent(LoginActivity.class);
                        finish();
                    } else {
                        Intent intent = new Intent(WelcomeActivity.this, MainActivity.class);
                        startActivityByIntent(intent);
                        finish();
                    }
                }
            }
        }, 2000);
        
        getPersimmions();
    }

    @TargetApi(23)
    private void getPersimmions() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            ArrayList<String> permissions = new ArrayList<String>();
            permissions.add(Manifest.permission.WRITE_APN_SETTINGS);
            /***
             * 定位权限为必须权限，用户如果禁止，则每次进入都会申请
             */
            // 定位精确位置
            if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_FINE_LOCATION);
            }
            if (checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
                permissions.add(Manifest.permission.ACCESS_COARSE_LOCATION);
            }
            /*
             * 读写权限和电话状态权限非必要权限(建议授予)只会申请一次，用户同意或者禁止，只会弹一次
			 */
            // 读写权限
            if (addPermission(permissions, Manifest.permission.WRITE_EXTERNAL_STORAGE)) {
                permissionInfo += "Manifest.permission.WRITE_EXTERNAL_STORAGE Deny \n";
            }
            // 读取电话状态权限
            if (addPermission(permissions, Manifest.permission.READ_PHONE_STATE)) {
                permissionInfo += "Manifest.permission.READ_PHONE_STATE Deny \n";
            }
            // 允许程序写入API设置
            if (addPermission(permissions, Manifest.permission.WRITE_APN_SETTINGS)) {
                permissionInfo += "Manifest.permission.WRITE_APN_SETTINGS Deny \n";
            }
            if (permissions.size() > 0) {
                requestPermissions(permissions.toArray(new String[permissions.size()]), SDK_PERMISSION_REQUEST);
            }
       
        }
    }

    @TargetApi(23)
    private boolean addPermission(ArrayList<String> permissionsList, String permission) {
        if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) { // 如果应用没有获得对应权限,则添加到列表中,准备批量申请	
            if (shouldShowRequestPermissionRationale(permission)) {
                return true;
            } else {
                permissionsList.add(permission);
                return false;
            }

        } else {
            return true;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }
}









