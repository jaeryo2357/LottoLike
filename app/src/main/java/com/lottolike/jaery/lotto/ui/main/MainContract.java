package com.lottolike.jaery.lotto.ui.main;

import android.widget.TextView;

import com.lottolike.jaery.lotto.BasePresenter;
import com.lottolike.jaery.lotto.BaseView;
import com.lottolike.jaery.lotto.R;

import java.util.ArrayList;

public interface MainContract {

    interface View extends BaseView<Presenter> {

        public void showLottoRound(int round);

        public void showLottoRoundDate(String date);

        void showLottoNumber(String lottoNumber);

        void showRecommendNumber(ArrayList<Integer> numbers);

        void showQRCodeView();

        void showSettingView();

        void showMyLottoListView();

        void showAddLottoListView();

        void showDetailLottoView();
    }

    interface Presenter extends BasePresenter {
        void settingButtonClick();

        void qrCodeButtonClick();

        void myLottoListButtonClick();

        void addLottoListButtonClick();

        void detailButtonClick();
    }
}
