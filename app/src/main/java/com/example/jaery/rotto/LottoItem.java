package com.example.jaery.rotto;

public class LottoItem {

    public static int GetBackgroundColor(int number)
    {
        if(number>=0&&number<10)return R.drawable.yellow_circle;
        else if(number>=10&&number<20) return R.drawable.blue_circle;
        else if(number>=20&&number<30) return R.drawable.red_circle;
        else if(number>=30&&number<40) return R.drawable.shadow_circle;
        else return R.drawable.green_circle;
    }
}
