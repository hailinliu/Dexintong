package com.runtai.newdexintong.module.homepage.bean;

/**
 * @author：rhf
 * @date：2017/11/1time11:52
 * @detail：
 */

public class MobileRechargeSuccessBean {
    /**
     * Code : 200
     * Data : {"Balance":9801.94,"IsCompleted":true,"Msg":"下单成功! 系统处理中..."}
     * Msg : null
     */

    private int Code;
    private DataBean Data;
    private Object Msg;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public Object getMsg() {
        return Msg;
    }

    public void setMsg(Object Msg) {
        this.Msg = Msg;
    }

    public static class DataBean {
        /**
         * Balance : 9801.94
         * IsCompleted : true
         * Msg : 下单成功! 系统处理中...
         */

        private double Balance;
        private boolean IsCompleted;
        private String Msg;

        public double getBalance() {
            return Balance;
        }

        public void setBalance(double Balance) {
            this.Balance = Balance;
        }

        public boolean isIsCompleted() {
            return IsCompleted;
        }

        public void setIsCompleted(boolean IsCompleted) {
            this.IsCompleted = IsCompleted;
        }

        public String getMsg() {
            return Msg;
        }

        public void setMsg(String Msg) {
            this.Msg = Msg;
        }
    }
}
