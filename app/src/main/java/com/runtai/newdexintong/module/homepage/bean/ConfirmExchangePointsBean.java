package com.runtai.newdexintong.module.homepage.bean;

/**
 * @author：rhf
 * @date：2017/7/28time14:52
 * @detail：
 */

public class ConfirmExchangePointsBean {
    /**
     * Code : 1000
     * Msg : 积分兑换成功! 请查看兑换记录!
     * Data : 
     */

    private int Code;
    private String Msg;
    private String Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public String getData() {
        return Data;
    }

    public void setData(String Data) {
        this.Data = Data;
    }
}
