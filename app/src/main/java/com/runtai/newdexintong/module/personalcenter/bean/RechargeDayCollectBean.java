package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/11/12time16:45
 * @detail：
 */

public class RechargeDayCollectBean {

    /**
     * Code : 200
     * Data : {"ORBalance":0,"OBalance":0,"RBalance":0,"list":[{"Rebate":0,"Amount":0,"Price":0,"Category":"移动缴费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"联通缴费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"电信缴费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"固话缴费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"QQ业务"},{"Rebate":0,"Amount":0,"Price":0,"Category":"水费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"电费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"燃气"},{"Rebate":0,"Amount":0,"Price":0,"Category":"合计："}]}
     * Msg : null
     */

    private int Code;
    private DataBean Data;
    private Object Msg;

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

    public Object getMsg() {
        return Msg;
    }

    public void setMsg(Object Msg) {
        this.Msg = Msg;
    }

    public static class DataBean {
        /**
         * ORBalance : 0.0
         * OBalance : 0.0
         * RBalance : 0.0
         * list : [{"Rebate":0,"Amount":0,"Price":0,"Category":"移动缴费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"联通缴费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"电信缴费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"固话缴费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"QQ业务"},{"Rebate":0,"Amount":0,"Price":0,"Category":"水费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"电费"},{"Rebate":0,"Amount":0,"Price":0,"Category":"燃气"},{"Rebate":0,"Amount":0,"Price":0,"Category":"合计："}]
         */

        private double ORBalance;
        private double OBalance;
        private double RBalance;
        private List<ListBean> list;

        public double getORBalance() {
            return ORBalance;
        }

        public void setORBalance(double ORBalance) {
            this.ORBalance = ORBalance;
        }

        public double getOBalance() {
            return OBalance;
        }

        public void setOBalance(double OBalance) {
            this.OBalance = OBalance;
        }

        public double getRBalance() {
            return RBalance;
        }

        public void setRBalance(double RBalance) {
            this.RBalance = RBalance;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * Rebate : 0.0
             * Amount : 0.0
             * Price : 0
             * Category : 移动缴费
             */

            private double Rebate;
            private double Amount;
            private double Price;
            private String Category;

            public double getRebate() {
                return Rebate;
            }

            public void setRebate(double Rebate) {
                this.Rebate = Rebate;
            }

            public double getAmount() {
                return Amount;
            }

            public void setAmount(double Amount) {
                this.Amount = Amount;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public String getCategory() {
                return Category;
            }

            public void setCategory(String Category) {
                this.Category = Category;
            }
        }
    }
}
