package com.lottolike.jaery.lotto.data.userlottodata.source;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface UserLottoDataSource {

    @NotNull
    Single<List<UserLottoData>> getUserLottoData();

    void insertUserLottoData(@NotNull UserLottoData data);

    void calculateUserLottoData(@NotNull List<OfficialLottoMainData> officialLottoMainData);
}
