package com.lottolike.jaery.lotto.util

import android.content.Context
import android.content.SharedPreferences
 
class SharedPreferences(context: Context) {
    private val PREFS_FILENAME = "appName.prefs"
    private val LOTTO_N = "LottoNo"
    private val RECOMMEND = "recommend_Lotto" //추천번호
    private val SOUND = "sound"
    private val VIBRATION = "vibration"
    private val APP_INIT = "init"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    
    var sound: Boolean
        get () = prefs.getBoolean(SOUND, true)
        set(value) = prefs.edit().putBoolean(SOUND, value).apply()
 
    var vibration: Boolean
        get () = prefs.getBoolean(VIBRATION, true)
        set(value) = prefs.edit().putBoolean(VIBRATION, value).apply()

    var init: Boolean
        get () = prefs.getBoolean(APP_INIT, false)
        set(value) = prefs.edit().putBoolean(APP_INIT, value).apply()

    var recommend: String
        get () = prefs.getString(RECOMMEND,"")!!
        set(value) = prefs.edit().putString(RECOMMEND, value).apply()

    var lottoNumber: Int
        get () = prefs.getInt(LOTTO_N,700)
        set(value) = prefs.edit().putInt(LOTTO_N, value).apply()
}