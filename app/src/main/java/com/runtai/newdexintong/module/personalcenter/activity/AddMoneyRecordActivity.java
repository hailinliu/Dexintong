package com.runtai.newdexintong.module.personalcenter.activity;

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
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.personalcenter.adapter.AddMoneyRecordAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.AddMoneyRecordBean;
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
 * 加款记录
 */
public class AddMoneyRecordActivity extends BaseActivity {

    private RelativeLayout rl_have_net;
    private RelativeLayout rl_no_data_show;
    private RelativeLayout rl_no_net;
    private PullToRefreshListView mPullToRefreshListView;
    private TextView tv_reload;
    private int pageSize = 20;
    private int currentPage = 2;
    private List<AddMoneyRecordBean.DataBean.ListBean> mData;
    private boolean isFirst = true;

    private int pages;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_money_record);

        initView();
        registerListener();
        httpData("1");

    }

    private void initView() {

        setActivtiyTitle();
        rl_have_net = (RelativeLayout) findViewById(R.id.rl_have_net);
        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.mPullToRefreshListView);
        mPullToRefreshListView.setHeaderLayout(new HeaderLayout(this));
        mPullToRefreshListView.setFooterLayout(new FooterLayout(this));

        tv_reload = (TextView) findViewById(R.id.tv_reload);
    }

    private void registerListener() {
        
        tv_reload.setOnClickListener(this);
        mPullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                mData.clear();
                httpData("1");
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载
                if (pages > 0 && pages >= currentPage) {

                    httpData(String.valueOf(currentPage));
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(AddMoneyRecordActivity.this, "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void httpData(String page) {

        if (!NetUtil.isNetworkAvailable(this)) {
            rl_have_net.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            rl_have_net.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/store/recharge";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", page);
        map.put("Size", String.valueOf(pageSize));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", page)
                .addParams("Size", String.valueOf(pageSize))
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
                        AddMoneyRecordBean addMoneyRecordBean = gson.fromJson(strJson, AddMoneyRecordBean.class);
                        AddMoneyRecordBean.DataBean data = addMoneyRecordBean.getData();
                        if (mData == null) {
                            mData = new ArrayList<AddMoneyRecordBean.DataBean.ListBean>();
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
                        AddMoneyRecordAdapter adapter = new AddMoneyRecordAdapter(AddMoneyRecordActivity.this, mData);
                        mPullToRefreshListView.onRefreshComplete();// 刷新UI
                        if (isFirst) {
                            isFirst = false;
                            mPullToRefreshListView.setAdapter(adapter);
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        
                    } else if (code == 403) {
                        DialogUtil.showDialog(AddMoneyRecordActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(AddMoneyRecordActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 设置界面标题
     */
    private void setActivtiyTitle() {

        RelativeLayout head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("加款记录");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_reload://重新加载
                httpData("1");
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
            mPullToRefreshListView.onRefreshComplete();// 刷新UI
        }
    }
}
