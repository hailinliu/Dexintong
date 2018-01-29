package com.runtai.newdexintong.module.home.activity.register;

import android.Manifest.permission;
import android.annotation.SuppressLint;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.ImageFormat;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.graphics.YuvImage;
import android.hardware.Camera;
import android.hardware.Camera.CameraInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Process;
import android.support.v7.app.AlertDialog;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.iflytek.cloud.ErrorCode;
import com.iflytek.cloud.IdentityListener;
import com.iflytek.cloud.IdentityResult;
import com.iflytek.cloud.IdentityVerifier;
import com.iflytek.cloud.InitListener;
import com.iflytek.cloud.SpeechConstant;
import com.iflytek.cloud.SpeechError;
import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.utils.LogUtil;
import com.runtai.newdexintong.comment.utils.NetUtil;
import com.runtai.newdexintong.comment.utils.RandomUtil;
import com.runtai.newdexintong.comment.utils.SPUtils;
import com.runtai.newdexintong.comment.utils.StringUtil;
import com.runtai.newdexintong.comment.utils.ToastUtil;
import com.runtai.newdexintong.comment.utils.des.Base64;
import com.runtai.newdexintong.comment.utils.des.UTF8Util;
import com.runtai.newdexintong.module.home.utils.AppConstant;
import com.runtai.newdexintong.module.home.utils.PictureUtil;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import okhttp3.Request;


/**
 * 离线视频流检测
 */
public class RegisterFaceActivity extends BaseVideoActivity {

	private final static String API_KEY = "lJsij4n8pYEj3bW-tSJqEhRgkdfHobC8";
	private final static String API_Secret = "i1H3kRBBzJ2Wo_1T-6RsbRmWgcHAREww";
	private static int fileUploadProgress = 0;
	private final static String TAG = RegisterFaceActivity.class.getSimpleName();
	private AlertDialog dialog = null;
	//private Button button_take_photos,button_select;
	private ImageView testImageView,imageView_preview;
	private static final int PHOTO_REQUEST_GALLERY = 1;
	private static final int PHOTO_REQUEST_CUT = 2;
	private boolean mStopTrack;
	private String mAuthid ;
	private byte[] mImageData = null;
	private Camera mCamera;
	private Handler mhandler = new Handler();
	private TextView mTvCountDown;
	private int mCurrentTimer = 5;
	private boolean mIsTimerRunning;
	private Toast mToast;
	//采用身份识别接口进行在线人脸识别
	private IdentityVerifier mIdVerifier;
	

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		//满屏幕显示
		getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,
				WindowManager.LayoutParams.FLAG_FULLSCREEN);
		//setContentView(R.layout.activity_video);
		mToast = Toast.makeText(this, "", Toast.LENGTH_SHORT);
		initUI();
		mhandler.post(timerRunnable);
		mIdVerifier = IdentityVerifier.createVerifier(RegisterFaceActivity.this, new InitListener() {
			@Override
			public void onInit(int errorCode) {
				/*if (ErrorCode.SUCCESS == errorCode) {
					showTip("引擎初始化成功");
				} else {
					showTip("引擎初始化失败，错误码：" + errorCode);
				}*/
			}
		});
		
	}

	private void showTip(final String str) {
		mToast.setText(str);
		mToast.show();
	}

	@SuppressLint("ShowToast")
	@SuppressWarnings("deprecation")
	private void initUI() {
		testImageView = (ImageView) findViewById(R.id.testImageView);
		imageView_preview = (ImageView) findViewById(R.id.imageView_preview);
		mTvCountDown = (TextView)findViewById(R.id.count_down);
		//button_select = (Button) findViewById(R.id.button_select);
		//button_select.setVisibility(View.VISIBLE);
		/*button_select.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent getAlbum = new Intent(Intent.ACTION_GET_CONTENT);
				getAlbum.setType("image*//*");
				startActivityForResult(getAlbum, PHOTO_REQUEST_GALLERY);
			}
		});*/
		//button_take_photos = (Button) findViewById(R.id.button_take_photos);
	/*	button_take_photos.setClickable(true);
		button_take_photos.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				button_take_photos.setClickable(false);
				
				Log.i("nihoa","nihoa");
			}
		});*/
		
	}
	private Runnable timerRunnable = new Runnable() {
		@Override
		public void run() {
			if (mCurrentTimer > 0) {
				mTvCountDown.setText(String.valueOf(mCurrentTimer));

				mCurrentTimer--;
				mhandler.postDelayed(timerRunnable, 1000);
			} else {
				mTvCountDown.setText("");
				mCameraId = CameraInfo.CAMERA_FACING_FRONT;
				openCamera();
				mCamera.startPreview();
				mCamera.takePicture(null, null, jpeg);
				//mCamera.takePicture(null, null, null, RegisterFaceActivity.this);
			

				mIsTimerRunning = false;
				mCurrentTimer = 5;
			}
		}
	};
	private boolean checkCameraPermission() {
		int status = checkPermission(permission.CAMERA, Process.myPid(), Process.myUid());
		return PackageManager.PERMISSION_GRANTED == status;

	}

	private void closeCamera() {
		if (null != mCamera) {
			mCamera.setPreviewCallback(null);
			mCamera.stopPreview();
			mCamera.release();
			mCamera = null;
		}
	}
	protected void openCamera() {
		if (null != mCamera) {
			return;
		}

		if (!checkCameraPermission()) {
			//	showTip("摄像头权限未打开，请打开后再试");
			mStopTrack = true;
			return;
		}

		// 只有一个摄相头，打开后置
		if (Camera.getNumberOfCameras() == 1) {
			mCameraId = CameraInfo.CAMERA_FACING_BACK;
		}

		try {
			mCamera = Camera.open(mCameraId);
			if (CameraInfo.CAMERA_FACING_FRONT == mCameraId) {
				//showTip("前置摄像头已开启，点击可切换");
			} else {
				//showTip("后置摄像头已开启，点击可切换");
			}
		} catch (Exception e) {
			e.printStackTrace();
			closeCamera();
			return;
		}

		Camera.Parameters params = mCamera.getParameters();
		params.setPreviewFormat(ImageFormat.NV21);
		params.setPreviewSize(PREVIEW_WIDTH, PREVIEW_HEIGHT);
		mCamera.setParameters(params);

		// 设置显示的偏转角度，大部分机器是顺时针90度，某些机器需要按情况设置
		mCamera.setDisplayOrientation(90);
		mCamera.setPreviewCallback(new Camera.PreviewCallback() {

			@Override
			public void onPreviewFrame(byte[] data, Camera camera) {
				System.arraycopy(data, 0, nv21, 0, data.length);
			}
		});

		try {
			mCamera.setPreviewDisplay(mPreviewSurface.getHolder());
			mCamera.startPreview();
		} catch (IOException e) {
			e.printStackTrace();
		}

		
	}
	Camera.PictureCallback jpeg = new Camera.PictureCallback() {
		@Override
		public void onPictureTaken(byte[] data, Camera camera) {
			Log.i(TAG, "onPictureTaken_data.length: " + data.length);
			String filename = "waitForRename.jpg";
			FileOutputStream fos;
			//获取拍到的图片Bitmap
			Bitmap bitmap_source = null;
			String pictureStoragePath = PictureUtil.getPictureStoragePath(getApplicationContext());
			File f = new File(pictureStoragePath, filename);
			try {
				fos = new FileOutputStream(f);
				if (data.length < 35000) {
					YuvImage image = new YuvImage(nv21, ImageFormat.NV21, PREVIEW_WIDTH, PREVIEW_HEIGHT, null);   //将NV21 data保存成YuvImage
					//图像压缩
					image.compressToJpeg(
							new Rect(0, 0, image.getWidth(), image.getHeight()),
							100, fos);
					Log.i(TAG, "onPictureTaken_data.length<20000: " + data.length);
					Log.i(TAG, "onPictureTaken_nv21.length: " + nv21.length);
					bitmap_source = PictureUtil.compressFacePhoto(f.getAbsolutePath());
					fos = new FileOutputStream(f);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					//旋转图片
					// 根据旋转角度，生成旋转矩阵
					Matrix matrix = new Matrix();
					if(mCameraId == CameraInfo.CAMERA_FACING_FRONT){
						matrix.postRotate(270);
					}else{
						matrix.postRotate(90);
					}
					Bitmap mBitmap1 = Bitmap.createBitmap(bitmap_source, 0, 0, bitmap_source.getWidth(), bitmap_source.getHeight(), matrix, true);
					testImageView.setImageBitmap(mBitmap1);
					boolean result = mBitmap1.compress(Bitmap.CompressFormat.JPEG, 100, bos);
					bos.close();
					Log.i(TAG, "onPictureTaken_mBitmap1.compress: " + result);
				} else {
					bitmap_source = PictureUtil.compressFacePhoto(data);
					BufferedOutputStream bos = new BufferedOutputStream(fos);
					//旋转图片
					// 根据旋转角度，生成旋转矩阵
					Matrix matrix = new Matrix();
					if(mCameraId == CameraInfo.CAMERA_FACING_FRONT){
						matrix.postRotate(270);
					}else{
						matrix.postRotate(90);
					}
					Bitmap mBitmap1 = Bitmap.createBitmap(bitmap_source, 0, 0, bitmap_source.getWidth(), bitmap_source.getHeight(), matrix, true);
					testImageView.setImageBitmap(mBitmap1);
					mBitmap1.compress(Bitmap.CompressFormat.JPEG, 70, bos);
					bos.close();
				}
				//fos.write(data);
				fos.flush();
				fos.close();

				Log.i(TAG, "onPictureTaken: 图片保存成功");
				
				mImageData = getBytes(pictureStoragePath+"/"+filename);
				rejisterByFace();
				
				
			} catch (Exception e) {
				System.out.println("图片保存异常" + e.getMessage());
				e.printStackTrace();
			}
		}
	};
	/**
	 * 获得指定文件的byte数组 
	 */
	private byte[] getBytes(String filePath){
		byte[] buffer = null;
		try {
			File file = new File(filePath);
			FileInputStream fis = new FileInputStream(file);
			ByteArrayOutputStream bos = new ByteArrayOutputStream(1000);
			byte[] b = new byte[1000];
			int n;
			while ((n = fis.read(b)) != -1) {
				bos.write(b, 0, n);
			}
			fis.close();
			bos.close();
			buffer = bos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return buffer;
	}
	private void rejisterByFace() {

		mAuthid = SPUtils.getString(getApplicationContext(),"login","");
		if (TextUtils.isEmpty(mAuthid)) {
			showTip("authid不能为空");

		}

		if (null != mImageData) {
		/*	mProDialog.setMessage("注册中...");
			mProDialog.show();*/
			// 设置用户标识，格式为6-18个字符（由字母、数字、下划线组成，不得以数字开头，不能包含空格）。
			// 当不设置时，云端将使用用户设备的设备ID来标识终端用户。
			// 设置人脸注册参数
			// 清空参数
			mIdVerifier.setParameter(SpeechConstant.PARAMS, null);
			// 设置会话场景
			mIdVerifier.setParameter(SpeechConstant.MFV_SCENES, "ifr");
			// 设置会话类型
			mIdVerifier.setParameter(SpeechConstant.MFV_SST, "enroll");
			// 设置用户id
			mIdVerifier.setParameter(SpeechConstant.AUTH_ID, mAuthid);
			// 设置监听器，开始会话
			mIdVerifier.startWorking(mEnrollListener);

			// 子业务执行参数，若无可以传空字符传
			StringBuffer params = new StringBuffer();
			// 向子业务写入数据，人脸数据可以一次写入
			mIdVerifier.writeData("ifr", params.toString(), mImageData, 0, mImageData.length);
			// 停止写入
			mIdVerifier.stopWrite("ifr");
		} else {
			showTip("请选择图片后再注册");
		}

	}
	/**
	 * 人脸注册监听器
	 */
	private IdentityListener mEnrollListener = new IdentityListener() {

		@Override
		public void onResult(IdentityResult result, boolean islast) {
			Log.d(TAG, result.getResultString());

			

			try {
				JSONObject object = new JSONObject(result.getResultString());
				int ret = object.getInt("ret");

				if (ErrorCode.SUCCESS == ret) {
					rejister();
					//showTip("注册成功");
				
				}else {
					showTip(new SpeechError(ret).getPlainDescription(true));
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

		@Override
		public void onEvent(int eventType, int arg1, int arg2, Bundle obj) {
		}

		@Override
		public void onError(SpeechError error) {
			
			showTip("你已经注册过了呦！");
			onBackPressed();
		}

	};
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		super.onActivityResult(requestCode, resultCode, data);
		switch (requestCode){
			case PHOTO_REQUEST_GALLERY:
				if(data!=null){
					startPhotoZoom(data.getData());
				}else{
					Log.i("onActivityResult: "," ");
				}
				break;
			case PHOTO_REQUEST_CUT:
				if(data!=null){
					if(data.getParcelableExtra("data") instanceof Bitmap){
						Bitmap bitmap = data.getParcelableExtra("data");
						String pictureStoragePath = PictureUtil.getPictureStoragePath(getApplicationContext());
						File file = new File(pictureStoragePath,"waitForRename.jpg");
						try {
							FileOutputStream fos = new FileOutputStream(file);
							imageView_preview.setImageBitmap(bitmap);
							imageView_preview.setVisibility(View.VISIBLE);
							bitmap.compress(Bitmap.CompressFormat.JPEG,80,fos);
							fos.flush();
							fos.close();
						} catch (IOException e) {
							e.printStackTrace();
						}
						Log.i(TAG, "onPictureTaken: 图片保存成功");
					/*	Intent intent = new Intent(getApplicationContext(), DialogInputNameActivity.class);
						startActivityForResult(intent, 0);*/
					}else{
						Log.i("onActivityResult: ","false");
					}
				}
				break;
			
			default:
				break;
		}

	}

	private void startPhotoZoom(Uri uri) {
		Intent intent = new Intent("com.android.camera.action.CROP");
		intent.setDataAndType(uri, "image/*");
		// crop为true是设置在开启的intent中设置显示的view可以剪裁
		intent.putExtra("crop", "true");

		// aspectX aspectY 是宽高的比例
		intent.putExtra("aspectX", 3);
		intent.putExtra("aspectY", 4);

		// outputX,outputY 是剪裁图片的宽高
		intent.putExtra("outputX", 300);
		intent.putExtra("outputY", 400);
		intent.putExtra("return-data", true);
		intent.putExtra("noFaceDetection", true);

		startActivityForResult(intent, PHOTO_REQUEST_CUT);
	}



	@Override
	protected void onResume() {
		super.onResume();
		//button_take_photos.setClickable(true);
	}

	@Override
	protected void onPause() {
		super.onPause();
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}

	private void showNormalDialog(String title, String message, boolean cancel, View v, boolean haveButton) {
		AlertDialog.Builder builder = new AlertDialog.Builder(this);
		builder.setCancelable(cancel);
		if (title != null) {
			builder.setTitle(title);
		} else {
			builder.setTitle("温馨提示");
		}
		builder.setMessage(message);
		if (v != null) {
			builder.setView(v);
		}
		if (haveButton) {
			builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
				@Override
				public void onClick(DialogInterface dialogInterface, int i) {
					dialog.dismiss();
					finish();
				}
			});
		}
		dialog = builder.show();
	}
	/**
	 * 刷脸注册
	 */
	private void rejister() {

		if (!NetUtil.isNetworkAvailable(this)) {
			ToastUtil.showToast(this, "网络连接失败，请检查网络...", Toast.LENGTH_SHORT);
			return;
		}

		String url = AppConstant.BASEURL2 + "api/store/face";
		String timeStamp = String.valueOf(new Date().getTime());
		String randomNumberTen = RandomUtil.getRandomNumberTen();
		String accessToken = SPUtils.getString(this, "accessToken", "");
		Map<String, String> map = new HashMap<String, String>();
		map.clear();
		map.put("Timestamp", timeStamp);
		map.put("Nonce", randomNumberTen);
		map.put("AppId", AppConstant.appid_value);

		String signValue = StringUtil.getSignValue(map);





		OkHttpUtils.post().url(url).addHeader(AppConstant.HEADER_NAME, AppConstant.HEADER_VERSION)
				.addHeader("Authorization", accessToken)
				.addParams("Timestamp", timeStamp)
				.addParams("Nonce", randomNumberTen)
				.addParams("AppId", AppConstant.appid_value)
				.addParams("Sign", signValue)
				.build().execute(new StringCallback() {

			private String strJson;

			@Override
			public void onResponse(String response) {

				try{
					byte[] decode = Base64.decode(response);//base64解码
					strJson = UTF8Util.getString(decode);
					JSONObject jsonObject = new JSONObject(strJson);
//                       
					String msg = jsonObject.getString("Msg");
					if(msg.equals("成功")){
						showTip("注册成功");
					
					}
					else{
						showTip("注册失败");
					}
					onBackPressed();
				}catch (JSONException e){
					e.getStackTrace();
				}


			}

			@Override
			public void onError(Request request, Exception e) {
				LogUtil.e("login", e.toString());
			}
		});
	}
}


