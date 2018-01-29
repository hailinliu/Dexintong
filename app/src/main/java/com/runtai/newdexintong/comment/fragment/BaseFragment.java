package com.runtai.newdexintong.comment.fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.umeng.analytics.MobclickAgent;

import java.lang.reflect.Field;

public class BaseFragment extends Fragment implements View.OnClickListener {

    public final String TAG = getClass().getSimpleName();
    private boolean debugLifeCycler = false;

    public boolean isCurrentStart = false;
    protected View contentView;
    protected LayoutInflater inflater;
    protected ViewGroup container;

    protected Resources res;
    private Context ctx;
    private ProgressDialog mLoading;

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (debugLifeCycler)

            System.out.println(TAG + "：onAttach");

    }

    @Override
    public void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        if (debugLifeCycler) {

            System.out.println(TAG + "：onCreate");
        }
        ctx = getActivity();
        res = ctx.getResources();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        
        if (debugLifeCycler)
            System.out.println(TAG + "：onActivityCreated");

    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (debugLifeCycler) System.out.println(TAG + "：onDestroy");
    }


    @Override
    public void onStart() {
        super.onStart();
        if (debugLifeCycler) System.out.println(TAG + "：onStart");
    }

    @Override
    public void onStop() {
        super.onStop();
        if (debugLifeCycler) System.out.println(TAG + "：onStop");
    }


    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPageEnd(TAG + "onPageEnd");
        if (debugLifeCycler) System.out.println(TAG + "：onPause");
    }


    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onPageStart(TAG + "onPageStart");
        if (debugLifeCycler) System.out.println(TAG + "：onResume");
    }


    @Override
    public void onDetach() {
        super.onDetach();
        try {
            Field childFragmentManager = Fragment.class
                    .getDeclaredField("mChildFragmentManager");
            childFragmentManager.setAccessible(true);
            childFragmentManager.set(this, null);

        } catch (NoSuchFieldException e) {
            throw new RuntimeException(e);
        } catch (IllegalAccessException e) {
            throw new RuntimeException(e);
        }
        if (debugLifeCycler) System.out.println(TAG + "：onDetach");
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        this.inflater = inflater;
        this.container = container;
        onCreateView(savedInstanceState);
        if (contentView == null) {

            return super.onCreateView(inflater, container, savedInstanceState);
        }

        return contentView;

    }

    protected void onCreateView(Bundle savedInstanceState) {
    }


    public void setContentView(int layoutResID) {
        setContentView((ViewGroup) inflater.inflate(layoutResID, container,
                false));
    }

    public void setContentView(View view) {
        contentView = view;
    }

    public View getContentView() {
        return contentView;
    }


    public View findViewById(int id) {
        if (contentView != null)
            return contentView.findViewById(id);
        return null;
    }


    @Override
    public void onDestroyView() {
        super.onDestroyView();

        contentView = null;
        container = null;
        inflater = null;

        if (debugLifeCycler) {

            System.out.println(TAG + "：onDestroyView");
        }

    }


    public void onFragmentStart() {

        if (debugLifeCycler) System.out.println(TAG + "：onFragmentStart");
        isCurrentStart = true;

    }

    public void onFragmentStop() {
        if (debugLifeCycler) System.out.println(TAG + "：onFragmentStop");
        isCurrentStart = false;
    }

    public void showLog(String msg) {
        LogUtil.i(TAG, msg);
    }

    protected void showLoading(String msg) {
        if (mLoading != null) {
            mLoading.dismiss();
        }
        if (getActivity() != null) {

            mLoading = new ProgressDialog(getActivity());
        }

        mLoading.setMessage(msg);
        mLoading.show();
    }

    /**
     * 显示加载数据的进度框
     */
    protected void showLoading() {
        showLoading("正在加载中，请稍候...");
    }

    /**
     * 关闭加载数据的进度框
     */
    protected void dismissLoading() {
        if (mLoading != null) {
            mLoading.dismiss();
        }
    }


    @Override
    public void onClick(View v) {

    }

    /**
     * 带有动画的Intent跳转
     *
     * @param
     * @param
     */
    public void startActivityByIntent(Class<? extends BaseCommonActivity> clazz) {
        startActivity(new Intent(getActivity(), clazz));
        /** <<<------右入左出 */
        getActivity().overridePendingTransition(R.anim.slide_in_right, R.anim.slide_out_left);
    }

    public void startActivityByIntent(Intent intent) {
        startActivity(intent);
        getActivity().overridePendingTransition(R.anim.base_slide_right_in, R.anim.base_slide_remain);
    }
}
