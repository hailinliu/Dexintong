package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @author：rhf
 * @date：2017/7/28time15:40
 * @detail：
 */

public class WoDeJiFen_Bean {

    private String name;
    private String jifen;

    public WoDeJiFen_Bean() {

    }

    /**
     * @param name  兑换金额
     * @param jifen 所需积分
     */
    public WoDeJiFen_Bean(String name, String jifen) {
        this.name = name;
        this.jifen = jifen;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJifen() {
        return jifen;
    }

    public void setJifen(String jifen) {
        this.jifen = jifen;
    }
}
