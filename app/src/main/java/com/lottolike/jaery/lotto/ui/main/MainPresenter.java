package com.lottolike.jaery.lotto.ui.main;

import com.lottolike.jaery.lotto.data.officiallottomaindata.source.OfficialLottoMainDataRepository;
import com.lottolike.jaery.lotto.data.util.LottoUtil;

import java.util.ArrayList;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;

import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mainView;
    private OfficialLottoMainDataRepository repository;
    private boolean isObserve = false;

    MainPresenter(MainContract.View view,  OfficialLottoMainDataRepository repository) {
        this.mainView = view;
        this.repository = repository;
    }

    @Override
    public void getOfficialLottoMainData() {
        repository.getOfficialLottoData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(mainView::showOfficialLottoData);
    }

    @Override
    public void getRecommendLottoNumber() {
        ArrayList<Integer> recommendLottoNumber = LottoUtil.INSTANCE.getRecommendLotto();
        mainView.showRecommendNumber(recommendLottoNumber);
    }

    @Override
    public void settingButtonClick() {
        mainView.showSettingView();
    }

    @Override
    public void qrCodeButtonClick() {
        mainView.showQRCodeView();
    }

    @Override
    public void myLottoListButtonClick() {
        mainView.showMyLottoListView();
    }

    @Override
    public void addLottoListButtonClick() {
        mainView.showAddLottoListView();
    }

    @Override
    public void detailButtonClick() {
        mainView.showDetailLottoView();
    }

    @Override
    public void start() {
        getOfficialLottoMainData();

        getRecommendLottoNumber();
    }

}
