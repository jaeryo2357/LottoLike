package com.lottolike.jaery.lotto.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

import com.lottolike.jaery.lotto.lotto.db.LottoDB
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences

import com.lottolike.jaery.lotto.lotto.domain.LottoNumberInfo
import com.lottolike.jaery.lotto.lotto.util.LottoUtil

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainModel {

    var _lottoNumbers: MutableLiveData<String> = MutableLiveData()
    var _lottoRound: MutableLiveData<Int> = MutableLiveData()
    var _lottoRoundDate: MutableLiveData<String> = MutableLiveData()
    var _recommendLotto: MutableLiveData<ArrayList<Int>> = MutableLiveData()

    var lottoNumbers: LiveData<String> = _lottoNumbers
    var lottoRound: LiveData<Int> = _lottoRound
    var lottoRoundDate: LiveData<String> = _lottoRoundDate
    var recommendLotto: LiveData<ArrayList<Int>> = _recommendLotto

    public fun changeLottoInfo(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {


            val info: LottoNumberInfo? = withContext(Dispatchers.IO) {
                LottoUtil.getLottoInfo()
            }
            info?.let {
                val pref = LottoPreferences.getInstance(context)

                pref.lottoRound = it.round
                _lottoRound.postValue(it.round)

                pref.lottoNumber = it.numbers
                _lottoNumbers.postValue(it.numbers)

                pref.lottoDate = it.date
                _lottoRoundDate.postValue(it.date)
            }
        }

//        CoroutineScope(Dispatchers.Default).launch {
//
//            val info: LottoNumberInfo = withContext(Dispatchers.IO) {
//                LottoUtil.getLottoNumberInfo()
//            }
//            val rankInfo = withContext(Dispatchers.IO) {
//                LottoUtil.getLottoRankInfo()
//            }
//
//            val pref = LottoPreferences.getInstance(context)
//
//            if (info.round != pref.lottoNumber || info.date != pref.lottoDate) {
//
//            }
//
//            pref.lottoNumber = info.round
//            pref.lottoDate = date
//
//            //채점
//            val lottoDB: LottoDB = LottoDB.getInstance(context)
//            lottoDB.myListCheck(info.numbers, rankInfo)
//        }
    }

    public fun changeRecommendLotto() {
        val recommendLottoNumber = LottoUtil.getRecommendLotto()
        _recommendLotto.postValue(recommendLottoNumber)
    }
}