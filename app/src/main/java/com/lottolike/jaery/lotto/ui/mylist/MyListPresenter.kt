package com.lottolike.jaery.lotto.ui.mylist

import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData
import com.lottolike.jaery.lotto.data.userlottodata.source.local.LottoDBHelper
import com.lottolike.jaery.lotto.data.userlottodata.source.local.LottoPreferences
import com.lottolike.jaery.lotto.data.model.BasicItem
import com.lottolike.jaery.lotto.data.model.LottoRoundItem
import com.lottolike.jaery.lotto.data.util.LottoUtil
import kotlinx.coroutines.*

class MyListPresenter(
        private val view: MyListContract.View,
        private val lottoDBHelper: LottoDBHelper,
        private val pref : LottoPreferences
) : MyListContract.Presenter {

    private var scope : Job = Job()

    override fun start() {
        val list = getUserDataList()
        list?.let {
            if (it.isNotEmpty()) {
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
                lottoDBHelper.calculateUserLottoDataList(pref.lottoNumber, rankInfo)
            }

            view.dismissRefreshIndicator()
            view.showMyList(getUserDataList())
        }
    }

    /**
     * MyListContact.View 종료될때 호출
     * Coroutine 작업을 중지
     */
    override fun onDestroy() {
        scope.cancel()
    }

    private fun getUserDataList(): List<UserLottoData>? {
        val items = lottoDBHelper.userLottoDataList

        if (items.size == 0) {
            view.showErrorListEmpty()
        }

        return items
    }
}