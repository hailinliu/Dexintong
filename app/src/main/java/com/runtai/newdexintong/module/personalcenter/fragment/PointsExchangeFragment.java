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
import com.runtai.newdexintong.module.personalcenter.adapter.JiFenDuiHuanAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.PointsExchangeRecordBean;
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
 * @作者：rhf
 * @日期：2017/3/28时间16:22
 * @描述：积分兑换Fragment
 */

public class PointsExchangeFragment extends BaseFragment {

    private PullToRefreshListView jifen_duihuan;
    JiFenDuiHuanAdapter adapter;
    private List<PointsExchangeRecordBean.DataBean.ListBean> listData;
    private int currentPage = 2;
    private int pages;
    private boolean isfirst = true;
    private RelativeLayout rl_no_data_show;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_points_exchange);
        View view = getContentView();
        initView(view);
        doHttp(1);
        regsiterListener();
    }

    public void initView(View view) {
        jifen_duihuan = (PullToRefreshListView) view.findViewById(R.id.jifen_duihuan);
        jifen_duihuan.setHeaderLayout(new HeaderLayout(getActivity()));
        jifen_duihuan.setFooterLayout(new FooterLayout(getActivity()));

        rl_no_data_show = (RelativeLayout) view.findViewById(R.id.rl_no_data_show);
        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
       
    }

    private void regsiterListener() {

        //点击重新加载
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                doHttp(1);
            }
        });
        jifen_duihuan.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                //下拉刷新
                listData.clear();
                doHttp(1);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                //上拉加载更多
                if (pages > 0 && pages >= currentPage) {

                    doHttp(currentPage);
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(getActivity(), "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 请求数据
     */
    private void doHttp(int page) {

        if (!NetUtil.isNetworkAvailable(getActivity())) {
            jifen_duihuan.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            jifen_duihuan.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }


        /**
         * 参数：page int 页码
         参数：s int 每页显示条数
         参数：t int 类型0兑换记录，1积分来源记录
         */
        String url = AppConstant.BASEURL2 + "api/score/log";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(page));
        map.put("Size", "20");
        map.put("T", String.valueOf(0));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(page))
                .addParams("Size", "20")
                .addParams("T", String.valueOf(0))
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
                        PointsExchangeRecordBean exchangeRecordBean = gson.fromJson(strJson, PointsExchangeRecordBean.class);
                        PointsExchangeRecordBean.DataBean dataBean = exchangeRecordBean.getData();
                        if (listData == null) {
                            listData = new ArrayList<PointsExchangeRecordBean.DataBean.ListBean>();
                        }
                        listData.addAll(dataBean.getList());
                        pages = dataBean.getPages();
                        int total = dataBean.getTotal();
                        LogUtil.e("points_exchange", String.valueOf(total));
                        if (listData.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            jifen_duihuan.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            jifen_duihuan.setVisibility(View.VISIBLE);
                        }
                        jifen_duihuan.onRefreshComplete();//停止刷新
                        adapter = new JiFenDuiHuanAdapter(getActivity(), listData);
                        if (isfirst) {
                            jifen_duihuan.setAdapter(adapter);
                            isfirst = false;
                        }else {
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
                LogUtil.e("score_list", e.toString());
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
            //Mark the current Refresh as complete. Will Reset the UI and hide the Refreshing View
            jifen_duihuan.onRefreshComplete();//刷新UI
        }
    }


   

//    @Override
//    public void setUserVisibleHint(boolean isVisibleToUser) {
//        // TODO Auto-generated method stub
//        super.setUserVisibleHint(isVisibleToUser);
//        if (isVisibleToUser) {
//            Log.e("积分兑换", "显示状态" + isVisibleToUser);
//            if (!isfirst) {
//                Log.e("积分兑换，请求数据", "这里应该正常了");
//                currentPage = 2;
//                listData.clear();
//                doHttp(1);
//            } else {
//                isfirst = false;
//            }
//        } else {
//            Log.e("积分兑换", "隐藏状态" + isVisibleToUser);
//        }
//    }

}
