package com.runtai.newdexintong.module.myorder.contract;

import com.runtai.newdexintong.comment.base.BasePresenter;
import com.runtai.newdexintong.comment.base.BaseView;
import com.runtai.newdexintong.module.myorder.bean.ShoppingCartBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/17.
 * 我的订单页面对应的mvp管理类
 */
public class MyOrderContract {

    public interface View extends BaseView<Presenter> {
        /**
         * 弹出消息提示
         *
         * @param msg
         */
        void showMessageByToast(String msg);

        /**
         * 接口请求到的数据
         *
         * @param data
         */
        void setData(List<ShoppingCartBean.DataBean> data);

        /**
         * 关闭加载框
         */
        void closeLoading();

        /**
         * 提示重新登录的弹出框
         */
        void showDialogLoginAgain();

        /**
         * 有网络的显示
         */
        void haveNetShow();

        /**
         * 无网络的显示
         */
        void noNetShow();
    }

    public interface Presenter extends BasePresenter {

        /**
         * 请求网络数据
         */
        void doHttp();
    }

}
