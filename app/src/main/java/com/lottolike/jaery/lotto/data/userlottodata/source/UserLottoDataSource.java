package com.lottolike.jaery.lotto.data.userlottodata.source;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.officiallottorankdata.OfficialLottoRankData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Single;

public interface UserLottoDataSource {

    @NotNull
    Single<List<UserLottoData>> getUserLottoData();

    @NotNull
    Completable insertUserLottoData(@NotNull UserLottoData data);

    @NotNull
    Completable calculateUserLottoData(@NotNull OfficialLottoMainData officialLottoMainData,
                                       @NotNull List<OfficialLottoRankData> officialLottoRankData);
}
