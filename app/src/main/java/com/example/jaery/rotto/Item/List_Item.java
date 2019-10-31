package com.example.jaery.rotto.Item;

public class List_Item {
    int primary_key; //db 기본키
    long money;
    int level; //등수  -1: 미 추첨
    String numbers;

    public int getPrimary_key() {
        return primary_key;
    }

    public void setPrimary_key(int primary_key) {
        this.primary_key = primary_key;
    }

    public long getMoney() {
        return money;
    }

    public void setMoney(long money) {
        this.money = money;
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
