package com.lottolike.jaery.lotto.data.officiallottorankdata.source;

import com.lottolike.jaery.lotto.data.officiallottorankdata.OfficialLottoRankData;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class OfficialLottoRankDataRepositoryImpl implements OfficialLottoRankDataRepository {


    @Nullable
    private static OfficialLottoRankDataRepositoryImpl INSTANCE = null;

    @NotNull
    private OfficialLottoRankDataSource remoteOfficialLottoRankDataSource;

    @Nullable
    private List<OfficialLottoRankData> cachedOfficialData;

    private boolean isCachedDirty = false;

    private OfficialLottoRankDataRepositoryImpl(@NotNull OfficialLottoRankDataSource remoteOfficialLottoRankDataSource) {
        this.remoteOfficialLottoRankDataSource = remoteOfficialLottoRankDataSource;
    }

    public static OfficialLottoRankDataRepository getInstance(@NotNull OfficialLottoRankDataSource remoteOfficialLottoRankDataSource) {
        if (INSTANCE == null) {
            synchronized (OfficialLottoRankDataRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OfficialLottoRankDataRepositoryImpl(remoteOfficialLottoRankDataSource);
                }
            }
        }

        return INSTANCE;
    }

    @NotNull
    @Override
    public Single<List<OfficialLottoRankData>> getOfficialLottoRankData() {
        if (cachedOfficialData != null && !isCachedDirty) {
            return Single.just(cachedOfficialData);
        }


        isCachedDirty = false;
        return getRemoteOfficialLottoRankData();
    }

    private Single<List<OfficialLottoRankData>> getRemoteOfficialLottoRankData() {
        return remoteOfficialLottoRankDataSource.getOfficialLottoRankData()
                .doOnSuccess(officialLottoRankData -> {
                   cachedOfficialData = officialLottoRankData;
                });
    }

    public void refreshOfficialData() {
        isCachedDirty = true;
    }
}
