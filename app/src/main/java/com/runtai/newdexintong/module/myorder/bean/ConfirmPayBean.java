package com.runtai.newdexintong.module.myorder.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/24time15:09
 * @detail：
 */

public class ConfirmPayBean {

    /**
     * Code : 9999
     * Data : {"succeeded":[],"coupons":[],"failed":[{"ItemId":2865,"ItemName":"蒙牛酸酸乳原味250ml"}]}
     * Msg : 抱歉! 购物车中部分商品今日热销售完 或 限购, 未能成功下单! 请从购物车中删除红色标记商品,重新下单! 
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
        private List<?> succeeded;
        private List<?> coupons;
        private List<FailedBean> failed;

        public List<?> getSucceeded() {
            return succeeded;
        }

        public void setSucceeded(List<?> succeeded) {
            this.succeeded = succeeded;
        }

        public List<?> getCoupons() {
            return coupons;
        }

        public void setCoupons(List<?> coupons) {
            this.coupons = coupons;
        }

        public List<FailedBean> getFailed() {
            return failed;
        }

        public void setFailed(List<FailedBean> failed) {
            this.failed = failed;
        }

        public static class FailedBean {
            /**
             * ItemId : 2865
             * ItemName : 蒙牛酸酸乳原味250ml
             */

            private int ItemId;
            private String ItemName;

            public int getItemId() {
                return ItemId;
            }

            public void setItemId(int ItemId) {
                this.ItemId = ItemId;
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
