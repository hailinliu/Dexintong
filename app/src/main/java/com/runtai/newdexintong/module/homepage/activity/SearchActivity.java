package com.runtai.newdexintong.module.homepage.activity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.RecognizerListener;
import com.iflytek.cloud.RecognizerResult;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.iflytek.cloud.SpeechRecognizer;
import com.iflytek.cloud.ui.RecognizerDialog;
import com.iflytek.sunflower.FlowerCollector;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.module.fenlei.util.JsonParser;
import com.runtai.newdexintong.module.home.view.XCFlowLayout;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.personalcenter.util.KeyboardUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 搜索界面
 */
public class SearchActivity extends BaseActivity implements View.OnTouchListener{

    private RelativeLayout head_back;
    private TextView tv_subtitle;
    private ImageView iv_delete_record;
    private EditText et_search2;
    private XCFlowLayout fl_history;
    private List<String> history_search_data;
    private RelativeLayout rl_record_title;
    private ImageView iv_search3;
    private static String TAG = SearchActivity.class.getSimpleName();
    // 语音听写对象
    private SpeechRecognizer mIat;
    // 语音听写UI
    private RecognizerDialog mIatDialog;
    // 用HashMap存储听写结果
    private HashMap<String, String> mIatResults = new LinkedHashMap<String, String>();

   
    private Toast mToast;
    //private SharedPreferences mSharedPreferences;
    // 引擎类型
    private String mEngineType = SpeechConstant.TYPE_CLOUD;

    private boolean mTranslateEnable = false;
    private ImageView button;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        initView();
        registerListener();
        initData();
        // 初始化识别无UI识别对象
        // 使用SpeechRecognizer对象，可根据回调消息自定义界面；
        mIat = SpeechRecognizer.createRecognizer(SearchActivity.this, mInitListener);
        
        // 初始化听写Dialog，如果只使用有UI听写功能，无需创建SpeechRecognizer
        // 使用UI听写功能，请根据sdk文件目录下的notice.txt,放置布局文件和图片资源
       mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
     
    }

    private void initView() {

        setActivityTitle();
        history_search_data = new ArrayList<>();
        et_search2 = (EditText) findViewById(R.id.et_search2);
        iv_delete_record = (ImageView) findViewById(R.id.iv_delete_record);
        fl_history = (XCFlowLayout) findViewById(R.id.fl_history);
        rl_record_title = (RelativeLayout) findViewById(R.id.rl_record_title);
        button =  (ImageView) findViewById(R.id.iv_search_result_voice);

    }

    private void registerListener() {

        head_back.setOnClickListener(this);
        iv_delete_record.setOnClickListener(this);
        tv_subtitle.setOnClickListener(this);
        iv_search3.setOnClickListener(this);
        button.setOnTouchListener(this);
        et_search2.setFocusable(true);
        et_search2.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View v, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_ENTER && event.getAction() == KeyEvent.ACTION_DOWN) {
                    /*隐藏软键盘*/
                    KeyboardUtil.hintKeyBoard(SearchActivity.this, et_search2);
                    search();
                    return true;
                }
                return false;
            }
        });

    }

    /**
     * 联网请求历史记录的数据
     */
    private void initData() {

        loadHistoryData(history_search_data);
        if (history_search_data.size() == 0) {
            rl_record_title.setVisibility(View.GONE);
        } else {
            rl_record_title.setVisibility(View.VISIBLE);
            initHistorySearchDataView(fl_history, history_search_data);

        }

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
        tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("搜索");
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (history_search_data.size() > 0) {
            rl_record_title.setVisibility(View.VISIBLE);
            fl_history.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle://搜索
            case R.id.iv_search3:
                search();
                break;
            case R.id.iv_delete_record://清除搜索记录
                new MyAlertDialog(this).builder()
                        .setTitle("您确定要清除搜索记录吗？")
                        .setPositiveButton("确定", new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                history_search_data.clear();
                                saveHistorySearchData(history_search_data);
                                rl_record_title.setVisibility(View.GONE);
                                fl_history.setVisibility(View.GONE);
                            }
                        }).setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                    }
                }).show();
                break;
        }
    }

    /**
     * 搜索商品
     */
    private void search() {
        String search_content = et_search2.getText().toString().trim();
        if (TextUtils.isEmpty(search_content)) {
            ToastUtil.showToast(this, "请输入要搜索的内容", Toast.LENGTH_SHORT);
            return;
        }

        Intent intent = new Intent(this, SearchResultAcivity.class);
        intent.putExtra("goodsname", search_content);
        et_search2.setText("");
        startActivityByIntent(intent);
        rl_record_title.setVisibility(View.VISIBLE);
        addKeyWordToHistory(search_content);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        saveHistorySearchData(history_search_data);
    }


    /**
     * 初始化历史搜索记录数据对应的view
     *
     * @param fl
     * @param histroySearchData
     */
    private void initHistorySearchDataView(XCFlowLayout fl,
                                           final List<String> histroySearchData) {
        ViewGroup.MarginLayoutParams lp = new ViewGroup.MarginLayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
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
                    Intent intent = new Intent(SearchActivity.this,
                            SearchResultAcivity.class);
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

    private void addKeyWordToHistory(String KeyWord) {

        if (history_search_data.size() != 9) {
            if (history_search_data.contains(KeyWord)) {
                history_search_data.remove(KeyWord);
            } else {

            }
            history_search_data.add(0, KeyWord);
        } else {
            if (history_search_data.contains(KeyWord)) {
            } else {
                history_search_data.add(0, KeyWord);
                history_search_data.remove(9);
            }
        }
        fl_history.removeAllViews();
        initHistorySearchDataView(fl_history, history_search_data);
    }

    /**
     * 保存历史记录的数据
     *
     * @param list
     */
    public void saveHistorySearchData(List<String> list) {

        SPUtils.putInt(this, "Status_size", list.size());

        for (int i = 0; i < list.size(); i++) {
            SPUtils.putString(this, "Status_" + i, "");
            SPUtils.putString(this, "Status_" + i, list.get(i));
        }
    }

    /**
     * 加载历史记录的数据
     *
     * @param list
     */
    public void loadHistoryData(List<String> list) {
        list.clear();
        int size = SPUtils.getInt(this, "Status_size", 0);
        for (int i = 0; i < size; i++) {
            list.add(SPUtils.getString(this, "Status_" + i, null));
        }
    }

    int ret = 0; // 函数调用返回值



    /**
     * 初始化监听器。
     */
    private InitListener mInitListener = new InitListener() {

        @Override
        public void onInit(int code) {
            Log.d(TAG, "SpeechRecognizer init() code = " + code);
            if (code != ErrorCode.SUCCESS) {
               // showTip("初始化失败，错误码：" + code);
                showTip("语音使用失败请重试");
            }
        }
    };



    /**
     * 听写监听器。
     */
    private RecognizerListener mRecognizerListener = new RecognizerListener() {

        @Override
        public void onBeginOfSpeech() {
            // 此回调表示：sdk内部录音机已经准备好了，用户可以开始语音输入
            showTip("开始说话");
        }

        @Override
        public void onError(SpeechError error) {
            // Tips：
            // 错误码：10118(您没有说话)，可能是录音机权限被禁，需要提示用户打开应用的录音权限。
            // 如果使用本地功能（语记）需要提示用户开启语记的录音权限。
            if(mTranslateEnable && error.getErrorCode() == 14002) {
                showTip( error.getPlainDescription(true)+"\n请确认是否已开通翻译功能" );
            } else {
               // showTip(error.getPlainDescription(true));
                showTip("你好像没说话呦！");
            }
        }

        @Override
        public void onEndOfSpeech() {
            // 此回调表示：检测到了语音的尾端点，已经进入识别过程，不再接受语音输入
            showTip("结束说话");
        }

        @Override
        public void onResult(RecognizerResult results, boolean isLast) {
            printResult(results);


        }

        @Override
        public void onVolumeChanged(int volume, byte[] data) {
            showTip("当前正在说话，音量大小：" + volume);
            Log.d(TAG, "返回音频数据："+data.length);
        }

        @Override
        public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
            // 以下代码用于获取与云端的会话id，当业务出错时将会话id提供给技术支持人员，可用于查询会话日志，定位出错原因
            // 若使用本地能力，会话id为null
            //	if (SpeechEvent.EVENT_SESSION_ID == eventType) {
            //		String sid = obj.getString(SpeechEvent.KEY_EVENT_SESSION_ID);
            //		Log.d(TAG, "session id =" + sid);
            //	}
        }
    };

    private void printResult(RecognizerResult results) {
        String text = JsonParser.parseIatResult(results.getResultString());

        String sn = null;
        // 读取json结果中的sn字段
        try {
            JSONObject resultJson = new JSONObject(results.getResultString());
            sn = resultJson.optString("sn");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        mIatResults.put(sn, text);

        StringBuffer resultBuffer = new StringBuffer();
        for (String key : mIatResults.keySet()) {
            resultBuffer.append(mIatResults.get(key));
        }

        et_search2.setText(resultBuffer.toString());
        et_search2.setSelection(et_search2.length());
        search();
    }
    



    private void showTip(final String str) {
        mToast.setText(str);
        mToast.show();
    }

    @Override
    public boolean onTouch(View v, MotionEvent event) {
        v.performClick();
        switch (event.getAction()){
            case MotionEvent.ACTION_DOWN:
                button.setImageResource(R.drawable.search_voice_clicked);
                // 移动数据分析，收集开始听写事件
                FlowerCollector.onEvent(SearchActivity.this, "iat_recognize");
                // 设置标点符号,设置为"0"返回结果无标点,设置为"1"返回结果有标点
                mIat.setParameter(SpeechConstant.ASR_PTT, "0");
                et_search2.setText(null);// 清空显示内容
                mIatResults.clear();
                     // 不显示听写对话框
                    ret = mIat.startListening(mRecognizerListener);
                    if (ret != ErrorCode.SUCCESS) {
                       // showTip("听写失败,错误码：" + ret);
                        showTip("语音使用失败，请再次重试");
                    } else {
                        showTip(getString(R.string.text_begin));
                    }
               
                break;
            case MotionEvent.ACTION_UP :
                button.setImageResource(R.drawable.search_voice_unclicked);
                break;

        }
        return true;
    }

}
