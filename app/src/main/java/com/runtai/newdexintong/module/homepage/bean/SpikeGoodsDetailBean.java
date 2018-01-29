package com.runtai.newdexintong.module.homepage.bean;

/**
 * @author：rhf
 * @date：2017/10/20time20:23
 * @detail：
 */

public class SpikeGoodsDetailBean {

    /**
     * Code : 200
     * Data : {"OriginalPrice":147.36,"KillPrice":144,"Spec":12,"SaleIncreaseCount":0,"SaleFromCount":0,"BuyMax":0,"limitCount":0,"ItemCount":100,"Id":6,"Stock":12,"MaxHtml":"限购0件","Unit":"提   ","ItemName":"维达健康家庭妇幼装长卷10卷装100克V4345","Photo":"/assets/images/morenerweima.png"}
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
         * OriginalPrice : 147.36
         * KillPrice : 144
         * Spec : 12
         * SaleIncreaseCount : 0
         * SaleFromCount : 0
         * BuyMax : 0
         * limitCount : 0
         * ItemCount : 100
         * Id : 6
         * Stock : 12
         * MaxHtml : 限购0件
         * Unit : 提   
         * ItemName : 维达健康家庭妇幼装长卷10卷装100克V4345
         * Photo : /assets/images/morenerweima.png
         */

        private double OriginalPrice;
        private double KillPrice;
        private int Spec;
        private int SaleIncreaseCount;
        private int SaleFromCount;
        private int BuyMax;
        private int limitCount;
        private int ItemCount;
        private int Id;
        private int Stock;
        private String MaxHtml;
        private String Unit;
        private String ItemName;
        private String Photo;
        private String Mfd;

        public String getMfd() {
            return Mfd;
        }

        public void setMfd(String mfd) {
            Mfd = mfd;
        }

        public double getOriginalPrice() {
            return OriginalPrice;
        }

        public void setOriginalPrice(double OriginalPrice) {
            this.OriginalPrice = OriginalPrice;
        }

        public double getKillPrice() {
            return KillPrice;
        }

        public void setKillPrice(double KillPrice) {
            this.KillPrice = KillPrice;
        }

        public int getSpec() {
            return Spec;
        }

        public void setSpec(int Spec) {
            this.Spec = Spec;
        }

        public int getSaleIncreaseCount() {
            return SaleIncreaseCount;
        }

        public void setSaleIncreaseCount(int SaleIncreaseCount) {
            this.SaleIncreaseCount = SaleIncreaseCount;
        }

        public int getSaleFromCount() {
            return SaleFromCount;
        }

        public void setSaleFromCount(int SaleFromCount) {
            this.SaleFromCount = SaleFromCount;
        }

        public int getBuyMax() {
            return BuyMax;
        }

        public void setBuyMax(int BuyMax) {
            this.BuyMax = BuyMax;
        }

        public int getLimitCount() {
            return limitCount;
        }

        public void setLimitCount(int limitCount) {
            this.limitCount = limitCount;
        }

        public int getItemCount() {
            return ItemCount;
        }

        public void setItemCount(int ItemCount) {
            this.ItemCount = ItemCount;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
        }

        public String getMaxHtml() {
            return MaxHtml;
        }

        public void setMaxHtml(String MaxHtml) {
            this.MaxHtml = MaxHtml;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }
    }
}
