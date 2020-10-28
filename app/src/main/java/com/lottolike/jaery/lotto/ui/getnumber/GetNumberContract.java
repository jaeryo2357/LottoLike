package com.lottolike.jaery.lotto.ui.getnumber;

import android.widget.EditText;

import com.lottolike.jaery.lotto.BasePresenter;
import com.lottolike.jaery.lotto.BaseView;

import java.util.ArrayList;

public interface GetNumberContract {

    interface View extends BaseView<Presenter> {

        void showRecommendView(ArrayList<Integer> recommend);

        void showSaveSuccess();

        void showSaveError();

        void clearSelectedNumber();

    }

    interface Presenter extends BasePresenter {

        void numberSaveButtonClick();

        void recommendButtonClick();

        void selfNumberButtonClick(int value);

        void clearButtonClick();
    }
}
