package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/28time13:55
 * @detail：
 */

public class MyPointsDataBean {

    /**
     * Code : 200
     * Data : [{"RequiredIntegral":15,"LimitMoney":500,"Denomination":10,"Id":4,"Name":"921"},{"RequiredIntegral":2,"LimitMoney":20,"Denomination":5,"Id":3,"Name":"余额售卖代金券20减5"},{"RequiredIntegral":20,"LimitMoney":20,"Denomination":10,"Id":2,"Name":"积分兑换代金券20-10"},{"RequiredIntegral":100,"LimitMoney":10,"Denomination":10,"Id":1,"Name":"10元代金券"}]
     * Msg : 成功
     */

    private int Code;
    private String Msg;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * RequiredIntegral : 15
         * LimitMoney : 500
         * Denomination : 10
         * Id : 4
         * Name : 921
         */

        private int RequiredIntegral;
        private int LimitMoney;
        private int Denomination;
        private int Id;
        private String Name;

        public int getRequiredIntegral() {
            return RequiredIntegral;
        }

        public void setRequiredIntegral(int RequiredIntegral) {
            this.RequiredIntegral = RequiredIntegral;
        }

        public int getLimitMoney() {
            return LimitMoney;
        }

        public void setLimitMoney(int LimitMoney) {
            this.LimitMoney = LimitMoney;
        }

        public int getDenomination() {
            return Denomination;
        }

        public void setDenomination(int Denomination) {
            this.Denomination = Denomination;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
