package com.lottolike.jaery.lotto.ui.mylist

import com.lottolike.jaery.lotto.data.UserLottoData
import com.lottolike.jaery.lotto.data.db.LottoDB
import com.lottolike.jaery.lotto.data.db.LottoPreferences
import com.lottolike.jaery.lotto.data.model.BasicItem
import com.lottolike.jaery.lotto.data.model.LottoRoundItem
import com.lottolike.jaery.lotto.data.util.LottoUtil
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
            if (list.size != 0) {
                view.showMyList(it)
            }
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

    private fun loadMyList(): List<UserLottoData>? {
        val items = lottoDB.myList

        if (items.size == 0) {
            view.showErrorListEmpty()
        }

        return items
    }
}