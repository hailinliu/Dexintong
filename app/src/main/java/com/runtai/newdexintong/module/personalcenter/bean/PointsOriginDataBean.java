package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/29time09:33
 * @detail：积分来源对应的bean
 */

public class PointsOriginDataBean {
    /**
     * Code : 1000
     * Msg : 成功
     * Data : {"list":[{"TransTime":"/Date(1500521206000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500521205000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"},{"TransTime":"/Date(1500521121000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500521120000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"},{"TransTime":"/Date(1500520977000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500520976000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"},{"TransTime":"/Date(1500520905000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500520905000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"},{"TransTime":"/Date(1500520731000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500520731000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"}],"total":14,"page":2}
     */

    private int Code;
    private String Msg;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public String getMsg() {
        return Msg;
    }

    public void setMsg(String Msg) {
        this.Msg = Msg;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * list : [{"TransTime":"/Date(1500521206000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500521205000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"},{"TransTime":"/Date(1500521121000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500521120000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"},{"TransTime":"/Date(1500520977000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500520976000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"},{"TransTime":"/Date(1500520905000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500520905000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"},{"TransTime":"/Date(1500520731000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号22"},{"TransTime":"/Date(1500520731000)/","Income":10,"Remark":"订货赠送积分,订单编号:21,订单商品编号21"}]
         * total : 14
         * page : 2
         */

        private int total;
        private int page;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPage() {
            return page;
        }

        public void setPage(int page) {
            this.page = page;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * TransTime : /Date(1500521206000)/
             * Income : 10
             * Remark : 订货赠送积分,订单编号:21,订单商品编号22
             */

            private String TransTime;
            private int Income;
            private String Remark;

            public String getTransTime() {
                return TransTime;
            }

            public void setTransTime(String TransTime) {
                this.TransTime = TransTime;
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
        }
    }
}
