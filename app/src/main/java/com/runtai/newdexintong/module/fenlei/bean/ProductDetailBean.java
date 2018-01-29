package com.runtai.newdexintong.module.fenlei.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/9/25time09:39
 * @detail：
 */

public class ProductDetailBean {

    /**
     * Code : 200
     * Data : {"promotion":[],"item":{"Mfd":"/Date(1189872000000)/","CanReturn":true,"LimitCycle":0,"SecuredNum":0,"OriginalSpec":80,"Exp":3650,"AlreadyItem":0,"LimitBuyNum":0,"Stock":257,"Status":1,"Spec":20,"ItemId":6439,"Cid":0,"Bid":0,"AvgSales":0,"SaleIncreaseCount":1,"SaleFromCount":1,"Price":77,"Packing":"","Category":"护肤品","Detail":"","Pinyin":"BQLGYHW","Barcode3":"","Barcode2":"","Barcode":"6927006133133","Unit":"瓶 ","SearchKey":null,"SearchAttrVal":null,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1032533253.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","PathId":"|68|83|87|","Name":"百雀羚甘油1号75g（维C）","Brand":"百雀羚"}}
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
         * promotion : []
         * item : {"Mfd":"/Date(1189872000000)/","CanReturn":true,"LimitCycle":0,"SecuredNum":0,"OriginalSpec":80,"Exp":3650,"AlreadyItem":0,"LimitBuyNum":0,"Stock":257,"Status":1,"Spec":20,"ItemId":6439,"Cid":0,"Bid":0,"AvgSales":0,"SaleIncreaseCount":1,"SaleFromCount":1,"Price":77,"Packing":"","Category":"护肤品","Detail":"","Pinyin":"BQLGYHW","Barcode3":"","Barcode2":"","Barcode":"6927006133133","Unit":"瓶 ","SearchKey":null,"SearchAttrVal":null,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1032533253.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","PathId":"|68|83|87|","Name":"百雀羚甘油1号75g（维C）","Brand":"百雀羚"}
         */

        private ItemBean item;
        private List<?> promotion;

        public ItemBean getItem() {
            return item;
        }

        public void setItem(ItemBean item) {
            this.item = item;
        }

        public List<?> getPromotion() {
            return promotion;
        }

        public void setPromotion(List<?> promotion) {
            this.promotion = promotion;
        }

        public static class ItemBean {
            /**
             * Mfd : /Date(1189872000000)/
             * CanReturn : true
             * LimitCycle : 0
             * SecuredNum : 0
             * OriginalSpec : 80
             * Exp : 3650
             * AlreadyItem : 0
             * LimitBuyNum : 0
             * Stock : 257
             * Status : 1
             * Spec : 20
             * ItemId : 6439
             * Cid : 0
             * Bid : 0
             * AvgSales : 0
             * SaleIncreaseCount : 1
             * SaleFromCount : 1
             * Price : 77
             * Packing : 
             * Category : 护肤品
             * Detail : 
             * Pinyin : BQLGYHW
             * Barcode3 : 
             * Barcode2 : 
             * Barcode : 6927006133133
             * Unit : 瓶 
             * SearchKey : null
             * SearchAttrVal : null
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1032533253.jpg?x-oss-process=image/resize,m_pad,w_228,h_230
             * PathId : |68|83|87|
             * Name : 百雀羚甘油1号75g（维C）
             * Brand : 百雀羚
             */

            private String Mfd;
            private boolean CanReturn;
            private int LimitCycle;
            private int SecuredNum;
            private int OriginalSpec;
            private int Exp;
            private int AlreadyItem;
            private int LimitBuyNum;
            private int Stock;
            private int Status;
            private int Spec;
            private int ItemId;
            private int Cid;
            private int Bid;
            private int AvgSales;
            private int SaleIncreaseCount;
            private int SaleFromCount;
            private double Price;
            private String Packing;
            private String Category;
            private String Detail;
            private String Pinyin;
            private String Barcode3;
            private String Barcode2;
            private String Barcode;
            private String Unit;
            private Object SearchKey;
            private Object SearchAttrVal;
            private String Photo;
            private String PathId;
            private String Name;
            private String Brand;
            private boolean IsDirect;

            public boolean isDirect() {
                return IsDirect;
            }

            public void setDirect(boolean direct) {
                IsDirect = direct;
            }

            public String getMfd() {
                return Mfd;
            }

            public void setMfd(String Mfd) {
                this.Mfd = Mfd;
            }

            public boolean isCanReturn() {
                return CanReturn;
            }

            public void setCanReturn(boolean CanReturn) {
                this.CanReturn = CanReturn;
            }

            public int getLimitCycle() {
                return LimitCycle;
            }

            public void setLimitCycle(int LimitCycle) {
                this.LimitCycle = LimitCycle;
            }

            public int getSecuredNum() {
                return SecuredNum;
            }

            public void setSecuredNum(int SecuredNum) {
                this.SecuredNum = SecuredNum;
            }

            public int getOriginalSpec() {
                return OriginalSpec;
            }

            public void setOriginalSpec(int OriginalSpec) {
                this.OriginalSpec = OriginalSpec;
            }

            public int getExp() {
                return Exp;
            }

            public void setExp(int Exp) {
                this.Exp = Exp;
            }

            public int getAlreadyItem() {
                return AlreadyItem;
            }

            public void setAlreadyItem(int AlreadyItem) {
                this.AlreadyItem = AlreadyItem;
            }

            public int getLimitBuyNum() {
                return LimitBuyNum;
            }

            public void setLimitBuyNum(int LimitBuyNum) {
                this.LimitBuyNum = LimitBuyNum;
            }

            public int getStock() {
                return Stock;
            }

            public void setStock(int Stock) {
                this.Stock = Stock;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
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

            public int getCid() {
                return Cid;
            }

            public void setCid(int Cid) {
                this.Cid = Cid;
            }

            public int getBid() {
                return Bid;
            }

            public void setBid(int Bid) {
                this.Bid = Bid;
            }

            public int getAvgSales() {
                return AvgSales;
            }

            public void setAvgSales(int AvgSales) {
                this.AvgSales = AvgSales;
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

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public String getPacking() {
                return Packing;
            }

            public void setPacking(String Packing) {
                this.Packing = Packing;
            }

            public String getCategory() {
                return Category;
            }

            public void setCategory(String Category) {
                this.Category = Category;
            }

            public String getDetail() {
                return Detail;
            }

            public void setDetail(String Detail) {
                this.Detail = Detail;
            }

            public String getPinyin() {
                return Pinyin;
            }

            public void setPinyin(String Pinyin) {
                this.Pinyin = Pinyin;
            }

            public String getBarcode3() {
                return Barcode3;
            }

            public void setBarcode3(String Barcode3) {
                this.Barcode3 = Barcode3;
            }

            public String getBarcode2() {
                return Barcode2;
            }

            public void setBarcode2(String Barcode2) {
                this.Barcode2 = Barcode2;
            }

            public String getBarcode() {
                return Barcode;
            }

            public void setBarcode(String Barcode) {
                this.Barcode = Barcode;
            }

            public String getUnit() {
                return Unit;
            }

            public void setUnit(String Unit) {
                this.Unit = Unit;
            }

            public Object getSearchKey() {
                return SearchKey;
            }

            public void setSearchKey(Object SearchKey) {
                this.SearchKey = SearchKey;
            }

            public Object getSearchAttrVal() {
                return SearchAttrVal;
            }

            public void setSearchAttrVal(Object SearchAttrVal) {
                this.SearchAttrVal = SearchAttrVal;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
            }

            public String getPathId() {
                return PathId;
            }

            public void setPathId(String PathId) {
                this.PathId = PathId;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getBrand() {
                return Brand;
            }

            public void setBrand(String Brand) {
                this.Brand = Brand;
            }
        }
    }
}
