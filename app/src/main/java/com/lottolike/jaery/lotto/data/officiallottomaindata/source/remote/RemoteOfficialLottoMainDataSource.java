package com.lottolike.jaery.lotto.data.officiallottomaindata.source.remote;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.officiallottomaindata.source.OfficialLottoMainDataSource;

import org.jetbrains.annotations.NotNull;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

import io.reactivex.rxjava3.core.Single;

public class RemoteOfficialLottoMainDataSource implements OfficialLottoMainDataSource {

    private final String remoteUrl = "https://dhlottery.co.kr/gameResult.do?method=byWin";

    @NotNull
    @Override
    public Single<OfficialLottoMainData> getOfficialLottoData() {
        return Single.create(emitter -> {
            emitter.onSuccess(getOfficialLottoDataUsingJsoup());
        });
    }

    private OfficialLottoMainData getOfficialLottoDataUsingJsoup() throws Exception {
        Document document = Jsoup.connect(remoteUrl).timeout(1000 * 3).get();

        int lottoRound = 0;
        int lottoBonusNumber = 0;
        String lottoNumbers = "1,2,3,4,5,6";
        String lottoNumberData = document.select("meta[id=desc]").first().attr("content");

        String lottoDateData = document.select("div[class=win_result] p[class=desc]").first().text().substring(1, 14);

        Pattern pattern = Pattern.compile("[0-9]+회");
        Matcher matcher = pattern.matcher(lottoNumberData);

        if (matcher.find()) {
            String findData = matcher.group();
            lottoRound = Integer.parseInt(findData.substring(0, findData.length() - 1));
        }

        pattern = Pattern.compile("[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+");
        matcher = pattern.matcher(lottoNumberData);
        if (matcher.find()) {
            lottoNumbers = matcher.group();
        }

        pattern = Pattern.compile("\\+[0-9]+");
        matcher = pattern.matcher(lottoNumberData);
        if (matcher.find()) {
            lottoBonusNumber = Integer.parseInt(matcher.group());
        }

        return new OfficialLottoMainData(lottoRound, lottoBonusNumber, lottoNumbers, lottoDateData);
    }
}
