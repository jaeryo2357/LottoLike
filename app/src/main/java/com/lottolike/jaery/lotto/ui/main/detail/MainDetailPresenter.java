package com.lottolike.jaery.lotto.ui.main.detail;

public class MainDetailPresenter implements MainDetailContact.Presenter{

    MainDetailContact.View view;

    @Override
    public void clickBackButton() {
        view.showMainView();
    }

    @Override
    public void start() {

    }
}
