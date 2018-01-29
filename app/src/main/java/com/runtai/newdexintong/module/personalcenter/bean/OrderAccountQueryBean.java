package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/11/13time13:40
 * @detail：订货账户查询数据bean
 */

public class OrderAccountQueryBean {

    /**
     * Code : 200
     * Data : {"total":2,"pages":1,"list":[{"InCome":67.92,"Expense":0,"Balance":82110.26,"Remark":"订单金额67.92,优惠0,实付67.92,退还67.92","TransType":"商户退货退款","TransTime":"2017-11-13 10:54:25","AcctType":"订货账户"},{"InCome":0,"Expense":67.92,"Balance":82042.34,"Remark":"订单金额67.92,实付金额67.92","TransType":"商品采购","TransTime":"2017-11-13 10:53:55","AcctType":"订货账户"}]}
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
         * total : 2
         * pages : 1
         * list : [{"InCome":67.92,"Expense":0,"Balance":82110.26,"Remark":"订单金额67.92,优惠0,实付67.92,退还67.92","TransType":"商户退货退款","TransTime":"2017-11-13 10:54:25","AcctType":"订货账户"},{"InCome":0,"Expense":67.92,"Balance":82042.34,"Remark":"订单金额67.92,实付金额67.92","TransType":"商品采购","TransTime":"2017-11-13 10:53:55","AcctType":"订货账户"}]
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
             * InCome : 67.92
             * Expense : 0.0
             * Balance : 82110.26
             * Remark : 订单金额67.92,优惠0,实付67.92,退还67.92
             * TransType : 商户退货退款
             * TransTime : 2017-11-13 10:54:25
             * AcctType : 订货账户
             */

            private double InCome;
            private double Expense;
            private double Balance;
            private String Remark;
            private String TransType;
            private String TransTime;
            private String AcctType;

            public double getInCome() {
                return InCome;
            }

            public void setInCome(double InCome) {
                this.InCome = InCome;
            }

            public double getExpense() {
                return Expense;
            }

            public void setExpense(double Expense) {
                this.Expense = Expense;
            }

            public double getBalance() {
                return Balance;
            }

            public void setBalance(double Balance) {
                this.Balance = Balance;
            }

            public String getRemark() {
                return Remark;
            }

            public void setRemark(String Remark) {
                this.Remark = Remark;
            }

            public String getTransType() {
                return TransType;
            }

            public void setTransType(String TransType) {
                this.TransType = TransType;
            }

            public String getTransTime() {
                return TransTime;
            }

            public void setTransTime(String TransTime) {
                this.TransTime = TransTime;
            }

            public String getAcctType() {
                return AcctType;
            }

            public void setAcctType(String AcctType) {
                this.AcctType = AcctType;
            }
        }
    }
}
