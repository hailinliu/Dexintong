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
import com.runtai.newdexintong.module.personalcenter.adapter.ImperfectGoodsAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.ImperfectGoodsBean;
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
 * @author：rhf
 * @date：2017/10/20time08:33
 * @detail：
 */

public class ImperfectGoodsFragment extends BaseFragment {

    private int pageItemCount = 20;
    private RelativeLayout rl_no_data_show;
    private PullToRefreshListView pullToRefreshListView;
    private boolean isFirst = true;
    private int currentPage = 2;
    private List<ImperfectGoodsBean.DataBean.ListBean> mImperfectData;
    private int imperfectPages;
    private TextView tv_reload;
    private RelativeLayout rl_no_net;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_return_goods);
        View view = getContentView();
        initView(view);
        imperfectDataByHttp(1);
        registerListener();
    }

    private void initView(View view) {
        pullToRefreshListView = (PullToRefreshListView) view.findViewById(R.id.pullToRefreshListView);
        pullToRefreshListView.setHeaderLayout(new HeaderLayout(getActivity()));
        pullToRefreshListView.setFooterLayout(new FooterLayout(getActivity()));
        rl_no_data_show = (RelativeLayout) view.findViewById(R.id.rl_no_data_show);

        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
    }

    private void registerListener() {

        //点击重新加载
        tv_reload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                imperfectDataByHttp(1);
            }
        });

        pullToRefreshListView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                if(null!=mImperfectData){
                    mImperfectData.clear(); 
                }
               
                imperfectDataByHttp(1);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (imperfectPages > 0 && imperfectPages >= currentPage) {
                    imperfectDataByHttp(currentPage);
                    currentPage++;
                } else {
                    new GetDataTask().execute();
                    ToastUtil.showToast(getActivity(), "没有更多订单了", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    /**
     * 申请残次界面
     *
     * @param imperfectPage
     */
    private void imperfectDataByHttp(int imperfectPage) {

        if (!NetUtil.isNetworkAvailable(getActivity())) {
            pullToRefreshListView.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            pullToRefreshListView.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/ret/imperfect";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(imperfectPage));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Page", String.valueOf(imperfectPage))
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
                        ImperfectGoodsBean imperfectGoodsBean = gson.fromJson(strJson, ImperfectGoodsBean.class);
                        ImperfectGoodsBean.DataBean data = imperfectGoodsBean.getData();
                        if (mImperfectData == null) {
                            mImperfectData = new ArrayList<ImperfectGoodsBean.DataBean.ListBean>(); 
                        }
                        mImperfectData.addAll(data.getList());
                        if (mImperfectData.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            pullToRefreshListView.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            pullToRefreshListView.setVisibility(View.VISIBLE);
                        }
                        imperfectPages = data.getPages();
                        ImperfectGoodsAdapter imperfectGoodsAdapter = new ImperfectGoodsAdapter(getActivity(), mImperfectData);
                        pullToRefreshListView.onRefreshComplete();
                        if (isFirst) {
                            pullToRefreshListView.setAdapter(imperfectGoodsAdapter);
                            isFirst = false;
                        } else {
                            imperfectGoodsAdapter.notifyDataSetChanged();
                        }
                        int totalImperfect = data.getTotal();
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

    public class GetDataTask extends AsyncTask<Void, Void, String> {

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
}
