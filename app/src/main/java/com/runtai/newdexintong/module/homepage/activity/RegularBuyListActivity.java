package com.runtai.newdexintong.module.homepage.activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
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
import com.runtai.newdexintong.module.fenlei.view.FooterLayout;
import com.runtai.newdexintong.module.fenlei.view.HeaderLayout;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.homepage.adapter.RegularBuyListAdapter;
import com.runtai.newdexintong.module.homepage.bean.RegularBuyBean;
import com.runtai.newdexintong.module.homepage.utils.GetCartTotalNumUtil;
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
 * 常购清单对应的界面
 */
public class RegularBuyListActivity extends BaseActivity 
        implements RegularBuyListAdapter.IupdateCartTotalNumber {

    private RelativeLayout head_back;
    private TextView tv_subtitle;
    //    private List<RegularBuyBean> mDatas;
    private List<RegularBuyBean.DataBean.ListBean> checkedGoodsData;
    private CheckBox checkbox_select;
    private ImageView iv_shoppingcar;
    private TextView tv_goods_amount;
    private boolean isClick = true;
    private RegularBuyListAdapter adapter;
    public HashMap<Integer, Boolean> isShow;
    private PullToRefreshListView lv_regular_buy;
    private RelativeLayout rl_check_all;
    private CheckBox cb_checkall_goods;
    private Button btn_delete_all;
    private List<RegularBuyBean.DataBean.ListBean> mDatas;
    /**
     * 总条目数量
     */
    private int totalCount;
    private int pages;
    private int currentPage = 2;
    /**
     * 每页显示的条目数量
     */
    private int pageItemCount = 20;
    private boolean isFirst = true;
    private RelativeLayout rl_no_data_show;
    private RelativeLayout rl_no_net;
    private TextView tv_reload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_regular_buy_list);
        GetCartTotalNumUtil.getShoppingCartNumber(this);
        initView();
        showLoading();
        httpData("1");
        registerListener();
    }

    private void initView() {
        isShow = new HashMap<>();
        checkedGoodsData = new ArrayList<>();
        mDatas = new ArrayList<>();
        setActivityTitle();
        lv_regular_buy = (PullToRefreshListView) findViewById(R.id.lv_regular_buy);
        lv_regular_buy.setHeaderLayout(new HeaderLayout(this));
        lv_regular_buy.setFooterLayout(new FooterLayout(this));
        iv_shoppingcar = (ImageView) findViewById(R.id.iv_shoppingcar);
        tv_goods_amount = (TextView) findViewById(R.id.tv_goods_amount);
        String totalNumber = SPUtils.getString(this, "TotalNumber", "");
        if (!"".equals(totalNumber) && totalNumber.length() > 0) {
            tv_goods_amount.setText(totalNumber);
        }
        rl_check_all = (RelativeLayout) findViewById(R.id.rl_check_all);
        cb_checkall_goods = (CheckBox) findViewById(R.id.cb_checkall_goods);
        btn_delete_all = (Button) findViewById(R.id.btn_delete_all);
        rl_no_data_show = (RelativeLayout) findViewById(R.id.rl_no_data_show);

        tv_reload = (TextView)findViewById(R.id.tv_reload);
        rl_no_net = (RelativeLayout) findViewById(R.id.rl_no_net);
    }

    /**
     * 联网请求数据
     */
    private void httpData(String page) {


        if (!NetUtil.isNetworkAvailable(this)) {
            lv_regular_buy.setVisibility(View.GONE);
            tv_goods_amount.setVisibility(View.GONE);
            iv_shoppingcar.setVisibility(View.GONE);
            rl_no_net.setVisibility(View.VISIBLE);
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            dismissLoading();
            return;
        }else {
            lv_regular_buy.setVisibility(View.VISIBLE);
            tv_goods_amount.setVisibility(View.VISIBLE);
            iv_shoppingcar.setVisibility(View.VISIBLE);
            rl_no_net.setVisibility(View.GONE);
        }
        
        String url = AppConstant.BASEURL2 + "api/main/buy";

        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(this, "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
//        map.put("Id", "1");
        map.put("Page", page);
        map.put("Size", String.valueOf(pageItemCount));
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);
        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
//                .addParams("Id", "1")
                .addParams("Page", page)
                .addParams("Size", String.valueOf(pageItemCount))
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
                        RegularBuyBean regularBuyBean = gson.fromJson(strJson, RegularBuyBean.class);

                        RegularBuyBean.DataBean data = regularBuyBean.getData();
                        totalCount = data.getTotal();
                        pages = data.getPages();
                        if (mDatas == null) {
                            mDatas = new ArrayList<RegularBuyBean.DataBean.ListBean>();
                        }
                        mDatas.addAll(data.getList());
                        if (mDatas.size() == 0) {
                            rl_no_data_show.setVisibility(View.VISIBLE);
                            lv_regular_buy.setVisibility(View.GONE);
                            iv_shoppingcar.setVisibility(View.GONE);
                            tv_goods_amount.setVisibility(View.GONE);
                        } else {
                            rl_no_data_show.setVisibility(View.GONE);
                            lv_regular_buy.setVisibility(View.VISIBLE);
                            iv_shoppingcar.setVisibility(View.VISIBLE);
                            tv_goods_amount.setVisibility(View.VISIBLE);
                        }
                        lv_regular_buy.onRefreshComplete();//停止刷新
                        adapter = new RegularBuyListAdapter(RegularBuyListActivity.this, mDatas);

                        if (isFirst) {
                            lv_regular_buy.setAdapter(adapter);
                            isFirst = false;
                        } else {
                            adapter.notifyDataSetChanged();
                        }
                        adapter.setIupdateCartTotalNumber(RegularBuyListActivity.this);
                    } else if (code == 403) {
                        DialogUtil.showDialog(RegularBuyListActivity.this, getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(RegularBuyListActivity.this, msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismissLoading();
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("regularbuy", e.toString());
                dismissLoading();
            }
        });
    }


    private void registerListener() {

        tv_reload.setOnClickListener(this);
        head_back.setOnClickListener(this);
        tv_subtitle.setOnClickListener(this);
        cb_checkall_goods.setOnClickListener(this);
        btn_delete_all.setOnClickListener(this);
        iv_shoppingcar.setOnClickListener(this);
        //listview条目点击事件的添加
        lv_regular_buy.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                RegularBuyListAdapter.ViewHolder holder = (RegularBuyListAdapter.ViewHolder) view.getTag();
                //在每次获取点击的item时将对应的checkBox状态改变，同时修改map的值
//                holder.checkbox_select.toggle();
//                position = position - 1;//添加了刷新头，所以对position进行修正
//                if (!mDatas.get(position).isCheck) {
//                    mDatas.get(position).isCheck = true;
//                    checkedGoodsData.add(mDatas.get(position));
//                } else {
//                    mDatas.get(position).isCheck = false;
//                    checkedGoodsData.remove(mDatas.get(position));
//                }
//
//                //将mDatas.size() 联网请求数据后这里换成请求到数据集合的大小
//                if (checkedGoodsData.size() == totalCount) {
//                    cb_checkall_goods.setChecked(true);
//                } else {
//                    cb_checkall_goods.setChecked(false);
//                }
//                adapter.notifyDataSetChanged();

            }

        });

        //下拉刷新和上拉加载设置
        lv_regular_buy.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener2<ListView>() {
            @Override
            public void onPullDownToRefresh(PullToRefreshBase<ListView> refreshView) {
                mDatas.clear();
                httpData("1");
                currentPage = 2;
            }

            @Override
            public void onPullUpToRefresh(PullToRefreshBase<ListView> refreshView) {
                if (pages > 0 && pages >= currentPage) {
                    httpData(String.valueOf(currentPage));
                    currentPage++;
                } else {
                    new MyTask().execute();
                    ToastUtil.showToast(RegularBuyListActivity.this, "没有更多数据了", Toast.LENGTH_SHORT);
                }
            }

        });

    }


    /**
     * 设置界面标题
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setText("常购清单");
        tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.GONE);
        tv_subtitle.setText("编辑");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle://点击编辑
                clickEditButton();
                break;

            case R.id.cb_checkall_goods://全选按钮

                checkedGoodsData.clear();
                if (cb_checkall_goods.isChecked()) {

                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).isCheck = true;
                        checkedGoodsData.add(mDatas.get(i));
                    }
                } else {
                    for (int i = 0; i < mDatas.size(); i++) {
                        mDatas.get(i).isCheck = false;
                    }
                }

                adapter.notifyDataSetChanged();

                break;

            case R.id.btn_delete_all://批量删除
                if (checkedGoodsData.size() == 0) {
                    DialogUtil.showPointDialog(this, "请选择要删除的商品");
                } else {
                    showDeleteAllDialog();
                }
                break;

            case R.id.iv_shoppingcar://点击购物车图标
               
                Intent intent = new Intent(this, MainActivity.class);
                intent.putExtra("tag", "SearchResultAcivity");
                //购物车无数据的时候点击跳转过来
                if ("cartNoData".equals(getIntent().getStringExtra("tag"))) {
                    SPUtils.putBoolean(this,"fromRegularbuy",true);
                }
                startActivityByIntent(intent);
                finish();
                break;
            case R.id.tv_reload:
                httpData("1");
                break;
        }
    }

    /**
     * 点击编辑或者完成按钮后的逻辑
     */
    private void clickEditButton() {
        if (mDatas.size() > 0) {
            if (isClick) {

                setCheckBoxIsShow(true);
                rl_no_data_show.setVisibility(View.GONE);
                iv_shoppingcar.setVisibility(View.GONE);
                tv_goods_amount.setVisibility(View.GONE);
                tv_subtitle.setText("完成");
                isClick = false;
                rl_check_all.setVisibility(View.VISIBLE);

            } else {

                setCheckBoxIsShow(false);
                rl_no_data_show.setVisibility(View.VISIBLE);
                iv_shoppingcar.setVisibility(View.VISIBLE);
                tv_goods_amount.setVisibility(View.VISIBLE);
                tv_subtitle.setText("编辑");
                isClick = true;
                rl_check_all.setVisibility(View.GONE);
            }
        }
    }

    private void showDeleteAllDialog() {
        new MyAlertDialog(this).builder()
                .setTitle("您确定要删除所有商品吗？")
                .setPositiveButton("删除", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        //此处联网操作删除所有商品
                        showTips(RegularBuyListActivity.this, R.mipmap.toast_right, "删除成功");
                    }
                }).setNegativeButton("留在清单中", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }

    /**
     * 设置checkbox的显示与隐藏
     *
     * @param flag
     */
    private void setCheckBoxIsShow(boolean flag) {

        if (flag) {
            checkedGoodsData.clear();
            for (int i = 0; i < mDatas.size(); i++) {
                isShow.put(i, true);
                mDatas.get(i).isCheck = false;
            }

        } else {
            for (int i = 0; i < mDatas.size(); i++) {
                isShow.put(i, false);
            }
        }
        RegularBuyListAdapter.setIsShow(isShow);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void updateTotalNum(String totalNum) {
        tv_goods_amount.setText(totalNum);
    }


    class MyTask extends AsyncTask<Void, Void, Void> {
        @Override
        protected Void doInBackground(Void... params) {
            return null;
        }

        @Override
        protected void onPostExecute(Void result) {
            super.onPostExecute(result);
            lv_regular_buy.onRefreshComplete();// 刷新UI
        }
    }

}
