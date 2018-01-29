package com.runtai.newdexintong.module.homepage.fragment;

import android.app.ActionBar;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.fragment.BaseFragment;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.MyDateUtils;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StrUtil;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.comment.view.MyGridView;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.GsonUtil;
import com.runtai.newdexintong.module.home.widget.DialogUtil;
import com.runtai.newdexintong.module.home.widget.MyAlertDialog;
import com.runtai.newdexintong.module.homepage.activity.FlowRechargeActivity;
import com.runtai.newdexintong.module.homepage.activity.MobilePackageActivity;
import com.runtai.newdexintong.module.homepage.activity.QbRechargeActivity;
import com.runtai.newdexintong.module.homepage.adapter.ReadContactsDataAdapter;
import com.runtai.newdexintong.module.homepage.adapter.RechargeMoneyItemAdapter;
import com.runtai.newdexintong.module.homepage.bean.MobileRechargeBean;
import com.runtai.newdexintong.module.homepage.bean.MobileRechargeSuccessBean;
import com.runtai.newdexintong.module.homepage.bean.PhoneOwnerInfoBean;
import com.runtai.newdexintong.module.homepage.bean.RechargeMoneyItemBean;
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
 * @author：rhf
 * @date：2017/7/29time15:58
 * @detail：手机充值
 */

public class MobileRechargeFragment extends BaseFragment {

    private MyGridView myGridView_mobile_recharge;
    private List<RechargeMoneyItemBean> mData;
    private LinearLayout ll_q_money_recharge;
    private LinearLayout ll_mobile_package;
    private LinearLayout ll_flow_recharge;
    private LinearLayout ll_more;
    private TextView tv_call_place;
    private ImageView iv_contact;
    private String displayName;
    private List<String> numbers;
    private String phoneNumber;
    private AlertDialog dialog;
    private String num;
    private EditText et_phone;
    private int mobileType;
    private String gudStr;

    TextWatcher textWatcher = new TextWatcher() {

        private CharSequence temp_phone;

        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            temp_phone = s;
            if (temp_phone.length() < 11) {
                clearData();
            }
        }

        @Override
        public void afterTextChanged(Editable s) {

            if (temp_phone.length() > 0 && StrUtil.isMobileNo(temp_phone.toString())) {
                getPhoneLocation(temp_phone.toString());
            }
        }
    };
    private String region;
    private TextView tv_call_name;
    private TextView tv_call_balance_text;
    private TextView tv_call_balance;
    private TextView tv_qianfei_text;
    private TextView tv_qianfei;
    private TextView tv_package_name_text;
    private TextView tv_package_name;
    private TextView tv_consume_text;
    private TextView tv_consume_price;
    private TextView tv_call_name_text;

    @Override
    protected void onCreateView(Bundle savedInstanceState) {
        super.onCreateView(savedInstanceState);
        setContentView(R.layout.fragment_mobile_recharge);
        View view = getContentView();
        initView(view);
        initData();
        registerListener();
    }

    private void initView(View view) {

        ll_q_money_recharge = (LinearLayout) view.findViewById(R.id.ll_q_money_recharge);
        ll_mobile_package = (LinearLayout) view.findViewById(R.id.ll_mobile_package);
        ll_flow_recharge = (LinearLayout) view.findViewById(R.id.ll_flow_recharge);
        ll_more = (LinearLayout) view.findViewById(R.id.ll_more);
        tv_call_place = (TextView) view.findViewById(R.id.tv_call_place);
        iv_contact = (ImageView) view.findViewById(R.id.iv_contact);

        et_phone = (EditText) view.findViewById(R.id.et_phone);
        tv_package_name = (TextView) view.findViewById(R.id.tv_package_name);
        tv_call_name_text = (TextView) view.findViewById(R.id.tv_call_name_text);
        tv_call_balance_text = (TextView) view.findViewById(R.id.tv_call_balance_text);
        tv_qianfei_text = (TextView) view.findViewById(R.id.tv_qianfei_text);
        tv_package_name_text = (TextView) view.findViewById(R.id.tv_package_name_text);
        tv_consume_text = (TextView) view.findViewById(R.id.tv_consume_text);
        tv_call_name = (TextView) view.findViewById(R.id.tv_call_name);
        tv_call_balance = (TextView) view.findViewById(R.id.tv_call_balance);
        tv_qianfei = (TextView) view.findViewById(R.id.tv_qianfei);
        tv_consume_price = (TextView) view.findViewById(R.id.tv_consume_price);

        myGridView_mobile_recharge = (MyGridView) view.findViewById(R.id.myGridView_mobile_recharge);
    }

    private void registerListener() {

        et_phone.addTextChangedListener(textWatcher);
        ll_q_money_recharge.setOnClickListener(this);
        ll_mobile_package.setOnClickListener(this);
        ll_flow_recharge.setOnClickListener(this);
        ll_more.setOnClickListener(this);
        iv_contact.setOnClickListener(this);

        //充值选择项目的条目点击事件
        myGridView_mobile_recharge.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String phone = getPhoneNum();
                RechargeMoneyItemBean rechargeMoneyItemBean = mData.get(position);
                String realmoney = rechargeMoneyItemBean.denomination;
                //条目点击事件
                if (!StrUtil.isMobileNo(et_phone.getText().toString().trim())) {
                    ToastUtil.showToast(getActivity(), "请输入正确的手机号码", Toast.LENGTH_SHORT);
                    return;
                }

//                getPhoneLocation(phone);
                showDialog(phone, realmoney);

            }
        });
    }

    /**
     * 获取号码归属地
     */
    private void getPhoneLocation(final String phoneNumber) {

        String url = AppConstant.BASEURL2 + "api/huafei/gsd360";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("No", phoneNumber);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("No", phoneNumber)
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
                        MobileRechargeBean mobileRechargeBean = gson.fromJson(strJson, MobileRechargeBean.class);
                        MobileRechargeBean.DataBean data = mobileRechargeBean.getData();
                        gudStr = data.getProvince() + "|" + data.getCity() + "|" + data.getSp();
                        mobileType = data.getMobileType();
                        region = data.getRegion();
                        tv_call_place.setText(gudStr);
                        getPhoneOwnerMsg(phoneNumber);
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismissLoading();

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
                dismissLoading();
            }
        });

    }

    /**
     * 获取电话号码机主信息
     *
     * @param phoneStr
     */
    private void getPhoneOwnerMsg(String phoneStr) {

        showLoading();
        String url = AppConstant.BASEURL2 + "api/huafei/query";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("MobileType", String.valueOf(mobileType));
        map.put("Region", region);
        map.put("No", phoneStr);
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("MobileType", String.valueOf(mobileType))
                .addParams("Region", region)
                .addParams("No", phoneStr)
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
                        PhoneOwnerInfoBean phoneOwnerBean = gson.fromJson(strJson, PhoneOwnerInfoBean.class);
                        PhoneOwnerInfoBean.DataBean ownerInfoBeanData = phoneOwnerBean.getData();
                        tv_call_name_text.setText("机主姓名:");
                        tv_call_balance_text.setText("余额:");
                        tv_qianfei_text.setText("欠费:");
                        tv_package_name_text.setText("使用套餐:");
                        tv_consume_text.setText("本月消费:");

                        String name = ownerInfoBeanData.getName();
                        String balance = ownerInfoBeanData.getBalance();
                        String amount = ownerInfoBeanData.getAmount();
                        String pack = ownerInfoBeanData.getPack();
                        String total = ownerInfoBeanData.getTotal();
                        if (name != null && name.length() > 0) {
                            tv_call_name.setText(name);
                        } else {
                            tv_call_name.setText("");
                            tv_call_name_text.setText("");
                        }
                        if (balance != null && balance.length() > 0) {
                            tv_call_balance.setText(StringUtil.strToDouble_new(balance));
                        } else {
                            tv_call_balance.setText("");
                            tv_call_balance_text.setText("");
                        }
                        if (amount != null && amount.length() > 0) {
                            tv_qianfei.setText(StringUtil.strToDouble_new(amount));
                        } else {
                            tv_qianfei_text.setText("");
                            tv_qianfei.setText("");
                        }
                        if (pack != null && pack.length() > 0) {
                            tv_package_name.setText(pack);
                        } else {
                            tv_package_name.setText("");
                            tv_package_name_text.setText("");
                        }
                        if (total != null && total.length() > 0) {
                            tv_consume_price.setText(StringUtil.strToDouble_new(ownerInfoBeanData.getTotal()));
                        } else {
                            tv_consume_price.setText("");
                            tv_consume_text.setText("");
                        }
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(), getResources().getString(R.string.need_login_again));
                    } else {
                        ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismissLoading();

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
                dismissLoading();
            }
        });


    }

    /**
     * 充值信息确认的对话框
     *
     * @param phoneNumber
     * @param paymoney
     */
    private void showDialog(String phoneNumber, final String paymoney) {

        new MyAlertDialog(getActivity()).builder().setTitle("确认充值信息")
                .setMsg("确定为号码" + phoneNumber + "充值" + paymoney + "吗？")
                .setPositiveButton("立即充值", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        recharge(paymoney);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                }).show();
    }

    /**
     * 充值
     */
    private void recharge(String money) {

        String url = AppConstant.BASEURL2 + "api/huafei/create";
        String timeStamp = MyDateUtils.getCurrentMillisecond();
        String randomNumberTen = RandomUtil.getRandomNumberTen();
        String accessToken = SPUtils.getString(getActivity(), "accessToken", "");

        Map<String, String> map = new HashMap<>();
        map.clear();
        map.put("Gsd", StringUtil.getUrlEncodeResult(gudStr));
        map.put("Miane", money);
        map.put("MobileType", String.valueOf(mobileType));
        map.put("No", getPhoneNum());
        map.put("Timestamp", timeStamp);
        map.put("Nonce", randomNumberTen);
        map.put("AppId", AppConstant.appid_value);
        String signValue = StringUtil.getSignValue(map);

        OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
                .addHeader("Authorization", accessToken)
                .addParams("Gsd", gudStr)
                .addParams("Miane", money)
                .addParams("MobileType", String.valueOf(mobileType))
                .addParams("No", getPhoneNum())
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
                    String msg1 = jsonObject.getString("Msg");
                    Gson gson = GsonUtil.buildGson();
                    MobileRechargeSuccessBean mobileRechargeSuccessBean = gson.fromJson(strJson, MobileRechargeSuccessBean.class);
                    String msg = mobileRechargeSuccessBean.getData().getMsg();

                    if (code == 200) {
                        ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                        et_phone.setText("");
                        clearData();
                    } else if (code == 403) {
                        DialogUtil.showDialog(getActivity(),
                                getResources().getString(R.string.need_login_again));
                    } else {
                        if (msg1 == null || "".equals(msg1)) {
                            ToastUtil.showToast(getActivity(), msg, Toast.LENGTH_SHORT);
                        } else {

                            ToastUtil.showToast(getActivity(), msg1, Toast.LENGTH_SHORT);
                        }
                    }
                } catch (JSONException e) {
                    e.printStackTrace();
                }

                dismissLoading();

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("search_result", e.toString());
                dismissLoading();
            }
        });
    }

    /**
     * 读取信息成功后清除数据
     */
    private void clearData() {

        tv_call_place.setText("");
        tv_call_name_text.setText("");
        tv_call_balance_text.setText("");
        tv_qianfei_text.setText("");
        tv_package_name_text.setText("");
        tv_consume_text.setText("");
        tv_call_name.setText("");
        tv_call_balance.setText("");
        tv_qianfei.setText("");
        tv_package_name.setText("");
        tv_consume_price.setText("");
    }


    private void initData() {

        mData = new ArrayList<>();
        for (int i = 0; i < 8; i++) {
            RechargeMoneyItemBean bean = new RechargeMoneyItemBean();
            if (i == 0) {
                bean.denomination = "10";
            } else if (i == 1) {
                bean.denomination = "20";
            } else if (i == 2) {
                bean.denomination = "30";
            } else if (i == 3) {
                bean.denomination = "50";
            } else if (i == 4) {
                bean.denomination = "100";
            } else if (i == 5) {
                bean.denomination = "200";
            } else if (i == 6) {
                bean.denomination = "300";
            } else if (i == 7) {
                bean.denomination = "500";
            }
            mData.add(bean);
        }
        myGridView_mobile_recharge.setAdapter(new RechargeMoneyItemAdapter(getActivity(), mData));
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.ll_q_money_recharge://Q币充值
                startActivityByIntent(QbRechargeActivity.class);
                break;
            case R.id.ll_mobile_package://移动存送套餐
                startActivityByIntent(MobilePackageActivity.class);
                break;
            case R.id.ll_flow_recharge://流量充值
                startActivityByIntent(FlowRechargeActivity.class);
                break;
            case R.id.ll_more://更多
                ToastUtil.showToast(getActivity(), "更多功能敬请期待", Toast.LENGTH_SHORT);
//                startActivityByIntent(MoreRechargeActivity.class);
                break;
            case R.id.iv_contact://点击打开联系人列表
                Intent intent = new Intent(Intent.ACTION_PICK,
                        ContactsContract.Contacts.CONTENT_URI);
                // // startActivity(intent);
                startActivityForResult(intent, 1);
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        switch (requestCode) {
            case 1:
                // 如果结果为空就return
                if (data == null) {
                    return;
                }

                Uri uri = data.getData();
                printContacts(uri);

                break;
        }

    }

    /*
    * 自定义显示Contacts提供的联系人的方法
    */
    public void printContacts(Uri uri) {
        // 生成ContentResolver对象
        ContentResolver contentResolver = getActivity().getContentResolver();

        // 这段代码和上面代码是等价的，使用两种方式获得联系人的Uri
        Cursor cursor = contentResolver.query(uri, null, null, null, null);

        // 循环遍历
        if (cursor.moveToFirst()) {

            int idColumn = cursor.getColumnIndex(ContactsContract.Contacts._ID);
            int displayNameColumn = cursor
                    .getColumnIndex(ContactsContract.Contacts.DISPLAY_NAME);

            do {
                // 获得联系人的ID
                String contactId = cursor.getString(idColumn);
                // 获得联系人姓名
                displayName = cursor.getString(displayNameColumn);

                // 使用Toast技术显示获得的联系人信息
                // Toast.makeText(MainActivity.this, "联系人姓名：" + displayName,
                // Toast.LENGTH_LONG).show();
                if (numbers == null) {
                    numbers = new ArrayList<String>();
                } else {
                    numbers.clear();
                }

                // 获得联系人的电话号码列表
                Cursor phoneCursor = getActivity().getContentResolver().query(
                        ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                        null,
                        ContactsContract.CommonDataKinds.Phone.CONTACT_ID + "="
                                + contactId, null, null);

                if (phoneCursor.moveToFirst()) {
                    do {
                        // 遍历所有的联系人下面所有的电话号码
                        phoneNumber = phoneCursor
                                .getString(phoneCursor
                                        .getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));

                        numbers.add(phoneNumber);
                    } while (phoneCursor.moveToNext());
                }

                if (numbers.size() > 1) {
                    Window dialogWindow = createDialog(R.layout.dialog_contacts);
                    ListView list_item = (ListView) dialogWindow
                            .findViewById(R.id.list_item);

                    list_item.setAdapter(new ReadContactsDataAdapter(getActivity(), numbers, displayName));

                    list_item
                            .setOnItemClickListener(new AdapterView.OnItemClickListener() {
                                @Override
                                public void onItemClick(AdapterView<?> parent,
                                                        View view, int position, long id) {
                                    String selected_num = parent.getAdapter()
                                            .getItem(position).toString();
                                    // 如果有+86把他去掉
                                    if (selected_num.contains("+86")) {
                                        selected_num = selected_num
                                                .substring(3);
                                    }
                                    num = getNum(selected_num);

                                    if (StrUtil.isMobileNo(num)) {
                                        et_phone.setText(num);
                                        // 将光标移动到文本最后面
                                        et_phone.setSelection(et_phone
                                                .getText().length());
                                    } else {
                                        ToastUtil.showToast(getActivity(),
                                                "请正确输入手机号", 1000);
                                        et_phone.setText("");
                                    }
                                    dialog.dismiss();
                                }
                            });

                } else {

                    // 如果有+86把他去掉
                    if (phoneNumber.contains("+86")) {
                        phoneNumber = phoneNumber.substring(3);
                    }
                    num = getNum(phoneNumber);
                    if (StringUtil.checkMobilephone(num) && num.length() == 11) {
                        et_phone.setText(num);
                        // 将光标移动到文本最后面
                        et_phone.setSelection(et_phone.getText().length());
                    } else {
                        ToastUtil.showToast(getActivity(), "请正确输入手机号", 1000);
                        et_phone.setText("");
                    }

                }

            } while (cursor.moveToNext());
        } else {

            // // 没有权限，跳到设置界面，调用Android系统“应用程序信息（Application Info）”界面
            // new AlertDialog.Builder(this)
            // .setMessage("app需要开启允许读取联系人权限")
            // .setPositiveButton("设置", new DialogInterface.OnClickListener() {
            // @Override
            // public void onClick(DialogInterface dialogInterface, int i) {
            // Intent intent = new
            // Intent(android.provider.Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
            // intent.setData(Uri.parse("package:" + getPackageName()));
            // startActivity(intent);
            // }
            // })
            // .setNegativeButton("取消", null)
            // .create()
            // .show();
        }
        cursor.close();

    }

    /**
     * 自定义对话框
     *
     * @param id
     * @return
     */
    public Window createDialog(int id) {
        LinearLayout dialog_layout = (LinearLayout) LayoutInflater.from(getActivity())
                .inflate(id, null);

        if (dialog != null) {
            dialog.dismiss();
        }
        dialog = new AlertDialog.Builder(getActivity()).create();
        dialog.show();

        Window dialogWindow = dialog.getWindow();
        dialogWindow.setGravity(Gravity.CENTER);
        // 设置对话框的大小
        WindowManager m = dialogWindow.getWindowManager();
        Display d = m.getDefaultDisplay(); // 为获取屏幕宽、高
        WindowManager.LayoutParams p = dialogWindow.getAttributes(); // 获取对话框当前的参数值
        p.height = ActionBar.LayoutParams.WRAP_CONTENT;
        // p.height = (int) (d.getHeight() * 0.23); //高度设置为屏幕的0.4
        p.width = (int) (d.getWidth() * 0.8); // 宽度设置为屏幕的0.7
        dialogWindow.setAttributes(p); // 设置生效

        // 设置对话框样式
        dialogWindow.setWindowAnimations(R.style.myDialogStyle);
        dialogWindow.setContentView(dialog_layout);

        return dialogWindow;
    }

    /**
     * 过滤出一段字符串中的所有数字
     *
     * @param str 需要过滤的字符串
     * @return 过滤后的字符串
     */
    public String getNum(String str) {
        String str2 = "";
        str = str.trim();
        if (str != null && !"".equals(str)) {
            for (int i = 0; i < str.length(); i++) {
                if (str.charAt(i) >= 48 && str.charAt(i) <= 57) {
                    str2 += str.charAt(i);
                }
            }
        }
        return str2;
    }

    /**
     * 获取电话号码
     *
     * @return
     */
    public String getPhoneNum() {
        return et_phone.getText().toString().trim();
    }

}
