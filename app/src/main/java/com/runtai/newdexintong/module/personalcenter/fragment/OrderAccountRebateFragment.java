package com.runtai.newdexintong.module.personalcenter.fragment;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
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
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.personalcenter.adapter.OrderAccountRebateAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.OrderAccountRebateBean;
import com.runtai.newdexintong.module.personalcenter.view.timepickerview.OptionsPopupWindow;
import com.runtai.newdexintong.module.personalcenter.view.timepickerview.TimePopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * @author：rhf
 * @date：2017/11/12time19:06
 * @detail：订货账户返利
 */

public class OrderAccountRebateFragment extends BaseFragment {

    private TextView tv_reload;
    private PullToRefreshListView mPullToRefreshListView;
    private RelativeLayout rl_no_net;
    private RelativeLayout rl_no_data_show;
    private RelativeLayout rl_have_net;
    private boolean isFirst = true;
    private int pageSize = 20;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private TextView tv_search;
    private int flagValue;
    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private TextView tvOptions;
    private TimePopupWindow pwTime;
    private List<OrderAccountRebateBean.DataBean.ListBean> mData;
    private int pages;
    private int currentPage = 2;
    private RelativeLayout rl_section_search;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_recharge_account_query);
        View view = getContentView();
        initView(view);
        registerListener();
        httpData("1");
    }

    private void initView(View view) {
        rl_have_net = (RelativeLayout) view.findViewById(R.id.rl_have_net);
        rl_no_data_show = (RelativeLayout) view.findViewById(R.id.rl_no_data_show);
        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        mPullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.mPullToRefreshListView);
        mPullToRefreshListView.setHeaderLayout(new HeaderLayout(getActivity()));
        mPullToRefreshListView.setFooterLayout(new FooterLayout(getActivity()));

        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_start_time.setText(MyDateUtils.getTodayTime());
        tv_end_time.setText(MyDateUtils.getNextDateTime(new Date()));
        rl_section_search = (RelativeLayout) view.findViewById(R.id.rl_section_search);

        // 时间选择器
        pwTime = new TimePopupWindow(getActivity(), TimePopupWindow.Type.ALL);
        // 时间选择后回调
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if (flagValue == 10) {
                    tv_start_time.setText(MyDateUtils.getTime(date));
                } else {
                    tv_end_time.setText(MyDateUtils.getTime(date));
                }

            }
        });
        // 选项选择器
        OptionsPopupWindow pwOptions = new OptionsPopupWindow(getActivity());

        // 监听确定选择按钮
        pwOptions
                .setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2,
                                                int options3) {
                        // 返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1)
                                + options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2)
                                .get(options3);
                        tvOptions.setText(tx);
                    }
                });
    }

    private void registerListener() {
        tv_reload.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mData.clear();
                httpData("1");
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                if (pages > 0 && pages >= currentPage) {
                    httpData(String.valueOf(currentPage));
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private class MyTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

            mPullToRefreshListView.onRefreshComplete();
            super.onPostExecute(s);

        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_reload://重新加载
                httpData("1");
                break;
            case R.id.tv_start_time://开始时间
                flagValue = 10;
                pwTime.showAtLocation(tv_start_time, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.tv_end_time://结束时间
                flagValue = 20;
                pwTime.showAtLocation(tv_end_time, Gravity.BOTTOM, 0, 0, MyDateUtils.getNextDate(new Date()));
                break;
            case R.id.tv_search://按时间段检索
                mData.clear();
                currentPage = 2;
                isFirst = true;
                httpData("1");
                break;
        }
    }

    private void httpData(String page) {

        if (!NetUtil.isNetworkAvailable(getActivity())) {
            rl_have_net.setVisibility(View.GONE);
            rl_section_search.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            rl_have_net.setVisibility(View.VISIBLE);
            rl_section_search.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/cw/roacct";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", page);
        map.put("Size", String.valueOf(pageSize));
        map.put("Star", StringUtil.getUrlEncodeResult(getStartTime()));
        map.put("End", StringUtil.getUrlEncodeResult(getEndTime()));
        map.put("T", "2");
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", page)
                .addParams("Size", String.valueOf(pageSize))
                .addParams("Star", getStartTime())
                .addParams("End", getEndTime())
                .addParams("T", "2")
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
                        OrderAccountRebateBean orderAccountRebateBean = gson.fromJson(strJson, OrderAccountRebateBean.class);
                        OrderAccountRebateBean.DataBean data = orderAccountRebateBean.getData();
                        if (mData == null) {
                            mData = new ArrayList<OrderAccountRebateBean.DataBean.ListBean>();
                        }
                        mData.addAll(data.getList());
                        pages = data.getPages();
                        int total = data.getTotal();
                        if (mData.size() == 0) {
                            mPullToRefreshListView.setVisibility(View.GONE);
                            rl_no_data_show.setVisibility(View.VISIBLE);
                        } else {
                            mPullToRefreshListView.setVisibility(View.VISIBLE);
                            rl_no_data_show.setVisibility(View.GONE);
                        }
                        OrderAccountRebateAdapter adapter = new OrderAccountRebateAdapter(getActivity(), mData);
                        mPullToRefreshListView.onRefreshComplete();// 刷新UI
                        if (isFirst) {
                            isFirst = false;
                            mPullToRefreshListView.setAdapter(adapter);
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

    /**
     * 获取开始时间
     *
     * @return
     */
    public String getStartTime() {
        return tv_start_time.getText().toString().trim();
    }

    /**
     * 获取结束时间
     *
     * @return
     */
    public String getEndTime() {
        return tv_end_time.getText().toString().trim();
    }
}
