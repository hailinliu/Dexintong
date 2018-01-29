package com.runtai.newdexintong.module.homepage.bean;

import java.util.List;

/**
 * @author：rhf
 * @date：2017/10/16time18:07
 * @detail：
 */

public class MobilePackageListBean {
    /**
     * Code : 200
     * Data : [{"CreateTime":"/Date(1508145976536)/","ReBete":0,"Amount":108,"BranchId":0,"Status":0,"ProductType":0,"Id":15,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存6个月套餐费送6个月套餐费-18元档"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":168,"BranchId":0,"Status":0,"ProductType":0,"Id":16,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存6个月套餐费送6个月套餐费-28元档"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":228,"BranchId":0,"Status":0,"ProductType":0,"Id":17,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存6个月套餐费送6个月套餐费-38元档"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":96,"BranchId":0,"Status":0,"ProductType":0,"Id":19,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存6个月套餐费送6个月套餐费-16元档"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":96,"BranchId":0,"Status":0,"ProductType":0,"Id":21,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存6个月套餐费送6个月套餐费（音乐套餐A）"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":156,"BranchId":0,"Status":0,"ProductType":0,"Id":22,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存6个月套餐费送6个月套餐费（音乐套餐B）"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":48,"BranchId":0,"Status":0,"ProductType":0,"Id":23,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存3个月套餐费送2个月套餐费-16元档"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":54,"BranchId":0,"Status":0,"ProductType":0,"Id":24,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存3个月套餐费送2个月套餐费-18元档"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":84,"BranchId":0,"Status":0,"ProductType":0,"Id":25,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存3个月套餐费送2个月套餐费-28元档"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":114,"BranchId":0,"Status":0,"ProductType":0,"Id":26,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存3个月套餐费送2个月套餐费-38元档"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":48,"BranchId":0,"Status":0,"ProductType":0,"Id":27,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存3个月套餐费送2个月套餐费（音乐套餐A）"},{"CreateTime":"/Date(1508145976537)/","ReBete":0,"Amount":78,"BranchId":0,"Status":0,"ProductType":0,"Id":28,"MainPriceId":null,"SubPriceId":"","Product":null,"Name":"动感地带存3个月套餐费送2个月套餐费（音乐套餐B）"}]
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
         * CreateTime : /Date(1508145976536)/
         * ReBete : 0
         * Amount : 108.0
         * BranchId : 0
         * Status : 0
         * ProductType : 0
         * Id : 15
         * MainPriceId : null
         * SubPriceId : 
         * Product : null
         * Name : 动感地带存6个月套餐费送6个月套餐费-18元档
         */

        private String CreateTime;
        private int ReBete;
        private int Amount;
        private int BranchId;
        private int Status;
        private int ProductType;
        private int Id;
        private Object MainPriceId;
        private String SubPriceId;
        private Object Product;
        private String Name;

        public String getCreateTime() {
            return CreateTime;
        }

        public void setCreateTime(String CreateTime) {
            this.CreateTime = CreateTime;
        }

        public int getReBete() {
            return ReBete;
        }

        public void setReBete(int ReBete) {
            this.ReBete = ReBete;
        }

        public int getAmount() {
            return Amount;
        }

        public void setAmount(int Amount) {
            this.Amount = Amount;
        }

        public int getBranchId() {
            return BranchId;
        }

        public void setBranchId(int BranchId) {
            this.BranchId = BranchId;
        }

        public int getStatus() {
            return Status;
        }

        public void setStatus(int Status) {
            this.Status = Status;
        }

        public int getProductType() {
            return ProductType;
        }

        public void setProductType(int ProductType) {
            this.ProductType = ProductType;
        }

        public int getId() {
            return Id;
        }

        public void setId(int Id) {
            this.Id = Id;
        }

        public Object getMainPriceId() {
            return MainPriceId;
        }

        public void setMainPriceId(Object MainPriceId) {
            this.MainPriceId = MainPriceId;
        }

        public String getSubPriceId() {
            return SubPriceId;
        }

        public void setSubPriceId(String SubPriceId) {
            this.SubPriceId = SubPriceId;
        }

        public Object getProduct() {
            return Product;
        }

        public void setProduct(Object Product) {
            this.Product = Product;
        }

        public String getName() {
            return Name;
        }

        public void setName(String Name) {
            this.Name = Name;
        }
    }
}
