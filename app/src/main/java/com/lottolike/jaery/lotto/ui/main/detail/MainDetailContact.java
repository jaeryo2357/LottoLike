package com.lottolike.jaery.lotto.ui.main.detail;

import com.lottolike.jaery.lotto.BasePresenter;
import com.lottolike.jaery.lotto.BaseView;
import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;

import java.util.List;

public class MainDetailContact {

    interface View extends BaseView<Presenter> {
        void showAdRequest();

        void showOfficialLottoData(List<OfficialLottoMainData> data);

        void showMainView();
    }

    interface Presenter extends BasePresenter {
        void clickBackButton();
    }
}
