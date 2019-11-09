package com.lottolike.jaery.Lotto.Service;

import android.app.Activity;
import android.app.AlarmManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;


import com.lottolike.jaery.Lotto.Receivers.AlarmReceiver;

import java.util.GregorianCalendar;

public class SenderAlert {
    public static void senderAlarm(Context context, GregorianCalendar calendar)
    {

        Intent intent = new Intent(context, AlarmReceiver.class);

        PendingIntent mPending = PendingIntent.getBroadcast(
                context.getApplicationContext(),
                777,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager manager = (AlarmManager)context.getApplicationContext().getSystemService(Context.ALARM_SERVICE);

        if(manager == null )return;

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            manager.setExactAndAllowWhileIdle(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),mPending);
        }else
        {
            manager.set(AlarmManager.RTC_WAKEUP,calendar.getTimeInMillis(),mPending);
        }


    }


    public static void cancelAlarm(Context context,Activity activity){
        Intent intent  = new Intent(activity, AlarmReceiver.class);

        PendingIntent mPending = PendingIntent.getBroadcast(
                context.getApplicationContext(),
                1,
                intent,
                PendingIntent.FLAG_UPDATE_CURRENT
        );

        AlarmManager manager = (AlarmManager)context.getSystemService(Context.ALARM_SERVICE);
        manager.cancel(mPending);
    }


}
