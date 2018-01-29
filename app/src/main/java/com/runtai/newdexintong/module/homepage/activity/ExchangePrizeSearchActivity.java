package com.runtai.newdexintong.module.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.SpannableString;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.home.view.XCFlowLayout;
import com.runtai.newdexintong.module.personalcenter.util.KeyboardUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 我要兑奖页面对应的搜索界面
 */
public class ExchangePrizeSearchActivity extends BaseActivity {

    private RelativeLayout head_back;
    private TextView tv_subtitle;
    private EditText et_search2;
    private ImageView iv_delete_record;
    private XCFlowLayout fl_history_data;
    private RelativeLayout rl_record_title;
    private List<String> history_data;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exchange_prize_search);

        initView();
        registerListener();
        initData();
    }

    private void initView() {

        history_data = new ArrayList<>();
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        setActivityTitle();

        iv_delete_record = (ImageView) findViewById(R.id.iv_delete_record);
        fl_history_data = (XCFlowLayout) findViewById(R.id.fl_history_data);
        rl_record_title = (RelativeLayout) findViewById(R.id.rl_record_title);
    }

    /**
     * 设置界面标题栏
     */
    private void setActivityTitle() {

        ((TextView) findViewById(R.id.tv_title)).setVisibility(View.GONE);
        RelativeLayout rl_search_result = (RelativeLayout) findViewById(R.id.rl_search_result);
        rl_search_result.setVisibility(View.VISIBLE);
        tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("搜索");

        et_search2 = (EditText) findViewById(R.id.et_search2);
        et_search2.setHint(new SpannableString("输入商品名称搜索"));
    }


    private void registerListener() {
        head_back.setOnClickListener(this);
        tv_subtitle.setOnClickListener(this);
        iv_delete_record.setOnClickListener(this);

        et_search2.setFocusable(true);
        et_search2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    /*隐藏软键盘*/
                    KeyboardUtil.hintKeyBoard(ExchangePrizeSearchActivity.this, et_search2);
                    search();
                    return true;
                }
                return false;
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (history_data.size() > 0) {
            rl_record_title.setVisibility(View.VISIBLE);
            fl_history_data.setVisibility(View.VISIBLE);
        }

    }

    /**
     * 联网请求历史记录的数据
     */
    private void initData() {

        loadHistoryData(history_data);
        if (history_data.size() == 0) {
            rl_record_title.setVisibility(View.GONE);
        } else {
            rl_record_title.setVisibility(View.VISIBLE);
            initHistorySearchDataView(fl_history_data, history_data);

        }

    }

    /**
     * 初始化历史搜索记录数据对应的view
     *
     * @param fl
     * @param histroySearchData
     */
    private void initHistorySearchDataView(XCFlowLayout fl,
                                           final List<String> histroySearchData) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        lp.leftMargin = 10;
        lp.rightMargin = 10;
        lp.topMargin = 10;
        lp.bottomMargin = 10;
        for (int i = 0; i < histroySearchData.size(); i++) {
            final TextView view = new TextView(this);
            view.setText(histroySearchData.get(i));
            view.setTextSize(14);
            view.setTag(i);
            view.setTextColor(getResources().getColor(R.color.text_gray));
            view.setBackgroundDrawable(getResources().getDrawable(
                    R.drawable.shape_button_black_edge_bg));
            view.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(ExchangePrizeSearchActivity.this,
                            ExchangePrizeSearchResultActivity.class);
                    intent.putExtra("goodsname",
                            histroySearchData.get((Integer) view.getTag()));
                    addKeyWordToHistory(histroySearchData.get((Integer) view.getTag()));
                    et_search2.setText("");
                    startActivity(intent);
                }
            });
            fl.addView(view, lp);
        }
    }

    /**
     * 加载历史记录的数据
     *
     * @param list
     */
    public void loadHistoryData(List<String> list) {
        list.clear();
        int size = SPUtils.getInt(this, "historyDataSize", 0);

        for (int i = 0; i < size; i++) {
            list.add(SPUtils.getString(this, "historyData" + i, null));
        }
    }

    private void addKeyWordToHistory(String KeyWord) {

        if (history_data.size() != 9) {
            if (history_data.contains(KeyWord)) {
                history_data.remove(KeyWord);
            } else {

            }
            history_data.add(0, KeyWord);
        } else {
            if (history_data.contains(KeyWord)) {
            } else {
                history_data.add(0, KeyWord);
                history_data.remove(9);
            }
        }
        fl_history_data.removeAllViews();
        initHistorySearchDataView(fl_history_data, history_data);
    }

    /**
     * 保存历史记录的数据
     *
     * @param list
     */
    public void saveHistorySearchData(List<String> list) {

        SPUtils.putInt(this, "historyDataSize", list.size());

        for (int i = 0; i < list.size(); i++) {
            SPUtils.putString(this, "historyData" + i, "");
            SPUtils.putString(this, "historyData" + i, list.get(i));
        }
    }

    /**
     * 搜索兑奖记录
     */
    private void search() {
        
        String search_content = et_search2.getText().toString().trim();
        if (TextUtils.isEmpty(search_content)) {
            ToastUtil.showToast(this, "请输入要搜索的内容", Toast.LENGTH_SHORT);
            return;
        }

        Intent intent = new Intent(this, ExchangePrizeSearchResultActivity.class);
        intent.putExtra("goodsname", search_content);
        et_search2.setText("");
        startActivityByIntent(intent);
        rl_record_title.setVisibility(View.VISIBLE);
        addKeyWordToHistory(search_content);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle://搜索按钮
                search();
                break;

            case R.id.iv_delete_record://删除历史纪录
                new MyAlertDialog(this).builder()
                        .setTitle("您确定要清除搜索记录吗？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                history_data.clear();
                                saveHistorySearchData(history_data);
                                rl_record_title.setVisibility(View.GONE);
                                fl_history_data.setVisibility(View.GONE);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();

                break;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveHistorySearchData(history_data);
    }
    
    
}
