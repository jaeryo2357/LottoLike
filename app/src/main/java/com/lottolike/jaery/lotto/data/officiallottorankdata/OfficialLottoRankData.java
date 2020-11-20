package com.lottolike.jaery.lotto.data.officiallottorankdata;

/**
   공식사이트에서 받아온 최신 로또 정보
 */
public class OfficialLottoRankData {

    private String lottoRankLevel;

    private String victoryMoney;

    private String victoryPerson;

    public OfficialLottoRankData(String lottoRankLevel, String victoryMoney, String victoryPerson) {
        this.lottoRankLevel = lottoRankLevel;
        this.victoryMoney = victoryMoney;
        this.victoryPerson = victoryPerson;
    }

    public String getLottoRankLevel() {
        return lottoRankLevel;
    }

    public void setLottoRankLevel(String lottoRankLevel) {
        this.lottoRankLevel = lottoRankLevel;
    }

    public String getVictoryMoney() {
        return victoryMoney;
    }

    public void setVictoryMoney(String victoryMoney) {
        this.victoryMoney = victoryMoney;
    }

    public String getVictoryPerson() {
        return victoryPerson;
    }

    public void setVictoryPerson(String victoryPerson) {
        this.victoryPerson = victoryPerson;
    }
}
