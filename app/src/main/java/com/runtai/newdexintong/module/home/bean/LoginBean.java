package com.runtai.newdexintong.module.home.bean;

/**
 * @author：rhf
 * @date：2017/7/25time18:51
 * @detail：
 */

public class LoginBean {


    /**
     * Code : 200
     * Data : {"Merchant":{"Latitude":34.780266,"Longitude":113.704205,"OBalance":98877.07,"RBalance":711.01,"VisiteTime":"/Date(1507628556000)/","CreateTime":"/Date(1467129600000)/","Birth":"/Date(1467129600000)/","IsRabateConf":false,"IsSecured":false,"IsCod":true,"IsXinHu":false,"Gender":false,"StrategyCount":0,"AttrType":1,"Status":1,"JiFen":6,"Seq":0,"StockId":1,"AuthType":0,"SalesManId":216,"Lvl":2,"Area":0,"Mtype":3,"BranchId":1,"Id":4305,"Activity":null,"SecuredEndTime":null,"Token":"","StockName":"郑州总仓","AreaName":"|河南省|郑州市|金水区|","AreaId":"|410000|410100|410105|","Mobile":"18539907766","SalesMan":"销售张三","IdCard":"                  ","Addr":"黄河路与经一路交叉口东100米亚圣大厦15楼","Phone":"18939376291","Boss":"秦亚芹","Name":"测试-秦亚芹","PayPwd":"14e1b600b1fd579f47433b88e8d85291","PassWord":"14e1b600b1fd579f47433b88e8d85291","Login":"18939376291~merchant"},"Token":{"ExpiresIn":14400,"StaffId":4305,"AccessToken":"0CD9D5F6626C0FDA698FC816276B4F22B47EFA55BF91953D8DB8F5194BDBEA84813F332D11E0E45942025ADBB3B4E2273BE20B9C6E0C9160C87F4B1077D30F01F9C29BA18A340C8A27D52F5E884DA49A7A9F85602962DC2B1FCCCC81D146C5A1FEA8032D4501BBF28B37DCE5B0E34811970BA220BA5BEE49CE1FFF9DFEEDBC2F0C290A8CA597B9D1DBA0B68F9024035F8BB36C571739D787DA4559E22F0650043C62DFFD0B35B80E755731C5AA03A284BB040CCA99E20C1442937B5BA0B614348881E20E94B62034A8F3BDBDFC98FD9536BA21884E13F543BB5191B2429858B6D977D4CF94F4ACEA371777C97F131BE434A258BF63D6D1992092DF2C0777075611497548B62915803FBD9CF9AF0E9A59"}}
     * Msg : 请求(或处理)成功
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
         * Merchant : {"Latitude":34.780266,"Longitude":113.704205,"OBalance":98877.07,"RBalance":711.01,"VisiteTime":"/Date(1507628556000)/","CreateTime":"/Date(1467129600000)/","Birth":"/Date(1467129600000)/","IsRabateConf":false,"IsSecured":false,"IsCod":true,"IsXinHu":false,"Gender":false,"StrategyCount":0,"AttrType":1,"Status":1,"JiFen":6,"Seq":0,"StockId":1,"AuthType":0,"SalesManId":216,"Lvl":2,"Area":0,"Mtype":3,"BranchId":1,"Id":4305,"Activity":null,"SecuredEndTime":null,"Token":"","StockName":"郑州总仓","AreaName":"|河南省|郑州市|金水区|","AreaId":"|410000|410100|410105|","Mobile":"18539907766","SalesMan":"销售张三","IdCard":"                  ","Addr":"黄河路与经一路交叉口东100米亚圣大厦15楼","Phone":"18939376291","Boss":"秦亚芹","Name":"测试-秦亚芹","PayPwd":"14e1b600b1fd579f47433b88e8d85291","PassWord":"14e1b600b1fd579f47433b88e8d85291","Login":"18939376291~merchant"}
         * Token : {"ExpiresIn":14400,"StaffId":4305,"AccessToken":"0CD9D5F6626C0FDA698FC816276B4F22B47EFA55BF91953D8DB8F5194BDBEA84813F332D11E0E45942025ADBB3B4E2273BE20B9C6E0C9160C87F4B1077D30F01F9C29BA18A340C8A27D52F5E884DA49A7A9F85602962DC2B1FCCCC81D146C5A1FEA8032D4501BBF28B37DCE5B0E34811970BA220BA5BEE49CE1FFF9DFEEDBC2F0C290A8CA597B9D1DBA0B68F9024035F8BB36C571739D787DA4559E22F0650043C62DFFD0B35B80E755731C5AA03A284BB040CCA99E20C1442937B5BA0B614348881E20E94B62034A8F3BDBDFC98FD9536BA21884E13F543BB5191B2429858B6D977D4CF94F4ACEA371777C97F131BE434A258BF63D6D1992092DF2C0777075611497548B62915803FBD9CF9AF0E9A59"}
         */

        private MerchantBean Merchant;
        private TokenBean Token;

        public MerchantBean getMerchant() {
            return Merchant;
        }

        public void setMerchant(MerchantBean Merchant) {
            this.Merchant = Merchant;
        }

        public TokenBean getToken() {
            return Token;
        }

        public void setToken(TokenBean Token) {
            this.Token = Token;
        }

        public static class MerchantBean {
            /**
             * Latitude : 34.780266
             * Longitude : 113.704205
             * OBalance : 98877.07
             * RBalance : 711.01
             * VisiteTime : /Date(1507628556000)/
             * CreateTime : /Date(1467129600000)/
             * Birth : /Date(1467129600000)/
             * IsRabateConf : false
             * IsSecured : false
             * IsCod : true
             * IsXinHu : false
             * Gender : false
             * StrategyCount : 0
             * AttrType : 1
             * Status : 1
             * JiFen : 6
             * Seq : 0
             * StockId : 1
             * AuthType : 0
             * SalesManId : 216
             * Lvl : 2
             * Area : 0
             * Mtype : 3
             * BranchId : 1
             * Id : 4305
             * Activity : null
             * SecuredEndTime : null
             * Token : 
             * StockName : 郑州总仓
             * AreaName : |河南省|郑州市|金水区|
             * AreaId : |410000|410100|410105|
             * Mobile : 18539907766
             * SalesMan : 销售张三
             * IdCard :                   
             * Addr : 黄河路与经一路交叉口东100米亚圣大厦15楼
             * Phone : 18939376291
             * Boss : 秦亚芹
             * Name : 测试-秦亚芹
             * PayPwd : 14e1b600b1fd579f47433b88e8d85291
             * PassWord : 14e1b600b1fd579f47433b88e8d85291
             * Login : 18939376291~merchant
             */

            private double Latitude;
            private double Longitude;
            private double OBalance;
            private double RBalance;
            private String VisiteTime;
            private String CreateTime;
            private String Birth;
            private boolean IsRabateConf;
            private boolean IsSecured;
            private boolean IsCod;
            private boolean IsXinHu;
            private boolean Gender;
            private int StrategyCount;
            private int AttrType;
            private int Status;
            private int JiFen;
            private int Seq;
            private int StockId;
            private int AuthType;
            private int SalesManId;
            private int Lvl;
            private int Area;
            private int Mtype;
            private int BranchId;
            private int Id;
            private Object Activity;
            private Object SecuredEndTime;
            private String Token;
            private String StockName;
            private String AreaName;
            private String AreaId;
            private String Mobile;
            private String SalesMan;
            private String IdCard;
            private String Addr;
            private String Phone;
            private String Boss;
            private String Name;
            private String PayPwd;
            private String PassWord;
            private String Login;

            public double getLatitude() {
                return Latitude;
            }

            public void setLatitude(double Latitude) {
                this.Latitude = Latitude;
            }

            public double getLongitude() {
                return Longitude;
            }

            public void setLongitude(double Longitude) {
                this.Longitude = Longitude;
            }

            public double getOBalance() {
                return OBalance;
            }

            public void setOBalance(double OBalance) {
                this.OBalance = OBalance;
            }

            public double getRBalance() {
                return RBalance;
            }

            public void setRBalance(double RBalance) {
                this.RBalance = RBalance;
            }

            public String getVisiteTime() {
                return VisiteTime;
            }

            public void setVisiteTime(String VisiteTime) {
                this.VisiteTime = VisiteTime;
            }

            public String getCreateTime() {
                return CreateTime;
            }

            public void setCreateTime(String CreateTime) {
                this.CreateTime = CreateTime;
            }

            public String getBirth() {
                return Birth;
            }

            public void setBirth(String Birth) {
                this.Birth = Birth;
            }

            public boolean isIsRabateConf() {
                return IsRabateConf;
            }

            public void setIsRabateConf(boolean IsRabateConf) {
                this.IsRabateConf = IsRabateConf;
            }

            public boolean isIsSecured() {
                return IsSecured;
            }

            public void setIsSecured(boolean IsSecured) {
                this.IsSecured = IsSecured;
            }

            public boolean isIsCod() {
                return IsCod;
            }

            public void setIsCod(boolean IsCod) {
                this.IsCod = IsCod;
            }

            public boolean isIsXinHu() {
                return IsXinHu;
            }

            public void setIsXinHu(boolean IsXinHu) {
                this.IsXinHu = IsXinHu;
            }

            public boolean isGender() {
                return Gender;
            }

            public void setGender(boolean Gender) {
                this.Gender = Gender;
            }

            public int getStrategyCount() {
                return StrategyCount;
            }

            public void setStrategyCount(int StrategyCount) {
                this.StrategyCount = StrategyCount;
            }

            public int getAttrType() {
                return AttrType;
            }

            public void setAttrType(int AttrType) {
                this.AttrType = AttrType;
            }

            public int getStatus() {
                return Status;
            }

            public void setStatus(int Status) {
                this.Status = Status;
            }

            public int getJiFen() {
                return JiFen;
            }

            public void setJiFen(int JiFen) {
                this.JiFen = JiFen;
            }

            public int getSeq() {
                return Seq;
            }

            public void setSeq(int Seq) {
                this.Seq = Seq;
            }

            public int getStockId() {
                return StockId;
            }

            public void setStockId(int StockId) {
                this.StockId = StockId;
            }

            public int getAuthType() {
                return AuthType;
            }

            public void setAuthType(int AuthType) {
                this.AuthType = AuthType;
            }

            public int getSalesManId() {
                return SalesManId;
            }

            public void setSalesManId(int SalesManId) {
                this.SalesManId = SalesManId;
            }

            public int getLvl() {
                return Lvl;
            }

            public void setLvl(int Lvl) {
                this.Lvl = Lvl;
            }

            public int getArea() {
                return Area;
            }

            public void setArea(int Area) {
                this.Area = Area;
            }

            public int getMtype() {
                return Mtype;
            }

            public void setMtype(int Mtype) {
                this.Mtype = Mtype;
            }

            public int getBranchId() {
                return BranchId;
            }

            public void setBranchId(int BranchId) {
                this.BranchId = BranchId;
            }

            public int getId() {
                return Id;
            }

            public void setId(int Id) {
                this.Id = Id;
            }

            public Object getActivity() {
                return Activity;
            }

            public void setActivity(Object Activity) {
                this.Activity = Activity;
            }

            public Object getSecuredEndTime() {
                return SecuredEndTime;
            }

            public void setSecuredEndTime(Object SecuredEndTime) {
                this.SecuredEndTime = SecuredEndTime;
            }

            public String getToken() {
                return Token;
            }

            public void setToken(String Token) {
                this.Token = Token;
            }

            public String getStockName() {
                return StockName;
            }

            public void setStockName(String StockName) {
                this.StockName = StockName;
            }

            public String getAreaName() {
                return AreaName;
            }

            public void setAreaName(String AreaName) {
                this.AreaName = AreaName;
            }

            public String getAreaId() {
                return AreaId;
            }

            public void setAreaId(String AreaId) {
                this.AreaId = AreaId;
            }

            public String getMobile() {
                return Mobile;
            }

            public void setMobile(String Mobile) {
                this.Mobile = Mobile;
            }

            public String getSalesMan() {
                return SalesMan;
            }

            public void setSalesMan(String SalesMan) {
                this.SalesMan = SalesMan;
            }

            public String getIdCard() {
                return IdCard;
            }

            public void setIdCard(String IdCard) {
                this.IdCard = IdCard;
            }

            public String getAddr() {
                return Addr;
            }

            public void setAddr(String Addr) {
                this.Addr = Addr;
            }

            public String getPhone() {
                return Phone;
            }

            public void setPhone(String Phone) {
                this.Phone = Phone;
            }

            public String getBoss() {
                return Boss;
            }

            public void setBoss(String Boss) {
                this.Boss = Boss;
            }

            public String getName() {
                return Name;
            }

            public void setName(String Name) {
                this.Name = Name;
            }

            public String getPayPwd() {
                return PayPwd;
            }

            public void setPayPwd(String PayPwd) {
                this.PayPwd = PayPwd;
            }

            public String getPassWord() {
                return PassWord;
            }

            public void setPassWord(String PassWord) {
                this.PassWord = PassWord;
            }

            public String getLogin() {
                return Login;
            }

            public void setLogin(String Login) {
                this.Login = Login;
            }
        }

        public static class TokenBean {
            /**
             * ExpiresIn : 14400
             * StaffId : 4305
             * AccessToken : 0CD9D5F6626C0FDA698FC816276B4F22B47EFA55BF91953D8DB8F5194BDBEA84813F332D11E0E45942025ADBB3B4E2273BE20B9C6E0C9160C87F4B1077D30F01F9C29BA18A340C8A27D52F5E884DA49A7A9F85602962DC2B1FCCCC81D146C5A1FEA8032D4501BBF28B37DCE5B0E34811970BA220BA5BEE49CE1FFF9DFEEDBC2F0C290A8CA597B9D1DBA0B68F9024035F8BB36C571739D787DA4559E22F0650043C62DFFD0B35B80E755731C5AA03A284BB040CCA99E20C1442937B5BA0B614348881E20E94B62034A8F3BDBDFC98FD9536BA21884E13F543BB5191B2429858B6D977D4CF94F4ACEA371777C97F131BE434A258BF63D6D1992092DF2C0777075611497548B62915803FBD9CF9AF0E9A59
             */

            private int ExpiresIn;
            private int StaffId;
            private String AccessToken;

            public int getExpiresIn() {
                return ExpiresIn;
            }

            public void setExpiresIn(int ExpiresIn) {
                this.ExpiresIn = ExpiresIn;
            }

            public int getStaffId() {
                return StaffId;
            }

            public void setStaffId(int StaffId) {
                this.StaffId = StaffId;
            }

            public String getAccessToken() {
                return AccessToken;
            }

            public void setAccessToken(String AccessToken) {
                this.AccessToken = AccessToken;
            }
        }
    }
}
