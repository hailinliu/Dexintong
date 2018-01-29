package com.runtai.newdexintong.module.homepage.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;
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
import com.runtai.newdexintong.module.homepage.adapter.ExchangePrizeAdapter;
import com.runtai.newdexintong.module.homepage.bean.ExchangePrizeBean;
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
 * 我要兑奖界面
 */
public class ExchangePrizeActivity extends BaseActivity {

    private RelativeLayout head_back;
    private TextView tv_subtitle2;
    private RelativeLayout rl_exchange_subtitle;
    private ImageView iv_exchange_search;
    private PullToRefreshListView lv_exchange_prize;
    private List<ExchangePrizeBean> mDatas;
    private int pages;
    private List<ExchangePrizeBean.DataBean.ListBean> listData;
    private int currentPage = 2;
    /**
     * 每页显示的条目数量
     */
    private int pageItemCount = 20;
    private boolean isFirst = true;
    private RelativeLayout rl_no_data_show;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;
    private LinearLayout ll_exchange_title;
    private View view_line;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_prize);
        initView();
        doHttp(1, 0);
        registerListener();
    }

    /**
     * 联网请求兑奖列表数据数据
     */
    private void doHttp(int page, int show_sort_value) {

        if (!NetUtil.isNetworkAvailable(this)) {
            lv_exchange_prize.setVisibility(View.GONE);
            ll_exchange_title.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            lv_exchange_prize.setVisibility(View.VISIBLE);
            ll_exchange_title.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

//        参数：page int 页码默认1
//        参数：s int 每页条数默认10
//        参数：q string 检索奖项关键字
//        参数：t int 条件0显示全部奖项，2显示指定名称奖项
        String url = AppConstant.BASEURL2 + "api/change/list";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(page));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("T", String.valueOf(show_sort_value));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(page))
                .addParams("Size", String.valueOf(pageItemCount))
                .addParams("T", String.valueOf(show_sort_value))
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
                        ExchangePrizeBean exchangePrizeBean = gson.fromJson(strJson, ExchangePrizeBean.class);

                        ExchangePrizeBean.DataBean data = exchangePrizeBean.getData();
                        if (listData == null) {
                            listData = new ArrayList<ExchangePrizeBean.DataBean.ListBean>();
                        }

                        listData.addAll(data.getList());
                        if (listData.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            lv_exchange_prize.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            lv_exchange_prize.setVisibility(View.VISIBLE);
                        }
                        pages = data.getPages();
                        int total = data.getTotal();
                        lv_exchange_prize.onRefreshComplete();// 刷新UI
                        ExchangePrizeAdapter exchangePrizeAdapter = new ExchangePrizeAdapter(ExchangePrizeActivity.this, listData);
                        if (isFirst) {
                            lv_exchange_prize.setAdapter(exchangePrizeAdapter);
                            isFirst = false;
                        } else {
                            exchangePrizeAdapter.notifyDataSetChanged();
                        }

                    } else if (code == 403) {
                        DialogUtil.showDialog(ExchangePrizeActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(ExchangePrizeActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("confirm_message", e.toString());
            }
        });

    }


    private void initView() {
        setActivityTitle();
        lv_exchange_prize = (PullToRefreshListView) findViewById(R.id.lv_exchange_prize);
        lv_exchange_prize.setHeaderLayout(new HeaderLayout(this));
        lv_exchange_prize.setFooterLayout(new FooterLayout(this));
        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);

        ll_exchange_title = (LinearLayout) findViewById(R.id.ll_exchange_title);
        view_line = findViewById(R.id.view_line);
    }


    private void registerListener() {

        tv_reload.setOnClickListener(this);
        head_back.setOnClickListener(this);
        tv_subtitle2.setOnClickListener(this);
        iv_exchange_search.setOnClickListener(this);
        lv_exchange_prize.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                listData.clear();
                doHttp(1, 0);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                if (pages > 0 && pages >= currentPage) {
                    doHttp(currentPage, 0);
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(ExchangePrizeActivity.this, "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("我要兑奖");

        rl_exchange_subtitle = (RelativeLayout) findViewById(R.id.rl_exchange_subtitle);
        rl_exchange_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle2 = (TextView) findViewById(R.id.tv_subtitle2);
        tv_subtitle2.setVisibility(View.VISIBLE);
        tv_subtitle2.setText("兑奖记录");
        iv_exchange_search = (ImageView) findViewById(R.id.iv_exchange_search);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.iv_exchange_search://我要兑奖界面的搜索
                Intent intent = new Intent(this, ExchangePrizeSearchActivity.class);
                startActivityByIntent(intent);
                break;

            case R.id.tv_subtitle2://兑奖记录
                startActivityByIntent(ExchangePrizeRecordActivity.class);
                break;
            case R.id.tv_reload://重新加载
                doHttp(1, 0);
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
            lv_exchange_prize.onRefreshComplete();// 刷新UI
        }
    }


}
