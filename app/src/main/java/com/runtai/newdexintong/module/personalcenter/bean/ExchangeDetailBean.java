package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @author：rhf
 * @date：2017/7/28time18:34
 * @detail：
 */

public class ExchangeDetailBean {

    /**
     * Code : 200
     * Data : {"Num":12,"Spec":6,"ItemId":407,"Id":1,"Orders":4,"Amount":20,"Ratio":50,"Item":"福临门一级大豆油1.8L","Name":"9.16号兑奖"}
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
         * Num : 12
         * Spec : 6
         * ItemId : 407
         * Id : 1
         * Orders : 4
         * Amount : 20
         * Ratio : 50
         * Item : 福临门一级大豆油1.8L
         * Name : 9.16号兑奖
         */

        private int Num;
        private int Spec;
        private int ItemId;
        private int Id;
        private int Orders;
        private int Amount;
        private int Ratio;
        private String Item;
        private String Name;

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
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

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public int getOrders() {
            return Orders;
        }

        public void setOrders(int Orders) {
            this.Orders = Orders;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public int getRatio() {
            return Ratio;
        }

        public void setRatio(int Ratio) {
            this.Ratio = Ratio;
        }

        public String getItem() {
            return Item;
        }

        public void setItem(String Item) {
            this.Item = Item;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
