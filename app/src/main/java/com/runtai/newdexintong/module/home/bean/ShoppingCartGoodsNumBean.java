package com.runtai.newdexintong.module.home.bean;

/**
 * @author：rhf
 * @date：2017/10/19time15:13
 * @detail：
 */

public class ShoppingCartGoodsNumBean {
    /**
     * Code : 200
     * Data : {"Kind":10,"Num":94}
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
         * Kind : 10
         * Num : 94
         */

        private int Kind;
        private int Num;

        public int getKind() {
            return Kind;
        }

        public void setKind(int Kind) {
            this.Kind = Kind;
        }

        public int getNum() {
            return Num;
        }

        public void setNum(int Num) {
            this.Num = Num;
        }
    }
}
