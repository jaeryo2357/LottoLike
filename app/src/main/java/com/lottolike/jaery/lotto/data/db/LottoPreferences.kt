package com.lottolike.jaery.lotto.data.db

import android.content.Context
import android.content.SharedPreferences
 
class LottoPreferences private constructor(context: Context) {
    private final val PREFS_FILENAME = "appName.prefs"
    private final val LOTTO_ROUND = "LottoRound"
    private final val LOTTO_DATE = "LottoDate"
    private final val LOTTO_NUBMER = "LottoNumber"
    private final val SOUND = "sound"
    private final val VIBRATION = "vibration"

    private val prefs: SharedPreferences = context.getSharedPreferences(PREFS_FILENAME, 0)

    var sound: Boolean
        get () = prefs.getBoolean(SOUND, true)
        set(value) = prefs.edit().putBoolean(SOUND, value).apply()
 
    var vibration: Boolean
        get () = prefs.getBoolean(VIBRATION, true)
        set(value) = prefs.edit().putBoolean(VIBRATION, value).apply()

    var lottoRound: Int
        get () = prefs.getInt(LOTTO_ROUND, -1);
        set(value) = prefs.edit().putInt(LOTTO_ROUND, value).apply()

    var lottoDate: String
        get() = prefs.getString(LOTTO_DATE, "")!!;
        set(value) = prefs.edit().putString(LOTTO_DATE, value).apply()

    var lottoNumber: String
        get() = prefs.getString(LOTTO_NUBMER, "")!!;
        set(value) = prefs.edit().putString(LOTTO_NUBMER, value).apply()

    companion object {
        private var instance : LottoPreferences? = null

        fun getInstance(context : Context) : LottoPreferences =
            instance ?: synchronized(context) {
                instance ?: LottoPreferences(context)
            }
    }
}