package com.runtai.newdexintong.comment.myapplication;

import android.app.Application;

import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechUtility;
import com.nostra13.universalimageloader.cache.disc.impl.UnlimitedDiskCache;
import com.nostra13.universalimageloader.cache.disc.naming.Md5FileNameGenerator;
import com.nostra13.universalimageloader.cache.memory.impl.UsingFreqLimitedMemoryCache;
import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.ImageLoaderConfiguration;
import com.nostra13.universalimageloader.core.assist.QueueProcessingType;
import com.nostra13.universalimageloader.core.download.BaseImageDownloader;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyCommUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.jpush.android.api.JPushInterface;
import okhttp3.Request;

public class MyApplication extends Application {

    private static MyApplication instance;
    private String appid;
    //    public LocationService locationService;
//    public Vibrator mVibrator;

    public static MyApplication getInstance() {
        return instance;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        
//        locationService = new LocationService(getApplicationContext());
//        mVibrator =(Vibrator)getApplicationContext().getSystemService(Service.VIBRATOR_SERVICE);
//        SDKInitializer.initialize(getApplicationContext());//初始化百度地图定位sdk
        initImageLoader();

        JPushInterface.setDebugMode(true); 	// 设置开启日志,发布时请关闭日志
        JPushInterface.init(this);// 初始化 JPush
        getAppid();
       
    }

    private String getAppid(){
        // identifyTypeValue 验证类型
        String url = AppConstant.BASEURL2 + "api/login/xf";
        String timeStamp = String.valueOf(new Date().getTime());
        String randomNumberTen = RandomUtil.getRandomNumberTen();

        Map<String, String> map = new HashMap<String, String>();
        map.clear();
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
       
        String signValue = StringUtil.getSignValue(map);
         OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                 .addParams("Timestamp", timeStamp)
                 .addParams("Nonce", randomNumberTen)
                 .addParams("AppId", AppConstant.appid_value)
                 .addParams("Sign", signValue) 
                 .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {

                try {
                    byte[] decode = Base64.decode(response);//base64解码
                    String strJson = UTF8Util.getString(decode);
                    JSONObject jsonObject = new JSONObject(strJson);
//                        int code = Integer.parseInt(jsonObject.getString("Code"));
                    appid = jsonObject.getString("Data");
                    SpeechUtility.createUtility(MyApplication.this, SpeechConstant.APPID +"="+appid);
                    //ToastUtil.showToast(MyApplication.this, appid, Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("getmessage", e.toString());
            }
        });
return appid;
    }
    private void initImageLoader() {

        ImageLoaderConfiguration mImageLoaderConfiguration = new ImageLoaderConfiguration.Builder(this).diskCacheExtraOptions(480, 800, null)
                .memoryCacheExtraOptions(480, 800).threadPoolSize(5).threadPriority(Thread.NORM_PRIORITY - 2).denyCacheImageMultipleSizesInMemory()
                .memoryCache(new UsingFreqLimitedMemoryCache(2 * 1024 * 1024)).memoryCacheSize(50 * 1024 * 1024).diskCacheSize(100 * 1024 * 1024)
                .diskCacheFileCount(100).diskCache(new UnlimitedDiskCache(MyCommUtil.getImgPath())).diskCacheFileNameGenerator(new Md5FileNameGenerator())
                // 将保存的时候的URI名称用MD5加密
                .tasksProcessingOrder(QueueProcessingType.LIFO).defaultDisplayImageOptions(DisplayImageOptions.createSimple())
                .imageDownloader(new BaseImageDownloader(this, 10 * 1000, 30 * 1000)).build();

        ImageLoader.getInstance().init(mImageLoaderConfiguration);// 全局初始化此配置

    }

 
}



