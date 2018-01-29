package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/19time20:34
 * @detail：
 */

public class DhhListDataBean {


    /**
     * Code : 200
     * Data : {"total":9,"pages":1,"list":[{"SaleFromCount":1,"Stock":63,"Spec":5,"SpecialId":5,"ActivityId":1,"Id":9407,"Price":23.9,"Unit":"包 ","Name":"卡奇豆香锅巴香辣味300g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1721402140.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":201,"Spec":6,"SpecialId":5,"ActivityId":1,"Id":7683,"Price":9.3,"Unit":"袋 ","Name":"佰士格果味威化饼干巧克力味90g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0937213721.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":23,"Spec":14,"SpecialId":5,"ActivityId":1,"Id":1455,"Price":43.96,"Unit":"盒 ","Name":"真巧酱心曲奇牛奶味盒装120g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1619501950.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":0,"Spec":20,"SpecialId":5,"ActivityId":1,"Id":2865,"Price":28.4,"Unit":"瓶 ","Name":"蒙牛酸酸乳原味250ml","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":172,"Spec":6,"SpecialId":5,"ActivityId":1,"Id":7229,"Price":14.88,"Unit":"盒 ","Name":"法丽兹柠檬香草巧克力曲奇58g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0943454345.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":388,"Spec":6,"SpecialId":5,"ActivityId":1,"Id":7689,"Price":19.98,"Unit":"袋 ","Name":"佰士格夹心威化饼干巧克力味246g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0942404240.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":12,"Spec":1,"SpecialId":5,"ActivityId":1,"Id":8611,"Price":33.5,"Unit":"提 ","Name":"蒙牛真果粒250ml草莓味（单提12盒）","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/083800380.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":135,"Spec":10,"SpecialId":5,"ActivityId":1,"Id":9165,"Price":22.9,"Unit":"袋 ","Name":"丹鹤小米锅巴牛肉味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1423172317.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":375,"Spec":6,"SpecialId":5,"ActivityId":1,"Id":7240,"Price":19.92,"Unit":"盒 ","Name":"法丽兹黑糖曲奇102g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/100223223.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"}],"banner":[],"Title":"订货会专场"}
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
         * total : 9
         * pages : 1
         * list : [{"SaleFromCount":1,"Stock":63,"Spec":5,"SpecialId":5,"ActivityId":1,"Id":9407,"Price":23.9,"Unit":"包 ","Name":"卡奇豆香锅巴香辣味300g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1721402140.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":201,"Spec":6,"SpecialId":5,"ActivityId":1,"Id":7683,"Price":9.3,"Unit":"袋 ","Name":"佰士格果味威化饼干巧克力味90g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0937213721.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":23,"Spec":14,"SpecialId":5,"ActivityId":1,"Id":1455,"Price":43.96,"Unit":"盒 ","Name":"真巧酱心曲奇牛奶味盒装120g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1619501950.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":0,"Spec":20,"SpecialId":5,"ActivityId":1,"Id":2865,"Price":28.4,"Unit":"瓶 ","Name":"蒙牛酸酸乳原味250ml","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1647454745.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":172,"Spec":6,"SpecialId":5,"ActivityId":1,"Id":7229,"Price":14.88,"Unit":"盒 ","Name":"法丽兹柠檬香草巧克力曲奇58g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0943454345.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":388,"Spec":6,"SpecialId":5,"ActivityId":1,"Id":7689,"Price":19.98,"Unit":"袋 ","Name":"佰士格夹心威化饼干巧克力味246g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/0942404240.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":12,"Spec":1,"SpecialId":5,"ActivityId":1,"Id":8611,"Price":33.5,"Unit":"提 ","Name":"蒙牛真果粒250ml草莓味（单提12盒）","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/083800380.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":135,"Spec":10,"SpecialId":5,"ActivityId":1,"Id":9165,"Price":22.9,"Unit":"袋 ","Name":"丹鹤小米锅巴牛肉味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1423172317.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"},{"SaleFromCount":1,"Stock":375,"Spec":6,"SpecialId":5,"ActivityId":1,"Id":7240,"Price":19.92,"Unit":"盒 ","Name":"法丽兹黑糖曲奇102g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/100223223.jpg?x-oss-process=image/resize,m_pad,w_228,h_230"}]
         * banner : []
         * Title : 订货会专场
         */

        private int total;
        private int pages;
        private String Title;
        private List<ListBean> list;
        private List<?> banner;

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

        public List<?> getBanner() {
            return banner;
        }

        public void setBanner(List<?> banner) {
            this.banner = banner;
        }

        public static class ListBean {
            /**
             * SaleFromCount : 1
             * Stock : 63
             * Spec : 5
             * SpecialId : 5
             * ActivityId : 1
             * Id : 9407
             * Price : 23.9
             * Unit : 包 
             * Name : 卡奇豆香锅巴香辣味300g
             * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1721402140.jpg?x-oss-process=image/resize,m_pad,w_228,h_230
             */

            private int SaleFromCount;
            private int Stock;
            private int Spec;
            private int SpecialId;
            private int ActivityId;
            private int Id;
            private double Price;
            private String Unit;
            private String Name;
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

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public double getPrice() {
                return Price;
            }

            public void setPrice(double Price) {
                this.Price = Price;
            }

            public String getUnit() {
                return Unit;
            }

            public void setUnit(String Unit) {
                this.Unit = Unit;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getPhoto() {
                return Photo;
            }

            public void setPhoto(String Photo) {
                this.Photo = Photo;
            }
        }
    }
}
