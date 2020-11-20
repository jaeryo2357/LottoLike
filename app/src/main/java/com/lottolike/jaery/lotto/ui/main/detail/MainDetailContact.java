package com.lottolike.jaery.lotto.ui.main.detail;

import com.lottolike.jaery.lotto.BasePresenter;
import com.lottolike.jaery.lotto.BaseView;
import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.officiallottorankdata.OfficialLottoRankData;

import java.util.List;

public class MainDetailContact {

    interface View extends BaseView<Presenter> {
        void showAdRequest();

        void showOfficialLottoData(OfficialLottoMainData mainData, List<OfficialLottoRankData> officialLottoRankData);

        void showMainView();
    }

    interface Presenter extends BasePresenter {
        void clickBackButton();

        void onDestroy();
    }
}
