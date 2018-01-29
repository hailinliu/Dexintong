package com.runtai.newdexintong.module.homepage.bean;

/**
 * @author：rhf
 * @date：2017/11/8time16:18
 * @detail：
 */

public class PhoneOwnerInfoBean {
    /**
     * Code : 200
     * Data : {"Total":"26.18","Amount":"18.22","Balance":"0.0","Pack":"4G飞享套餐-58A元套餐（100分+500M）2015版新","Name":"任**"}
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
         * Total : 26.18
         * Amount : 18.22
         * Balance : 0.0
         * Pack : 4G飞享套餐-58A元套餐（100分+500M）2015版新
         * Name : 任**
         */

        private String Total;
        private String Amount;
        private String Balance;
        private String Pack;
        private String Name;

        public String getTotal() {
            return Total;
        }

        public void setTotal(String Total) {
            this.Total = Total;
        }

        public String getAmount() {
            return Amount;
        }

        public void setAmount(String Amount) {
            this.Amount = Amount;
        }

        public String getBalance() {
            return Balance;
        }

        public void setBalance(String Balance) {
            this.Balance = Balance;
        }

        public String getPack() {
            return Pack;
        }

        public void setPack(String Pack) {
            this.Pack = Pack;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
