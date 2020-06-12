package com.lottolike.jaery.lotto.ui.detail

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
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import kotlin.math.roundToInt

class LottoDetailActivity : AppCompatActivity() {

    private val sharedPreferences: SharedPreferences by lazy{ SharedPreferences(this@LottoDetailActivity)}
    private val viewModel by lazy{ ViewModelProvider(this@LottoDetailActivity).get(DetailViewModel::class.java)}
    lateinit var binding: ActivityLottoDetailBinding
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = DataBindingUtil.setContentView(this,R.layout.activity_lotto_detail)
        binding.run {
            lifecycleOwner = this@LottoDetailActivity
            vm = viewModel
        }
        val adRequest = AdRequest.Builder().build()
        adView.loadAd(adRequest)

        lotto_detail_back.setOnClickListener {
            finishAfterTransition()
        }
        viewModel.setLottoNumber(sharedPreferences.lottoNumber,LottoItem.GetNumber(this,sharedPreferences.lottoNumber))

        detail_chart.apply{
            animation.duration = animationDuration
            labelsFormatter = { "${it.roundToInt()}"}
        }
        FirebaseExt.getLottoRank(sharedPreferences.lottoNumber){ success,hash ->
            if(success)detail_chart.animate(hash!!)
        }
        getLottoInfo()
    }

    private fun getLottoInfo(){
        CoroutineScope(Dispatchers.IO).launch {
            try {
                val url = "https://dhlottery.co.kr/gameResult.do?method=byWin"
                val doc = Jsoup.connect(url).timeout(1000 * 10).get()  //타임아웃 10초
                val contentData: Elements = doc.select("table tbody tr")

                for (data in contentData) {
                    val element = data.select("td")
                    val lank = element[0].text() //순위 : 1등, 2등...
                    val money = element[3].text() //등위별 당첨금액
                    val person = element[2].text() //당첨된 사람의 수

                    when (lank) {
                        "1등" -> binding.info1 = LottoInfo(money, person)
                        "2등" -> binding.info2 = LottoInfo(money, person)
                        "3등" -> binding.info3 = LottoInfo(money, person)
                        "4등" -> binding.info4 = LottoInfo(money, person)
                        else -> binding.info5 = LottoInfo(money, person)
                    }
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
        }
    }

    companion object {
        private const val animationDuration = 1000L
    }
}
