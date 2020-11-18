package com.example.jaery.lotto

import com.lottolike.jaery.lotto.lotto.util.LottoUtil
import kotlinx.coroutines.runBlocking
import org.junit.Assert
import org.junit.Test

class LottoUnitTest {

    @Test
    fun getLottoDate() = runBlocking {
        val date : String = LottoUtil.getLottoRoundDate()

        Assert.assertEquals(date, "2020년 06월 27일")
    }
}