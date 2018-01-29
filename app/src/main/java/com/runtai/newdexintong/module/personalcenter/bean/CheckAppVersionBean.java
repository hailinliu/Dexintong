package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @author：rhf
 * @date：2017/10/26time13:40
 * @detail：
 */

public class CheckAppVersionBean {


    /**
     * Code : 200
     * Data : {"url":"http://rt.api.cndnet.cn/content/apk/yblb2b.apk","desc":"1.Android更新服务器地址","code":"1.0"}
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
         * url : http://rt.api.cndnet.cn/content/apk/yblb2b.apk
         * desc : 1.Android更新服务器地址
         * code : 1.0
         */

        private String url;
        private String desc;
        private String code;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
