package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.personalcenter.adapter.MyPointsAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.MyPointsDataBean;
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
 * @日期：2017/2/15时间15:03
 * @描述：我的积分
 */

public class MyPointsActivity extends BaseActivity {
    
    TextView wdjf_tv;
    ListView wdjf_listview;
    MyPointsAdapter adapter;
    private RelativeLayout head_back;
    private List<MyPointsDataBean.DataBean> scoreListData;
    private RelativeLayout rl_no_data_show;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_points);
        initView();
        doHttp();
    }

    /**
     * 请求积分数据列表数据
     */
    private void doHttp() {

        if (!NetUtil.isNetworkAvailable(this)) {
            wdjf_listview.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            wdjf_listview.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }

        String url = AppConstant.BASEURL2 + "api/score/list";
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
                        Gson gson = GsonUtil.buildGson();
                        MyPointsDataBean myPointsDataBean = gson.fromJson(strJson, MyPointsDataBean.class);
                        if (scoreListData == null) {
                            scoreListData = new ArrayList<MyPointsDataBean.DataBean>();
                        }
                        scoreListData.clear();
                        scoreListData = myPointsDataBean.getData();
                        if (scoreListData.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            wdjf_listview.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            wdjf_listview.setVisibility(View.VISIBLE);
                        }
                        adapter = new MyPointsAdapter(MyPointsActivity.this, scoreListData);
                        wdjf_listview.setAdapter(adapter);

                    } else if (code == 403) {
                        DialogUtil.showDialog(MyPointsActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(MyPointsActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("score_list", e.toString());
            }
        });

    }


    private void initView() {

        TextView tv_shop_name = (TextView) findViewById(R.id.tv_shop_name);
        wdjf_tv = (TextView) findViewById(R.id.wdjf_tv);
        tv_shop_name.setText(SPUtils.getString(MyPointsActivity.this, "shopName", ""));
        wdjf_tv.setText(SPUtils.getString(MyPointsActivity.this, "jifen", "") + "分");
        wdjf_listview = (ListView) findViewById(R.id.wdjf_listview);

        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
        tv_reload = (TextView) findViewById(R.id.tv_reload);

        tv_reload.setOnClickListener(this);

        setActivityTitle();
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("我的积分");
        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("积分记录");
        tv_subtitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle://积分记录
                startActivityByIntent(PointsRecordActivity.class);
                break;
            case R.id.tv_reload://重新加载
                doHttp();
                break;
            default:
                break;
        }
    }
}
