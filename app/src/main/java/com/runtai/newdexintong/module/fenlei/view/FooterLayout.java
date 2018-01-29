package com.runtai.newdexintong.module.fenlei.view;

import android.content.Context;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.LoadingLayoutBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.runtai.newdexintong.R;

/**
 * Created by dalong on 2016/11/7.
 */

public class FooterLayout extends LoadingLayoutBase {

    private LinearLayout footer_base;
    private TextView footerTv;
    private Context mContext;
    private TaoBaoView mTaoBaoView;
    private LinearLayout refresh_footer_content;
    private RotateAnimation refreshingAnimation;

    public FooterLayout(Context context) {
        this(context, PullToRefreshBase.Mode.PULL_FROM_END);
    }

    public FooterLayout(Context context, PullToRefreshBase.Mode mode) {
        super(context);
        init(context, mode);
    }

    private void init(Context mContext, PullToRefreshBase.Mode mode) {
        this.mContext = mContext;
        LayoutInflater.from(mContext).inflate(R.layout.my_refresh_footer, this);
        footer_base = (LinearLayout) findViewById(R.id.footer_base);
        refresh_footer_content = (LinearLayout) findViewById(R.id.refresh_footer_content);
        mTaoBaoView = (TaoBaoView) findViewById(R.id.taobao_view);
        footerTv = (TextView) findViewById(R.id.footer);
        refreshingAnimation = (RotateAnimation) AnimationUtils.loadAnimation(mContext, R.anim.rotating);
        LinearInterpolator lir = new LinearInterpolator();
        refreshingAnimation.setInterpolator(lir);
        mTaoBaoView.setProgress(90);
        LayoutParams lp = (LayoutParams) footer_base.getLayoutParams();
        lp.gravity = mode == PullToRefreshBase.Mode.PULL_FROM_END ? Gravity.TOP : Gravity.BOTTOM;
        reset();

    }

    @Override
    public int getContentSize() {
        return footer_base.getHeight();
    }

    /**
     * 下拉可以刷新
     */
    @Override
    public void pullToRefresh() {
        footerTv.setText("上拉加载");
        mTaoBaoView.setIsShowIcon(true,"pullUp");
    }

    /**
     * 松开后刷新
     */
    @Override
    public void releaseToRefresh() {
        footerTv.setText("松开加载");
        mTaoBaoView.setIsShowIcon(false,"pullUp");
    }

    /**
     * 下拉中
     *
     * @param scaleOfLayout scaleOfLayoutscaleOfLayout
     */
    @Override
    public void onPull(float scaleOfLayout) {
//        mTaoBaoView.setIsShowIcon(true,"pullUp");
        scaleOfLayout = scaleOfLayout > 1.0f ? 1.0f : scaleOfLayout;
        int progress = (int) ((scaleOfLayout) * 100);
        mTaoBaoView.setProgress(progress > 90 ? 90 : progress);
    }

    /**
     * 正在刷新
     */
    @Override
    public void refreshing() {
        footerTv.setText("正在加载");
        mTaoBaoView.setIsShowIcon(false,"pullUp");
        mTaoBaoView.startAnimation(refreshingAnimation);
    }

    @Override
    public void reset() {
        mTaoBaoView.clearAnimation();
    }

    @Override
    public void setPullLabel(CharSequence pullLabel) {

    }

    @Override
    public void setRefreshingLabel(CharSequence refreshingLabel) {

    }

    @Override
    public void setReleaseLabel(CharSequence releaseLabel) {

    }
}
