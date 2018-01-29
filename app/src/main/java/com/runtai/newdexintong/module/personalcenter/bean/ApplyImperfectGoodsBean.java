package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @author：rhf
 * @date：2017/10/18time18:32
 * @detail：
 */

public class ApplyImperfectGoodsBean {
    /**
     * Code : 200
     * Data : {"ReturnNum":0,"ConfirmNum":5,"Spec":1,"OrderId":1011,"Id":1035,"photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230","Unit":"提 ","ItemName":"蒙牛真果粒250ml椰果味（单提12盒）"}
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
         * ReturnNum : 0
         * ConfirmNum : 5
         * Spec : 1
         * OrderId : 1011
         * Id : 1035
         * photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0836183618.jpg?x-oss-process=image/resize,m_pad,w_228,h_230
         * Unit : 提 
         * ItemName : 蒙牛真果粒250ml椰果味（单提12盒）
         */

        private int ReturnNum;
        private int ConfirmNum;
        private int Spec;
        private int OrderId;
        private int Id;
        private String photo;
        private String Unit;
        private String ItemName;

        public int getReturnNum() {
            return ReturnNum;
        }

        public void setReturnNum(int ReturnNum) {
            this.ReturnNum = ReturnNum;
        }

        public int getConfirmNum() {
            return ConfirmNum;
        }

        public void setConfirmNum(int ConfirmNum) {
            this.ConfirmNum = ConfirmNum;
        }

        public int getSpec() {
            return Spec;
        }

        public void setSpec(int Spec) {
            this.Spec = Spec;
        }

        public int getOrderId() {
            return OrderId;
        }

        public void setOrderId(int OrderId) {
            this.OrderId = OrderId;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public String getPhoto() {
            return photo;
        }

        public void setPhoto(String photo) {
            this.photo = photo;
        }

        public String getUnit() {
            return Unit;
        }

        public void setUnit(String Unit) {
            this.Unit = Unit;
        }

        public String getItemName() {
            return ItemName;
        }

        public void setItemName(String ItemName) {
            this.ItemName = ItemName;
        }
    }
}
