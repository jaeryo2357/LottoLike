package com.lottolike.jaery.lotto.util


import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.lottolike.jaery.lotto.data.util.LottoUtil


@BindingAdapter("app:lotto_background")
fun lottoBackground(view : TextView, number : Int) {
    view.setBackgroundResource(LottoUtil.getLottoBackgroundColor(number))
}

