package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/17time10:00
 * @detail：
 */

public class FlowRechargeBean {
    /**
     * Code : 200
     * Data : [{"Price":3,"Pkg":10,"Id":2,"PkgDesc":"移动10M流量包"},{"Price":5,"Pkg":30,"Id":3,"PkgDesc":"移动30M流量包"},{"Price":10,"Pkg":70,"Id":4,"PkgDesc":"移动70M流量包"},{"Price":20,"Pkg":150,"Id":5,"PkgDesc":"移动150M流量包"},{"Price":30,"Pkg":500,"Id":6,"PkgDesc":"移动500M流量包"},{"Price":40,"Pkg":700,"Id":7,"PkgDesc":"移动700M流量包"},{"Price":50,"Pkg":1024,"Id":8,"PkgDesc":"移动1G流量包"},{"Price":70,"Pkg":2048,"Id":9,"PkgDesc":"移动2G流量包"},{"Price":100,"Pkg":3072,"Id":10,"PkgDesc":"移动3G流量包"},{"Price":130,"Pkg":4096,"Id":11,"PkgDesc":"移动4G流量包"},{"Price":180,"Pkg":6144,"Id":12,"PkgDesc":"移动6G流量包"},{"Price":280,"Pkg":11264,"Id":13,"PkgDesc":"移动11G流量包"}]
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
         * Price : 3.0
         * Pkg : 10
         * Id : 2
         * PkgDesc : 移动10M流量包
         */

        private double Price;
        private int Pkg;
        private int Id;
        private String PkgDesc;

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public int getPkg() {
            return Pkg;
        }

        public void setPkg(int Pkg) {
            this.Pkg = Pkg;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getPkgDesc() {
            return PkgDesc;
        }

        public void setPkgDesc(String PkgDesc) {
            this.PkgDesc = PkgDesc;
        }
    }
}
