package com.lottolike.jaery.lotto.data.userlottodata.source;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.officiallottorankdata.OfficialLottoRankData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;
import com.lottolike.jaery.lotto.data.userlottodata.source.local.LocalUserLottoDataSource;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import io.reactivex.rxjava3.core.Completable;
import io.reactivex.rxjava3.core.Flowable;
import io.reactivex.rxjava3.core.Single;

public class UserLottoDataRepositoryImpl implements UserLottoDataRepository{

    @Nullable
    private static UserLottoDataRepositoryImpl INSTANCE = null;

    @NotNull
    private UserLottoDataSource localUserLottoDataSource;

    @Nullable
    private Map<Integer, UserLottoData> cachedUserData;

    public boolean isCachedDirty = false;

    private UserLottoDataRepositoryImpl(@NotNull UserLottoDataSource localUserLottoDataSource) {
        this.localUserLottoDataSource = localUserLottoDataSource;
    }

    public static UserLottoDataRepositoryImpl getInstance(@NotNull UserLottoDataSource localUserLottoDataSource) {
        if (INSTANCE == null) {
            synchronized (UserLottoDataRepositoryImpl.class) {
                if (INSTANCE == null) {
                    INSTANCE = new UserLottoDataRepositoryImpl(localUserLottoDataSource);
                }
            }
        }

        return INSTANCE;
    }

    @NotNull
    @Override
    public Single<List<UserLottoData>> getUserLottoData() {
        if (cachedUserData != null && !isCachedDirty) {
            return Flowable.fromIterable(cachedUserData.values()).toList();
        } else if (cachedUserData == null) {
            cachedUserData = new HashMap<>();
        }

        isCachedDirty = false;
        return getLocalUserLottoDataList();
    }

    @Override
    public Completable insertUserLottoData(@NotNull UserLottoData data) {
        return localUserLottoDataSource.insertUserLottoData(data);
    }

    @Override
    public Completable calculateUserLottoData(@NotNull OfficialLottoMainData officialLottoMainData, @NotNull List<OfficialLottoRankData> officialLottoRankData) {
        return localUserLottoDataSource.calculateUserLottoData(officialLottoMainData, officialLottoRankData);
    }


    @Override
    public void refreshUserData() {
        isCachedDirty = true;
    }

    /**
     * local에서 Data를 가져오는 동시에 cached에 저장한다.
     * @return
     */
    private Single<List<UserLottoData>> getLocalUserLottoDataList() {
        return localUserLottoDataSource.getUserLottoData()
                .flatMap(userDataList -> Flowable.fromIterable(userDataList)
                .doOnNext(userData -> {
                    cachedUserData.put(userData.getPrimaryKey(), userData);
                })
                .toList());
    }
}
