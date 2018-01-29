package com.runtai.newdexintong.module.homepage.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
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
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.homepage.adapter.ExchangePrizeRecordAdapter;
import com.runtai.newdexintong.module.homepage.bean.ExchangePrizeRecordBean;
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
 * 兑奖记录对应的界面
 */
public class ExchangePrizeRecordActivity extends BaseActivity {

    private PullToRefreshListView lv_exchange_record;
    private RelativeLayout head_back;
    private List<ExchangePrizeRecordBean.DataBean.ListBean> mData;
    private int currentPage = 2;
    /**
     * 每页中条目数量
     */
    private int pageItemCount = 20;
    private int pages;
    private boolean isFirst = true;
    private RelativeLayout rl_no_data_show;
    private TextView tv_reload;
    private RelativeLayout rl_no_net;
    private LinearLayout ll_exchange_record_title;
    private View view_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_prize_record);
        initView();
        httpData(1);
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        lv_exchange_record = (PullToRefreshListView) findViewById(R.id.lv_exchange_record);
        lv_exchange_record.setHeaderLayout(new HeaderLayout(this));
        lv_exchange_record.setFooterLayout(new FooterLayout(this));
        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);

        ll_exchange_record_title = (LinearLayout) findViewById(R.id.ll_exchange_record_title);
        view_line = findViewById(R.id.view_line);
    }

    private void registerListener() {

        tv_reload.setOnClickListener(this);
        head_back.setOnClickListener(this);
        lv_exchange_record.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                mData.clear();
                httpData(1);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                if (pages > 0 && pages >= currentPage) {
                    httpData(currentPage);
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(ExchangePrizeRecordActivity.this, "没有更多记录了", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("兑奖记录");

    }
    

    /**
     * 请求数据
     */
    private void httpData(int page) {

        if (!NetUtil.isNetworkAvailable(this)) {
            lv_exchange_record.setVisibility(View.GONE);
            ll_exchange_record_title.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            lv_exchange_record.setVisibility(View.VISIBLE);
            ll_exchange_record_title.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/change/log";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
//        map.put("Q", "");
        map.put("Page", String.valueOf(page));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("T", "0");
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
//                .addParams("Q", "")
                .addParams("Page", String.valueOf(page))
                .addParams("Size", String.valueOf(pageItemCount))
                .addParams("T", "0")
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
                        ExchangePrizeRecordBean exchangePrizeRecordBean = gson.fromJson(strJson, ExchangePrizeRecordBean.class);
                        ExchangePrizeRecordBean.DataBean data = exchangePrizeRecordBean.getData();
                        if (mData == null) {
                            mData = new ArrayList<ExchangePrizeRecordBean.DataBean.ListBean>();
                        }
                        mData.addAll(data.getList());
                        if (mData.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            lv_exchange_record.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            lv_exchange_record.setVisibility(View.VISIBLE);
                        }
                        pages = data.getPages();
                        int total = data.getTotal();
                        lv_exchange_record.onRefreshComplete();// 刷新UI
                        ExchangePrizeRecordAdapter adapter = new ExchangePrizeRecordAdapter(ExchangePrizeRecordActivity.this, mData);

                        if (isFirst) {
                            lv_exchange_record.setAdapter(adapter);
                            isFirst = false;
                        } else {
                            adapter.notifyDataSetChanged();
                        }

                    } else if (code == 403) {
                        DialogUtil.showDialog(ExchangePrizeRecordActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(ExchangePrizeRecordActivity.this, msg, Toast.LENGTH_SHORT);
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

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_reload:
                httpData(1);
                break;

        }
    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            lv_exchange_record.onRefreshComplete();// 刷新UI
        }
    }
}
