package com.lottolike.jaery.lotto.ui.mylist

import android.content.Context
import com.lottolike.jaery.lotto.lotto.db.LottoDB
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences.Companion.getInstance
import com.lottolike.jaery.lotto.lotto.model.BasicItem
import com.lottolike.jaery.lotto.lotto.model.LottoRoundItem
import com.lottolike.jaery.lotto.lotto.util.LottoUtil
import kotlinx.coroutines.*

import java.util.*

class MyListPresenter(
        private val view: MyListContract.View,
        private val lottoDB: LottoDB,
        private val pref : LottoPreferences
) : MyListContract.Presenter {

    private var scope : Job = Job()

    override fun start() {
        val list = loadMyList()
        list?.let {
            view.showMyList(it)
        }
    }

    override fun onSwipeRefresh() {
        start()
    }

    override fun calculateMyList() {
        view.showRefreshIndicator()
        scope = CoroutineScope(Dispatchers.Main + scope).launch{

            val rankInfo = LottoUtil.getLottoRankInfo()

            //같은 scope 를 공유?
            launch(Dispatchers.Default) {
                lottoDB.myListCheck(pref.lottoNumber, rankInfo)
            }

            view.dismissRefreshIndicator()
            view.showMyList(loadMyList())
        }
    }

    /**
     * MyListContact.View 종료될때 호출
     * Coroutine 작업을 중지
     */
    override fun onDestroy() {
        scope.cancel()
    }

    private fun loadMyList(): ArrayList<BasicItem>? {
        val items = lottoDB.myList

        if (items.size == 0) {
            view.showErrorListEmpty()
        } else {
            items.add(0, inputLottoRoundView())
        }

        return items
    }

    private fun inputLottoRoundView(): LottoRoundItem {
        val round = pref.lottoRound
        val date = pref.lottoDate
        return LottoRoundItem(0, round, date)
    }

}