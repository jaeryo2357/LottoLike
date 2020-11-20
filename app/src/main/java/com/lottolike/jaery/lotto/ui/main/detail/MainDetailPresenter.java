package com.lottolike.jaery.lotto.ui.main.detail;

import android.util.Pair;

import com.lottolike.jaery.lotto.data.officiallottomaindata.source.OfficialLottoMainDataRepository;
import com.lottolike.jaery.lotto.data.officiallottorankdata.source.OfficialLottoRankDataRepository;
import com.lottolike.jaery.lotto.data.officiallottorankdata.source.OfficialLottoRankDataRepositoryImpl;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Scheduler;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MainDetailPresenter implements MainDetailContact.Presenter{

    private MainDetailContact.View view;
    private OfficialLottoMainDataRepository officialLottoMainDataRepository;
    private OfficialLottoRankDataRepository officialLottoRankDataRepository;

    public MainDetailPresenter(MainDetailContact.View view,
                               OfficialLottoMainDataRepository officialLottoMainDataRepository,
                               OfficialLottoRankDataRepository officialLottoRankDataRepository) {
        this.view = view;
        this.officialLottoMainDataRepository = officialLottoMainDataRepository;
        this.officialLottoRankDataRepository = officialLottoRankDataRepository;
    }

    @Override
    public void clickBackButton() {
        view.showMainView();
    }

    @Override
    public void start() {
        view.showAdRequest();
        getOfficialLottoData();
    }

    private void getOfficialLottoData() {
        Disposable disposable = officialLottoMainDataRepository.getOfficialLottoData()
                .flatMap(officialLottoMainData -> {

                    return Single.zip(Single.just(officialLottoMainData),
                            officialLottoRankDataRepository.getOfficialLottoRankData(), Pair::new);
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pair -> {
                    view.showOfficialLottoData(pair.first, pair.second);
                }, Throwable::printStackTrace);

        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
