package com.lottolike.jaery.lotto.util;

import android.content.Context;

import com.lottolike.jaery.lotto.Database.LottoDB;
import com.lottolike.jaery.lotto.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;



public class LottoItem {

    public static int GetBackgroundColor(int number)
    {
        if(number>=0&&number<=10)return R.drawable.yellow_circle;
        else if(number>10&&number<=20) return R.drawable.blue_circle;
        else if(number>20&&number<=30) return R.drawable.red_circle;
        else if(number>30&&number<=40) return R.drawable.shadow_circle;
        else if(number<=45)return R.drawable.green_circle;
        else return R.drawable.stroke_circle;
    }

    public static  HashMap<String, String> GetNumber(Context context, int N){  //회차에 해당하는 로또 번호 가져오는 함수

        LottoDB db=new LottoDB(context);
        db.open();
        HashMap<String,String> today=db.GetNo(""+N);
        return today;
    }


    public static ArrayList<Integer> GetFreeNumber(){
        ArrayList<Integer> a =new ArrayList<Integer>();

        a.add((int)(Math.random()*45)+1);
        for(int i=1;i<6;)
        {
            int e=(int)(Math.random()*45)+1;
            if(!a.contains(e)) {
                a.add(e);
                i++;
            }
        }
        Collections.sort(a);
        return a;
    }

}
