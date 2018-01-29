package com.runtai.newdexintong.module.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshGridView;
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
import com.runtai.newdexintong.module.fenlei.activity.ProductDetailActivity;
import com.runtai.newdexintong.module.fenlei.view.FooterLayout;
import com.runtai.newdexintong.module.fenlei.view.HeaderLayout;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.homepage.adapter.SearchResultAdapter;
import com.runtai.newdexintong.module.homepage.adapter.SelectBrandAdapter;
import com.runtai.newdexintong.module.homepage.bean.FindGoodsBean;
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
 * 搜索结果页面
 */
public class SearchResultAcivity extends BaseActivity
        implements SearchResultAdapter.IupdateCartTotalNumber {

    private RelativeLayout head_back;
    private EditText et_search2;
    private TextView tv_general_sort;
    private TextView tv_price_sort;
    private TextView tv_brand_sort;
    private RelativeLayout rl_general;
    private RelativeLayout rl_price;
    private RelativeLayout rl_brand;
    private int checkedColor = Color.parseColor("#FF2D48");
    private int uncheckedColor = Color.parseColor("#333333");
    List<FindGoodsBean> mData;
    private PullToRefreshGridView gridview_goods_sort;
    private ImageView iv_price_sort;
    private boolean clickPriceSort = false;
    private boolean clickBrandSort = false;
    private PopupWindow popupWindow;
    private LinearLayout ll_sort_kinds;
    private TextView tv_brand_name;
    private int popuWindow_clicked_position = 0;
    private SelectBrandAdapter adapter;
    private ImageView iv_brand_sort;
    private ImageView iv_shoppingcar;
    private TextView tv_goods_amount;
    private RelativeLayout rl_no_data;
    private RelativeLayout rl_have_data;
    private String goodsname;
    private SearchResultAdapter searchResultAdapter;
    private List<SearchResultBean.DataBean.BrandsBean> brandsData;
    private List<SearchResultBean.DataBean.ListBean> listData;
    /**
     * 总页数
     */
    private int pages;
    /**
     * 当前页码
     */
    private int currentPage = 2;
    private int title_category_value;
    private int brand_bid;
    /**
     * 每页显示的条目数量
     */
    private int pageItemCount = 20;
    private boolean isFirst = true;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search_result);
        GetCartTotalNumUtil.getShoppingCartNumber(this);
        Intent intent = getIntent();
        if ("api/product/search".equals(intent.getStringExtra("mUrl"))) {
            goodsname = intent.getStringExtra("paramValue0");
        } else {
            goodsname = intent.getStringExtra("goodsname");
        }
        initView();
        registerListener();
        doHttp("1", "0", "0");
    }

    private void initView() {

        if (brandsData == null) {
            brandsData = new ArrayList<SearchResultBean.DataBean.BrandsBean>();
        }
        if (listData == null) {
            listData = new ArrayList<SearchResultBean.DataBean.ListBean>();
        }

        setActivityTitle();

        ll_sort_kinds = (LinearLayout) findViewById(R.id.ll_sort_kinds);
        //综合排序
        rl_general = (RelativeLayout) findViewById(R.id.rl_general);
        tv_general_sort = (TextView) findViewById(R.id.tv_general_sort);
        //价格排序
        rl_price = (RelativeLayout) findViewById(R.id.rl_price);
        tv_price_sort = (TextView) findViewById(R.id.tv_price_sort);
        iv_price_sort = (ImageView) findViewById(R.id.iv_price_sort);
        //品牌选择
        rl_brand = (RelativeLayout) findViewById(R.id.rl_brand);
        tv_brand_sort = (TextView) findViewById(R.id.tv_brand_sort);
        iv_brand_sort = (ImageView) findViewById(R.id.iv_brand_sort);

        gridview_goods_sort = (PullToRefreshGridView) findViewById(R.id.gridview_goods_sort);
        gridview_goods_sort.setHeaderLayout(new HeaderLayout(this));
        gridview_goods_sort.setFooterLayout(new FooterLayout(this));
        iv_shoppingcar = (ImageView) findViewById(R.id.iv_shoppingcar);
        rl_no_data = (RelativeLayout) findViewById(R.id.rl_no_data);
        rl_have_data = (RelativeLayout) findViewById(R.id.rl_have_data);
        tv_goods_amount = (TextView) findViewById(R.id.tv_goods_amount);
        String totalNumber = SPUtils.getString(this, "TotalNumber", "");
        if (!"".equals(totalNumber) && totalNumber.length() > 0) {
            tv_goods_amount.setText(totalNumber);
        }
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
    }

    /**
     * @param currentPage
     * @param brandNumber
     * @param sortValue
     */
    private void doHttp(String currentPage, String brandNumber, String sortValue) {

        if (!NetUtil.isNetworkAvailable(this)) {
            ll_sort_kinds.setVisibility(View.GONE);
            rl_have_data.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            ll_sort_kinds.setVisibility(View.VISIBLE);
            rl_have_data.setVisibility(View.VISIBLE);
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
        map.put("Q", StringUtil.getUrlEncodeResult(goodsname));
        map.put("Bid", brandNumber);
        map.put("Sort", sortValue);
        map.put("Page", currentPage);
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Q", goodsname)
                .addParams("Bid", brandNumber)
                .addParams("Sort", sortValue)
                .addParams("Page", currentPage)
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

                            brandsData = data.getBrands();
                            if (listData == null) {
                                listData = new ArrayList<SearchResultBean.DataBean.ListBean>();
                            }
                            listData.addAll(data.getList());
                            pages = data.getPages();
                            int total = data.getTotal();
                            checkDataAndShow();
                            gridview_goods_sort.onRefreshComplete();// 停止刷新
                            if (searchResultAdapter == null) {
                                searchResultAdapter = new SearchResultAdapter(SearchResultAcivity.this, listData);
                                gridview_goods_sort.setAdapter(searchResultAdapter);
                            } else {
                                searchResultAdapter.setData(listData);
                                searchResultAdapter.notifyDataSetChanged();
                            }
                            searchResultAdapter.setIupdateCartTotalNumber(SearchResultAcivity.this);
                        }
                    } else {
                        ToastUtil.showToast(SearchResultAcivity.this, msg, Toast.LENGTH_SHORT);
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

    private void registerListener() {

        tv_reload.setOnClickListener(this);
        head_back.setOnClickListener(this);
        et_search2.setOnClickListener(this);
        rl_general.setOnClickListener(this);
        rl_price.setOnClickListener(this);
        rl_brand.setOnClickListener(this);

        iv_shoppingcar.setOnClickListener(this);
        //默认选中综合排序
        rl_general.callOnClick();
        gridview_goods_sort.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                int stock = listData.get(position).getStock();
                int saleFromCount = listData.get(position).getSaleFromCount();
                if (stock > 0 && stock > saleFromCount) {
                    Intent intent = new Intent(SearchResultAcivity.this, ProductDetailActivity.class);
                    intent.putExtra("ItemId", String.valueOf(listData.get(position).getItemId()));
                    intent.putExtra("ActivityId", "0");
                    intent.putExtra("SpecialId", "0");
                    startActivityByIntent(intent);
                } else {
                    ToastUtil.showToast(SearchResultAcivity.this, "抱歉，售罄商品不支持查看详情", Toast.LENGTH_SHORT);
                }

            }
        });

        gridview_goods_sort.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<GridView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<GridView> refreshView) {
                //下拉刷新
                listData.clear();
                if (title_category_value == 100) {
                    //综合排序
                    doHttp("1", "0", "0");
                } else if (title_category_value == 201) {
                    //价格由低到高排序
                    doHttp("1", "0", "4");
                } else if (title_category_value == 202) {
                    //价格由高到低排序
                    doHttp("1", "0", "3");
                } else {
                    //品牌选择
                    doHttp("1", String.valueOf(brand_bid), "0");
                }
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<GridView> refreshView) {
                //上拉加载更多
                if (pages > 0 && pages >= currentPage) {
                    if (title_category_value == 100) {
                        //综合排序
                        doHttp(String.valueOf(currentPage), "0", "0");
                    } else if (title_category_value == 201) {
                        //价格由低到高排序
                        doHttp(String.valueOf(currentPage), "0", "4");
                    } else if (title_category_value == 202) {
                        //价格由高到低排序
                        doHttp(String.valueOf(currentPage), "0", "3");
                    } else {
                        //品牌选择
                        doHttp(String.valueOf(currentPage), String.valueOf(brand_bid), "0");
                    }
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(SearchResultAcivity.this, "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }
        });
    }

    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setVisibility(View.GONE);
        RelativeLayout rl_search_result = (RelativeLayout) findViewById(R.id.rl_search_result);
        rl_search_result.setVisibility(View.VISIBLE);
        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.GONE);

        et_search2 = (EditText) findViewById(R.id.et_search2);
        et_search2.setFocusable(false);
        et_search2.setText(goodsname);
    }

    private void clearChoise() {
        tv_general_sort.setTextColor(uncheckedColor);
        tv_price_sort.setTextColor(uncheckedColor);
        tv_brand_sort.setTextColor(uncheckedColor);
    }


    /**
     * 检查数据并且展示页面
     */
    private void checkDataAndShow() {

        if (listData.size() == 0) {
            ll_sort_kinds.setVisibility(View.GONE);
            rl_have_data.setVisibility(View.GONE);
            rl_no_data.setVisibility(View.VISIBLE);
        } else {
            ll_sort_kinds.setVisibility(View.VISIBLE);
            rl_have_data.setVisibility(View.VISIBLE);
            rl_no_data.setVisibility(View.GONE);
        }
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.et_search2://搜索
                onBackPressed();
                break;
            case R.id.rl_general://综合排序
                title_category_value = 100;
                tv_brand_sort.setText("品牌选择");
                clearChoise();
                tv_general_sort.setTextColor(checkedColor);
                tv_price_sort.setText("价格排序");
                closePopupWindow();
                showLoading();
                listData.clear();
                if (isFirst) {
                    isFirst = false;
                } else {
                    doHttp("1", "0", "0");
                }
                iv_price_sort.setImageResource(R.mipmap.down_gray);
                iv_brand_sort.setImageResource(R.mipmap.down_gray);
                clickPriceSort = false;
                break;
            case R.id.rl_price://价格排序
                tv_brand_sort.setText("品牌选择");
                clearChoise();
                tv_price_sort.setTextColor(checkedColor);
                closePopupWindow();
                if (clickPriceSort) {
                    iv_price_sort.setImageResource(R.mipmap.down_red_arrow);
                    clickPriceSort = false;

                } else {
                    iv_price_sort.setImageResource(R.mipmap.up_red_arrow);
                    showPopuwindow(R.layout.popuwindow_price_sort, 0);
                    clickPriceSort = true;
                }

                iv_brand_sort.setImageResource(R.mipmap.down_gray);
                break;
            case R.id.rl_brand://品牌选择

                clearChoise();
                tv_brand_sort.setTextColor(checkedColor);

                if (clickPriceSort) {
                    closePopupWindow();
                }
                if (clickBrandSort) {
                    closePopupWindow();
                    iv_brand_sort.setImageResource(R.mipmap.down_red_arrow);
                } else {
                    showPopuwindow(R.layout.popuwindow_search_brand_selected, 1);
                    iv_brand_sort.setImageResource(R.mipmap.up_red_arrow);
                    clickBrandSort = true;
                }
                clickPriceSort = false;
                iv_price_sort.setImageResource(R.mipmap.down_gray);

                break;
            case R.id.iv_shoppingcar://点击购物车图标
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("tag", "SearchResultAcivity");
                startActivityByIntent(intent);
                break;
            case R.id.tv_reload:
                //默认选中综合排序
                rl_general.callOnClick();
                doHttp("1", String.valueOf(brand_bid), "0");
                break;
        }
    }

    /**
     * 弹出的popuwindow
     *
     * @param resId 资源文件id
     * @param flag  0 表示价格排序的popuwindow
     *              1表示品牌选择的popuwindow
     */
    private void showPopuwindow(int resId, final int flag) {

        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowView = inflater.inflate(resId, null);
        popupWindow = new PopupWindow(popupWindowView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);

        popupWindow.setAnimationStyle(R.style.PopupAnimation);
        popupWindow.setFocusable(false);
        popupWindowView.setFocusable(true);
        popupWindowView.setFocusableInTouchMode(true);

        if (flag == 1) {
            //品牌选择的popuwindow
            tv_brand_name = (TextView) popupWindowView.findViewById(R.id.tv_brand_name);

            GridView gv_brand_selected = (GridView) popupWindowView.findViewById(R.id.gv_brand_selected);
            adapter = new SelectBrandAdapter(this, brandsData);
            adapter.setSelectPosition(popuWindow_clicked_position);
            gv_brand_selected.setAdapter(adapter);
            gv_brand_selected.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                    title_category_value = 300;
                    adapter.setSelectPosition(position);
                    SearchResultBean.DataBean.BrandsBean brandsBean = brandsData.get(position);
                    closePopupWindow();
                    iv_brand_sort.setImageResource(R.mipmap.down_red_arrow);
                    tv_brand_sort.setText(brandsBean.getBrand());
                    tv_price_sort.setText("价格排序");
                    popuWindow_clicked_position = position;
                    listData.clear();
                    showLoading();
                    brand_bid = brandsBean.getBid();
                    doHttp("1", String.valueOf(brand_bid), "0");
                }
            });

        } else {
            //价格排序的popuwindow
            TextView tv_high_to_low = (TextView) popupWindowView.findViewById(R.id.tv_high_to_low);
            TextView tv_low_to_high = (TextView) popupWindowView.findViewById(R.id.tv_low_to_high);
            tv_high_to_low.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title_category_value = 202;
                    closePopupWindow();
                    iv_price_sort.setImageResource(R.mipmap.down_red_arrow);
                    tv_price_sort.setText("由高到低");
                    clickPriceSort = false;
                    showLoading();
                    listData.clear();
                    doHttp("1", "0", "3");
                }
            });
            tv_low_to_high.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    title_category_value = 201;
                    closePopupWindow();
                    iv_price_sort.setImageResource(R.mipmap.down_red_arrow);
                    tv_price_sort.setText("由低到高");
                    clickPriceSort = false;
                    showLoading();
                    listData.clear();
                    doHttp("1", "0", "4");
                }
            });

        }

        //点击周边关闭popuwindow
        popupWindowView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closePopupWindow();
                if (clickPriceSort) {
                    iv_brand_sort.setImageResource(R.mipmap.down_gray);
                } else {

                    iv_brand_sort.setImageResource(R.mipmap.down_red_arrow);
                }
                if (!clickBrandSort || clickPriceSort) {
                    iv_price_sort.setImageResource(R.mipmap.down_gray);
                }

                if (clickPriceSort) {
                    iv_price_sort.setImageResource(R.mipmap.down_red_arrow);
                }
                clickPriceSort = false;

                return false;
            }
        });

        popupWindowView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePopupWindow();
                    iv_brand_sort.setImageResource(R.mipmap.down_gray);
                    clickPriceSort = false;
                }
                return false;
            }
        });

        //显示PopupWindow
        popupWindow.showAsDropDown(ll_sort_kinds, 0, 0);
    }

    /**
     * 关闭popuwindow
     */
    private void closePopupWindow() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
        clickBrandSort = false;
    }

    @Override
    public void updateTotalNum(String totalNum) {

        tv_goods_amount.setText(totalNum);

    }

    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            gridview_goods_sort.onRefreshComplete();// 刷新UI
        }
    }
}
