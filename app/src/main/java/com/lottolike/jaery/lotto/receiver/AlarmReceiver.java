package com.lottolike.jaery.lotto.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.lottolike.jaery.lotto.service.SenderAlert;
import com.lottolike.jaery.lotto.service.ShowNotify;
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences;

import java.util.Calendar;
import java.util.GregorianCalendar;

public class AlarmReceiver extends BroadcastReceiver {

    LottoPreferences sharedPreferences;
    Context context;
    @Override
    public void onReceive(final Context context, Intent intent) {

        this.context = context;
        sharedPreferences = new LottoPreferences(context);
          GregorianCalendar now = new GregorianCalendar();
          now.set(Calendar.MINUTE,0);
          now.add(Calendar.DAY_OF_MONTH,7);
        SenderAlert.senderAlarm(context,now); //7일 뒤에 다시 설정

        int lottoRound = sharedPreferences.getLottoNumber();
        Intent service= new Intent(context, ShowNotify.class);
        service.putExtra("drwNo",lottoRound + 1);
        context.startService(service);
    }
}
