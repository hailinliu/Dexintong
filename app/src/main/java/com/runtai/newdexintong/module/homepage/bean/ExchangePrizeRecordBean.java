package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 */
public class ExchangePrizeRecordBean {


    /**
     * Code : 200
     * Data : {"total":1,"pages":1,"list":[{"Amount":30,"Qty":1,"CreateTime":"2017-10-14","StatusDesc":"兑奖成功","Award":"蒙牛真果粒","ItemName":"蒙牛真果粒250ml椰果味（单提12盒）"}]}
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
         * list : [{"Amount":30,"Qty":1,"CreateTime":"2017-10-14","StatusDesc":"兑奖成功","Award":"蒙牛真果粒","ItemName":"蒙牛真果粒250ml椰果味（单提12盒）"}]
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
             * Amount : 30.0
             * Qty : 1
             * CreateTime : 2017-10-14
             * StatusDesc : 兑奖成功
             * Award : 蒙牛真果粒
             * ItemName : 蒙牛真果粒250ml椰果味（单提12盒）
             */

            private double Amount;
            private int Qty;
            private String CreateTime;
            private String StatusDesc;
            private String Award;
            private String ItemName;

            public double getAmount() {
                return Amount;
            }

            public void setAmount(double Amount) {
                this.Amount = Amount;
            }

            public int getQty() {
                return Qty;
            }

            public void setQty(int Qty) {
                this.Qty = Qty;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getStatusDesc() {
                return StatusDesc;
            }

            public void setStatusDesc(String StatusDesc) {
                this.StatusDesc = StatusDesc;
            }

            public String getAward() {
                return Award;
            }

            public void setAward(String Award) {
                this.Award = Award;
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
