package com.runtai.newdexintong.comment.utils;

import android.graphics.Bitmap.Config;

import com.nostra13.universalimageloader.core.DisplayImageOptions;
import com.nostra13.universalimageloader.core.assist.ImageScaleType;
import com.nostra13.universalimageloader.core.display.CircleBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.RoundedBitmapDisplayer;
import com.nostra13.universalimageloader.core.display.SimpleBitmapDisplayer;
import com.runtai.newdexintong.R;

public class ImageLoadUtil {

    private static DisplayImageOptions mBaseimageOptions;
    private static DisplayImageOptions mCircleBitmapOption;


    /**
     * 圆形的图片
     *
     * @return
     */
    public static DisplayImageOptions getCircleBitmapOption() {

        if (mCircleBitmapOption == null) {
            mCircleBitmapOption = new DisplayImageOptions.Builder().bitmapConfig(Config.RGB_565)
                    .cacheInMemory(true)
                    .cacheOnDisk(true)
                    .considerExifParams(true)
                    .imageScaleType(ImageScaleType.EXACTLY)
                    .displayer(new CircleBitmapDisplayer()).build();
        }
        return mCircleBitmapOption;
    }

    /**
     * @return
     */
    public static DisplayImageOptions getBaseImageOptions() {

        if (mBaseimageOptions == null) {
            mBaseimageOptions = new DisplayImageOptions.Builder()
                    .bitmapConfig(Config.RGB_565).cacheInMemory(true).cacheOnDisk(true).considerExifParams(true)
                    .imageScaleType(ImageScaleType.EXACTLY).displayer(new SimpleBitmapDisplayer()).build();
        }
        return mBaseimageOptions;
    }


    /**
     * 带圆角的
     *
     * @param imageOnLoading
     * @param imageForEmptyUri
     * @param imageOnFail
     * @param bitmapConfig
     * @param rounded
     * @return
     */
    public static DisplayImageOptions getImageOptions(int imageOnLoading, int imageForEmptyUri,
                                                      int imageOnFail, Config bitmapConfig, int rounded) {

        DisplayImageOptions mDisplayImageOptions = new DisplayImageOptions.Builder().showImageOnLoading(imageOnLoading)
                .showImageForEmptyUri(imageForEmptyUri).showImageOnFail(imageOnFail).bitmapConfig(bitmapConfig).cacheInMemory(true).cacheOnDisk(true)
                .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).displayer(new RoundedBitmapDisplayer(rounded)).build();

        return mDisplayImageOptions;
    }

    private static DisplayImageOptions mBannerimageOptions;

    public static DisplayImageOptions getBannerOptions() {

        if (mBannerimageOptions == null) {
            mBannerimageOptions = new DisplayImageOptions.Builder().
                    bitmapConfig(Config.ARGB_8888).cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.mipmap.no_net_show_banner_icon)
                    .showImageOnFail(R.mipmap.no_net_show_banner_icon)
                    .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).
                            displayer(new SimpleBitmapDisplayer()).build();
        }

        return mBannerimageOptions;

    }

    private static DisplayImageOptions arrayPicOptions;

    /**
     * tab4修改组图中 显示组图的选项
     *
     * @return
     */
    public static DisplayImageOptions getArrayPicOptions() {

        if (arrayPicOptions == null) {
            arrayPicOptions = new DisplayImageOptions.Builder().
                    bitmapConfig(Config.ARGB_8888).cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.mipmap.no_net_show_icon)
                    .showImageOnFail(R.mipmap.no_net_show_icon)
                    .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).
                            displayer(new SimpleBitmapDisplayer()).build();
        }

        return arrayPicOptions;

    }

    private static DisplayImageOptions defaultHeadPicOptions;

    /**
     * 加载产品的小图片的时候使用
     *
     * @return
     */
    public static DisplayImageOptions getDefaultHeadPicOptions() {

        if (defaultHeadPicOptions == null) {
            defaultHeadPicOptions = new DisplayImageOptions.Builder().
                    bitmapConfig(Config.ARGB_8888).cacheInMemory(true).cacheOnDisk(true)
                    .showImageOnLoading(R.mipmap.no_net_show_icon)
                    .showImageOnFail(R.mipmap.no_net_show_icon)
                    .considerExifParams(true).imageScaleType(ImageScaleType.EXACTLY).
                            displayer(new SimpleBitmapDisplayer()).build();
        }

        return mBannerimageOptions;

    }
}
