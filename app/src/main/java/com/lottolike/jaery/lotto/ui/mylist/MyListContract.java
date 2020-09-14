package com.lottolike.jaery.lotto.ui.mylist;

import android.content.Context;

import com.lottolike.jaery.lotto.lotto.model.BasicItem;

import java.util.ArrayList;

public interface MyListContract {
    interface MyListView {
        void showMyList(ArrayList<BasicItem> list);

        void showErrorListEmpty();
    }

    interface MyListPresenter {

        void start(Context context);

        void reCalculateMyList();
    }
}
