package com.runtai.newdexintong.module.personalcenter.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.NetUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.fenlei.view.FooterLayout;
import com.runtai.newdexintong.module.fenlei.view.HeaderLayout;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.personalcenter.adapter.PreSaleOrdersAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.PreSaleOrdersBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * @author：rhf
 * @date：2017/9/23time16:03
 * @detail：我的订货会各个状态对应的fragment
 */

public class PreSaleOrderFragment extends BaseFragment {

    private int mPage;
    private PullToRefreshListView pullToRefreshListView;
    /**
     * 每页默认显示的条目数量
     */
    private int pageItemCount = 20;
    private List<PreSaleOrdersBean.DataBean.ListBean> mData;
    private int pages;
    private int currentPage = 2;
    private boolean isFirst = true;
    private RelativeLayout rl_no_data_show;
    private int tagValue = 0;
    private PreSaleOrdersAdapter preSaleOrdersAdapter;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;

    public static PreSaleOrderFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(AppConstant.MERCHANT_DETAILS_PAGE, page);
        PreSaleOrderFragment tripFragment = new PreSaleOrderFragment();
        tripFragment.setArguments(args);
        return tripFragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mPage = getArguments().getInt(AppConstant.MERCHANT_DETAILS_PAGE);

    }

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_pre_sale_order);

        View view = getContentView();
        initView(view);

        initData();
        registerListener();
    }

    private void initView(View view) {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
        pullToRefreshListView.setHeaderLayout(new HeaderLayout(getActivity()));
        pullToRefreshListView.setFooterLayout(new FooterLayout(getActivity()));

        rl_no_data_show = (RelativeLayout) view.findViewById(R.id.rl_no_data_show);
        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
    }

    private void registerListener() {
        //点击重新加载
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (mData == null) {
                    mData = new ArrayList<PreSaleOrdersBean.DataBean.ListBean>();
                }
                mData.clear();
                isFirst = true;
                currentPage = 2;
                httpData(1, tagValue);
            }
        });
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                mData.clear();
                httpData(1, tagValue);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载
                if (pages > 0 && pages >= currentPage) {
                    httpData(currentPage, tagValue);
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            pullToRefreshListView.onRefreshComplete();// 刷新UI
        }
    }


    private void initData() {
        if (mData == null) {
            mData = new ArrayList<PreSaleOrdersBean.DataBean.ListBean>();
        }
        mData.clear();
        switch (mPage) {
            case 0:
                tagValue = 0;
                break;
            case 1:
                tagValue = 1;
                break;
            case 2:
                tagValue = 2;
                break;
            case 3:
                tagValue = 3;
                break;
        }
        isFirst = true;
        currentPage = 2;
        httpData(1, tagValue);
    }

    /**
     * 请求数据
     *
     * @param page
     */
    private void httpData(int page, int flagValue) {

        if (!NetUtil.isNetworkAvailable(getActivity())) {
            pullToRefreshListView.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            pullToRefreshListView.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/dhh/list";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(page));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("T", String.valueOf(flagValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(page))
                .addParams("Size", String.valueOf(pageItemCount))
                .addParams("T", String.valueOf(flagValue))
                .addParams("Sign", signValue)
                .addParams("Timestamp", timeStamp)
                .addParams("Nonce", randomNumberTen)
                .addParams("AppId", AppConstant.appid_value)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {

                try {
                    byte[] decode = Base64.decode(response);//base64解码
                    String strJson = UTF8Util.getString(decode);
                    JSONObject jsonObject = new JSONObject(strJson);
                    int code = Integer.parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 200) {
                        Gson gson = GsonUtil.buildGson();
                        PreSaleOrdersBean preSaleOrdersBean = gson.fromJson(strJson, PreSaleOrdersBean.class);
                        PreSaleOrdersBean.DataBean data = preSaleOrdersBean.getData();
                        pages = data.getPages();
                        int total = data.getTotal();
                        mData.addAll(data.getList());
                        if (mData.size() == 0) {
                            pullToRefreshListView.setVisibility(View.GONE);
                            rl_no_data_show.setVisibility(View.VISIBLE);
                        } else {
                            pullToRefreshListView.setVisibility(View.VISIBLE);
                            rl_no_data_show.setVisibility(View.GONE);
                        }
                        pullToRefreshListView.onRefreshComplete();// 刷新UI
                        preSaleOrdersAdapter = new PreSaleOrdersAdapter(getActivity(), mData);
                        if (isFirst) {
                            isFirst = false;
                            pullToRefreshListView.setAdapter(preSaleOrdersAdapter);
                        } else {
                            preSaleOrdersAdapter.notifyDataSetChanged();
                        }
                    } else {
                        ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismissLoading();

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
                dismissLoading();
            }
        });

    }

}
