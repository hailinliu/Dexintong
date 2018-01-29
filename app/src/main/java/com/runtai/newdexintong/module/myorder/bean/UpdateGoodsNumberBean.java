package com.runtai.newdexintong.module.myorder.bean;

/**
 * @author：rhf
 * @date：2017/8/11time17:18
 * @detail：
 */

public class UpdateGoodsNumberBean {
    /**
     * Code : 1000
     * Msg : 购物车商品数量更新成功
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
