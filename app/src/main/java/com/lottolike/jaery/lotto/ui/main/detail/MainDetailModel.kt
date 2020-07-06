package com.lottolike.jaery.lotto.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lottolike.jaery.lotto.lotto.domain.LottoNumberInfo
import com.lottolike.jaery.lotto.lotto.domain.LottoRankInfo
import com.lottolike.jaery.lotto.lotto.util.LottoUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainDetailModel : ViewModel() {
    private val _lottoRound = MutableLiveData<Int>(0)
    private val _numberArray = MutableLiveData<ArrayList<Int>>(arrayListOf(0, 0, 0, 0, 0, 0, 0))
    private val _numberRank = MutableLiveData<ArrayList<LottoRankInfo>>(arrayListOf())

    var lottoRound : LiveData<Int> = _lottoRound
    var numberArray : LiveData<ArrayList<Int>> = _numberArray
    var numberRank : LiveData<ArrayList<LottoRankInfo>> = _numberRank


    fun initLottoNumber()
    {
        CoroutineScope(Dispatchers.Main).launch {
            val info : LottoNumberInfo = LottoUtil.getLottoNumberInfo()

            _lottoRound.postValue(info.round)

            val numberArray : ArrayList<Int> = arrayListOf()
            val numbers  = info.numbers.replace("+",",").split(",")

            for (index in numbers.indices) {
                    numberArray.add(numbers[index].toInt())
            }

            _numberArray.postValue(numberArray)
        }
    }

    fun initLottoRanK() {
        CoroutineScope(Dispatchers.Main).launch {
           _numberRank.postValue(LottoUtil.getLottoRankInfo())
        }
    }
}