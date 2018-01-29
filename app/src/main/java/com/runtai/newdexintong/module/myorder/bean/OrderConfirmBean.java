package com.runtai.newdexintong.module.myorder.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/8/7time14:57
 * @detail：
 */

public class OrderConfirmBean {


    /**
     * Code : 200
     * Data : {"BenefitMoney":5,"PaidMoney":244.9,"OriginalMoney":249.9,"Benefit":0,"CouponBenefit":5,"List":[{"GiftItemId":0,"SpecialId":0,"ActivityId":0,"CouponBenefit":5,"OriginalMoney":249.9,"PaidMoney":244.9,"BenefitMoney":5,"Benefit":0,"Items":[{"IsDirect":false,"IsSend":false,"StockCount":4758,"Spec":6,"ItemId":5618,"BuyNum":5,"OriginalPrice":49.98,"BenefitPrice":48.98,"Unit":"包 ","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1738183818.jpg?x-oss-process=image/resize,m_pad,w_100,h_100","ItemName":"ABC夜用棉柔排湿表层卫生巾280mm8片K14"}],"ActivityName":"","GiftName":"","TipMsg":"优质商品请放心购买","Describe":""}],"Coupon":[{"IsUseCoupon":true,"CouponId":1012,"CouponBenefit":5,"CouponLimit":30,"CouponExpiry":"2017-10-19至2017-11-18","CouponNum":"201743051-11012","CouponName":"满30减5元","RedisKeyName":"cart-mid4305-spid0-act0"}]}
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
         * BenefitMoney : 5
         * PaidMoney : 244.9
         * OriginalMoney : 249.9
         * Benefit : 0
         * CouponBenefit : 5
         * List : [{"GiftItemId":0,"SpecialId":0,"ActivityId":0,"CouponBenefit":5,"OriginalMoney":249.9,"PaidMoney":244.9,"BenefitMoney":5,"Benefit":0,"Items":[{"IsDirect":false,"IsSend":false,"StockCount":4758,"Spec":6,"ItemId":5618,"BuyNum":5,"OriginalPrice":49.98,"BenefitPrice":48.98,"Unit":"包 ","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1738183818.jpg?x-oss-process=image/resize,m_pad,w_100,h_100","ItemName":"ABC夜用棉柔排湿表层卫生巾280mm8片K14"}],"ActivityName":"","GiftName":"","TipMsg":"优质商品请放心购买","Describe":""}]
         * Coupon : [{"IsUseCoupon":true,"CouponId":1012,"CouponBenefit":5,"CouponLimit":30,"CouponExpiry":"2017-10-19至2017-11-18","CouponNum":"201743051-11012","CouponName":"满30减5元","RedisKeyName":"cart-mid4305-spid0-act0"}]
         */

        private double BenefitMoney;
        private double PaidMoney;
        private double OriginalMoney;
        private double Benefit;
        private int CouponBenefit;
        private java.util.List<ListBean> List;
        private java.util.List<CouponBean> Coupon;

        public double getBenefitMoney() {
            return BenefitMoney;
        }

        public void setBenefitMoney(double BenefitMoney) {
            this.BenefitMoney = BenefitMoney;
        }

        public double getPaidMoney() {
            return PaidMoney;
        }

        public void setPaidMoney(double PaidMoney) {
            this.PaidMoney = PaidMoney;
        }

        public double getOriginalMoney() {
            return OriginalMoney;
        }

        public void setOriginalMoney(double OriginalMoney) {
            this.OriginalMoney = OriginalMoney;
        }

        public double getBenefit() {
            return Benefit;
        }

        public void setBenefit(double Benefit) {
            this.Benefit = Benefit;
        }

        public int getCouponBenefit() {
            return CouponBenefit;
        }

        public void setCouponBenefit(int CouponBenefit) {
            this.CouponBenefit = CouponBenefit;
        }

        public List<ListBean> getList() {
            return List;
        }

        public void setList(List<ListBean> List) {
            this.List = List;
        }

        public List<CouponBean> getCoupon() {
            return Coupon;
        }

        public void setCoupon(List<CouponBean> Coupon) {
            this.Coupon = Coupon;
        }

        public static class ListBean {
            /**
             * GiftItemId : 0
             * SpecialId : 0
             * ActivityId : 0
             * CouponBenefit : 5
             * OriginalMoney : 249.9
             * PaidMoney : 244.9
             * BenefitMoney : 5
             * Benefit : 0
             * Items : [{"IsDirect":false,"IsSend":false,"StockCount":4758,"Spec":6,"ItemId":5618,"BuyNum":5,"OriginalPrice":49.98,"BenefitPrice":48.98,"Unit":"包 ","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1738183818.jpg?x-oss-process=image/resize,m_pad,w_100,h_100","ItemName":"ABC夜用棉柔排湿表层卫生巾280mm8片K14"}]
             * ActivityName : 
             * GiftName : 
             * TipMsg : 优质商品请放心购买
             * Describe : 
             */

            private int GiftItemId;
            private int SpecialId;
            private int ActivityId;
            private int CouponBenefit;
            private double OriginalMoney;
            private double PaidMoney;
            private double BenefitMoney;
            private double Benefit;
            private String ActivityName;
            private String GiftName;
            private String TipMsg;
            private String Describe;
            private List<ItemsBean> Items;
          

            public int getGiftItemId() {
                return GiftItemId;
            }

            public void setGiftItemId(int GiftItemId) {
                this.GiftItemId = GiftItemId;
            }

            public int getSpecialId() {
                return SpecialId;
            }

            public void setSpecialId(int SpecialId) {
                this.SpecialId = SpecialId;
            }

            public int getActivityId() {
                return ActivityId;
            }

            public void setActivityId(int ActivityId) {
                this.ActivityId = ActivityId;
            }

            public int getCouponBenefit() {
                return CouponBenefit;
            }

            public void setCouponBenefit(int CouponBenefit) {
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

            public void setPaidMoney(double PaidMoney) {
                this.PaidMoney = PaidMoney;
            }

            public double getBenefitMoney() {
                return BenefitMoney;
            }

            public void setBenefitMoney(double BenefitMoney) {
                this.BenefitMoney = BenefitMoney;
            }

            public double getBenefit() {
                return Benefit;
            }

            public void setBenefit(double Benefit) {
                this.Benefit = Benefit;
            }

            public String getActivityName() {
                return ActivityName;
            }

            public void setActivityName(String ActivityName) {
                this.ActivityName = ActivityName;
            }

            public String getGiftName() {
                return GiftName;
            }

            public void setGiftName(String GiftName) {
                this.GiftName = GiftName;
            }

            public String getTipMsg() {
                return TipMsg;
            }

            public void setTipMsg(String TipMsg) {
                this.TipMsg = TipMsg;
            }

            public String getDescribe() {
                return Describe;
            }

            public void setDescribe(String Describe) {
                this.Describe = Describe;
            }

            public List<ItemsBean> getItems() {
                return Items;
            }

            public void setItems(List<ItemsBean> Items) {
                this.Items = Items;
            }

            public static class ItemsBean {
                /**
                 * IsDirect : false
                 * IsSend : false
                 * StockCount : 4758
                 * Spec : 6
                 * ItemId : 5618
                 * BuyNum : 5
                 * OriginalPrice : 49.98
                 * BenefitPrice : 48.98
                 * Unit : 包 
                 * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1738183818.jpg?x-oss-process=image/resize,m_pad,w_100,h_100
                 * ItemName : ABC夜用棉柔排湿表层卫生巾280mm8片K14
                 */

                private boolean IsDirect;
                private boolean IsSend;
                private int StockCount;
                private int Spec;
                private int ItemId;
                private int BuyNum;
                private double OriginalPrice;
                private double BenefitPrice;
                private String Unit;
                private String Photo;
                private String ItemName;

                private double BenefitLittleSum;

                public double getBenefitLittleSum() {
                    return BenefitLittleSum;
                }

                public void setBenefitLittleSum(double benefitLittleSum) {
                    BenefitLittleSum = benefitLittleSum;
                }

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

                public int getStockCount() {
                    return StockCount;
                }

                public void setStockCount(int StockCount) {
                    this.StockCount = StockCount;
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

                public int getBuyNum() {
                    return BuyNum;
                }

                public void setBuyNum(int BuyNum) {
                    this.BuyNum = BuyNum;
                }

                public double getOriginalPrice() {
                    return OriginalPrice;
                }

                public void setOriginalPrice(double OriginalPrice) {
                    this.OriginalPrice = OriginalPrice;
                }

                public double getBenefitPrice() {
                    return BenefitPrice;
                }

                public void setBenefitPrice(double BenefitPrice) {
                    this.BenefitPrice = BenefitPrice;
                }

                public String getUnit() {
                    return Unit;
                }

                public void setUnit(String Unit) {
                    this.Unit = Unit;
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

        public static class CouponBean {
            /**
             * IsUseCoupon : true
             * CouponId : 1012
             * CouponBenefit : 5
             * CouponLimit : 30
             * CouponExpiry : 2017-10-19至2017-11-18
             * CouponNum : 201743051-11012
             * CouponName : 满30减5元
             * RedisKeyName : cart-mid4305-spid0-act0
             */

            private boolean IsUseCoupon;
            private int CouponId;
            private int CouponBenefit;
            private int CouponLimit;
            private String CouponExpiry;
            private String CouponNum;
            private String CouponName;
            private String RedisKeyName;
            public boolean isSelected;

            public boolean isIsUseCoupon() {
                return IsUseCoupon;
            }

            public void setIsUseCoupon(boolean IsUseCoupon) {
                this.IsUseCoupon = IsUseCoupon;
            }

            public int getCouponId() {
                return CouponId;
            }

            public void setCouponId(int CouponId) {
                this.CouponId = CouponId;
            }

            public int getCouponBenefit() {
                return CouponBenefit;
            }

            public void setCouponBenefit(int CouponBenefit) {
                this.CouponBenefit = CouponBenefit;
            }

            public int getCouponLimit() {
                return CouponLimit;
            }

            public void setCouponLimit(int CouponLimit) {
                this.CouponLimit = CouponLimit;
            }

            public String getCouponExpiry() {
                return CouponExpiry;
            }

            public void setCouponExpiry(String CouponExpiry) {
                this.CouponExpiry = CouponExpiry;
            }

            public String getCouponNum() {
                return CouponNum;
            }

            public void setCouponNum(String CouponNum) {
                this.CouponNum = CouponNum;
            }

            public String getCouponName() {
                return CouponName;
            }

            public void setCouponName(String CouponName) {
                this.CouponName = CouponName;
            }

            public String getRedisKeyName() {
                return RedisKeyName;
            }

            public void setRedisKeyName(String RedisKeyName) {
                this.RedisKeyName = RedisKeyName;
            }
        }
    }
}
