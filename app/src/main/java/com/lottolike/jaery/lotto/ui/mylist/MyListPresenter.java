package com.lottolike.jaery.lotto.ui.mylist;

import android.util.Pair;

import com.lottolike.jaery.lotto.data.officiallottomaindata.source.OfficialLottoMainDataRepository;
import com.lottolike.jaery.lotto.data.officiallottorankdata.source.OfficialLottoRankDataRepository;
import com.lottolike.jaery.lotto.data.userlottodata.source.UserLottoDataRepository;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.core.Single;
import io.reactivex.rxjava3.disposables.Disposable;
import io.reactivex.rxjava3.schedulers.Schedulers;

public class MyListPresenter implements MyListContract.Presenter{

    private MyListContract.View view;
    private UserLottoDataRepository userLottoDatarepository;
    private OfficialLottoMainDataRepository officialLottoMainDataRepository;
    private OfficialLottoRankDataRepository officialLottoRankDataRepository;

    public MyListPresenter(MyListContract.View view, UserLottoDataRepository userLottoDatarepository,
                           OfficialLottoMainDataRepository officialLottoMainDataRepository,
                           OfficialLottoRankDataRepository officialLottoRankDataRepository) {
        this.view = view;
        this.userLottoDatarepository = userLottoDatarepository;
        this.officialLottoMainDataRepository = officialLottoMainDataRepository;
        this.officialLottoRankDataRepository = officialLottoRankDataRepository;
    }

    @Override
    public void start() {
        getUserLottoDataList();
    }

    @Override
    public void calculateMyList() {
        view.showRefreshIndicator();
        Disposable disposable = officialLottoRankDataRepository.getOfficialLottoRankData()
                .flatMap( officialLottoRankData -> {

                    return Single.zip(Single.just(officialLottoRankData), officialLottoMainDataRepository.getOfficialLottoData()
                    ,Pair::new);
                }).subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(pair -> {
                    Disposable calculateDisposable = userLottoDatarepository.calculateUserLottoData(pair.second, pair.first)
                    .subscribeOn(Schedulers.computation())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribe(this::getUserLottoDataList, error -> {
                       view.dismissRefreshIndicator();
                       view.showErrorListEmpty();
                    });

                    compositeDisposable.add(calculateDisposable);
                }, error -> {
                    view.dismissRefreshIndicator();
                    view.showErrorListEmpty();
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void onSwipeRefresh() {
        view.showRefreshIndicator();
        userLottoDatarepository.refreshUserData();
        getUserLottoDataList();
    }

    private void getUserLottoDataList() {
        Disposable disposable = userLottoDatarepository.getUserLottoData()
                .flatMap(userLottoDataList -> {
                    return Single.zip(Single.just(userLottoDataList), officialLottoMainDataRepository.getOfficialLottoData(), Pair::new);
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe( pair -> {
                    view.dismissRefreshIndicator();
                    if (pair.first.isEmpty()) {
                        view.showErrorListEmpty();
                    } else {
                        view.showMyList(pair.first, pair.second);
                    }
                }, error -> {
                    view.dismissRefreshIndicator();
                    view.showErrorListEmpty();
                });

        compositeDisposable.add(disposable);
    }

    @Override
    public void onDestroy() {
        compositeDisposable.clear();
    }
}
