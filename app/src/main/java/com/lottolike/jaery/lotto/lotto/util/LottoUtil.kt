package com.lottolike.jaery.lotto.lotto.util

import com.lottolike.jaery.lotto.lotto.domain.LottoNumberInfo
import com.lottolike.jaery.lotto.lotto.domain.LottoRankInfo
import kotlinx.coroutines.*
import org.jsoup.Jsoup
import org.jsoup.select.Elements
import java.util.*
import java.util.concurrent.CompletableFuture

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

    public suspend fun getLottoNumberInfo() : LottoNumberInfo = withContext(Dispatchers.IO) {
        var round : Int? = null
        var number : String? = null
        val roundRegex : Regex = "[0-9]+회".toRegex()
        val numbersRegex : Regex = """[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+\+[0-9]+""".toRegex()
        try {
            val url = "https://dhlottery.co.kr/gameResult.do?method=byWin"
            val doc = Jsoup.connect(url).timeout(1000 * 10).get()  //타임아웃 10초
            val contentData: String = doc.select("meta[id=desc]").first().attr("content")

            roundRegex.find(contentData)?.let {
                round = it.value.substring(0, it.value.length - 1).toInt()
            }
            numbersRegex.find(contentData)?.let {
                number = it.value
            }

        }catch (e: Exception){
            e.printStackTrace()
        }

        LottoNumberInfo(round ?: 0, number ?: "0,0,0,0,0,0+0")
    }

    public suspend fun getLottoRoundDate() : String = withContext(Dispatchers.IO) {
        var date : String = "2020년 6월 30일"

        try {
            val url = "https://dhlottery.co.kr/gameResult.do?method=byWin"
            val doc = Jsoup.connect(url).timeout(1000 * 10).get()  //타임아웃 10초
            val contentData: String = doc.select("div[class=win_result] p[class=desc]").first().text()

            date = contentData.substring(1, 14)

        }catch (e: Exception){
            e.printStackTrace()
        }

        date
    }
}