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
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.personalcenter.adapter.YouHuiQuan_wsy_Adapter;
import com.runtai.newdexintong.module.personalcenter.bean.MyCouponBean;
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
 * @作者：高炎鹏
 * @日期：2017/4/2时间09:17
 * @描述：未使用
 */
public class UnUsedCouponFragment extends BaseFragment {

    private PullToRefreshListView yhq_wsy_list;
    YouHuiQuan_wsy_Adapter adapter;
    private int pages;
    private int total;
    private int currentPage = 2;
    private List<MyCouponBean.DataBean.ListBean> listData;
    /**
     * 每页显示的条目数量
     */
    private int pageItemCount = 20;
    private boolean isFirst = true;
    private RelativeLayout rl_no_data_show;
    private TextView tv_reload;
    private RelativeLayout rl_no_net;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_coupon_unused);
        View view = getContentView();
        initView(view);
        httpCouponData(1);
        registerListener();
    }

    public void initView(View view) {
        yhq_wsy_list = (PullToRefreshListView) view.findViewById(R.id.yhq_wsy_list);
        yhq_wsy_list.setHeaderLayout(new HeaderLayout(getActivity()));
        yhq_wsy_list.setFooterLayout(new FooterLayout(getActivity()));

        rl_no_data_show = (RelativeLayout) view.findViewById(R.id.rl_no_data_show);
        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
    }

    private void registerListener() {

        tv_reload.setOnClickListener(this);
        yhq_wsy_list.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                listData.clear();
                httpCouponData(1);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                if (pages > 0 && pages >= currentPage) {

                    httpCouponData(currentPage);
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 请求优惠券数据
     */
    private void httpCouponData(int page) {
        
        if (!NetUtil.isNetworkAvailable(getActivity())) {
            yhq_wsy_list.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            yhq_wsy_list.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        /**
         * page int 页码
         s int 每页条数
         t int 状态0未使用，2已过期，3已使用
         */

        String url = AppConstant.BASEURL2 + "api/coupon/list";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("T", "0");
        map.put("Page", String.valueOf(page));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("T", "0")
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
                        MyCouponBean myCouponBean = gson.fromJson(strJson, MyCouponBean.class);
                        MyCouponBean.DataBean data = myCouponBean.getData();

                        if (listData == null) {
                            listData = new ArrayList<MyCouponBean.DataBean.ListBean>();
                        }
                        listData.addAll(data.getList());

                        if (listData.size() == 0) {
                            yhq_wsy_list.setVisibility(View.GONE);
                            rl_no_data_show.setVisibility(View.VISIBLE);
                        } else {
                            yhq_wsy_list.setVisibility(View.VISIBLE);
                            rl_no_data_show.setVisibility(View.GONE);
                        }
                        pages = data.getPages();
                        total = data.getTotal();
                        LogUtil.e("UnUsedCoupon", "总页数" + pages + ".....总条目数" + total);
                        yhq_wsy_list.onRefreshComplete();//刷新UI
                        adapter = new YouHuiQuan_wsy_Adapter(getActivity(), listData);
                        if (isFirst) {
                            yhq_wsy_list.setAdapter(adapter);
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
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("unusedcoupon", e.toString());
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
            yhq_wsy_list.onRefreshComplete();//刷新UI
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_reload://点击重新加载
                httpCouponData(1);
                break;
        }
    }

    //    private boolean isfirst = true;
//
//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        // TODO Auto-generated method stub
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            Log.e("积分兑换", "显示状态" + isVisibleToUser);
//            if (!isfirst) {
//                Log.e("积分兑换，请求数据", "这里应该正常了");
//                currentPage = 2;
////                showListByList(userId,1,true);
//            } else {
//                isfirst = false;
//            }
//        } else {
//            Log.e("积分兑换", "隐藏状态" + isVisibleToUser);
//        }
//    }


}