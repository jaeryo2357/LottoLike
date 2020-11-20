package com.lottolike.jaery.lotto.data.officiallottomaindata.source;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface OfficialLottoMainDataRepository {

    @NotNull
    Single<OfficialLottoMainData> getOfficialLottoData();
}
