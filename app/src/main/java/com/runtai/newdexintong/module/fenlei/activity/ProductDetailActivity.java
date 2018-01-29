package com.runtai.newdexintong.module.fenlei.activity;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.ImageLoadUtil;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.NetUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.fenlei.adapter.Product_XQ_Adapter;
import com.runtai.newdexintong.module.fenlei.adapter.ViewPager_Adapter;
import com.runtai.newdexintong.module.fenlei.bean.ChaoShiModelBean;
import com.runtai.newdexintong.module.fenlei.bean.EditShoppingCartGoodsBean;
import com.runtai.newdexintong.module.fenlei.bean.ProductDetailBean;
import com.runtai.newdexintong.module.fenlei.util.ScreenUtils;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.homepage.bean.OrdinaryGoodsDetailBean;
import com.runtai.newdexintong.module.homepage.utils.GetCartTotalNumUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cn.sharesdk.framework.Platform;
import cn.sharesdk.framework.PlatformActionListener;
import cn.sharesdk.onekeyshare.OnekeyShare;
import okhttp3.Request;

/**
 * @作者：rhf
 * @日期：2017/3/31时间15:02
 * @描述：商品详情页面
 */

public class ProductDetailActivity extends BaseActivity {

    private TextView add_jinhuodan;
    private TextView pro_xq_zongjia;
    private List<ChaoShiModelBean> list;
    private Product_XQ_Adapter adapter;
    private WebView pro_xq_web;
    private int screen_height;
    private int screen_width;
    private ViewPager pro_xq_vp;
    private int position;
    private TextView tv_fenzi, tv_fenmu;
    private LinearLayout viewpager_zhishiqi;
    ArrayList<String> list_vp = new ArrayList<>();
    private ViewPager_Adapter vp_adapter;

    private TextView tv_current_price;//单价
    private RelativeLayout rl_shoppingcar_icon;
    private String itemId;
    private String activityId;
    private String specialId;
    private TextView tv_sale_from_count;
    private TextView tv_name;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
//                    tv_stock_count.setText(String.valueOf(item.getStock()));
                    tv_sale_from_count.setText(String.valueOf(item.getSaleFromCount()));
                    tv_name.setText(item.getName());
                    tv_spec_number.setText(String.valueOf(item.getSpec()));
                    tv_spec_unit.setText(item.getUnit());
                    tv_current_price.setText(StringUtil.strToDouble_new(String.valueOf(item.getPrice())));
//                    tv_create_time.setText(item.get);
                    if (item.isCanReturn()) {
                        iscan_return_goods.setText("支持");
                    } else {
                        iscan_return_goods.setText("不支持");
                    }

                    tv_order_multiple.setText(String.valueOf(item.getSaleIncreaseCount()));
                    ImageLoader.getInstance().displayImage(item.getPhoto(), iv_goodsdetail_pic,
                            ImageLoadUtil.getBannerOptions(), null);
                    tv_create_time.setText(item.getMfd());
                    if (item.isDirect()) {
                        //直配商品
                        tv_isdirect.setText("是");
                    } else {
                        tv_isdirect.setText("否");
                    }
                    break;
            }
        }
    };
    private ProductDetailBean.DataBean.ItemBean item;
    private TextView tv_spec_number;
    private TextView tv_spec_unit;
    private TextView iscan_return_goods;
    private TextView tv_order_multiple;
    private TextView tv_goods_amount;
    private ImageView iv_goodsdetail_pic;
    private TextView tv_reload;
    private RelativeLayout rl_no_net;
    private RelativeLayout rl_content;
    private ScrollView mScrollView;
    private TextView tv_create_time;
    private TextView tv_isdirect;
    private RelativeLayout rl_share;
    private ProductDetailBean.DataBean data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_productdetail);
        GetCartTotalNumUtil.getShoppingCartNumber(this);
        initView();
        Intent intent = getIntent();

        activityId = intent.getStringExtra("ActivityId");
        specialId = intent.getStringExtra("SpecialId");
        String mUrl = intent.getStringExtra("mUrl");
        if ("api/product/detail".equals(mUrl)) {
            itemId = intent.getStringExtra("paramValue0");
            activityId = "0";
            specialId = "0";
        } else {
            itemId = intent.getStringExtra("ItemId");
        }

        if (itemId != null && activityId != null && specialId != null) {

            httpData();
        }


        screen_width = ScreenUtils.getScreenWidth(this);
        screen_height = ScreenUtils.getScreenHeight(this);
        Log.e("screen_width", "screen_width" + screen_width);

        ViewGroup.LayoutParams lp = add_jinhuodan.getLayoutParams();
        lp.width = screen_width / 2;
        add_jinhuodan.setLayoutParams(lp);

//        http();
    }

    @SuppressLint("SetJavaScriptEnabled")
    private void initView() {
        setActivityTitle();

        viewpager_zhishiqi = (LinearLayout) findViewById(R.id.viewpager_zhishiqi);
        viewpager_zhishiqi.setAlpha(0.5f);

        tv_fenzi = (TextView) findViewById(R.id.tv_fenzi);
        tv_fenmu = (TextView) findViewById(R.id.tv_fenmu);

        add_jinhuodan = (TextView) findViewById(R.id.add_jinhuodan);
        add_jinhuodan.setOnClickListener(this);
        pro_xq_zongjia = (TextView) findViewById(R.id.pro_xq_zongjia);

        pro_xq_web = (WebView) findViewById(R.id.pro_xq_web);
        WebSettings webSettings = pro_xq_web.getSettings();
        pro_xq_web.getSettings().setJavaScriptEnabled(true);
        webSettings.setUseWideViewPort(true);
        webSettings.setLayoutAlgorithm(WebSettings.LayoutAlgorithm.NORMAL);
        dismissLoading();
        pro_xq_web.setWebViewClient(new MyWebViewClient());
        pro_xq_web.getSettings().setSupportZoom(false);
        /*设置不可点击*/
        pro_xq_web.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                return true;
            }
        });

        pro_xq_vp = (ViewPager) findViewById(R.id.pro_xq_vp);

        tv_current_price = (TextView) findViewById(R.id.tv_current_price);
        tv_sale_from_count = (TextView) findViewById(R.id.tv_sale_from_count);
        tv_name = (TextView) findViewById(R.id.tv_name);
        tv_spec_number = (TextView) findViewById(R.id.tv_spec_number);
        tv_spec_unit = (TextView) findViewById(R.id.tv_spec_unit);
        iscan_return_goods = (TextView) findViewById(R.id.iscan_return_goods);
        tv_order_multiple = (TextView) findViewById(R.id.tv_order_multiple);
        tv_goods_amount = (TextView) findViewById(R.id.tv_goods_amount);
        String totalNumber = SPUtils.getString(this, "TotalNumber", "");
        if (!"".equals(totalNumber) && totalNumber.length() > 0) {
            tv_goods_amount.setText(totalNumber);
        }
        rl_shoppingcar_icon = (RelativeLayout) findViewById(R.id.rl_shoppingcar_icon);
        rl_shoppingcar_icon.setOnClickListener(this);
        iv_goodsdetail_pic = (ImageView) findViewById(R.id.iv_goodsdetail_pic);

        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        tv_reload.setOnClickListener(this);
        rl_content = (RelativeLayout) findViewById(R.id.rl_content);
        mScrollView = (ScrollView) findViewById(R.id.mScrollView);
        tv_create_time = (TextView) findViewById(R.id.tv_create_time);
        tv_isdirect = (TextView) findViewById(R.id.tv_isdirect);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        RelativeLayout head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("商品详情");
        rl_share = (RelativeLayout) findViewById(R.id.rl_share);
        rl_share.setVisibility(View.VISIBLE);
        rl_share.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.add_jinhuodan://加入购物车
                getGoodsDetail(itemId);
                break;

            case R.id.rl_shoppingcar_icon://点击购物车图标
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("tag", "SearchResultAcivity");
                startActivityByIntent(intent);
                finish();
                break;
            case R.id.tv_reload://重新加载
                httpData();
                break;
            case R.id.rl_share://分享
                showShare();
                break;
            default:
                break;
        }
    }

    /**
     * 分享
     */
    private void showShare() {

        OnekeyShare oks = new OnekeyShare();
        //关闭sso授权
        oks.disableSSOWhenAuthorize();

        // title标题，印象笔记、邮箱、信息、微信、人人网和QQ空间等使用
        String name = item.getName();
        oks.setTitle(name);
        // titleUrl是标题的网络链接，QQ和QQ空间等使用
        oks.setTitleUrl(AppConstant.BASEURL_SHARE+"product/detail/"+ itemId);
        oks.setImageUrl(item.getPhoto());
// text是分享文本，所有平台都需要这个字段
        oks.setText(AppConstant.BASEURL_SHARE+"product/detail/"+ itemId);
// imagePath是图片的本地路径，Linked-In以外的平台都支持此参数
//oks.setImagePath("/sdcard/test.jpg");//确保SDcard下面存在此张图片
// url仅在微信（包括好友和朋友圈）中使用
        oks.setUrl(AppConstant.BASEURL_SHARE+"product/detail/"+ itemId);
       
// comment是我对这条分享的评论，仅在人人网和QQ空间使用
        oks.setComment("我是测试评论文本");
// site是分享此内容的网站名称，仅在QQ空间使用
        oks.setSite(getString(R.string.app_name));
// siteUrl是分享此内容的网站地址，仅在QQ空间使用
        oks.setSiteUrl(AppConstant.BASEURL_SHARE+"product/detail/"+ itemId);

        oks.setCallback(new PlatformActionListener() {
            @Override
            public void onComplete(Platform platform, int i, HashMap<String, Object> hashMap) {
//                if (platform.getName().equals(SinaWeibo.NAME)){
//
//                }
                Log.i("ssss","onComplete");
                ToastUtil.showToast(ProductDetailActivity.this,"分享成功",Toast.LENGTH_SHORT);
            }

            @Override
            public void onError(Platform platform, int i, Throwable throwable) {
                Log.i("ssss","onError"+throwable);
                throwable.printStackTrace();
            }

            @Override
            public void onCancel(Platform platform, int i) {
                Log.i("ssss","oncancel");
            }
        });

// 启动分享GUI
        oks.show(this);
    }

    class MyWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, String url) {
            view.loadUrl(url);
            return true;
        }
    }

    /**
     * 请求数据
     */
    private void httpData() {

        if (!NetUtil.isNetworkAvailable(this)) {
            mScrollView.setVisibility(View.GONE);
            rl_content.setVisibility(View.GONE);
            rl_shoppingcar_icon.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            mScrollView.setVisibility(View.VISIBLE);
            rl_content.setVisibility(View.VISIBLE);
            rl_shoppingcar_icon.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/product/detail";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("ItemId", itemId);
        map.put("SpecialId", specialId);
        map.put("ActivityId", activityId);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("ItemId", itemId)
                .addParams("SpecialId", specialId)
                .addParams("ActivityId", activityId)
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
                        ProductDetailBean productDetailBean = gson.fromJson(strJson, ProductDetailBean.class);
                        data = productDetailBean.getData();
                        item = data.getItem();
                        mHandler.sendEmptyMessage(10);
                    } else if (code == 403) {
                        DialogUtil.showDialog(ProductDetailActivity.this,
                                getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(ProductDetailActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 读取
     */
    private void getGoodsDetail(String idValue) {

        if (!NetUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/cart/item";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(ProductDetailActivity.this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("ItemId", idValue);
        map.put("SpecialId", specialId);
        map.put("ActivityId", activityId);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("ItemId", idValue)
                .addParams("SpecialId", specialId)
                .addParams("ActivityId", activityId)
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
                        OrdinaryGoodsDetailBean getGoodsDetailBean = gson.fromJson(strJson, OrdinaryGoodsDetailBean.class);
                        OrdinaryGoodsDetailBean.DataBean data = getGoodsDetailBean.getData();
                        showPopopwindow(data);

                    } else if (code == 403) {
                        DialogUtil.showDialog(ProductDetailActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(ProductDetailActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
            }
        });
    }

    private PopupWindow popupWindow;
    private View popupWindowView;
    private TextView tv_edit_goods_number;
    private ImageView iv_shoppingcar_goods_pic;
    private TextView item_goodsName;
    private TextView item_goodsStanded;
    private TextView item_goodsPrice;
    private TextView tv_min_number;
    private TextView tv_stock_number;
    private TextView tv_limit_number;
    private TextView tv_total_goods_number;
    private TextView tv_total_price;
    private TextView tv_not_enough;
    private RelativeLayout rl_total;
    private double price;

    /**
     * 显示PopWindow
     */
    private void showPopopwindow(OrdinaryGoodsDetailBean.DataBean mData) {

        closePopupWindow();
        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.popuwindow_add_shoppingcar, null);

        popupWindow = new PopupWindow(popupWindowView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        Button btn_confirm_add = (Button) popupWindowView.findViewById(R.id.btn_confirm_add);
        iv_shoppingcar_goods_pic = (ImageView) popupWindowView.findViewById(R.id.iv_shoppingcar_goods_pic);
        ImageView iv_picture = (ImageView) popupWindowView.findViewById(R.id.iv_picture);
        item_goodsName = (TextView) popupWindowView.findViewById(R.id.item_goodsName);
        item_goodsStanded = (TextView) popupWindowView.findViewById(R.id.item_goodsStanded);
        TextView tv_goods_spec_unit = (TextView) popupWindowView.findViewById(R.id.tv_goods_spec_unit);
        item_goodsPrice = (TextView) popupWindowView.findViewById(R.id.item_goodsPrice);
        tv_min_number = (TextView) popupWindowView.findViewById(R.id.tv_min_number);

        tv_stock_number = (TextView) popupWindowView.findViewById(R.id.tv_stock_number);
        tv_limit_number = (TextView) popupWindowView.findViewById(R.id.tv_limit_number);
        tv_edit_goods_number = (TextView) popupWindowView.findViewById(R.id.tv_edit_goods_number);
        tv_not_enough = (TextView) popupWindowView.findViewById(R.id.tv_not_enough);
        TextView tv_date = (TextView) popupWindowView.findViewById(R.id.tv_date);

        tv_total_goods_number = (TextView) popupWindowView.findViewById(R.id.tv_total_goods_number);
        tv_total_price = (TextView) popupWindowView.findViewById(R.id.tv_total_price);
        ImageView iv_reduce = (ImageView) popupWindowView.findViewById(R.id.iv_reduce);
        ImageView iv_add = (ImageView) popupWindowView.findViewById(R.id.iv_add);
        rl_total = (RelativeLayout) popupWindowView.findViewById(R.id.rl_total);
        RelativeLayout rl_popuwindow = (RelativeLayout) popupWindowView.findViewById(R.id.rl_popuwindow);


        item_goodsName.setText(mData.getName());
        item_goodsStanded.setText(String.valueOf(mData.getSpec()));
        tv_goods_spec_unit.setText(mData.getUnit());
        final double price = mData.getPrice();
        item_goodsPrice.setText(StringUtil.strToDouble_new(String.valueOf(price)));
        final int saleFromCount = mData.getSaleFromCount();//最小起订量
        tv_min_number.setText(String.valueOf(saleFromCount));
        tv_edit_goods_number.setText(String.valueOf(mData.getAlreadyItem()));
        tv_total_goods_number.setText(String.valueOf(mData.getAlreadyItem()));
        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(mData.getAlreadyItem() * price)));
        tv_date.setText(mData.getMfd());
        ImageLoader.getInstance().displayImage(mData.getPhoto(), iv_picture, ImageLoadUtil.getDefaultHeadPicOptions(), null);
        final int itemIdValue = mData.getItemId();

        View view_popuwindow_bg_color = popupWindowView.findViewById(R.id.view_popuwindow_bg_color);
        btn_confirm_add.setOnClickListener(this);

        //点击减号
        iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editShoppingCartByHttp(0, itemIdValue, price);

            }
        });
        //点击加号
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editShoppingCartByHttp(1, itemIdValue, price);
            }
        });

        //点击周边关闭popuwindow
        view_popuwindow_bg_color.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closePopupWindow();
                return false;
            }
        });

        popupWindowView.setFocusable(true);
        popupWindowView.setFocusableInTouchMode(true);
        popupWindowView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePopupWindow();
                }
                return false;
            }
        });

        //显示PopupWindow
        popupWindow.showAtLocation(popupWindowView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 关闭窗口
     */
    private void closePopupWindow() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 编辑购物车中商品数量
     *
     * @param ActionValue 1添加、0减少
     */
    private void editShoppingCartByHttp(int ActionValue, int itemIdValue, final double goodsPrice) {

        if (!NetUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }

        String url = AppConstant.BASEURL2 + "api/cart/atc";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(ProductDetailActivity.this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("ItemId", String.valueOf(itemIdValue));
        map.put("SpecialId", specialId);
        map.put("ActivityId", activityId);
        map.put("Action", String.valueOf(ActionValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("ItemId", String.valueOf(itemIdValue))
                .addParams("SpecialId", specialId)//不是专场里面点击编辑购物车此处都为0
                .addParams("ActivityId", activityId)//不是专场里面点击编辑购物车此处都为0
                .addParams("Action", String.valueOf(ActionValue))
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
                        EditShoppingCartGoodsBean bean = gson.fromJson(strJson, EditShoppingCartGoodsBean.class);
                        EditShoppingCartGoodsBean.DataBean goodsDetailData = bean.getData();
                        int num = goodsDetailData.getNum();
                        int kind = goodsDetailData.getKind();
                        SPUtils.putString(ProductDetailActivity.this, "kindNumber", String.valueOf(kind));
                        SPUtils.putString(ProductDetailActivity.this, "TotalNumber", String.valueOf(num));
                        tv_goods_amount.setText(String.valueOf(num));
                        int qty = goodsDetailData.getQty();
                        tv_edit_goods_number.setText(String.valueOf(qty));
                        tv_total_goods_number.setText(String.valueOf(qty));
                        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(qty * goodsPrice)));
                    } else if (code == 403) {
                        DialogUtil.showDialog(ProductDetailActivity.this, getResources().getString(R.string.need_login_again));
                    }
                    ToastUtil.showToast(ProductDetailActivity.this, msg, Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("addShoppingCar", e.toString());
            }
        });
    }


    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }
}
