package com.runtai.newdexintong.module.home.bean;

/**
 * @author：rhf
 * @date：2017/6/2time09:48
 * @detail：
 */

public class MessageIdentifyBean {
    /**
     * isCompleted : true
     * data : 
     * msg : 短信码已发送! 请注意查收!
     */

    private boolean isCompleted;
    private String data;
    private String msg;

    public boolean isIsCompleted() {
        return isCompleted;
    }

    public void setIsCompleted(boolean isCompleted) {
        this.isCompleted = isCompleted;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
