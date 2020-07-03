package com.lottolike.jaery.lotto.model;

public class LottoRoundItem extends BasicItem{
    int round;
    String time;

    public LottoRoundItem(int type, int drwN0, String time) {
        super(type);
        this.round = drwN0;
        this.time = time;
    }

    public int getRound() {
        return round;
    }

    public void setRound(int round) {
        this.round = round;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
