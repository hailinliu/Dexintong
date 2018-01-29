package com.runtai.newdexintong.module.personalcenter.event;

/**
 * @author：rhf
 * @date：2017/8/19time13:42
 * @detail：
 */
public class CouponCountEvent {

    private int mCount;
    
    public CouponCountEvent(int count) {
        // TODO Auto-generated constructor stub
        mCount = count;
    }
    public int getMsg(){
        return mCount;
    }
}
