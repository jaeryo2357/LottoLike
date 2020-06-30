package com.lottolike.jaery.lotto.ui.detail

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.lottolike.jaery.lotto.R
import com.lottolike.jaery.lotto.databinding.ActivityLottoDetailBinding
import com.lottolike.jaery.lotto.lotto.domain.LottoRankInfo
import com.lottolike.jaery.lotto.util.FirebaseExt
import com.lottolike.jaery.lotto.util.LottoItem
import com.lottolike.jaery.lotto.util.SharedPreferences
import kotlinx.android.synthetic.main.activity_lotto_detail.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import kotlin.math.roundToInt

class LottoDetailActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences by lazy{ SharedPreferences(this@LottoDetailActivity)}
    private val viewModel by lazy{ ViewModelProvider(this@LottoDetailActivity).get(DetailViewModel::class.java)}
    private lateinit var binding: ActivityLottoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lotto_detail)
        binding.run {
            lifecycleOwner = this@LottoDetailActivity
            vm = viewModel
        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        lotto_detail_back.setOnClickListener {
            finishAfterTransition()
        }
        viewModel.setLottoNumber(sharedPreferences.lottoNumber,LottoItem.GetNumber(this, sharedPreferences.lottoNumber))

        detail_chart.apply{
            animation.duration = animationDuration
            labelsFormatter = { "${it.roundToInt()}"}
        }
        FirebaseExt.getLottoRank(sharedPreferences.lottoNumber){ success, hash ->
            if(success)detail_chart.animate(hash!!)
        }
    }

    companion object {
        private const val animationDuration = 1000L
    }
}
