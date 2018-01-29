package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/20time19:08
 * @detail：秒杀对应的bean
 */

public class SpikeDataBean {

    /**
     * Code : 200
     * Data : {"total":2,"pages":1,"list":[{"SpecialId":4,"Id":5,"ActivityId":null,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0942424242.jpg?x-oss-process=image/resize,m_pad,h_228,w_224","ItemName":"法丽兹抹茶慕斯巧克力曲奇58g","Spec":6,"Unit":"盒   ","BuyMax":0,"Price":11.94,"SaleFromCount":1,"SaleIncreaseCount":1,"IsSold":false},{"SpecialId":4,"Id":1,"ActivityId":null,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,h_228,w_224","ItemName":"蒙牛酸酸乳原味250ml","Spec":20,"Unit":"瓶   ","BuyMax":5,"Price":19.6,"SaleFromCount":1,"SaleIncreaseCount":1,"IsSold":true}],"banner":[{"ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20170927161635063.jpg","Title":"","Url":""}],"Title":"秒杀专场"}
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
         * total : 2
         * pages : 1
         * list : [{"SpecialId":4,"Id":5,"ActivityId":null,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0942424242.jpg?x-oss-process=image/resize,m_pad,h_228,w_224","ItemName":"法丽兹抹茶慕斯巧克力曲奇58g","Spec":6,"Unit":"盒   ","BuyMax":0,"Price":11.94,"SaleFromCount":1,"SaleIncreaseCount":1,"IsSold":false},{"SpecialId":4,"Id":1,"ActivityId":null,"Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,h_228,w_224","ItemName":"蒙牛酸酸乳原味250ml","Spec":20,"Unit":"瓶   ","BuyMax":5,"Price":19.6,"SaleFromCount":1,"SaleIncreaseCount":1,"IsSold":true}]
         * banner : [{"ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20170927161635063.jpg","Title":"","Url":""}]
         * Title : 秒杀专场
         */

        private int total;
        private int pages;
        private String Title;
        private List<ListBean> list;
        private List<BannerBean> banner;

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

        public String getTitle() {
            return Title;
        }

        public void setTitle(String Title) {
            this.Title = Title;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public List<BannerBean> getBanner() {
            return banner;
        }

        public void setBanner(List<BannerBean> banner) {
            this.banner = banner;
        }

        public static class ListBean {
            /**
             * SpecialId : 4
             * Id : 5
             * ActivityId : null
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0942424242.jpg?x-oss-process=image/resize,m_pad,h_228,w_224
             * ItemName : 法丽兹抹茶慕斯巧克力曲奇58g
             * Spec : 6
             * Unit : 盒   
             * BuyMax : 0
             * Price : 11.94
             * SaleFromCount : 1
             * SaleIncreaseCount : 1
             * IsSold : false
             */

            private int SpecialId;
            private int Id;
            private int ActivityId;
            private String Photo;
            private String ItemName;
            private int Spec;
            private String Unit;
            private int BuyMax;
            private double Price;
            private int SaleFromCount;
            private int SaleIncreaseCount;
            private boolean IsSold;
            private String Mfd;

            public String getMfd() {
                return Mfd;
            }

            public void setMfd(String mfd) {
                Mfd = mfd;
            }

            public int getSpecialId() {
                return SpecialId;
            }

            public void setSpecialId(int SpecialId) {
                this.SpecialId = SpecialId;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public int getActivityId() {
                return ActivityId;
            }

            public void setActivityId(int ActivityId) {
                this.ActivityId = ActivityId;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
            }

            public String getItemName() {
                return ItemName;
            }

            public void setItemName(String ItemName) {
                this.ItemName = ItemName;
            }

            public int getSpec() {
                return Spec;
            }

            public void setSpec(int Spec) {
                this.Spec = Spec;
            }

            public String getUnit() {
                return Unit;
            }

            public void setUnit(String Unit) {
                this.Unit = Unit;
            }

            public int getBuyMax() {
                return BuyMax;
            }

            public void setBuyMax(int BuyMax) {
                this.BuyMax = BuyMax;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public int getSaleFromCount() {
                return SaleFromCount;
            }

            public void setSaleFromCount(int SaleFromCount) {
                this.SaleFromCount = SaleFromCount;
            }

            public int getSaleIncreaseCount() {
                return SaleIncreaseCount;
            }

            public void setSaleIncreaseCount(int SaleIncreaseCount) {
                this.SaleIncreaseCount = SaleIncreaseCount;
            }

            public boolean isIsSold() {
                return IsSold;
            }

            public void setIsSold(boolean IsSold) {
                this.IsSold = IsSold;
            }
        }

        public static class BannerBean {
            /**
             * ImgPath : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20170927161635063.jpg
             * Title : 
             * Url : 
             */

            private String ImgPath;
            private String Title;
            private String Url;

            public String getImgPath() {
                return ImgPath;
            }

            public void setImgPath(String ImgPath) {
                this.ImgPath = ImgPath;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getUrl() {
                return Url;
            }

            public void setUrl(String Url) {
                this.Url = Url;
            }
        }
    }
}
