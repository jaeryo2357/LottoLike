package com.lottolike.jaery.lotto.ui.main

import android.content.Context
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.lottolike.jaery.lotto.data.OfficialLottoData

import com.lottolike.jaery.lotto.data.db.LottoPreferences

import com.lottolike.jaery.lotto.data.util.LottoUtil

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class MainModel {

    private var _officialLottoData: MutableLiveData<OfficialLottoData> = MutableLiveData()
    private var _recommendLotto: MutableLiveData<ArrayList<Int>> = MutableLiveData()

    var officialLottoData: LiveData<OfficialLottoData> = _officialLottoData
    var recommendLotto: LiveData<ArrayList<Int>> = _recommendLotto

    public fun changeLottoInfo(context: Context) {
        CoroutineScope(Dispatchers.Main).launch {


            val info: OfficialLottoData = withContext(Dispatchers.IO) {
                LottoUtil.getLottoInfo()
            }
            info?.let {
                val pref = LottoPreferences.getInstance(context)

                pref.lottoRound = it.lottoRound

                pref.lottoNumber = it.officialLottoNumber

                pref.lottoDate = it.lottoDate

                _officialLottoData.postValue(it)
            }
        }

    }

    public fun changeRecommendLotto() {
        val recommendLottoNumber = LottoUtil.getRecommendLotto()
        _recommendLotto.postValue(recommendLottoNumber)
    }
}