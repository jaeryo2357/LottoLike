package com.lottolike.jaery.Lotto.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lottolike.jaery.Lotto.Database.BasicDB;
import com.lottolike.jaery.Lotto.Database.LottoDB;
import com.lottolike.jaery.Lotto.Service.SenderAlert;

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


            if(temp.size()>0)
            {
                String date = temp.get("date");

                String[] dates  = date.split("-");

                gregorianCalendar.set(Calendar.YEAR,Integer.parseInt(dates[0])); //2019
                gregorianCalendar.set(Calendar.MONTH,Integer.parseInt(dates[1])-1); // 10
                gregorianCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dates[2]));//19
                gregorianCalendar.set(Calendar.HOUR_OF_DAY,21);

                gregorianCalendar.add(Calendar.DAY_OF_MONTH,7);

                GregorianCalendar now = new GregorianCalendar();

                long diff = now.getTimeInMillis()-gregorianCalendar.getTimeInMillis();

                if(diff>0) //이미 현재시간이 알람시간보다 넘음
                {
                    SenderAlert.senderAlarm(context,now);
                }else
                    SenderAlert.senderAlarm(context,gregorianCalendar);//알림 보내기
            }


        }
    }
}
