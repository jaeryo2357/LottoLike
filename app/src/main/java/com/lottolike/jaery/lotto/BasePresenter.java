package com.lottolike.jaery.lotto;

import io.reactivex.rxjava3.disposables.CompositeDisposable;

public interface BasePresenter {
    CompositeDisposable compositeDisposable = new CompositeDisposable();

    public void start();
}
