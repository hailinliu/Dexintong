package com.runtai.newdexintong.module.homepage.bean;

/**
 * Created by Administrator on 2017/2/14.
 */
public class CashRackItemBean {

    public String title;

    @Override
    public String toString() {
        return "CashRackItemBean{" +
                "title='" + title + '\'' +
                '}';
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
