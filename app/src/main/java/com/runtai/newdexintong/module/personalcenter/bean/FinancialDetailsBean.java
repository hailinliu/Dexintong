package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/9/22time15:05
 * @detail：财务明细对应的bean
 */

public class FinancialDetailsBean {

    /**
     * Code : 200
     * Data : {"total":4,"pages":1,"list":[{"DepositType":"易宝网银","Desc":"加款","T":"0","AccountType":"0","InCome":"0.01","Expense":"0.00","Remark":"豫便利","TransTime":"2017/9/20 11:55:27"},{"DepositType":"人工","Desc":"加款","T":"0","AccountType":"0","InCome":"100000.00","Expense":"0.00","Remark":"豫便利","TransTime":"2017/9/15 14:56:53"},{"DepositType":"人工","Desc":"加款","T":"0","AccountType":"1","InCome":"99999.00","Expense":"0.00","Remark":"豫便利","TransTime":"2017/9/15 14:56:24"},{"DepositType":"人工","Desc":"加款","T":"0","AccountType":"0","InCome":"99999.00","Expense":"0.00","Remark":"豫便利","TransTime":"2017/9/14 11:01:42"}]}
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
         * total : 4
         * pages : 1
         * list : [{"DepositType":"易宝网银","Desc":"加款","T":"0","AccountType":"0","InCome":"0.01","Expense":"0.00","Remark":"豫便利","TransTime":"2017/9/20 11:55:27"},{"DepositType":"人工","Desc":"加款","T":"0","AccountType":"0","InCome":"100000.00","Expense":"0.00","Remark":"豫便利","TransTime":"2017/9/15 14:56:53"},{"DepositType":"人工","Desc":"加款","T":"0","AccountType":"1","InCome":"99999.00","Expense":"0.00","Remark":"豫便利","TransTime":"2017/9/15 14:56:24"},{"DepositType":"人工","Desc":"加款","T":"0","AccountType":"0","InCome":"99999.00","Expense":"0.00","Remark":"豫便利","TransTime":"2017/9/14 11:01:42"}]
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
             * DepositType : 易宝网银
             * Desc : 加款
             * T : 0
             * AccountType : 0
             * InCome : 0.01
             * Expense : 0.00
             * Remark : 豫便利
             * TransTime : 2017/9/20 11:55:27
             */

            private String DepositType;
            private String Desc;
            private String T;
            private String AccountType;
            private String InCome;
            private String Expense;
            private String Remark;
            private String TransTime;

            public String getDepositType() {
                return DepositType;
            }

            public void setDepositType(String DepositType) {
                this.DepositType = DepositType;
            }

            public String getDesc() {
                return Desc;
            }

            public void setDesc(String Desc) {
                this.Desc = Desc;
            }

            public String getT() {
                return T;
            }

            public void setT(String T) {
                this.T = T;
            }

            public String getAccountType() {
                return AccountType;
            }

            public void setAccountType(String AccountType) {
                this.AccountType = AccountType;
            }

            public String getInCome() {
                return InCome;
            }

            public void setInCome(String InCome) {
                this.InCome = InCome;
            }

            public String getExpense() {
                return Expense;
            }

            public void setExpense(String Expense) {
                this.Expense = Expense;
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
