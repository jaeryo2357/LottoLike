package com.lottolike.jaery.lotto.ui.getnumber;

import com.lottolike.jaery.lotto.lotto.db.LottoDB;
import com.lottolike.jaery.lotto.lotto.util.LottoUtil;

import java.util.ArrayList;

public class GetNumberPresenter implements GetNumberContract.Presenter {
    private GetNumberContract.View view;
    private LottoDB db;

    private ArrayList<Integer> selectedNumber;

    public GetNumberPresenter(GetNumberContract.View view, LottoDB db) {
        this.view = view;
        this.db = db;
        selectedNumber = new ArrayList<>();
    }

    @Override
    public void numberSaveButtonClick() {
        if (selectedNumberCheck()) {
            insertLottoNumber(selectedNumber);
            view.showSaveSuccess();
        } else {
            view.showSaveError();
        }
    }

    @Override
    public void recommendButtonClick() {
        view.showRecommendView(LottoUtil.INSTANCE.getRecommendLotto());
    }

    @Override
    public void selfNumberButtonClick(int value) {
        Integer newValue = value;

        if (selectedNumber.size() == 6) {
            view.showSaveError();
            return;
        }

        if (selectedNumber.contains(newValue)) {
            selectedNumber.remove(newValue);
        } else {
            selectedNumber.add(newValue);
        }

        view.showRecommendView(selectedNumber);
    }

    @Override
    public void clearButtonClick() {
        view.clearSelectedNumber();
    }


    @Override
    public void start() {

    }

    private boolean selectedNumberCheck() {
        return selectedNumber.size() == 6;
    }

    private void insertLottoNumber(ArrayList<Integer> number) {
        db.myListInsert(number);
    }
}
