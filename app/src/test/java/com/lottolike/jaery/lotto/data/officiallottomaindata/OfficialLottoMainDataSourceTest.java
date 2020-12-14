package com.lottolike.jaery.lotto.data.officiallottomaindata;

import com.lottolike.jaery.lotto.data.officiallottomaindata.source.OfficialLottoMainDataSource;
import com.lottolike.jaery.lotto.data.officiallottomaindata.source.remote.RemoteOfficialLottoMainDataSource;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class OfficialLottoMainDataSourceTest {

    private RemoteOfficialLottoMainDataSource dataSource;
    private final String lottoFakeData = "동행복권 941회 당첨번호 12,14,25,27,39,40+35. 1등 총 16명, 1인당 당첨금액 1,347,297,422원.";

    @Before
    public void setUp() throws Exception {
        dataSource = new RemoteOfficialLottoMainDataSource();
    }

    @Test
    public void lottoMainData_SuccessParsing_ReturnsBonusNumber() {
        dataSource.getOfficialLottoData()
                .subscribe(success -> {
                    assertEquals(success.getBonusNumber(), 35);
                });
    }

    @Test
    public void lottoMainData_RoundParsing_ReturnsLottoRound() {
        assertEquals(941, dataSource.getLottoRoundParsing(lottoFakeData));
    }

    @Test
    public void lottoMainData_RoundParsingUsingNotNumber_ReturnZero() {
        assertEquals(0, dataSource.getLottoRoundParsing("notNumber"));
    }

    @Test
    public void lottoMainData_NumberParsing_ReturnsLottoNumber() {
        assertEquals("12,14,25,27,39,40", dataSource.getLottoNumberParsing(lottoFakeData));
    }

    @After
    public void tearDown() throws Exception {
        dataSource = null;
    }
}