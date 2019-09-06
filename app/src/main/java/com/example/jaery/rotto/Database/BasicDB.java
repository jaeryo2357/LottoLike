package com.example.jaery.rotto.Database;

import android.app.Activity;
import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BasicDB {

    static final String PREF_ROTTO = "RottoNo";
    static final String APP_INIT = "init";


    public static SharedPreferences getSharedPreferences(Context ctx) {
        return PreferenceManager.getDefaultSharedPreferences(ctx);
    }

    // 계정 정보 저장
    public static void setRottoN(Context ctx, int N0) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putInt(PREF_ROTTO, N0);
        editor.apply();
    }

    // 저장된 정보 가져오기
    public static int getRottoN(Context ctx) {
        return getSharedPreferences(ctx).getInt(PREF_ROTTO, 200);
    }


    // 초기 설정
    public static void setInit(Context ctx, boolean init) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(APP_INIT, init);
        editor.apply();
    }

    // 저장된 정보 가져오기
    public static boolean getInit(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(APP_INIT, false);
    }


    // 정보 삭제
    public static void clearRottoN(Context ctx) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.clear();
        editor.apply();
    }
}