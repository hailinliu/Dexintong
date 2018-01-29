package com.runtai.newdexintong.module.myorder.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.RadioButton;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.utils.ActivityStack;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.NetUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.myorder.bean.ConfirmPayBean;
import com.runtai.newdexintong.module.myorder.view.OnPasswordInputFinish;
import com.runtai.newdexintong.module.myorder.view.PassView;
import com.runtai.newdexintong.module.personalcenter.activity.MyOrderActivity;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * 确认支付页面
 */
public class ConfirmPayActivity extends BaseActivity implements OnPasswordInputFinish {

    private RelativeLayout head_back;
    private TextView tv_money_amount;
    private TextView tv_order_number;
    private Button btn_confirm_pay;
    private RelativeLayout rl_account_balance;
    private RelativeLayout rl_delivery_pay;
    private RelativeLayout rl_credit_payment;
    private RadioButton radioBtn_account_pay;
    private RadioButton radioBtn_delivery_pay;
    private RadioButton radioBtn_credit_payment;
    /**
     * 支付方式的标记值
     */
    private int flagValue = 1;
    private PopupWindow popupWindow;
    private View popupWindowView;
    private PassView passView;
    private String ids_selected;
    private String realpay_money;
    private String ids_selected_new;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_confirm_pay);
        Intent intent = getIntent();
        ids_selected = intent.getStringExtra("ids_selected");
        ids_selected_new = StringUtil.getUrlEncodeResult(ids_selected);
        realpay_money = intent.getStringExtra("realpay_money");
        initView();
        registerListener();
    }

    private void initView() {

        setActivityTitle();
        tv_money_amount = (TextView) findViewById(R.id.tv_money_amount);
        tv_money_amount.setText(StringUtil.strToDouble_new(realpay_money));

        tv_order_number = (TextView) findViewById(R.id.tv_order_number);
        btn_confirm_pay = (Button) findViewById(R.id.btn_confirm_pay);
        rl_account_balance = (RelativeLayout) findViewById(R.id.rl_account_balance);
        rl_delivery_pay = (RelativeLayout) findViewById(R.id.rl_delivery_pay);
        rl_credit_payment = (RelativeLayout) findViewById(R.id.rl_credit_payment);

        radioBtn_account_pay = (RadioButton) findViewById(R.id.radioBtn_account_pay);
        radioBtn_delivery_pay = (RadioButton) findViewById(R.id.radioBtn_delivery_pay);
        radioBtn_credit_payment = (RadioButton) findViewById(R.id.radioBtn_credit_payment);
        View view_line = findViewById(R.id.view_line);
        
        //判断是否允许到付
        if (SPUtils.getBoolean(ConfirmPayActivity.this, "IsCod", false)) {
            rl_delivery_pay.setVisibility(View.VISIBLE);
            view_line.setVisibility(View.VISIBLE);
            
        } else {
            rl_delivery_pay.setVisibility(View.GONE);
            view_line.setVisibility(View.GONE);
        }
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("支付方式");
    }

    private void registerListener() {
        head_back.setOnClickListener(this);
        rl_account_balance.setOnClickListener(this);
        rl_delivery_pay.setOnClickListener(this);
        rl_credit_payment.setOnClickListener(this);
        btn_confirm_pay.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
//                showDialog();
                onBackPressed();
                break;
            case R.id.rl_account_balance://账户余额支付
                clearCheckd();
                radioBtn_account_pay.setChecked(true);
//                ：货到付款0，余额支付1
                flagValue = 1;
                break;
            case R.id.rl_delivery_pay://货到付款支付
                clearCheckd();
                radioBtn_delivery_pay.setChecked(true);
                flagValue = 0;
                break;
            case R.id.rl_credit_payment://授信支付
                clearCheckd();
                radioBtn_credit_payment.setChecked(true);
                flagValue = 2;
                break;
            case R.id.btn_confirm_pay://确认支付下单
//                showPopuWindow();
                makeOrdersByHttp();
                break;
        }
    }

    /**
     * 弹出确认支付的popuWindow
     */
    private void showPopuWindow() {

        passView = new PassView(this);
        popupWindow = new PopupWindow(passView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        passView.setOnFinishInput(this);
        View view_popuwindow_bg = passView.findViewById(R.id.view_popuwindow_bg);

        TextView tv_forget_pay_password = (TextView) passView.findViewById(R.id.tv_forget_pay_password);
        ImageView iv_close = (ImageView) passView.findViewById(R.id.iv_close);

        iv_close.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                closePopupWindow();
            }
        });
        tv_forget_pay_password.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //忘记支付密码
                Intent intent2 = new Intent(ConfirmPayActivity.this, FindPayPassWordActivity.class);
                startActivityByIntent(intent2);
                closePopupWindow();
            }
        });
        //点击周边关闭popuwindow
        view_popuwindow_bg.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                closePopupWindow();
                return false;
            }
        });
        passView.setFocusable(true);
        passView.setFocusableInTouchMode(true);
        passView.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {
                if (keyCode == KeyEvent.KEYCODE_BACK) {
                    closePopupWindow();
                }
                return false;
            }
        });

        //显示PopupWindow
        popupWindow.showAtLocation(passView, Gravity.BOTTOM, 0, 0);
    }

    /**
     * 关闭popuwindow
     */
    private void closePopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 退出界面的提示框
     */
    private void showDialog() {

        new MyAlertDialog(this).builder()
                .setTitle("未支付的订单，系统会自动取消哦")
                .setPositiveButton("确认", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        Intent intent = new Intent(ConfirmPayActivity.this, MainActivity.class);
                        intent.putExtra("tag", "SearchResultAcivity");
                        startActivityByIntent(intent);
                        finish();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();

    }

    /**
     * 清除所有选中的状态
     */
    private void clearCheckd() {
        radioBtn_account_pay.setChecked(false);
        radioBtn_delivery_pay.setChecked(false);
        radioBtn_credit_payment.setChecked(false);
    }

//    @Override
//    public boolean dispatchKeyEvent(KeyEvent event) {
//        // TODO Auto-generated method stub  
//        if (event.getAction() == KeyEvent.ACTION_DOWN
//                && event.getKeyCode() == KeyEvent.KEYCODE_BACK) {
////            showDialog();
//            return true;//返回true，把事件消费掉，不会继续调用onBackPressed 
//        }
//        return super.dispatchKeyEvent(event);
//    }

    @Override
    public void inputFinish() {
        String strPassword = passView.getStrPassword();
    }


    /**
     * 下单
     */
    private void makeOrdersByHttp() {

        if (!NetUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/order/jiesuan";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Ids", ids_selected_new);
        map.put("PaymentType", String.valueOf(flagValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Ids", ids_selected)
                .addParams("PaymentType", String.valueOf(flagValue))
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
                        showOrderSuccessDialog();
                    } else if (code == 403) {
                        DialogUtil.showDialog(ConfirmPayActivity.this,
                                getResources().getString(R.string.need_login_again));
                    } else if (code == 9999) {
                        Gson gson = GsonUtil.buildGson();
                        ConfirmPayBean confirmPayBean = gson.fromJson(strJson, ConfirmPayBean.class);
                        ConfirmPayBean.DataBean data = confirmPayBean.getData();
                        if (data != null) {
//                            List<ConfirmPayBean.DataBean.FailedBean> failed = data.getFailed();
                            String failedJson = gson.toJson(data);
                            LogUtil.e("failed", failedJson);
                            System.out.println("failed" + failedJson);
                            showOrderFailedMsg(ConfirmPayActivity.this, msg, failedJson);
                        } else {
                            ToastUtil.showToast(ConfirmPayActivity.this, msg, Toast.LENGTH_SHORT);
                        }

                    } else {
                        ToastUtil.showToast(ConfirmPayActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 商品库存不足或者售罄的时候，提示用户返回购物车删除商品名称标红的商品
     */
    public static void showOrderFailedMsg(final Context mContext, String msg, final String failedIds) {

        new MyAlertDialog(mContext)
                .builder()
                .setTitle(msg)
                .setCancelable(false)
                .setNegativeButton("回购物车", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, MainActivity.class);
                        intent.putExtra("tag", "SearchResultAcivity");
                        SPUtils.putString(mContext, "failedIds", failedIds);
                        SPUtils.putBoolean(mContext, "fromConfirmPay", true);
                        SPUtils.putBoolean(mContext, "showRedTitle", true);
                        ((BaseCommonActivity) mContext).startActivityByIntent(intent);
                        ActivityStack.getInstance().finishActivity(ConfirmPayActivity.class);
                    }
                }).show();
    }

    /**
     * 下单成功后弹出提示框
     */
    private void showOrderSuccessDialog() {

        new MyAlertDialog(this).builder()
                .setCancelable(false)
                .setTitle("下单成功")
                .setPositiveButton("查看订单", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent_df = new Intent(ConfirmPayActivity.this, MyOrderActivity.class);
                        intent_df.putExtra("status", 1);
                        SPUtils.putBoolean(ConfirmPayActivity.this, "isWatchOrder", true);
                        startActivityByIntent(intent_df);
                        finish();
                    }
                }).setNegativeButton("去首页", new View.OnClickListener() {

            @Override
            public void onClick(View v) {

                Intent intent = new Intent(ConfirmPayActivity.this, MainActivity.class);
                intent.putExtra("tag", "selected_homePage");
                startActivityByIntent(intent);
                finish();
            }
        }).show();
    }
}
