package com.runtai.newdexintong.module.homepage.activity;

import android.content.Context;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StrUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.homepage.adapter.SelectPackageAdapter;
import com.runtai.newdexintong.module.homepage.bean.MobilePackageListBean;
import com.runtai.newdexintong.module.homepage.bean.MobileRechargeBean;
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
 * 移动送套餐
 */
public class MobilePackageActivity extends BaseActivity {

    /**
     * 运营商名称
     */
    private TextView tv_operator_name;

    TextWatcher textWatcher = new TextWatcher() {

//        private CharSequence temp_password;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
//            temp_password = s;
        }

        @Override
        public void afterTextChanged(Editable s) {
            String str = s.toString();
            if (str.length() > 0 && StrUtil.isMobileNo(str)) {

                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        getPhoneLocation(getPhoneNumber());
                    }
                }).start();
            }
        }
    };
    private EditText et_phone_number;
    private RelativeLayout head_back;
    private String gsdStr;
    private Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 10:
                    if (tv_operator_name != null && gsdStr != null)
                        tv_operator_name.setText(gsdStr);
                    break;
            }
        }
    };
    private List<MobilePackageListBean.DataBean> data;
    private PopupWindow popupWindow;
    private TextView tv_select_package;
    private TextView tv_need_money;
    private int package_id;
    private Button btn_recharge_now;
    private int mobileType;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_mobile_package);
        initView();

        registerListener();
    }

    private void initView() {
        setActivityTitle();
        et_phone_number = (EditText) findViewById(R.id.et_phone_number);
        tv_operator_name = (TextView) findViewById(R.id.tv_operator_name);
        tv_select_package = (TextView) findViewById(R.id.tv_select_package);
        tv_need_money = (TextView) findViewById(R.id.tv_need_money);
        btn_recharge_now = (Button) findViewById(R.id.btn_recharge_now);
    }

    private void registerListener() {
        head_back.setOnClickListener(this);
        btn_recharge_now.setOnClickListener(this);
        tv_select_package.setOnClickListener(this);
        et_phone_number.addTextChangedListener(textWatcher);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("移动套餐充值");

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.btn_recharge_now://立即充值
                if (check()) {
                    showDialog(getPhoneNumber(), getMobilePackageName());
                }
                break;
            case R.id.tv_select_package:
                if (StrUtil.isMobileNo(getPhoneNumber())) {
                    showPopuwindow();
                }
                break;
        }
    }

    /**
     * 充值
     */
    private void recharge() {

        String url = AppConstant.BASEURL2 + "api/huafei/pkgcreate";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("ActivityId", String.valueOf(package_id));
        map.put("Price", getPayMoney());
        map.put("MobileType", String.valueOf(mobileType));
        map.put("No", getPhoneNumber());
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("ActivityId", String.valueOf(package_id))
                .addParams("Price", getPayMoney())
                .addParams("MobileType", String.valueOf(mobileType))
                .addParams("No", getPhoneNumber())
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
                        clearInputData();
                    }
                    if (code == 403) {
                        DialogUtil.showDialog(MobilePackageActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(MobilePackageActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 点击充值前进行检查
     *
     * @return
     */
    private boolean check() {
        String phone_number = getPhoneNumber();
        String operator_name = tv_operator_name.getText().toString().trim();
        String package_name = getMobilePackageName();

        if (!StrUtil.isMobileNo(phone_number)) {
            ToastUtil.showToast(MobilePackageActivity.this, "请输入正确的手机号码", Toast.LENGTH_SHORT);
            return false;
        }

        if (TextUtils.isEmpty(operator_name)) {
            ToastUtil.showToast(MobilePackageActivity.this, "没有检测到运营商信息", Toast.LENGTH_SHORT);
            return false;
        }
        
        if (!operator_name.contains("移动")){
            DialogUtil.showPointDialog(MobilePackageActivity.this,"移动套餐充值仅支持移动号码充值，请重新输入");
            
            clearInputData();
            return false;
        }

        if ("请选择移动存送套餐类型".equals(package_name)) {
            ToastUtil.showToast(MobilePackageActivity.this, "请选择要购买的套餐", Toast.LENGTH_SHORT);
            return false;
        }

        return true;
    }

    /**
     * 充值信息确认的对话框
     *
     * @param phoneNumber
     * @param packageName
     */
    private void showDialog(String phoneNumber, final String packageName) {

        new MyAlertDialog(this).builder().setTitle("确认充值信息")
                .setMsg("确定为" + getOperatorName() + "号码" + phoneNumber + "充值" + packageName + "吗？")
                .setPositiveButton("立即充值", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recharge();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    /**
     * 清空输入的数据
     */
    private void clearInputData() {
        et_phone_number.setText("");
        tv_operator_name.setText("");
        tv_select_package.setText("请选择移动存送套餐类型");
        tv_select_package.setTextColor(getResources().getColor(R.color.black_text));
        tv_need_money.setText("");
    }

    /**
     * 获取手机号码
     *
     * @return
     */
    public String getPhoneNumber() {

        return et_phone_number.getText().toString().trim();

    }

    /**
     * 获取选中的套餐名称
     *
     * @return
     */
    public String getMobilePackageName() {
        return tv_select_package.getText().toString().trim();
    }


    /**
     * 获取支付金额
     */
    public String getPayMoney() {
        return tv_need_money.getText().toString().trim();
    }

    /**
     * 获取运营商名称
     * @return
     */
    public String getOperatorName() {
        return tv_operator_name.getText().toString().trim();
    }

    /**
     * 获取号码归属地
     */
    private void getPhoneLocation(String phoneNumber) {

        String url = AppConstant.BASEURL2 + "api/huafei/gsd360";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(MobilePackageActivity.this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("No", phoneNumber);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("No", phoneNumber)
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
                        MobileRechargeBean mobileRechargeBean = gson.fromJson(strJson, MobileRechargeBean.class);
                        MobileRechargeBean.DataBean data = mobileRechargeBean.getData();
                        gsdStr = data.getProvince() + " " + data.getCity() + " " + data.getSp();
                        mobileType = data.getMobileType();
                        mHandler.sendEmptyMessage(10);
                        getPackageList();
                    } else if (code == 403) {
                        DialogUtil.showDialog(MobilePackageActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(MobilePackageActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 获取套餐列表
     */
    public void getPackageList() {

        String url = AppConstant.BASEURL2 + "api/huafei/pkg";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

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
//                        tv_select_package.setEnabled(true);
                        Gson gson = GsonUtil.buildGson();
                        MobilePackageListBean mobilePackageListBean = gson.fromJson(strJson, MobilePackageListBean.class);
                        if (data == null) {
                            data = new ArrayList<>();
                        }
                        data.clear();
                        data = mobilePackageListBean.getData();
                    } else if (code == 403) {
                        DialogUtil.showDialog(MobilePackageActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(MobilePackageActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 弹出显示选择城市列表的popuwindow
     */
    private void showPopuwindow() {
        closePopupWindow();
        View popupWindowView = LayoutInflater.from(this).inflate(R.layout.popuwindow_select_package_or_denomination,
                null);
        popupWindow = new PopupWindow(popupWindowView,
                WindowManager.LayoutParams.WRAP_CONTENT, WindowManager.LayoutParams.WRAP_CONTENT, true);
        ListView lv_select_package = (ListView) popupWindowView.findViewById(R.id.lv_select_package);

        lv_select_package.setAdapter(new SelectPackageAdapter(this, data));
        WindowManager manager = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        @SuppressWarnings("deprecation")
        //获取xoff
//                int xpos = -popupWindow.getWidth() / 2;
                int xpos = manager.getDefaultDisplay().getWidth() / 2 - popupWindow.getWidth() / 2;

        lv_select_package.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                closePopupWindow();
                tv_select_package.setText(data.get(position).getName());
                tv_need_money.setText(String.valueOf(data.get(position).getAmount()));
                package_id = data.get(position).getId();
            }
        });
        popupWindow.setOutsideTouchable(true);
        popupWindow.setFocusable(true);
        popupWindow.setBackgroundDrawable(new BitmapDrawable());
        popupWindow.setTouchInterceptor(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_OUTSIDE) {
                    closePopupWindow();
                    return true;
                }

                return false;
            }
        });

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
        popupWindow.showAsDropDown(tv_select_package, -xpos / 2, 0);
    }

    /**
     * 关闭popuwindow窗口
     */
    private void closePopupWindow() {
        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (mHandler != null)
            mHandler.removeCallbacksAndMessages(null);
    }

}
