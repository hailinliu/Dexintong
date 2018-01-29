package com.runtai.newdexintong.module.homepage.utils;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ImageView;

import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;

/**
 * ImageView创建工厂
 */
public class ViewFactory {
    public static ImageLoader imageloader;
    private static ImageView imageView;

    /**
     * 获取ImageView视图的同时加载显示url
     *
     * @param
     * @return
     */
    public static ImageView getImageView(Context context, String url) {

        if (context != null && url != null) {
            imageloader = ImageLoader.getInstance();
            imageView = (ImageView) LayoutInflater.from(context).inflate(
                    R.layout.view_banner, null);
            if (imageloader != null && imageView != null) {

                imageloader.displayImage(url, imageView, ImageLoadUtil.getBannerOptions(), null);
            }
            
        }
        return imageView;
    }
}
