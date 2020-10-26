package com.lottolike.jaery.lotto.ui.getnumber;

import com.lottolike.jaery.lotto.lotto.util.LottoUtil;

public class GetNumberPresenter implements GetNumberContract.Presenter {
    private GetNumberContract.View view;

    public GetNumberPresenter(GetNumberContract.View view) {
        this.view = view;
    }

    @Override
    public void recommendButtonClick() {
        view.showRecommendView(LottoUtil.INSTANCE.getRecommendLotto());
    }

    @Override
    public void selfInputCancelButtonClick() {
        view.hideSelfInputView();
    }

    @Override
    public void selfInputButtonClick() {
        view.showSelfInputView();
    }

    @Override
    public void start() {

    }
}
