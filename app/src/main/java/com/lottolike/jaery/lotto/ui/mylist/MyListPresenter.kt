package com.lottolike.jaery.lotto.ui.mylist

import android.content.Context
import com.lottolike.jaery.lotto.lotto.db.LottoDB
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences.Companion.getInstance
import com.lottolike.jaery.lotto.lotto.model.BasicItem
import com.lottolike.jaery.lotto.lotto.model.LottoRoundItem
import com.lottolike.jaery.lotto.lotto.util.LottoUtil
import com.lottolike.jaery.lotto.lotto.util.LottoUtil.getLottoRankInfo
import com.lottolike.jaery.lotto.ui.mylist.MyListContract.MyListView
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import java.util.*

class MyListPresenter(private val view: MyListView) : MyListContract.MyListPresenter {
    private var lottoDB: LottoDB? = null
    private var pref: LottoPreferences? = null
    override fun start(context: Context) {
        lottoDB = LottoDB.getInstance(context)
        pref = getInstance(context)
        val list = loadMyList()
        list?.let {
            view.showMyList(list)
        }
    }

    override fun reCalculateMyList() {
        CoroutineScope(Dispatchers.Main).launch{

            val rankInfo = LottoUtil.getLottoRankInfo()
            withContext(Dispatchers.Default) {
                lottoDB?.myListCheck(pref!!.lottoNumber, rankInfo)
            }

            view.showMyList(loadMyList())
        }
    }

    private fun loadMyList(): ArrayList<BasicItem>? {
        val items = lottoDB!!.myList

        if (items.size == 0) {
            view.showErrorListEmpty()
            return null
        } else {
            items.add(0, inputLottoRoundView())
            return items
        }
    }

    private fun inputLottoRoundView(): LottoRoundItem {
        val round = pref!!.lottoRound
        val date = pref!!.lottoDate
        return LottoRoundItem(0, round, date)
    }

}