package com.lottolike.jaery.lotto.ui.mylist;

import com.lottolike.jaery.lotto.BasePresenter;
import com.lottolike.jaery.lotto.BaseView;
import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;

import java.util.List;

public interface MyListContract {

    interface View extends BaseView<Presenter> {
        void showMyList(List<UserLottoData> userLottoData, OfficialLottoMainData officialLottoMainData);

        void showOfficialDate(OfficialLottoMainData officialLottoMainData);

        void showRefreshIndicator();

        void dismissRefreshIndicator();

        void showErrorListEmpty();
    }

    interface Presenter extends BasePresenter {

        void calculateMyList();

        void onSwipeRefresh();

        void onDestroy();
    }
}
