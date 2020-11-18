package com.lottolike.jaery.lotto.ui.main;

import android.content.Context;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;


import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;

import java.util.ArrayList;

public class MainPresenter implements MainContract.Presenter{

    private MainContract.View mainView;
    private MainModel mainModel;
    private boolean isObserve = false;

    MainPresenter(MainContract.View view,  MainModel model) {
        mainView = view;
        mainModel = model;
    }

    //모델의 변수 변화를 구독 후, 변경되면 View 반영
    private void observeModel() {

        mainModel.getOfficialLottoMainData().observe((LifecycleOwner) mainView, new Observer<OfficialLottoMainData>() {
            @Override
            public void onChanged(OfficialLottoMainData data) {
                //2020년 06월 27일
                mainView.showLottoRound(data.getLottoRound());
                mainView.showLottoNumber(data.getOfficialLottoNumber());
                mainView.showLottoRoundDate(data.getLottoDate());
            }
        });

        //로또 추천번호
        mainModel.getRecommendLotto().observe((LifecycleOwner) mainView, new Observer<ArrayList<Integer>>() {
            @Override
            public void onChanged(ArrayList<Integer> numbers) {
                mainView.showRecommendNumber(numbers);
            }
        });
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
        if (!isObserve) {
            observeModel();
            mainModel.changeRecommendLotto();
            mainModel.changeLottoInfo((Context)mainView);
            isObserve = true;
        }
    }

}
