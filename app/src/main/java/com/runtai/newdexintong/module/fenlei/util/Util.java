package com.runtai.newdexintong.module.fenlei.util;

import android.content.Context;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

public class Util {

    private Context context;

    public Util(Context context) {
        this.context = context;
    }

    public void hintKeyBoard(View view) {
//		InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);  
//		imm.toggleSoftInput(0, InputMethodManager.HIDE_NOT_ALWAYS);
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager.isActive()) {
            inputMethodManager.hideSoftInputFromWindow(view.getApplicationWindowToken(), 0);
        }
    }

}
