package com.runtai.newdexintong.module.homepage.bean;

/**
 * @author：rhf
 * @date：2017/10/16time15:57
 * @detail：
 */

public class MobileRechargeBean {
    /**
     * Code : 200
     * Data : {"MobileType":0,"Region":"371","sp":"移动","city":"郑州","province":"河南"}
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
         * MobileType : 0
         * Region : 371
         * sp : 移动
         * city : 郑州
         * province : 河南
         */

        private int MobileType;
        private String Region;
        private String sp;
        private String city;
        private String province;

        public int getMobileType() {
            return MobileType;
        }

        public void setMobileType(int MobileType) {
            this.MobileType = MobileType;
        }

        public String getRegion() {
            return Region;
        }

        public void setRegion(String Region) {
            this.Region = Region;
        }

        public String getSp() {
            return sp;
        }

        public void setSp(String sp) {
            this.sp = sp;
        }

        public String getCity() {
            return city;
        }

        public void setCity(String city) {
            this.city = city;
        }

        public String getProvince() {
            return province;
        }

        public void setProvince(String province) {
            this.province = province;
        }
    }
}
