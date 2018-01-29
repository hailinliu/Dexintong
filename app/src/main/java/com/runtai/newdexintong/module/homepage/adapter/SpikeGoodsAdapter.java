package com.runtai.newdexintong.module.homepage.adapter;

import android.content.Context;
import android.graphics.Paint;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.runtai.newdexintong.R;
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
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.homepage.bean.SpikeDataBean;
import com.runtai.newdexintong.module.homepage.bean.SpikeGoodsDetailBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

/**
 * Created by Administrator on 2017/2/14.
 */
public class SpikeGoodsAdapter extends BaseAdapter {

    private List<SpikeDataBean.DataBean.ListBean> mDatas;
    private LayoutInflater mInflater;
    private Context context;
    private PopupWindow popupWindow;
    private TextView tv_edit_goods_number;
    private ImageView iv_picture;
    private TextView item_goodsName;
    private TextView item_goodsStanded;
    private TextView item_goodsPrice;
    private TextView tv_min_number;
    private TextView tv_stock_number;
    private TextView tv_limit_number;
    private TextView tv_total_goods_number;
    private TextView tv_total_price;
    private TextView tv_not_enough;
    private RelativeLayout rl_total;
    private RelativeLayout rl_popuwindow;
    private TextView tv_date;
    private int referNumber;
    private String strMsg = "";
    /**
     * 每次编辑的数量
     */
    private int editNumOnce;

    public SpikeGoodsAdapter(Context context, List<SpikeDataBean.DataBean.ListBean> datas) {
        this.context = context;
        mInflater = LayoutInflater.from(context);
        mDatas = datas;
    }


    public void setData(List<SpikeDataBean.DataBean.ListBean> list) {

        this.mDatas = list;
//        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return mDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder holder = null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = mInflater.inflate(R.layout.item_find_goods, null);
            holder.tv_title = (TextView) convertView.findViewById(R.id.tv_title);
            holder.iv_add_shopcar = (ImageView) convertView.findViewById(R.id.iv_add_shopcar);
            holder.iv_goods = (ImageView) convertView.findViewById(R.id.iv_goods);
            holder.tv_money = (TextView) convertView.findViewById(R.id.tv_money);
            holder.tv_goods_spec = (TextView) convertView.findViewById(R.id.tv_goods_spec);
            holder.tv_stock_num = (TextView) convertView.findViewById(R.id.tv_stock_num);
            holder.tv_goods_spec_unit = (TextView) convertView.findViewById(R.id.tv_goods_spec_unit);
            holder.tv_text = (TextView) convertView.findViewById(R.id.tv_text);
            holder.tv_sell_out_icon = (TextView) convertView.findViewById(R.id.tv_sell_out_icon);
            holder.ll_spike = (LinearLayout) convertView.findViewById(R.id.ll_spike);
            holder.tv_create_time = (TextView) convertView.findViewById(R.id.tv_create_time);
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        holder.ll_spike.setVisibility(View.VISIBLE);
        final SpikeDataBean.DataBean.ListBean listBean = mDatas.get(position);
        holder.tv_title.setText(listBean.getItemName());
        ImageLoader.getInstance().displayImage(listBean.getPhoto(), holder.iv_goods, ImageLoadUtil.getDefaultHeadPicOptions(), null);
        holder.tv_goods_spec.setText(String.valueOf(listBean.getSpec()));
        holder.tv_goods_spec_unit.setText(listBean.getUnit().trim());
        holder.tv_create_time.setText(listBean.getMfd());
        holder.tv_stock_num.setText(String.valueOf(listBean.getBuyMax()));
        holder.tv_money.setText(StringUtil.strToDouble_new(String.valueOf(listBean.getPrice())));
        holder.tv_text.setText("限购");
        if (listBean.isIsSold()) {
            //售罄的情况
            holder.tv_sell_out_icon.setVisibility(View.VISIBLE);
            holder.iv_add_shopcar.setEnabled(false);
        } else {
            holder.tv_sell_out_icon.setVisibility(View.GONE);
            holder.iv_add_shopcar.setEnabled(true);
        }
        holder.iv_add_shopcar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                getGoosMsg(listBean.getId());
            }


        });
        return convertView;
    }

    class ViewHolder {

        TextView tv_title, tv_money, tv_goods_spec, tv_stock_num, tv_goods_spec_unit;
        ImageView iv_add_shopcar, iv_goods;
        TextView tv_text;
        TextView tv_sell_out_icon;
        LinearLayout ll_spike;
        TextView tv_create_time;
    }


    private void getGoosMsg(int idValue) {
        if (!NetUtil.isNetworkAvailable(context)) {
            ToastUtil.showToast(context, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/promotion/item";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(context, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", String.valueOf(idValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Id", String.valueOf(idValue))
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
                        SpikeGoodsDetailBean spikeGoodsDetailBean = gson.fromJson(strJson, SpikeGoodsDetailBean.class);
                        SpikeGoodsDetailBean.DataBean data = spikeGoodsDetailBean.getData();
                        initPopuWindow(data);
                    } else if (code == 403) {
                        DialogUtil.showDialog(context, context.getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(context, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }


            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
            }
        });
    }


    private void initPopuWindow(final SpikeGoodsDetailBean.DataBean mData) {

        closePopupWindow();
        LayoutInflater inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View popupWindowView = inflater.inflate(R.layout.popuwindow_add_shoppingcar, null);

        popupWindow = new PopupWindow(popupWindowView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        Button btn_confirm_add = (Button) popupWindowView.findViewById(R.id.btn_confirm_add);
        iv_picture = (ImageView) popupWindowView.findViewById(R.id.iv_picture);
        item_goodsName = (TextView) popupWindowView.findViewById(R.id.item_goodsName);
        item_goodsStanded = (TextView) popupWindowView.findViewById(R.id.item_goodsStanded);
        item_goodsPrice = (TextView) popupWindowView.findViewById(R.id.item_goodsPrice);
        tv_min_number = (TextView) popupWindowView.findViewById(R.id.tv_min_number);
        TextView tv_left_bracket = (TextView) popupWindowView.findViewById(R.id.tv_left_bracket);
        TextView tv_limit_buy_text = (TextView) popupWindowView.findViewById(R.id.tv_limit_buy_text);
        TextView tv_right_kuohao = (TextView) popupWindowView.findViewById(R.id.tv_right_kuohao);

        tv_stock_number = (TextView) popupWindowView.findViewById(R.id.tv_stock_number);
        tv_limit_number = (TextView) popupWindowView.findViewById(R.id.tv_limit_number);
        tv_edit_goods_number = (TextView) popupWindowView.findViewById(R.id.tv_edit_goods_number);
        tv_not_enough = (TextView) popupWindowView.findViewById(R.id.tv_not_enough);
        TextView tv_date = (TextView) popupWindowView.findViewById(R.id.tv_date);
        TextView tv_qiding_text = (TextView) popupWindowView.findViewById(R.id.tv_qiding_text);
        LinearLayout ll_date = (LinearLayout) popupWindowView.findViewById(R.id.ll_date);
        tv_total_goods_number = (TextView) popupWindowView.findViewById(R.id.tv_total_goods_number);
        tv_total_price = (TextView) popupWindowView.findViewById(R.id.tv_total_price);
        ImageView iv_reduce = (ImageView) popupWindowView.findViewById(R.id.iv_reduce);
        ImageView iv_add = (ImageView) popupWindowView.findViewById(R.id.iv_add);
        rl_total = (RelativeLayout) popupWindowView.findViewById(R.id.rl_total);
        rl_popuwindow = (RelativeLayout) popupWindowView.findViewById(R.id.rl_popuwindow);
        TextView tv_goods_spec_unit2 = (TextView) popupWindowView.findViewById(R.id.tv_goods_spec_unit);
        TextView tv_original_price_text = (TextView) popupWindowView.findViewById(R.id.tv_original_price_text);
        TextView tv_spike_original_price = (TextView) popupWindowView.findViewById(R.id.tv_spike_original_price);
        View view_popuwindow_bg_color = popupWindowView.findViewById(R.id.view_popuwindow_bg_color);
        ImageLoader.getInstance().displayImage(mData.getPhoto(), iv_picture,
                ImageLoadUtil.getDefaultHeadPicOptions(), null);
        item_goodsName.setText(mData.getItemName());
        item_goodsStanded.setText(String.valueOf(mData.getSpec()));
        tv_goods_spec_unit2.setText(mData.getUnit().trim());
        item_goodsPrice.setText(StringUtil.strToDouble_new(String.valueOf(mData.getKillPrice())));
        //限购
        tv_left_bracket.setVisibility(View.VISIBLE);
        tv_limit_buy_text.setVisibility(View.VISIBLE);
        tv_right_kuohao.setVisibility(View.VISIBLE);
        tv_limit_buy_text.setText(String.valueOf(mData.getMaxHtml()));

        //秒杀弹窗显示原价和秒杀价格
        tv_spike_original_price.setVisibility(View.VISIBLE);
        tv_original_price_text.setVisibility(View.VISIBLE);
        tv_spike_original_price.setText(StringUtil.strToDouble_new(String.valueOf(mData.getOriginalPrice())));
        //原价上面画线
        tv_spike_original_price.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);
        tv_original_price_text.getPaint().setFlags(Paint.STRIKE_THRU_TEXT_FLAG);

        tv_qiding_text.setVisibility(View.GONE);
        tv_min_number.setVisibility(View.GONE);
        btn_confirm_add.setVisibility(View.VISIBLE);
        //秒杀跟订货会时间隐藏
        ll_date.setVisibility(View.VISIBLE);
        tv_date.setText(mData.getMfd());
        final int buyMax = mData.getBuyMax();//最大购买数量
        final int stock = mData.getStock();
        final int saleFromCount = mData.getSaleFromCount();
        final int saleIncreaseCount = mData.getSaleIncreaseCount();
        tv_edit_goods_number.setText(String.valueOf(saleFromCount));
        tv_total_goods_number.setText(String.valueOf(saleFromCount));
        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(saleFromCount * mData.getKillPrice())));
        if (saleIncreaseCount <= 1) {
            editNumOnce = 1;
        } else {
            editNumOnce = saleIncreaseCount;
        }
        if (buyMax == 0) {
            //不限购
            referNumber = stock;
        } else {
            //限购
            if (buyMax > stock) {
                referNumber = stock;
            } else {
                referNumber = buyMax;
            }
        }

        final int limitCount = mData.getLimitCount();//已经购买数量

        //点击减号
        iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int editedNumber = getEditedNumber();
                if (editedNumber > saleFromCount) {
                    editedNumber = editedNumber - editNumOnce;
                    tv_edit_goods_number.setText(String.valueOf(editedNumber));
                    tv_total_goods_number.setText(String.valueOf(editedNumber));
                    tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(editedNumber * mData.getKillPrice())));
                } else {
                    ToastUtil.showToast(context, "购买数量不能小于最小起订量", Toast.LENGTH_SHORT);
                }
            }
        });
        //点击加号
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int editedNumber = getEditedNumber();
                if (buyMax == 0) {
                    //不限购
                    if (editedNumber < mData.getStock()) {
                        editedNumber = editedNumber + editNumOnce;
                        tv_edit_goods_number.setText(String.valueOf(editedNumber));
                        tv_total_goods_number.setText(String.valueOf(editedNumber));
                        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(editedNumber * mData.getKillPrice())));
                    } else {
                        ToastUtil.showToast(context, "购买数量不能大于库存数", Toast.LENGTH_SHORT);
                    }
                } else {
                    //限购
                    if (referNumber == 0) {
                        ToastUtil.showToast(context, "此商品已经售罄", Toast.LENGTH_SHORT);
                    } else {
                        if (referNumber - limitCount > 0) {
                            if (editedNumber < (referNumber - limitCount)) {
                                editedNumber = editedNumber + editNumOnce;
                                tv_edit_goods_number.setText(String.valueOf(editedNumber));
                                tv_total_goods_number.setText(String.valueOf(editedNumber));
                                tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(editedNumber * mData.getKillPrice())));
                            } else {
                                ToastUtil.showToast(context, "您已达到最大购买数", Toast.LENGTH_SHORT);
                            }
                        } else {
                            ToastUtil.showToast(context, "抱歉，您已达到最大购买数，不能继续购买", Toast.LENGTH_SHORT);

                        }
                    }
                }
            }
        });

        //下单
        btn_confirm_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showOrderTips(mData);
            }
        });
        //点击周边关闭popuwindow
        view_popuwindow_bg_color.setOnTouchListener(new View.OnTouchListener() {
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
     * 下单提醒
     *
     * @param mData
     */
    private void showOrderTips(final SpikeGoodsDetailBean.DataBean mData) {
        new MyAlertDialog(context)
                .builder().setTitle("温馨提示")
                .setMsg("确定立即下单吗？")
                .setCancelable(false)
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                })
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        makeSpikeOrder(mData.getId());
                    }
                }).show();
    }


    /**
     * 秒杀下单
     */
    private void makeSpikeOrder(int goodsId) {

        if (!NetUtil.isNetworkAvailable(context)) {
            ToastUtil.showToast(context, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/promotion/spikecreate";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(context, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Id", String.valueOf(goodsId));
        map.put("Qty", String.valueOf(getEditedNumber()));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Id", String.valueOf(goodsId))
                .addParams("Qty", String.valueOf(getEditedNumber()))
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
                        closePopupWindow();
                    }
                    if (code == 403) {
                        DialogUtil.showDialog(context, context.getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(context, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
            }
        });

    }

    /**
     * 关闭窗口
     */
    private void closePopupWindow() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    /**
     * 获得编辑后的显示数量
     *
     * @return
     */
    private int getEditedNumber() {
        return Integer.parseInt(tv_edit_goods_number.getText().toString().trim());
    }

}
