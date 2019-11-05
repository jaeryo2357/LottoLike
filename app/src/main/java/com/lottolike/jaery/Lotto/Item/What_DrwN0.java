package com.lottolike.jaery.Lotto.Item;

public class What_DrwN0 extends BasicItem{
    int drwN0;
    String time;


    public What_DrwN0(int type, int drwN0, String time) {
        super(type);
        this.drwN0 = drwN0;
        this.time = time;
    }

    public int getDrwN0() {
        return drwN0;
    }

    public void setDrwN0(int drwN0) {
        this.drwN0 = drwN0;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
