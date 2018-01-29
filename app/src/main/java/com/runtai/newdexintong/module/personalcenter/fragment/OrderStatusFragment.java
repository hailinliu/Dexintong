package com.runtai.newdexintong.module.personalcenter.fragment;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.View;
import android.widget.AdapterView;
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
import com.runtai.newdexintong.module.personalcenter.activity.OrderDetailActivity;
import com.runtai.newdexintong.module.personalcenter.adapter.MyOrderStatusAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.MyOrderBean;
import com.runtai.newdexintong.module.personalcenter.view.timepickerview.OptionsPopupWindow;
import com.runtai.newdexintong.module.personalcenter.view.timepickerview.TimePopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/5/24.
 * 我的订单不同状态对应的fragment
 */
public class OrderStatusFragment extends BaseFragment {

    private int mPage;
    private PullToRefreshListView pullToRefreshListView;
    private List<MyOrderBean.DataBean.ListBean> list;
    /**
     * 每页显示的条目数据
     */
    private int pageItemCount = 20;
    /**
     * 数据总页数
     */
    private int pages;
    private int currentPage = 2;
    private int tValue;
    private int statusValue;
    private boolean isFirst = true;
    private RelativeLayout rl_no_data_show;
    private MyOrderStatusAdapter adapter;
    private TextView tv_reload;
    private RelativeLayout rl_no_net;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private TextView tv_search;
    private TimePopupWindow pwTime;
    private int flagValue;
    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private TextView tvOptions;

    public static OrderStatusFragment newInstance(int page) {
        Bundle args = new Bundle();
        args.putInt(AppConstant.MERCHANT_DETAILS_PAGE, page);
        OrderStatusFragment tripFragment = new OrderStatusFragment();
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
        setContentView(R.layout.fragment_order_status);

        View view = getContentView();
        initView(view);
        showLoading();
        initData();
        registerListener();
    }

    public void initData() {

        if (list == null) {
            list = new ArrayList<MyOrderBean.DataBean.ListBean>();
        }
        list.clear();
        switch (mPage) {
            case 0://全部
                tValue = 0;
                statusValue = 0;
                break;
            case 1://下单成功
                tValue = 1;
                statusValue = 1;
                break;
            case 2://拣货中
                tValue = 1;
                statusValue = 4;
                break;
            case 3://配送中
                tValue = 1;
                statusValue = 5;
                break;
            case 4://等待确认
                tValue = 1;
                statusValue = 6;
                break;
            case 5://已确认
                tValue = 1;
                statusValue = 7;
                break;

        }
        currentPage = 2;
        isFirst = true;
        httpData(1, tValue, statusValue);

    }

    private void initView(View view) {

        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
        pullToRefreshListView.setHeaderLayout(new HeaderLayout(getActivity()));
        pullToRefreshListView.setFooterLayout(new FooterLayout(getActivity()));

        rl_no_data_show = (RelativeLayout) view.findViewById(R.id.rl_no_data_show);
        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);

        tv_start_time = (TextView) view.findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) view.findViewById(R.id.tv_end_time);
        tv_search = (TextView) view.findViewById(R.id.tv_search);
        tv_start_time.setText(MyDateUtils.getSixDaysBefore());
        tv_end_time.setText(MyDateUtils.getNextDateTime(new Date()));

        // 时间选择器
        pwTime = new TimePopupWindow(getActivity(), TimePopupWindow.Type.ALL);
        // 时间选择后回调
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                if (flagValue == 10) {
                    tv_start_time.setText(getTime(date));
                } else {
                    tv_end_time.setText(getTime(date));
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

        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        //点击重新加载
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                initData();
            }
        });

        pullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getActivity(), OrderDetailActivity.class);
                intent.putExtra("orderId", String.valueOf(list.get(position - 1).getOrder().getId()));
                if (mPage == 0) {
                    SPUtils.putInt(getActivity(), "statusValue", list.get(position - 1).getOrder().getStatus());
                }
                SPUtils.putInt(getActivity(), "mPageValue", mPage);
                intent.putExtra("ordernumber", list.get(position - 1).getOrder().getOrderNum());
                intent.putExtra("ordertime", list.get(position - 1).getOrder().getCreateTime());
                startActivityByIntent(intent);
            }
        });

        //下拉刷新和上拉加载设置
        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                list.clear();
                httpData(1, tValue, statusValue);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {

                if (pages > 0 && pages >= currentPage) {
                    httpData(currentPage, tValue, statusValue);
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }

        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.tv_start_time://开始时间
                flagValue = 10;
                pwTime.showAtLocation(tv_start_time, Gravity.BOTTOM, 0, 0, MyDateUtils.getSixDaysBeforeDate());
                break;
            case R.id.tv_end_time://结束时间
                flagValue = 20;
                pwTime.showAtLocation(tv_end_time, Gravity.BOTTOM, 0, 0, MyDateUtils.getNextDate(new Date()));
                break;
            case R.id.tv_search://点击按时间段检索
                
                initData();
                break;
        }
    }

    /**
     * 获取订单数据
     *
     * @param page         读取数据的页码
     * @param t_Value      0全部，1各订单状态
     * @param status_Value 状态：0,1,4,5,6,7
     */
    private void httpData(int page, int t_Value, int status_Value) {

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

        String url = AppConstant.BASEURL2 + "api/order/list";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(page));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("T", String.valueOf(t_Value));
        map.put("Status", String.valueOf(status_Value));
        map.put("Star", StringUtil.getUrlEncodeResult(getStartTime()));
        map.put("End", StringUtil.getUrlEncodeResult(getEndTime()));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(page))
                .addParams("Size", String.valueOf(pageItemCount))
                .addParams("T", String.valueOf(t_Value))
                .addParams("Status", String.valueOf(status_Value))
                .addParams("Star", getStartTime())
                .addParams("End", getEndTime())
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
                        MyOrderBean myOrderBean = gson.fromJson(strJson, MyOrderBean.class);
                        MyOrderBean.DataBean data = myOrderBean.getData();
                        pages = data.getPages();
                        list.addAll(data.getList());
                        if (list.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            pullToRefreshListView.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            pullToRefreshListView.setVisibility(View.VISIBLE);
                        }
                        int total = data.getTotal();
                        pullToRefreshListView.onRefreshComplete();
                        adapter = new MyOrderStatusAdapter(getActivity(), list);
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


    private class MyTask extends AsyncTask<Void, Void, String> {

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

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    /**
     * 获取搜索的开始时间
     *
     * @return
     */
    public String getStartTime() {

        return tv_start_time.getText().toString().trim();
    }

    /**
     * 获取搜索的结束时间
     *
     * @return
     */
    public String getEndTime() {

        return tv_end_time.getText().toString().trim();
    }
}