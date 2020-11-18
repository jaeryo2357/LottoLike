package com.lottolike.jaery.lotto.data.officiallottorankdata.source;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.officiallottorankdata.OfficialLottoRankData;

import org.jetbrains.annotations.NotNull;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public interface OfficialLottoRankDataSource {

    @NotNull
    Single<List<OfficialLottoRankData>> getOfficialLottoRankData();
}
