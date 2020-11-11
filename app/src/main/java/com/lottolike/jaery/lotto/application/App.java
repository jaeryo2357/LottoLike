package com.lottolike.jaery.lotto.application;

import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;

import android.os.Build;

import com.lottolike.jaery.lotto.data.db.LottoPreferences;

public class App extends Application {

    public static final String CHANNEL_ID="LottoChannel";
    private LottoPreferences sharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = LottoPreferences.Companion.getInstance(this);
        createNotificationChannel();
    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.O){
            NotificationChannel serviceChannel=new NotificationChannel(
                    CHANNEL_ID,
                    "Lotto Alert Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            if (!sharedPreferences.getSound()) {
               serviceChannel.setSound(null,null);
            }

            if(sharedPreferences.getVibration()) {
                serviceChannel.setVibrationPattern(new long[]{0, 500});
            }
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }
}