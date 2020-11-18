package com.lottolike.jaery.lotto.ui.main.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData
import com.lottolike.jaery.lotto.data.util.LottoUtil
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainDetailModel : ViewModel() {

    private val _officialLottoMainData : MutableLiveData<List<OfficialLottoMainData>> = MutableLiveData()

    var officialLottoMainData : LiveData<List<OfficialLottoMainData>> = _officialLottoMainData


    fun loadOfficialLottoData()
    {
        CoroutineScope(Dispatchers.Main).launch {
            val data : List<OfficialLottoMainData>? = LottoUtil.getLottoInfo()

            data?.let {
                _officialLottoMainData.postValue(data)
            }
        }
    }

}