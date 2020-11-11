package com.lottolike.jaery.lotto.data;

/**
   공식사이트에서 받아온 최신 로또 정보
 */
public class OfficialLottoData {
    private int lottoRound;

    private int lottoRankLevel;

    private int bonusNumber;

    OfficialLottoData(String lottoRound, String money, String date) {
        this.lottoDate = lottoRound;
        this.victoryMoney = money;
        this.lottoDate = date;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }

    public void setBonusNumber(int bonusNumber) {
        this.bonusNumber = bonusNumber;
    }

    private String officialLottoNumber;

    private String lottoDate;

    private String victoryMoney;

    private String victoryPerson;

    public int getLottoRound() {
        return lottoRound;
    }

    public void setLottoRound(int lottoRound) {
        this.lottoRound = lottoRound;
    }

    public int getLottoRankLevel() {
        return lottoRankLevel;
    }

    public void setLottoRankLevel(int lottoRankLevel) {
        this.lottoRankLevel = lottoRankLevel;
    }

    public String getOfficialLottoNumber() {
        return officialLottoNumber;
    }

    public void setOfficialLottoNumber(String officialLottoNumber) {
        this.officialLottoNumber = officialLottoNumber;
    }

    public String getLottoDate() {
        return lottoDate;
    }

    public void setLottoDate(String lottoDate) {
        this.lottoDate = lottoDate;
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
