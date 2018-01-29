package com.runtai.newdexintong.module.home.bean;

/**
 * @作者：rhf
 * @日期：2017/5/31时间15:15
 * @描述：
 */

public class KeyValueBean  {


    /**
     * Code : 200
     * Data : null
     * Msg : 短信码已发送! 请注意查收
     */

    private int Code;
    private Object Data;
    private String Msg;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public Object getData() {
        return Data;
    }

    public void setData(Object Data) {
        this.Data = Data;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }
}
