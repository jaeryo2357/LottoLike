package com.lottolike.jaery.lotto.data.officiallottomaindata.source;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class OfficialLottoMainDataRepositoryImpl implements OfficialLottoMainDataRepository {

    @Nullable
    private static OfficialLottoMainDataRepositoryImpl INSTANCE = null;

    @NotNull
    private OfficialLottoMainDataSource remoteOfficialLottoMainDataSource;

    @Nullable
    private OfficialLottoMainData cachedOfficialData;

    private boolean isCachedDirty = false;

    private OfficialLottoMainDataRepositoryImpl(@NotNull OfficialLottoMainDataSource remoteOfficialLottoMainDataSource) {
        this.remoteOfficialLottoMainDataSource = remoteOfficialLottoMainDataSource;
    }

    public static OfficialLottoMainDataRepository getInstance(@NotNull OfficialLottoMainDataSource remoteOfficialLottoMainDataSource) {
        if (INSTANCE == null) {
            synchronized (OfficialLottoMainDataRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new OfficialLottoMainDataRepositoryImpl(remoteOfficialLottoMainDataSource);
                }
            }
        }

        return INSTANCE;
    }

    @NotNull
    @Override
    public Single<OfficialLottoMainData> getOfficialLottoData() {
        if (cachedOfficialData != null && !isCachedDirty) {
            return Single.just(cachedOfficialData);
        }

        isCachedDirty = false;
        return getRemoteOfficialLottoMainData();
    }

    public void refreshOfficialData() {
        isCachedDirty = true;
    }

    private Single<OfficialLottoMainData> getRemoteOfficialLottoMainData() {
        return remoteOfficialLottoMainDataSource.getOfficialLottoData()
                .doOnSuccess(officialLottoMainData -> {
                    cachedOfficialData = officialLottoMainData;
                });
    }

}
