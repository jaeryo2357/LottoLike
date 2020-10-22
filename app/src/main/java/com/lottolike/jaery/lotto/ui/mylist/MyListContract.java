package com.lottolike.jaery.lotto.ui.mylist;

import android.content.Context;

import com.lottolike.jaery.lotto.BasePresenter;
import com.lottolike.jaery.lotto.BaseView;
import com.lottolike.jaery.lotto.lotto.model.BasicItem;

import java.util.ArrayList;

public interface MyListContract {

    interface View extends BaseView<Presenter> {
        void showMyList(ArrayList<BasicItem> list);

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
