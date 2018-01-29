package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/18time16:05
 * @detail：
 */

public class ImperfectGoodsBean {

    /**
     * Code : 200
     * Data : {"total":11,"pages":1,"list":[{"Status":0,"Qty":1,"ItemId":0,"Amount":33.5,"ReturnPrice":33.5,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/10/18 16:51:21","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"},{"Status":0,"Qty":2,"ItemId":0,"Amount":68,"ReturnPrice":34,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/180337337.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/10/14 16:32:47","ItemName":"蒙牛真果粒250ml蓝莓味（单提12盒）"},{"Status":1,"Qty":11,"ItemId":0,"Amount":15.62,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"销售确认","CreateTime":"2017/9/28 17:11:21","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":1,"Qty":8,"ItemId":0,"Amount":7.84,"ReturnPrice":0.98,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"销售确认","CreateTime":"2017/9/28 17:06:39","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":1,"Qty":1,"ItemId":0,"Amount":0.98,"ReturnPrice":0.98,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"销售确认","CreateTime":"2017/9/28 17:06:32","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":5,"ItemId":0,"Amount":4.9,"ReturnPrice":0.98,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:06:27","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":1,"ItemId":0,"Amount":1.42,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:04:30","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":2,"ItemId":0,"Amount":2.84,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:04:20","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":3,"ItemId":0,"Amount":4.26,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:04:08","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":4,"ItemId":0,"Amount":5.68,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:03:52","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":1,"Qty":3,"ItemId":0,"Amount":3,"ReturnPrice":1,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"销售确认","CreateTime":"2017/9/28 14:12:00","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"}]}
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
         * total : 11
         * pages : 1
         * list : [{"Status":0,"Qty":1,"ItemId":0,"Amount":33.5,"ReturnPrice":33.5,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/10/18 16:51:21","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"},{"Status":0,"Qty":2,"ItemId":0,"Amount":68,"ReturnPrice":34,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/180337337.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/10/14 16:32:47","ItemName":"蒙牛真果粒250ml蓝莓味（单提12盒）"},{"Status":1,"Qty":11,"ItemId":0,"Amount":15.62,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"销售确认","CreateTime":"2017/9/28 17:11:21","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":1,"Qty":8,"ItemId":0,"Amount":7.84,"ReturnPrice":0.98,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"销售确认","CreateTime":"2017/9/28 17:06:39","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":1,"Qty":1,"ItemId":0,"Amount":0.98,"ReturnPrice":0.98,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"销售确认","CreateTime":"2017/9/28 17:06:32","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":5,"ItemId":0,"Amount":4.9,"ReturnPrice":0.98,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:06:27","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":1,"ItemId":0,"Amount":1.42,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:04:30","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":2,"ItemId":0,"Amount":2.84,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:04:20","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":3,"ItemId":0,"Amount":4.26,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:04:08","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":0,"Qty":4,"ItemId":0,"Amount":5.68,"ReturnPrice":1.42,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"进行中","CreateTime":"2017/9/28 17:03:52","ItemName":"蒙牛酸酸乳原味250ml"},{"Status":1,"Qty":3,"ItemId":0,"Amount":3,"ReturnPrice":1,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","StatusDes":"销售确认","CreateTime":"2017/9/28 14:12:00","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"}]
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
             * Status : 0
             * Qty : 1
             * ItemId : 0
             * Amount : 33.5
             * ReturnPrice : 33.5
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230
             * StatusDes : 进行中
             * CreateTime : 2017/10/18 16:51:21
             * ItemName : 蒙牛真果粒250ml黄桃味（单提12盒）
             */

            private int Status;
            private int Qty;
            private int ItemId;
            private double Amount;
            private double ReturnPrice;
            private String Photo;
            private String StatusDes;
            private String CreateTime;
            private String ItemName;

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public int getQty() {
                return Qty;
            }

            public void setQty(int Qty) {
                this.Qty = Qty;
            }

            public int getItemId() {
                return ItemId;
            }

            public void setItemId(int ItemId) {
                this.ItemId = ItemId;
            }

            public double getAmount() {
                return Amount;
            }

            public void setAmount(double Amount) {
                this.Amount = Amount;
            }

            public double getReturnPrice() {
                return ReturnPrice;
            }

            public void setReturnPrice(double ReturnPrice) {
                this.ReturnPrice = ReturnPrice;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
            }

            public String getStatusDes() {
                return StatusDes;
            }

            public void setStatusDes(String StatusDes) {
                this.StatusDes = StatusDes;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
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
