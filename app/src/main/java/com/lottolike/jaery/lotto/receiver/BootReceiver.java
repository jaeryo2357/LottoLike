package com.lottolike.jaery.lotto.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lottolike.jaery.lotto.lotto.db.LottoDB;
import com.lottolike.jaery.lotto.service.SenderAlert;
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

public class BootReceiver extends BroadcastReceiver {

    @Override
    public void onReceive(Context context, Intent intent) {
        if (intent.getAction().equals(Intent.ACTION_BOOT_COMPLETED)) {


            GregorianCalendar gregorianCalendar = new GregorianCalendar();
            LottoPreferences sharedPreferences = new LottoPreferences(context);
            String date = sharedPreferences.getLottoDate();

            if (!date.equals("")) {

                String[] dates = date.split(" ");

                gregorianCalendar.set(Calendar.YEAR, Integer.parseInt(dates[0].substring(0, dates[0].length() -1))); //2019
                gregorianCalendar.set(Calendar.MONTH, Integer.parseInt(dates[1].substring(0, dates[1].length() - 1)) - 1); // 10
                gregorianCalendar.set(Calendar.DAY_OF_MONTH, Integer.parseInt(dates[2].substring(0, dates[2].length() - 1)));//19
                gregorianCalendar.set(Calendar.HOUR_OF_DAY, 21);
                gregorianCalendar.set(Calendar.MINUTE, 0);
                gregorianCalendar.add(Calendar.DAY_OF_MONTH, 7);

                GregorianCalendar now = new GregorianCalendar();

                long diff = now.getTimeInMillis() - gregorianCalendar.getTimeInMillis();

                if (diff > 0) {//이미 현재시간이 알람시간보다 넘음
                    SenderAlert.senderAlarm(context, now);
                } else {
                    SenderAlert.senderAlarm(context, gregorianCalendar);//알림 보내기
                }
            }
        }
    }
}
