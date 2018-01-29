package com.runtai.newdexintong.module.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RadioButton;
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
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.personalcenter.bean.ApplyReplaceGoodsBean;
import com.runtai.newdexintong.module.personalcenter.bean.ApplyReturnGoodsBean;
import com.runtai.newdexintong.module.personalcenter.view.timepickerview.OptionsPopupWindow;
import com.runtai.newdexintong.module.personalcenter.view.timepickerview.TimePopupWindow;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * @ 作者：rhf
 * @ 日期：2017/4/6时间17:50
 * @ 描述：申请退货
 */

public class ApplyReturnGoodsActivity extends BaseActivity {

    private RelativeLayout head_back;
    private List<ApplyReplaceGoodsBean> mData;
    private Button btn_confirm;
    private Window dialog;
    private TextView tv_time, tvOptions;
    TimePopupWindow pwTime;
    OptionsPopupWindow pwOptions;
    private TextView tv_select_return_goods_category;
    private TextView tv_goods_name;
    private TextView tv_goods_number;
    private TextView tv_goods_unit;
    private TextView tv_price;

    private ArrayList<String> options1Items = new ArrayList<String>();
    private ArrayList<ArrayList<String>> options2Items = new ArrayList<ArrayList<String>>();
    private ArrayList<ArrayList<ArrayList<String>>> options3Items = new ArrayList<ArrayList<ArrayList<String>>>();
    private View popupWindowView;
    private PopupWindow popupWindow;
    private TextView tv_confirm;
    private RelativeLayout rl_return_all;
    private RelativeLayout rl_return_piece;
    private RadioButton radioBtn_return_all;
    private RadioButton radioBtn_return_piece;
    private ImageView iv_right_pic;
    private ImageView iv_right_pic2;
    private String orderId;
    private String orderDetailId;
    private int flagValue = 0;
    private int returnNum = 0;
    private ImageView iv_reduce;
    private ImageView iv_add;
    private TextView tv_edit_goods_number;
    private TextView tv_ls_limit_num;
    private TextView tv_retunn_limit_number;
    private LinearLayout ll_edit_goosNumber;
    private TextView tv_total_return_number;
    TextView tv_unit2;
    private TextView tv_real_price;
    private TextView tv_return_goods_unit;
    private ApplyReturnGoodsBean.DataBean data;
    private TextView tv_return_goods_money;
    private LinearLayout ll_real_price;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_apply_return_goods);
        Intent intent = getIntent();
        orderId = intent.getStringExtra("orderId");
        orderDetailId = intent.getStringExtra("orderDetailId");
        initView();
        httpData();
        registerListener();
    }

    /**
     * 获取退货信息
     */
    private void httpData() {
        String url = AppConstant.BASEURL2 + "api/order/ret";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Num", "0");
        map.put("Id", orderDetailId);
        map.put("OrderId", orderId);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Num", "0")
                .addParams("Id", orderDetailId)
                .addParams("OrderId", orderId)
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
                        ApplyReturnGoodsBean applyReturnGoodsBean = gson.fromJson(strJson, ApplyReturnGoodsBean.class);
                        data = applyReturnGoodsBean.getData();
                        tv_goods_name.setText(data.getItemName());
                        tv_goods_number.setText(String.valueOf(data.getSpec()));
                        tv_goods_unit.setText(data.getUnit());
                        tv_price.setText(StringUtil.strToDouble_new(String.valueOf(data.getBenefitPrice() * data.getSpec())));
                        tv_retunn_limit_number.setText(String.valueOf(data.getConfirmNum() / data.getSpec()));
                        tv_ls_limit_num.setText(String.valueOf(data.getConfirmNum()));
                        tv_unit2.setText(data.getUnit());
                        tv_return_goods_unit.setText(data.getUnit());
                        tv_real_price.setText(StringUtil.strToDouble_new(String.valueOf(data.getBenefitPrice())));
                    } else if (code == 403) {
                        DialogUtil.showDialog(ApplyReturnGoodsActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(ApplyReturnGoodsActivity.this, msg, Toast.LENGTH_SHORT);
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
        
        setActivityTitle();

        tv_goods_name = (TextView) findViewById(R.id.tv_goods_name);
        tv_goods_number = (TextView) findViewById(R.id.tv_goods_number);
        tv_goods_unit = (TextView) findViewById(R.id.tv_goods_unit);
        tv_price = (TextView) findViewById(R.id.tv_price);
        tv_time = (TextView) findViewById(R.id.tv_time);
        iv_right_pic = (ImageView) findViewById(R.id.iv_right_pic);
        iv_right_pic2 = (ImageView) findViewById(R.id.iv_right_pic2);
        tv_select_return_goods_category = (TextView) findViewById(R.id.tv_select_return_goods_category);
        btn_confirm = (Button) findViewById(R.id.btn_confirm);
        iv_reduce = (ImageView) findViewById(R.id.iv_reduce);
        iv_add = (ImageView) findViewById(R.id.iv_add);
        tv_edit_goods_number = (TextView) findViewById(R.id.tv_edit_goods_number);
        tv_ls_limit_num = (TextView) findViewById(R.id.tv_ls_limit_num);
        tv_retunn_limit_number = (TextView) findViewById(R.id.tv_retunn_limit_number);
        ll_edit_goosNumber = (LinearLayout) findViewById(R.id.ll_edit_goosNumber);
        tv_total_return_number = (TextView) findViewById(R.id.tv_total_return_number);
        tv_unit2 = (TextView) findViewById(R.id.tv_unit2);
        tv_real_price = (TextView) findViewById(R.id.tv_real_price);
        tv_return_goods_unit = (TextView) findViewById(R.id.tv_return_goods_unit);
        tv_return_goods_money = (TextView) findViewById(R.id.tv_return_goods_money);
        ll_real_price = (LinearLayout) findViewById(R.id.ll_real_price);

        // 时间选择器
        pwTime = new TimePopupWindow(this, TimePopupWindow.Type.ALL);
        // 时间选择后回调
        pwTime.setOnTimeSelectListener(new TimePopupWindow.OnTimeSelectListener() {

            @Override
            public void onTimeSelect(Date date) {
                tv_time.setText(getTime(date));
            }
        });
        // 选项选择器
        pwOptions = new OptionsPopupWindow(this);

        // 监听确定选择按钮
        pwOptions
                .setOnoptionsSelectListener(new OptionsPopupWindow.OnOptionsSelectListener() {

                    @Override
                    public void onOptionsSelect(int options1, int option2,
                                                int options3) {
                        // 返回的分别是三个级别的选中位置
                        String tx = options1Items.get(options1)
                                + options2Items.get(options1).get(option2)
                                + options3Items.get(options1).get(option2)
                                .get(options3);
                        tvOptions.setText(tx);
                    }
                });


    }

    private void registerListener() {
        head_back.setOnClickListener(this);
        btn_confirm.setOnClickListener(this);
        tv_time.setOnClickListener(this);
        tv_select_return_goods_category.setOnClickListener(this);
        iv_right_pic.setOnClickListener(this);
        iv_right_pic2.setOnClickListener(this);
        iv_reduce.setOnClickListener(this);
        iv_add.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("申请退货");
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.btn_confirm://确定按钮
                if (checkMsg()) {
                    confirmReturnGoods();
                }
                break;
            case R.id.tv_time://弹出日期时间选择器
            case R.id.iv_right_pic:
                pwTime.showAtLocation(tv_time, Gravity.BOTTOM, 0, 0, new Date());
                break;
            case R.id.tv_select_return_goods_category://请选择退货类型
            case R.id.iv_right_pic2:
                showPopuWindow();
                break;
            case R.id.iv_reduce://点击减号
                int editNumber = getEditNumber();
                if (editNumber == 1) {
                    ToastUtil.showToast(ApplyReturnGoodsActivity.this, "退货数量不能少于1", Toast.LENGTH_SHORT);
                } else {
                    --editNumber;
                    tv_edit_goods_number.setText(String.valueOf(editNumber));
                    tv_total_return_number.setText(String.valueOf(editNumber));
                    tv_return_goods_money.setText(StringUtil.strToDouble_new(String.valueOf(editNumber * data.getBenefitPrice())));
                }
                break;
            case R.id.iv_add://点击加号
                int editNumber2 = getEditNumber();
                if (editNumber2 < data.getConfirmNum()) {
                    ++editNumber2;
                    tv_edit_goods_number.setText(String.valueOf(editNumber2));
                    tv_total_return_number.setText(String.valueOf(editNumber2));
                    tv_return_goods_money.setText(StringUtil.strToDouble_new(String.valueOf(editNumber2 * data.getBenefitPrice())));
                } else {
                    ToastUtil.showToast(ApplyReturnGoodsActivity.this, "退货数量不能大于可退数量", Toast.LENGTH_SHORT);
                }
                break;
            default:
                break;
        }
    }

    public boolean checkMsg() {
        String returnType = getReturnType();
        if ("请选择生产日期".equals(getDate())) {
            ToastUtil.showToast(this, "请请选择生产日期", Toast.LENGTH_SHORT);
            return false;
        }

        if ("请选择退货类型".equals(returnType)) {

            ToastUtil.showToast(this, "请选择退货类型", Toast.LENGTH_SHORT);
            return false;
        }
        if ("0".equals(getReturnGoodsNum())) {
            ToastUtil.showToast(this, "请选择退货数量", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    /**
     * 确认退货
     */
    private void confirmReturnGoods() {

        String url = AppConstant.BASEURL2 + "api/order/retcreate";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Type", String.valueOf(flagValue));
        map.put("Date", getDate());
        map.put("OrderId", orderId);
        map.put("Id", orderDetailId);
        map.put("Num", String.valueOf(getReturnGoodsNum()));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Type", String.valueOf(flagValue))
                .addParams("Date", getDate())
                .addParams("OrderId", orderId)
                .addParams("Id", orderDetailId)
                .addParams("Num", String.valueOf(getReturnGoodsNum()))
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
                    if (code == 403) {
                        DialogUtil.showDialog(ApplyReturnGoodsActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(ApplyReturnGoodsActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 弹出选择退货方式的popuWindow
     */
    private void showPopuWindow() {

        LayoutInflater inflater = (LayoutInflater) getSystemService(LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.popuwindow_select_return_goods_style,
                null);
        popupWindow = new PopupWindow(popupWindowView,
                WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.MATCH_PARENT, true);
        View view_popuwindow_bg = popupWindowView.findViewById(R.id.view_popuwindow_bg);
        tv_confirm = (TextView) popupWindowView.findViewById(R.id.tv_confirm);
        rl_return_all = (RelativeLayout) popupWindowView.findViewById(R.id.rl_return_all);
        rl_return_piece = (RelativeLayout) popupWindowView.findViewById(R.id.rl_return_piece);
        radioBtn_return_all = (RadioButton) popupWindowView.findViewById(R.id.radioBtn_return_all);
        radioBtn_return_piece = (RadioButton) popupWindowView.findViewById(R.id.radioBtn_return_piece);

        String returnType = getReturnType();
        if ("整件退".equals(returnType)) {
            clearChecked();
            radioBtn_return_all.setChecked(true);
        } else if ("零散退".equals(returnType)) {
            clearChecked();
            radioBtn_return_piece.setChecked(true);
        }

        //点击确定
        tv_confirm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
//                0整件退，1散退
                if (radioBtn_return_all.isChecked()) {
                    ll_real_price.setVisibility(View.VISIBLE);
                    tv_total_return_number.setVisibility(View.VISIBLE);
                    tv_return_goods_unit.setVisibility(View.VISIBLE);
                    tv_select_return_goods_category.setText("整件退");
                    flagValue = 0;
                    ll_edit_goosNumber.setVisibility(View.GONE);
                    tv_total_return_number.setText(String.valueOf(data.getConfirmNum()));
                    tv_return_goods_money.setText(StringUtil.strToDouble_new(String.valueOf(data.getConfirmNum() * data.getBenefitPrice())));
                } else {
                    ll_real_price.setVisibility(View.VISIBLE);
                    tv_total_return_number.setVisibility(View.VISIBLE);
                    ll_edit_goosNumber.setVisibility(View.VISIBLE);
                    tv_return_goods_unit.setVisibility(View.VISIBLE);
                    tv_select_return_goods_category.setText("零散退");
                    flagValue = 1;
                    tv_total_return_number.setText(String.valueOf(getEditNumber()));
                    tv_return_goods_money.setText(StringUtil.strToDouble_new(String.valueOf(getEditNumber() * data.getBenefitPrice())));

                }
                closePopupWindow();
            }
        });

        //整件退
        rl_return_all.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearChecked();
                radioBtn_return_all.setChecked(true);
            }
        });

        //零散退
        rl_return_piece.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                clearChecked();
                radioBtn_return_piece.setChecked(true);
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
     * 清除选中状态
     */
    private void clearChecked() {
        radioBtn_return_all.setChecked(false);
        radioBtn_return_piece.setChecked(false);
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

    public static String getTime(Date date) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(date);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (pwTime != null) {
            pwTime.dismiss();
        }
    }

    /**
     * 获取日期
     *
     * @return
     */
    public String getDate() {
        return tv_time.getText().toString().trim();
    }

    /**
     * 获取退货类型描述
     *
     * @return
     */
    public String getReturnType() {
        return tv_select_return_goods_category.getText().toString().trim();
    }

    /**
     * 获取退货数量
     *
     * @return
     */
    public String getReturnGoodsNum() {
        return tv_total_return_number.getText().toString().trim();
    }

    /**
     * 获取编辑中的数量
     *
     * @return
     */
    public int getEditNumber() {
        return Integer.parseInt(tv_edit_goods_number.getText().toString().trim());
    }
}
