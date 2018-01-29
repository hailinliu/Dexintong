package com.runtai.newdexintong.module.personalcenter.activity;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.provider.MediaStore;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;
import com.runtai.newdexintong.comment.utils.FileUtil;
import com.runtai.newdexintong.comment.utils.MyCommUtil;
import com.runtai.newdexintong.comment.utils.GetUUID;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;

import okhttp3.Request;

/**
 * @作者：gyp
 * @日期：2017/4/5时间11:47
 * @描述：上传证件
 */

public class UploadActivity extends BaseActivity {

    private RelativeLayout head_back;
    private LinearLayout upload_shenfenzheng;//上传身份证界面
    private RelativeLayout up_sfz_zheng;//身份证正面
    private ImageView sfz_zheng_img;//身份证正面图片
    private RelativeLayout up_sfz_fan;//身份证反面
    private ImageView sfz_fan_img;//身份证正面图片
    private LinearLayout upload_yingyezheng;//上传营业证界面
    private RelativeLayout up_yyz;//营业证
    private ImageView yyzz_img;//营业证图片

    private Intent intent;
    private String zhengjian;
    private Dialog dialog;
    private int choose_index = -1;
    private int face_id_value = 111;
    private String sfz_zheng = ""; //身份证正面图片路径
    private int back_id_value = 222;
    private String sfz_fan = ""; //身份证反面图片路径
    private int business_licence_value = 333;
    private String yyzz = ""; //营业证图片路径

    // 裁剪后图片的宽(X)和高(Y),480 X 480的正方形。
    //此处为身份证照片尺寸设置
    private static int output_X = 300;
    private static int output_Y = 180;
    //此处为营业执照照片尺寸设置
    private static int output_X2 = 250;
    private static int output_Y2 = 450;
    private ImageView iv_idCard_face;
    private ImageView iv_idCard_back;
    /**
     * 请求不同状态的状态码
     */
    private static final int CAMERA_REQUEST_CODE = 1;
    private static final int RESULT_REQUEST_CODE = 2;
    private static final int SELECT_PIC_KITKAT = 3;

    private static final String IMAGE_FILE_NAME = "myImageView.jpg";
    private String fileName;
    private String fileId;
    private int tagId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_upload);

        fileId = GetUUID.getUUID(UploadActivity.this);

        initIntent();
        doHttp();
        initView();
        registerListener();
    }

    /**
     * 网络请求已经上传的图片
     */
    private void doHttp() {
    }

    private void initIntent() {
        intent = getIntent();
        zhengjian = intent.getStringExtra("zhengjian");
    }

    private void initView() {
        setActivityTitle();

        upload_shenfenzheng = (LinearLayout) findViewById(R.id.upload_shenfenzheng);
        up_sfz_zheng = (RelativeLayout) findViewById(R.id.up_sfz_zheng);
        sfz_zheng_img = (ImageView) findViewById(R.id.sfz_zheng_img);
        up_sfz_fan = (RelativeLayout) findViewById(R.id.up_sfz_fan);
        sfz_fan_img = (ImageView) findViewById(R.id.sfz_fan_img);

        upload_yingyezheng = (LinearLayout) findViewById(R.id.upload_yingyezheng);
        up_yyz = (RelativeLayout) findViewById(R.id.up_yyz);
        yyzz_img = (ImageView) findViewById(R.id.yyzz_img);


        if (zhengjian.equals("sfz")) {
            upload_shenfenzheng.setVisibility(View.VISIBLE);
            upload_yingyezheng.setVisibility(View.GONE);
        } else {
            upload_shenfenzheng.setVisibility(View.GONE);
            upload_yingyezheng.setVisibility(View.VISIBLE);
        }

        iv_idCard_face = (ImageView) findViewById(R.id.iv_idCard_face);
        iv_idCard_back = (ImageView) findViewById(R.id.iv_idCard_back);

    }

    private void registerListener() {

        up_sfz_zheng.setOnClickListener(this);
        sfz_zheng_img.setOnClickListener(this);
        up_sfz_fan.setOnClickListener(this);
        sfz_fan_img.setOnClickListener(this);
        up_yyz.setOnClickListener(this);
        yyzz_img.setOnClickListener(this);
    }

    /**
     * 设置界面标题
     */
    private void setActivityTitle() {

        head_back = (RelativeLayout) findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        TextView tv_title = (TextView) findViewById(R.id.tv_title);
        tv_title.setText("上传证件");
        TextView tv_subtitle = (TextView) findViewById(R.id.tv_subtitle);
        tv_subtitle.setVisibility(View.VISIBLE);
        tv_subtitle.setText("完成");
        tv_subtitle.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.tv_subtitle://点击完成，上传图片
                if ("sfz".equals(zhengjian)) {
                    if (face_id_value == 111) {
                        ToastUtil.showToast(this, "请添加身份证正面照片", Toast.LENGTH_SHORT);
                        return;
                    } else if (back_id_value == 222) {
                        ToastUtil.showToast(this, "请添加身份证反面照片", Toast.LENGTH_SHORT);
                        return;
                    } else {
                        ToastUtil.showToast(this, "身份证照片上传成功！", Toast.LENGTH_SHORT);
                        onBackPressed();
                    }

                } else if ("yyzz".equals(zhengjian)) {

                    if (business_licence_value == 333) {
                        ToastUtil.showToast(this, "请添加营业执照照片", Toast.LENGTH_SHORT);
                        return;
                    } else {
                        ToastUtil.showToast(this, "营业执照照片上传成功", Toast.LENGTH_SHORT);
                        onBackPressed();
                    }
                }
                break;
            case R.id.up_sfz_zheng://身份证正面
            case R.id.sfz_zheng_img://身份证正面图片
                choose_index = 0;
                showDialog();
                break;
            case R.id.up_sfz_fan://身份证反面
            case R.id.sfz_fan_img://身份证反面图片
                choose_index = 1;
                showDialog();
                break;
            case R.id.up_yyz://营业证
            case R.id.yyzz_img://营业证图片
                choose_index = 2;
                showDialog();
                break;
        }
    }

    /**
     * 显示Dialog
     */
    private void showDialog() {

        dialog = new Dialog(this);
        dialog.setContentView(R.layout.dialog_layout);
        dialog.findViewById(R.id.dialog_xiangce).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotoAlum();
                dialog.dismiss();
            }
        });
        dialog.findViewById(R.id.dialog_paizhao).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                selectPhotoGraph();
                dialog.dismiss();
            }
        });

        //获得当前窗体
        Window window = dialog.getWindow();
        if (window != null) {
            window.setBackgroundDrawable(null);
            //重新设置
            WindowManager.LayoutParams lp = window.getAttributes();
            window.setGravity(Gravity.CENTER);
            //lp.x = 100; // 新位置X坐标
            //lp.y = 100; // 新位置Y坐标
            //lp.width = 804; // 宽度
            //lp.height = 524; // 高度
            //lp.alpha = 0.7f; // 透明度
            // dialog.onWindowAttributesChanged(lp);
            //(当Window的Attributes改变时系统会调用此函数)
            window.setAttributes(lp);
        }
        dialog.show();
    }


    /**
     * 选择相册图片上传
     */
    public void selectPhotoAlum() {

        Intent intent1 = new Intent(Intent.ACTION_GET_CONTENT);
        intent1.setAction("android.intent.action.PICK");
        intent1.setData(MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent1.setType("image/*");
        startActivityForResult(intent1, SELECT_PIC_KITKAT);
    }

    /**
     * 选择拍照上传
     */
    public void selectPhotoGraph() {

        if (MyCommUtil.isSDcardExist()) {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            intent.putExtra("output",
                    Uri.fromFile(new File(Environment.getExternalStorageDirectory(),
                            IMAGE_FILE_NAME)));
            intent.putExtra("android.intent.extra.videoQuality", 0);
            startActivityForResult(intent, CAMERA_REQUEST_CODE);
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // 结果码不等于取消时候

        if (resultCode != RESULT_CANCELED) {
            switch (requestCode) {
                case SELECT_PIC_KITKAT:
                    //从相册中选择
                    if (data.getData() != null) {
                        String realFilePath = MyCommUtil.getRealFilePath(UploadActivity.this, data.getData());
                        fileName = realFilePath.substring(realFilePath.lastIndexOf("/") + 1, realFilePath.length());
                        cropRawPhoto(data.getData());
//                        setImageToImageView(data.getData());
                    }

                    break;
                case CAMERA_REQUEST_CODE:
                    //拍照上传
                    if (MyCommUtil.isSDcardExist()) {

                        File tempFile = new File(
                                Environment.getExternalStorageDirectory(),
                                IMAGE_FILE_NAME);
                        cropRawPhoto(Uri.fromFile(tempFile));
                        fileName = IMAGE_FILE_NAME;
                    } else {
                        ToastUtil.showToast(this, "未检测到sd卡", Toast.LENGTH_SHORT);
                    }
//                    setImageToImageView(Uri.fromFile(tempFile));
                    break;
                case RESULT_REQUEST_CODE:
                    //将裁剪后的照片进行设置
                    setImageToImageView(data);
                    break;

            }
        }
    }

    /**
     * 将图片上传到服务器
     *
     * @param file
     */

    private void uploadPicture(File file) {

        //测试用接口
        String upload_url = "http://192.168.1.113:8080/mobileService/api/file/upload";
//        String upload_url = "http://192.168.1.120:8080/uploadImage/api/file/upload";

        OkHttpUtils.post().url(upload_url)
                .addParams("fileuuid", fileId + String.valueOf(System.currentTimeMillis()))
                .addFile("file", fileName, file)
                .build().execute(new StringCallback() {

            @Override
            public void onResponse(String response) {

                Gson gson = new Gson();

            }

            @Override
            public void onError(Request request, Exception e) {
                LogUtil.e("upload_error", e.toString());
            }
        });
    }

    /**
     * 裁剪原始的图片
     */
    public void cropRawPhoto(Uri uri) {

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setDataAndType(uri, "image/*");
        // 设置裁剪
        intent.putExtra("crop", "true");

        // aspectX , aspectY :宽高的比例
        if (choose_index == 0 || choose_index == 1) {
            intent.putExtra("aspectX", 5);
            intent.putExtra("aspectY", 3);
            // outputX , outputY : 裁剪图片宽高
            intent.putExtra("outputX", output_X);
            intent.putExtra("outputY", output_Y);
        } else if (choose_index == 2) {
            intent.putExtra("aspectX", 19);
            intent.putExtra("aspectY", 26);
            // outputX , outputY : 裁剪图片宽高
            intent.putExtra("outputX", output_X2);
            intent.putExtra("outputY", output_Y2);
        }

        intent.putExtra("return-data", true);
        intent.putExtra("outputFormat", Bitmap.CompressFormat.JPEG.toString());

        startActivityForResult(intent, RESULT_REQUEST_CODE);
    }

    /**
     * 提取保存裁剪之后的图片数据，并设置头像部分的View
     */
    private void setImageToImageView(Intent intent) {

//        String realFilePath = MyCommUtil.getRealFilePath(UploadActivity.this, uri);
//        fileName = realFilePath.substring(realFilePath.lastIndexOf("/") + 1, realFilePath.length());
//        try {
//            String filePath = PictureUtil.compressImage(UploadActivity.this, realFilePath, fileName, 40);
//
//            Bitmap bitmap = BitmapFactory.decodeFile(filePath);
//
//            if (choose_index == 0) {
//                //身份证正面照片
//                iv_idCard_face.setImageBitmap(bitmap);
//                face_id_value = 112;
//            } else if (choose_index == 1) {
//                //身份证反面照片
//                iv_idCard_back.setImageBitmap(bitmap);
//                back_id_value = 223;
//            } else {
//                //营业执照的照片
//                yyzz_img.setImageBitmap(bitmap);
//                business_licence_value = 334;
//            }
//            File file = new File(filePath);
//            uploadPicture(file);
//        } catch (FileNotFoundException e) {
//            e.printStackTrace();
//        }

        Bundle extras = intent.getExtras();

        if (extras != null) {
            Bitmap photo = extras.getParcelable("data");
            String savePath = FileUtil.saveFile(this, fileName, photo);

            if (choose_index == 0) {
                //身份证正面照片
                iv_idCard_face.setImageBitmap(photo);
                face_id_value = 112;
            } else if (choose_index == 1) {
                //身份证反面照片
                iv_idCard_back.setImageBitmap(photo);
                back_id_value = 223;
            } else {
                yyzz_img.setImageBitmap(photo);
                business_licence_value = 334;
            }

            uploadPicture(new File(savePath));

        }
    }

}
