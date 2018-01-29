package com.runtai.newdexintong.module.personalcenter.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
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
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.personalcenter.adapter.OtherReplaceGoodsAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.OtherReplaceGoodsBean;
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
 * 申请其他替换对应的界面
 */
public class OtherReplaceActivity extends BaseActivity {

    private ImageView iv_search3;
    private TextView tv_subtitle;
    private RelativeLayout head_back;
    private ListView lv_other_replace;
    private List<OtherReplaceGoodsBean.DataBean> mData;
    private EditText et_search2;
    private static HashMap<Integer, Boolean> isChecked;
    private OtherReplaceGoodsAdapter adapter;
    private TextView tv_no_data;
    private String QValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_replace);

        showLoading();
        httpData();
        initView();
        registerListener();
    }

    private void httpData() {

        String url = AppConstant.BASEURL2 + "api/order/searchs";
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
                        OtherReplaceGoodsBean otherReplaceGoodsBean = gson.fromJson(strJson, OtherReplaceGoodsBean.class);
                        if (mData == null) {
                            mData = new ArrayList<OtherReplaceGoodsBean.DataBean>();
                        }
                        mData.clear();
                        mData = otherReplaceGoodsBean.getData();
                        adapter = new OtherReplaceGoodsAdapter(OtherReplaceActivity.this, mData);
                        lv_other_replace.setAdapter(adapter);
                    } else if (code == 403) {
                        DialogUtil.showDialog(OtherReplaceActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(OtherReplaceActivity.this, msg, Toast.LENGTH_SHORT);
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
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
        isChecked = new HashMap<>();
        lv_other_replace = (ListView) findViewById(R.id.lv_other_replace);
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        tv_subtitle.setOnClickListener(this);
        lv_other_replace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
//                isChecked.clear();
                OtherReplaceGoodsBean.DataBean dataBean = mData.get(position);
                for (int i = 0; i < mData.size(); i++) {
                    if (position == i) {
                        isChecked.put(position, true);
                    } else {
                        isChecked.put(i, false);
                    }
                }

                OtherReplaceGoodsAdapter.setIsChecked(isChecked);
                adapter.notifyDataSetChanged();

                showMyDialog(dataBean);

            }

        });
    }

    /**
     * 弹出提示框
     */
    private void showMyDialog(final OtherReplaceGoodsBean.DataBean bean) {

        new MyAlertDialog(OtherReplaceActivity.this).builder()
                .setTitle("您确定要调换为选中的商品吗？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent();
                        intent.putExtra("unit", bean.getUnit());
                        intent.putExtra("specStr", String.valueOf(bean.getSpec()));
                        intent.putExtra("goodsName", bean.getItemName());
                        intent.putExtra("goodsPrice", StringUtil.strToDouble_new(String.valueOf(bean.getOriginalPrice())));
                        intent.putExtra("stock", String.valueOf(bean.getStock()));
                        intent.putExtra("itemId", String.valueOf(bean.getItemId()));
                        setResult(100, intent);
                        onBackPressed();
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    /**
     * 设置界面标题栏
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setVisibility(View.GONE);
        RelativeLayout rl_search_result = (RelativeLayout) findViewById(R.id.rl_search_result);
        iv_search3 = (ImageView) findViewById(R.id.iv_search3);
        rl_search_result.setVisibility(View.VISIBLE);
        et_search2 = (EditText) findViewById(R.id.et_search2);
        tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("搜索");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle://点击搜索
                if (checkSearchInfo()) {
                    Intent intent = new Intent(this, OtherReplaceSearchResultActivity.class);
                    intent.putExtra("goodsname", getSearchContent());
                    startActivityByIntent(intent);
                    finish();
                }
                break;
        }
    }

    private boolean checkSearchInfo() {

        String search_content = getSearchContent();
        if (TextUtils.isEmpty(search_content)) {
            ToastUtil.showToast(this, "请输入要搜索的商品名称", Toast.LENGTH_SHORT);
            return false;
        }
        return true;

    }

    private String getSearchContent() {
        return et_search2.getText().toString().trim();
    }
}
