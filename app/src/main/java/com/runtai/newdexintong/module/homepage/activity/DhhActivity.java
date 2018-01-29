package com.runtai.newdexintong.module.homepage.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
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
import com.runtai.newdexintong.comment.view.MyGridView;
import com.runtai.newdexintong.module.fenlei.view.FooterLayout;
import com.runtai.newdexintong.module.fenlei.view.HeaderLayout;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.view.FullyLinearLayoutManager;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.homepage.adapter.DhhGoodsAdapter;
import com.runtai.newdexintong.module.homepage.adapter.SpecialSaleTitleAdapter;
import com.runtai.newdexintong.module.homepage.bean.ADInfo;
import com.runtai.newdexintong.module.homepage.bean.DhhListDataBean;
import com.runtai.newdexintong.module.homepage.bean.FindGoodsBean;
import com.runtai.newdexintong.module.homepage.bean.SpecialDataBean;
import com.runtai.newdexintong.module.homepage.bean.SpecialSaleTitleBean;
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
 * 订货会对应的界面
 */
public class DhhActivity extends BaseActivity {

    private RelativeLayout head_back;
    //    private ImageView iv_search_special;
    private RecyclerView special_title_recyclerview;
    private List<SpecialSaleTitleBean> mData;
    private SpecialSaleTitleAdapter adapter;
    private View view_selected;
    private MyGridView gridview_goods_msg;
    private List<FindGoodsBean> goodsData;
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private List<ImageView> views = new ArrayList<ImageView>();
    private PullToRefreshScrollView pullToRefreshScrollView;
    private ImageView iv_search_special;
    private ImageView iv_shoppingcar;
    private int pageItemCount = 20;
    private String mUrl;

    private ImageView headerPic;
    private List<SpecialDataBean.DataBean.ListBean> mList;
    private TextView tv_title;
    private int currentPage = 2;
    private int pages;
    private List<SpecialDataBean.DataBean.CategoryBean> category;
    private List<DhhListDataBean.DataBean.ListBean> mDhhData;
    private boolean isFirst = true;
    private RelativeLayout rl_no_data_show;
    private TextView tv_goods_amount;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;
    private DhhGoodsAdapter adapter1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dhh);
        GetCartTotalNumUtil.getShoppingCartNumber(this);
        initView();
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("mUrl");
        if ("api/promotion/dhh".equals(mUrl)) {
            httpdhhData(1);
        }
//        makeSpikeOrder();
        registerListener();

    }

    private void httpdhhData(int page) {

        if (!NetUtil.isNetworkAvailable(this)) {
            pullToRefreshScrollView.setVisibility(View.GONE);
            tv_goods_amount.setVisibility(View.GONE);
            iv_shoppingcar.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            pullToRefreshScrollView.setVisibility(View.VISIBLE);
            tv_goods_amount.setVisibility(View.VISIBLE);
            iv_shoppingcar.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }
        String url = AppConstant.BASEURL2 + mUrl;
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Page", String.valueOf(page));
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
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
                        DhhListDataBean dhhListDataBean = gson.fromJson(strJson, DhhListDataBean.class);
                        DhhListDataBean.DataBean data = dhhListDataBean.getData();
                        List<?> banner = data.getBanner();
                        tv_title.setText(data.getTitle());
                        if (banner.size() > 0) {
                            headerPic.setVisibility(View.VISIBLE);
                            
//                            ImageLoader.getInstance().displayImage(banner.get(0).getImgPath(), headerPic,
//                                    ImageLoadUtil.getDefaultHeadPicOptions(), null);
                        } else {
                            headerPic.setVisibility(View.GONE);
                        }
                        pages = data.getPages();
                        if (mDhhData == null) {
                            mDhhData = new ArrayList<DhhListDataBean.DataBean.ListBean>();
                        }
                        mDhhData.addAll(data.getList());
                        if (mDhhData.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            pullToRefreshScrollView.setVisibility(View.GONE);
                        }else {
                            rl_no_data_show.setVisibility(View.GONE);
                            pullToRefreshScrollView.setVisibility(View.VISIBLE);
                        }
                        pullToRefreshScrollView.onRefreshComplete();
                        if (adapter1 == null) {
                            adapter1 = new DhhGoodsAdapter(DhhActivity.this, mDhhData);
                            gridview_goods_msg.setAdapter(adapter1);
                        } else {
                            adapter1.setData(mDhhData);
                            adapter1.notifyDataSetChanged();
                        }
                        
                    } else if (code == 403) {
                        DialogUtil.showDialog(DhhActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(DhhActivity.this, msg, Toast.LENGTH_SHORT);
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

    private void initView() {
        
        showLoading();
        setActivityTitle();
        pullToRefreshScrollView = (PullToRefreshScrollView) findViewById(R.id.pullToRefreshScrollView);
        pullToRefreshScrollView.setHeaderLayout(new HeaderLayout(this));
        pullToRefreshScrollView.setFooterLayout(new FooterLayout(this));

        gridview_goods_msg = (MyGridView) findViewById(R.id.gridview_goods_msg);

        special_title_recyclerview = (RecyclerView) findViewById(R.id.special_title_recyclerview);
//        category = new ArrayList<>();
//        adapter = new SpecialSaleTitleAdapter(this, mData);
//        special_title_recyclerview.setAdapter(adapter);
        FullyLinearLayoutManager manager = new FullyLinearLayoutManager(this);
        manager.setOrientation(FullyLinearLayoutManager.HORIZONTAL);
        special_title_recyclerview.setLayoutManager(manager);
        special_title_recyclerview.setItemAnimator(new DefaultItemAnimator());

        view_selected = findViewById(R.id.view_selected);

        // 放置广告的位置
        headerPic = (ImageView) findViewById(R.id.headerPic);
        iv_shoppingcar = (ImageView) findViewById(R.id.iv_shoppingcar);
        tv_goods_amount = (TextView) findViewById(R.id.tv_goods_amount);
        String totalNumber = SPUtils.getString(this, "TotalNumber", "");
        if (!"".equals(totalNumber) && totalNumber.length() > 0) {
            tv_goods_amount.setText(totalNumber);
        }

        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        tv_reload = (TextView)findViewById(R.id.tv_reload);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);

    }

    private void registerListener() {
        
        tv_reload.setOnClickListener(this);
        head_back.setOnClickListener(this);
        iv_search_special.setOnClickListener(this);
//        adapter.setOnItemClickLitener(new SpecialSaleTitleAdapter.OnItemClickLitener() {
//            @Override
//            public void onItemClick(View view, SpecialSaleTitleBean bean) {
////                initData(10);
////                gridview_goods_msg.setAdapter(new SpecialGoodsAdapter(SpecialSaleActivity.this, goodsData));
//            }
//        });

//        gridview_goods_msg.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                Intent intent = new Intent(DhhActivity.this, ProductDetailActivity.class);
//                startActivityByIntent(intent);
//            }
//        });

        iv_shoppingcar.setOnClickListener(this);
        pullToRefreshScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ScrollView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //下拉刷新
                mDhhData.clear();
                httpdhhData(1);
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //上拉加载
                if (pages > 0 && pages >= currentPage) {
                    httpdhhData(currentPage);
                    currentPage++;
                } else {
                    new GetDataTask().execute();
                    ToastUtil.showToast(DhhActivity.this, "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });
    }


    /**
     * 设置界面
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
//        tv_title.setText("订货会");
        iv_search_special = (ImageView) findViewById(R.id.iv_search_special);
        iv_search_special.setVisibility(View.VISIBLE);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.iv_search_special://搜索按钮
                Intent intent = new Intent(this, SearchActivity.class);
                startActivityByIntent(intent);
                break;
            case R.id.iv_shoppingcar://点击购物车图标
                Intent intent2 = new Intent(this, MainActivity.class);
                intent2.putExtra("tag", "SearchResultAcivity");
                startActivityByIntent(intent2);
                break;
            case R.id.tv_reload://重新加载
                httpdhhData(1);
                break;
        }
    }

//    @Override
//    public void onImageClick(ADInfo info, int position, View imageView) {
//
//    }

    public class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {

            return null;
        }

        @Override
        protected void onPostExecute(String s) {
            pullToRefreshScrollView.onRefreshComplete();
            super.onPostExecute(s);

        }
    }
}
