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

class MainModel {

    var _lottoNumbers : MutableLiveData<String> = MutableLiveData()
    var _lottoRound : MutableLiveData<Int> = MutableLiveData()
    var _lottoRoundDate : MutableLiveData<String> = MutableLiveData()
    var _recommendLotto : MutableLiveData<ArrayList<Int>> = MutableLiveData()

    var lottoNumbers : LiveData<String> = _lottoNumbers
    var lottoRound : LiveData<Int> = _lottoRound
    var lottoRoundDate : LiveData<String> = _lottoRoundDate
    var recommendLotto : LiveData<ArrayList<Int>> = _recommendLotto

    public fun changeLottoInfo(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {
            val pref = LottoPreferences.getInstance(context)

            val info : LottoNumberInfo = LottoUtil.getLottoNumberInfo()
            _lottoRound.postValue(info.round)
            _lottoNumbers.postValue(info.numbers)

            val date : String = LottoUtil.getLottoRoundDate()
            _lottoRoundDate.postValue(date)


            //이전과 로또 회차와 날짜가 달라질 경우 DB에 있는 번호 목록 재채점
            if (info.round != pref.lottoNumber || date != pref.lottoDate) {
                pref.lottoNumber = info.round
                pref.lottoDate = date

                //채점
                val lottoDB : LottoDB = LottoDB.getInstance(context)
                lottoDB.myListCheck(info.numbers, info.money)
            }
        }
    }

    public fun changeRecommendLotto() {
        val recommendLottoNumber = LottoUtil.getRecommendLotto()
        _recommendLotto.postValue(recommendLottoNumber)
    }
}