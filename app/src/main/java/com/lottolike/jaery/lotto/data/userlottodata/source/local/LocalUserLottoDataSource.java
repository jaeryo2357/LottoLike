package com.lottolike.jaery.lotto.data.userlottodata.source.local;

import android.content.Context;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;
import com.lottolike.jaery.lotto.data.userlottodata.source.UserLottoDataSource;

import org.jetbrains.annotations.NotNull;

import java.util.List;

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

    @Override
    public void insertUserLottoData(@NotNull UserLottoData userLottoData) {
        lottoDBHelper.insertLottoUserData(userLottoData);
    }

    @Override
    public void calculateUserLottoData(@NotNull List<OfficialLottoMainData> officialLottoMainData) {
        lottoDBHelper.calculateUserLottoDataList(officialLottoMainData);
    }
}
