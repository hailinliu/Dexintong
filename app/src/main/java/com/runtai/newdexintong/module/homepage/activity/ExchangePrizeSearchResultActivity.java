package com.runtai.newdexintong.module.homepage.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
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
 * 兑奖对应的搜索结果页面
 */
public class ExchangePrizeSearchResultActivity extends BaseActivity {

    private RelativeLayout head_back;
    private EditText et_search2;
    private PullToRefreshListView lv_exchange_search_result;
    List<ExchangePrizeBean> mDatas;
    private String goodsname;
    private List<ExchangePrizeBean.DataBean.ListBean> listData;
    private int pages;
    private int currentPage = 2;
    /**
     * 每页显示的条目数量
     */
    private int pageItemCount = 20;
    private RelativeLayout rl_no_data_show;
    private View view_line;
    private LinearLayout ll_exchange_search_result_title;
    private TextView tv_reload;
    private RelativeLayout rl_no_net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_prize_search_result);

        goodsname = getIntent().getStringExtra("goodsname");
        initView();
        doHttp(1, goodsname, 2);
        registerListener();
    }

    /**
     * 请求数据
     */
    private void doHttp(int page, String searchKey, int show_sort_value) {

        if (!NetUtil.isNetworkAvailable(this)) {
            lv_exchange_search_result.setVisibility(View.GONE);
            ll_exchange_search_result_title.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            lv_exchange_search_result.setVisibility(View.VISIBLE);
            ll_exchange_search_result_title.setVisibility(View.VISIBLE);
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
        map.put("Q", StringUtil.getUrlEncodeResult(searchKey));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(page))
                .addParams("Size", String.valueOf(pageItemCount))
                .addParams("T", String.valueOf(show_sort_value))
                .addParams("Q", searchKey)
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
                            lv_exchange_search_result.setVisibility(View.GONE);
                            rl_no_data_show.setVisibility(View.VISIBLE);
                        } else {
                            lv_exchange_search_result.setVisibility(View.VISIBLE);
                            rl_no_data_show.setVisibility(View.GONE);
                        }
                        pages = data.getPages();
                        int total = data.getTotal();
                        lv_exchange_search_result.onRefreshComplete();// 刷新UI
                        ExchangePrizeAdapter exchangePrizeAdapter = new ExchangePrizeAdapter(ExchangePrizeSearchResultActivity.this, listData);
                        lv_exchange_search_result.setAdapter(exchangePrizeAdapter);

                    } else {
                        ToastUtil.showToast(ExchangePrizeSearchResultActivity.this, msg, Toast.LENGTH_SHORT);
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
        et_search2 = (EditText) findViewById(R.id.et_search2);
        et_search2.setFocusable(false);
        et_search2.setText(goodsname);

        lv_exchange_search_result = (PullToRefreshListView) findViewById(R.id.lv_exchange_search_result);
        lv_exchange_search_result.setHeaderLayout(new HeaderLayout(this));
        lv_exchange_search_result.setFooterLayout(new FooterLayout(this));

        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);

        ll_exchange_search_result_title = (LinearLayout)
                findViewById(R.id.ll_exchange_search_result_title);
        view_line = findViewById(R.id.view_line);
    }


    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setVisibility(View.GONE);
        RelativeLayout rl_search_result = (RelativeLayout) findViewById(R.id.rl_search_result);
        rl_search_result.setVisibility(View.VISIBLE);
        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.GONE);
    }

    private void registerListener() {
        tv_reload.setOnClickListener(this);
        head_back.setOnClickListener(this);
        et_search2.setOnClickListener(this);

        lv_exchange_search_result.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                //下拉刷新
                listData.clear();
                doHttp(1, goodsname, 2);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                //上拉加载更多
                if (pages > 0 && pages >= currentPage) {
                    doHttp(currentPage, goodsname, 2);
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(ExchangePrizeSearchResultActivity.this, "没有更多订单了...", Toast.LENGTH_SHORT);
                }
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
            case R.id.et_search2:
                onBackPressed();
                break;
            case R.id.tv_reload:
                doHttp(1, goodsname, 2);
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
            lv_exchange_search_result.onRefreshComplete();// 刷新UI
        }
    }
}
