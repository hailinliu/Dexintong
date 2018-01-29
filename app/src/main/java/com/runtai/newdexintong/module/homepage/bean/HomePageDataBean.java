package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/26time10:47
 * @detail：
 */

public class HomePageDataBean {
    /**
     * Code : 1000
     * Msg : null
     * Data : {"ads1":[{"Id":0,"Title":"苹果","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90309086.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"http://www.baidu.com","Style":"#b8e4fc","Seq":1},{"Id":0,"Title":"梨","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90303574.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"http://www.sohu.com","Style":"#ffb4b4","Seq":2},{"Id":0,"Title":"橘子","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90243733.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"http://douban.com","Style":"#b8e4fc","Seq":3},{"Id":0,"Title":"芒果","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90210395.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"http://www.a.com","Style":"#ffb4b4","Seq":4},{"Id":0,"Title":"西瓜","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90236516.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"2.com","Style":"#b8e4fc","Seq":5},{"Id":0,"Title":"1","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90201898.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"1","Style":"#ffb4b4","Seq":6}],"ads2":[{"Id":0,"Title":"天天特价","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90152837.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"1","Style":"day01","Seq":2},{"Id":0,"Title":"中鹤特供","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90145748.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"2","Style":"day02","Seq":3},{"Id":0,"Title":"沃土计划","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90138253.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"3","Style":"day03","Seq":4},{"Id":0,"Title":"新品上架","ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90128706.jpg?x-oss-process=image/resize,m_pad,w_300,h_750","ImgUrl":"4","Style":"day04","Seq":6}],"ads4":[{"Id":0,"Title":"手机版大牌驾到1","ImgPath":"默认图","ImgUrl":"http://www.baidu.com","Style":null,"Seq":1},{"Id":0,"Title":"手机版大牌驾到2","ImgPath":"默认图","ImgUrl":"http://www.baidu.com","Style":null,"Seq":2},{"Id":0,"Title":"手机版大牌驾到3","ImgPath":"默认图","ImgUrl":"http://www.baidu.com","Style":null,"Seq":3}],"ads5":[{"Id":0,"Title":"掌柜快抢","ImgPath":"默认图","ImgUrl":"http://www.baidu.com","Style":null,"Seq":1}],"ads6":[{"Id":0,"Title":"天天特价","ImgPath":"默认图","ImgUrl":"http://www.baidu.com","Style":null,"Seq":1},{"Id":0,"Title":"新品上架","ImgPath":"默认图","ImgUrl":"http://www.baidu.com","Style":null,"Seq":2},{"Id":0,"Title":"订货会","ImgPath":"默认图","ImgUrl":"http://www.baidu.com","Style":null,"Seq":3},{"Id":0,"Title":"优惠组合","ImgPath":"默认图","ImgUrl":"http://www.baidu.com","Style":null,"Seq":4}],"ads7":[],"ads8":[]}
     */

    private int Code;
    private Object Msg;
    private DataBean Data;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public Object getMsg() {
        return Msg;
    }

    public void setMsg(Object Msg) {
        this.Msg = Msg;
    }

    public DataBean getData() {
        return Data;
    }

    public void setData(DataBean Data) {
        this.Data = Data;
    }

    public static class DataBean {
        private List<Ads1Bean> ads1;
        private List<Ads2Bean> ads2;
        private List<Ads4Bean> ads4;
        private List<Ads5Bean> ads5;
        private List<Ads6Bean> ads6;
        private List<?> ads7;
        private List<?> ads8;

        public List<Ads1Bean> getAds1() {
            return ads1;
        }

        public void setAds1(List<Ads1Bean> ads1) {
            this.ads1 = ads1;
        }

        public List<Ads2Bean> getAds2() {
            return ads2;
        }

        public void setAds2(List<Ads2Bean> ads2) {
            this.ads2 = ads2;
        }

        public List<Ads4Bean> getAds4() {
            return ads4;
        }

        public void setAds4(List<Ads4Bean> ads4) {
            this.ads4 = ads4;
        }

        public List<Ads5Bean> getAds5() {
            return ads5;
        }

        public void setAds5(List<Ads5Bean> ads5) {
            this.ads5 = ads5;
        }

        public List<Ads6Bean> getAds6() {
            return ads6;
        }

        public void setAds6(List<Ads6Bean> ads6) {
            this.ads6 = ads6;
        }

        public List<?> getAds7() {
            return ads7;
        }

        public void setAds7(List<?> ads7) {
            this.ads7 = ads7;
        }

        public List<?> getAds8() {
            return ads8;
        }

        public void setAds8(List<?> ads8) {
            this.ads8 = ads8;
        }

        public static class Ads1Bean {
            /**
             * Id : 0
             * Title : 苹果
             * ImgPath : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90309086.jpg?x-oss-process=image/resize,m_pad,w_300,h_750
             * ImgUrl : http://www.baidu.com
             * Style : #b8e4fc
             * Seq : 1
             */

            private int Id;
            private String Title;
            private String ImgPath;
            private String ImgUrl;
            private String Style;
            private int Seq;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getImgPath() {
                return ImgPath;
            }

            public void setImgPath(String ImgPath) {
                this.ImgPath = ImgPath;
            }

            public String getImgUrl() {
                return ImgUrl;
            }

            public void setImgUrl(String ImgUrl) {
                this.ImgUrl = ImgUrl;
            }

            public String getStyle() {
                return Style;
            }

            public void setStyle(String Style) {
                this.Style = Style;
            }

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }
        }

        public static class Ads2Bean {
            /**
             * Id : 0
             * Title : 天天特价
             * ImgPath : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/90152837.jpg?x-oss-process=image/resize,m_pad,w_300,h_750
             * ImgUrl : 1
             * Style : day01
             * Seq : 2
             */

            private int Id;
            private String Title;
            private String ImgPath;
            private String ImgUrl;
            private String Style;
            private int Seq;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getImgPath() {
                return ImgPath;
            }

            public void setImgPath(String ImgPath) {
                this.ImgPath = ImgPath;
            }

            public String getImgUrl() {
                return ImgUrl;
            }

            public void setImgUrl(String ImgUrl) {
                this.ImgUrl = ImgUrl;
            }

            public String getStyle() {
                return Style;
            }

            public void setStyle(String Style) {
                this.Style = Style;
            }

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }
        }

        public static class Ads4Bean {
            /**
             * Id : 0
             * Title : 手机版大牌驾到1
             * ImgPath : 默认图
             * ImgUrl : http://www.baidu.com
             * Style : null
             * Seq : 1
             */

            private int Id;
            private String Title;
            private String ImgPath;
            private String ImgUrl;
            private Object Style;
            private int Seq;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getImgPath() {
                return ImgPath;
            }

            public void setImgPath(String ImgPath) {
                this.ImgPath = ImgPath;
            }

            public String getImgUrl() {
                return ImgUrl;
            }

            public void setImgUrl(String ImgUrl) {
                this.ImgUrl = ImgUrl;
            }

            public Object getStyle() {
                return Style;
            }

            public void setStyle(Object Style) {
                this.Style = Style;
            }

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }
        }

        public static class Ads5Bean {
            /**
             * Id : 0
             * Title : 掌柜快抢
             * ImgPath : 默认图
             * ImgUrl : http://www.baidu.com
             * Style : null
             * Seq : 1
             */

            private int Id;
            private String Title;
            private String ImgPath;
            private String ImgUrl;
            private Object Style;
            private int Seq;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getImgPath() {
                return ImgPath;
            }

            public void setImgPath(String ImgPath) {
                this.ImgPath = ImgPath;
            }

            public String getImgUrl() {
                return ImgUrl;
            }

            public void setImgUrl(String ImgUrl) {
                this.ImgUrl = ImgUrl;
            }

            public Object getStyle() {
                return Style;
            }

            public void setStyle(Object Style) {
                this.Style = Style;
            }

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }
        }

        public static class Ads6Bean {
            /**
             * Id : 0
             * Title : 天天特价
             * ImgPath : 默认图
             * ImgUrl : http://www.baidu.com
             * Style : null
             * Seq : 1
             */

            private int Id;
            private String Title;
            private String ImgPath;
            private String ImgUrl;
            private Object Style;
            private int Seq;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getTitle() {
                return Title;
            }

            public void setTitle(String Title) {
                this.Title = Title;
            }

            public String getImgPath() {
                return ImgPath;
            }

            public void setImgPath(String ImgPath) {
                this.ImgPath = ImgPath;
            }

            public String getImgUrl() {
                return ImgUrl;
            }

            public void setImgUrl(String ImgUrl) {
                this.ImgUrl = ImgUrl;
            }

            public Object getStyle() {
                return Style;
            }

            public void setStyle(Object Style) {
                this.Style = Style;
            }

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }
        }
    }
}
