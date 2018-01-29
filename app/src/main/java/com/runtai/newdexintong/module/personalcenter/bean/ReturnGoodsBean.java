package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/5time18:33
 * @detail：
 */

public class ReturnGoodsBean {


    /**
     * Code : 200
     * Data : {"total":1,"pages":1,"list":[{"IsChange":false,"Status":2,"ReturnNum":2,"Spec":1,"MerchantId":4305,"ItemId":8609,"ReturnMoney":2,"BenefitPrice":1,"StatusDes":"确认退库","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","CreateTime":"2017/9/28 14:11:12","Unit":"提 ","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"}]}
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
         * list : [{"IsChange":false,"Status":2,"ReturnNum":2,"Spec":1,"MerchantId":4305,"ItemId":8609,"ReturnMoney":2,"BenefitPrice":1,"StatusDes":"确认退库","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","CreateTime":"2017/9/28 14:11:12","Unit":"提 ","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"}]
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
             * IsChange : false
             * Status : 2
             * ReturnNum : 2
             * Spec : 1
             * MerchantId : 4305
             * ItemId : 8609
             * ReturnMoney : 2
             * BenefitPrice : 1
             * StatusDes : 确认退库
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0834313431.jpg?x-oss-process=image/resize,m_pad,w_228,h_230
             * CreateTime : 2017/9/28 14:11:12
             * Unit : 提 
             * ItemName : 蒙牛真果粒250ml黄桃味（单提12盒）
             */

            private boolean IsChange;
            private int Status;
            private int ReturnNum;
            private int Spec;
            private int MerchantId;
            private int ItemId;
            private double ReturnMoney;
            private double BenefitPrice;
            private String StatusDes;
            private String Photo;
            private String CreateTime;
            private String Unit;
            private String ItemName;

            public boolean isIsChange() {
                return IsChange;
            }

            public void setIsChange(boolean IsChange) {
                this.IsChange = IsChange;
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

            public int getMerchantId() {
                return MerchantId;
            }

            public void setMerchantId(int MerchantId) {
                this.MerchantId = MerchantId;
            }

            public int getItemId() {
                return ItemId;
            }

            public void setItemId(int ItemId) {
                this.ItemId = ItemId;
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

            public String getStatusDes() {
                return StatusDes;
            }

            public void setStatusDes(String StatusDes) {
                this.StatusDes = StatusDes;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
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
