package com.example.jaery.rotto.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.example.jaery.rotto.Database.BasicDB;
import com.example.jaery.rotto.Database.LottoDB;
import com.example.jaery.rotto.Service.SenderAlert;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {


            GregorianCalendar gregorianCalendar = new GregorianCalendar();

            int n = BasicDB.getRottoN(context.getApplicationContext());

            LottoDB db = new LottoDB(context);
            db.open();

            HashMap<String,String> temp = db.GetNo(n+"");

            db.close();

            Log.d("부팅","테스트");

            if(temp.size()>0)
            {
                String date = temp.get("date");

                String[] dates  = date.split("-");

                gregorianCalendar.set(Calendar.YEAR,Integer.parseInt(dates[0])); //2019
                gregorianCalendar.set(Calendar.MONTH,Integer.parseInt(dates[1])); // 10
                gregorianCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dates[2]));//19
                gregorianCalendar.set(Calendar.HOUR_OF_DAY,21);
                gregorianCalendar.add(Calendar.DAY_OF_MONTH,7);



                SenderAlert.senderAlarm(context,gregorianCalendar);//알림 보내기
            }


        }
    }
}
