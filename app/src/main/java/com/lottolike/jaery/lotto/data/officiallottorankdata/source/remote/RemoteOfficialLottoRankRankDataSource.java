package com.lottolike.jaery.lotto.data.officiallottorankdata.source.remote;

import com.lottolike.jaery.lotto.data.officiallottorankdata.OfficialLottoRankData;
import com.lottolike.jaery.lotto.data.officiallottorankdata.source.OfficialLottoRankDataSource;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.core.Single;

public class RemoteOfficialLottoRankRankDataSource implements OfficialLottoRankDataSource {

    private String remoteUrl = "https://dhlottery.co.kr/gameResult.do?method=byWin";

    @NotNull
    @Override
    public Single<List<OfficialLottoRankData>> getOfficialLottoRankData() {
        return Single.create(emitter -> {
            emitter.onSuccess(getOfficialLottoRankDataUsingJsoup());
        });
    }

    private List<OfficialLottoRankData> getOfficialLottoRankDataUsingJsoup() throws Exception {
        List<OfficialLottoRankData> officialLottoRankDataList = new ArrayList<>();

        Document document = Jsoup.connect(remoteUrl).timeout(1000 * 10).get();

        Elements lottoRankData = document.select("table tbody tr");

        for (Element element : lottoRankData) {
            Elements rankElements = element.select("td");
            String rank = rankElements.get(0).text();
            String person = rankElements.get(2).text();
            String money = rankElements.get(3).text();

            officialLottoRankDataList.add(new OfficialLottoRankData(rank, money, person));
        }

        return officialLottoRankDataList;
    }
}
