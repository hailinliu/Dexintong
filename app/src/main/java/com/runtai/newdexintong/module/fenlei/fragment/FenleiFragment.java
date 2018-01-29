package com.runtai.newdexintong.module.fenlei.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
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
import com.runtai.newdexintong.module.fenlei.activity.ProductDetailActivity;
import com.runtai.newdexintong.module.fenlei.adapter.FenleiShaiXuanAdapter;
import com.runtai.newdexintong.module.fenlei.adapter.SortLeftListAdapter;
import com.runtai.newdexintong.module.fenlei.adapter.SortRightListAdapter;
import com.runtai.newdexintong.module.fenlei.bean.SortLeftDataBean;
import com.runtai.newdexintong.module.fenlei.bean.SortRightDataBean;
import com.runtai.newdexintong.module.fenlei.util.Util;
import com.runtai.newdexintong.module.fenlei.view.FooterLayout;
import com.runtai.newdexintong.module.fenlei.view.HeaderLayout;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.homepage.activity.SearchActivity;
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
 * @作者：
 * @日期：2017/2/11时间10:16
 * @描述：分类界面Fragment
 */

public class FenleiFragment extends BaseFragment {

    private ListView chaoshi_left;//左侧ListView列表
    private SortLeftListAdapter adapter1;
    private SortRightListAdapter adapter2;
    private LinearLayout feilei_menu;
    private LinearLayout ll_grid_area;
    private LinearLayout feilei_leibie, feilei_pinpai, feilei_shangpin;
    private ImageView feilei_leibie_image, feilei_pinpai_image, feilei_shangpin_image;
    private ImageView fenlei_sousuo_img;
    private PopupWindow popTele;
    private View layoutTele;
    private GridView menulist;

    private PullToRefreshGridView mPullToRefreshGridView;//右侧GridView列表

    private List<String> list_leibie, list_pinpai, list_shangpin;//类别、品牌、商品列表数据
    private FenleiShaiXuanAdapter adapter;
    private int leibie, pinpai, shangpin = 0;
    private RelativeLayout rl_sortFragment_search;
    private EditText et_search_sortFragment;

    private boolean kinds_select = false;
    private boolean brand_select = false;
    private boolean goods_select = false;

    private int checkedTextColor = Color.parseColor("#FA3D3D");
    private int uncheckedTextColor = Color.parseColor("#333333");
    private TextView tv_kinds_select;
    private TextView tv_brand_select;
    private TextView tv_goods_select;
    /**
     * 选中popuwindow中的某项
     */
    private String selected_content;
    private ImageView iv_search_sortFragment;
    private List<SortLeftDataBean.DataBean.LeftBean> leftSortData;
    private List<SortLeftDataBean.DataBean.BrandsBean> brandsData;
    private List<SortLeftDataBean.DataBean.ThreeBean> categoryData;
    private int pages;
    private List<SortRightDataBean.DataBean.ListBean> listRightData;
    private int currentPage = 2;
    private int select_sort_value;
    private int brand_id;
    private LinearLayout ll_right_not_data_show;
    private int category_id;
    /**
     * 右侧数据每页显示的条目数
     */
    private int pageItemCount = 20;
    private boolean isFirst = true;
    private TextView tv_reload;
    private RelativeLayout rl_no_net;
    private LinearLayout ll_have_net;
    private boolean isFirstHttp = true;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_fenlei);
        View view = getContentView();
        initView(view);
        initDate();
        httpLeftListData(0, 0);
        registerListener();
    }

    private void initDate() {

        list_leibie = new ArrayList<>();
        list_pinpai = new ArrayList<>();
        list_shangpin = new ArrayList<>();

        for (int i = 0; i < 4; i++) {
            if (i == 0) {
                list_shangpin.add("销量由低到高");
            } else if (i == 1) {
                list_shangpin.add("销量由高到低");
            } else if (i == 2) {
                list_shangpin.add("价格由低到高");
            } else {
                list_shangpin.add("价格由高到低");
            }
        }

    }

    private void initView(View view) {

        setSortFragmentTitle(view);
        chaoshi_left = (ListView) view.findViewById(R.id.chaoshi_left);

        feilei_menu = (LinearLayout) view.findViewById(R.id.feilei_menu);
        ll_grid_area = (LinearLayout) view.findViewById(R.id.ll_grid_area);

        mPullToRefreshGridView = (PullToRefreshGridView) view.findViewById(R.id.mPullToRefreshGridView);
        mPullToRefreshGridView.setHeaderLayout(new HeaderLayout(getActivity()));
        mPullToRefreshGridView.setFooterLayout(new FooterLayout(getActivity()));

        ll_right_not_data_show = (LinearLayout) view.findViewById(R.id.ll_right_not_data_show);

        feilei_leibie = (LinearLayout) view.findViewById(R.id.feilei_leibie);
        feilei_pinpai = (LinearLayout) view.findViewById(R.id.feilei_pinpai);
        feilei_shangpin = (LinearLayout) view.findViewById(R.id.feilei_shangpin);
        feilei_leibie_image = (ImageView) view.findViewById(R.id.feilei_leibie_image);
        feilei_pinpai_image = (ImageView) view.findViewById(R.id.feilei_pinpai_image);
        feilei_shangpin_image = (ImageView) view.findViewById(R.id.feilei_shangpin_image);
        tv_kinds_select = (TextView) view.findViewById(R.id.tv_kinds_select);
        tv_brand_select = (TextView) view.findViewById(R.id.tv_brand_select);
        tv_goods_select = (TextView) view.findViewById(R.id.tv_goods_select);

        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
        ll_have_net = (LinearLayout) view.findViewById(R.id.ll_have_net);
    }


    private void registerListener() {

        tv_reload.setOnClickListener(this);
        feilei_leibie.setOnClickListener(this);
        feilei_pinpai.setOnClickListener(this);
        feilei_shangpin.setOnClickListener(this);

        et_search_sortFragment.setOnClickListener(this);
        iv_search_sortFragment.setOnClickListener(this);

        chaoshi_left.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                adapter1.setSelectPosition(position);
                currentPage = 2;
                select_sort_value = 0;
                popPositiong = -1;

                closePopuWindow();
                setSelectStyle(4);
                SortLeftDataBean.DataBean.LeftBean leftBean = leftSortData.get(position);
                int cid_value = leftBean.getId();
                SPUtils.putInt(getActivity(), "left_selected_value", cid_value);
                httpLeftListData(cid_value, position);
                isFirst = true;
                chaoshi_left.smoothScrollToPositionFromTop(position, 0);

            }
        });

        mPullToRefreshGridView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                ToastUtil.showCenterToast(getActivity(), "商品列表选中" + position, 0);
                SortRightDataBean.DataBean.ListBean listBean = listRightData.get(position);
                if (listBean.getStock() > 0 && listBean.getStock() > listBean.getSaleFromCount()) {
                    int itemId = listBean.getItemId();
                    Intent intent = new Intent(getActivity(), ProductDetailActivity.class);
                    intent.putExtra("ItemId", String.valueOf(itemId));
                    intent.putExtra("ActivityId", "0");
                    intent.putExtra("SpecialId", "0");
                    startActivityByIntent(intent);
                }else {
                    ToastUtil.showToast(getActivity(), "抱歉，售罄商品不支持查看详情", Toast.LENGTH_SHORT);
                }
               
            }
        });
        mPullToRefreshGridView.getDrawingCacheBackgroundColor();

        //右侧数据的上拉加载与下拉刷新
        mPullToRefreshGridView.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                //下拉刷新
                listRightData.clear();
                if (select_sort_value == 100) {
                    //类别选择
                    httpRightGoodsData(category_id, "0", "0", "1");
                } else if (select_sort_value == 200) {
                    //品牌选择
                    httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), String.valueOf(brand_id), "0", "1");
                } else if (select_sort_value == 300) {
                    //销量由低到高
                    httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "2", "1");
                } else if (select_sort_value == 301) {
                    //销量由高到低
                    httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "1", "1");
                } else if (select_sort_value == 302) {
                    //价格由低到高
                    httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "4", "1");
                } else if (select_sort_value == 303) {
                    //价格由高到低
                    httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "3", "1");
                } else {
                    httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "0", "1");
                }

                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {

                if (pages > 0 && pages >= currentPage) {
                    //上拉加载更多
                    if (select_sort_value == 100) {
                        //类别选择
                        httpRightGoodsData(category_id, "0", "0", String.valueOf(currentPage));
                    } else if (select_sort_value == 200) {
                        //品牌选择
                        httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0),
                                String.valueOf(brand_id), "0", String.valueOf(currentPage));
                    } else if (select_sort_value == 300) {
                        //销量由低到高
                        httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "2", String.valueOf(currentPage));
                    } else if (select_sort_value == 301) {
                        //销量由高到低
                        httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "1", String.valueOf(currentPage));
                    } else if (select_sort_value == 302) {
                        //价格由低到高
                        httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "4", String.valueOf(currentPage));
                    } else if (select_sort_value == 303) {
                        //价格由高到低
                        httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "3", String.valueOf(currentPage));
                    } else {
                        httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "0", String.valueOf(currentPage));
                    }

                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(getActivity(), "没有更多商品了", Toast.LENGTH_SHORT);
                }

            }
        });
    }


    private void setSortFragmentTitle(View view) {

        RelativeLayout head_back = (RelativeLayout) view.findViewById(R.id.head_back);
        head_back.setVisibility(View.GONE);
        ((TextView) view.findViewById(R.id.tv_title)).setVisibility(View.GONE);
        rl_sortFragment_search = (RelativeLayout) view.findViewById(R.id.rl_sortFragment_search);
        rl_sortFragment_search.setVisibility(View.VISIBLE);
        et_search_sortFragment = (EditText) view.findViewById(R.id.et_search_sortFragment);
        iv_search_sortFragment = (ImageView) view.findViewById(R.id.iv_search_sortFragment);

    }

    @Override
    public void onResume() {
        super.onResume();
        setSelectStyle(3);
    }

    @Override
    public void onFragmentStart() {
        super.onFragmentStart();
//        setSelectStyle(4);
//        httpLeftListData(0, 0);
        adapter2 = null;
    }

    /**
     * 请求左侧分类列表数据
     */
    private void httpLeftListData(final int left_list_cid_value, final int pos_clicked) {

        if (!NetUtil.isNetworkAvailable(getActivity())) {
            ll_have_net.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            ll_have_net.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }
        showLoading();
        String url = AppConstant.BASEURL2 + "api/category/list";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Cid", String.valueOf(left_list_cid_value));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Cid", String.valueOf(left_list_cid_value))
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
                        SortLeftDataBean sortLeftDataBean = gson.fromJson(strJson, SortLeftDataBean.class);
                        if (sortLeftDataBean != null) {

                            SortLeftDataBean.DataBean data = sortLeftDataBean.getData();
                            if (brandsData == null) {

                                brandsData = new ArrayList<SortLeftDataBean.DataBean.BrandsBean>();
                            }
                            brandsData.clear();
                            brandsData.addAll(data.getBrands());
                            if (leftSortData == null) {

                                leftSortData = new ArrayList<SortLeftDataBean.DataBean.LeftBean>();
                            }
                            leftSortData.clear();
                            leftSortData.addAll(data.getLeft());
                            if (categoryData == null) {

                                categoryData = new ArrayList<SortLeftDataBean.DataBean.ThreeBean>();
                            }
                            categoryData.clear();
                            categoryData.addAll(data.getThree());
                            adapter1 = new SortLeftListAdapter(getActivity(), leftSortData);
                            chaoshi_left.setAdapter(adapter1);
                            adapter1.setSelectPosition(pos_clicked);

                            list_pinpai.clear();
                            for (int i = 0; i < brandsData.size(); i++) {
                                list_pinpai.add(brandsData.get(i).getBrandNameCn());
                            }

                            list_leibie.clear();
                            for (int i = 0; i < categoryData.size(); i++) {
                                list_leibie.add(categoryData.get(i).getCateName());
                            }
                            if (listRightData == null) {
                                listRightData = new ArrayList<SortRightDataBean.DataBean.ListBean>();
                            }
                            if (isFirstHttp) {
                                isFirstHttp = false;
                                SPUtils.putInt(getActivity(),"left_selected_value",leftSortData.get(0).getId());
                            }
                            listRightData.clear();
                            httpRightGoodsData(leftSortData.get(pos_clicked).getId(), "0", "0", "1");
                        }
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
                        dismissLoading();
                    } else {
                        ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                        dismissLoading();
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                    dismissLoading();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("homepage", e.getMessage());
                dismissLoading();
            }
        });

    }

    /**
     * 获取右侧分类中的商品
     *
     * @param cid_value  分类编号，默认为0
     * @param bid_value  品牌编号，默认为0
     * @param sort_value 排序号销量排序由高到低1，由低到高2；价格排序由高到低3，由低到高4，默认0
     * @param page       加载的页面页码
     */

    private void httpRightGoodsData(int cid_value, String bid_value, String sort_value, final String page) {

        String url = AppConstant.BASEURL2 + "api/category/products";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Cid", String.valueOf(cid_value));
        map.put("Bid", bid_value);
        map.put("Sort", sort_value);
        map.put("PageSize", String.valueOf(pageItemCount));
        map.put("Page", page);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Cid", String.valueOf(cid_value))
                .addParams("Bid", bid_value)
                .addParams("Sort", sort_value)
                .addParams("PageSize", String.valueOf(pageItemCount))
                .addParams("Page", page)
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
                        SortRightDataBean sortRightDataBean = gson.fromJson(strJson, SortRightDataBean.class);
                        if (sortRightDataBean != null) {

                            SortRightDataBean.DataBean data = sortRightDataBean.getData();
                            if (listRightData == null) {
                                listRightData = new ArrayList<>();
                            }
                            listRightData.addAll(data.getList());
                            pages = data.getPages();
                            int total = data.getTotal();
//                        LogUtil.e("pages", "" + pages);
//                        LogUtil.e("total", "" + total);
                            if (listRightData.size() == 0) {
                                mPullToRefreshGridView.setVisibility(View.GONE);
                                ll_right_not_data_show.setVisibility(View.VISIBLE);
                            } else {
                                mPullToRefreshGridView.setVisibility(View.VISIBLE);
                                ll_right_not_data_show.setVisibility(View.GONE);
                            }
                            LogUtil.e("total_number", String.valueOf(total) + ".....单页数据" + page + "总页数...." + pages);
                            mPullToRefreshGridView.onRefreshComplete();// 刷新UI
                            if (adapter2 == null) {
                                adapter2 = new SortRightListAdapter(getActivity(), listRightData);
                                mPullToRefreshGridView.setAdapter(adapter2);
                            } else {
                                adapter2.setData(listRightData);
                                adapter2.notifyDataSetChanged();
                            }

                        } else if (code == 403) {
                            DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
                        } else {
                            ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                dismissLoading();

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("right_data", e.toString());
                dismissLoading();
            }
        });
    }

    @Override
    public void onFragmentStop() {
        super.onFragmentStop();
        closePopuWindow();
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.et_search_sortFragment://搜索
            case R.id.iv_search_sortFragment:
                startActivityByIntent(SearchActivity.class);
                break;
            case R.id.feilei_leibie://类别选择
                tv_brand_select.setText("品牌选择");
                tv_goods_select.setText("商品选择");
//                popPositiong = leibie;
                popPositiong = -1;
                closePopuWindow();
                if (kinds_select) {
                    closePopuWindow();
                    kinds_select = false;
                    feilei_leibie_image.setImageResource(R.mipmap.down_gray);
                    feilei_leibie_image.setImageResource(R.mipmap.down_red_arrow);
                } else {

                    showPopuWindow(v, list_leibie, 0);
                    kinds_select = true;
                    feilei_leibie_image.setImageResource(R.mipmap.up_red_arrow);
                    tv_kinds_select.setTextColor(checkedTextColor);
                }
                setSelectStyle(0);
//                listRightData.clear();
//                httpRightGoodsData(category_id, "0", "0", "1");
                break;
            case R.id.feilei_pinpai://品牌选择
                tv_kinds_select.setText("类别选择");
                tv_goods_select.setText("商品选择");
//                popPositiong = pinpai;
                popPositiong = -1;
                closePopuWindow();
                if (brand_select) {
                    closePopuWindow();
                    brand_select = false;
                    feilei_pinpai_image.setImageResource(R.mipmap.down_gray);
                    feilei_pinpai_image.setImageResource(R.mipmap.down_red_arrow);
                } else {

                    showPopuWindow(v, list_pinpai, 1);
                    brand_select = true;
                    feilei_pinpai_image.setImageResource(R.mipmap.up_red_arrow);
                    tv_brand_select.setTextColor(checkedTextColor);
                }
                setSelectStyle(1);
//                listRightData.clear();
//                httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), String.valueOf(brand_id), "0", "1");
                break;
            case R.id.feilei_shangpin://商品选择
                tv_kinds_select.setText("类别选择");
                tv_brand_select.setText("品牌选择");
//                popPositiong = shangpin;
                popPositiong = -1;
                closePopuWindow();
                if (goods_select) {
                    closePopuWindow();
                    goods_select = false;
                    feilei_shangpin_image.setImageResource(R.mipmap.down_gray);
                    feilei_shangpin_image.setImageResource(R.mipmap.down_red_arrow);
                } else {
                    showPopuWindow(v, list_shangpin, 2);
                    goods_select = true;
                    feilei_shangpin_image.setImageResource(R.mipmap.up_red_arrow);
                    tv_goods_select.setTextColor(checkedTextColor);
                }
                setSelectStyle(2);
//                listRightData.clear();
//                sort(shangpin);
                break;
            case R.id.tv_reload://重新加载
                setSelectStyle(4);
                httpLeftListData(0, 0);
                break;
            default:
                break;
        }
    }

    /**
     * 设置title处三处选择的样式变化
     *
     * @param flag
     */
    private void setSelectStyle(int flag) {

        if (flag == 0) {
            tv_brand_select.setTextColor(uncheckedTextColor);
            tv_goods_select.setTextColor(uncheckedTextColor);
            feilei_pinpai_image.setImageResource(R.mipmap.down_gray);
            feilei_shangpin_image.setImageResource(R.mipmap.down_gray);
            brand_select = false;
            goods_select = false;
        } else if (flag == 1) {
            tv_kinds_select.setTextColor(uncheckedTextColor);
            tv_goods_select.setTextColor(uncheckedTextColor);
            feilei_leibie_image.setImageResource(R.mipmap.down_gray);
            feilei_shangpin_image.setImageResource(R.mipmap.down_gray);
            kinds_select = false;
            goods_select = false;
        } else if (flag == 2) {
            tv_kinds_select.setTextColor(uncheckedTextColor);
            tv_brand_select.setTextColor(uncheckedTextColor);
            feilei_leibie_image.setImageResource(R.mipmap.down_gray);
            feilei_pinpai_image.setImageResource(R.mipmap.down_gray);
            kinds_select = false;
            brand_select = false;
        } else if (flag == 3) {
            tv_kinds_select.setTextColor(uncheckedTextColor);
            tv_brand_select.setTextColor(uncheckedTextColor);
            tv_goods_select.setTextColor(uncheckedTextColor);
            feilei_leibie_image.setImageResource(R.mipmap.down_gray);
            feilei_pinpai_image.setImageResource(R.mipmap.down_gray);
            feilei_shangpin_image.setImageResource(R.mipmap.down_gray);
            kinds_select = false;
            brand_select = false;
            goods_select = false;
        } else {
            tv_kinds_select.setText("类别选择");
            tv_brand_select.setText("品牌选择");
            tv_goods_select.setText("商品选择");
            tv_kinds_select.setTextColor(uncheckedTextColor);
            tv_brand_select.setTextColor(uncheckedTextColor);
            tv_goods_select.setTextColor(uncheckedTextColor);
            feilei_leibie_image.setImageResource(R.mipmap.down_gray);
            feilei_pinpai_image.setImageResource(R.mipmap.down_gray);
            feilei_shangpin_image.setImageResource(R.mipmap.down_gray);
            kinds_select = false;
            brand_select = false;
            goods_select = false;
        }
    }

    private int popPositiong = 0;

    /**
     * 创建popuwindow
     *
     * @param view
     * @param list
     * @param choose
     */
    private void showPopuWindow(View view, final List<String> list, final int choose) {
        //隐藏键盘
        new Util(getActivity()).hintKeyBoard(view);
//        if (popTele != null && popTele.isShowing()) {
//            popTele.dismiss();
//            popTele = null;
//        } else {
        layoutTele = getActivity().getLayoutInflater().inflate(R.layout.pop_menulist, null);
        menulist = (GridView) layoutTele.findViewById(R.id.menulist);
        if (list != null) {
            adapter = new FenleiShaiXuanAdapter(list, getActivity());
            adapter.setSelectPosition(popPositiong);
            menulist.setAdapter(adapter);
        }
        // 点击列表中item的处理
        menulist.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> arg0, View view, int index, long arg3) {
                adapter.setSelectPosition(index);

                closePopuWindow();
                setSelectStyle(3);
                popPositiong = index;
//                ToastUtil.showCenterToast(getActivity(), "选中" + index, 0);
                if (list != null) {
                    selected_content = list.get(index);
                }
                listRightData.clear();
                currentPage = 2;
                isFirst = true;
                if (choose == 0) {
                    //类别选择
                    select_sort_value = 100;
                    leibie = popPositiong;
                    tv_kinds_select.setText(selected_content);
                    category_id = categoryData.get(index).getId();
                    httpRightGoodsData(category_id, "0", "0", "1");
                } else if (choose == 1) {
                    //品牌选择
                    select_sort_value = 200;
                    pinpai = popPositiong;
                    tv_brand_select.setText(selected_content);
                    brand_id = brandsData.get(index).getId();
                    httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), String.valueOf(brand_id), "0", "1");
                } else if (choose == 2) {
                    //商品选择，包括综合排序，价格由低到高排序，价格由高到低排序
                    shangpin = popPositiong;
                    tv_goods_select.setText(selected_content);
//                    销量排序由高到低1，由低到高2；价格排序由高到低3，由低到高4
                    sort(index);

                }
            }
        });

        popTele = new PopupWindow(layoutTele, feilei_menu.getWidth(), ll_grid_area.getHeight(), true);
//            ColorDrawable cd = new ColorDrawable(0);
//            popTele.setBackgroundDrawable(cd);
        popTele.setAnimationStyle(R.style.PopupAnimation);
//            popTele.update();
//            popTele.setInputMethodMode(PopupWindow.INPUT_METHOD_NEEDED);
//            popTele.setTouchable(true); // 设置popupwindow可点击
//        popTele.setOutsideTouchable(true); // 设置popupwindow外部可点击
        popTele.setFocusable(false); // 获取焦点

        layoutTele.setFocusable(true);
        layoutTele.setFocusableInTouchMode(true);
        layoutTele.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closePopuWindow();
                setSelectStyle(3);
                return false;
            }
        });
        // 设置popupwindow的位置
        popTele.showAsDropDown(feilei_menu, 0, 0);
//        }
    }

    /**
     * 排序
     *
     * @param index 销量排序由高到低1，由低到高2；价格排序由高到低3，由低到高4
     */
    private void sort(int index) {
        if (index == 0) {//销量由低到高
            select_sort_value = 300;
            httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "2", "1");
        } else if (index == 1) {//销量由高到低
            select_sort_value = 301;
            httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "1", "1");
        } else if (index == 2) {//价格由低到高
            select_sort_value = 302;
            httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "4", "1");
        } else {//价格由高到低
            select_sort_value = 303;
            httpRightGoodsData(SPUtils.getInt(getActivity(), "left_selected_value", 0), "0", "3", "1");
        }
    }

    /**
     * 关闭popuwindow
     */
    private void closePopuWindow() {
        if (popTele != null && popTele.isShowing()) {
            popTele.dismiss();
            popTele = null;
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

            mPullToRefreshGridView.onRefreshComplete();// 刷新UI

        }
    }

}
