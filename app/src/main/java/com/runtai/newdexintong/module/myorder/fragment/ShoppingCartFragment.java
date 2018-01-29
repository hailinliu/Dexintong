package com.runtai.newdexintong.module.myorder.fragment;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.activity.BaseCommonActivity;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.comment.utils.Arith;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.homepage.activity.RegularBuyListActivity;
import com.runtai.newdexintong.module.homepage.activity.SpecialSaleActivity;
import com.runtai.newdexintong.module.myorder.activity.OrderConfirmActivity;
import com.runtai.newdexintong.module.myorder.adapter.MyShoppingCartAdapter;
import com.runtai.newdexintong.module.myorder.adapter.OrderFailedGoodsAdapter;
import com.runtai.newdexintong.module.myorder.bean.ConfirmPayBean;
import com.runtai.newdexintong.module.myorder.bean.MyShoppingCarGroupBean;
import com.runtai.newdexintong.module.myorder.bean.ShoppingCartBean;
import com.runtai.newdexintong.module.myorder.contract.MyOrderContract;
import com.runtai.newdexintong.module.myorder.presenter.MyShoppingCartPresenter;
import com.runtai.newdexintong.module.myorder.view.PinnedHeaderListView;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.Request;

import static com.runtai.newdexintong.module.myorder.adapter.MyShoppingCartAdapter.setIsGroupChecked;

/**
 * 购物车对应的fragment
 */

public class ShoppingCartFragment extends BaseFragment
        implements MyShoppingCartAdapter.CallBack, MyOrderContract.View {

    List<ShoppingCartBean.DataBean.ItemsBean> mDatas;
    List<ShoppingCartBean.DataBean.ItemsBean> checkList;//存放选中条目的集合
    private PinnedHeaderListView list_Shopping;
    private MyShoppingCartAdapter myOrderAdapter;
    private CheckBox cb_checkall;
    private TextView tv_total_price;
    double totalPrice = 0;
    private TextView gong;
    private TextView fuhao;
    private Button bt_goPay_or_delete;
    private boolean flag = true;
    private TextView tv_subtitle;
    private HashMap<Integer, Boolean> isShow;
    public static HashMap<Integer, Boolean> isGroupChecked;
    private LinearLayout ll_reduced;
    private LinearLayout ll_kinds_and_piece;
    private RelativeLayout rl_bottom_part;
    private View tv_bg1;
    private TextView tv_kinds_number;
    private TextView tv_piece;
    private TextView tv_reduced_number;
    private int selectedNumber;
    private RelativeLayout rl_success;
    private MainActivity mainActivity;

    private Map<Integer, ShoppingCartBean.DataBean> mapdata;

    /**
     * 购物车总数量
     */
    private int item_total_count;
    /**
     * 赠品数量,现在这个2是临时设置的
     */
    private int item_present_count;
    /**
     * 全选时的数量
     */
    private int selected_all_count;

    private RelativeLayout rl_tourist_login_shoppingcar;
    /**
     * 满减对应选中的价格总和
     */
    private double fullcut_total_price = 0;
    /**
     * 满赠对应的价格的价格总和
     */
    private double fullpresent_total_price = 0;
    private View group_header_right_part;
    private int sectionValue;
    private MyShoppingCartPresenter myOrderPresenter;
    private View group_header_left_part;
    private TextView tv_checked_all_text;
    private List<MyShoppingCarGroupBean> groupStatusData;
    private ShoppingCartBean.DataBean.ItemsBean shoppingCartItemBean;
    /**
     * 点击去结算传递到订单确认页面的id字符串
     * 参数：ids string 专场编号:活动编号_专场编号:活动编号_专场编号，如：1:2_1:2
     */
    private String ids_str;
    /**
     * 单个分组进行条目选择的时候的存储
     */
    StringBuffer sb_changed;

    private int check_group_count;
    private double totalReduceMoney;
    private RelativeLayout rl_cart_no_data;
    private Button btn_goto_special;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;
    private RelativeLayout rl_shoppingcar_data;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_myorder);
        View view = getContentView();
        initView(view);
        initData();
        registerListener();

    }


    @Override
    public void onStart() {
        super.onStart();
        
        //当从购物车跳转到活动页面返回后刷新及时刷新数据。
        if (SPUtils.getBoolean(getActivity(), "isRefresh", false)) {
            SPUtils.putBoolean(getActivity(), "isRefresh", false);
            myOrderAdapter = null;
            if (myOrderPresenter != null) {
                httpCartData();
            }
        }

        //下单成功后去查看订单后返回购物车
        if (SPUtils.getBoolean(getActivity(), "isWatchOrder", false)) {
            SPUtils.putBoolean(getActivity(), "isWatchOrder", false);
            myOrderAdapter = null;
            if (myOrderPresenter != null) {
                httpCartData();
            }
        }

       //从确认支付页面返回
        if (SPUtils.getBoolean(getActivity(), "fromConfirmPay", false)) {
            SPUtils.putBoolean(getActivity(), "fromConfirmPay", false);
            myOrderAdapter = null;
            if (myOrderPresenter != null) {
                httpCartData();
            }

            //库存不足的时候字体显示红色
            if (SPUtils.getBoolean(getActivity(), "showRedTitle", false)) {
                SPUtils.putBoolean(getActivity(), "showRedTitle", false);
                List<String> failedList = new ArrayList<>();
                failedList.clear();
                String failedIds = SPUtils.getString(getActivity(), "failedIds", "");
                if (failedIds.length() > 0 && !"".equals(failedIds)) {
                    ConfirmPayBean.DataBean dataBean1 = GsonUtil.buildGson().fromJson(failedIds, ConfirmPayBean.DataBean.class);
                    List<ConfirmPayBean.DataBean.FailedBean> failed = dataBean1.getFailed();
                    for (int i = 0; i < failed.size(); i++) {
                        failedList.add(failed.get(i).getItemName());
                    }
                }
                Window dialog = ((BaseActivity) getActivity()).createDialog(R.layout.dialog_order_failed_goods_list, getActivity());
                ListView lv_order_failed_goods = (ListView) dialog.findViewById(R.id.lv_order_failed_goods);
                lv_order_failed_goods.setAdapter(new OrderFailedGoodsAdapter(getActivity(),failedList));
                //点击知道了
                ((TextView)dialog.findViewById(R.id.tv_confirm)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) getActivity()).closeDialog();
                    }
                });
                ((ImageView)dialog.findViewById(R.id.iv_close)).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ((BaseActivity) getActivity()).closeDialog();
                    }
                });

            }
           
        }

        //购物车列表无数据的时候，跳转到常购清单并返回
        if (SPUtils.getBoolean(getActivity(), "fromRegularbuy", false)) {
            SPUtils.putBoolean(getActivity(), "fromRegularbuy", false);
            myOrderAdapter = null;
            if (myOrderPresenter != null) {
                httpCartData();
            }
        }

    }


    private void initData() {

        groupStatusData = new ArrayList<>();
        groupStatusData.clear();

        mapdata = new HashMap<>();
        mDatas = new ArrayList<>();
        mapdata.clear();
        mDatas.clear();
        checkList = new ArrayList<>();
        checkList.clear();

    }

    @Override
    public void onFragmentStart() {
        super.onFragmentStart();

//        setNoChecked();
        myOrderAdapter = null;
        showLoading();
        myOrderPresenter = new MyShoppingCartPresenter(this, getActivity());
        httpCartData();
     
    }

    private void httpCartData() {
        myOrderPresenter.doHttp();
    }


    private void initView(View view) {

        mainActivity = (MainActivity) getActivity();
        isShow = new HashMap<>();
        isGroupChecked = new HashMap<>();
        setMyOrderTitle(view);

        sb_changed = new StringBuffer();

        list_Shopping = (PinnedHeaderListView) view.findViewById(R.id.list_Shopping);
        cb_checkall = (CheckBox) view.findViewById(R.id.cb_checkall);
        tv_checked_all_text = (TextView) view.findViewById(R.id.tv_checked_all_text);

        gong = (TextView) view.findViewById(R.id.gong);
        fuhao = (TextView) view.findViewById(R.id.fuhao);
        tv_total_price = (TextView) view.findViewById(R.id.tv_total_price);
        bt_goPay_or_delete = (Button) view.findViewById(R.id.bt_goPay_or_delete);
        tv_kinds_number = (TextView) view.findViewById(R.id.tv_kinds_number);
        tv_piece = (TextView) view.findViewById(R.id.tv_piece);
        tv_reduced_number = (TextView) view.findViewById(R.id.tv_reduced_number);
        ll_reduced = (LinearLayout) view.findViewById(R.id.ll_reduced);
        ll_kinds_and_piece = (LinearLayout) view.findViewById(R.id.ll_kinds_and_piece);
        rl_bottom_part = (RelativeLayout) view.findViewById(R.id.rl_bottom_part);
        tv_bg1 = view.findViewById(R.id.tv_bg1);

//        rl_success = (RelativeLayout) view.findViewById(R.id.rl_success);
        rl_tourist_login_shoppingcar = (RelativeLayout) view.findViewById(R.id.rl_tourist_login_shoppingcar);
        group_header_right_part = view.findViewById(R.id.group_header_right_part);
        group_header_left_part = view.findViewById(R.id.group_header_left_part);
        group_header_right_part.setBackgroundColor(Color.parseColor("#00ffffff"));
        group_header_left_part.setBackgroundColor(Color.parseColor("#00ffffff"));
        rl_cart_no_data = (RelativeLayout) view.findViewById(R.id.rl_cart_no_data);
        btn_goto_special = (Button) view.findViewById(R.id.btn_goto_special);

        rl_no_net = (RelativeLayout) view.findViewById(R.id.rl_no_net);
        tv_reload = (TextView) findViewById(R.id.tv_reload);
        rl_shoppingcar_data = (RelativeLayout) view.findViewById(R.id.rl_shoppingcar_data);

    }

    private void registerListener() {
        tv_reload.setOnClickListener(this);
        bt_goPay_or_delete.setOnClickListener(this);
        tv_subtitle.setOnClickListener(this);
        group_header_right_part.setOnClickListener(this);
        group_header_left_part.setOnClickListener(this);
        cb_checkall.setOnClickListener(this);
       
        tv_checked_all_text.setOnClickListener(this);
        btn_goto_special.setOnClickListener(this);
        list_Shopping.setOnItemClickListener(new PinnedHeaderListView.OnItemClickListener() {

            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int section, int position, long id) {

            }

            @Override
            public void onSectionClick(AdapterView<?> adapterView, View view, int section, long id) {

                groupChecked(section);
            }
        });

    }

    /**
     * 选中分组标题的checkbox
     *
     * @param clicked_position
     */
    private void groupChecked(int clicked_position) {

        Boolean isChecked = myOrderAdapter.getIsGroupChecked().get(clicked_position);
        if (isChecked != null) {
            if (isChecked) {
                isGroupChecked.put(clicked_position, false);
            } else {
                isGroupChecked.put(clicked_position, true);
            }
        } else {

            isGroupChecked.put(clicked_position, true);
        }
        setIsGroupChecked(isGroupChecked);
        groupCheckedDataChange(clicked_position);
        myOrderAdapter.notifyDataSetChanged();
    }

    /**
     * 选中分组的checkbox时数据的更新
     *
     * @param
     */
    private void groupCheckedDataChange(int clicked_position) {

        selected_all_count = item_total_count - item_present_count;

        ShoppingCartBean.DataBean dataBean = mapdata.get(clicked_position);
        LogUtil.e("clicked_position", String.valueOf(dataBean.getSpecialId()));
        if (!dataBean.isGroupCheck) {
            dataBean.isGroupCheck = true;
            int mSpecialId = dataBean.getSpecialId();
            for (int j = 0; j < dataBean.getItems().size(); j++) {
                shoppingCartItemBean = dataBean.getItems().get(j);
                shoppingCartItemBean.isCheck = true;
                checkList.add(shoppingCartItemBean);

                //满赠 专场ID (0普场；1满减；2满赠；3折扣；4限时秒杀；5订货会预售)
                if (dataBean.getSpecialId() == 2) {
                    double fullPresent_money = Arith.mul(Double.parseDouble(String.valueOf(shoppingCartItemBean.getOriginalPrice())),
                            Double.parseDouble(String.valueOf(shoppingCartItemBean.getBuyNum())));
                    fullpresent_total_price = Arith.add(dataBean.getOriginalMoney(), fullPresent_money);

                } else if (dataBean.getSpecialId() == 1) {
                    //满减
                    double fullCut_money = Arith.mul(Double.parseDouble(String.valueOf(shoppingCartItemBean.getOriginalPrice())),
                            Double.parseDouble(String.valueOf(shoppingCartItemBean.getBuyNum())));
                    fullcut_total_price = Arith.add(dataBean.getOriginalMoney(), fullCut_money);
                }

                double addMoney = Arith.mul(Double.parseDouble(String.valueOf(shoppingCartItemBean.getOriginalPrice())),
                        Double.parseDouble(String.valueOf(shoppingCartItemBean.getBuyNum())));

                selectedNumber = Arith.add(selectedNumber,
                        shoppingCartItemBean.getBuyNum());
                if (shoppingCartItemBean.isIsSend()) {//如果有赠品
                    double giftPrice = shoppingCartItemBean.getOriginalPrice() * shoppingCartItemBean.getBuyNum();
                    totalPrice = Arith.add(totalPrice, addMoney) - giftPrice;
                } else {
                    totalPrice = Arith.add(totalPrice, addMoney);
                }

            }
            // 3折扣；6天天特价
            if (mSpecialId == 6) {
                double benefitMoney = dataBean.getBenefitMoney();
                totalPrice = totalPrice - benefitMoney;
                totalReduceMoney = totalReduceMoney + benefitMoney;
            } else if (mSpecialId == 3) {
                double benefitMoney2 = dataBean.getBenefitMoney();
                totalPrice = totalPrice - benefitMoney2;
                totalReduceMoney = totalReduceMoney + benefitMoney2;
            } else if (mSpecialId == 1) {
                double benefitMoney3 = dataBean.getBenefitMoney();
                if (fullcut_total_price >= benefitMoney3) {
                    totalReduceMoney = totalReduceMoney + benefitMoney3;
                    totalPrice = totalPrice - benefitMoney3;
                }
            }
            if (checkList.size() >= selected_all_count) {
                cb_checkall.setChecked(true);
            }
//            tv_kinds_number.setText(String.valueOf(removeDuplicate(checkList).size()));
        } else {

            dataBean.isGroupCheck = false;

            int specId = dataBean.getSpecialId();
            for (int j = 0; j < dataBean.getItems().size(); j++) {

                shoppingCartItemBean = dataBean.getItems().get(j);
                shoppingCartItemBean.isCheck = false;
                checkList.remove(shoppingCartItemBean);
                //满赠
                if (mapdata.get(clicked_position).getSpecialId() == 2) {
                    double fullPresent_reduce_money = Arith.mul(Double.parseDouble(String.valueOf(shoppingCartItemBean.getOriginalPrice())),
                            Double.parseDouble(String.valueOf(shoppingCartItemBean.getBuyNum())));
                    fullpresent_total_price = Arith.sub(dataBean.getOriginalMoney(), fullPresent_reduce_money);
                } else if (mapdata.get(clicked_position).getSpecialId() == 1) {
                    //满减
                    double fullCut_reduce_money = Arith.mul(Double.parseDouble(String.valueOf(shoppingCartItemBean.getOriginalPrice())),
                            Double.parseDouble(String.valueOf(shoppingCartItemBean.getBuyNum())));
                    fullcut_total_price = Arith.sub(dataBean.getOriginalMoney(), fullCut_reduce_money);
                }

                int buyNum = shoppingCartItemBean.getBuyNum();
                double minusMoney = Arith.mul(Double.parseDouble(String.valueOf(shoppingCartItemBean.getOriginalPrice())),
                        Double.parseDouble(String.valueOf(buyNum)));
                if (shoppingCartItemBean.isIsSend()) {//如果有赠品
                    double giftPrice = shoppingCartItemBean.getOriginalPrice() * shoppingCartItemBean.getBuyNum();
                    totalPrice = Arith.sub(totalPrice, minusMoney) + giftPrice;
                } else {

                    totalPrice = Arith.sub(totalPrice, minusMoney);
                }

                selectedNumber = Arith.sub(selectedNumber, buyNum);

            }
            //3折扣；6天天特价
            if (specId == 6) {
                double benefitMoney = dataBean.getBenefitMoney();
                totalPrice = totalPrice + benefitMoney;
                totalReduceMoney = totalReduceMoney - benefitMoney;
            } else if (specId == 3) {
                double benefitMoney2 = dataBean.getBenefitMoney();
                totalPrice = totalPrice + benefitMoney2;
                totalReduceMoney = totalReduceMoney - benefitMoney2;
            } else if (specId == 1) {
                double benefitMoney3 = dataBean.getBenefitMoney();
//                tv_reduced_number.setText(StringUtil.strToDouble_new(String.valueOf(0)));
                totalPrice = totalPrice + benefitMoney3;
                totalReduceMoney = totalReduceMoney - benefitMoney3;
            }

            if (checkList.size() < selected_all_count) {
                cb_checkall.setChecked(false);
            }
        }

        check_group_count = 0;
        for (int i = 0; i < mapdata.size(); i++) {

            if (mapdata.get(i).isGroupCheck) {
                check_group_count += mapdata.get(i).getItems().size();
            }

        }

        tv_kinds_number.setText(String.valueOf(check_group_count));
        tv_piece.setText(String.valueOf(selectedNumber));
        tv_reduced_number.setText(StringUtil.strToDouble_new(String.valueOf(totalReduceMoney)));
        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(totalPrice)));
    }

    public double getFullPresentTotalMoney() {
        return fullpresent_total_price;
    }

    @Override
    public double getFullCutTotalMoney() {
        return fullcut_total_price;
    }

    @Override
    public void setIsCanClick(boolean isCanClick) {

        group_header_right_part.setEnabled(isCanClick);
    }

    @Override
    public void setTotalAndReduceMoney(double reduceMoney) {
        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(totalPrice - reduceMoney)));
        tv_reduced_number.setText(StringUtil.strToDouble_new(String.valueOf(reduceMoney)));
    }

    @Override
    public void refreshShoppingCartData() {
        myOrderPresenter.doHttp();
//        refreshAdapter();
    }


    /**
     * 设置为购物车商品没有被选中
     */
    private void setNoChecked() {

        tv_total_price.setText(String.valueOf(0.00));
        tv_reduced_number.setText("0.00");
        totalPrice = 0.00;
        selectedNumber = 0;
        tv_kinds_number.setText(String.valueOf(0));
        tv_piece.setText(String.valueOf(0));
        fullcut_total_price = 0.0;
        fullpresent_total_price = 0.0;
        checkList.clear();
        totalReduceMoney = 0.0;
        isGroupChecked.clear();
    }

    /**
     * 设置首页fragment对应的标题栏
     */
    private void setMyOrderTitle(View view) {
        RelativeLayout head_back = (RelativeLayout) view.findViewById(R.id.head_back);
        head_back.setVisibility(View.GONE);
        TextView tv_title = (TextView) view.findViewById(R.id.tv_title);
        tv_title.setText("购物车");
        tv_subtitle = (TextView) view.findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("编辑");

    }


    @Override
    public void refreshAdapter() {
        myOrderAdapter.notifyDataSetChanged();
    }

    @Override
    public void totalPriceRemoveOne(int section_pos, int position) {
        ShoppingCartBean.DataBean dataBean = mapdata.get(section_pos);
        double onceChangeMoney = Double.parseDouble(String.valueOf(dataBean.getItems().get(position).getOriginalPrice()));

        int saleIncreaseCount = dataBean.getItems().get(position).getSaleIncreaseCount();
        if (saleIncreaseCount <= 1) {
            selectedNumber = Arith.sub(selectedNumber,
                    1);
            totalPrice = Arith.sub(totalPrice,onceChangeMoney);
            if (mapdata.get(section_pos).getSpecialId() == 2) {
                fullpresent_total_price = Arith.sub(dataBean.getOriginalMoney(),
                        onceChangeMoney);
            } else if (mapdata.get(section_pos).getSpecialId() == 1) {
                fullcut_total_price = Arith.sub(dataBean.getOriginalMoney(),
                        onceChangeMoney);
            }
        }else {
            selectedNumber = Arith.sub(selectedNumber,
                    saleIncreaseCount);
            totalPrice = Arith.sub(totalPrice,onceChangeMoney*saleIncreaseCount);
            if (mapdata.get(section_pos).getSpecialId() == 2) {
                fullpresent_total_price = Arith.sub(dataBean.getOriginalMoney(),
                        onceChangeMoney*saleIncreaseCount);
            } else if (mapdata.get(section_pos).getSpecialId() == 1) {
                fullcut_total_price = Arith.sub(dataBean.getOriginalMoney(),
                        onceChangeMoney*saleIncreaseCount);
            }
        }
        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(totalPrice)));
        tv_piece.setText(String.valueOf(selectedNumber));

//        System.out.println("满赠总价：" + fullpresent_total_price + "满减总价：" + fullcut_total_price);
    }

    @Override
    public void totalPriceAddOne(int section_pos, int position) {

        ShoppingCartBean.DataBean dataBean = mapdata.get(section_pos);
        double onceReduceMoney = Double.parseDouble(String.valueOf(dataBean.getItems().get(position).getOriginalPrice()));


        int saleIncreaseCount = dataBean.getItems().get(position).getSaleIncreaseCount();
        if (saleIncreaseCount <= 1) {
            selectedNumber = Arith.add(selectedNumber,
                    1);
            totalPrice = Arith.add(totalPrice, onceReduceMoney);
            if (mapdata.get(section_pos).getSpecialId() == 2) {
                fullpresent_total_price = Arith.add(dataBean.getOriginalMoney(),
                        onceReduceMoney);
            } else if (mapdata.get(section_pos).getSpecialId() == 1) {
                fullcut_total_price = Arith.add(dataBean.getOriginalMoney(),
                        onceReduceMoney);
            }
        }else {
            selectedNumber = Arith.add(selectedNumber,
                    saleIncreaseCount);
            totalPrice = Arith.add(totalPrice, onceReduceMoney*saleIncreaseCount);
        }
        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(totalPrice)));
        tv_piece.setText(String.valueOf(selectedNumber));

//        System.out.println("满赠总价：" + fullpresent_total_price + "满减总价：" + fullcut_total_price);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.bt_goPay_or_delete:
                if ("批量删除".equals(bt_goPay_or_delete.getText().toString().trim())) {
                    if (checkList.size() == 0) {
                        DialogUtil.showPointDialog(getActivity(), "请选择要删除的商品");
                    } else {
                        showDialog();
                    }
                } else if ("去结算".equals(bt_goPay_or_delete.getText().toString().trim())) {
//                    myOrderPresenter.doHttp();
                    if (checkList.size() == 0) {
                        DialogUtil.showPointDialog(getActivity(), "请选择要购买的商品");
                    } else {
                        sb_changed.delete(0, sb_changed.length());
//                        SpecialId|ActivityId:SpecialId|ActivityId:SpecialId|ActivityId
                        for (int i = 0; i < mapdata.size(); i++) {
                            if (mapdata.get(i).isGroupCheck) {
                                sb_changed.append(mapdata.get(i).getSpecialId() + "|" + mapdata.get(i).getActivityId() + ":");
                            }

                        }
                        String str = sb_changed.toString();
                        ids_str = str.substring(0, str.length() - 1);
                        Intent intent = new Intent(getActivity(), OrderConfirmActivity.class);
                        intent.putExtra("ids_selected", ids_str);
                        startActivityByIntent(intent);
                    }
                }
                break;
            case R.id.tv_subtitle://点击编辑完成
                setNoChecked();
                if (flag) {
                    setTextViewIsShow(false);
                    tv_subtitle.setText("完成");
                    gong.setVisibility(View.GONE);
                    fuhao.setVisibility(View.GONE);
                    tv_total_price.setVisibility(View.GONE);
                    ll_reduced.setVisibility(View.GONE);
                    ll_kinds_and_piece.setVisibility(View.GONE);
                    bt_goPay_or_delete.setText("批量删除");
                    flag = false;

                } else {
                    setTextViewIsShow(true);
                    tv_subtitle.setText("编辑");
                    gong.setVisibility(View.VISIBLE);
                    fuhao.setVisibility(View.VISIBLE);
                    tv_total_price.setVisibility(View.VISIBLE);
                    ll_reduced.setVisibility(View.VISIBLE);
                    ll_kinds_and_piece.setVisibility(View.VISIBLE);
                    bt_goPay_or_delete.setText("去结算");
                    flag = true;
                }
                break;

            case R.id.group_header_right_part://跳转到专场页面

                int sectionForPosition = myOrderAdapter.getSectionForPosition(list_Shopping.getFirstVisiblePosition());

                ShoppingCartBean.DataBean dataBean = mapdata.get(sectionForPosition);
                Intent intent = new Intent(getActivity(), SpecialSaleActivity.class);
                intent.putExtra("mUrl", "api/promotion/activity");
                intent.putExtra("paramName0", "SpecialId");
                intent.putExtra("paramValue0", String.valueOf(dataBean.getSpecialId()));
                intent.putExtra("paramName1", "ActivityId");
                intent.putExtra("paramValue1", String.valueOf(dataBean.getActivityId()));
                intent.putExtra("cart", "isRefresh");
                ((BaseCommonActivity) getActivity()).startActivityByIntent(intent);
                break;
            case R.id.group_header_left_part://点击页面第一个条目的左侧部分

                int section_pos = myOrderAdapter.getSectionForPosition(list_Shopping.getFirstVisiblePosition());
                groupChecked(section_pos);

                break;

            case R.id.cb_checkall://全选
                checkAllGoods();
                break;
            case R.id.btn_goto_special://无数据的时候跳转到常购清单
                Intent intent2 = new Intent(getActivity(), RegularBuyListActivity.class);
                intent2.putExtra("tag", "cartNoData");
                startActivityByIntent(intent2);
                break;
            case R.id.tv_reload://有网络后重新加载
                setNoChecked();
                myOrderAdapter = null;
                showLoading();
                myOrderPresenter = new MyShoppingCartPresenter(this, getActivity());
                httpCartData();
                break;

        }
    }


    /**
     * 购物车中全选的逻辑
     */
    private void checkAllGoods() {

        selected_all_count = item_total_count - item_present_count;
        if (cb_checkall.isChecked()) {
            checkList.clear();
            setNoChecked();
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = true;
                checkList.add(mDatas.get(i));
            }

            //每个分组上checkbox的选中状态
            isGroupChecked.clear();

            for (int i = 0; i < mapdata.size(); i++) {
                isGroupChecked.put(i, true);
                mapdata.get(i).isGroupCheck = true;
                ShoppingCartBean.DataBean dataBean = mapdata.get(i);

                for (int j = 0; j < mapdata.get(i).getItems().size(); j++) {
                    ShoppingCartBean.DataBean.ItemsBean itemsBean = mapdata.get(i).getItems().get(j);
                    itemsBean.isCheck = true;
                }

            }

            for (int i = 0; i < mapdata.size(); i++) {
                int specialId = mapdata.get(i).getSpecialId();
                for (int j = 0; j < mapdata.get(i).getItems().size(); j++) {

                    ShoppingCartBean.DataBean.ItemsBean itemsBean = mapdata.get(i).getItems().get(j);
                    if (specialId == 2) {
                        fullpresent_total_price = Arith.add(fullpresent_total_price,
                                Arith.mul(Double.parseDouble(String.valueOf(itemsBean.getOriginalPrice())),
                                        Double.parseDouble(String.valueOf(itemsBean.getBuyNum()))));

                    } else if (specialId == 1) {

                        fullcut_total_price = Arith.add(fullcut_total_price,
                                Arith.mul(Double.parseDouble(String.valueOf(itemsBean.getOriginalPrice())),
                                        Double.parseDouble(String.valueOf(itemsBean.getBuyNum()))));

                    }
                    Double mul = Arith.mul(Double.parseDouble(String.valueOf(itemsBean.getOriginalPrice())),
                            Double.parseDouble(String.valueOf(itemsBean.getBuyNum())));
                    if (itemsBean.isIsSend()) {//如果有赠品
                        double giftPrice = itemsBean.getOriginalPrice() * itemsBean.getBuyNum();
                        totalPrice = Arith.add(totalPrice, mul) - giftPrice;
                    } else {
                        totalPrice = Arith.add(totalPrice, mul);
                    }

                    selectedNumber = Arith.add(selectedNumber,
                            itemsBean.getBuyNum());

                }

                // 3折扣；6天天特价
                if (specialId == 6) {
                    double benefitMoney = mapdata.get(i).getBenefitMoney();
                    totalPrice = totalPrice - benefitMoney;
                    totalReduceMoney = totalReduceMoney + benefitMoney;
                } else if (specialId == 3) {
                    double benefitMoney2 = mapdata.get(i).getBenefitMoney();
                    totalPrice = totalPrice - benefitMoney2;
                    totalReduceMoney = totalReduceMoney + benefitMoney2;
                } else if (specialId == 1) {
                    double benefitMoney3 = mapdata.get(i).getBenefitMoney();
                    if (fullcut_total_price >= benefitMoney3) {
//                        tv_reduced_number.setText(StringUtil.strToDouble_new(String.valueOf(benefitMoney3)));
                        totalPrice = totalPrice - benefitMoney3;
                        totalReduceMoney = totalReduceMoney + benefitMoney3;
                    }
//                    else {
//                        tv_reduced_number.setText(StringUtil.strToDouble_new(String.valueOf(0)));
//                    }
                }

            }

            tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(totalPrice)));
            tv_kinds_number.setText(String.valueOf(checkList.size()));
            tv_piece.setText(String.valueOf(selectedNumber));
            tv_reduced_number.setText(StringUtil.strToDouble_new(String.valueOf(totalReduceMoney)));
        } else {
            checkList.clear();
            isGroupChecked.clear();

            //对应的为每个组的那个checkbox的选中状态
            for (int i = 0; i < mapdata.size(); i++) {
                isGroupChecked.put(i, false);
                mapdata.get(i).isGroupCheck = false;
                for (int j = 0; j < mapdata.get(i).getItems().size(); j++) {
                    ShoppingCartBean.DataBean.ItemsBean itemsBean = mapdata.get(i).getItems().get(j);
                    itemsBean.isCheck = false;
                }
            }

//            //对应的每个分组里面商品的选中状态
            for (int i = 0; i < mDatas.size(); i++) {
                mDatas.get(i).isCheck = false;
            }
            setNoChecked();
        }

        setIsGroupChecked(isGroupChecked);
        myOrderAdapter.notifyDataSetChanged();
    }

    /**
     * 进货单编辑的时候控件的显示与隐藏
     */
    private void setTextViewIsShow(boolean flag) {

        isShow.clear();
        checkList.clear();
        totalPrice = 0.00;
        fullpresent_total_price = 0.0;
        fullcut_total_price = 0.0;
        selectedNumber = 0;
        tv_kinds_number.setText(String.valueOf(0));
        tv_piece.setText(String.valueOf(0));
        tv_total_price.setText(String.valueOf(0.00));
        cb_checkall.setChecked(false);
        if (flag) {

            for (int i = 0; i < mDatas.size(); i++) {
                isShow.put(i, true);
                mDatas.get(i).isCheck = false;
            }

            for (int i = 0; i < mapdata.size(); i++) {
                isGroupChecked.put(i, false);
                mapdata.get(i).isGroupCheck = false;

                for (int j = 0; j < mapdata.get(i).getItems().size(); j++) {
                    ShoppingCartBean.DataBean.ItemsBean itemsBean = mapdata.get(i).getItems().get(j);
                    itemsBean.isCheck = false;
                }
            }

        } else {
            for (int i = 0; i < mDatas.size(); i++) {
                isShow.put(i, false);
                mDatas.get(i).isCheck = false;
            }
            for (int i = 0; i < mapdata.size(); i++) {
                isGroupChecked.put(i, false);
                mapdata.get(i).isGroupCheck = false;

                for (int j = 0; j < mapdata.get(i).getItems().size(); j++) {
                    ShoppingCartBean.DataBean.ItemsBean itemsBean = mapdata.get(i).getItems().get(j);
                    itemsBean.isCheck = false;
                }
            }
        }
        MyShoppingCartAdapter.setIsShow(isShow);
        setIsGroupChecked(isGroupChecked);
        myOrderAdapter.notifyDataSetChanged();

    }

    /**
     * 批量删除的时候弹出的对话框
     */
    private void showDialog() {

        new MyAlertDialog(getActivity()).builder()
                .setTitle("您确定要删除所选商品吗？")
                .setPositiveButton("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        sb_changed.delete(0, sb_changed.length());
//                        SpecialId|ActivityId:SpecialId|ActivityId:SpecialId|ActivityId
                        for (int i = 0; i < mapdata.size(); i++) {
                            if (mapdata.get(i).isGroupCheck) {
                                sb_changed.append(mapdata.get(i).getSpecialId() + "|" + mapdata.get(i).getActivityId() + ":");
                            }

                        }
                        String str = sb_changed.toString();
                        ids_str = str.substring(0, str.length() - 1);
                        deleteAllByHttp();
//                        myOrderAdapter.notifyDataSetChanged();
                        checkList.clear();
                        totalPrice = 0;
                        tv_total_price.setText(StringUtil.strToDouble_new(String.valueOf(totalPrice)));

                        if (cb_checkall.isChecked()) {
                            cb_checkall.setChecked(false);
                            tv_total_price.setText(String.valueOf(0));
                        }
                        if (mDatas.size() == 0) {
                            cb_checkall.setClickable(false);
                        }

//                        ((BaseCommonActivity) (getActivity())).showTips(getActivity(), R.mipmap.toast_right, "删除成功");
                    }
                }).setNegativeButton("留在购物车", new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        }).show();

    }

    /**
     * 批量删除
     */
    private void deleteAllByHttp() {

        String url = AppConstant.BASEURL2 + "api/cart/batch";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        String ids_newstr = StringUtil.getUrlEncodeResult(ids_str);
        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Ids", ids_newstr);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Ids", ids_str)
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
                        SPUtils.putString(getActivity(), "TotalNumber", String.valueOf(0));
                    }
                    if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getActivity().getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                refreshShoppingCartData();
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("delete_all_shoppingcar_data", e.toString());
            }
        });

    }


    @Override
    public void showMessageByToast(String msg) {
        ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
    }

    @Override
    public void setData(List<ShoppingCartBean.DataBean> data) {

        mapdata.clear();
        mDatas.clear();
        for (int i = 0; i < data.size(); i++) {
            ShoppingCartBean.DataBean dataBean = data.get(i);
            mapdata.put(i, data.get(i));
            for (int j = 0; j < dataBean.getItems().size(); j++) {
                mDatas.add(dataBean.getItems().get(j));
            }
        }

        if (myOrderAdapter == null) {
            myOrderAdapter = new MyShoppingCartAdapter(mapdata, getActivity());
            myOrderAdapter.setCallBack(this);
            list_Shopping.setAdapter(myOrderAdapter);
        } else {
            myOrderAdapter.setNewData(mapdata, getActivity());
            myOrderAdapter.notifyDataSetChanged();
        }
        
        rl_tourist_login_shoppingcar.setVisibility(View.GONE);
        if (mapdata.size() == 0) {
            rl_cart_no_data.setVisibility(View.VISIBLE);
            rl_bottom_part.setVisibility(View.GONE);
            list_Shopping.setVisibility(View.GONE);
            tv_bg1.setVisibility(View.GONE);
            tv_subtitle.setVisibility(View.GONE);
            SPUtils.putString(getActivity(), "TotalNumber", String.valueOf(0));
        } else {
            rl_cart_no_data.setVisibility(View.GONE);
            rl_bottom_part.setVisibility(View.VISIBLE);
            list_Shopping.setVisibility(View.VISIBLE);
            tv_bg1.setVisibility(View.VISIBLE);
            tv_subtitle.setVisibility(View.VISIBLE);
        }
//        }
        tv_subtitle.setText("编辑");
        gong.setVisibility(View.VISIBLE);
        fuhao.setVisibility(View.VISIBLE);
        tv_total_price.setVisibility(View.VISIBLE);
        ll_kinds_and_piece.setVisibility(View.VISIBLE);
        ll_reduced.setVisibility(View.VISIBLE);
        bt_goPay_or_delete.setText("去结算");
        flag = true;

//        checkList.clear();
//        for (int i = 0; i < mDatas.size(); i++) {
//            mDatas.get(i).isCheck = false;
//        }
//        isGroupChecked.clear();
//        for (int i = 0; i < mapdata.size(); i++) {
//            isGroupChecked.put(i, false);
//        }
//        setNoChecked();
//        cb_checkall.setChecked(false);
        cb_checkall.setChecked(true);
        checkAllGoods();
        item_total_count = mDatas.size();

        closeLoading();

    }

    @Override
    public void closeLoading() {
        dismissLoading();
    }

    @Override
    public void showDialogLoginAgain() {
        DialogUtil.showDialog(getActivity(), getActivity().getResources().getString(R.string.need_login_again));
    }

    @Override
    public void haveNetShow() {

        rl_no_net.setVisibility(View.GONE);
        rl_bottom_part.setVisibility(View.VISIBLE);
        rl_shoppingcar_data.setVisibility(View.VISIBLE);
        tv_bg1.setVisibility(View.VISIBLE);
        tv_subtitle.setVisibility(View.VISIBLE);
    }

    @Override
    public void noNetShow() {

        rl_no_net.setVisibility(View.VISIBLE);
        rl_shoppingcar_data.setVisibility(View.GONE);
        rl_bottom_part.setVisibility(View.GONE);
        tv_bg1.setVisibility(View.GONE);
        tv_subtitle.setVisibility(View.GONE);
    }

    @Override
    public void setPresenter(MyOrderContract.Presenter presenter) {

    }

}
