package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/9/25time10:12
 * @detail：
 */

public class OrderDetailBean {


    /**
     * Code : 200
     * Data : {"Order":{"Status":7,"Id":1011,"Benefit":0,"CouponBenefit":0,"OriginalMoney":522,"PaidMoney":522,"BenefitMoney":0,"CreateTime":"2017-10-14 14:36:48","OrderNum":"43051216171014143648783","ActivityName":""},"Detail":[{"IsDirect":false,"IsSend":false,"Staus":11,"DeliverNum":3,"Qty":3,"ItemId":8889,"Id":1033,"PaidMoney":522,"Price":34,"Operate":"3","StausDes":"已确认","Payable":"已付","SpecUnit":"1/提 /件","Photo":"/assets/images/morenerweima.png","ItemName":"蒙牛真果粒250ml蓝莓味（单提12盒）"},{"IsDirect":false,"IsSend":false,"Staus":11,"DeliverNum":6,"Qty":6,"ItemId":8609,"Id":1034,"PaidMoney":522,"Price":33.5,"Operate":"3","StausDes":"已确认","Payable":"已付","SpecUnit":"1/提 /件","Photo":"/assets/images/morenerweima.png","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"},{"IsDirect":false,"IsSend":false,"Staus":11,"DeliverNum":5,"Qty":5,"ItemId":8610,"Id":1035,"PaidMoney":522,"Price":33,"Operate":"123","StausDes":"已确认","Payable":"已付","SpecUnit":"1/提 /件","Photo":"/assets/images/morenerweima.png","ItemName":"蒙牛真果粒250ml椰果味（单提12盒）"},{"IsDirect":false,"IsSend":false,"Staus":11,"DeliverNum":2,"Qty":2,"ItemId":2833,"Id":1036,"PaidMoney":522,"Price":27,"Operate":"123","StausDes":"已确认","Payable":"已付","SpecUnit":"20/瓶 /件","Photo":"/assets/images/morenerweima.png","ItemName":"蒙牛酸酸乳草莓味250ml"}]}
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
         * Order : {"Status":7,"Id":1011,"Benefit":0,"CouponBenefit":0,"OriginalMoney":522,"PaidMoney":522,"BenefitMoney":0,"CreateTime":"2017-10-14 14:36:48","OrderNum":"43051216171014143648783","ActivityName":""}
         * Detail : [{"IsDirect":false,"IsSend":false,"Staus":11,"DeliverNum":3,"Qty":3,"ItemId":8889,"Id":1033,"PaidMoney":522,"Price":34,"Operate":"3","StausDes":"已确认","Payable":"已付","SpecUnit":"1/提 /件","Photo":"/assets/images/morenerweima.png","ItemName":"蒙牛真果粒250ml蓝莓味（单提12盒）"},{"IsDirect":false,"IsSend":false,"Staus":11,"DeliverNum":6,"Qty":6,"ItemId":8609,"Id":1034,"PaidMoney":522,"Price":33.5,"Operate":"3","StausDes":"已确认","Payable":"已付","SpecUnit":"1/提 /件","Photo":"/assets/images/morenerweima.png","ItemName":"蒙牛真果粒250ml黄桃味（单提12盒）"},{"IsDirect":false,"IsSend":false,"Staus":11,"DeliverNum":5,"Qty":5,"ItemId":8610,"Id":1035,"PaidMoney":522,"Price":33,"Operate":"123","StausDes":"已确认","Payable":"已付","SpecUnit":"1/提 /件","Photo":"/assets/images/morenerweima.png","ItemName":"蒙牛真果粒250ml椰果味（单提12盒）"},{"IsDirect":false,"IsSend":false,"Staus":11,"DeliverNum":2,"Qty":2,"ItemId":2833,"Id":1036,"PaidMoney":522,"Price":27,"Operate":"123","StausDes":"已确认","Payable":"已付","SpecUnit":"20/瓶 /件","Photo":"/assets/images/morenerweima.png","ItemName":"蒙牛酸酸乳草莓味250ml"}]
         */

        private OrderBean Order;
        private List<DetailBean> Detail;

        public OrderBean getOrder() {
            return Order;
        }

        public void setOrder(OrderBean Order) {
            this.Order = Order;
        }

        public List<DetailBean> getDetail() {
            return Detail;
        }

        public void setDetail(List<DetailBean> Detail) {
            this.Detail = Detail;
        }

        public static class OrderBean {
            /**
             * Status : 7
             * Id : 1011
             * Benefit : 0
             * CouponBenefit : 0
             * OriginalMoney : 522
             * PaidMoney : 522
             * BenefitMoney : 0
             * CreateTime : 2017-10-14 14:36:48
             * OrderNum : 43051216171014143648783
             * ActivityName : 
             */

            private int Status;
            private int Id;
            private double Benefit;
            private double CouponBenefit;
            private double OriginalMoney;
            private double PaidMoney;
            private double BenefitMoney;
            private String CreateTime;
            private String OrderNum;
            private String ActivityName;

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public double getBenefit() {
                return Benefit;
            }

            public void setBenefit(double Benefit) {
                this.Benefit = Benefit;
            }

            public double getCouponBenefit() {
                return CouponBenefit;
            }

            public void setCouponBenefit(double CouponBenefit) {
                this.CouponBenefit = CouponBenefit;
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

            public void setPaidMoney(int PaidMoney) {
                this.PaidMoney = PaidMoney;
            }

            public double getBenefitMoney() {
                return BenefitMoney;
            }

            public void setBenefitMoney(double BenefitMoney) {
                this.BenefitMoney = BenefitMoney;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
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
        }

        public static class DetailBean {
            /**
             * IsDirect : false
             * IsSend : false
             * Staus : 11
             * DeliverNum : 3
             * Qty : 3
             * ItemId : 8889
             * Id : 1033
             * PaidMoney : 522
             * Price : 34
             * Operate : 3
             * StausDes : 已确认
             * Payable : 已付
             * SpecUnit : 1/提 /件
             * Photo : /assets/images/morenerweima.png
             * ItemName : 蒙牛真果粒250ml蓝莓味（单提12盒）
             */

            private boolean IsDirect;
            private boolean IsSend;
            private int Staus;
            private int DeliverNum;
            private int Qty;
            private int ItemId;
            private int Id;
            private double PaidMoney;
            private double Price;
            private String Operate;
            private String StausDes;
            private String Payable;
            private String SpecUnit;
            private String Photo;
            private String ItemName;

            public boolean isIsDirect() {
                return IsDirect;
            }

            public void setIsDirect(boolean IsDirect) {
                this.IsDirect = IsDirect;
            }

            public boolean isIsSend() {
                return IsSend;
            }

            public void setIsSend(boolean IsSend) {
                this.IsSend = IsSend;
            }

            public int getStaus() {
                return Staus;
            }

            public void setStaus(int Staus) {
                this.Staus = Staus;
            }

            public int getDeliverNum() {
                return DeliverNum;
            }

            public void setDeliverNum(int DeliverNum) {
                this.DeliverNum = DeliverNum;
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

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public double getPaidMoney() {
                return PaidMoney;
            }

            public void setPaidMoney(double PaidMoney) {
                this.PaidMoney = PaidMoney;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public String getOperate() {
                return Operate;
            }

            public void setOperate(String Operate) {
                this.Operate = Operate;
            }

            public String getStausDes() {
                return StausDes;
            }

            public void setStausDes(String StausDes) {
                this.StausDes = StausDes;
            }

            public String getPayable() {
                return Payable;
            }

            public void setPayable(String Payable) {
                this.Payable = Payable;
            }

            public String getSpecUnit() {
                return SpecUnit;
            }

            public void setSpecUnit(String SpecUnit) {
                this.SpecUnit = SpecUnit;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
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
