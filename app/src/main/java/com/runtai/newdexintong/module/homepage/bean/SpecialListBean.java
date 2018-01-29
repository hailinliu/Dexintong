package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/8/31time17:02
 * @detail：专场列表页面对应的bean
 */

public class SpecialListBean {

    public String name;

    private List<SpecialListDataBean.DataBean.ListBean.ActivitysBean> activitys;

    private List<String> imgList;
    private int ActivityId;
    private int SpecialId;

    public int getActivityId() {
        return ActivityId;
    }

    public void setActivityId(int ActivityId) {
        this.ActivityId = ActivityId;
    }

    public int getSpecialId() {
        return SpecialId;
    }

    public void setSpecialId(int SpecialId) {
        this.SpecialId = SpecialId;
    }

    public List<SpecialListDataBean.DataBean.ListBean.ActivitysBean> getActivitys() {
        return activitys;
    }

    public void setActivitys(List<SpecialListDataBean.DataBean.ListBean.ActivitysBean> activitys) {
        this.activitys = activitys;
    }

    public List<String> getImgList() {
        return imgList;
    }

    public void setImgList(List<String> imgList) {
        this.imgList = imgList;
    } 
}
