package com.runtai.newdexintong.module.fenlei.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/7/26time19:26
 * @detail：分类中左侧列表及品牌数据
 */

public class SortLeftDataBean {


    /**
     * Code : 200
     * Data : {"left":[{"Status":true,"SubCount":6,"ParentId":0,"Id":1,"PathName":"|粮油副食|","PathID":"|1|","RewriteName":"liangyouganhuo","CateName":"粮油副食"},{"Status":true,"SubCount":2,"ParentId":1,"Id":2,"PathName":"|粮油干货|厨房调味|","PathID":"|1|2|","RewriteName":"chufangtiaowei","CateName":"厨房调味"},{"Status":true,"SubCount":0,"ParentId":2,"Id":3,"PathName":"|粮油干货|厨房调味|酱油|","PathID":"|1|2|3|","RewriteName":"jiangyou","CateName":"酱油"},{"Status":true,"SubCount":0,"ParentId":2,"Id":4,"PathName":"|粮油干货|厨房调味|黄豆酱|","PathID":"|1|2|4|","RewriteName":"huangdoujiang","CateName":"黄豆酱"},{"Status":true,"SubCount":7,"ParentId":0,"Id":6,"PathName":"|酒类商城|","PathID":"|6|","RewriteName":"jiuleishagncheng","CateName":"酒类商城"},{"Status":true,"SubCount":5,"ParentId":0,"Id":9,"PathName":"|休闲零食|","PathID":"|9|","RewriteName":"xiuxianlingshi","CateName":"休闲零食"},{"Status":true,"SubCount":1,"ParentId":9,"Id":10,"PathName":"|休闲零食|方便食品|","PathID":"|9|10|","RewriteName":"fangbianshipin","CateName":"方便食品"},{"Status":true,"SubCount":0,"ParentId":10,"Id":11,"PathName":"|休闲零食|方便食品|方便面|","PathID":"|9|10|11|","RewriteName":"fangbianmian","CateName":"方便面"},{"Status":true,"SubCount":1,"ParentId":0,"Id":12,"PathName":"|日用洗化|","PathID":"|12|","RewriteName":"riyongxihua","CateName":"日用洗化"},{"Status":true,"SubCount":1,"ParentId":12,"Id":13,"PathName":"|日用洗化|护肤用品|","PathID":"|12|13|","RewriteName":"hufuyongpin","CateName":"护肤用品"},{"Status":true,"SubCount":0,"ParentId":13,"Id":14,"PathName":"|日用洗化|护肤用品|洗面奶|","PathID":"|12|13|14|","RewriteName":"ximiannai","CateName":"洗面奶"},{"Status":true,"SubCount":1,"ParentId":0,"Id":15,"PathName":"|百货商城|","PathID":"|15|","RewriteName":"baihuoshangcheng","CateName":"百货商城"},{"Status":true,"SubCount":1,"ParentId":15,"Id":16,"PathName":"|百货商城|家居家纺|","PathID":"|15|16|","RewriteName":"jiajujiafang","CateName":"家居家纺"},{"Status":true,"SubCount":0,"ParentId":16,"Id":17,"PathName":"|百货商城|家居家纺|毛巾|","PathID":"|15|16|17|","RewriteName":"maojin","CateName":"毛巾"},{"Status":true,"SubCount":6,"ParentId":0,"Id":18,"PathName":"|饮料饮品|","PathID":"|18|","RewriteName":"yinliaoyinpiao","CateName":"饮料饮品"},{"Status":true,"SubCount":1,"ParentId":18,"Id":19,"PathName":"|饮料饮品|碳酸饮料|","PathID":"|18|19|","RewriteName":"tansuanyinliao","CateName":"碳酸饮料"},{"Status":true,"SubCount":0,"ParentId":19,"Id":20,"PathName":"|饮料饮品|碳酸饮料|芬达|","PathID":"|18|19|20|","RewriteName":"fenda","CateName":"芬达"},{"Status":true,"SubCount":3,"ParentId":9,"Id":21,"PathName":"|休闲零食|饼干/糕点|","PathID":"|9|21|","RewriteName":"binggangaodian","CateName":"饼干/糕点"},{"Status":true,"SubCount":0,"ParentId":9,"Id":22,"PathName":"|休闲零食|糖果巧克力|","PathID":"|9|22|","RewriteName":"tangguoqiaokel","CateName":"糖果巧克力"},{"Status":true,"SubCount":4,"ParentId":9,"Id":23,"PathName":"|休闲零食|休闲食品|","PathID":"|9|23|","RewriteName":"xiuxianship","CateName":"休闲食品"},{"Status":true,"SubCount":0,"ParentId":9,"Id":24,"PathName":"|休闲零食|节日礼品|","PathID":"|9|24|","RewriteName":"jierilipin","CateName":"节日礼品"},{"Status":true,"SubCount":0,"ParentId":23,"Id":25,"PathName":"|休闲零食|休闲食品|豆类食品|","PathID":"|9|23|25|","RewriteName":"douleiship","CateName":"豆类食品"},{"Status":true,"SubCount":0,"ParentId":21,"Id":27,"PathName":"|休闲零食|饼干/糕点|面包|","PathID":"|9|21|27|","RewriteName":"mianbao","CateName":"面包"},{"Status":true,"SubCount":0,"ParentId":1,"Id":29,"PathName":"|粮油副食|大米面粉|","PathID":"|1|29|","RewriteName":"damimianfen","CateName":"大米面粉"},{"Status":true,"SubCount":0,"ParentId":1,"Id":30,"PathName":"|粮油副食|食用油|","PathID":"|1|30|","RewriteName":"shiyogyou","CateName":"食用油"},{"Status":true,"SubCount":0,"ParentId":1,"Id":31,"PathName":"|粮油副食|健康杂粮|","PathID":"|1|31|","RewriteName":"jiankangzaliang","CateName":"健康杂粮"},{"Status":true,"SubCount":0,"ParentId":1,"Id":32,"PathName":"|粮油副食|菌菇干货|","PathID":"|1|32|","RewriteName":"junguganhuo","CateName":"菌菇干货"},{"Status":true,"SubCount":0,"ParentId":1,"Id":33,"PathName":"|粮油副食|方便速食|","PathID":"|1|33|","RewriteName":"fangbiansushi","CateName":"方便速食"},{"Status":true,"SubCount":0,"ParentId":18,"Id":34,"PathName":"|饮料饮品|果蔬饮料|","PathID":"|18|34|","RewriteName":"guoshuyinliao","CateName":"果蔬饮料"},{"Status":true,"SubCount":0,"ParentId":18,"Id":35,"PathName":"|饮料饮品|饮用水|","PathID":"|18|35|","RewriteName":"yinyongshui","CateName":"饮用水"},{"Status":true,"SubCount":0,"ParentId":18,"Id":36,"PathName":"|饮料饮品|茶饮料|","PathID":"|18|36|","RewriteName":"chayinliao","CateName":"茶饮料"},{"Status":true,"SubCount":0,"ParentId":18,"Id":37,"PathName":"|饮料饮品|功能饮料|","PathID":"|18|37|","RewriteName":"gongnengyinliao","CateName":"功能饮料"},{"Status":true,"SubCount":0,"ParentId":18,"Id":38,"PathName":"|饮料饮品|咖啡饮料|","PathID":"|18|38|","RewriteName":"kafeiyinliao","CateName":"咖啡饮料"},{"Status":true,"SubCount":3,"ParentId":6,"Id":39,"PathName":"|酒类商城|啤酒|","PathID":"|6|39|","RewriteName":"pijiu","CateName":"啤酒"},{"Status":true,"SubCount":0,"ParentId":6,"Id":40,"PathName":"|酒类商城|洋酒|","PathID":"|6|40|","RewriteName":"yangjiu","CateName":"洋酒"},{"Status":true,"SubCount":0,"ParentId":6,"Id":41,"PathName":"|酒类商城|黄酒|","PathID":"|6|41|","RewriteName":"huangjiu","CateName":"黄酒"},{"Status":true,"SubCount":0,"ParentId":6,"Id":42,"PathName":"|酒类商城|保健酒|","PathID":"|6|42|","RewriteName":"baojianjiu","CateName":"保健酒"},{"Status":true,"SubCount":0,"ParentId":6,"Id":43,"PathName":"|酒类商城|其他酒类|","PathID":"|6|43|","RewriteName":"qitajiulei","CateName":"其他酒类"},{"Status":true,"SubCount":0,"ParentId":6,"Id":44,"PathName":"|酒类商城|葡萄酒|","PathID":"|6|44|","RewriteName":"putaojiu","CateName":"葡萄酒"},{"Status":true,"SubCount":4,"ParentId":6,"Id":45,"PathName":"|酒类商城|白酒|","PathID":"|6|45|","RewriteName":"baijiu","CateName":"白酒"},{"Status":true,"SubCount":0,"ParentId":45,"Id":46,"PathName":"|酒类商城|白酒|牛栏山|","PathID":"|6|45|46|","RewriteName":"niulanshan","CateName":"牛栏山"},{"Status":true,"SubCount":0,"ParentId":21,"Id":47,"PathName":"|休闲零食|饼干/糕点|饼干|","PathID":"|9|21|47|","RewriteName":"zhenqiao","CateName":"饼干"},{"Status":true,"SubCount":0,"ParentId":45,"Id":48,"PathName":"|酒类商城|白酒|茅台|","PathID":"|6|45|48|","RewriteName":"maotai","CateName":"茅台"},{"Status":true,"SubCount":0,"ParentId":45,"Id":49,"PathName":"|酒类商城|白酒|五粮液|","PathID":"|6|45|49|","RewriteName":"wuliangye","CateName":"五粮液"},{"Status":true,"SubCount":0,"ParentId":45,"Id":50,"PathName":"|酒类商城|白酒|洋河|","PathID":"|6|45|50|","RewriteName":"yanghe","CateName":"洋河"},{"Status":true,"SubCount":0,"ParentId":23,"Id":52,"PathName":"|休闲零食|休闲食品|花生|","PathID":"|9|23|52|","RewriteName":"huasheng","CateName":"花生"},{"Status":true,"SubCount":0,"ParentId":39,"Id":53,"PathName":"|酒类商城|啤酒|雪花|","PathID":"|6|39|53|","RewriteName":"xuehua","CateName":"雪花"},{"Status":true,"SubCount":0,"ParentId":39,"Id":54,"PathName":"|酒类商城|啤酒|青岛|","PathID":"|6|39|54|","RewriteName":"qingdao","CateName":"青岛"},{"Status":true,"SubCount":0,"ParentId":39,"Id":55,"PathName":"|酒类商城|啤酒|百威|","PathID":"|6|39|55|","RewriteName":"baiwei","CateName":"百威"},{"Status":true,"SubCount":0,"ParentId":23,"Id":56,"PathName":"|休闲零食|休闲食品|兰花豆|","PathID":"|9|23|56|","RewriteName":"lanhuadou","CateName":"兰花豆"},{"Status":true,"SubCount":0,"ParentId":23,"Id":57,"PathName":"|休闲零食|休闲食品|肉制品|","PathID":"|9|23|57|","RewriteName":"rouzhiping","CateName":"肉制品"},{"Status":true,"SubCount":0,"ParentId":21,"Id":58,"PathName":"|休闲零食|饼干/糕点|蛋糕|","PathID":"|9|21|58|","RewriteName":"dg","CateName":"蛋糕"}],"three":[{"Id":2,"RewriteName":"chufangtiaowei","CateName":"厨房调味"},{"Id":29,"RewriteName":"damimianfen","CateName":"大米面粉"},{"Id":30,"RewriteName":"shiyogyou","CateName":"食用油"},{"Id":31,"RewriteName":"jiankangzaliang","CateName":"健康杂粮"},{"Id":32,"RewriteName":"junguganhuo","CateName":"菌菇干货"},{"Id":33,"RewriteName":"fangbiansushi","CateName":"方便速食"}],"brands":[{"Id":1,"BrandLogo":"","BrandNameEn":"taitaile","BrandNameCn":"太太乐"}]}
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
        
        private List<LeftBean> left;
        private List<ThreeBean> three;
        private List<BrandsBean> brands;

        public List<LeftBean> getLeft() {
            return left;
        }

        public void setLeft(List<LeftBean> left) {
            this.left = left;
        }

        public List<ThreeBean> getThree() {
            return three;
        }

        public void setThree(List<ThreeBean> three) {
            this.three = three;
        }

        public List<BrandsBean> getBrands() {
            return brands;
        }

        public void setBrands(List<BrandsBean> brands) {
            this.brands = brands;
        }

        public static class LeftBean {
            /**
             * Status : true
             * SubCount : 6
             * ParentId : 0
             * Id : 1
             * PathName : |粮油副食|
             * PathID : |1|
             * RewriteName : liangyouganhuo
             * CateName : 粮油副食
             */

            private boolean Status;
            private int SubCount;
            private int ParentId;
            private int Id;
            private String PathName;
            private String PathID;
            private String RewriteName;
            private String CateName;

            public boolean isStatus() {
                return Status;
            }

            public void setStatus(boolean Status) {
                this.Status = Status;
            }

            public int getSubCount() {
                return SubCount;
            }

            public void setSubCount(int SubCount) {
                this.SubCount = SubCount;
            }

            public int getParentId() {
                return ParentId;
            }

            public void setParentId(int ParentId) {
                this.ParentId = ParentId;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getPathName() {
                return PathName;
            }

            public void setPathName(String PathName) {
                this.PathName = PathName;
            }

            public String getPathID() {
                return PathID;
            }

            public void setPathID(String PathID) {
                this.PathID = PathID;
            }

            public String getRewriteName() {
                return RewriteName;
            }

            public void setRewriteName(String RewriteName) {
                this.RewriteName = RewriteName;
            }

            public String getCateName() {
                return CateName;
            }

            public void setCateName(String CateName) {
                this.CateName = CateName;
            }
        }

        public static class ThreeBean {
            /**
             * Id : 2
             * RewriteName : chufangtiaowei
             * CateName : 厨房调味
             */

            private int Id;
            private String RewriteName;
            private String CateName;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getRewriteName() {
                return RewriteName;
            }

            public void setRewriteName(String RewriteName) {
                this.RewriteName = RewriteName;
            }

            public String getCateName() {
                return CateName;
            }

            public void setCateName(String CateName) {
                this.CateName = CateName;
            }
        }

        public static class BrandsBean {
            /**
             * Id : 1
             * BrandLogo : 
             * BrandNameEn : taitaile
             * BrandNameCn : 太太乐
             */

            private int Id;
            private String BrandLogo;
            private String BrandNameEn;
            private String BrandNameCn;

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public String getBrandLogo() {
                return BrandLogo;
            }

            public void setBrandLogo(String BrandLogo) {
                this.BrandLogo = BrandLogo;
            }

            public String getBrandNameEn() {
                return BrandNameEn;
            }

            public void setBrandNameEn(String BrandNameEn) {
                this.BrandNameEn = BrandNameEn;
            }

            public String getBrandNameCn() {
                return BrandNameCn;
            }

            public void setBrandNameCn(String BrandNameCn) {
                this.BrandNameCn = BrandNameCn;
            }
        }
    }
}
