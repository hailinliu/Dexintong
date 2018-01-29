package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

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
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;

/**
 * 商品评价对应的activity
 */
public class EvaluateGoodsActivity extends BaseActivity {

    private RelativeLayout head_back;
    private RatingBar rb_dispatching_evaluate;
    private RatingBar rb_quality_evaluate;
    private RatingBar rb_service_evaluate;
    private Button btn_cancel;
    private Button btn_submit;
    private EditText et_evaluate_describe;
    private String orderId;
    private String evaluateStar_value;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_evaluate_goods);

        orderId = getIntent().getStringExtra("orderId");
        initView();
        registerListener();
    }

    private void initView() {
        setActivityTitle();
        rb_dispatching_evaluate = (RatingBar) findViewById(R.id.rb_dispatching_evaluate);
        rb_quality_evaluate = (RatingBar) findViewById(R.id.rb_quality_evaluate);
        rb_service_evaluate = (RatingBar) findViewById(R.id.rb_service_evaluate);

        btn_cancel = (Button) findViewById(R.id.btn_cancel);
        btn_submit = (Button) findViewById(R.id.btn_submit);

        et_evaluate_describe = (EditText) findViewById(R.id.et_evaluate_describe);
    }

    private void registerListener() {
        head_back.setOnClickListener(this);
        btn_cancel.setOnClickListener(this);
        btn_submit.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("商品评价");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.btn_submit://提交
                if (CheckInfo()) {
                    httpEvaluate();
                }
                break;
            case R.id.btn_cancel://取消
                onBackPressed();
                break;
        }
    }

    /**
     * 评价
     */
    private void httpEvaluate() {

        if (!NetUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/order/review";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("OrderId", orderId);
        map.put("Star", StringUtil.getUrlEncodeResult(evaluateStar_value));
        map.put("Msg", StringUtil.getUrlEncodeResult(getEvaluateStr()));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("OrderId", orderId)
                .addParams("Star", evaluateStar_value)
                .addParams("Msg", getEvaluateStr())
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
                        finish();
                    }
                    if (code == 403) {
                        DialogUtil.showDialog(EvaluateGoodsActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(EvaluateGoodsActivity.this, msg, Toast.LENGTH_SHORT);
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
     * 提交前进行信息检查
     */
    private boolean CheckInfo() {

        int dispatching_rating = (int) rb_dispatching_evaluate.getRating();
        int quality_rating = (int) rb_quality_evaluate.getRating();
        int service_rating = (int) rb_service_evaluate.getRating();
        evaluateStar_value = dispatching_rating + "," + quality_rating + "," + service_rating;
        String evaluate_describe = et_evaluate_describe.getText().toString().trim();
        LogUtil.e("rb_dispatching_evaluate", android.R.attr.rating + "");

        if (dispatching_rating < 1) {
            ToastUtil.showToast(EvaluateGoodsActivity.this, "请对配送进行评价", Toast.LENGTH_SHORT);
            return false;
        }

        if (quality_rating < 1) {
            ToastUtil.showToast(EvaluateGoodsActivity.this, "请对质量进行评价", Toast.LENGTH_SHORT);
            return false;
        }
        if (service_rating < 1) {
            ToastUtil.showToast(EvaluateGoodsActivity.this, "请对服务进行评价", Toast.LENGTH_SHORT);
            return false;
        }

        if (TextUtils.isEmpty(evaluate_describe)) {
            ToastUtil.showToast(EvaluateGoodsActivity.this, "请输入您的评价简述", Toast.LENGTH_SHORT);
            return false;
        }
        return true;
    }

    /**
     * 获取评价内容
     *
     * @return
     */
    public String getEvaluateStr() {
        return et_evaluate_describe.getText().toString().trim();
    }
}











