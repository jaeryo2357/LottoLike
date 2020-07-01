package com.lottolike.jaery.lotto.ui.main.detail

import android.graphics.Color
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProvider
import com.google.android.gms.ads.AdRequest
import com.lottolike.jaery.lotto.R
import com.lottolike.jaery.lotto.databinding.ActivityLottoDetailBinding
import com.lottolike.jaery.lotto.util.FirebaseExt
import com.lottolike.jaery.lotto.util.LottoItem
import com.lottolike.jaery.lotto.util.SharedPreferences
import kotlinx.android.synthetic.main.activity_lotto_detail.*
import kotlin.math.roundToInt

class MainDetailController : AppCompatActivity() {

    private val viewModel by lazy{ ViewModelProvider(this@MainDetailController).get(MainDetailModel::class.java)}
    private lateinit var binding: ActivityLottoDetailBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this, R.layout.activity_lotto_detail)
        binding.run {
            lifecycleOwner = this@MainDetailController
            vm = viewModel
        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        lotto_detail_back.setOnClickListener {
            finishAfterTransition()
        }
        viewModel.initLottoNumber()

        viewModel.initLottoRanK()

        changeBackColor()
    }

    private fun changeBackColor() {
        lotto_detail_back.setColorFilter(Color.WHITE)
    }

}
