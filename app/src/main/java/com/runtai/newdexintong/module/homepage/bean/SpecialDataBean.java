package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/19time20:36
 * @detail：
 */

public class SpecialDataBean {


    /**
     * Code : 200
     * Data : {"total":7,"pages":1,"list":[{"BuyMax":50,"Spec":6,"ItemId":7228,"SpecialId":1,"ActivityId":3,"OriginalPrice":14.88,"BarginPrice":0,"Unit":"盒 ","ItemName":"法丽兹抹茶慕斯巧克力曲奇58g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":33,"Spec":6,"ItemId":7682,"SpecialId":1,"ActivityId":3,"OriginalPrice":9.3,"BarginPrice":0,"Unit":"袋 ","ItemName":"佰士格果味威化饼干青苹果味90g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":10,"Spec":10,"ItemId":8669,"SpecialId":1,"ActivityId":3,"OriginalPrice":22.4,"BarginPrice":0,"Unit":"袋 ","ItemName":"卡奇豆香锅巴香甜味150g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":5,"Spec":4,"ItemId":2098,"SpecialId":1,"ActivityId":3,"OriginalPrice":38,"BarginPrice":0,"Unit":"盒 ","ItemName":"心相印150抽纸3连包DT2150","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":0,"Spec":12,"ItemId":2718,"SpecialId":1,"ActivityId":3,"OriginalPrice":50.4,"BarginPrice":0,"Unit":"罐 ","ItemName":"青岛啤酒易拉罐500m啤酒","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":50,"Spec":6,"ItemId":5618,"SpecialId":1,"ActivityId":3,"OriginalPrice":49.98,"BarginPrice":0,"Unit":"包 ","ItemName":"ABC夜用棉柔排湿表层卫生巾280mm8片K14","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":5,"Spec":6,"ItemId":7229,"SpecialId":1,"ActivityId":3,"OriginalPrice":14.88,"BarginPrice":0,"Unit":"盒 ","ItemName":"法丽兹柠檬香草巧克力曲奇58g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"}],"banner":[{"ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20171011085404445.png","Title":"","Url":""}],"category":[{"CateId":5,"CateName":"粮油干货"}],"ActivityName":"粮油干货"}
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
         * total : 7
         * pages : 1
         * list : [{"BuyMax":50,"Spec":6,"ItemId":7228,"SpecialId":1,"ActivityId":3,"OriginalPrice":14.88,"BarginPrice":0,"Unit":"盒 ","ItemName":"法丽兹抹茶慕斯巧克力曲奇58g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":33,"Spec":6,"ItemId":7682,"SpecialId":1,"ActivityId":3,"OriginalPrice":9.3,"BarginPrice":0,"Unit":"袋 ","ItemName":"佰士格果味威化饼干青苹果味90g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":10,"Spec":10,"ItemId":8669,"SpecialId":1,"ActivityId":3,"OriginalPrice":22.4,"BarginPrice":0,"Unit":"袋 ","ItemName":"卡奇豆香锅巴香甜味150g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":5,"Spec":4,"ItemId":2098,"SpecialId":1,"ActivityId":3,"OriginalPrice":38,"BarginPrice":0,"Unit":"盒 ","ItemName":"心相印150抽纸3连包DT2150","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":0,"Spec":12,"ItemId":2718,"SpecialId":1,"ActivityId":3,"OriginalPrice":50.4,"BarginPrice":0,"Unit":"罐 ","ItemName":"青岛啤酒易拉罐500m啤酒","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":50,"Spec":6,"ItemId":5618,"SpecialId":1,"ActivityId":3,"OriginalPrice":49.98,"BarginPrice":0,"Unit":"包 ","ItemName":"ABC夜用棉柔排湿表层卫生巾280mm8片K14","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"},{"BuyMax":5,"Spec":6,"ItemId":7229,"SpecialId":1,"ActivityId":3,"OriginalPrice":14.88,"BarginPrice":0,"Unit":"盒 ","ItemName":"法丽兹柠檬香草巧克力曲奇58g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100"}]
         * banner : [{"ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20171011085404445.png","Title":"","Url":""}]
         * category : [{"CateId":5,"CateName":"粮油干货"}]
         * ActivityName : 粮油干货
         */

        private int total;
        private int pages;
        private String ActivityName;
        private List<ListBean> list;
        private List<BannerBean> banner;
        private List<CategoryBean> category;

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

        public String getActivityName() {
            return ActivityName;
        }

        public void setActivityName(String ActivityName) {
            this.ActivityName = ActivityName;
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

        public List<CategoryBean> getCategory() {
            return category;
        }

        public void setCategory(List<CategoryBean> category) {
            this.category = category;
        }

        public static class ListBean {
            /**
             * BuyMax : 50
             * Spec : 6
             * ItemId : 7228
             * SpecialId : 1
             * ActivityId : 3
             * OriginalPrice : 14.88
             * BarginPrice : 0
             * Unit : 盒 
             * ItemName : 法丽兹抹茶慕斯巧克力曲奇58g
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img//assets/images/morenerweima.png?x-oss-process=image/resize,m_pad,w_100,h_100
             */

            private int BuyMax;
            private int Spec;
            private int ItemId;
            private int SpecialId;
            private int ActivityId;
            private double OriginalPrice;
            private double BarginPrice;
            private String Unit;
            private String ItemName;
            private String Photo;
            private boolean IsSold;
            private String Mfd;

            public String getMfd() {
                return Mfd;
            }

            public void setMfd(String mfd) {
                Mfd = mfd;
            }

            public boolean isSold() {
                return IsSold;
            }

            public void setSold(boolean sold) {
                IsSold = sold;
            }

            public int getBuyMax() {
                return BuyMax;
            }

            public void setBuyMax(int BuyMax) {
                this.BuyMax = BuyMax;
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

            public int getSpecialId() {
                return SpecialId;
            }

            public void setSpecialId(int SpecialId) {
                this.SpecialId = SpecialId;
            }

            public int getActivityId() {
                return ActivityId;
            }

            public void setActivityId(int ActivityId) {
                this.ActivityId = ActivityId;
            }

            public double getOriginalPrice() {
                return OriginalPrice;
            }

            public void setOriginalPrice(double OriginalPrice) {
                this.OriginalPrice = OriginalPrice;
            }

            public double getBarginPrice() {
                return BarginPrice;
            }

            public void setBarginPrice(double BarginPrice) {
                this.BarginPrice = BarginPrice;
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

        public static class BannerBean {
            /**
             * ImgPath : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20171011085404445.png
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

        public static class CategoryBean {
            /**
             * CateId : 5
             * CateName : 粮油干货
             */

            private int CateId;
            private String CateName;

            public int getCateId() {
                return CateId;
            }

            public void setCateId(int CateId) {
                this.CateId = CateId;
            }

            public String getCateName() {
                return CateName;
            }

            public void setCateName(String CateName) {
                this.CateName = CateName;
            }
        }
    }
}
