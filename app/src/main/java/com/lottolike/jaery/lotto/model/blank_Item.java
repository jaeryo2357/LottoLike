package com.lottolike.jaery.lotto.model;

public class blank_Item {
    private int number;
    private boolean click;

    public blank_Item(int number, boolean click) {
        this.number = number;
        this.click = click;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public boolean isClick() {
        return click;
    }

    public void setClick(boolean click) {
        this.click = click;
    }
}
