package com.lottolike.jaery.lotto.data.userlottodata.source.local;

import android.content.Context;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.officiallottorankdata.OfficialLottoRankData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;
import com.lottolike.jaery.lotto.data.userlottodata.source.UserLottoDataSource;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public class LocalUserLottoDataSource implements UserLottoDataSource {

    private LottoDBHelper lottoDBHelper;

    public LocalUserLottoDataSource(Context context) {
        this.lottoDBHelper = LottoDBHelper.getInstance(context);
    }

    @NotNull
    @Override
    public Single<List<UserLottoData>> getUserLottoData() {
        return Single.just(lottoDBHelper.getUserLottoDataList());
    }

    @NotNull
    @Override
    public Completable insertUserLottoData(@NotNull UserLottoData userLottoData) {
        return Completable.create(emitter -> {
            lottoDBHelper.insertLottoUserData(userLottoData);
            emitter.onComplete();
        });
    }

    @NotNull
    @Override
    public Completable calculateUserLottoData(@NotNull OfficialLottoMainData officialLottoMainData, @NotNull List<OfficialLottoRankData> officialLottoRankData) {
        return Completable.create(emitter -> {
            lottoDBHelper.calculateUserLottoDataList(officialLottoMainData, officialLottoRankData);
        });
    }

}
