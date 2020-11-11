package com.lottolike.jaery.lotto.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lottolike.jaery.lotto.data.OfficialLottoData
import com.lottolike.jaery.lotto.data.domain.LottoNumberInfo
import com.lottolike.jaery.lotto.data.domain.LottoRankInfo
import com.lottolike.jaery.lotto.data.util.LottoUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainDetailModel : ViewModel() {

    private val _officialLottoData : MutableLiveData<List<OfficialLottoData>> = MutableLiveData()

    var officialLottoData : LiveData<List<OfficialLottoData>> = _officialLottoData


    fun loadOfficialLottoData()
    {
        CoroutineScope(Dispatchers.Main).launch {
            val data : List<OfficialLottoData>? = LottoUtil.getLottoInfo()

            data?.let {
                _officialLottoData.postValue(data)
            }
        }
    }

}