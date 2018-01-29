package com.runtai.newdexintong.module.personalcenter.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.personalcenter.bean.MyPointsDataBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static com.runtai.newdexintong.R.id.jifen_quan_tv;

/**
 * @作者：高炎鹏
 * @日期：2017/2/15时间15:48
 * @描述：我的积分兑换界面适配器
 */
public class MyPointsAdapter extends BaseAdapter {

    private Context mContext;
    private List<MyPointsDataBean.DataBean> list;
    private MyPointsDataBean.DataBean bean;

    public MyPointsAdapter(Context mContext, List<MyPointsDataBean.DataBean> list) {
        this.mContext = mContext;
        this.list = list;
    }


    @Override
    public int getCount() {
        return list == null ? 0 : list.size();
    }

    @Override
    public Object getItem(int position) {
        return list == null ? null : list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.adapter_jifenduihuan, null);
            holder.tv_limit_money = (TextView) view.findViewById(R.id.tv_limit_money);
            holder.jifen_quan_tv = (TextView) view.findViewById(jifen_quan_tv);
            holder.wdjf_jifen = (TextView) view.findViewById(R.id.wdjf_jifen);
            holder.wdjf_bt = (TextView) view.findViewById(R.id.wdjf_bt);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        bean = list.get(position);
        final String requiredPoints = String.valueOf(bean.getRequiredIntegral());
        holder.tv_limit_money.setText(String.valueOf(bean.getLimitMoney()));
        holder.jifen_quan_tv.setText(String.valueOf(bean.getDenomination()));
        holder.wdjf_jifen.setText(requiredPoints);
        holder.wdjf_bt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new MyAlertDialog(mContext)
                        .builder()
                        .setMsg(list.get(position).getName() + "将消耗" + requiredPoints + "积分\n确定要兑换吗？")
                        .setNegativeButton("取消", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {

                            }
                        })
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                exchangeByHttp(list.get(position).getId());

                            }
                        }).show();
            }
        });
        return view;
    }

    /**
     * 通过接口兑换积分
     */
    private void exchangeByHttp(int point_Id) {

        String url = AppConstant.BASEURL2 + "api/score/change";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", String.valueOf(point_Id));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Id", String.valueOf(point_Id))
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
                    ToastUtil.showToast(mContext, msg, Toast.LENGTH_SHORT);
//                    ((BaseCommonActivity) mContext).showTips(mContext, R.mipmap.toast_right,
//                            msg);
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("exchange_point", e.toString());
            }
        });
    }

    static class ViewHolder {
        // ImageView wdjf_img;
        TextView tv_limit_money;
        TextView jifen_quan_tv;//背景上的金额
        TextView wdjf_jifen;
        TextView wdjf_bt;
    }


}
