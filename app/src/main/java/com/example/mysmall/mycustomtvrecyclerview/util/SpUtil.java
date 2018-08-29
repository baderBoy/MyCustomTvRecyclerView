package com.example.mysmall.mycustomtvrecyclerview.util;

import android.content.Context;
import android.content.SharedPreferences;

import com.example.mysmall.mycustomtvrecyclerview.Common.TvRecyclerViewApplication;

public class SpUtil {
    private static SharedPreferences mSp;
    private static final Context mContext = TvRecyclerViewApplication.mContext;

    private static SharedPreferences checkoutSP() {
        if (mSp == null) {
            mSp = mContext.getSharedPreferences("config", Context.MODE_PRIVATE);
        }

        return mSp;
    }

    public static void putString(String key, String value) {
        mSp = checkoutSP();
        mSp.edit().putString(key, value).apply();
    }

    public static String getString(String key, String defaultValue) {
        mSp = checkoutSP();
        return mSp.getString(key, defaultValue);
    }

    public static void putBoolean(String key, boolean value) {
        mSp = checkoutSP();
        mSp.edit().putBoolean(key, value).apply();
    }

    public static boolean getBoolean(String key, boolean defaultVaule) {
        mSp = checkoutSP();
        return mSp.getBoolean(key,defaultVaule);
    }

    public static void putInt(String key, int value) {
        mSp = checkoutSP();
        mSp.edit().putInt(key, value).apply();
    }

    public static int getInt(String key, int defaultValue) {
        mSp = checkoutSP();
        return mSp.getInt(key, defaultValue);
    }

}
