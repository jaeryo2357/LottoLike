package com.lottolike.jaery.lotto.view.detail

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelProviders
import com.lottolike.jaery.lotto.R
import com.lottolike.jaery.lotto.databinding.ActivityLottoDetailBinding
import com.lottolike.jaery.lotto.databinding.ActivityLottoDetailBindingImpl
import com.lottolike.jaery.lotto.util.FirebaseExt
import com.lottolike.jaery.lotto.util.LottoItem
import com.lottolike.jaery.lotto.util.SharedPreferences
import kotlinx.android.synthetic.main.activity_lotto_detail.*
import kotlin.math.roundToInt

class LottoDetailActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences by lazy{ SharedPreferences(this@LottoDetailActivity)}
    private val viewModel by lazy{ ViewModelProvider(this@LottoDetailActivity).get(DetailViewModel::class.java)}
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding: ActivityLottoDetailBinding =
                DataBindingUtil.setContentView(this,R.layout.activity_lotto_detail)
        binding.run {
            lifecycleOwner = this@LottoDetailActivity
            vm = viewModel
        }
        lotto_detail_back.setOnClickListener {
            finishAfterTransition()
        }
        viewModel.setLottoInfo(sharedPreferences.lottoNumber,LottoItem.GetNumber(this,sharedPreferences.lottoNumber))

        detail_chart.apply{
            animation.duration = animationDuration
            labelsFormatter = { "${it.roundToInt()}"}
        }
        FirebaseExt.getLottoRank(sharedPreferences.lottoNumber){ success,hash ->
            if(success)detail_chart.animate(hash!!)
        }
    }

    companion object {
        private const val animationDuration = 1000L
    }
}
