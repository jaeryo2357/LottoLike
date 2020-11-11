package com.lottolike.jaery.lotto.data.util

import com.lottolike.jaery.lotto.R
import com.lottolike.jaery.lotto.data.domain.LottoNumberInfo
import com.lottolike.jaery.lotto.data.domain.LottoRankInfo
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.util.*

object LottoUtil {
    public fun getRecommendLotto(): ArrayList<Int> {
        val numbers = ArrayList<Int>()

        numbers.add((Math.random() * 45).toInt() + 1)

        var count = 1;
        while (count < 6) {
            val randomNumber = (Math.random() * 45).toInt() + 1
            if (!numbers.contains(randomNumber)) {
                numbers.add(randomNumber)
                count++
            }
        }

        numbers.sort()

        return numbers
    }

    public fun getLottoBackgroundColor(number : Int) : Int {
        val color : Int

        when (number) {
            in 1..10 -> {
                color = R.drawable.yellow_circle;
            }

            in 11..20 -> {
                color = R.drawable.blue_circle
            }

            in 21..30 -> {
                color = R.drawable.red_circle
            }

            in 31..40 -> {
                color = R.drawable.shadow_circle
            }

            in 41..45 -> {
                color = R.drawable.green_circle
            }

            else -> {
                color = R.drawable.stroke_circle
            }
        }

        return color
    }

    public suspend fun getLottoRankInfo() : ArrayList<LottoRankInfo> = withContext(Dispatchers.IO) {
            val rankList = ArrayList<LottoRankInfo>();
            try {
                val url = "https://dhlottery.co.kr/gameResult.do?method=byWin"
                val doc = Jsoup.connect(url).timeout(1000 * 10).get()  //타임아웃 10초
                val contentData: Elements = doc.select("table tbody tr")

                for (data in contentData) {
                    val element = data.select("td")
                    val rank = element[0].text() //순위 : 1등, 2등...
                    val money = element[3].text() //등위별 당첨금액
                    val person = element[2].text() //당첨된 사람의 수

                    rankList.add( LottoRankInfo(rank, money, person))
                }
            }catch (e: Exception){
                e.printStackTrace()
            }
          rankList
    }

    public suspend fun getLottoInfo() : LottoNumberInfo? = withContext(Dispatchers.IO) {
        var round : Int? = null
        var number : String? = null
        var money : String? = null


        try {
            val url = "https://dhlottery.co.kr/gameResult.do?method=byWin"
            val doc = Jsoup.connect(url).timeout(1000 * 10).get()  //타임아웃 10초

            val info : LottoNumberInfo? = getLottoNumber(doc)
            info?.date = getLottoRoundDate(doc)

            info
        }catch (e: Exception){
            e.printStackTrace()
            null
        }
    }

    private suspend fun getLottoNumber(doc : Document) : LottoNumberInfo? = withContext(Dispatchers.Default) {
        var round : Int? = null
        var number : String? = null
        var money : String? = null

        val roundRegex : Regex = "[0-9]+회".toRegex()
        val numbersRegex : Regex = """[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+\+[0-9]+""".toRegex()
        val moneyRegex : Regex = """[,0-9]+원""".toRegex()


        try {
            val contentData: String = doc.select("meta[id=desc]").first().attr("content")

            roundRegex.find(contentData)?.let {
                round = it.value.substring(0, it.value.length - 1).toInt()
            }
            numbersRegex.find(contentData)?.let {
                number = it.value
            }

            moneyRegex.find(contentData)?.let {
                money = it.value
            }

        }catch (e: Exception){
            e.printStackTrace()
        }

        LottoNumberInfo(round ?: 0, number ?: "0,0,0,0,0,0+0",
                money ?: "0원", "미확인 날짜")
    }

    private suspend fun getLottoRoundDate(doc : Document) : String  = withContext(Dispatchers.Default) {
        var date : String = "미확인 날짜"

        try {

            val contentData: String = doc.select("div[class=win_result] p[class=desc]").first().text()

            date = contentData.substring(1, 14)

        }catch (e: Exception){
            e.printStackTrace()
        }

        date
    }
}