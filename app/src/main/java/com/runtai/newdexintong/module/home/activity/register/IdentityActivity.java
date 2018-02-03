package com.runtai.newdexintong.module.home.activity.register;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;

import com.runtai.newdexintong.R;
import com.runtai.newdexintong.comment.activity.BaseActivity;

public class IdentityActivity extends BaseActivity {

  
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_identity);
        RelativeLayout head_back = (RelativeLayout)findViewById(R.id.head_back);
        head_back.setOnClickListener(this);
        RelativeLayout Identity_rl_faceidentity = (RelativeLayout)findViewById(R.id.Identity_rl_faceidentity);
        Identity_rl_faceidentity.setOnClickListener(this);
        RelativeLayout Identity_rl_voiceidentity = (RelativeLayout)findViewById(R.id.Identity_rl_voiceidentity);
        Identity_rl_voiceidentity.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.head_back:
                onBackPressed();
                break;
            case R.id.Identity_rl_faceidentity://人脸注册
                Intent intent = new Intent(this, RegisterFaceActivity.class);
                startActivityByIntent(intent);
                break;
            case R.id.Identity_rl_voiceidentity://声音注册
                Intent mintent = new Intent(this, VocalVerifyDemo.class);
                startActivityByIntent(mintent);
                break;
        }
    }
}
