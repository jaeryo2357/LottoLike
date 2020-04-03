package com.lottolike.jaery.lotto.util


import android.widget.TextView
import androidx.databinding.BindingAdapter


@BindingAdapter("app:lotto_background")
fun lottoBackground(view : TextView, number : Int)
{
    view.setBackgroundResource(LottoItem.GetBackgroundColor(number))
}