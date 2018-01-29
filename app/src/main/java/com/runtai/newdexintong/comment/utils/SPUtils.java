package com.runtai.newdexintong.comment.utils;

import android.content.Context;
import android.content.SharedPreferences;

public class SPUtils {
    private static final String CONFIG = "config";
    private static SharedPreferences sp;

    public static boolean getBoolean(Context paramContext, String paramString, boolean paramBoolean) {
        if (sp == null)
            sp = paramContext.getSharedPreferences("config", 0);
        return sp.getBoolean(paramString, paramBoolean);
    }

    public static int getInt(Context paramContext, String paramString, int paramInt) {
        if (sp == null)
            sp = paramContext.getSharedPreferences("config", 0);
        return sp.getInt(paramString, paramInt);
    }

    public static String getString(Context paramContext, String paramString1, String paramString2) {
        if (sp == null)
            sp = paramContext.getSharedPreferences("config", 0);
        return sp.getString(paramString1, paramString2);
    }

    public static void putBoolean(Context paramContext, String paramString, boolean paramBoolean) {
        if (sp == null)
            sp = paramContext.getSharedPreferences("config", 0);
        sp.edit().putBoolean(paramString, paramBoolean).apply();
    }

    public static void putInt(Context paramContext, String paramString, int paramInt) {
        if (sp == null)
            sp = paramContext.getSharedPreferences("config", 0);
        sp.edit().putInt(paramString, paramInt).apply();
    }

    public static void putString(Context paramContext, String paramString1, String paramString2) {
        if (sp == null)
            sp = paramContext.getSharedPreferences("config", 0);
        sp.edit().putString(paramString1, paramString2).apply();
    }

   
}

