package com.runtai.newdexintong.module.personalcenter.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.fenlei.view.FooterLayout;
import com.runtai.newdexintong.module.fenlei.view.HeaderLayout;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.personalcenter.adapter.ImperfectGoodsAdapter;
import com.runtai.newdexintong.module.personalcenter.adapter.ReplaceGoodsAdapter;
import com.runtai.newdexintong.module.personalcenter.adapter.ReturnGoodsAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.ImperfectGoodsBean;
import com.runtai.newdexintong.module.personalcenter.bean.ReplaceGoodsBean;
import com.runtai.newdexintong.module.personalcenter.bean.ReturnGoodsBean;
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
 * @date：2017/7/5time10:55
 * @detail：退货换货对应的fragment
 */

public class ReturnReplaceGoodsFragment extends BaseFragment {

    private int mPage;
    private PullToRefreshListView pullToRefreshListView;
    private List<ReturnGoodsBean.DataBean.ListBean> mRetrunData;
    /**
     * 每页显示的数据条目数量
     */
    private int pageItemCount = 20;
    private int replacePages;
    private int returnPages;
    private int imperfectPages;
    private int currentReplacePage = 2;
    private int currentReturnPage = 2;
    private int currentImperfectPage = 2;

    private int tagValue;
    private boolean isFirst = true;
    private RelativeLayout rl_no_data_show;
    private List<ImperfectGoodsBean.DataBean.ListBean> mImperfectData;
    private ReplaceGoodsBean.DataBean mReplaceGoodsBean;
    private List<ReplaceGoodsBean.DataBean.ListBean> mReplaceGoodsData;


    public static ReturnReplaceGoodsFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(AppConstant.MERCHANT_DETAILS_PAGE, page);
        ReturnReplaceGoodsFragment tripFragment = new ReturnReplaceGoodsFragment();
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

        setContentView(R.layout.fragment_order_return_replace_goods);
        View view = getContentView();
        initView(view);
        initData();
        registerListener();
    }

    private void initData() {
        if (mRetrunData == null) {
            mRetrunData = new ArrayList<>();
        }
        mRetrunData.clear();
        if (mReplaceGoodsData == null) {
            mReplaceGoodsData = new ArrayList<>();
        }
        mReplaceGoodsData.clear();
        if (mImperfectData == null) {
            mImperfectData = new ArrayList<ImperfectGoodsBean.DataBean.ListBean>();
        }
        mImperfectData.clear();
        switch (mPage) {
            case 0://退货
                currentReturnPage = 2;
                tagValue = 0;
                isFirst = true;
                returnGoodsByHttp(1);

                break;
            case 1://调货
                currentReplacePage = 2;
                tagValue = 1;
                isFirst = true;
                replaceGoodsHttp(1);

            case 2://残次
                currentImperfectPage = 2;
                tagValue = 2;
                isFirst = true;
                imperfectDataByHttp(1);
                break;

        }
    }

    /**
     * 申请残次界面
     *
     * @param imperfectPage
     */
    private void imperfectDataByHttp(int imperfectPage) {

        String url = AppConstant.BASEURL2 + "api/ret/imperfect";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(imperfectPage));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(imperfectPage))
                .addParams("Size", String.valueOf(pageItemCount))
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
                        ImperfectGoodsBean imperfectGoodsBean = gson.fromJson(strJson, ImperfectGoodsBean.class);
                        ImperfectGoodsBean.DataBean data = imperfectGoodsBean.getData();
                        mImperfectData.addAll(data.getList());
                        imperfectPages = data.getPages();
                        ImperfectGoodsAdapter imperfectGoodsAdapter = new ImperfectGoodsAdapter(getActivity(), mImperfectData);
                        pullToRefreshListView.onRefreshComplete();
                        if (isFirst) {
                            pullToRefreshListView.setAdapter(imperfectGoodsAdapter);
                            isFirst = false;
                        } else {
                            imperfectGoodsAdapter.notifyDataSetChanged();
                        }
                        int totalImperfect = data.getTotal();
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
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

    private void initView(View view) {

        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
        pullToRefreshListView.setHeaderLayout(new HeaderLayout(getActivity()));
        pullToRefreshListView.setFooterLayout(new FooterLayout(getActivity()));

        rl_no_data_show = (RelativeLayout) view.findViewById(R.id.rl_no_data_show);
    }

    private void registerListener() {

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                if (mPage == 0) {
                    //下拉刷新
                    mRetrunData.clear();
                    returnGoodsByHttp(1);
                    currentReturnPage = 2;
                } else if (mPage == 1) {
                    //下拉刷新
                    mReplaceGoodsData.clear();
                    replaceGoodsHttp(1);
                    currentReplacePage = 2;
                } else if (mPage == 2) {
                    //下拉刷新
                    mImperfectData.clear();
                    imperfectDataByHttp(1);
                    currentImperfectPage = 2;
                }
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                if (mPage == 0) {
                    if (returnPages > 0 && returnPages >= currentReturnPage) {
                        returnGoodsByHttp(currentReturnPage);
                        currentReturnPage++;
                    } else {
                        new GetDataTask().execute();
                        ToastUtil.showToast(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT);
                    }
                } else if (mPage == 1) {
                    if (replacePages > 0 && replacePages >= currentReplacePage) {
                        replaceGoodsHttp(currentReplacePage);
                        currentReplacePage++;
                    } else {
                        new GetDataTask().execute();
                        ToastUtil.showToast(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT);
                    }

                } else if (mPage == 2) {
                    if (imperfectPages > 0 && imperfectPages >= currentImperfectPage) {
                        imperfectDataByHttp(currentImperfectPage);
                        currentImperfectPage++;
                    } else {
                        new GetDataTask().execute();
                        ToastUtil.showToast(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT);
                    }
                }
            }
        });
    }


    /**
     * 调货接口处理
     *
     * @param replaceGoodsPage
     */
    private void replaceGoodsHttp(int replaceGoodsPage) {

        String url = AppConstant.BASEURL2 + "api/ret/adjust";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(replaceGoodsPage));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(replaceGoodsPage))
                .addParams("Size", String.valueOf(pageItemCount))
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
                        ReplaceGoodsBean replaceGoodsBean = gson.fromJson(strJson, ReplaceGoodsBean.class);
                        mReplaceGoodsBean = replaceGoodsBean.getData();
                        int total = mReplaceGoodsBean.getTotal();
                        replacePages = mReplaceGoodsBean.getPages();
                        mReplaceGoodsData.addAll(mReplaceGoodsBean.getList());
                        pullToRefreshListView.onRefreshComplete();
                        ReplaceGoodsAdapter replaceGoodsAdapter = new ReplaceGoodsAdapter(getActivity(), mReplaceGoodsData);
                        if (isFirst) {
                            isFirst = false;
                            pullToRefreshListView.setAdapter(replaceGoodsAdapter);
                        } else {
                            replaceGoodsAdapter.notifyDataSetChanged();
                        }

                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
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

    /**
     * 退货接口处理
     *
     * @param page
     */
    private void returnGoodsByHttp(int page) {

        String url = AppConstant.BASEURL2 + "api/ret/list";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(page));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(page))
                .addParams("Size", String.valueOf(pageItemCount))
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
                        ReturnGoodsBean replaceReturnGoodsBean = gson.fromJson(strJson, ReturnGoodsBean.class);
                        ReturnGoodsBean.DataBean data = replaceReturnGoodsBean.getData();
                        mRetrunData.addAll(data.getList());
                        if (mRetrunData.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            pullToRefreshListView.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            pullToRefreshListView.setVisibility(View.VISIBLE);
                        }
                        returnPages = data.getPages();
                        int total = data.getTotal();
                        pullToRefreshListView.onRefreshComplete();
                        ReturnGoodsAdapter adapter = new ReturnGoodsAdapter(getActivity(), mRetrunData);
                        if (isFirst) {
                            pullToRefreshListView.setAdapter(adapter);
                            isFirst = false;
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
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

    public class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pullToRefreshListView.onRefreshComplete();
            super.onPostExecute(s);

        }
    }
}
