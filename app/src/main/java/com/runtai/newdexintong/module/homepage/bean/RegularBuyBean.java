package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class RegularBuyBean {
    /**
     * Code : 1000
     * Msg :
     * Data : {"list":[{"Id":0,"total":0,"pages":0,"Cid":31,"Bid":0,"ItemId":12,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094632068.png?x-oss-process=image/resize,m_pad,h_228,w_230","Name":"德国奶粉","Stock":3,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":0,"Price":1,"Spec":30,"PathId":null,"Status":0},{"Id":0,"total":0,"pages":0,"Cid":26,"Bid":0,"ItemId":3,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170627173249489.png?x-oss-process=image/resize,m_pad,h_228,w_230","Name":"康师傅方便面","Stock":51,"Unit":"包 ","AvgSales":0,"SaleFromCount":1,"SaleIncreaseCount":0,"Price":1.1,"Spec":50,"PathId":null,"Status":0}],"total":2,"pages":1}
     */

    private int Code;
    private String Msg;
    private DataBean Data;

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

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * list : [{"Id":0,"total":0,"pages":0,"Cid":31,"Bid":0,"ItemId":12,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094632068.png?x-oss-process=image/resize,m_pad,h_228,w_230","Name":"德国奶粉","Stock":3,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":0,"Price":1,"Spec":30,"PathId":null,"Status":0},{"Id":0,"total":0,"pages":0,"Cid":26,"Bid":0,"ItemId":3,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170627173249489.png?x-oss-process=image/resize,m_pad,h_228,w_230","Name":"康师傅方便面","Stock":51,"Unit":"包 ","AvgSales":0,"SaleFromCount":1,"SaleIncreaseCount":0,"Price":1.1,"Spec":50,"PathId":null,"Status":0}]
         * total : 2
         * pages : 1
         */

        private int total;
        private int pages;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPages() {
            return pages;
        }

        public void setPages(int pages) {
            this.pages = pages;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * Id : 0
             * total : 0
             * pages : 0
             * Cid : 31
             * Bid : 0
             * ItemId : 12
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094632068.png?x-oss-process=image/resize,m_pad,h_228,w_230
             * Name : 德国奶粉
             * Stock : 3
             * Unit : 包
             * AvgSales : 0
             * SaleFromCount : 2
             * SaleIncreaseCount : 0
             * Price : 1
             * Spec : 30
             * PathId : null
             * Status : 0
             */

            private int Id;
            private int total;
            private int pages;
            private int Cid;
            private int Bid;
            private int ItemId;
            private String Photo;
            private String Name;
            private int Stock;
            private String Unit;
            private int AvgSales;
            private int SaleFromCount;
            private int SaleIncreaseCount;
            private double Price;
            private int Spec;
            private Object PathId;
            private int Status;
            private String Mfd;

            public String getMfd() {
                return Mfd;
            }

            public void setMfd(String mfd) {
                Mfd = mfd;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getTotal() {
                return total;
            }

            public void setTotal(int total) {
                this.total = total;
            }

            public int getPages() {
                return pages;
            }

            public void setPages(int pages) {
                this.pages = pages;
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

            public int getItemId() {
                return ItemId;
            }

            public void setItemId(int ItemId) {
                this.ItemId = ItemId;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public int getStock() {
                return Stock;
            }

            public void setStock(int Stock) {
                this.Stock = Stock;
            }

            public String getUnit() {
                return Unit;
            }

            public void setUnit(String Unit) {
                this.Unit = Unit;
            }

            public int getAvgSales() {
                return AvgSales;
            }

            public void setAvgSales(int AvgSales) {
                this.AvgSales = AvgSales;
            }

            public int getSaleFromCount() {
                return SaleFromCount;
            }

            public void setSaleFromCount(int SaleFromCount) {
                this.SaleFromCount = SaleFromCount;
            }

            public int getSaleIncreaseCount() {
                return SaleIncreaseCount;
            }

            public void setSaleIncreaseCount(int SaleIncreaseCount) {
                this.SaleIncreaseCount = SaleIncreaseCount;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public int getSpec() {
                return Spec;
            }

            public void setSpec(int Spec) {
                this.Spec = Spec;
            }

            public Object getPathId() {
                return PathId;
            }

            public void setPathId(Object PathId) {
                this.PathId = PathId;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public boolean isCheck;

            public ListBean() {
                this.isCheck = false;
            }
        }
    }


}
