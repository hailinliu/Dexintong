package com.runtai.newdexintong.module.homepage.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
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
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.homepage.adapter.SpecialListAdapter;
import com.runtai.newdexintong.module.homepage.bean.SpecialListBean;
import com.runtai.newdexintong.module.homepage.bean.SpecialListDataBean;
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
 * 专场列表页面
 */
public class SpecialListActivity extends BaseActivity {

    private RelativeLayout head_back;
    private ImageView iv_special_pic;
    private List<SpecialListDataBean.DataBean.ListBean> mData;
    private String paramName0;
    private String paramValue0;
    private String mUrl;
    private ListView lv_special_list;
    private TextView tv_title;
    private List<SpecialListBean> mDatas_two;
    private RelativeLayout rl_no_data_show;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_special);
        showLoading();
        initView();
        Intent intent = getIntent();
        mUrl = intent.getStringExtra("mUrl");
        paramName0 = intent.getStringExtra("paramName0");
        paramValue0 = intent.getStringExtra("paramValue0");
        if (paramName0 != null && paramName0.length() > 0 && paramValue0 != null
                && paramValue0.length() > 0 && mUrl != null && mUrl.length() > 0) {

            httpData();
        }

        registerListener();
    }

    private void httpData() {

        if (!NetUtil.isNetworkAvailable(this)) {
            lv_special_list.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            lv_special_list.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }
        String url = AppConstant.BASEURL2 + mUrl;
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put(paramName0, paramValue0);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams(paramName0, paramValue0)
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
                        SpecialListDataBean specialListDataBean = gson.fromJson(strJson, SpecialListDataBean.class);
                        SpecialListDataBean.DataBean data = specialListDataBean.getData();
                        tv_title.setText(data.getTitle());
                        ImageLoader.getInstance().displayImage(data.getBanner().get(0).getImgPath(), iv_special_pic,
                                ImageLoadUtil.getDefaultHeadPicOptions(), null);
                        if (mData == null) {
                            mData = new ArrayList<SpecialListDataBean.DataBean.ListBean>();
                        }
                        mData.clear();
                        mData = data.getList();
                        if (mData.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            lv_special_list.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            lv_special_list.setVisibility(View.VISIBLE);
                        }
                        initData();
                    } else if (code == 403) {
                        DialogUtil.showDialog(SpecialListActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(SpecialListActivity.this, msg, Toast.LENGTH_SHORT);
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

    private void initData() {

        mDatas_two = new ArrayList<>();

        for (int i = 0; i < mData.size(); i++) {
            SpecialListBean bean = new SpecialListBean();
            bean.name = mData.get(i).getActivityName();
            List<SpecialListDataBean.DataBean.ListBean.ActivitysBean> activitys = mData.get(i).getActivitys();
            int activityId = mData.get(i).getActivityId();
            int specialId = mData.get(i).getSpecialId();
            List<String> imageList = new ArrayList<>();
            for (int j = 0; j < activitys.size(); j++) {
                imageList.add(activitys.get(j).getPhoto());
            }
            bean.setActivityId(activityId);
            bean.setSpecialId(specialId);
            bean.setImgList(imageList);
            bean.setActivitys(activitys);
            mDatas_two.add(bean);
        }
        lv_special_list.setAdapter(new SpecialListAdapter(SpecialListActivity.this, mDatas_two));
    }

    private void initView() {

        setActivityTitle();
        lv_special_list = (ListView) findViewById(R.id.lv_special_list);
        LayoutInflater inflator = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LinearLayout header = (LinearLayout) inflator.inflate(R.layout.header_special_list, null);
        lv_special_list.addHeaderView(header);
        iv_special_pic = (ImageView) findViewById(R.id.iv_special_pic);
        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);

        tv_reload = (TextView)findViewById(R.id.tv_reload);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);

    }

    private void registerListener() {
        head_back.setOnClickListener(this);
        tv_reload.setOnClickListener(this);
    }

    /**
     * 设置界面
     */
    private void setActivityTitle() {

        head_back = (RelativeLayout) findViewById(R.id.head_back);
        tv_title = (TextView) findViewById(R.id.tv_title);
//        tv_title.setText(getIntent().getStringExtra("special_name"));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);

        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_reload://重新加载
                httpData();
                break;
        }
    }
}
