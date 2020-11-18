package com.lottolike.jaery.lotto.data.util

import com.lottolike.jaery.lotto.R

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

    public fun getLottoBackgroundColor(number: Int): Int {
        val color: Int

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
}