package com.lottolike.jaery.Lotto.Database;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class BasicDB {

    static final String PREF_ROTTO = "LottoNo";
    static final String APP_INIT = "init";
    static final String PREF_RECOMMEND_LOTTO = "recommend_Lotto";
    static final String Alert_vibration = "vibration";
    static final String Alert_sound = "sound";

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
        return getSharedPreferences(ctx).getInt(PREF_ROTTO, 700);
    }


    // 계정 정보 저장
    public static void setAlert_sound(Context ctx, boolean ischeck) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(Alert_sound, ischeck);
        editor.apply();
    }

    // 저장된 정보 가져오기
    public static boolean getAlert_sound(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(Alert_sound, false);
    }

    // 계정 정보 저장
    public static void setAlert_vibration(Context ctx, boolean ischeck) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putBoolean(Alert_vibration, ischeck);
        editor.apply();
    }

    // 저장된 정보 가져오기
    public static boolean getAlert_vibradtion(Context ctx) {
        return getSharedPreferences(ctx).getBoolean(Alert_vibration, false);
    }

    // 계정 정보 저장
    public static void setRecommend(Context ctx, String recommend) {
        SharedPreferences.Editor editor = getSharedPreferences(ctx).edit();
        editor.putString(PREF_RECOMMEND_LOTTO, recommend);
        editor.apply();
    }

    // 저장된 정보 가져오기
    public static String getRecommend(Context ctx) {
        return getSharedPreferences(ctx).getString(PREF_RECOMMEND_LOTTO,"");
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