package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @author：rhf
 * @date：2017/10/17time17:17
 * @detail：
 */

public class DhhGoodsBean {
    /**
     * Code : 200
     * Data : {"Spec":20,"TransformedNum":0,"BuyNum":20,"Stock":20,
     * "SaleIncreaseCount":1,"SaleFromCount":1,"Id":1002,"ItemId":2865,"ItemName":"蒙牛酸酸乳原味250ml",
     * "Unit":"瓶 ","Packing":"","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,
     * w_228,h_230"}
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
         * Spec : 20
         * TransformedNum : 0
         * BuyNum : 20
         * Stock : 20
         * SaleIncreaseCount : 1
         * SaleFromCount : 1
         * Id : 1002
         * ItemId : 2865
         * ItemName : 蒙牛酸酸乳原味250ml
         * Unit : 瓶 
         * Packing : 
         * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230
         */

        private int Spec;
        private int TransformedNum;
        private int BuyNum;
        private int Stock;
        private int SaleIncreaseCount;
        private int SaleFromCount;
        private int Id;
        private int ItemId;
        private String ItemName;
        private String Unit;
        private String Packing;
        private String Photo;

        public int getSpec() {
            return Spec;
        }

        public void setSpec(int Spec) {
            this.Spec = Spec;
        }

        public int getTransformedNum() {
            return TransformedNum;
        }

        public void setTransformedNum(int TransformedNum) {
            this.TransformedNum = TransformedNum;
        }

        public int getBuyNum() {
            return BuyNum;
        }

        public void setBuyNum(int BuyNum) {
            this.BuyNum = BuyNum;
        }

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

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getItemId() {
            return ItemId;
        }

        public void setItemId(int ItemId) {
            this.ItemId = ItemId;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getPacking() {
            return Packing;
        }

        public void setPacking(String Packing) {
            this.Packing = Packing;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }
    }
}
