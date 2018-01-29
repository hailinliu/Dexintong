package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/29time11:57
 * @detail：
 */

public class MyCouponBean {


    /**
     * Code : 200
     * Data : {"total":5,"pages":1,"list":[{"LimitMoney":15,"Denomination":5,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":195,"ExpiryDate":"2017.09.21至2017.10.01","Name":"9.21 满15-5"},{"LimitMoney":50,"Denomination":5,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":166,"ExpiryDate":"2017.09.21至2017.10.21","Name":"9.21 满50-5"},{"LimitMoney":10,"Denomination":10,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":115,"ExpiryDate":"2017.09.16至2017.09.26","Name":"9.15 测试 满100-10"},{"LimitMoney":20,"Denomination":10,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":86,"ExpiryDate":"2017.09.15至2017.10.05","Name":"9.15 全场不限满20-10"},{"LimitMoney":10,"Denomination":10,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":62,"ExpiryDate":"2017.09.15至2017.09.25","Name":"9.15 测试 满100-10"}]}
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
         * total : 5
         * pages : 1
         * list : [{"LimitMoney":15,"Denomination":5,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":195,"ExpiryDate":"2017.09.21至2017.10.01","Name":"9.21 满15-5"},{"LimitMoney":50,"Denomination":5,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":166,"ExpiryDate":"2017.09.21至2017.10.21","Name":"9.21 满50-5"},{"LimitMoney":10,"Denomination":10,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":115,"ExpiryDate":"2017.09.16至2017.09.26","Name":"9.15 测试 满100-10"},{"LimitMoney":20,"Denomination":10,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":86,"ExpiryDate":"2017.09.15至2017.10.05","Name":"9.15 全场不限满20-10"},{"LimitMoney":10,"Denomination":10,"Status":0,"CategoryId":0,"BrandId":0,"SpecialPerformanceId":-1,"Id":62,"ExpiryDate":"2017.09.15至2017.09.25","Name":"9.15 测试 满100-10"}]
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
             * LimitMoney : 15
             * Denomination : 5
             * Status : 0
             * CategoryId : 0
             * BrandId : 0
             * SpecialPerformanceId : -1
             * Id : 195
             * ExpiryDate : 2017.09.21至2017.10.01
             * Name : 9.21 满15-5
             */

            private int LimitMoney;
            private int Denomination;
            private int Status;
            private int CategoryId;
            private int BrandId;
            private int SpecialPerformanceId;
            private int Id;
            private String ExpiryDate;
            private String Name;

            public int getLimitMoney() {
                return LimitMoney;
            }

            public void setLimitMoney(int LimitMoney) {
                this.LimitMoney = LimitMoney;
            }

            public int getDenomination() {
                return Denomination;
            }

            public void setDenomination(int Denomination) {
                this.Denomination = Denomination;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public int getCategoryId() {
                return CategoryId;
            }

            public void setCategoryId(int CategoryId) {
                this.CategoryId = CategoryId;
            }

            public int getBrandId() {
                return BrandId;
            }

            public void setBrandId(int BrandId) {
                this.BrandId = BrandId;
            }

            public int getSpecialPerformanceId() {
                return SpecialPerformanceId;
            }

            public void setSpecialPerformanceId(int SpecialPerformanceId) {
                this.SpecialPerformanceId = SpecialPerformanceId;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getExpiryDate() {
                return ExpiryDate;
            }

            public void setExpiryDate(String ExpiryDate) {
                this.ExpiryDate = ExpiryDate;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }
        }
    }
}
