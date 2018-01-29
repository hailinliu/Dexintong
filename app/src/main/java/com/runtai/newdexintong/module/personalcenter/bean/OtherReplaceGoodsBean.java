package com.runtai.newdexintong.module.personalcenter.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/8/3time14:54
 * @detail：
 */

public class OtherReplaceGoodsBean {

    /**
     * Code : 200
     * Data : [{"SaleIncreaseCount":1,"SaleFromCount":1,"Stock":143,"Spec":6,"ItemId":7681,"OriginalPrice":9.3,"Unit":"袋 ","ItemName":"佰士格果味威化饼干牛奶味90g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0936333633.jpg?x-oss-process=image/resize,m_pad,w_62,h_62"},{"SaleIncreaseCount":1,"SaleFromCount":1,"Stock":97,"Spec":6,"ItemId":7687,"OriginalPrice":19.92,"Unit":"袋 ","ItemName":"佰士格夹心威化饼干牛奶味246g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0941264126.jpg?x-oss-process=image/resize,m_pad,w_62,h_62"}]
     * Msg : 成功
     */

    private int Code;
    private String Msg;
    private List<DataBean> Data;

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

    public List<DataBean> getData() {
        return Data;
    }

    public void setData(List<DataBean> Data) {
        this.Data = Data;
    }

    public static class DataBean {
        /**
         * SaleIncreaseCount : 1
         * SaleFromCount : 1
         * Stock : 143
         * Spec : 6
         * ItemId : 7681
         * OriginalPrice : 9.3
         * Unit : 袋 
         * ItemName : 佰士格果味威化饼干牛奶味90g
         * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0936333633.jpg?x-oss-process=image/resize,m_pad,w_62,h_62
         */

        private int SaleIncreaseCount;
        private int SaleFromCount;
        private int Stock;
        private int Spec;
        private int ItemId;
        private double OriginalPrice;
        private String Unit;
        private String ItemName;
        private String Photo;
        public boolean isChecked;

        public int getSaleIncreaseCount() {
            return SaleIncreaseCount;
        }

        public void setSaleIncreaseCount(int SaleIncreaseCount) {
            this.SaleIncreaseCount = SaleIncreaseCount;
        }

        public int getSaleFromCount() {
            return SaleFromCount;
        }

        public void setSaleFromCount(int SaleFromCount) {
            this.SaleFromCount = SaleFromCount;
        }

        public int getStock() {
            return Stock;
        }

        public void setStock(int Stock) {
            this.Stock = Stock;
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

        public double getOriginalPrice() {
            return OriginalPrice;
        }

        public void setOriginalPrice(double OriginalPrice) {
            this.OriginalPrice = OriginalPrice;
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

        public String getPhoto() {
            return Photo;
        }

        public void setPhoto(String Photo) {
            this.Photo = Photo;
        }
    }
}
