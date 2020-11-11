package com.lottolike.jaery.lotto.data;

/**
 * 사용자가 추가한 로또 번호 목록에 대한 정보
 */
public class UserLottoData {
    private int primaryKey; //db 기본키
    private String money;
    private int level; //등수  -1: 미 추첨
    private String userNumbers;

    public UserLottoData(int primaryKey, int level, String money, String userNumbers) {
        this.primaryKey = primaryKey;
        this.money = money;
        this.level = level;
        this.userNumbers = userNumbers;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getUserNumbers() {
        return userNumbers;
    }

    public void setUserNumbers(String userNumbers) {
        this.userNumbers = userNumbers;
    }
}
