package com.runtai.newdexintong.module.homepage.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
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
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.homepage.adapter.ScanResultListAdapter;
import com.runtai.newdexintong.module.homepage.bean.SearchResultBean;
import com.runtai.newdexintong.module.homepage.utils.GetCartTotalNumUtil;
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
 * 扫描结果页面
 */
public class ScanResultActivity extends BaseActivity implements ScanResultListAdapter.IupdateCartTotalNumber {

    private RelativeLayout head_back;
    private ImageView iv_shoppingcar;
    private TextView tv_goods_amount;
    private PullToRefreshListView lv_scan_result;
    private String resultData;
    private int pageItemCount = 20;
    private RelativeLayout rl_no_data_show;
    private List<SearchResultBean.DataBean.ListBean> mScanData;
    private boolean isFirst = true;
    private int currentPage = 2;
    private int pages;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;
    private RelativeLayout rl_have_net;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scan_result);
        GetCartTotalNumUtil.getShoppingCartNumber(this);
        resultData = getIntent().getStringExtra("result");
//        resultData = "6923644283582";
        initView();
        httpData(String.valueOf(1));
        registerListener();
    }

    private void initView() {

        setActivityTitle();
        lv_scan_result = (PullToRefreshListView) findViewById(R.id.lv_scan_result);
        lv_scan_result.setFooterLayout(new FooterLayout(this));
        lv_scan_result.setHeaderLayout(new HeaderLayout(this));
        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        iv_shoppingcar = (ImageView) findViewById(R.id.iv_shoppingcar);
        tv_goods_amount = (TextView) findViewById(R.id.tv_goods_amount);
        String totalNumber = SPUtils.getString(this, "TotalNumber", "");
        if (!"".equals(totalNumber) && totalNumber.length() > 0) {
            tv_goods_amount.setText(totalNumber);
        }
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
        tv_reload = (TextView)findViewById(R.id.tv_reload);
        rl_have_net = (RelativeLayout) findViewById(R.id.rl_have_net);
    }

    /**
     * 获取扫描结果的值
     */
    private void httpData(String pageValue) {

        if (!NetUtil.isNetworkAvailable(this)) {
            rl_have_net.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            rl_have_net.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }
        /**
         * q string 搜索关键字，不能空
         bid int 品牌编号，默认0
         sort int 排序号销量排序由高到低1，由低到高2；价格排序由高到低3，由低到高4，默认0
         s int 每页显示数量，默认3
         page int 页码，默认1
         */

        String url = AppConstant.BASEURL2 + "api/product/search";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Q", resultData);
        map.put("Bid", "0");
        map.put("Sort", "0");
        map.put("Page", String.valueOf(pageValue));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Q", resultData)
                .addParams("Bid", "0")
                .addParams("Sort", "0")
                .addParams("Page", String.valueOf(pageValue))
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
                        SearchResultBean searchResultBean = gson.fromJson(strJson, SearchResultBean.class);
                        if (searchResultBean != null) {
                            SearchResultBean.DataBean data = searchResultBean.getData();
                            pages = data.getPages();
                            int total = data.getTotal();
                            if (mScanData == null) {
                                mScanData = new ArrayList<SearchResultBean.DataBean.ListBean>();
                            }
                            mScanData.addAll(data.getList());
                            if (mScanData.size() == 0) {
                                lv_scan_result.setVisibility(View.GONE);
                                rl_no_data_show.setVisibility(View.VISIBLE);
                            } else {
                                lv_scan_result.setVisibility(View.VISIBLE);
                                rl_no_data_show.setVisibility(View.GONE);
                            }
                            lv_scan_result.onRefreshComplete();// 停止刷新
                            ScanResultListAdapter adapter = new ScanResultListAdapter(ScanResultActivity.this, mScanData);
                            if (isFirst) {
                                lv_scan_result.setAdapter(adapter);
                                isFirst = false;
                            } else {
                                adapter.notifyDataSetChanged();
                            }
                            adapter.setIupdateCartTotalNumber(ScanResultActivity.this);
                        }
                    } else if (code == 403) {
                        DialogUtil.showDialog(ScanResultActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(ScanResultActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 设置界面标题栏
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("扫描结果");
    }

    private void registerListener() {

        tv_reload.setOnClickListener(this);
        head_back.setOnClickListener(this);
        iv_shoppingcar.setOnClickListener(this);
        lv_scan_result.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mScanData.clear();
                httpData("1");
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (pages > 0 && pages >= currentPage) {
                    httpData(String.valueOf(currentPage));
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(ScanResultActivity.this, "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });

        //条目点击跳转到商品详情
        lv_scan_result.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

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
            case R.id.iv_shoppingcar://点击购物车图标
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("tag", "SearchResultAcivity");
                startActivityByIntent(intent);
                finish();
                break;
            case R.id.tv_reload://重新加载
                httpData("1");
                break;

        }
    }

    @Override
    public void updateTotalNum(String number) {
        tv_goods_amount.setText(number);
    }


    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            lv_scan_result.onRefreshComplete();// 刷新UI
        }
    }
}
