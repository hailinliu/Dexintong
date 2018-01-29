package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * Created by Administrator on 2017/3/30.
 * 我要兑奖对应的实体类
 */
public class ExchangePrizeBean {


    /**
     * Code : 200
     * Data : {"total":6,"pages":1,"list":[{"Spec":36,"ItemId":1576,"Id":8,"Amount":2,"Ratio":50,"Time":"2017/09/21-2017/09/30","Name":"再来一盒","Item":"旺仔牛奶复原乳125ml（36盒）"},{"Spec":6,"ItemId":2008,"Id":6,"Amount":5,"Ratio":8,"Time":"2017/09/19-2017/12/31","Name":"5元红包","Item":"金龙鱼精炼一级大豆油2.5L"},{"Spec":1,"ItemId":2012,"Id":5,"Amount":5,"Ratio":20,"Time":"2017/09/19-2017/09/29","Name":"再来一瓶","Item":"西瓜"},{"Spec":6,"ItemId":2006,"Id":4,"Amount":5,"Ratio":8,"Time":"2017/09/19-2017/12/31","Name":"5元红包","Item":"金龙鱼精炼一级大豆油1.8L"},{"Spec":12,"ItemId":1186,"Id":2,"Amount":2.3,"Ratio":30,"Time":"2017/09/19-2018/01/11","Name":"再来一瓶","Item":"美汁源酷儿香橙味450ml"},{"Spec":6,"ItemId":407,"Id":1,"Amount":20,"Ratio":50,"Time":"2017/09/16-2017/09/30","Name":"9.16号兑奖","Item":"福临门一级大豆油1.8L"}]}
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
         * total : 6
         * pages : 1
         * list : [{"Spec":36,"ItemId":1576,"Id":8,"Amount":2,"Ratio":50,"Time":"2017/09/21-2017/09/30","Name":"再来一盒","Item":"旺仔牛奶复原乳125ml（36盒）"},{"Spec":6,"ItemId":2008,"Id":6,"Amount":5,"Ratio":8,"Time":"2017/09/19-2017/12/31","Name":"5元红包","Item":"金龙鱼精炼一级大豆油2.5L"},{"Spec":1,"ItemId":2012,"Id":5,"Amount":5,"Ratio":20,"Time":"2017/09/19-2017/09/29","Name":"再来一瓶","Item":"西瓜"},{"Spec":6,"ItemId":2006,"Id":4,"Amount":5,"Ratio":8,"Time":"2017/09/19-2017/12/31","Name":"5元红包","Item":"金龙鱼精炼一级大豆油1.8L"},{"Spec":12,"ItemId":1186,"Id":2,"Amount":2.3,"Ratio":30,"Time":"2017/09/19-2018/01/11","Name":"再来一瓶","Item":"美汁源酷儿香橙味450ml"},{"Spec":6,"ItemId":407,"Id":1,"Amount":20,"Ratio":50,"Time":"2017/09/16-2017/09/30","Name":"9.16号兑奖","Item":"福临门一级大豆油1.8L"}]
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
             * Spec : 36
             * ItemId : 1576
             * Id : 8
             * Amount : 2
             * Ratio : 50
             * Time : 2017/09/21-2017/09/30
             * Name : 再来一盒
             * Item : 旺仔牛奶复原乳125ml（36盒）
             */

            private int Spec;
            private int ItemId;
            private int Id;
            private double Amount;
            private int Ratio;
            private String Time;
            private String Name;
            private String Item;

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

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public double getAmount() {
                return Amount;
            }

            public void setAmount(double Amount) {
                this.Amount = Amount;
            }

            public int getRatio() {
                return Ratio;
            }

            public void setRatio(int Ratio) {
                this.Ratio = Ratio;
            }

            public String getTime() {
                return Time;
            }

            public void setTime(String Time) {
                this.Time = Time;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getItem() {
                return Item;
            }

            public void setItem(String Item) {
                this.Item = Item;
            }
        }
    }
}
