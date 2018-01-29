package com.runtai.newdexintong.module.homepage.bean;

/**
 * @author：rhf
 * @date：2017/10/21time08:21
 * @detail：
 */

public class SpecialGoodsDetailBean {
    /**
     * Code : 200
     * Data : {"Stock":14,"Spec":12,"AlreadyItem":0,"SaleFromCount":1,"ItemId":1798,"Price":75.6,"Mfd":"1999年12月31日","Unit":"瓶 ","Packing":"","Name":"欢乐家桔子罐头460g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1437543754.jpg?x-oss-process=image/resize,m_pad,w_100,h_100"}
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
         * Stock : 14
         * Spec : 12
         * AlreadyItem : 0
         * SaleFromCount : 1
         * ItemId : 1798
         * Price : 75.6
         * Mfd : 1999年12月31日
         * Unit : 瓶 
         * Packing : 
         * Name : 欢乐家桔子罐头460g
         * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1437543754.jpg?x-oss-process=image/resize,m_pad,w_100,h_100
         */

        private int Stock;
        private int Spec;
        private int AlreadyItem;
        private int SaleFromCount;
        private int ItemId;
        private double Price;
        private String Mfd;
        private String Unit;
        private String Packing;
        private String Name;
        private String Photo;

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
        }

        public int getSpec() {
            return Spec;
        }

        public void setSpec(int Spec) {
            this.Spec = Spec;
        }

        public int getAlreadyItem() {
            return AlreadyItem;
        }

        public void setAlreadyItem(int AlreadyItem) {
            this.AlreadyItem = AlreadyItem;
        }

        public int getSaleFromCount() {
            return SaleFromCount;
        }

        public void setSaleFromCount(int SaleFromCount) {
            this.SaleFromCount = SaleFromCount;
        }

        public int getItemId() {
            return ItemId;
        }

        public void setItemId(int ItemId) {
            this.ItemId = ItemId;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public String getMfd() {
            return Mfd;
        }

        public void setMfd(String Mfd) {
            this.Mfd = Mfd;
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

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }
    }
}
