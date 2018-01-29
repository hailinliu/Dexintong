package com.runtai.newdexintong.module.personalcenter.util;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Environment;

import java.io.File;
import java.math.BigDecimal;
import java.util.Locale;

/**
 * 公司Android项目的包名按照下面示例进行命名
 * 
 * com.jpsycn.xxxx.android
 * 
 * 这样公司的项目就可以统一放到 jpsycn这个目录中
 * 
 * 各个项目就可以放到xxxx这个子目录中
 * 
 * @author 程辉
 * @version 2013-11-08
 * 
 */
public class SDCardUtils {

	private static final String EXTERNAL_STORAGE_PERMISSION = "android.permission.WRITE_EXTERNAL_STORAGE";

	private static final String DIR_CAMERA = "Camera";
	private static final String DIR_VIDEO = "Video";
	private static final String DIR_AUDIO = "Audio";
	private static final String DIR_CRASH = "crash";
	private static final String DIR_CACHE = "cache";
	private static final String DIR_DOWNLOAD = "Download";
	private static final String DIR_GPS = "gps";
	private static final String DIR_IMAGE = "Image";

	private static final String DIR_DOCUMENT = "document";
	/**
	 * 获取应用在sdcard中的目录 目录名为应用的包名 有无SDCard的情况 sdcard有无写权限的情况 不同api的情况
	 * 
	 * @param context
	 * @return
	 */
	public static File getAppDir(Context context) {
		File appCacheDir = null;
		if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())
				&& hasExternalStoragePermission(context)) {
			String packageName = context.getPackageName();
			String[] split = packageName.split("\\.");
			
			appCacheDir = new File(Environment.getExternalStoragePublicDirectory(split[1]
					.toUpperCase(Locale.CHINA)), split[2]);
		}
		if (appCacheDir == null || (!appCacheDir.exists() && !appCacheDir.mkdirs())) {
			appCacheDir = context.getCacheDir();
		}
		return appCacheDir;
	}

	public static File getVideoDir(Context context) {
		return getDir(context, DIR_VIDEO);
	}

	public static File getAudioDir(Context context) {
		return getDir(context, DIR_AUDIO);
	}

	public static File getCameraDir(Context context) {
		return getDir(context, DIR_CAMERA);
	}

	public static File getCacheDir(Context context) {
		return getDir(context, DIR_CACHE);
	}

	public static File getCrashDir(Context context) {
		return getDir(context, DIR_CRASH);
	}

	public static File getDownloadDir(Context context) {
		return getDir(context, DIR_DOWNLOAD);
	}
	public static File getGPSDir(Context context) {
		return getDir(context, DIR_GPS);
	}
	public static File getImageDir(Context context) {
		return getDir(context, DIR_IMAGE);
	}

	private static File getDir(Context context, String dirName) {
		File dir = new File(getAppDir(context), dirName);
		if (!dir.exists()) {
			dir.mkdirs();
		}
		return dir;
	}

	private static boolean hasExternalStoragePermission(Context context) {
		int perm = context.checkCallingOrSelfPermission(EXTERNAL_STORAGE_PERMISSION);
		return perm == PackageManager.PERMISSION_GRANTED;
	}

	public static File getDocumentDir(Context context) {
		return getDir(context, DIR_DOCUMENT);
	}
	
	/** 
     * 获取文件夹大小 
     * @param file File实例 
     * @return long 单位为M 
     * @throws Exception 
     */  
    public static double getFolderSize(File file)throws Exception{ 
    	double size = 0;
        try {
			File[] fileList = file.listFiles();
			for (int i = 0; i < fileList.length; i++)  
			{  
			    if (fileList[i].isDirectory())  
			    {  
			        size = size + getFolderSize(fileList[i]);  
			    } else  
			    {  
			        size = size + fileList[i].length();  
			    }  
			}
		} catch (Exception e) {
			e.printStackTrace();
		}  
        return size;  
    }  
    /** 
     * 格式化单位 
     * @param size 
     * @return 
     */  
    public static String getFormatSize(double size) {  
        double kiloByte = size/1024;  
        if(kiloByte < 1) {  
            return size + " Byte";  
        }  
          
        double megaByte = kiloByte/1024;  
        if(megaByte < 1) {  
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));  
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "KB";  
        }  
          
        double gigaByte = megaByte/1024;  
        if(gigaByte < 1) {  
            BigDecimal result2  = new BigDecimal(Double.toString(megaByte));  
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "MB";  
        }  
          
        double teraBytes = gigaByte/1024;  
        if(teraBytes < 1) {  
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));  
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "GB";  
        }  
        BigDecimal result4 = new BigDecimal(teraBytes);  
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString() + "TB";  
    }  
}
