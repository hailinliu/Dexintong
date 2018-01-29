package com.runtai.newdexintong.module.homepage.bean;

/**
 * @author：rhf
 * @date：2017/10/20time21:18
 * @detail：订货会商品详情
 */

public class DhhGoodsDetailBean {
    /**
     * Code : 200
     * Data : {"Spec":10,"SaleIncreaseCount":1,"SaleFromCount":1,"Stock":10,"Id":9165,"Price":22.9,"Unit":"袋 ","Name":"丹鹤小米锅巴牛肉味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1423172317.jpg?x-oss-process=image/resize,m_pad,h_228,w_224"}
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
         * Spec : 10
         * SaleIncreaseCount : 1
         * SaleFromCount : 1
         * Stock : 10
         * Id : 9165
         * Price : 22.9
         * Unit : 袋 
         * Name : 丹鹤小米锅巴牛肉味65g
         * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1423172317.jpg?x-oss-process=image/resize,m_pad,h_228,w_224
         */

        private int Spec;
        private int SaleIncreaseCount;
        private int SaleFromCount;
        private int Stock;
        private int Id;
        private double Price;
        private String Unit;
        private String Name;
        private String Photo;
        private String Mfd;

        public String getMfd() {
            return Mfd;
        }

        public void setMfd(String mfd) {
            Mfd = mfd;
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

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public double getPrice() {
            return Price;
        }

        public void setPrice(double Price) {
            this.Price = Price;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
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
