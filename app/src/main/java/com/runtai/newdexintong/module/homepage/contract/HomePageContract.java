package com.runtai.newdexintong.module.homepage.contract;

import com.runtai.newdexintong.comment.base.BasePresenter;
import com.runtai.newdexintong.comment.base.BaseView;
import com.runtai.newdexintong.module.homepage.bean.HomePageAdsPicBean;

import java.util.List;

/**
 * Created by Administrator on 2017/4/19.
 * 首页对应的mvp管理类
 */
public class HomePageContract {

    public interface View extends BaseView<Presenter> {
        /**
         * 弹出消息提示
         *
         * @param msg
         */
        void showMessageByToast(String msg);

        /**
         * 设置轮播图图片
         */
        void setBannerData(List<HomePageAdsPicBean.DataBean.Ads1Bean.AdLocationsBean> bannerData);

        /**
         * 设置专场一
         * @param ads2
         */
        void setSpecialOne(HomePageAdsPicBean.DataBean.Ads2Bean ads2);

        /**
         * 设置专场二
         * @param ads3
         */
        void setSpecialTwo(HomePageAdsPicBean.DataBean.Ads3Bean ads3);

        /**
         * 设置专场三
         * @param ads4
         */
        void setSpecialThree(HomePageAdsPicBean.DataBean.Ads4Bean ads4);

        /**
         * 设置专场四
         * @param ads5
         */
        void setSpecialFour(HomePageAdsPicBean.DataBean.Ads5Bean ads5);

        /**
         * 设置专场五
         * @param ads6
         */
        void setSpecialFive(HomePageAdsPicBean.DataBean.Ads6Bean ads6);
    }

    public interface Presenter extends BasePresenter {

        /**
         * 获取首页广告图的数据
         */
        void httpBannerData();
    }
}
