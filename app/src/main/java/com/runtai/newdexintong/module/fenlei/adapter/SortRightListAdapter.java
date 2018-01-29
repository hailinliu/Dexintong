package com.runtai.newdexintong.module.fenlei.adapter;

import android.content.Context;
import android.os.Handler;
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
import com.runtai.newdexintong.module.fenlei.bean.EditShoppingCartGoodsBean;
import com.runtai.newdexintong.module.fenlei.bean.SortRightDataBean;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.homepage.bean.OrdinaryGoodsDetailBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

public class SortRightListAdapter extends BaseAdapter implements View.OnClickListener {

    private Context mContext;
    private Handler handler;
    private List<SortRightDataBean.DataBean.ListBean> list;
    
    //    private String userId;
    private int itemId;
    private int onceChangeNumber;


    public SortRightListAdapter(Context context, List<SortRightDataBean.DataBean.ListBean> list) {
        this.mContext = context;
        this.list = list;
//        userId = SPUtils.getString(context, "userid", "");
    }

    public void setData(List<SortRightDataBean.DataBean.ListBean> list) {

        this.list = list;
//        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        if (list != null) {
            return list.size();
        }
        return 0;
    }

    @Override
    public Object getItem(int position) {
        if (list != null) {
            return list.get(position);
        }
        return null;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int index, View view, ViewGroup parent) {
        ViewHolder holder;
        if (view == null) {
            holder = new ViewHolder();
            view = LayoutInflater.from(mContext).inflate(R.layout.activity_fenlei_gridview_item, null);
            holder.fenlei_gridview_item_image = (ImageView) view.findViewById(R.id.fenlei_gridview_item_image);
            holder.fenlei_gridview_item_name = (TextView) view.findViewById(R.id.fenlei_gridview_item_name);
            holder.fenlei_gridview_item_spec = (TextView) view.findViewById(R.id.fenlei_gridview_item_spec);
            holder.fenlei_gridview_item_spec_unit = (TextView) view.findViewById(R.id.fenlei_gridview_item_spec_unit);
            holder.fenlei_gridview_item_price = (TextView) view.findViewById(R.id.fenlei_gridview_item_price);
            holder.fenlei_gridview_item_car = (ImageView) view.findViewById(R.id.fenlei_gridview_item_car);
            holder.tv_sell_out_icon = (TextView) view.findViewById(R.id.tv_sell_out_icon);
            holder.tv_create_time = (TextView) view.findViewById(R.id.tv_create_time);
            view.setTag(holder);
        } else {
            holder = (ViewHolder) view.getTag();
        }
        final SortRightDataBean.DataBean.ListBean bean = list.get(index);
        holder.fenlei_gridview_item_name.setText(bean.getName());
        holder.fenlei_gridview_item_spec.setText(String.valueOf(bean.getSpec()));
        holder.fenlei_gridview_item_spec_unit.setText(bean.getUnit().trim());
        holder.tv_create_time.setText(bean.getMfd());
        ImageLoader.getInstance().displayImage(bean.getPhoto(), holder.fenlei_gridview_item_image,
                ImageLoadUtil.getDefaultHeadPicOptions(), null);
        holder.fenlei_gridview_item_price.setText(StringUtil.strToDouble_new(String.valueOf(bean.getPrice())));

        if (bean.getStock() > 0 && bean.getStock() > bean.getSaleFromCount()) {
            //没有售罄
            holder.tv_sell_out_icon.setVisibility(View.GONE);
            holder.fenlei_gridview_item_car.setEnabled(true);
        }else {
            holder.tv_sell_out_icon.setVisibility(View.VISIBLE);
            holder.fenlei_gridview_item_car.setEnabled(false);
        }
        holder.fenlei_gridview_item_car.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getGoodsDetail(String.valueOf(bean.getItemId()));
            }
        });
        return view;
    }


    class ViewHolder {
        ImageView fenlei_gridview_item_image;
        TextView fenlei_gridview_item_name;
        TextView fenlei_gridview_item_spec, fenlei_gridview_item_spec_unit;
        TextView fenlei_gridview_item_price;
        ImageView fenlei_gridview_item_car;
        TextView tv_sell_out_icon;
        TextView tv_create_time;
    }

    /**
     * 读取
     */
    private void getGoodsDetail(String idValue) {

        if (!NetUtil.isNetworkAvailable(mContext)) {
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/cart/item";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("ItemId", idValue);
        map.put("SpecialId", "0");
        map.put("ActivityId", "0");
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("ItemId", idValue)
                .addParams("SpecialId", "0")
                .addParams("ActivityId", "0")
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
                        OrdinaryGoodsDetailBean getGoodsDetailBean = gson.fromJson(strJson, OrdinaryGoodsDetailBean.class);
                        OrdinaryGoodsDetailBean.DataBean data = getGoodsDetailBean.getData();
                        showPopopwindow(data);

                    }else if (code == 403) {
                        DialogUtil.showDialog(mContext, mContext.getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(mContext, msg, Toast.LENGTH_SHORT);
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

    private PopupWindow popupWindow;
    private View popupWindowView;
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


    /**
     * 显示PopWindow
     */
    private void showPopopwindow(OrdinaryGoodsDetailBean.DataBean mData) {

        closePopupWindow();
        LayoutInflater inflater = (LayoutInflater) mContext.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        popupWindowView = inflater.inflate(R.layout.popuwindow_add_shoppingcar, null);

        popupWindow = new PopupWindow(popupWindowView, WindowManager.LayoutParams.MATCH_PARENT,
                WindowManager.LayoutParams.WRAP_CONTENT, true);

        Button btn_confirm_add = (Button) popupWindowView.findViewById(R.id.btn_confirm_add);
        ImageView iv_shoppingcar_goods_pic = (ImageView) popupWindowView.findViewById(R.id.iv_shoppingcar_goods_pic);
        ImageView iv_picture = (ImageView) popupWindowView.findViewById(R.id.iv_picture);
        item_goodsName = (TextView) popupWindowView.findViewById(R.id.item_goodsName);
        item_goodsStanded = (TextView) popupWindowView.findViewById(R.id.item_goodsStanded);
        TextView tv_goods_spec_unit = (TextView) popupWindowView.findViewById(R.id.tv_goods_spec_unit);
        item_goodsPrice = (TextView) popupWindowView.findViewById(R.id.item_goodsPrice);
        tv_min_number = (TextView) popupWindowView.findViewById(R.id.tv_min_number);

        tv_stock_number = (TextView) popupWindowView.findViewById(R.id.tv_stock_number);
        tv_limit_number = (TextView) popupWindowView.findViewById(R.id.tv_limit_number);
        tv_edit_goods_number = (TextView) popupWindowView.findViewById(R.id.tv_edit_goods_number);
        tv_not_enough = (TextView) popupWindowView.findViewById(R.id.tv_not_enough);
        TextView tv_date = (TextView) popupWindowView.findViewById(R.id.tv_date);

        tv_total_goods_number = (TextView) popupWindowView.findViewById(R.id.tv_total_goods_number);
        tv_total_price = (TextView) popupWindowView.findViewById(R.id.tv_total_price);
        ImageView iv_reduce = (ImageView) popupWindowView.findViewById(R.id.iv_reduce);
        ImageView iv_add = (ImageView) popupWindowView.findViewById(R.id.iv_add);
        rl_total = (RelativeLayout) popupWindowView.findViewById(R.id.rl_total);
        RelativeLayout rl_popuwindow = (RelativeLayout) popupWindowView.findViewById(R.id.rl_popuwindow);


        item_goodsName.setText(mData.getName());
        item_goodsStanded.setText(String.valueOf(mData.getSpec()));
        tv_goods_spec_unit.setText(mData.getUnit());
        final double price = mData.getPrice();
        item_goodsPrice.setText(StringUtil.strToDouble_new(String.valueOf(price)));
        final int saleFromCount = mData.getSaleFromCount();//最小起订量
        tv_min_number.setText(String.valueOf(saleFromCount));
        tv_edit_goods_number.setText(String.valueOf(mData.getAlreadyItem()));
        tv_total_goods_number.setText(String.valueOf(mData.getAlreadyItem()));
        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(mData.getAlreadyItem()*price)));
        tv_date.setText(mData.getMfd());
        ImageLoader.getInstance().displayImage(mData.getPhoto(),iv_picture,ImageLoadUtil.getDefaultHeadPicOptions(),null);
        itemId = mData.getItemId();


        View view_popuwindow_bg_color = popupWindowView.findViewById(R.id.view_popuwindow_bg_color);
        btn_confirm_add.setOnClickListener(this);

        //点击减号
        iv_reduce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editShoppingCartByHttp(0, itemId, price);

            }
        });
        //点击加号
        iv_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                editShoppingCartByHttp(1, itemId, price);
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
     * 关闭窗口
     */
    private void closePopupWindow() {

        if (popupWindow != null && popupWindow.isShowing()) {
            popupWindow.dismiss();
            popupWindow = null;
        }
    }

    @Override
    public void onClick(View v) {
        double price = Double.parseDouble(item_goodsPrice.getText().toString().trim());
        switch (v.getId()) {

            case R.id.btn_confirm_add://确定按钮
//                editShoppingCartByHttp();
                closePopupWindow();
                break;
        }
    }


    /**
     * 编辑购物车中商品数量
     *
     * @param ActionValue 1添加、0减少
     */
    private void editShoppingCartByHttp(int ActionValue, int itemIdValue, final double goodsPrice) {
        
        if (!NetUtil.isNetworkAvailable(mContext)) {
            ToastUtil.showToast(mContext, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/cart/atc";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(mContext, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("ItemId", String.valueOf(itemIdValue));
        map.put("SpecialId", "0");
        map.put("ActivityId", "0");
        map.put("Action", String.valueOf(ActionValue));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("ItemId", String.valueOf(itemIdValue))
                .addParams("SpecialId", "0")//不是专场里面点击编辑购物车此处都为0
                .addParams("ActivityId", "0")//不是专场里面点击编辑购物车此处都为0
                .addParams("Action", String.valueOf(ActionValue))
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
                        EditShoppingCartGoodsBean bean = gson.fromJson(strJson, EditShoppingCartGoodsBean.class);
                        EditShoppingCartGoodsBean.DataBean data = bean.getData();
                        int num = data.getNum();
                        int kind = data.getKind();
                        SPUtils.putString(mContext, "kindNumber", String.valueOf(kind));
                        SPUtils.putString(mContext, "TotalNumber", String.valueOf(num));
                        int qty = data.getQty();
                        tv_edit_goods_number.setText(String.valueOf(qty));
                        tv_total_goods_number.setText(String.valueOf(qty));
                        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(qty * goodsPrice)));
                    } else if (code == 403) {
                        DialogUtil.showDialog(mContext, mContext.getResources().getString(R.string.need_login_again));
                    }
                    ToastUtil.showToast(mContext, msg, Toast.LENGTH_SHORT);
                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("addShoppingCar", e.toString());
            }
        });
    }


}
