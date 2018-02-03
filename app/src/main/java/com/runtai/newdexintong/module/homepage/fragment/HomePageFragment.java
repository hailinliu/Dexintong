package com.runtai.newdexintong.module.homepage.fragment;

import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyCommUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.NetUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.comment.view.MyGridView;
import com.runtai.newdexintong.module.home.bean.ShoppingCartGoodsNumBean;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.view.CustomRecyclerViewDivider;
import com.runtai.newdexintong.module.home.view.FullyLinearLayoutManager;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.homepage.activity.CommonScanActivity;
import com.runtai.newdexintong.module.homepage.activity.ExchangePrizeActivity;
import com.runtai.newdexintong.module.homepage.activity.MessageBoxActivity;
import com.runtai.newdexintong.module.homepage.activity.RechargeCenterActivity;
import com.runtai.newdexintong.module.homepage.activity.RegularBuyListActivity;
import com.runtai.newdexintong.module.homepage.activity.SearchActivity;
import com.runtai.newdexintong.module.homepage.activity.SpecialListActivity;
import com.runtai.newdexintong.module.homepage.adapter.HomePageAds5ItemAdapter;
import com.runtai.newdexintong.module.homepage.adapter.HomePageAds6ItemAdapter;
import com.runtai.newdexintong.module.homepage.adapter.SpecialCategoryAdapter;
import com.runtai.newdexintong.module.homepage.bean.ADInfo;
import com.runtai.newdexintong.module.homepage.bean.HomePageAdsPicBean;
import com.runtai.newdexintong.module.homepage.bean.HomepageSpecialBean;
import com.runtai.newdexintong.module.homepage.utils.SkipToPointActivityUtil;
import com.runtai.newdexintong.module.homepage.utils.ViewFactory;
import com.runtai.newdexintong.module.homepage.view.CycleViewPager;
import com.runtai.newdexintong.module.homepage.view.MyScrollview;
import com.runtai.newdexintong.module.homepage.view.ToTopImageView;
import com.runtai.newdexintong.module.personalcenter.bean.CheckAppVersionBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.FileCallBack;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static java.lang.Integer.parseInt;


/**
 * Created by Administrator on 2017/1/23.
 * 首页对应的fragment
 */
public class HomePageFragment extends BaseFragment implements CycleViewPager.ImageCycleViewListener {

    private RelativeLayout head_back, rl_search_jiemian, rl_title;
    private LinearLayout ll_regular_buy_list, ll_recharge_center, ll_exchange_prize, ll_special_area;
    private TextView tv_title;
    private ImageView iv_scan, iv_message;
    private List<ADInfo> infos = new ArrayList<ADInfo>();
    private List<ImageView> views = new ArrayList<ImageView>();

    private CycleViewPager cycleViewPager;
    private EditText et_search;
    //    private List<String> mDatas;
    private List<Object> mDatas_two;
    private MyScrollview mScrollView;
    private MyGridView myGridView_find_goods;
    private ImageView iv_special_one;
    private ImageView iv_special_two;
    private ImageView iv_search2;
    private ToTopImageView iv_go_to_top;
    private RecyclerView mRecyclerView;
    private SpecialCategoryAdapter infoAdapter;
    private List<HomePageAdsPicBean.DataBean.Ads1Bean.AdLocationsBean> bannerData;
    private ImageView iv_special_three;
    private ImageView iv_special_four;
    private boolean isHttp = false;
    private List<HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean> adbottomGroups;
    private HomePageAdsPicBean.DataBean.Ads2Bean ads2Data;
    private HomePageAdsPicBean.DataBean.Ads3Bean ads3;
    private HomePageAdsPicBean.DataBean.Ads4Bean ads4;
    private HomePageAdsPicBean.DataBean.Ads5Bean ads5;
    private HomePageAdsPicBean.DataBean.Ads6Bean ads6;
    private MyGridView mGridView_ads6;
    private MyGridView ads5_mGridView;
    private RelativeLayout rl_no_net;
//    private RelativeLayout rl_homepage_net_ok;
    private TextView tv_reload;
    private ProgressDialog progressDialog;
    private TextView tv_homepage;


    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_homepage);
        View view = getContentView();
        initView(view);
        checkAppVersion();
        isHttp = true;
        httpData();
        regsiterListener();
        getNoticeMessage();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        getShoppingCartNumber();
    }
    

    /**
     * 获取首页中广告图版图数据
     */
    private void httpData() {


        if (!NetUtil.isNetworkAvailable(getActivity())) {
            mScrollView.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            mScrollView.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        showLoading();
        String url = AppConstant.BASEURL2 + "api/main/home";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
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
                        HomePageAdsPicBean homeBannerBean = gson.fromJson(strJson, HomePageAdsPicBean.class);
                        HomePageAdsPicBean.DataBean data = homeBannerBean.getData();
                        HomePageAdsPicBean.DataBean.Ads1Bean ads1 = data.getAds1();
                        if (bannerData == null) {
                            bannerData = new ArrayList<HomePageAdsPicBean.DataBean.Ads1Bean.AdLocationsBean>();
                        }
                        bannerData.clear();
                        bannerData = ads1.getAdLocations();
                        initcycleViewPager(bannerData);
                        ads2Data = data.getAds2();
                        ImageLoader.getInstance().displayImage(ads2Data.getAdLocations().get(0).getImgUrl(), iv_special_one,
                                ImageLoadUtil.getDefaultHeadPicOptions(), null);

                        ads3 = data.getAds3();
                        ImageLoader.getInstance().displayImage(ads3.getAdLocations().get(0).getImgUrl(), iv_special_two, ImageLoadUtil.getDefaultHeadPicOptions(), null);
                        ImageLoader.getInstance().displayImage(ads3.getAdLocations().get(1).getImgUrl(), iv_special_three, ImageLoadUtil.getDefaultHeadPicOptions(), null);

                        ads4 = data.getAds4();
                        ImageLoader.getInstance().displayImage(ads4.getAdLocations().get(0).getImgUrl(),
                                iv_special_four, ImageLoadUtil.getDefaultHeadPicOptions(), null);
                        ads5 = data.getAds5();
                        List<HomePageAdsPicBean.DataBean.Ads5Bean.AdLocationsBeanXXXX> ads5Data = ads5.getAdLocations();
                        ads5_mGridView.setAdapter(new HomePageAds5ItemAdapter(getActivity(),ads5Data));
                        ads6 = data.getAds6();
                        List<HomePageAdsPicBean.DataBean.Ads6Bean.AdLocationsBeanXXXXX> adLocations = ads6.getAdLocations();
                        mGridView_ads6.setAdapter(new HomePageAds6ItemAdapter(getActivity(), adLocations));

                        HomePageAdsPicBean.DataBean.Ads7Bean ads7 = data.getAds7();
                        if (adbottomGroups == null) {
                            adbottomGroups = new ArrayList<HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean>();
                        }
                        adbottomGroups.clear();
                        adbottomGroups = ads7.getAdGroups();
                        initData();

                        if (mScrollView != null) {
                            mScrollView.smoothScrollTo(0,0);
                        }
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getActivity().getResources().getString(R.string.need_login_again));
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
                LogUtil.e("homepage", e.toString());
                dismissLoading();
            }
        });
    }


    @Override
    public void onFragmentStart() {
        super.onFragmentStart();
        if (isHttp) {
            httpData();
        }
    }

    private void initView(View view) {
        setHomePageTitle(view);
        mScrollView = (MyScrollview) view.findViewById(R.id.mScrollView);
        tv_homepage = (TextView)view.findViewById(R.id.tv_homepage);
        if (mScrollView != null) {
            mScrollView.smoothScrollTo(0,0);
        }
        mRecyclerView = (RecyclerView) view.findViewById(R.id.mRecyclerView);
        FullyLinearLayoutManager fullyLinearLayoutManager = new FullyLinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(fullyLinearLayoutManager);
        mRecyclerView.addItemDecoration(new CustomRecyclerViewDivider(getActivity(),CustomRecyclerViewDivider.VERTICAL_LIST,
                R.drawable.shape_recyclerview_divider_bg,0));
//        initAdapter();
        setToTopButton(view);

//        setDownRefresh();
        // 放置广告的位置
        cycleViewPager = (CycleViewPager) getActivity().getFragmentManager()
                .findFragmentById(R.id.cycleViewPager);
//        initcycleViewPager(cycleViewPager, infos, views);

//        setLastQuickRob(view);

        mGridView_ads6 = (MyGridView) view.findViewById(R.id.mGridView_ads6);
        ads5_mGridView = (MyGridView) view.findViewById(R.id.ads5_mGridView);
        ll_regular_buy_list = (LinearLayout) view.findViewById(R.id.ll_regular_buy_list);
        ll_recharge_center = (LinearLayout) view.findViewById(R.id.ll_recharge_center);
        ll_exchange_prize = (LinearLayout) view.findViewById(R.id.ll_exchange_prize);
        ll_special_area = (LinearLayout) view.findViewById(R.id.ll_special_area);
        iv_special_one = (ImageView) view.findViewById(R.id.iv_special_one);
        iv_special_two = (ImageView) view.findViewById(R.id.iv_special_two);

        iv_special_one = (ImageView) view.findViewById(R.id.iv_special_one);
        iv_special_two = (ImageView) view.findViewById(R.id.iv_special_two);
        iv_special_three = (ImageView) view.findViewById(R.id.iv_special_three);
        iv_special_four = (ImageView) view.findViewById(R.id.iv_special_four);
        //无网络时候用到的
        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
//        rl_homepage_net_ok = (RelativeLayout) view.findViewById(R.id.rl_homepage_net_ok);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);

    }

    private void initAdapter() {

        infoAdapter = new SpecialCategoryAdapter(getActivity(), mDatas_two);
        mRecyclerView.setAdapter(infoAdapter);

    }


    /**
     * 设置首页的一键置顶按钮
     *
     * @param view
     */
    private void setToTopButton(View view) {
        iv_go_to_top = (ToTopImageView) view.findViewById(R.id.iv_go_to_top);
//        iv_go_to_top.setLimitHeight(800);
//        final ScrollView scrollView = mScrollView.getRefreshableView();
//        mScrollView.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                switch (event.getAction()) {
//                    case MotionEvent.ACTION_MOVE:
//                        iv_go_to_top.tellMe(mScrollView);
//                        break;
//                }
//                return false;
//            }
//        });
    }


    private void initData() {

        mDatas_two = new ArrayList<>();
        for (int i = 0; i < adbottomGroups.size(); i++) {
            HomepageSpecialBean info = new HomepageSpecialBean();
            info.setText(adbottomGroups.get(i).getName());

            List<HomePageAdsPicBean.DataBean.Ads7Bean.AdGroupsBean.ViewAdLocationsBean> viewAdLocations
                    = adbottomGroups.get(i).getViewAdLocations();
            info.setViewAdLocations(viewAdLocations);
            mDatas_two.add(info);
            initAdapter();

        }
    }

    private void regsiterListener() {

        tv_reload.setOnClickListener(this);
        iv_scan.setOnClickListener(this);
        et_search.setOnClickListener(this);
        iv_search2.setOnClickListener(this);
        iv_message.setOnClickListener(this);
        ll_regular_buy_list.setOnClickListener(this);
        ll_recharge_center.setOnClickListener(this);
        ll_exchange_prize.setOnClickListener(this);
        ll_special_area.setOnClickListener(this);
        iv_special_one.setOnClickListener(this);
        iv_special_two.setOnClickListener(this);
        iv_special_three.setOnClickListener(this);
        iv_special_four.setOnClickListener(this);
        iv_special_four.setOnClickListener(this);
        tv_homepage.setOnClickListener(this);

//        mScrollView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
//            @Override
//            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
//                new GetDataTask().execute();
//            }
//        });

//        myGridView_find_goods.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//
//                Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
//                startActivityByIntent(intent);
//            }
//        });

    }

    /**
     * 设置下拉刷新
     */
//    private void setDownRefresh() {
//
//        mScrollView.setMode(PullToRefreshBase.Mode.BOTH);
//        mScrollView.getLoadingLayoutProxy(false, true).setPullLabel(
//                getString(R.string.pull_to_refresh_footer_pull_label));
//        mScrollView
//                .getLoadingLayoutProxy(false, true)
//                .setRefreshingLabel(
//                        getString(R.string.pull_to_refresh_footer_refreshing_label));
//        mScrollView.getLoadingLayoutProxy(false, true).setReleaseLabel(
//                getString(R.string.pull_to_refresh_footer_release_label));
//    }

    public class GetDataTask extends AsyncTask<Void, Void, String> {

        @Override
        protected String doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(String s) {

//            mScrollView.onRefreshComplete();
            ToastUtil.showToast(getActivity(), "刷新已完成", Toast.LENGTH_SHORT);
            super.onPostExecute(s);

        }
    }

    /**
     * 设置首页fragment对应的标题栏
     */
    private void setHomePageTitle(View view) {

        head_back = (RelativeLayout) view.findViewById(R.id.head_back);
        head_back.setVisibility(View.GONE);
        iv_scan = (ImageView) view.findViewById(R.id.iv_scan);
        iv_scan.setVisibility(View.VISIBLE);
        tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setVisibility(View.GONE);
        iv_message = (ImageView) view.findViewById(R.id.iv_message);
        iv_message.setVisibility(View.GONE);
        rl_title = (RelativeLayout) view.findViewById(R.id.rl_title);
        rl_search_jiemian = (RelativeLayout) view.findViewById(R.id.rl_search_jiemian);
        rl_search_jiemian.setVisibility(View.VISIBLE);
        iv_search2 = (ImageView) view.findViewById(R.id.iv_search2);
        et_search = (EditText) view.findViewById(R.id.et_search);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.tv_homepage:
                
            case R.id.et_search://打开搜索界面
            case R.id.iv_search2:
                ((BaseCommonActivity) getActivity()).startActivityByIntent(SearchActivity.class);
                break;
            case R.id.iv_scan://扫一扫
                Intent intent = new Intent(getActivity(), CommonScanActivity.class);
                ((BaseCommonActivity) getActivity()).startActivityForResultByIntent(intent, AppConstant.REQUEST_CODE_SCAN);
                break;
            case R.id.iv_message://消息盒子界面
                ((BaseCommonActivity) getActivity()).startActivityByIntent(MessageBoxActivity.class);
                break;
            case R.id.ll_regular_buy_list://常购清单
                if (SPUtils.getBoolean(getActivity(), "isTourist", false)) {
                    ToastUtil.showToast(getActivity(), "亲，游客模式不支持查看哦", Toast.LENGTH_SHORT);
                } else {
                    ((BaseCommonActivity) getActivity()).startActivityByIntent(RegularBuyListActivity.class);
                }
                break;
            case R.id.ll_recharge_center://充值缴费
                if (SPUtils.getBoolean(getActivity(), "isTourist", false)) {
                    ToastUtil.showToast(getActivity(), "亲，游客模式不支持查看哦", Toast.LENGTH_SHORT);
                } else {
                    ((BaseCommonActivity) getActivity()).startActivityByIntent(RechargeCenterActivity.class);
                }
                break;
            case R.id.ll_exchange_prize://我要兑奖
                if (SPUtils.getBoolean(getActivity(), "isTourist", false)) {
                    ToastUtil.showToast(getActivity(), "亲，游客模式不支持查看哦", Toast.LENGTH_SHORT);
                } else {
                    ((BaseCommonActivity) getActivity()).startActivityByIntent(ExchangePrizeActivity.class);
                }
                break;
            case R.id.ll_special_area://特价专区
                Intent intent_special_area = new Intent(getActivity(), SpecialListActivity.class);
                intent_special_area.putExtra("mUrl", "api/promotion/list");
                intent_special_area.putExtra("paramName0", "SpecialId");
                intent_special_area.putExtra("paramValue0", "6");
                startActivityByIntent(intent_special_area);
                break;

            case R.id.iv_special_one://打开专场一
                String ads2Url = ads2Data.getAdLocations().get(0).getUrl().trim();
                List<HomePageAdsPicBean.DataBean.Ads2Bean.AdLocationsBeanX.ParamsBeanX> paramsAd2 = ads2Data.getAdLocations().get(0).getParams();
                int adSize = paramsAd2.size();
                if (adSize == 0) {
                    SkipToPointActivityUtil.jumpToNoParams(getActivity(), ads2Url);
                } else if (adSize == 1) {
                    String value0 = paramsAd2.get(0).getValue();
                    String key0 = paramsAd2.get(0).getKey();
                    SkipToPointActivityUtil.jumpToOneParams(getActivity(), ads2Url, key0, value0);
                } else if (adSize == 2) {
                    String key0 = paramsAd2.get(0).getKey();
                    String value0 = paramsAd2.get(0).getValue();
                    String key1 = paramsAd2.get(1).getKey();
                    String value1 = paramsAd2.get(1).getValue();
                    SkipToPointActivityUtil.jumpToTwoParams(getActivity(), ads2Url, key0, value0, key1, value1);
                }
                break;
            case R.id.iv_special_two://打开专场二
                HomePageAdsPicBean.DataBean.Ads3Bean.AdLocationsBeanXX adLocationsBeanXX_1 = ads3.getAdLocations().get(0);
                String ads3Url_1 = adLocationsBeanXX_1.getUrl().trim();
                List<HomePageAdsPicBean.DataBean.Ads3Bean.AdLocationsBeanXX.ParamsBeanXX> paramsAd3_1 = adLocationsBeanXX_1.getParams();
                int ad3Size_1 = paramsAd3_1.size();
                if (ad3Size_1 == 0) {
                    SkipToPointActivityUtil.jumpToNoParams(getActivity(), ads3Url_1);
                } else if (ad3Size_1 == 1) {
                    String value0 = paramsAd3_1.get(0).getValue();
                    String key0 = paramsAd3_1.get(0).getKey();
                    SkipToPointActivityUtil.jumpToOneParams(getActivity(), ads3Url_1, key0, value0);
                } else if (ad3Size_1 == 2) {
                    String key0 = paramsAd3_1.get(0).getKey();
                    String value0 = paramsAd3_1.get(0).getValue();
                    String key1 = paramsAd3_1.get(1).getKey();
                    String value1 = paramsAd3_1.get(1).getValue();
                    SkipToPointActivityUtil.jumpToTwoParams(getActivity(), ads3Url_1, key0, value0, key1, value1);
                }
                break;
            case R.id.iv_special_three://打开专场三
                HomePageAdsPicBean.DataBean.Ads3Bean.AdLocationsBeanXX adLocationsBeanXX_2 = ads3.getAdLocations().get(1);
                String ads3Url_2 = adLocationsBeanXX_2.getUrl().trim();
                List<HomePageAdsPicBean.DataBean.Ads3Bean.AdLocationsBeanXX.ParamsBeanXX> paramsAd3_2 = adLocationsBeanXX_2.getParams();
                int ad3Size_2 = paramsAd3_2.size();
                if (ad3Size_2 == 0) {
                    SkipToPointActivityUtil.jumpToNoParams(getActivity(), ads3Url_2);
                } else if (ad3Size_2 == 1) {
                    String value0 = paramsAd3_2.get(0).getValue();
                    String key0 = paramsAd3_2.get(0).getKey();
                    SkipToPointActivityUtil.jumpToOneParams(getActivity(), ads3Url_2, key0, value0);
                } else if (ad3Size_2 == 2) {
                    String key0 = paramsAd3_2.get(0).getKey();
                    String value0 = paramsAd3_2.get(0).getValue();
                    String key1 = paramsAd3_2.get(1).getKey();
                    String value1 = paramsAd3_2.get(1).getValue();
                    SkipToPointActivityUtil.jumpToTwoParams(getActivity(), ads3Url_2, key0, value0, key1, value1);
                }
                break;
            case R.id.iv_special_four://打开专场四
                HomePageAdsPicBean.DataBean.Ads4Bean.AdLocationsBeanXXX adLocationsBeanXXX = ads4.getAdLocations().get(0);
                String ads4Url = adLocationsBeanXXX.getUrl();
                List<HomePageAdsPicBean.DataBean.Ads4Bean.AdLocationsBeanXXX.ParamsBeanXXX> ads4Params = adLocationsBeanXXX.getParams();
                int ads4Size = ads4Params.size();
                if (ads4Size == 0) {
                    SkipToPointActivityUtil.jumpToNoParams(getActivity(), ads4Url);
                } else if (ads4Size == 1) {
                    String value0 = ads4Params.get(0).getValue();
                    String key0 = ads4Params.get(0).getKey();
                    SkipToPointActivityUtil.jumpToOneParams(getActivity(), ads4Url, key0, value0);
                } else if (ads4Size == 2) {
                    String key0 = ads4Params.get(0).getKey();
                    String value0 = ads4Params.get(0).getValue();
                    String key1 = ads4Params.get(1).getKey();
                    String value1 = ads4Params.get(1).getValue();
                    SkipToPointActivityUtil.jumpToTwoParams(getActivity(), ads4Url, key0, value0, key1, value1);
                }

                break;
            case R.id.tv_reload://重新加载
                httpData();
                break;

        }
    }
    /**
     * 获取公告内容
     */
    private void getNoticeMessage() {

        if (!NetUtil.isNetworkAvailable(HomePageFragment.this.getContext())) {
            ToastUtil.showToast(HomePageFragment.this.getContext(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }

        String url = AppConstant.BASEURL2 + "api/main/msg";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(HomePageFragment.this.getContext(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
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
                    int code = parseInt(jsonObject.getString("Code"));
                    String msg = jsonObject.getString("Msg");
                    if (code == 200) {
                       /* Gson gson = GsonUtil.buildGson();
                        CheckAppVersionBean checkAppVersionBean = gson.fromJson(strJson, CheckAppVersionBean.class);
                        CheckAppVersionBean.DataBean data = checkAppVersionBean.getData();
                        int serverVersionCode = (int) Double.parseDouble(data.getCode());
                        String desc = data.getDesc();
                        String downloadUrl = data.getUrl();*/
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
    protected void initcycleViewPager(List<HomePageAdsPicBean.DataBean.Ads1Bean.AdLocationsBean> bannerData) {

        this.bannerData = bannerData;
        if (views.size() > 0) {
            return;
        }
        for (int i = 0; i < bannerData.size(); i++) {
            ADInfo info = new ADInfo();
            info.setUrl(bannerData.get(i).getImgUrl());
            info.setContent("图片-->" + i);
            infos.add(info);

        }


        // 将最后一个ImageView添加进来
        views.add(ViewFactory.getImageView(getActivity(),
                infos.get(infos.size() - 1).getUrl()));
        for (int i = 0; i < infos.size(); i++) {
            views.add(ViewFactory.getImageView(getActivity(), infos.get(i)
                    .getUrl()));
        }
        // 将第一个ImageView添加进来
        views.add(ViewFactory
                .getImageView(getActivity(), infos.get(0).getUrl()));

        // 设置循环，在调用setData方法前调用
        cycleViewPager.setCycle(true);

        // 在加载数据前设置是否循环
        cycleViewPager.setData(views, infos, this);
        // 设置轮播
        cycleViewPager.setWheel(true);

        // 设置轮播时间，默认5000ms
        cycleViewPager.setTime(5000);
        // 设置圆点指示图标组居中显示，默认靠右
//        cycleViewPager.setIndicatorCenter();
    }


    /**
     * 轮播图处理点击事件
     *
     * @param info
     * @param position
     * @param imageView
     */
    @Override
    public void onImageClick(ADInfo info, int position, View imageView) {

        HomePageAdsPicBean.DataBean.Ads1Bean.AdLocationsBean adLocationsBean = bannerData.get(position - 1);
        String url = adLocationsBean.getUrl().trim();
        List<HomePageAdsPicBean.DataBean.Ads1Bean.AdLocationsBean.ParamsBean> params = adLocationsBean.getParams();
        int size = params.size();
        if (size == 0) {
            SkipToPointActivityUtil.jumpToNoParams(getActivity(), url);
        } else if (size == 1) {
            String value0 = params.get(0).getValue();
            String key0 = params.get(0).getKey();
            SkipToPointActivityUtil.jumpToOneParams(getActivity(), url, key0, value0);
        } else if (size == 2) {
            String key0 = params.get(0).getKey();
            String value0 = params.get(0).getValue();
            String key1 = params.get(1).getKey();
            String value1 = params.get(1).getValue();
            SkipToPointActivityUtil.jumpToTwoParams(getActivity(), url, key0, value0, key1, value1);
        }

    }


    @Override
    public void onDestroy() {
        iv_go_to_top.clearCallBacks();
        super.onDestroy();
    }


    /**
     * 获取当前购物车中商品总数量
     */
    private void getShoppingCartNumber() {

        String url = AppConstant.BASEURL2 + "api/cart/getcartnum";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
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
                        ShoppingCartGoodsNumBean shoppingCartGoodsNumBean = gson.fromJson(strJson, ShoppingCartGoodsNumBean.class);
                        ShoppingCartGoodsNumBean.DataBean data = shoppingCartGoodsNumBean.getData();
                        SPUtils.putString(getActivity(), "TotalNumber", String.valueOf(data.getNum()));
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

    /**
     * 检查更新
     */
    private void checkAppVersion() {

        if (!NetUtil.isNetworkAvailable(getActivity())) {
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }

        String url = AppConstant.BASEURL2 + "api/login/update";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
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
                        CheckAppVersionBean checkAppVersionBean = gson.fromJson(strJson, CheckAppVersionBean.class);
                        CheckAppVersionBean.DataBean data = checkAppVersionBean.getData();
                        int serverVersionCode = (int)Double.parseDouble(data.getCode());
                        String desc = data.getDesc();
                        String downloadUrl = data.getUrl();
                        int versionCode = MyCommUtil.getVersionCode(getActivity());
                        if (versionCode < serverVersionCode) {
                            showDownDialog(desc, downloadUrl);
                        }

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

    /**
     * 弹出提示版本更新的对话框
     *
     * @param desc
     * @param downloadUrl
     */
    private void showDownDialog(String desc, final String downloadUrl) {

        new MyAlertDialog(getActivity()).builder()
                .setTitle("版本更新")
                .setMsg(desc)
                .setCancelable(false)
                .setPositiveButton("马上更新", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        // 下载操作 下载最新apk
                        progressDialog = new ProgressDialog(getActivity(),
                                ProgressDialog.THEME_HOLO_LIGHT);
                        progressDialog.setMessage("正在下载...");
                        progressDialog.setCanceledOnTouchOutside(false);
//                        progressDialog.setCancelable(false);
                        progressDialog
                                .setProgressStyle(ProgressDialog.STYLE_HORIZONTAL);
                        progressDialog.show();
                        progressDialog.setMax(100);
                        downLoadNewVersionApk(downloadUrl);
                    }
                }).show();
    }

    /**
     * 下载新版本的apk安装包
     *
     * @param downloadUrl
     */
    private void downLoadNewVersionApk(String downloadUrl) {
        OkHttpUtils
                .get()
                .url(downloadUrl)
                .build()
                .execute(new FileCallBack(AppConstant.DOWNLOAD_PUBLIC_PATH,
                        "yblb2b.apk") {
                    @Override
                    public void onResponse(File response) {
                        progressDialog.dismiss();
                        File apkFile = response;
                        Intent intent = new Intent();
                        intent.setAction("android.intent.action.VIEW");
                        Uri fileUri = Uri.fromFile(apkFile);
                        intent.setDataAndType(fileUri,
                                "application/vnd.android.package-archive");
                        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                        startActivity(intent);
                    }

                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void inProgress(float progress) {
                        progressDialog.setProgress(Math
                                .round((progress * 100)));

                    }
                });
    }

}
