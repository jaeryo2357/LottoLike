package com.lottolike.jaery.lotto.lotto.db

import android.content.Context
import android.content.SharedPreferences
 
class LottoPreferences(context: Context) {
    private final val PREFS_FILENAME = "appName.prefs"
    private final val LOTTO_NUM = "LottoNo"
    private final val LOTTO_DATE = "LottoDate"
    private final val SOUND = "sound"
    private final val VIBRATION = "vibration"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)
    
    var sound: Boolean
        get () = prefs.getBoolean(SOUND, true)
        set(value) = prefs.edit().putBoolean(SOUND, value).apply()
 
    var vibration: Boolean
        get () = prefs.getBoolean(VIBRATION, true)
        set(value) = prefs.edit().putBoolean(VIBRATION, value).apply()

    var lottoNumber: Int
        get () = prefs.getInt(LOTTO_NUM, -1);
        set(value) = prefs.edit().putInt(LOTTO_NUM, value).apply()

    var lottoDate: String
        get() = prefs.getString(LOTTO_DATE, "")!!;
        set(value) = prefs.edit().putString(LOTTO_DATE, value).apply()
}