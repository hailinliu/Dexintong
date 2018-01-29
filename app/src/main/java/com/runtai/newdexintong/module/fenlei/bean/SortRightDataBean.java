package com.runtai.newdexintong.module.fenlei.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/27time20:14
 * @detail：
 */

public class SortRightDataBean {
    /**
     * Code : 1000
     * Msg : 成功
     * Data : {"list":[{"Cid":31,"Bid":8,"Brand":"黑木森","ItemId":12,
     * "Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094632068.png?x-oss-process=image/resize,m_pad,w_228,h_230",
     * "Name":"德国奶粉","Stock":0,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":30,"Spec":30,"PathId":"|25|31|","Status":1},{"Cid":44,"Bid":15,"Brand":"贝因美","ItemId":25,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/52143873.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"奶粉","Stock":5,"Unit":"桶 ","AvgSales":0,"SaleFromCount":1,"SaleIncreaseCount":5,"Price":1794,"Spec":6,"PathId":"|25|31|44|","Status":1},{"Cid":31,"Bid":7,"Brand":"美赞臣","ItemId":10,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094335685.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"美赞臣奶粉2","Stock":0,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":50,"Spec":50,"PathId":"|25|31|","Status":1},{"Cid":31,"Bid":7,"Brand":"美赞臣","ItemId":9,"Photo":"/assets/images/morenerweima.png","Name":"美赞臣奶粉1","Stock":0,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":50,"Spec":50,"PathId":"|25|31|","Status":2},{"Cid":31,"Bid":8,"Brand":"黑木森","ItemId":14,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170713103232860.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"德国奶粉2","Stock":4,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":50,"Spec":50,"PathId":"25|31","Status":1},{"Cid":31,"Bid":15,"Brand":"贝因美","ItemId":11,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094512169.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"贝因美奶1","Stock":0,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":60,"Spec":60,"PathId":"|25|31|","Status":1}],"total":6,"pages":1}
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
         * list : [{"Cid":31,"Bid":8,"Brand":"黑木森","ItemId":12,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094632068.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"德国奶粉","Stock":0,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":30,"Spec":30,"PathId":"|25|31|","Status":1},{"Cid":44,"Bid":15,"Brand":"贝因美","ItemId":25,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/52143873.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"奶粉","Stock":5,"Unit":"桶 ","AvgSales":0,"SaleFromCount":1,"SaleIncreaseCount":5,"Price":1794,"Spec":6,"PathId":"|25|31|44|","Status":1},{"Cid":31,"Bid":7,"Brand":"美赞臣","ItemId":10,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094335685.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"美赞臣奶粉2","Stock":0,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":50,"Spec":50,"PathId":"|25|31|","Status":1},{"Cid":31,"Bid":7,"Brand":"美赞臣","ItemId":9,"Photo":"/assets/images/morenerweima.png","Name":"美赞臣奶粉1","Stock":0,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":50,"Spec":50,"PathId":"|25|31|","Status":2},{"Cid":31,"Bid":8,"Brand":"黑木森","ItemId":14,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170713103232860.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"德国奶粉2","Stock":4,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":50,"Spec":50,"PathId":"25|31","Status":1},{"Cid":31,"Bid":15,"Brand":"贝因美","ItemId":11,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094512169.png?x-oss-process=image/resize,m_pad,w_228,h_230","Name":"贝因美奶1","Stock":0,"Unit":"包 ","AvgSales":0,"SaleFromCount":2,"SaleIncreaseCount":3,"Price":60,"Spec":60,"PathId":"|25|31|","Status":1}]
         * total : 6
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
             * Cid : 31
             * Bid : 8
             * Brand : 黑木森
             * ItemId : 12
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/20170704094632068.png?x-oss-process=image/resize,m_pad,w_228,h_230
             * Name : 德国奶粉
             * Stock : 0
             * Unit : 包 
             * AvgSales : 0
             * SaleFromCount : 2
             * SaleIncreaseCount : 3
             * Price : 30
             * Spec : 30
             * PathId : |25|31|
             * Status : 1
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
    }
}
