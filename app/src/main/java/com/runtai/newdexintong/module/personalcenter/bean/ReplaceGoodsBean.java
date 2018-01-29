package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/17time16:10
 * @detail：
 */

public class ReplaceGoodsBean {


    /**
     * Code : 200
     * Data : {"total":5,"pages":1,"list":[{"IsChange":true,"Rstatus":0,"BuyNum":1,"Rspec":1,"MerchantId":4305,"RsItemId":8610,"Status":0,"ReturnNum":1,"Spec":1,"ItemId":8610,"Difference":0,"Price":33,"ReturnMoney":33,"BenefitPrice":33,"RstatusDes":"进行中","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/10/19 14:27:08","RsUnit":"提 ","RsItemName":"蒙牛真果粒250ml椰果味（单提12盒）","StatusDes":"进行中","CreateTime":"2017/10/19 14:27:08","Unit":"提 ","ItemName":"蒙牛真果粒250ml椰果味（单提12盒）"},{"IsChange":true,"Rstatus":0,"BuyNum":4,"Rspec":1,"MerchantId":4305,"RsItemId":8609,"Status":0,"ReturnNum":5,"Spec":20,"ItemId":2865,"Difference":126.9,"Price":33.5,"ReturnMoney":7.1,"BenefitPrice":1.42,"RstatusDes":"进行中","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/9/28 17:11:06","RsUnit":"提 ","RsItemName":"蒙牛真果粒250ml黄桃味（单提12盒）","StatusDes":"进行中","CreateTime":"2017/9/28 17:11:06","Unit":"瓶 ","ItemName":"蒙牛酸酸乳原味250ml"},{"IsChange":true,"Rstatus":2,"BuyNum":3,"Rspec":1,"MerchantId":4305,"RsItemId":8610,"Status":2,"ReturnNum":8,"Spec":20,"ItemId":2865,"Difference":87.64,"Price":33,"ReturnMoney":11.36,"BenefitPrice":1.42,"RstatusDes":"已转单","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/9/28 15:32:58","RsUnit":"提 ","RsItemName":"蒙牛真果粒250ml椰果味（单提12盒）","StatusDes":"确认退库","CreateTime":"2017/9/28 15:32:58","Unit":"瓶 ","ItemName":"蒙牛酸酸乳原味250ml"},{"IsChange":true,"Rstatus":0,"BuyNum":24,"Rspec":12,"MerchantId":4305,"RsItemId":1931,"Status":0,"ReturnNum":2,"Spec":1,"ItemId":8609,"Difference":35,"Price":4.25,"ReturnMoney":67,"BenefitPrice":33.5,"RstatusDes":"进行中","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/153603363.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/9/28 15:17:24","RsUnit":"提 ","RsItemName":"蒙牛特仑苏纯牛奶单提","StatusDes":"进行中","CreateTime":"2017/9/28 15:17:24","Unit":"提 ","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"},{"IsChange":true,"Rstatus":2,"BuyNum":20,"Rspec":20,"MerchantId":4305,"RsItemId":295,"Status":2,"ReturnNum":2,"Spec":1,"ItemId":2997,"Difference":43.2,"Price":2.26,"ReturnMoney":2,"BenefitPrice":1,"RstatusDes":"已转单","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/083609369.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1533373337.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/9/28 14:24:46","RsUnit":"瓶 ","RsItemName":"蒙牛纯牛奶250ml","StatusDes":"确认退库","CreateTime":"2017/9/28 14:24:46","Unit":"瓶 ","ItemName":"蒙牛纯甄酸牛奶200g单提"}]}
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
         * total : 5
         * pages : 1
         * list : [{"IsChange":true,"Rstatus":0,"BuyNum":1,"Rspec":1,"MerchantId":4305,"RsItemId":8610,"Status":0,"ReturnNum":1,"Spec":1,"ItemId":8610,"Difference":0,"Price":33,"ReturnMoney":33,"BenefitPrice":33,"RstatusDes":"进行中","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/10/19 14:27:08","RsUnit":"提 ","RsItemName":"蒙牛真果粒250ml椰果味（单提12盒）","StatusDes":"进行中","CreateTime":"2017/10/19 14:27:08","Unit":"提 ","ItemName":"蒙牛真果粒250ml椰果味（单提12盒）"},{"IsChange":true,"Rstatus":0,"BuyNum":4,"Rspec":1,"MerchantId":4305,"RsItemId":8609,"Status":0,"ReturnNum":5,"Spec":20,"ItemId":2865,"Difference":126.9,"Price":33.5,"ReturnMoney":7.1,"BenefitPrice":1.42,"RstatusDes":"进行中","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/9/28 17:11:06","RsUnit":"提 ","RsItemName":"蒙牛真果粒250ml黄桃味（单提12盒）","StatusDes":"进行中","CreateTime":"2017/9/28 17:11:06","Unit":"瓶 ","ItemName":"蒙牛酸酸乳原味250ml"},{"IsChange":true,"Rstatus":2,"BuyNum":3,"Rspec":1,"MerchantId":4305,"RsItemId":8610,"Status":2,"ReturnNum":8,"Spec":20,"ItemId":2865,"Difference":87.64,"Price":33,"ReturnMoney":11.36,"BenefitPrice":1.42,"RstatusDes":"已转单","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/9/28 15:32:58","RsUnit":"提 ","RsItemName":"蒙牛真果粒250ml椰果味（单提12盒）","StatusDes":"确认退库","CreateTime":"2017/9/28 15:32:58","Unit":"瓶 ","ItemName":"蒙牛酸酸乳原味250ml"},{"IsChange":true,"Rstatus":0,"BuyNum":24,"Rspec":12,"MerchantId":4305,"RsItemId":1931,"Status":0,"ReturnNum":2,"Spec":1,"ItemId":8609,"Difference":35,"Price":4.25,"ReturnMoney":67,"BenefitPrice":33.5,"RstatusDes":"进行中","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/153603363.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/9/28 15:17:24","RsUnit":"提 ","RsItemName":"蒙牛特仑苏纯牛奶单提","StatusDes":"进行中","CreateTime":"2017/9/28 15:17:24","Unit":"提 ","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"},{"IsChange":true,"Rstatus":2,"BuyNum":20,"Rspec":20,"MerchantId":4305,"RsItemId":295,"Status":2,"ReturnNum":2,"Spec":1,"ItemId":2997,"Difference":43.2,"Price":2.26,"ReturnMoney":2,"BenefitPrice":1,"RstatusDes":"已转单","RsPhoto":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/083609369.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1533373337.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","RsCreateTime":"2017/9/28 14:24:46","RsUnit":"瓶 ","RsItemName":"蒙牛纯牛奶250ml","StatusDes":"确认退库","CreateTime":"2017/9/28 14:24:46","Unit":"瓶 ","ItemName":"蒙牛纯甄酸牛奶200g单提"}]
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
             * IsChange : true
             * Rstatus : 0
             * BuyNum : 1
             * Rspec : 1
             * MerchantId : 4305
             * RsItemId : 8610
             * Status : 0
             * ReturnNum : 1
             * Spec : 1
             * ItemId : 8610
             * Difference : 0
             * Price : 33
             * ReturnMoney : 33
             * BenefitPrice : 33
             * RstatusDes : 进行中
             * RsPhoto : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230
             * RsCreateTime : 2017/10/19 14:27:08
             * RsUnit : 提 
             * RsItemName : 蒙牛真果粒250ml椰果味（单提12盒）
             * StatusDes : 进行中
             * CreateTime : 2017/10/19 14:27:08
             * Unit : 提 
             * ItemName : 蒙牛真果粒250ml椰果味（单提12盒）
             */

            private boolean IsChange;
            private int Rstatus;
            private int BuyNum;
            private int Rspec;
            private int MerchantId;
            private int RsItemId;
            private int Status;
            private int ReturnNum;
            private int Spec;
            private int ItemId;
            private double Difference;
            private double Price;
            private double ReturnMoney;
            private double BenefitPrice;
            private String RstatusDes;
            private String RsPhoto;
            private String Photo;
            private String RsCreateTime;
            private String RsUnit;
            private String RsItemName;
            private String StatusDes;
            private String CreateTime;
            private String Unit;
            private String ItemName;

            public boolean isIsChange() {
                return IsChange;
            }

            public void setIsChange(boolean IsChange) {
                this.IsChange = IsChange;
            }

            public int getRstatus() {
                return Rstatus;
            }

            public void setRstatus(int Rstatus) {
                this.Rstatus = Rstatus;
            }

            public int getBuyNum() {
                return BuyNum;
            }

            public void setBuyNum(int BuyNum) {
                this.BuyNum = BuyNum;
            }

            public int getRspec() {
                return Rspec;
            }

            public void setRspec(int Rspec) {
                this.Rspec = Rspec;
            }

            public int getMerchantId() {
                return MerchantId;
            }

            public void setMerchantId(int MerchantId) {
                this.MerchantId = MerchantId;
            }

            public int getRsItemId() {
                return RsItemId;
            }

            public void setRsItemId(int RsItemId) {
                this.RsItemId = RsItemId;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public int getReturnNum() {
                return ReturnNum;
            }

            public void setReturnNum(int ReturnNum) {
                this.ReturnNum = ReturnNum;
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

            public double getDifference() {
                return Difference;
            }

            public void setDifference(double Difference) {
                this.Difference = Difference;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public double getReturnMoney() {
                return ReturnMoney;
            }

            public void setReturnMoney(double ReturnMoney) {
                this.ReturnMoney = ReturnMoney;
            }

            public double getBenefitPrice() {
                return BenefitPrice;
            }

            public void setBenefitPrice(double BenefitPrice) {
                this.BenefitPrice = BenefitPrice;
            }

            public String getRstatusDes() {
                return RstatusDes;
            }

            public void setRstatusDes(String RstatusDes) {
                this.RstatusDes = RstatusDes;
            }

            public String getRsPhoto() {
                return RsPhoto;
            }

            public void setRsPhoto(String RsPhoto) {
                this.RsPhoto = RsPhoto;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
            }

            public String getRsCreateTime() {
                return RsCreateTime;
            }

            public void setRsCreateTime(String RsCreateTime) {
                this.RsCreateTime = RsCreateTime;
            }

            public String getRsUnit() {
                return RsUnit;
            }

            public void setRsUnit(String RsUnit) {
                this.RsUnit = RsUnit;
            }

            public String getRsItemName() {
                return RsItemName;
            }

            public void setRsItemName(String RsItemName) {
                this.RsItemName = RsItemName;
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
}
