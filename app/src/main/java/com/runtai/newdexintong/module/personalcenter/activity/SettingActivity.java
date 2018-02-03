package com.runtai.newdexintong.module.personalcenter.activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.ActivityStack;
import com.runtai.newdexintong.comment.utils.DataCleanManager;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyCommUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.NetUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.activity.login.LoginActivity;
import com.runtai.newdexintong.module.home.activity.register.IdentityActivity;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.personalcenter.bean.CheckAppVersionBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

import static java.lang.Integer.parseInt;

public class SettingActivity extends BaseActivity {

    private ImageView setting_iv_back;
    private RelativeLayout setting_rl_clear;
    private RelativeLayout setting_rl_check_updata;
    private RelativeLayout setting_rl_aboutus;
    private TextView setting_tv_login_out;
    private String cache;
    private TextView setting_tv_clear_memory;

    private RelativeLayout head_back;
    private ProgressDialog progressDialog;
    private RelativeLayout setting_authentication;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);
        initView();
        try {
            cache = DataCleanManager.getTotalCacheSize(this);
        } catch (Exception e) {
            e.printStackTrace();
        }
        setting_tv_clear_memory.setText(cache);

    }

    private void initView() {

        setting_rl_clear = (RelativeLayout) findViewById(R.id.setting_rl_clear);
        setting_rl_clear.setOnClickListener(this);
        setting_rl_check_updata = (RelativeLayout) findViewById(R.id.setting_rl_check_updata);
        setting_rl_check_updata.setOnClickListener(this);
        setting_rl_aboutus = (RelativeLayout) findViewById(R.id.setting_rl_aboutus);
        setting_rl_aboutus.setOnClickListener(this);
        setting_authentication = (RelativeLayout)findViewById(R.id.setting_authentication);
        setting_authentication.setOnClickListener(this);
        setting_tv_login_out = (TextView) findViewById(R.id.setting_tv_login_out);
        setting_tv_login_out.setOnClickListener(this);
        setting_tv_clear_memory = (TextView) findViewById(R.id.setting_tv_clear_memory);
        
        setActivityTitle();
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("设置");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.setting_rl_clear:
                DataCleanManager.clearAllCache(this);
                setting_tv_clear_memory.setText("0KB");
                showToast("清理完成");
                break;
            case R.id.setting_rl_check_updata://检测版本更新
                checkAppVersion();
                break;
            case R.id.setting_rl_aboutus://关于软件
                startActivityByIntent(AboutUsActivity.class);
                break;
            case R.id.setting_authentication://身份验证
                Intent intent = new Intent(this, IdentityActivity.class);
                startActivity(intent);
                break;
            case R.id.setting_tv_login_out://退出登录
                new AlertDialog.Builder(this)
                        .setTitle("确定退出当前账号")
                        .setCancelable(false)
                        .setNegativeButton("取消", null)
                        .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {

                                SPUtils.putBoolean(SettingActivity.this, "isTourist", false);
                                SPUtils.putString(SettingActivity.this, "login_success", "");
                                SPUtils.putString(SettingActivity.this, "accessToken", "");//BasicAuth后面需要加一个英文空格
                               // startActivityByIntent(LoginActivity.class);
                                Intent intent = new Intent();
                                intent.setClass(SettingActivity.this, LoginActivity.class);
                                intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);  //注意本行的FLAG设置 
                                startActivity(intent);
                                ActivityStack.getInstance().finishActivity(MainActivity.class);
                                finish();
                            }
                        }).show();
                break;
        }
    }

    /**
     * 检查更新
     */
    private void checkAppVersion() {

        if (!NetUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }

        String url = AppConstant.BASEURL2 + "api/login/update";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Sign", signValue)
                .addParams("Timestamp", timeStamp)
                .addParams("Nonce", randomNumberTen)
                .addParams("AppId", AppConstant.appid_value)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {

                try {
                    byte[] decode = Base64.decode(response);//base64解码
                    String strJson = UTF8Util.getString(decode);
                    JSONObject jsonObject = new JSONObject(strJson);
                    int code = parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 200) {
                        Gson gson = GsonUtil.buildGson();
                        CheckAppVersionBean checkAppVersionBean = gson.fromJson(strJson, CheckAppVersionBean.class);
                        CheckAppVersionBean.DataBean data = checkAppVersionBean.getData();
                        int serverVersionCode = (int) Double.parseDouble(data.getCode());
                        String desc = data.getDesc();
                        String downloadUrl = data.getUrl();
                        int versionCode = MyCommUtil.getVersionCode(SettingActivity.this);
                        if (versionCode < serverVersionCode) {
                            showDownDialog(desc, downloadUrl);
                        } else {
                            ToastUtil.showToast(SettingActivity.this, "已是最新版本", Toast.LENGTH_SHORT);
                        }

                    } else if (code == 403) {
                        DialogUtil.showDialog(SettingActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(SettingActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismissLoading();

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
                dismissLoading();
            }
        });


    }

    /**
     * 弹出提示版本更新的对话框
     *
     * @param desc
     * @param downloadUrl
     */
    private void showDownDialog(String desc, final String downloadUrl) {

        new MyAlertDialog(this).builder()
                .setTitle("版本更新")
                .setMsg(desc)
                .setCancelable(false)
                .setPositiveButton("马上更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 下载操作 下载最新apk
                        progressDialog = new ProgressDialog(SettingActivity.this,
                                ProgressDialog.THEME_HOLO_LIGHT);
                        progressDialog.setMessage("正在下载...");
                        progressDialog.setCanceledOnTouchOutside(false);
//                        progressDialog.setCancelable(false);
                        progressDialog
                                .setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();
                        progressDialog.setMax(100);
                        downLoadNewVersionApk(downloadUrl);
                    }
                }).show();
    }

    /**
     * 下载新版本的apk安装包
     *
     * @param downloadUrl
     */
    private void downLoadNewVersionApk(String downloadUrl) {
        OkHttpUtils
                .get()
                .url(downloadUrl)
                .build()
                .execute(new FileCallBack(AppConstant.DOWNLOAD_PUBLIC_PATH,
                        "yblb2b.apk") {
                    @Override
                    public void onResponse(File response) {
                        progressDialog.dismiss();
                        File apkFile = response;
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri fileUri = Uri.fromFile(apkFile);
                        intent.setDataAndType(fileUri,
                                "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void inProgress(float progress) {
                        progressDialog.setProgress(Math
                                .round((progress * 100)));

                    }
                });
    }
}
