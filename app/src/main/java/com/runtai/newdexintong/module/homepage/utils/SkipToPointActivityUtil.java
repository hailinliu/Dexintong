package com.runtai.newdexintong.module.homepage.utils;

import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.fenlei.activity.ProductDetailActivity;
import com.runtai.newdexintong.module.homepage.activity.DhhActivity;
import com.runtai.newdexintong.module.homepage.activity.RegularBuyListActivity;
import com.runtai.newdexintong.module.homepage.activity.SearchResultAcivity;
import com.runtai.newdexintong.module.homepage.activity.SpecialListActivity;
import com.runtai.newdexintong.module.homepage.activity.SpecialSaleActivity;
import com.runtai.newdexintong.module.homepage.activity.SpikeActivity;

/**
 * @author：rhf
 * @date：2017/10/27time10:45
 * @detail：跳转到指定界面
 */
public class SkipToPointActivityUtil {

    /**
     * 一个参数跳转的页面
     *
     * @param ads2Url
     * @param oneParamsKey
     * @param oneParamsValue
     */
    public static void jumpToOneParams(Context mContext,String ads2Url, String oneParamsKey, String oneParamsValue) {

        if (ads2Url != null && !"".equals(ads2Url)) {
            if ("api/promotion/list".equals(ads2Url)) {
                Intent intent = new Intent(mContext, SpecialListActivity.class);
                intent.putExtra("paramName0", oneParamsKey);
                intent.putExtra("paramValue0", oneParamsValue);
                intent.putExtra("mUrl", ads2Url);
                mContext.startActivity(intent);
            } else if ("api/product/search".equals(ads2Url)) {
                Intent intent = new Intent(mContext, SearchResultAcivity.class);
                intent.putExtra("paramName0", oneParamsKey);
                intent.putExtra("paramValue0", oneParamsValue);
                intent.putExtra("mUrl", ads2Url);
                mContext.startActivity(intent);
            } else if ("api/product/detail".equals(ads2Url)) {
                Intent intent = new Intent(mContext, ProductDetailActivity.class);
                intent.putExtra("paramName0", oneParamsKey);
                intent.putExtra("paramValue0", oneParamsValue);
                intent.putExtra("mUrl", ads2Url);
                mContext.startActivity(intent);
            }

        } else {
            ToastUtil.showToast(mContext, "暂时没有设置此活动", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 两个参数跳转的页面
     *
     * @param twoParamsUrl
     * @param oneParamsKey
     * @param oneParamsValue
     * @param secParamsKey
     * @param secParamsValue
     */
    public static void jumpToTwoParams(Context mContext,String twoParamsUrl, String oneParamsKey, String oneParamsValue, 
                                 String secParamsKey, String secParamsValue) {

        if (twoParamsUrl != null && !"".equals(twoParamsUrl)) {
            if ("api/promotion/activity".equals(twoParamsUrl)) {
                Intent intent = new Intent(mContext, SpecialSaleActivity.class);
                intent.putExtra("mUrl", twoParamsUrl);
                intent.putExtra("paramName0", oneParamsKey);
                intent.putExtra("paramValue0", oneParamsValue);
                intent.putExtra("paramName1", secParamsKey);
                intent.putExtra("paramValue1", secParamsValue);
                mContext.startActivity(intent);
            }

        } else {
            ToastUtil.showToast(mContext, "暂时没有设置此活动", Toast.LENGTH_SHORT);
        }
    }

    /**
     * 没有参数跳转的页面
     *
     * @param mNoParamsUrl
     */
    public static void jumpToNoParams(Context mContext,String mNoParamsUrl) {

        if (mNoParamsUrl != null && !"".equals(mNoParamsUrl)) {
            if ("api/promotion/spike".equals(mNoParamsUrl)) {
                Intent intent = new Intent(mContext, SpikeActivity.class);
                intent.putExtra("mUrl", mNoParamsUrl);
                mContext.startActivity(intent);
            } else if ("api/promotion/dhh".equals(mNoParamsUrl)) {
                Intent intent = new Intent(mContext, DhhActivity.class);
                intent.putExtra("mUrl", mNoParamsUrl);
                mContext.startActivity(intent);
            } else if ("api/main/buy".equals(mNoParamsUrl)) {//常购清单
                Intent intent = new Intent(mContext, RegularBuyListActivity.class);
                intent.putExtra("mUrl", mNoParamsUrl);
                mContext.startActivity(intent);
            }
        } else {
            ToastUtil.showToast(mContext, "暂时没有设置此活动", Toast.LENGTH_SHORT);
        }
    }

}
