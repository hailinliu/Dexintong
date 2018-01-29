package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @author：rhf
 * @date：2017/7/7time09:11
 * @detail：
 */

public class ApplyReplaceGoodsBean {

    /**
     * Code : 200
     * Data : {"Stock":10,"SaleIncreaseCount":1,"SaleFromCount":1,"Num":0,"Spec":1,
     * "ItemId":8610,"Id":1030,"Amount":0,"OriginalPrice":33,"Unit":"提 ","ItemName":"蒙牛真果粒250ml椰果味（单提12盒）"}
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
         * Stock : 10
         * SaleIncreaseCount : 1
         * SaleFromCount : 1
         * Num : 0
         * Spec : 1
         * ItemId : 8610
         * Id : 1030
         * Amount : 0
         * OriginalPrice : 33
         * Unit : 提 
         * ItemName : 蒙牛真果粒250ml椰果味（单提12盒）
         */

        private int Stock;
        private int SaleIncreaseCount;
        private int SaleFromCount;
        private int Num;
        private int Spec;
        private int ItemId;
        private int Id;
        private int Amount;
        private double OriginalPrice;
        private String Unit;
        private String ItemName;

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
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

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
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

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
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
