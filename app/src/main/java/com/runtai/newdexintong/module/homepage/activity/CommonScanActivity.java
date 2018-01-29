package com.runtai.newdexintong.module.homepage.activity;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.SurfaceView;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.zxing.Result;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.zxinglib.utils.AlertDialog;
import com.runtai.zxinglib.utils.Constant;
import com.runtai.zxinglib.utils.StringUtils;
import com.runtai.zxinglib.zxing.ScanListener;
import com.runtai.zxinglib.zxing.ScanManager;
import com.runtai.zxinglib.zxing.decode.DecodeThread;
import com.runtai.zxinglib.zxing.decode.Utils;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.ref.SoftReference;
import java.util.List;


/**
 * 二维码扫描界面
 */
public final class CommonScanActivity extends BaseActivity implements ScanListener, View.OnClickListener {
    
    static final String TAG = CommonScanActivity.class.getSimpleName();
    SurfaceView scanPreview = null;
    View scanContainer;
    View scanCropView;
    ImageView scanLine;
    ScanManager scanManager;
    TextView iv_light;
    TextView qrcode_g_gallery;
    TextView qrcode_ic_back;
    final int PHOTOREQUESTCODE = 1111;

    private int scanMode;//扫描模型（条形，二维码，全部）

    Button rescan;
    ImageView scan_image;
    RelativeLayout authorize_return;
    TextView title;
    TextView scan_hint;
    TextView tv_scan_result;

    @Override
    public void onCreate(Bundle icicle) {
        super.onCreate(icicle);

        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON);
        setContentView(R.layout.activity_scan_code);

        rescan = (Button) findViewById(R.id.service_register_rescan);
        scan_image = (ImageView) findViewById(R.id.scan_image);
        authorize_return = (RelativeLayout) findViewById(R.id.authorize_return);
        title = (TextView) findViewById(R.id.common_title_TV_center);
        scan_hint = (TextView) findViewById(R.id.scan_hint);
        tv_scan_result = (TextView) findViewById(R.id.tv_scan_result);

        scanMode = getIntent().getIntExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
        initView();
    }

    void initView() {
        switch (scanMode) {
            case DecodeThread.BARCODE_MODE:
//                title.setText(R.string.scan_barcode_title);
                scan_hint.setText(R.string.scan_barcode_hint);
                break;
            case DecodeThread.QRCODE_MODE:
//                title.setText(R.string.scan_qrcode_title);
                scan_hint.setText(R.string.scan_qrcode_hint);
                break;
            case DecodeThread.ALL_MODE:
//                title.setText(R.string.scan_allcode_title);
                scan_hint.setText(R.string.scan_allcode_hint);
                break;
        }
        scanPreview = (SurfaceView) findViewById(R.id.capture_preview);
        scanContainer = findViewById(R.id.capture_container);
        scanCropView = findViewById(R.id.capture_crop_view);
        scanLine = (ImageView) findViewById(R.id.capture_scan_line);
        qrcode_g_gallery = (TextView) findViewById(R.id.qrcode_g_gallery);
        qrcode_ic_back = (TextView) findViewById(R.id.qrcode_ic_back);
        iv_light = (TextView) findViewById(R.id.iv_light);
        
        qrcode_g_gallery.setOnClickListener(this);
        qrcode_ic_back.setOnClickListener(this);
        iv_light.setOnClickListener(this);
        rescan.setOnClickListener(this);
        authorize_return.setOnClickListener(this);
        //构造出扫描管理器

        scanManager = new ScanManager(this, scanPreview, scanContainer, scanCropView, scanLine, scanMode, this);
    }

    @Override
    public void onResume() {
        super.onResume();
        scanManager.onResume();
        rescan.setVisibility(View.INVISIBLE);
//        scan_image.setVisibility(View.GONE);
    }

    @Override
    public void onPause() {
        super.onPause();
        scanManager.onPause();
    }

    @Override
    public void scanResult(Result rawResult, Bundle bundle) {
        //扫描成功后，扫描器不会再连续扫描，如需连续扫描，调用reScan()方法。
        //scanManager.reScan();
//		Toast.makeText(this, "result="+rawResult.getText(), Toast.LENGTH_LONG).show();
       
        if (!scanManager.isScanning()) { //如果当前不是在扫描状态
            //设置再次扫描按钮出现
            rescan.setVisibility(View.VISIBLE);
            scan_image.setVisibility(View.VISIBLE);
            Bitmap barcode = null;
            byte[] compressedBitmap = bundle.getByteArray(DecodeThread.BARCODE_BITMAP);
            if (compressedBitmap != null) {
                barcode = BitmapFactory.decodeByteArray(compressedBitmap, 0, compressedBitmap.length, null);
                barcode = barcode.copy(Bitmap.Config.ARGB_8888, true);
                //这里可以剪裁图片处理
            }
//            scan_image.setImageBitmap(barcode);//扫描成功后把扫描截图设置上去
        }
        rescan.setVisibility(View.VISIBLE);
        scan_image.setVisibility(View.VISIBLE);
//        tv_scan_result.setVisibility(View.VISIBLE);
//        tv_scan_result.setText("结果：" + rawResult.getText());
        handleText(rawResult.getText());
    }

    /**
     * 如果程序出现oom就用这个方法
     */
    public static Bitmap byteToBitmap(byte[] imgByte) {
        
        InputStream input = null;
        Bitmap bitmap = null;
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inSampleSize = 8;
        input = new ByteArrayInputStream(imgByte);
        SoftReference softRef = new SoftReference(BitmapFactory.decodeStream(
                input, null, options));
        bitmap = (Bitmap) softRef.get();
        if (imgByte != null) {
            imgByte = null;
        }

        try {
            if (input != null) {
                input.close();
            }
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return bitmap;
    }

    void startScan() {
        if (rescan.getVisibility() == View.VISIBLE) {
            rescan.setVisibility(View.INVISIBLE);
//            scan_image.setVisibility(View.GONE);
            scan_image.setImageBitmap(null);
            tv_scan_result.setVisibility(View.GONE);
            scanManager.reScan();
        }
    }

    @Override
    public void scanError(Exception e) {
        Toast.makeText(this, e.getMessage(), Toast.LENGTH_LONG).show();
        //相机扫描出错时
        if (e.getMessage() != null && e.getMessage().startsWith("相机")) {
            scanPreview.setVisibility(View.INVISIBLE);
        }
    }

    public void showPictures(int requestCode) {
        Intent intent = new Intent(Intent.ACTION_PICK);
        intent.setType("image/*");
        startActivityForResult(intent, requestCode);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        String photo_path;
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case PHOTOREQUESTCODE:
                    String[] proj = {MediaStore.Images.Media.DATA};
                    Cursor cursor = this.getContentResolver().query(data.getData(), proj, null, null, null);
                    if (cursor.moveToFirst()) {
                        int colum_index = cursor.getColumnIndexOrThrow(MediaStore.Images.Media.DATA);
                        photo_path = cursor.getString(colum_index);
                        if (photo_path == null) {
                            photo_path = Utils.getPath(getApplicationContext(), data.getData());
                        }
                        scanManager.scanningImage(photo_path);
                    }
            }
        }
    }

    @Override
    public void onClick(View v) {
        int i = v.getId();
        if (i == R.id.qrcode_g_gallery) {
            showPictures(PHOTOREQUESTCODE);

        } else if (i == R.id.iv_light) {
            scanManager.switchLight();

        } else if (i == R.id.qrcode_ic_back) {
            finish();

        } else if (i == R.id.service_register_rescan) {
            startScan();

        } else if (i == R.id.authorize_return) {
            finish();

        }
    }

    /**
     * 扫描结果
     * 判断扫描结果是否是URL或TEXT
     */
    private void handleText(String text) {
        if (StringUtils.isUrl(text)) {
            showUrlOption(text);
        } else {
            handleOtherText(text);
        }
    }

    /**
     * URL地址 提示打开(可再判断是否是image图片地址)
     */
    private void showUrlOption(final String url) {

        if (url.contains("http://weixin.qq.com/r/")) {
            if (isWeixinAvilible(this)) {
                //已安装微信客户端
                Intent intent = getPackageManager().getLaunchIntentForPackage("com.tencent.mm");
                startActivity(intent);
            } else {
                //没有安装微信客户端
                Toast.makeText(this, "您还没有安装微信，请先安装微信客户端",Toast.LENGTH_SHORT).show();
            }
            return;
        }
        Log.e("扫面结果", "是个网址");
        new AlertDialog(CommonScanActivity.this).builder().setMsg("是否打开该链接?" + "\n" + url)
                .setPositiveButton("打开", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startE(url);
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        startScan();
                    }
                }).show();
    }

    /**
     * 判断 用户是否安装微信客户端
     */
    public static boolean isWeixinAvilible(Context context) {
        final PackageManager packageManager = context.getPackageManager();// 获取packagemanager
        List<PackageInfo> pinfo = packageManager.getInstalledPackages(0);// 获取所有已安装程序的包信息
        if (pinfo != null) {
            for (int i = 0; i < pinfo.size(); i++) {
                String pn = pinfo.get(i).packageName;
                if (pn.equals("com.tencent.mm")) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * 启用外部浏览器(或内置浏览器)
     */
    public void startE(String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        startActivity(intent);
    }

    /**
     * 本文框显示本文
     */
    private void handleOtherText(final String text) {
        // 判断是否符合基本的json格式	可添加解析类工具
        if (!text.matches("^\\{.*")) {
//            showCopyTextOption(text);
            Intent intent = new Intent(this,ScanResultActivity.class);
            intent.putExtra("result",text);
            startActivityByIntent(intent);
            finish();
        }
    }

    /**
     * 显示普通文本，携带复制信息按钮
     *
     * @param text
     */
    private void showCopyTextOption(final String text) {
        new AlertDialog(this).builder().setMsg(text)
                .setPositiveButton("复制", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ClipboardManager clipboard = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
                        ClipData myClip = ClipData.newPlainText("text", text);
                        clipboard.setPrimaryClip(myClip);
                        Toast.makeText(CommonScanActivity.this, "复制成功", Toast.LENGTH_SHORT).show();
                    }
                })
                .setNegativeButton("取消", new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        /** 重新扫描 */
                        startScan();
                    }
                }).show();
    }

    /**
     * 获取粘贴板内容
     */
    public void pause() {
        ClipboardManager cbm = (ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE);
        cbm.getPrimaryClip().getItemAt(0).getText().toString();
    }

}