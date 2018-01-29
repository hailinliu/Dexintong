package com.runtai.newdexintong.module.home.activity.login;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.Editable;
import android.text.InputType;
import android.text.SpannableString;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.text.method.DigitsKeyListener;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MD5Util;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.NetUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StrUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.activity.MainActivity;
import com.runtai.newdexintong.module.home.activity.register.UserRegisterActivity;
import com.runtai.newdexintong.module.home.bean.LoginBean;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.CountDownButtonHelper;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.homepage.bean.LoginSelectCityBean;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;


/**
 * 登录界面
 */
public class LoginActivity extends BaseActivity {
    private final String TAG = "LoginActivity";
    // private final int REQUEST_PICTURE_CHOOSE = 1;
    private final int REQUEST_CAMERA_IMAGE = 2;

  
    // authid为6-18个字符长度，用于唯一标识用户

    private Toast mToast;

 

    private RelativeLayout head_back;
    private TextView tv_title;
    private Button btn_login;
    private ImageView iv_select_city;
    private ImageView iv_password;
    private TextView tv_user_register;
    private EditText et_password;
    private TextView tv_forget_password;
    private Button btn_message_identify;
    private Button btn_token_identify;
  
    private TextView tv_cityName;
    private EditText et_account;
    private EditText et_identify_code;
    private boolean flag = false;
    private TextView tv_tourist_login;
    private ImageView iv_clear_phone;
    private Button btn_get_code;

    // 定位结果监听
//    public BDLocationListener myListener;
    // 搜索的经纬度
    private double latitude;
    private double longitude;
    private String city_name;

    TextWatcher accountWatcher = new TextWatcher() {

        private CharSequence temp_phone;
        private CharSequence temp;//监听前的文本  
        private int editStart;//光标开始位置  
        private int editEnd;//光标结束位置  
        private final int charMaxNum = 11;
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
           /* LogUtil.i("TextWatcher","开始位置:"+start);
            LogUtil.i("TextWatcher","结束位置:"+after);
            LogUtil.i("TextWatcher","count数量:"+count);
            if(after-start>1) {
              
                facelogin.setVisibility(View.VISIBLE);
            }*/
            temp_phone = s;
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

            if(temp_phone.length()==charMaxNum){
                facelogin.setVisibility(View.VISIBLE);
            }
           else{
                facelogin.setVisibility(View.INVISIBLE);
            }

        }

        @Override
        public void afterTextChanged(Editable s) {
            /** 得到光标开始和结束位置 ,超过最大数后记录刚超出的数字索引进行控制 */
            editStart = et_account.getSelectionStart();
            editEnd = et_account.getSelectionEnd();
            if (temp_phone.length() > charMaxNum) {
                Toast.makeText(getApplicationContext(), "你输入的字数已经超过了限制！", Toast.LENGTH_LONG).show();
                s.delete(editStart - 1, editEnd);
                int tempSelection = editStart;
                et_account.setText(s);
                et_account.setSelection(tempSelection);
            }
            if (temp_phone.length() == charMaxNum) {
                facelogin.setVisibility(View.VISIBLE);
                
            } else {
                iv_clear_phone.setVisibility(View.GONE);
              
            }

        }
    };
    TextWatcher codeWatcher = new TextWatcher() {

        private CharSequence temp_code;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp_code = s;
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (s != null && StrUtil.isMobileNo(getPhone())
                    && !TextUtils.isEmpty(getPassword())) {
                if (temp_code.length() == 6) {
                    setLoginButton(true);
                } else {
                    setLoginButton(false);

                }
            }
        }
    };

    TextWatcher passwordWatcher = new TextWatcher() {

        private CharSequence temp_password;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp_password = s;
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp_password.length() > 0 && StrUtil.isMobileNo(getPhone())
                    ) {
                setGetCodeButton(true);
            } else {
                setGetCodeButton(false);
            }
        }
    };


//    private LocationClient mLocationClient;
    private String account;
    private LoginSelectCityBean bean;
    private String cityName;
    String key = "";
    String domain = "";
    String city = "";
    String image = "";
    /**
     * 短信验证和令牌验证的区别值
     */
    private String identifyTypeValue = String.valueOf(0);
//    private LocationService locationService;
    private Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    tv_cityName.setText(city_name);
                    break;
            }
        }
    };
  
    private TextView facelogin;
   

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

//        locationService = ((MyApplication) getApplication()).locationService;
//        myListener = new MyLocationListener();
//        locationService.registerListener(myListener);
//        locationService.setLocationOption(locationService.getDefaultLocationClientOption());
//        locationService.start();// 定位SDK

//        getCityListData();
        mToast = Toast.makeText(LoginActivity.this, "", Toast.LENGTH_SHORT);
        initView();
       // mAuthid = et_account.getText().toString();
        registerListener();
    }


    private void initView() {

        setActivityTitle();
        btn_login = (Button) findViewById(R.id.btn_login);
      
        tv_cityName = (TextView) findViewById(R.id.tv_cityName);
//        StrUtil.setCursorAfter(et_city);
        et_account = (EditText) findViewById(R.id.et_account);
        StrUtil.setCursorAfter(et_account);
        
        et_password = (EditText) findViewById(R.id.et_password);
        StrUtil.setCursorAfter(et_password);
        String digits = getResources().getString(R.string.filter_vcode);
        et_password.setKeyListener(DigitsKeyListener.getInstance(digits));

        et_identify_code = (EditText) findViewById(R.id.et_identify_code);
        StrUtil.setCursorAfter(et_identify_code);
        et_identify_code.setImeOptions(EditorInfo.IME_ACTION_SEND);
        iv_select_city = (ImageView) findViewById(R.id.iv_select_city);
        iv_password = (ImageView) findViewById(R.id.iv_password);
        tv_user_register = (TextView) findViewById(R.id.tv_user_register);
        tv_forget_password = (TextView) findViewById(R.id.tv_forget_password);
        btn_message_identify = (Button) findViewById(R.id.btn_message_identify);
        btn_token_identify = (Button) findViewById(R.id.btn_token_identify);

        tv_tourist_login = (TextView) findViewById(R.id.tv_tourist_login);
        iv_clear_phone = (ImageView) findViewById(R.id.iv_clear_phone);
        btn_get_code = (Button) findViewById(R.id.btn_get_code);
        facelogin = (TextView) findViewById(R.id.face_login);
        String username_ = SPUtils.getString(this, "login", "");
        String password_ = SPUtils.getString(this, "password", "");
        if (!"".equals(username_) && !"".equals(password_)) {
            et_account.setText(username_);
            facelogin.setVisibility(View.VISIBLE);
            et_password.setText(password_);
            et_identify_code.requestFocus();
            setGetCodeButton(true);
        } else {
            setGetCodeButton(false);
        }


    }


    private void registerListener() {

        btn_login.setOnClickListener(this);
        iv_select_city.setOnClickListener(this);
        iv_password.setOnClickListener(this);
        tv_user_register.setOnClickListener(this);
        tv_forget_password.setOnClickListener(this);
        btn_message_identify.setOnClickListener(this);
        btn_token_identify.setOnClickListener(this);
        tv_tourist_login.setOnClickListener(this);
        iv_clear_phone.setOnClickListener(this);
        btn_get_code.setOnClickListener(this);
        facelogin.setOnClickListener(this);
        et_identify_code.addTextChangedListener(codeWatcher);
        et_password.addTextChangedListener(passwordWatcher);
        et_account.addTextChangedListener(accountWatcher);
//        tv_cityName.addTextChangedListener(citynameWatcher);

        et_identify_code.setOnEditorActionListener(new TextView.OnEditorActionListener() {

            @Override
            public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {

                if (actionId == EditorInfo.IME_ACTION_SEND
                        || actionId == EditorInfo.IME_ACTION_DONE
                        || (event != null && KeyEvent.KEYCODE_ENTER == event.getKeyCode()
                        && KeyEvent.ACTION_DOWN == event.getAction())) {
                    //处理事件
                    if (checkLoginInfo(123)) {
                        loginIn();
                    }
                }
                return false;
            }
        });
    }

    /**
     * 设置标题栏
     */
    private void setActivityTitle() {
        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setVisibility(View.GONE);
        tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("登录");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.btn_login://登录按钮
                if (checkLoginInfo(123)) {
                    loginIn();
                }
                break;
            case R.id.face_login://刷脸验证
                showTip("刷脸前要保证先注册呦！");
            Intent intent = new Intent(this,MixedVerifyActivity.class);
            intent.putExtra("et_account",getPhone());
            intent.putExtra("et_password",getPassword());
            startActivityByIntent(intent);
                /*// 设置相机拍照后照片保存路径
                mPictureFile = new File(Environment.getExternalStorageDirectory(),
                        "picture" + System.currentTimeMillis()/1000 + ".jpg");
                // 启动拍照,并保存到临时文件
                Intent mIntent = new Intent();
                mIntent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);
                mIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(mPictureFile));
                mIntent.putExtra(MediaStore.Images.Media.ORIENTATION, 0);
                startActivityForResult(mIntent, REQUEST_CAMERA_IMAGE);*/
                
                break;
            case R.id.iv_select_city://点击图标选择城市，弹出下来菜单
//                showPopuwindow();
                break;

            case R.id.iv_password://明文和密文的切换
                if (flag) {
                    iv_password.setImageResource(R.mipmap.hide_password_icon);
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    flag = false;

                } else {
                    iv_password.setImageResource(R.mipmap.show_password_icon);
                    et_password.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_VISIBLE_PASSWORD);
                    flag = true;
                }
                StrUtil.setCursorAfter(et_password);
                break;
            case R.id.tv_user_register://新用户注册
                startActivityByIntent(UserRegisterActivity.class);
                break;
            case R.id.tv_forget_password://忘记密码之后，找到密码
                Intent intent_findpassword = new Intent(this, FindPassWordActivity.class);
                intent_findpassword.putExtra("login_account", et_account.getText().toString().trim());
                startActivityByIntent(intent_findpassword);
                break;

            case R.id.btn_message_identify://短信验证
                identifyTypeValue = String.valueOf(1);
                et_identify_code.setText("");
                et_identify_code.setHint(new SpannableString("请输入短信验证码"));
                btn_message_identify.setTextColor(Color.parseColor("#ffffff"));
                btn_token_identify.setTextColor(Color.parseColor("#FF2D48"));
                btn_message_identify.setBackgroundResource(R.drawable.shape_button_red_filled_bg);
                btn_token_identify.setBackgroundResource(R.drawable.shape_button_red_edge_bg);
                btn_get_code.setVisibility(View.VISIBLE);
                et_identify_code.setHintTextColor(Color.parseColor("#ffacacac"));

                break;
            case R.id.btn_token_identify://令牌验证

                identifyTypeValue = String.valueOf(0);
                et_identify_code.setText("");
                et_identify_code.setHint(new SpannableString("请输入动态口令"));
                btn_token_identify.setTextColor(Color.parseColor("#ffffff"));
                btn_message_identify.setTextColor(Color.parseColor("#FF2D48"));
                btn_token_identify.setBackgroundResource(R.drawable.shape_button_red_filled_bg);
                btn_message_identify.setBackgroundResource(R.drawable.shape_button_red_edge_bg);
                et_identify_code.setHintTextColor(Color.parseColor("#ffacacac"));
                btn_get_code.setVisibility(View.GONE);
                break;

            case R.id.tv_tourist_login://游客登录
                startActivityByIntent(TouristLoginActivity.class);
                break;

            case R.id.btn_get_code://点击获取验证码
                if (!NetUtil.isNetworkAvailable(this)) {
                    ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
                    return;
                }
                //获取验证码成功
                btn_get_code.setEnabled(false);

                getMessageIdentifyCode();
                CountDownButtonHelper helper = new CountDownButtonHelper(
                        btn_get_code, "重发", 60, 1);
                helper.setOnFinishListener(new CountDownButtonHelper.OnFinishListener() {

                    @Override
                    public void finish() {
                        et_account.setEnabled(true);
                        iv_clear_phone.setEnabled(true);
                        setGetCodeButton(true);
                    }
                });

                helper.start();
                setGetCodeButton(false);
                iv_clear_phone.setEnabled(false);
                et_account.setEnabled(false);
                break;
            case R.id.iv_clear_phone://清除电话号码
                et_account.setText("");
                setLoginButton(false);
                setGetCodeButton(false);
                break;

        }
    }

    /**
     * 获取短信验证码
     */
    private void getMessageIdentifyCode() {

        if (checkLoginInfo(1)) {

          
            // identifyTypeValue 验证类型
            String URL = AppConstant.BASEURL2 + "api/login/send";
            String timeStamp = MyDateUtils.getCurrentMillisecond();
            String randomNumberTen = RandomUtil.getRandomNumberTen();
            String accessToken = SPUtils.getString(this, "accessToken", "");
            String pwd = MD5Util.md5(MD5Util.md5(getPassword()));
            String usr = getPhone();

            Map<String, String> map = new HashMap<>();
            map.clear();
            map.put("Login", usr);
            map.put("Pwd", pwd);
            map.put("Vt", "1");
            map.put("Timestamp", timeStamp);
            map.put("Nonce", randomNumberTen);
            map.put("AppId", AppConstant.appid_value);
           String signValue = StringUtil.getSignValue(map);

            OkHttpUtils.post().url(URL).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                    .addParams("Login", usr)
                    .addParams("Pwd", pwd)
                    .addParams("Vt", "1")
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
//                        int code = Integer.parseInt(jsonObject.getString("Code"));
                        String msg = jsonObject.getString("Msg");

                        ToastUtil.showToast(LoginActivity.this, msg, Toast.LENGTH_SHORT);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }

                @Override
                public void onError(Request request, Exception e) {
                    LogUtil.e("getmessage", e.toString());
                }
            });

        }
    }

    /**
     * 密码登录
     */
    private void loginIn() {

        if (!NetUtil.isNetworkAvailable(this)) {
            ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
            return;
        }
        String url = AppConstant.BASEURL2 + "api/login/signin";
        final String account = getPhone();
        String pwd = MD5Util.md5(MD5Util.md5(getPassword()));
        String timeStamp = String.valueOf(new Date().getTime());
        String randomNumberTen = RandomUtil.getRandomNumberTen();

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Vt", identifyTypeValue);
        map.put("Timestamp", timeStamp);
        map.put("Pwd", pwd);
        map.put("Nonce", randomNumberTen);
        map.put("Login", account);
        map.put("Captcha", getIdentifycode());
        map.put("AppId", AppConstant.appid_value);
        map.put("Type", "0");
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, "2.0")
                .addParams("AppId", AppConstant.appid_value)
                .addParams("Captcha", getIdentifycode())
                .addParams("Login", account)
                .addParams("Nonce", randomNumberTen)
                .addParams("Pwd", pwd)
                .addParams("Timestamp", timeStamp)
                .addParams("Vt", identifyTypeValue)
                .addParams("Type","0")
                .addParams("Sign", signValue)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {
                byte[] decode = Base64.decode(response);//base64解码
                String strJson = UTF8Util.getString(decode);
                Gson gson = GsonUtil.buildGson();
                LoginBean loginBean = gson.fromJson(strJson, LoginBean.class);
                if (loginBean != null) {
                    int code = loginBean.getCode();
                    String msg = loginBean.getMsg();

                    if (200 == code) {
                        try {
                            LoginBean.DataBean data = loginBean.getData();

                            String accessToken = data.getToken().getAccessToken();
                            SPUtils.putString(LoginActivity.this, "login", account);
                            SPUtils.putString(LoginActivity.this, "login_success", account);
                            SPUtils.putString(LoginActivity.this, "password", getPassword());
                            SPUtils.putString(LoginActivity.this, "accessToken", "BasicAuth " + accessToken);//BasicAuth后面需要加一个英文空格
                            SPUtils.putBoolean(LoginActivity.this,"IsCod",data.getMerchant().isIsCod());

                            LoginBean.DataBean.MerchantBean merchant = data.getMerchant();
//                            SPUtils.putString(LoginActivity.this, "shopName", merchant.getName());
//                            SPUtils.putString(LoginActivity.this, "jifen", String.valueOf(merchant.getJiFen()));
                            SPUtils.putString(LoginActivity.this, "merchant", gson.toJson(merchant));

                            Intent intent = new Intent(LoginActivity.this, MainActivity.class);
//                            intent.putExtra("tag", "selected_homePage");
                            startActivityByIntent(intent);
                            finish();
                            showToast("登录成功！");
                        } catch (Exception e) {
                            e.printStackTrace();
                            LogUtil.e("failed",e.getMessage());
                        }

                    } else {
                        showToast(msg);
                        LogUtil.e("failed",msg);
                    }
                } else {
                    ToastUtil.showToast(LoginActivity.this, "解析数据失败", Toast.LENGTH_SHORT);
                }
            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("login", e.toString());
            }
        });
    }

   
    /**
     * 检查登录信息
     *
     * @return
     */
    private boolean checkLoginInfo(int flag) {

        String city_name = tv_cityName.getText().toString().trim();
        account = et_account.getText().toString().trim();
        //mAuthid = account;
        String password = et_password.getText().toString().trim();
        String identify_code = et_identify_code.getText().toString().trim();

        if (!StrUtil.isMobileNo(account)) {
            ToastUtil.showToast(this, "请输入正确的手机号", Toast.LENGTH_SHORT);
            return false;
        }

        if (password.length() < 6 || password.length() > 20) {
            ToastUtil.showToast(LoginActivity.this, "密码须有6-20位字母或数字组成", Toast.LENGTH_SHORT);
            return false;
        }

        if (flag == 123) {
            if (TextUtils.isEmpty(identify_code)) {
                ToastUtil.showToast(this, "请输入验证码", Toast.LENGTH_SHORT);
                return false;
            }
        }

        return true;
    }

    /**
     * 设置获取验证码的button
     *
     * @param flag
     */
    public void setGetCodeButton(boolean flag) {
        if (flag) {
            btn_get_code.setBackgroundResource(R.drawable.shape_button_red_edge_bg);
            btn_get_code.setTextColor(getResources().getColor(R.color.new_title_color));
        } else {
            btn_get_code.setBackgroundResource(R.drawable.shape_button_gray_edge_bg);
            btn_get_code.setTextColor(getResources().getColor(R.color.text_gray));
        }
        btn_get_code.setEnabled(flag);
    }

    /**
     * 设置登录按钮
     */
    private void setLoginButton(boolean isCan) {
        if (isCan) {
            btn_login.setEnabled(true);
            btn_login.setTextColor(getResources().getColor(R.color.white));
        } else {
            btn_login.setEnabled(false);
            btn_login.setTextColor(getResources().getColor(R.color.text_gray));
        }
    }

    /**
     * 获取登录城市
     *
     * @return
     */
    public String getCity() {
        return tv_cityName.getText().toString().trim();
    }

    /**
     * 获取登录账号
     *
     * @return
     */
    public String getPhone() {
        return et_account.getText().toString().trim();
    }

    /**
     * 获取登录密码
     *
     * @return
     */
    public String getPassword() {
        return et_password.getText().toString().trim();
    }

    /**
     * 获取输入的验证码
     *
     * @return
     */
    public String getIdentifycode() {
        return et_identify_code.getText().toString().trim();
    }

    private void showTip(final String tip) {
        mToast.setText(tip);
        mToast.show();
    }
    
   
  
}
