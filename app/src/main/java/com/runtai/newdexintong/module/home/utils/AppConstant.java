package com.runtai.newdexintong.module.home.utils;

import android.os.Environment;

/**
 * Created by Administrator on 2016/12/15.
 */
public class AppConstant {

    /**
     * 网络请求域名
     */
    public static String BASEURL = "http://v.cndnet.cn/";
    // 测试库
   public static String BASEURL2 = "http://rt.api.cndnet.cn/";
    //上线域名
   //public static String BASEURL2 = "http://api2017.yubianli.com/";
    // 测试库分享用域名
   public static String BASEURL_SHARE = "http://rt.b2b.cndnet.cn/";
    // 上线后分享用域名
  //public static String BASEURL_SHARE = "http://sh.yubianli.com/";
    /**
     * AppId对应的值
     */
    public static String appid_value = "1";
    /**
     * AppSercet对应的值
     */
    public static String appsercet_value = "G3xpqwOp+6gHk4+InpZ9Ag==";
    /**
     * 请求头对应的key值
     */
    public static String HEADER_NAME = "api_version";
    /**
     * 请求头对应版本号
     */
    public static String HEADER_VERSION = "1.0";

    /**
     * 从微信官方网站申请到的appId
     */
    public static final String APP_ID = "wxae23a3678b353516";
    /**
     * 微信支付用的商户号
     */
    public static final String MCH_ID = "1491102962";

    /**
     * 支付宝支付用的商户PID
     */
    public static final String PARTNER = "2088511795161523";
//    public static final String PARTNER = "2017102009411421";

    /**
     * 支付宝支付用的商户收款账号
     */
    public static final String SELLER = "mqb315@126.com";


    /**
     * 扫一扫请求码
     */
    public static final int REQUEST_CODE_SCAN = 0x0000;

    /**
     * 我的订单用到的
     */
    public static final String MERCHANT_DETAILS_PAGE = "MERCHANT_DETAILS_PAGE";

    public static String APP_ROOT_PATH = "yubianlib2b";
    /**
     * 图片缓存的目录
     */
    public static final String APP_IMAGE_LOADER = "/imageLoader/";

    /*
     *
	 *   mnt/sdcard/mobilesafe
	 
	 **/
    public static final String ROOT_PUBLIC_PATH = Environment.getExternalStorageDirectory().getPath() + "/" + APP_ROOT_PATH;


    /*
     * mnt/sdcard/mobilesafe/download
     * 
     */
    public static final String DOWNLOAD_PUBLIC_PATH = ROOT_PUBLIC_PATH + "/download";


}
