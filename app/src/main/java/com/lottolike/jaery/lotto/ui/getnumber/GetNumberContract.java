package com.lottolike.jaery.lotto.ui.getnumber;

import com.lottolike.jaery.lotto.BasePresenter;
import com.lottolike.jaery.lotto.BaseView;

import java.util.ArrayList;

public interface GetNumberContract {

    interface View extends BaseView<Presenter> {


        void clearRecommendView();

        void hideSelfInputView();

        void showRecommendView(ArrayList<Integer> recommend);

        void showSelfInputView();
    }

    interface Presenter extends BasePresenter {

        void recommendButtonClick();

        void selfInputCancelButtonClick();

        void selfInputButtonClick();
    }
}
