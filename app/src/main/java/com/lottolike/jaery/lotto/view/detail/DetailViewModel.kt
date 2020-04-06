package com.lottolike.jaery.lotto.view.detail

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import java.text.DecimalFormat

class DetailViewModel : ViewModel() {
    private val _lottoNum = MutableLiveData<Int>(0)
    private val _numberOne = MutableLiveData<Int>(0)
    private val _numberTwo = MutableLiveData<Int>(0)
    private val _numberTrd = MutableLiveData<Int>(0)
    private val _numberFor = MutableLiveData<Int>(0)
    private val _numberFiv = MutableLiveData<Int>(0)
    private val _numberSix = MutableLiveData<Int>(0)
    private val _numberBus = MutableLiveData<Int>(0)
    private val _winner = MutableLiveData("")

    var lottoNum : LiveData<Int> = _lottoNum
    var numberOne : LiveData<Int> = _numberOne
    var numberTwo : LiveData<Int> = _numberTwo
    var numberTrd : LiveData<Int> = _numberTrd
    var numberFor : LiveData<Int> = _numberFor
    var numberFiv : LiveData<Int> = _numberFiv
    var numberSix : LiveData<Int> = _numberSix
    var numberBus : LiveData<Int> = _numberBus
    var winner : LiveData<String> = _winner

    fun setLottoInfo(lottoN : Int,hash : HashMap<String,String>)
    {
        _lottoNum.postValue(lottoN)
        _numberOne.postValue(hash["N1"]!!.toInt())
        _numberTwo.postValue(hash["N2"]!!.toInt())
        _numberTrd.postValue(hash["N3"]!!.toInt())
        _numberFor.postValue(hash["N4"]!!.toInt())
        _numberFiv.postValue(hash["N5"]!!.toInt())
        _numberSix.postValue(hash["N6"]!!.toInt())
        _numberBus.postValue(hash["bonusNo"]!!.toInt())

        val winnerMoney: Long = hash["winner"]!!.toLong()
        val format = DecimalFormat("###,###")
        _winner.postValue("${format.format(winnerMoney)}원")
    }
}