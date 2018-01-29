package com.runtai.newdexintong.module.myorder.presenter;

import android.content.Context;
import android.widget.Toast;

import com.google.gson.Gson;
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
import com.runtai.newdexintong.module.myorder.bean.ShoppingCartBean;
import com.runtai.newdexintong.module.myorder.contract.MyOrderContract;
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
 * Created by Administrator on 2017/4/19.
 */
public class MyShoppingCartPresenter implements MyOrderContract.Presenter {


    private MyOrderContract.View mView;
    private Context mContext;

    public MyShoppingCartPresenter(MyOrderContract.View view, Context context) {
        this.mView = view;
        mView.setPresenter(this);
        this.mContext = context;
    }

    @Override
    public void start() {

    }

    @Override
    public void doHttp() {

        if (!NetUtil.isNetworkAvailable(mContext)) {
            mView.noNetShow();
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            mView.closeLoading();
            return;
        }else {
            mView.haveNetShow();
        }
        String url = AppConstant.BASEURL2 + "api/cart/list";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

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
                        ShoppingCartBean shoppingCarBean = gson.fromJson(strJson, ShoppingCartBean.class);
                        List<ShoppingCartBean.DataBean> shoppingCarData = new ArrayList<ShoppingCartBean.DataBean>();
                        shoppingCarData.clear();
                        shoppingCarData = shoppingCarBean.getData();
                        mView.setData(shoppingCarData);
                    } else if (code == 403) {
                        mView.showDialogLoginAgain();
                    } else {
                        mView.showMessageByToast(msg);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                mView.closeLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("shoppingcar_data", e.toString());
                mView.closeLoading();
            }
        });
    }

}
