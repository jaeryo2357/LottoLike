package com.lottolike.jaery.Lotto.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.IBinder;

import androidx.annotation.Nullable;
import androidx.core.app.NotificationCompat;

import com.lottolike.jaery.Lotto.Database.BasicDB;
import com.lottolike.jaery.Lotto.MainActivity;

import com.lottolike.jaery.Lotto.Application.App;
import com.lottolike.jaery.Lotto.R;

public class ShowNotify extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    @Override
    public int onStartCommand(Intent intent, int flags, int startId) {



        Intent notificationIntent=new Intent(this, MainActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        PendingIntent pendingIntent=PendingIntent.getActivity(this
                ,0,notificationIntent,0); //알람을 눌렀을 때 해당 엑티비티로

        Uri alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        int drwN0 = intent.getIntExtra("drwNo",0);

        String context = "최신정보와 추천 번호를 확인해보세요";
        if(drwN0 != 0)
            context = drwN0+"회차 당첨번호와 추천 번호를 확인해보세요";

        NotificationCompat.Builder builder=new NotificationCompat.Builder(this, App.CHAANEL_ID)
                .setContentTitle("로또")
                .setContentText(context)
                .setSmallIcon(R.drawable.ic_launcher_foreground)
                .setContentIntent(pendingIntent);

        if (BasicDB.getAlert_sound(getApplicationContext()))builder.setSound(alarmSound);
        if(BasicDB.getAlert_vibradtion(getApplicationContext()))builder.setVibrate(new long[]{0, 500});


        ((NotificationManager)getSystemService(NOTIFICATION_SERVICE)).notify(33,builder.build());

        return START_NOT_STICKY;
    }

}
