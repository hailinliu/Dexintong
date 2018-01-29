package com.runtai.zxinglib;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.RelativeLayout;
import android.widget.TextView;


public class MainActivity extends Activity {

    TextView text_size;
    Button create_code, scan_2code, scan_bar_code, scan_code;
    RelativeLayout clean;
    Intent intent;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        text_size = (TextView) findViewById(R.id.text_size);
//
//        create_code = (Button) findViewById(R.id.create_code);
//        scan_2code = (Button) findViewById(R.id.scan_2code);
//        scan_bar_code = (Button) findViewById(R.id.scan_bar_code);
//        scan_code = (Button) findViewById(R.id.scan_code);
//        clean = (RelativeLayout) findViewById(R.id.clean);
//        
//        create_code.setOnClickListener(this);
//        scan_2code.setOnClickListener(this);
//        scan_bar_code.setOnClickListener(this);
//        scan_code.setOnClickListener(this);
//        clean.setOnClickListener(this);
    }

//    private void getSize() {
//        String sizes = DataCleanManager.getTotalCacheSize(MainActivity.this);
//        text_size.setText(sizes);
//    }
//
//    @Override
//    protected void onStart() {
//        super.onStart();
//        getSize();
//    }
//
//    @Override
//    public void onClick(View view) {
//
//        int i = view.getId();
//        if (i == R.id.create_code) {
//            intent = new Intent(this, CreateCodeActivity.class);
//            startActivity(intent);
//
//        } else if (i == R.id.scan_2code) {
//            intent = new Intent(this, CommonScanActivity.class);
//            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_QRCODE_MODE);
//            startActivity(intent);
//
//        } else if (i == R.id.scan_bar_code) {
//            intent = new Intent(this, CommonScanActivity.class);
//            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_BARCODE_MODE);
//            startActivity(intent);
//
//        } else if (i == R.id.scan_code) {
//            intent = new Intent(this, CommonScanActivity.class);
//            intent.putExtra(Constant.REQUEST_SCAN_MODE, Constant.REQUEST_SCAN_MODE_ALL_MODE);
//            startActivity(intent);
//
//        } else if (i == R.id.clean) {
//            DataCleanManager.clearAllCache(MainActivity.this);
//            getSize();
//
//        }
//    }
}
