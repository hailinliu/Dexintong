package com.runtai.newdexintong.module.personalcenter.bean;

import java.io.Serializable;

/**
 * @作者：高炎鹏
 * @日期：2017/3/29时间15:54
 * @描述：
 */

public class OrderBeanItem implements Serializable {

    private String image;//图片
    private String name;//名称
    private String guige;//规格
    private String danjia;//单价
    private String shuliang;//数量
    private String zonge;//总额
    private boolean isCheck;

    public boolean isCheck() {
        return isCheck;
    }

    public void setCheck(boolean check) {
        isCheck = check;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGuige() {
        return guige;
    }

    public void setGuige(String guige) {
        this.guige = guige;
    }

    public String getDanjia() {
        return danjia;
    }

    public void setDanjia(String danjia) {
        this.danjia = danjia;
    }

    public String getShuliang() {
        return shuliang;
    }

    public void setShuliang(String shuliang) {
        this.shuliang = shuliang;
    }

    public String getZonge() {
        return zonge;
    }

    public void setZonge(String zonge) {
        this.zonge = zonge;
    }
}
