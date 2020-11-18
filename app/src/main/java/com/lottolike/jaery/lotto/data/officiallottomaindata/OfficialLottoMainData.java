package com.lottolike.jaery.lotto.data.officiallottomaindata;

/**
   공식사이트에서 받아온 최신 로또 번호 정보
 */
public class OfficialLottoMainData {
    private int lottoRound;

    private int bonusNumber;

    //1,2,3,4,5,6
    private String officialLottoNumber;

    private String lottoDate;

    public OfficialLottoMainData(int lottoRound, int bonusNumber, String officialLottoNumber, String lottoDate) {
        this.lottoRound = lottoRound;
        this.bonusNumber = bonusNumber;
        this.officialLottoNumber = officialLottoNumber;
        this.lottoDate = lottoDate;
    }

    public int getLottoRound() {
        return lottoRound;
    }

    public void setLottoRound(int lottoRound) {
        this.lottoRound = lottoRound;
    }

    public int getBonusNumber() {
        return bonusNumber;
    }

    public void setBonusNumber(int bonusNumber) {
        this.bonusNumber = bonusNumber;
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
}
