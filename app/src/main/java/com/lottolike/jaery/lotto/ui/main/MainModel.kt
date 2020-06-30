package com.lottolike.jaery.lotto.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lottolike.jaery.lotto.lotto.domain.LottoNumberInfo
import com.lottolike.jaery.lotto.lotto.util.LottoUtil
import com.lottolike.jaery.lotto.util.Database.LottoDB
import com.lottolike.jaery.lotto.util.SharedPreferences
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.launch

class MainModel {
    private lateinit var sharedPreferences : SharedPreferences;
    private lateinit var lottoDB : LottoDB

    lateinit var _lottoNumbers : MutableLiveData<String>
    lateinit var _lottoRound : MutableLiveData<Int>
    var lottoNumbers : LiveData<String> = _lottoNumbers
    var lottoRound : LiveData<Int> = _lottoRound

    constructor(context : Context) {
        sharedPreferences = SharedPreferences(context)
        lottoDB = LottoDB(context)
        lottoDB.open()
    }

    public fun changeLottoInfo() {
        CoroutineScope(Dispatchers.Main).launch {
            val info : LottoNumberInfo = LottoUtil.getLottoNumberInfo()

            _lottoRound.postValue(info.round)
            _lottoNumbers.postValue(info.numbers)
        }
    }

    public fun onDestroy() {
        lottoDB.close()
    }
}