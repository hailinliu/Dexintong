package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * Created by baiyuliang on 2016-5-27.
 */
public class HomepageSpecialBean extends Object {
    private String text;
    private List<Object> imgList;
    private List<HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean.ViewAdLocationsBean> viewAdLocations;

    public List<HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean.ViewAdLocationsBean> getViewAdLocations() {
        return viewAdLocations;
    }

    public void setViewAdLocations(List<HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean.ViewAdLocationsBean> viewAdLocations) {
        this.viewAdLocations = viewAdLocations;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public List<Object> getImgList() {
        return imgList;
    }

    public void setImgList(List<Object> imgList) {
        this.imgList = imgList;
    }
}
