package com.runtai.newdexintong.module.personalcenter.fragment;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.runtai.newdexintong.module.home.bean.LoginBean;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.homepage.activity.MessageBoxActivity;
import com.runtai.newdexintong.module.personalcenter.activity.AccountQueryActivity;
import com.runtai.newdexintong.module.personalcenter.activity.AddMoneyActivity;
import com.runtai.newdexintong.module.personalcenter.activity.CouponActivity;
import com.runtai.newdexintong.module.personalcenter.activity.FinancialDetailsActivity;
import com.runtai.newdexintong.module.personalcenter.activity.MyOrderActivity;
import com.runtai.newdexintong.module.personalcenter.activity.MyPointsActivity;
import com.runtai.newdexintong.module.personalcenter.activity.PreSaleOrdersActivity;
import com.runtai.newdexintong.module.personalcenter.activity.RechargeDayCollectActivity;
import com.runtai.newdexintong.module.personalcenter.activity.ReturnReplaceImperfectGoodsAcitivity;
import com.runtai.newdexintong.module.personalcenter.activity.SettingActivity;
import com.runtai.newdexintong.module.personalcenter.activity.StoreAddressActivity;
import com.runtai.newdexintong.module.personalcenter.activity.StoreInfoActivity;
import com.runtai.newdexintong.module.personalcenter.bean.UserInfoBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/1/23.
 * 个人中心对应的fragment
 */
public class PersonalCenterFragment extends BaseFragment {

    /*订单区分和角标*/
    private LinearLayout ll_make_order_success;
    private LinearLayout ll_tv_picking;
    private LinearLayout ll_dispatching;
    private LinearLayout ll_order_confirmed;
    private LinearLayout ll_return_replace_goods;
    private TextView me_ll_daifahuo_corner_num;
    private TextView me_ll_peisongzhong_corner_num;
    private TextView me_ll_yiwancheng_corner_num;
    private TextView me_ll_tuihuojilu_corner_num;

    private RelativeLayout rl_message;//消息
    private RelativeLayout rl_setting;//设置
    private TextView chongzhi_tv;
    private RelativeLayout rl_my_order;//全部订单
    private RelativeLayout rl_me_youhuiquan;//优惠券
    private RelativeLayout rl_me_jifen;//积分
    private RelativeLayout rl_me_caiwumingxi;//财务明细
    private RelativeLayout rl_me_zaixiankefu;//在线客服
    private RelativeLayout rl_me_kefudianhua;//客服电话
    private RelativeLayout rl_me_yewuyuanphone;//业务员电话
    private RelativeLayout rl_me_dianpuziliao;//店铺资料
    private RelativeLayout rl_me_dianpudizhi;//店铺地址

    private TextView me_youhuiquan_tv;//优惠券数量
    private TextView me_jifen_tv;//积分数量

    private TextView tv_shop_name;//店铺名称
    private TextView me_chongzhiyue;//充值余额
    private TextView me_huokuanyue;//货款余额
    private TextView me_kefu_phone;//客服电话
    private TextView me_yewuyuan_phone;//业务员电话

    private Intent intent;

    private LinearLayout chongzhizhanghu_ll;//充值账户
    private LinearLayout denglu_ll;//登录模式
    private LinearLayout youke_ll;//游客模式
    private RelativeLayout rl_pre_sale_orders;
    private LoginBean.DataBean.MerchantBean merchantBean;
    /**
     * 商户号
     */
    private TextView tv_shop_number;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;
    private ScrollView mScrollView;
    private RelativeLayout rl_recharge_day_detail;
    private RelativeLayout rl_account_query;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_personalcenter);
        View view = getContentView();
        initView(view);
        registerListener();
    }

    private void initView(View view) {

        rl_message = (RelativeLayout) view.findViewById(R.id.rl_message);
        rl_setting = (RelativeLayout) view.findViewById(R.id.rl_setting);
        chongzhi_tv = (TextView) view.findViewById(R.id.chongzhi_tv);
        rl_my_order = (RelativeLayout) view.findViewById(R.id.rl_my_order);
        rl_me_youhuiquan = (RelativeLayout) view.findViewById(R.id.rl_me_youhuiquan);
        rl_me_jifen = (RelativeLayout) view.findViewById(R.id.rl_me_jifen);
        rl_me_caiwumingxi = (RelativeLayout) view.findViewById(R.id.rl_me_caiwumingxi);
        rl_me_dianpuziliao = (RelativeLayout) view.findViewById(R.id.rl_me_dianpuziliao);
        rl_me_zaixiankefu = (RelativeLayout) view.findViewById(R.id.rl_me_zaixiankefu);
        rl_me_kefudianhua = (RelativeLayout) view.findViewById(R.id.rl_me_kefudianhua);

        rl_me_yewuyuanphone = (RelativeLayout) view.findViewById(R.id.rl_me_yewuyuanphone);

        rl_me_dianpudizhi = (RelativeLayout) view.findViewById(R.id.rl_me_dianpudizhi);

        ll_make_order_success = (LinearLayout) view.findViewById(R.id.ll_make_order_success);

        ll_tv_picking = (LinearLayout) view.findViewById(R.id.ll_tv_picking);

        ll_dispatching = (LinearLayout) view.findViewById(R.id.ll_dispatching);

        ll_order_confirmed = (LinearLayout) view.findViewById(R.id.ll_order_confirmed);
        ll_return_replace_goods = (LinearLayout) view.findViewById(R.id.ll_return_replace_goods);

//        me_ll_daifahuo_corner_num = (TextView) view.findViewById(R.id.me_ll_daifahuo_corner_num);
//        me_ll_peisongzhong_corner_num = (TextView) view.findViewById(R.id.me_ll_peisongzhong_corner_num);
//        me_ll_yiwancheng_corner_num = (TextView) view.findViewById(R.id.me_ll_yiwancheng_corner_num);
//        me_ll_tuihuojilu_corner_num = (TextView) view.findViewById(R.id.me_ll_tuihuojilu_corner_num);

        me_youhuiquan_tv = (TextView) view.findViewById(R.id.me_youhuiquan_tv);
        me_jifen_tv = (TextView) view.findViewById(R.id.me_jifen_tv);

        tv_shop_name = (TextView) view.findViewById(R.id.tv_shop_name);
        me_chongzhiyue = (TextView) view.findViewById(R.id.me_chongzhiyue);
        me_huokuanyue = (TextView) view.findViewById(R.id.me_huokuanyue);

        me_kefu_phone = (TextView) view.findViewById(R.id.me_kefu_phone);
        me_yewuyuan_phone = (TextView) view.findViewById(R.id.me_yewuyuan_phone);

        denglu_ll = (LinearLayout) view.findViewById(R.id.denglu_ll);
        youke_ll = (LinearLayout) view.findViewById(R.id.youke_ll);
        chongzhizhanghu_ll = (LinearLayout) view.findViewById(R.id.chongzhizhanghu_ll);

        rl_pre_sale_orders = (RelativeLayout) view.findViewById(R.id.rl_pre_sale_orders);
        tv_shop_number = (TextView) view.findViewById(R.id.tv_shop_number);

        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        tv_reload = (TextView) view.findViewById(R.id.tv_reload);
        mScrollView = (ScrollView) view.findViewById(R.id.mScrollView);
        rl_recharge_day_detail = (RelativeLayout) view.findViewById(R.id.rl_recharge_day_detail);
        rl_account_query = (RelativeLayout) view.findViewById(R.id.rl_account_query);

    }

    private void registerListener() {

        rl_account_query.setOnClickListener(this);
        rl_message.setOnClickListener(this);
        rl_setting.setOnClickListener(this);
        chongzhi_tv.setOnClickListener(this);
        rl_my_order.setOnClickListener(this);
        rl_me_youhuiquan.setOnClickListener(this);
        rl_me_jifen.setOnClickListener(this);
        rl_me_caiwumingxi.setOnClickListener(this);
        rl_me_dianpuziliao.setOnClickListener(this);
        rl_me_zaixiankefu.setOnClickListener(this);
        rl_me_kefudianhua.setOnClickListener(this);
        rl_me_yewuyuanphone.setOnClickListener(this);
        rl_me_dianpudizhi.setOnClickListener(this);
        ll_make_order_success.setOnClickListener(this);
        ll_tv_picking.setOnClickListener(this);
        ll_dispatching.setOnClickListener(this);
        ll_order_confirmed.setOnClickListener(this);
        ll_return_replace_goods.setOnClickListener(this);
        rl_pre_sale_orders.setOnClickListener(this);
        tv_reload.setOnClickListener(this);
        rl_recharge_day_detail.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.rl_message://消息
//                ((BaseCommonActivity) getActivity()).startActivityByIntent(MessageBoxActivity.class);
                startActivityByIntent(MessageBoxActivity.class);
                break;
            case R.id.rl_setting://设置
                startActivityByIntent(SettingActivity.class);
                break;
            case R.id.chongzhi_tv://加款
                startActivityByIntent(AddMoneyActivity.class);
                break;
            case R.id.rl_my_order://我的订单
                Intent intent2 = new Intent(getActivity(), MyOrderActivity.class);
                intent2.putExtra("status", 0);
                startActivityByIntent(intent2);
                break;
            case R.id.ll_make_order_success://下单成功
                Intent intent_df = new Intent(getActivity(), MyOrderActivity.class);
                intent_df.putExtra("status", 1);
                startActivityByIntent(intent_df);
                break;
            case R.id.ll_tv_picking://拣货中
                Intent intent_picking = new Intent(getActivity(), MyOrderActivity.class);
                intent_picking.putExtra("status", 2);
                startActivityByIntent(intent_picking);
                break;
            case R.id.ll_dispatching://配送中
                Intent intent_ywc = new Intent(getActivity(), MyOrderActivity.class);
                intent_ywc.putExtra("status", 3);
                startActivityByIntent(intent_ywc);
                break;
            case R.id.ll_order_confirmed://已确认
                Intent intent_confirmed = new Intent(getActivity(), MyOrderActivity.class);
                intent_confirmed.putExtra("status", 5);
                startActivityByIntent(intent_confirmed);
                break;

            case R.id.rl_pre_sale_orders://预售订单
                Intent intent_pre_orders = new Intent(getActivity(), PreSaleOrdersActivity.class);
                startActivityByIntent(intent_pre_orders);
                break;
            case R.id.ll_return_replace_goods://退货调货
                Intent intent_rr = new Intent(getActivity(), ReturnReplaceImperfectGoodsAcitivity.class);
                startActivityByIntent(intent_rr);
                break;
            case R.id.rl_me_youhuiquan://我的优惠券
//                startActivityByIntent(CouponActivity.class);
                Intent intent = new Intent(getActivity(), CouponActivity.class);
                startActivityByIntent(intent);
                break;
            case R.id.rl_me_jifen://我的积分
                startActivityByIntent(MyPointsActivity.class);
                break;
            case R.id.rl_me_caiwumingxi://财务明细
                startActivityByIntent(FinancialDetailsActivity.class);
                break;
            case R.id.rl_me_zaixiankefu://在线客服
                startActivityByIntent(FinancialDetailsActivity.class);
                break;
            case R.id.rl_me_kefudianhua://客服电话
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + me_kefu_phone.getText().toString().trim()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.rl_me_yewuyuanphone://业务员电话
                intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + me_yewuyuan_phone.getText().toString().trim()));
                intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                startActivity(intent);
                break;
            case R.id.rl_me_dianpuziliao://店铺资料
//                startActivityByIntent(StoreInfoActivity.class);
                startActivityByIntent(StoreInfoActivity.class);
                break;
            case R.id.rl_me_dianpudizhi://店铺地址
                startActivityByIntent(StoreAddressActivity.class);
                break;
            case R.id.tv_reload://重新加载
                httpData();
                break;
            case R.id.rl_recharge_day_detail://充值日汇总
                startActivityByIntent(RechargeDayCollectActivity.class);
                break;
            case R.id.rl_account_query://账户查询
                startActivityByIntent(AccountQueryActivity.class);
                break;
        }
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("我的", "onStart");
        if (SPUtils.getBoolean(getActivity(), "wxPaySuccess", false)) {
            SPUtils.putBoolean(getActivity(), "wxPaySuccess", false);
            httpData();
        }
        if (SPUtils.getBoolean(getActivity(), "isAliPaySuccess", false)) {
            SPUtils.putBoolean(getActivity(), "isAliPaySuccess", false);
            httpData();
        }
    }

    @Override
    public void onFragmentStart() {
        super.onFragmentStart();

        if (!SPUtils.getBoolean(getActivity(), "isTourist", false)) {
            denglu_ll.setVisibility(View.VISIBLE);
            chongzhizhanghu_ll.setVisibility(View.VISIBLE);
            youke_ll.setVisibility(View.GONE);
        } else {
            denglu_ll.setVisibility(View.GONE);
            chongzhizhanghu_ll.setVisibility(View.GONE);
            youke_ll.setVisibility(View.VISIBLE);
            tv_shop_name.setText("游客模式");
        }

        httpData();

    }

    /**
     * 更新账户个人信息的操作
     */
    private void httpData() {

        if (!NetUtil.isNetworkAvailable(getActivity())) {
            mScrollView.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(getActivity(), "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        } else {
            mScrollView.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/store/home";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url)
                .addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
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
                        UserInfoBean userInfoBean = gson.fromJson(strJson, UserInfoBean.class);
                        UserInfoBean.DataBean data = userInfoBean.getData();
                        tv_shop_name.setText(data.getName());
                        me_chongzhiyue.setText(String.valueOf(data.getRBalance()));
                        me_huokuanyue.setText(String.valueOf(data.getOBalance()));
                        me_jifen_tv.setText(String.valueOf(data.getJiFen()));
                        me_yewuyuan_phone.setText(data.getMobile());
                        tv_shop_number.setText(String.valueOf(data.getId()));

                        SPUtils.putString(getActivity(), "shopName", data.getName());
                        SPUtils.putString(getActivity(), "jifen", String.valueOf(data.getJiFen()));
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

    @Override
    public void onFragmentStop() {
        super.onFragmentStop();
        Log.e("我的", "释放资源、清空数据");
    }
}
