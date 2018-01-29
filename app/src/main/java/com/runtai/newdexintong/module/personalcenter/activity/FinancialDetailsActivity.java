package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
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
import com.runtai.newdexintong.module.fenlei.adapter.FenleiShaiXuanAdapter;
import com.runtai.newdexintong.module.fenlei.view.FooterLayout;
import com.runtai.newdexintong.module.fenlei.view.HeaderLayout;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.personalcenter.adapter.FinancialDetailsAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.FinancialDetailsBean;
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
 * @作者：gyp
 * @日期：2017/2/14时间09:42
 * @描述：财务明细
 */

public class FinancialDetailsActivity extends BaseActivity {

    private RelativeLayout cwmx_head;
    private RelativeLayout rl_title;
    private PullToRefreshListView cwmx_listview;
    private LinearLayout cwmx_ll_hasdata, cwmx_ll_nodata;
    private FinancialDetailsAdapter adapter;
    private List<FinancialDetailsBean.DataBean.ListBean> mData;

    private RelativeLayout head_back;
    /**
     * 每个页面中的条目数量
     */
    private int pageItemCount = 20;
    private int pages;
    private boolean isFirst = true;
    private int currentPage = 2;
    private RelativeLayout rl_no_data_show;
    private RelativeLayout rl_no_net;
    private RelativeLayout rl_have_net;
    private TextView tv_reload;
    private TextView tv_start_time;
    private TextView tv_end_time;
    private TextView tv_search;
    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private TextView tvOptions;
    private TimePopupWindow pwTime;
    private int flagValue;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_caiwumingxi);
        showLoading();
        initView();
        initData();

        httpData(1, 0);
//        setDate();
        initListener();
    }

    private void httpData(int page, int categoryId) {

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

        String url = AppConstant.BASEURL2 + "api/cw/list";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("T", String.valueOf(categoryId));
        map.put("Page", String.valueOf(page));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Star", StringUtil.getUrlEncodeResult(getStartTime()));
        map.put("End", StringUtil.getUrlEncodeResult(getEndTime()));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("T", String.valueOf(categoryId))
                .addParams("Page", String.valueOf(page))
                .addParams("Size", String.valueOf(pageItemCount))
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
                        FinancialDetailsBean financialDetailsBean = gson.fromJson(strJson, FinancialDetailsBean.class);
                        FinancialDetailsBean.DataBean data = financialDetailsBean.getData();
                        int total = data.getTotal();
                        pages = data.getPages();

                        mData.addAll(data.getList());
                        if (mData.size() == 0) {
                            cwmx_listview.setVisibility(View.GONE);
                            rl_no_data_show.setVisibility(View.VISIBLE);
                        } else {
                            cwmx_listview.setVisibility(View.VISIBLE);
                            rl_no_data_show.setVisibility(View.GONE);
                        }
                        cwmx_listview.onRefreshComplete();// 刷新UI
                        adapter = new FinancialDetailsAdapter(FinancialDetailsActivity.this, mData);
                        if (isFirst) {
                            cwmx_listview.setAdapter(adapter);
                            isFirst = false;
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                    } else if (code == 403) {
                        DialogUtil.showDialog(FinancialDetailsActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(FinancialDetailsActivity.this, msg, Toast.LENGTH_SHORT);
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

    private void initListener() {

        tv_reload.setOnClickListener(this);
        tv_start_time.setOnClickListener(this);
        tv_end_time.setOnClickListener(this);
        tv_search.setOnClickListener(this);

        cwmx_listview.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 下拉刷新
                mData.clear();
                httpData(1, popPositiong);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                // 上拉加载
                if (pages > 0 && pages >= currentPage) {

                    httpData(currentPage, popPositiong);
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(FinancialDetailsActivity.this, "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    private void initData() {

        if (pop_list == null) {
            pop_list = new ArrayList<>();
        }
//        0加款，1退款，2进货，3移动，4联通，5电信，6存送套餐，7固话，8流量，9Q币，11水电燃
        pop_list.add("加款");
        pop_list.add("退款");
        pop_list.add("进货");
        pop_list.add("移动");
        pop_list.add("联通");
        pop_list.add("电信");
        pop_list.add("存送套餐");
        pop_list.add("固话");
        pop_list.add("流量");
        pop_list.add("Q币");
        pop_list.add("水电燃");

        if (mData == null) {
            mData = new ArrayList<FinancialDetailsBean.DataBean.ListBean>();
        }
    }

    private void initView() {

        setActivityTitle();
        rl_title = (RelativeLayout) findViewById(R.id.rl_title);
        cwmx_listview = (PullToRefreshListView) findViewById(R.id.cwmx_listview);
        cwmx_listview.setHeaderLayout(new HeaderLayout(this));
        cwmx_listview.setFooterLayout(new FooterLayout(this));

        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
        rl_have_net = (RelativeLayout) findViewById(R.id.rl_have_net);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        tv_start_time = (TextView) findViewById(R.id.tv_start_time);
        tv_end_time = (TextView) findViewById(R.id.tv_end_time);
        tv_search = (TextView) findViewById(R.id.tv_search);
        tv_start_time.setText(MyDateUtils.getSixDaysBefore());
        tv_end_time.setText(MyDateUtils.getNextDateTime(new Date()));

        // 时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.ALL);
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
        OptionsPopupWindow pwOptions = new OptionsPopupWindow(this);

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

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("财务明细");
        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("筛选");
        tv_subtitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle:
                /** 弹出popwindow */
                createPoPWindow();
                break;
            case R.id.tv_reload://重新加载
                httpData(1, popPositiong);
                break;
            case R.id.tv_start_time://开始时间
                flagValue = 10;
                pwTime.showAtLocation(tv_start_time, Gravity.BOTTOM, 0, 0, MyDateUtils.getSixDaysBeforeDate());
                break;
            case R.id.tv_end_time://结束时间
                flagValue = 20;
                pwTime.showAtLocation(tv_end_time, Gravity.BOTTOM, 0, 0, MyDateUtils.getNextDate(new Date()));
                break;
            case R.id.tv_search://点击按时间段检索
                mData.clear();
                currentPage =2;
                isFirst = true;
                httpData(1, popPositiong);
                break;

        }
    }

    private void createPoPWindow() {
        if (popTele != null && popTele.isShowing()) {
            popTele.dismiss();
        } else {
            popuWindowView = getLayoutInflater().inflate(R.layout.popuwindow_finicialdetail_menulist, null);
            menulist = (GridView) popuWindowView.findViewById(R.id.menulist);
            if (pop_list != null) {
                pop_adapter = new FenleiShaiXuanAdapter(pop_list, FinancialDetailsActivity.this);
                pop_adapter.setSelectPosition(popPositiong);
                menulist.setAdapter(pop_adapter);
            }
            // 点击列表中item的处理
            menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
                    pop_adapter.setSelectPosition(index);
                    if (popTele != null && popTele.isShowing()) {
                        popTele.dismiss();
                    }
                    popPositiong = index;
                    isFirst = true;
                    mData.clear();
                    if (index == 10) {
                        httpData(1, 11);
                        popPositiong = 11;
                    } else {
                        httpData(1, popPositiong);
                    }
                }
            });

            popTele = new PopupWindow(popuWindowView, WindowManager.LayoutParams.MATCH_PARENT,
                    WindowManager.LayoutParams.WRAP_CONTENT, true);
            View viewBg = popuWindowView.findViewById(R.id.view_popuwindow_bg);
//            viewBg.setAlpha(0.6f);
            //点击周边关闭popuwindow
            viewBg.setOnTouchListener(new View.OnTouchListener() {
                @Override
                public boolean onTouch(View v, MotionEvent event) {
                    closePopupWindow();
                    return false;
                }
            });

            popuWindowView.setFocusable(true);
            popuWindowView.setFocusableInTouchMode(true);
            popuWindowView.setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        closePopupWindow();
                    }
                    return false;
                }
            });

            // 设置popupwindow的位置
            popTele.showAsDropDown(head_back, 0, 0);
//            popTele.showAtLocation(popuWindowView, Gravity.BOTTOM, 0, 0);

        }
    }

    /**
     * 关闭窗口
     */
    private void closePopupWindow() {

        if (popTele != null && popTele.isShowing()) {
            popTele.dismiss();
            popTele = null;
        }
    }

    private PopupWindow popTele;
    private View popuWindowView;
    private GridView menulist;
    private List<String> pop_list;
    private FenleiShaiXuanAdapter pop_adapter;
    private int popPositiong;


    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            cwmx_listview.onRefreshComplete();// 刷新UI
        }
    }

    /**
     * 获取开始时间
     *
     * @return
     */
    private String getStartTime() {
        return tv_start_time.getText().toString().trim();
    }

    /**
     * 获取结束时间
     *
     * @return
     */
    private String getEndTime() {
        return tv_end_time.getText().toString().trim();
    }

}
