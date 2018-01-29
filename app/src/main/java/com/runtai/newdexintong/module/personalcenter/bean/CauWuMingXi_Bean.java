package com.runtai.newdexintong.module.personalcenter.bean;

/**
 * @作者：高炎鹏
 * @日期：2017/2/14时间10:09
 * @描述：财务明细实体
 */
public class CauWuMingXi_Bean {

    private String cwmx_time;
    private int cwmx_type;
    private String cwmx_account;
    private String cwmx_account_num;
    private String cwmx_money;

    public CauWuMingXi_Bean(){

    }

    /**
     * @param cwmx_time         财务明细时间
     * @param cwmx_type         财务明细类型
     * @param cwmx_account      财务明细支付类型
     * @param cwmx_account_num  财务明细支付账号
     * @param cwmx_money        财务明细支付金额
     */
    public CauWuMingXi_Bean(String cwmx_time, int cwmx_type, String cwmx_account, String cwmx_account_num, String cwmx_money) {
        this.cwmx_time = cwmx_time;
        this.cwmx_type = cwmx_type;
        this.cwmx_account = cwmx_account;
        this.cwmx_account_num = cwmx_account_num;
        this.cwmx_money = cwmx_money;
    }

    public String getCwmx_time() {
        return cwmx_time;
    }

    public void setCwmx_time(String cwmx_time) {
        this.cwmx_time = cwmx_time;
    }

    public int getCwmx_type() {
        return cwmx_type;
    }

    public void setCwmx_type(int cwmx_type) {
        this.cwmx_type = cwmx_type;
    }

    public String getCwmx_account() {
        return cwmx_account;
    }

    public void setCwmx_account(String cwmx_account) {
        this.cwmx_account = cwmx_account;
    }

    public String getCwmx_account_num() {
        return cwmx_account_num;
    }

    public void setCwmx_account_num(String cwmx_account_num) {
        this.cwmx_account_num = cwmx_account_num;
    }

    public String getCwmx_money() {
        return cwmx_money;
    }

    public void setCwmx_money(String cwmx_money) {
        this.cwmx_money = cwmx_money;
    }

}
