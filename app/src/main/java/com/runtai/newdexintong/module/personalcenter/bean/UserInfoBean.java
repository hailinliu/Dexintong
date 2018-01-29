package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @author：rhf
 * @date：2017/10/21time11:55
 * @detail：
 */

public class UserInfoBean {

    /**
     * Code : 200
     * Data : {"OBalance":61402.11,"RBalance":280.24,"JiFen":9988,"Id":4305,"SalesMan":"销售张三","Mobile":"15237125618","Name":"测试-秦亚芹","Boss":"秦亚芹"}
     * Msg : 成功
     */

    private int Code;
    private DataBean Data;
    private String Msg;

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

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public static class DataBean {
        /**
         * OBalance : 61402.11
         * RBalance : 280.24
         * JiFen : 9988
         * Id : 4305
         * SalesMan : 销售张三
         * Mobile : 15237125618
         * Name : 测试-秦亚芹
         * Boss : 秦亚芹
         */

        private double OBalance;
        private double RBalance;
        private int JiFen;
        private int Id;
        private String SalesMan;
        private String Mobile;
        private String Name;
        private String Boss;

        public double getOBalance() {
            return OBalance;
        }

        public void setOBalance(double OBalance) {
            this.OBalance = OBalance;
        }

        public double getRBalance() {
            return RBalance;
        }

        public void setRBalance(double RBalance) {
            this.RBalance = RBalance;
        }

        public int getJiFen() {
            return JiFen;
        }

        public void setJiFen(int JiFen) {
            this.JiFen = JiFen;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getSalesMan() {
            return SalesMan;
        }

        public void setSalesMan(String SalesMan) {
            this.SalesMan = SalesMan;
        }

        public String getMobile() {
            return Mobile;
        }

        public void setMobile(String Mobile) {
            this.Mobile = Mobile;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getBoss() {
            return Boss;
        }

        public void setBoss(String Boss) {
            this.Boss = Boss;
        }
    }
}
