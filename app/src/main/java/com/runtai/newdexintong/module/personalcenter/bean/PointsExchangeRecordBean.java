package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/28time15:16
 * @detail：积分记录对应的bean
 */

public class PointsExchangeRecordBean {


    /**
     * Code : 200
     * Data : {"total":10,"pages":1,"list":[{"Expense":3,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-23 15:50"},{"Expense":3,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-23 15:47"},{"Expense":3,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-23 15:22"},{"Expense":3,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-23 15:22"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-18 17:34"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-18 17:32"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-18 17:22"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-09-29 10:21"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-09-28 16:38"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-09-28 14:04"}]}
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
         * total : 10
         * pages : 1
         * list : [{"Expense":3,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-23 15:50"},{"Expense":3,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-23 15:47"},{"Expense":3,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-23 15:22"},{"Expense":3,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-23 15:22"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-18 17:34"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-18 17:32"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-10-18 17:22"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-09-29 10:21"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-09-28 16:38"},{"Expense":2,"Income":0,"Remark":"积分兑换现金券","TransTime":"2017-09-28 14:04"}]
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
             * Expense : 3
             * Income : 0
             * Remark : 积分兑换现金券
             * TransTime : 2017-10-23 15:50
             */

            private int Expense;
            private int Income;
            private String Remark;
            private String TransTime;

            public int getExpense() {
                return Expense;
            }

            public void setExpense(int Expense) {
                this.Expense = Expense;
            }

            public int getIncome() {
                return Income;
            }

            public void setIncome(int Income) {
                this.Income = Income;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public String getTransTime() {
                return TransTime;
            }

            public void setTransTime(String TransTime) {
                this.TransTime = TransTime;
            }
        }
    }
}
