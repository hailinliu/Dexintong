package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/27time14:47
 * @detail：
 */

public class SearchResultBean {
    /**
     * Code : 1000
     * Msg : 成功
     * Data : {"list":[{"Cid":26,"Bid":3,"Brand":"康师傅","ItemId":3,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170627173249489.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"康师傅方便面","Stock":1,"Unit":"包 ","AvgSales":0,"SaleFromCount":1,"SaleIncreaseCount":3,"Price":55.00000000000001,"Spec":50,"PathId":"|4|24|26|","Status":0}],"total":1,"pages":1,"brands":[{"Bid":3,"Brand":"康师傅"}]}
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
         * list : [{"Cid":26,"Bid":3,"Brand":"康师傅","ItemId":3,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170627173249489.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"康师傅方便面","Stock":1,"Unit":"包 ","AvgSales":0,"SaleFromCount":1,"SaleIncreaseCount":3,"Price":55.00000000000001,"Spec":50,"PathId":"|4|24|26|","Status":0}]
         * total : 1
         * pages : 1
         * brands : [{"Bid":3,"Brand":"康师傅"}]
         */

        private int total;
        private int pages;
        private List<ListBean> list;
        private List<BrandsBean> brands;

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

        public List<BrandsBean> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBean> brands) {
            this.brands = brands;
        }

        public static class ListBean {
            /**
             * Cid : 26
             * Bid : 3
             * Brand : 康师傅
             * ItemId : 3
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170627173249489.png?x-oss-process=image/resize,m_pad,w_228,h_230
             * Name : 康师傅方便面
             * Stock : 1
             * Unit : 包 
             * AvgSales : 0
             * SaleFromCount : 1
             * SaleIncreaseCount : 3
             * Price : 55.00000000000001
             * Spec : 50
             * PathId : |4|24|26|
             * Status : 0
             */

            private int Cid;
            private int Bid;
            private String Brand;
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
            private String PathId;
            private int Status;
            private String Mfd;

            public String getMfd() {
                return Mfd;
            }

            public void setMfd(String mfd) {
                Mfd = mfd;
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

            public String getBrand() {
                return Brand;
            }

            public void setBrand(String Brand) {
                this.Brand = Brand;
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

            public String getPathId() {
                return PathId;
            }

            public void setPathId(String PathId) {
                this.PathId = PathId;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }
        }

        public static class BrandsBean {
            /**
             * Bid : 3
             * Brand : 康师傅
             */

            private int Bid;
            private String Brand;

            public int getBid() {
                return Bid;
            }

            public void setBid(int Bid) {
                this.Bid = Bid;
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
