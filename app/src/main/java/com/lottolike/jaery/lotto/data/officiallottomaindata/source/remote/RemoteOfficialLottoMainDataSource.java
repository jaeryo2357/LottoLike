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
            try {
                emitter.onSuccess(getOfficialLottoDataUsingJsoup());
            } catch (Exception e) {
                emitter.onError(e);
            }
        });
    }

    private OfficialLottoMainData getOfficialLottoDataUsingJsoup() throws Exception {
        Document document = Jsoup.connect(remoteUrl).timeout(1000 * 10).get();

        int lottoRound = 0;
        int lottoBonusNumber = 0;
        String lottoNumbers = "1,2,3,4,5,6";
        String lottoNumberData = document.select("meta[id=desc]").first().attr("content");

        String lottoDateData = document.select("div[class=win_result] p[class=desc]").first().text().substring(1, 14);


        lottoRound = getLottoRoundParsing(lottoNumberData);

        lottoNumbers = getLottoNumberParsing(lottoNumberData);

        lottoBonusNumber = getBonusNumberParsing(lottoNumberData);

        return new OfficialLottoMainData(lottoRound, lottoBonusNumber, lottoNumbers, lottoDateData);
    }

    //파싱 메소드를 분리하므로 각각 메소드를 유닛 테스트 할 수 있다.

    public int getLottoRoundParsing(String parsingData) {
        Pattern pattern = Pattern.compile("[0-9]+회");
        Matcher matcher = pattern.matcher(parsingData);

        if (matcher.find()) {
            try {
                String findData = matcher.group();
                return Integer.parseInt(findData.substring(0, findData.length() - 1));
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }

    public String getLottoNumberParsing(String parsingData) {
        Pattern pattern = Pattern.compile("[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+,[0-9]+");
        Matcher matcher = pattern.matcher(parsingData);

        if (matcher.find()) {
            return matcher.group();
        } else {
            return "";
        }
    }

    public int getBonusNumberParsing(String parsingData) {
        Pattern pattern = Pattern.compile("\\+[0-9]+");
        Matcher matcher = pattern.matcher(parsingData);

        if (matcher.find()) {
            try {
                return Integer.parseInt(matcher.group());
            } catch (NumberFormatException e) {
                return 0;
            }
        } else {
            return 0;
        }
    }
}
