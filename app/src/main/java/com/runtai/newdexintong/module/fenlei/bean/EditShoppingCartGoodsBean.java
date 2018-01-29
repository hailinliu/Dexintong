package com.runtai.newdexintong.module.fenlei.bean;

/**
 * @author：rhf
 * @date：2017/9/22time16:45
 * @detail：
 */

public class EditShoppingCartGoodsBean {

    /**
     * Code : 200
     * Data : {"BenefitMoney":0,"Kind":5,"Num":91,"PaidMoney":1727.42,"Qty":9,"aHref":"#","benefitPrice":430.92,"littleSum":430.92,"tipMsg":"优质商品请放心购买"}
     * Msg : 您选购的产品已成功放入购物车!建议每天20:00前,对购物中产品进行结算下单!
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
         * BenefitMoney : 0
         * Kind : 5
         * Num : 91
         * PaidMoney : 1727.42
         * Qty : 9
         * aHref : #
         * benefitPrice : 430.92
         * littleSum : 430.92
         * tipMsg : 优质商品请放心购买
         */

        private double BenefitMoney;
        private int Kind;
        private int Num;
        private double PaidMoney;
        private int Qty;
        private String aHref;
        private double benefitPrice;
        private double littleSum;
        private String tipMsg;

        public double getBenefitMoney() {
            return BenefitMoney;
        }

        public void setBenefitMoney(double BenefitMoney) {
            this.BenefitMoney = BenefitMoney;
        }

        public int getKind() {
            return Kind;
        }

        public void setKind(int Kind) {
            this.Kind = Kind;
        }

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }

        public double getPaidMoney() {
            return PaidMoney;
        }

        public void setPaidMoney(double PaidMoney) {
            this.PaidMoney = PaidMoney;
        }

        public int getQty() {
            return Qty;
        }

        public void setQty(int Qty) {
            this.Qty = Qty;
        }

        public String getAHref() {
            return aHref;
        }

        public void setAHref(String aHref) {
            this.aHref = aHref;
        }

        public double getBenefitPrice() {
            return benefitPrice;
        }

        public void setBenefitPrice(double benefitPrice) {
            this.benefitPrice = benefitPrice;
        }

        public double getLittleSum() {
            return littleSum;
        }

        public void setLittleSum(double littleSum) {
            this.littleSum = littleSum;
        }

        public String getTipMsg() {
            return tipMsg;
        }

        public void setTipMsg(String tipMsg) {
            this.tipMsg = tipMsg;
        }
    }
}
