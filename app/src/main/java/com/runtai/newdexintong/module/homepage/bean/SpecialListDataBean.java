package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/19time17:04
 * @detail：
 */

public class SpecialListDataBean {


    /**
     * Code : 200
     * Data : {"list":[{"ActivityId":1,"SpecialId":6,"Activitys":[{"Spec":10,"BarginPrice":2.29,"OriginalPrice":2.29,"LeftAlone":"仅剩20件","Unit":"袋 ","ItemName":"丹鹤小米锅巴牛肉味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1423172317.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"},{"Spec":1,"BarginPrice":30.5,"OriginalPrice":33.5,"LeftAlone":"仅剩30件","Unit":"提 ","ItemName":"蒙牛真果粒250ml草莓味（单提12盒）","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/083800380.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"},{"Spec":10,"BarginPrice":2.29,"OriginalPrice":2.29,"LeftAlone":"仅剩20件","Unit":"袋 ","ItemName":"丹鹤小米锅巴麻辣味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/142101211.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"}],"ActivityName":"9.27特价"}],"banner":[{"ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20170927163759397.jpg","Title":"天天特价","Url":""}],"Title":"天天特价"}
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
         * list : [{"ActivityId":1,"SpecialId":6,"Activitys":[{"Spec":10,"BarginPrice":2.29,"OriginalPrice":2.29,"LeftAlone":"仅剩20件","Unit":"袋 ","ItemName":"丹鹤小米锅巴牛肉味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1423172317.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"},{"Spec":1,"BarginPrice":30.5,"OriginalPrice":33.5,"LeftAlone":"仅剩30件","Unit":"提 ","ItemName":"蒙牛真果粒250ml草莓味（单提12盒）","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/083800380.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"},{"Spec":10,"BarginPrice":2.29,"OriginalPrice":2.29,"LeftAlone":"仅剩20件","Unit":"袋 ","ItemName":"丹鹤小米锅巴麻辣味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/142101211.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"}],"ActivityName":"9.27特价"}]
         * banner : [{"ImgPath":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20170927163759397.jpg","Title":"天天特价","Url":""}]
         * Title : 天天特价
         */

        private String Title;
        private List<ListBean> list;
        private List<BannerBean> banner;

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
             * ActivityId : 1
             * SpecialId : 6
             * Activitys : [{"Spec":10,"BarginPrice":2.29,"OriginalPrice":2.29,"LeftAlone":"仅剩20件","Unit":"袋 ","ItemName":"丹鹤小米锅巴牛肉味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1423172317.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"},{"Spec":1,"BarginPrice":30.5,"OriginalPrice":33.5,"LeftAlone":"仅剩30件","Unit":"提 ","ItemName":"蒙牛真果粒250ml草莓味（单提12盒）","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/083800380.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"},{"Spec":10,"BarginPrice":2.29,"OriginalPrice":2.29,"LeftAlone":"仅剩20件","Unit":"袋 ","ItemName":"丹鹤小米锅巴麻辣味65g","Photo":"http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/142101211.jpg?x-oss-process=image/resize,m_pad,w_99,h_185"}]
             * ActivityName : 9.27特价
             */

            private int ActivityId;
            private int SpecialId;
            private String ActivityName;
            private List<ActivitysBean> Activitys;

            public int getActivityId() {
                return ActivityId;
            }

            public void setActivityId(int ActivityId) {
                this.ActivityId = ActivityId;
            }

            public int getSpecialId() {
                return SpecialId;
            }

            public void setSpecialId(int SpecialId) {
                this.SpecialId = SpecialId;
            }

            public String getActivityName() {
                return ActivityName;
            }

            public void setActivityName(String ActivityName) {
                this.ActivityName = ActivityName;
            }

            public List<ActivitysBean> getActivitys() {
                return Activitys;
            }

            public void setActivitys(List<ActivitysBean> Activitys) {
                this.Activitys = Activitys;
            }

            public static class ActivitysBean {
                /**
                 * Spec : 10
                 * BarginPrice : 2.29
                 * OriginalPrice : 2.29
                 * LeftAlone : 仅剩20件
                 * Unit : 袋 
                 * ItemName : 丹鹤小米锅巴牛肉味65g
                 * Photo : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/ProductData/Img/1423172317.jpg?x-oss-process=image/resize,m_pad,w_99,h_185
                 */

                private int Spec;
                private double BarginPrice;
                private double OriginalPrice;
                private String LeftAlone;
                private String Unit;
                private String ItemName;
                private String Photo;

                public int getSpec() {
                    return Spec;
                }

                public void setSpec(int Spec) {
                    this.Spec = Spec;
                }

                public double getBarginPrice() {
                    return BarginPrice;
                }

                public void setBarginPrice(double BarginPrice) {
                    this.BarginPrice = BarginPrice;
                }

                public double getOriginalPrice() {
                    return OriginalPrice;
                }

                public void setOriginalPrice(double OriginalPrice) {
                    this.OriginalPrice = OriginalPrice;
                }

                public String getLeftAlone() {
                    return LeftAlone;
                }

                public void setLeftAlone(String LeftAlone) {
                    this.LeftAlone = LeftAlone;
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

        public static class BannerBean {
            /**
             * ImgPath : http://ybl-bucket.oss-cn-beijing.aliyuncs.com/OCData/20170927163759397.jpg
             * Title : 天天特价
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
