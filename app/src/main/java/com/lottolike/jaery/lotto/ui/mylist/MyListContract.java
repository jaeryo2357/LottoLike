package com.lottolike.jaery.lotto.ui.mylist;

import com.lottolike.jaery.lotto.BasePresenter;
import com.lottolike.jaery.lotto.BaseView;
import com.lottolike.jaery.lotto.data.OfficialLottoData;
import com.lottolike.jaery.lotto.data.UserLottoData;

import java.util.List;

public interface MyListContract {

    interface View extends BaseView<Presenter> {
        void showMyList(List<UserLottoData> userLottoData, OfficialLottoData officialLottoData);

        void showOfficialDate(OfficialLottoData officialLottoData);

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
