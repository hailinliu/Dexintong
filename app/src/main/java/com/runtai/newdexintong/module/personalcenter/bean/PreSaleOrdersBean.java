package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/8/3time19:00
 * @detail：
 */

public class PreSaleOrdersBean {


    /**
     * Code : 200
     * Data : {"total":1,"pages":1,"list":[{"CreateTime":"/Date(1507860661177)/","TransformedNum":0,"Status":0,"BuyNum":1,"Id":1002,"Total":28.4,"Price":28.4,"StatusDesc":"下单成功","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_300,h_300","SpecDesc":"20瓶 /件","ItemName":"蒙牛酸酸乳原味250ml"}]}
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
         * total : 1
         * pages : 1
         * list : [{"CreateTime":"/Date(1507860661177)/","TransformedNum":0,"Status":0,"BuyNum":1,"Id":1002,"Total":28.4,"Price":28.4,"StatusDesc":"下单成功","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_300,h_300","SpecDesc":"20瓶 /件","ItemName":"蒙牛酸酸乳原味250ml"}]
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
             * CreateTime : /Date(1507860661177)/
             * TransformedNum : 0
             * Status : 0
             * BuyNum : 1
             * Id : 1002
             * Total : 28.4
             * Price : 28.4
             * StatusDesc : 下单成功
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_300,h_300
             * SpecDesc : 20瓶 /件
             * ItemName : 蒙牛酸酸乳原味250ml
             */

            private String CreateTime;
            private int TransformedNum;
            private int Status;
            private int BuyNum;
            private int Id;
            private double Total;
            private double Price;
            private String StatusDesc;
            private String Photo;
            private String SpecDesc;
            private String ItemName;

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public int getTransformedNum() {
                return TransformedNum;
            }

            public void setTransformedNum(int TransformedNum) {
                this.TransformedNum = TransformedNum;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public int getBuyNum() {
                return BuyNum;
            }

            public void setBuyNum(int BuyNum) {
                this.BuyNum = BuyNum;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public double getTotal() {
                return Total;
            }

            public void setTotal(double Total) {
                this.Total = Total;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public String getStatusDesc() {
                return StatusDesc;
            }

            public void setStatusDesc(String StatusDesc) {
                this.StatusDesc = StatusDesc;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
            }

            public String getSpecDesc() {
                return SpecDesc;
            }

            public void setSpecDesc(String SpecDesc) {
                this.SpecDesc = SpecDesc;
            }

            public String getItemName() {
                return ItemName;
            }

            public void setItemName(String ItemName) {
                this.ItemName = ItemName;
            }
        }
    }
}
