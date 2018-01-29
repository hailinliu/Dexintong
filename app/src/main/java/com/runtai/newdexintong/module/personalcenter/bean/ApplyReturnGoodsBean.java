package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @author：rhf
 * @date：2017/10/18time19:12
 * @detail：申请退货
 */

public class ApplyReturnGoodsBean {
    /**
     * Code : 200
     * Data : {"Num":0,"ConfirmNum":80,"Spec":20,"ItemId":6439,"Coupon":0,
     * "Activi":0,"Amount":0,"BenefitPrice":3.85,"OriginalPrice":3.85,"Unit":"瓶 ","ItemName":"百雀羚甘油1号75g（维"}
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
         * Num : 0
         * ConfirmNum : 80
         * Spec : 20
         * ItemId : 6439
         * Coupon : 0
         * Activi : 0
         * Amount : 0
         * BenefitPrice : 3.85
         * OriginalPrice : 3.85
         * Unit : 瓶
         * ItemName : 百雀羚甘油1号75g（维
         */

        private int Num;
        private int ConfirmNum;
        private int Spec;
        private int ItemId;
        private int Coupon;
        private int Activi;
        private int Amount;
        private double BenefitPrice;
        private double OriginalPrice;
        private String Unit;
        private String ItemName;

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }

        public int getConfirmNum() {
            return ConfirmNum;
        }

        public void setConfirmNum(int ConfirmNum) {
            this.ConfirmNum = ConfirmNum;
        }

        public int getSpec() {
            return Spec;
        }

        public void setSpec(int Spec) {
            this.Spec = Spec;
        }

        public int getItemId() {
            return ItemId;
        }

        public void setItemId(int ItemId) {
            this.ItemId = ItemId;
        }

        public int getCoupon() {
            return Coupon;
        }

        public void setCoupon(int Coupon) {
            this.Coupon = Coupon;
        }

        public int getActivi() {
            return Activi;
        }

        public void setActivi(int Activi) {
            this.Activi = Activi;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public double getBenefitPrice() {
            return BenefitPrice;
        }

        public void setBenefitPrice(double BenefitPrice) {
            this.BenefitPrice = BenefitPrice;
        }

        public double getOriginalPrice() {
            return OriginalPrice;
        }

        public void setOriginalPrice(double OriginalPrice) {
            this.OriginalPrice = OriginalPrice;
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
    }
}
