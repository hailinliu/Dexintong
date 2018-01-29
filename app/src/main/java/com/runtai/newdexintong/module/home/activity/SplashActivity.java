package com.runtai.newdexintong.module.home.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
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
import java.lang.ref.WeakReference;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

import static java.lang.Integer.parseInt;

public class SplashActivity extends BaseActivity {
    protected static final String TAG = "SplashActivity";
    protected static final int ENTER_HOME = 1;
    protected static final int SHOW_UPDATE_DIALOG = 2;
    protected static final int URL_ERROR = 3;
    protected static final int NETWORK_ERROR = 4;
    protected static final int JSON_ERROR = 5;
    private ProgressDialog progressDialog;
    private String desc;
    private String downloadUrl;
    private String msg2;
    private Handler handler;
    
    static class MyHandler extends Handler{
        WeakReference<SplashActivity> splashActivityWeakReference;
         MyHandler(SplashActivity activity){
            splashActivityWeakReference = new WeakReference<>(activity);   
       }
        
        public void handleMessage(Message msg) {
            SplashActivity activity =  splashActivityWeakReference.get();
            if(activity!=null){
                switch (msg.what) {
                    case ENTER_HOME:// 进入主页面
                        ToastUtil.showToast(activity.getBaseContext(), "已是最新版本", Toast.LENGTH_SHORT);
                        activity.enterHome();
                        // Log.e(TAG, "进入主页面");
                        break;
                    case SHOW_UPDATE_DIALOG:// 弹出升级对话框
                        // Log.e(TAG, "弹出升级对话框");
                        activity.showDownDialog(activity.desc, activity.downloadUrl);
                        break;
                    case URL_ERROR:// URL错误
				/*
				 * Toast.makeText(getApplicationContext(), "URL错误", 1).show();
				 * finish();
				 */
                        DialogUtil.showDialog(activity.getBaseContext(), activity.getResources().getString(R.string.need_login_again));
                        activity.enterHome();
                        break;
                    case NETWORK_ERROR:// 网络异常
                        // Toast.makeText(getApplicationContext(), "亲,请连接网络哟~",
                        // 1).show();
                        // finish();
                        ToastUtil.showToast(activity.getBaseContext(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
                        activity.enterHome();
                        break;
                    case JSON_ERROR:// json解析错误
                        // Toast.makeText(SplashActivity.this, "json解析错误", 1).show();
                        ToastUtil.showToast(activity.getBaseContext(), activity.msg2, Toast.LENGTH_SHORT);
                        activity.enterHome();
                        break;
                } 
            }
            
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        handler = new MyHandler(this);
        checkAppVersion();
      
    }
    /**
     * 检查更新
     */
    private void checkAppVersion() {
        new Thread(){

            private Message msg;

            public void run(){
                msg = new Message();
              if (!NetUtil.isNetworkAvailable(SplashActivity.this)) {
                  msg.what= NETWORK_ERROR;
                  handler.sendMessage(msg);
                  dismissLoading();
                  return;
              }
             
              String url = AppConstant.BASEURL2 + "api/login/update";
              String timeStamp = MyDateUtils.getCurrentMillisecond();
              String randomNumberTen = RandomUtil.getRandomNumberTen();
              String accessToken = SPUtils.getString(SplashActivity.this, "accessToken", "");

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
                          msg2 = jsonObject.getString("Msg");
                          if (code == 200) {
                              Gson gson = GsonUtil.buildGson();
                              CheckAppVersionBean checkAppVersionBean = gson.fromJson(strJson, CheckAppVersionBean.class);
                              CheckAppVersionBean.DataBean data = checkAppVersionBean.getData();
                              int serverVersionCode = (int) Double.parseDouble(data.getCode());
                              desc = data.getDesc();
                              downloadUrl = data.getUrl();
                              int versionCode = MyCommUtil.getVersionCode(SplashActivity.this);
                              //showDownDialog(desc, downloadUrl);
                        if (versionCode < serverVersionCode) {
                          //版本更新逻辑
                           msg.what= SHOW_UPDATE_DIALOG;
                          
                        } else {
                            //提示已经是最新页面，转到enterhome界面
                            msg.what=ENTER_HOME;
                           
                        }

                          } else if (code == 403) {
                              msg.what= URL_ERROR;
                             //网络错误，提示
                             
                          } else {
                             //不了解这个，但是照样可以输出显示
                              msg.what= JSON_ERROR;
                             
                          }
                      } catch (JSONException e) {
                          e.printStackTrace();
                      }
                      handler.sendMessage(msg);  
                      dismissLoading();

                  }

                  @Override
                  public void onError(Request request, Exception e) {
                      LogUtil.e("search_result", e.toString());
                      dismissLoading();
                  }
              });
               
          } 
           
        }.start();
        

        


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
                        progressDialog = new ProgressDialog(SplashActivity.this,
                                ProgressDialog.THEME_HOLO_LIGHT);
                        progressDialog.setMessage("正在下载...");
                        progressDialog.setCanceledOnTouchOutside(false);
//                        progressDialog.setCancelable(false);
                        progressDialog.setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
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
    public void enterHome(){
        Intent intent = new Intent(getApplicationContext(), GuideActivity.class);
        startActivity(intent);
        finish();
    }
}
