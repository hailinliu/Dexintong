package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/6time14:44
 * @detail：
 */

public class MyOrderBean {

    /**
     * Code : 200
     * Data : {"total":1,"pages":1,"list":[{"Order":{"IsPrePaid":true,"Status":1,"ActivityId":0,"SpecialId":0,"Id":1012,"Benefit":0,"OriginalMoney":33.5,"PaidMoney":33.5,"CouponBenefit":0,"BenefitMoney":0,"Photo":["/assets/images/morenerweima.png"],"CreateTime":"2017-10-14 15:43","Operate":"取消订单","OrderNum":"43051216171014154323250","ActivityName":""},"Detail":["/assets/images/morenerweima.png"]}]}
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
         * list : [{"Order":{"IsPrePaid":true,"Status":1,"ActivityId":0,"SpecialId":0,"Id":1012,"Benefit":0,"OriginalMoney":33.5,"PaidMoney":33.5,"CouponBenefit":0,"BenefitMoney":0,"Photo":["/assets/images/morenerweima.png"],"CreateTime":"2017-10-14 15:43","Operate":"取消订单","OrderNum":"43051216171014154323250","ActivityName":""},"Detail":["/assets/images/morenerweima.png"]}]
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
             * Order : {"IsPrePaid":true,"Status":1,"ActivityId":0,"SpecialId":0,
             * "Id":1012,"Benefit":0,"OriginalMoney":33.5,"PaidMoney":33.5,"CouponBenefit":0,"BenefitMoney":0,
             * "Photo":["/assets/images/morenerweima.png"],"CreateTime":"2017-10-14 15:43","Operate":"取消订单",
             * "OrderNum":"43051216171014154323250","ActivityName":""}
             * Detail : ["/assets/images/morenerweima.png"]
             */

            private OrderBean Order;
            private List<String> Detail;

            public OrderBean getOrder() {
                return Order;
            }

            public void setOrder(OrderBean Order) {
                this.Order = Order;
            }

            public List<String> getDetail() {
                return Detail;
            }

            public void setDetail(List<String> Detail) {
                this.Detail = Detail;
            }

            public static class OrderBean {
                /**
                 * IsPrePaid : 1
                 * Status : 1
                 * ActivityId : 0
                 * SpecialId : 0
                 * Id : 1012
                 * Benefit : 0
                 * OriginalMoney : 33.5
                 * PaidMoney : 33.5
                 * CouponBenefit : 0
                 * BenefitMoney : 0
                 * Photo : ["/assets/images/morenerweima.png"]
                 * CreateTime : 2017-10-14 15:43
                 * Operate : 取消订单
                 * OrderNum : 43051216171014154323250
                 * ActivityName : 
                 */

                private int IsPrePaid;
                private int Status;
                private int ActivityId;
                private int SpecialId;
                private int Id;
                private int Benefit;
                private double OriginalMoney;
                private double PaidMoney;
                private int CouponBenefit;
                private int BenefitMoney;
                private String CreateTime;
                private String Operate;
                private String OrderNum;
                private String ActivityName;
                private List<String> Photo;

                public int getIsPrePaid() {
                    return IsPrePaid;
                }

                public void setIsPrePaid(int IsPrePaid) {
                    this.IsPrePaid = IsPrePaid;
                }

                public int getStatus() {
                    return Status;
                }

                public void setStatus(int Status) {
                    this.Status = Status;
                }

                public int getActivityId() {
                    return ActivityId;
                }

                public void setActivityId(int ActivityId) {
                    this.ActivityId = ActivityId;
                }

                public int getSpecialId() {
                    return SpecialId;
                }

                public void setSpecialId(int SpecialId) {
                    this.SpecialId = SpecialId;
                }

                public int getId() {
                    return Id;
                }

                public void setId(int Id) {
                    this.Id = Id;
                }

                public int getBenefit() {
                    return Benefit;
                }

                public void setBenefit(int Benefit) {
                    this.Benefit = Benefit;
                }

                public double getOriginalMoney() {
                    return OriginalMoney;
                }

                public void setOriginalMoney(double OriginalMoney) {
                    this.OriginalMoney = OriginalMoney;
                }

                public double getPaidMoney() {
                    return PaidMoney;
                }

                public void setPaidMoney(double PaidMoney) {
                    this.PaidMoney = PaidMoney;
                }

                public int getCouponBenefit() {
                    return CouponBenefit;
                }

                public void setCouponBenefit(int CouponBenefit) {
                    this.CouponBenefit = CouponBenefit;
                }

                public int getBenefitMoney() {
                    return BenefitMoney;
                }

                public void setBenefitMoney(int BenefitMoney) {
                    this.BenefitMoney = BenefitMoney;
                }

                public String getCreateTime() {
                    return CreateTime;
                }

                public void setCreateTime(String CreateTime) {
                    this.CreateTime = CreateTime;
                }

                public String getOperate() {
                    return Operate;
                }

                public void setOperate(String Operate) {
                    this.Operate = Operate;
                }

                public String getOrderNum() {
                    return OrderNum;
                }

                public void setOrderNum(String OrderNum) {
                    this.OrderNum = OrderNum;
                }

                public String getActivityName() {
                    return ActivityName;
                }

                public void setActivityName(String ActivityName) {
                    this.ActivityName = ActivityName;
                }

                public List<String> getPhoto() {
                    return Photo;
                }

                public void setPhoto(List<String> Photo) {
                    this.Photo = Photo;
                }
            }
        }
    }
}
