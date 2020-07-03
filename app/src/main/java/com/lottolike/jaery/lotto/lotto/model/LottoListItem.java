package com.lottolike.jaery.lotto.lotto.model;

import java.util.ArrayList;

public class LottoListItem extends BasicItem {
    int primary_key; //db 기본키
    String money;
    int level; //등수  -1: 미 추첨
    String numbers;
    ArrayList<Integer> corrects;

    public LottoListItem(int type, int primary_key, String money, int level, String numbers, ArrayList<Integer> corrects) {
        super(type);
        this.primary_key = primary_key;
        this.money = money;
        this.level = level;
        this.numbers = numbers;
        this.corrects = corrects;
    }

    public int getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(int primary_key) {
        this.primary_key = primary_key;
    }

    public String getMoney() {
        return money;
    }

    public void setMoney(String money) {
        this.money = money;
    }

    public ArrayList<Integer> getCorrects() {
        return corrects;
    }

    public void setCorrects(ArrayList<Integer> corrects) {
        this.corrects = corrects;
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getNumbers() {
        return numbers;
    }

    public void setNumbers(String numbers) {
        this.numbers = numbers;
    }
}
