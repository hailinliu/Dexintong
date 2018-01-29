package com.runtai.newdexintong.module.personalcenter.activity;

import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.personalcenter.adapter.OtherReplaceGoodsAdapter;
import com.runtai.newdexintong.module.personalcenter.bean.OtherReplaceGoodsBean;

import java.util.HashMap;
import java.util.List;

/**
 * 其他调换搜索结果页面
 */
public class OtherReplaceSearchResultActivity extends BaseActivity {

    private RelativeLayout head_back;
    private String goodsname;
    private EditText et_search2;
    private TextView tv_no_data;
    private PullToRefreshListView pullToRefresh_other_replace;
    private List<OtherReplaceGoodsBean> mData;
    private static HashMap<Integer, Boolean> isChecked;
    private OtherReplaceGoodsAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_replace_search_result);

        goodsname = getIntent().getStringExtra("goodsname");
        initView();
        initData();
        registerListener();
    }

    private void initView() {

        isChecked = new HashMap<>();
        setActivityTitle();
        pullToRefresh_other_replace = (PullToRefreshListView) findViewById(R.id.pullToRefresh_other_replace);
        tv_no_data = (TextView) findViewById(R.id.tv_no_data);
    }

    private void initData() {

//        mData = new ArrayList<>();
//        mData.clear();
//        for (int i = 0; i < 6; i++) {
//            OtherReplaceGoodsBean bean = new OtherReplaceGoodsBean();
//            bean.goodsname = i + "";
//            mData.add(bean);
//        }
//
//        adapter = new OtherReplaceGoodsAdapter(this, mData);
//        pullToRefresh_other_replace.setAdapter(adapter);

    }


    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        head_back = (RelativeLayout) findViewById(R.id.head_back);
        ((TextView) findViewById(R.id.tv_title)).setVisibility(View.GONE);
        RelativeLayout rl_search_result = (RelativeLayout) findViewById(R.id.rl_search_result);
        rl_search_result.setVisibility(View.VISIBLE);
        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.GONE);

        et_search2 = (EditText) findViewById(R.id.et_search2);
        et_search2.setFocusable(false);
        et_search2.setText(goodsname);
    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        et_search2.setOnClickListener(this);

//        pullToRefresh_other_replace.setOnItemClickListener(new AdapterView.OnItemClickListener() {
//            @Override
//            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
////                isChecked.clear();
//                OtherReplaceGoodsBean otherReplaceGoodsBean = mData.get(position - 1);
//                for (int i = 0; i < mData.size(); i++) {
//                    if (position - 1 == i) {
//                        isChecked.put(position - 1, true);
//                    } else {
//                        isChecked.put(i, false);
//                    }
//                }
//
//                OtherReplaceGoodsAdapter.setIsChecked(isChecked);
//                adapter.notifyDataSetChanged();
//
//                showMyDialog();
//
//            }
//
//        });
    }


    /**
     * 弹出提示框
     */
    private void showMyDialog() {
        new MyAlertDialog(this).builder()
                .setTitle("您确定要调换为选中的商品吗？")
                .setPositiveButton("确定", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        onBackPressed();
//                        ((BaseCommonActivity) (context)).showTips(context, R.mipmap.toast_right, "删除成功");
                    }
                }).setNegativeButton("取消", new View.OnClickListener() {
            @Override
            public void onClick(View v) {
            }
        }).show();
    }


    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.et_search2:
                onBackPressed();
                break;
        }
    }
}
