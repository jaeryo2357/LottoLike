package com.lottolike.jaery.Lotto.Application;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

import com.lottolike.jaery.Lotto.Database.BasicDB;

public class App extends Application {
    public static final String CHAANEL_ID="LottoChannel";

    @Override
    public void onCreate() {
        super.onCreate();

        createNotificationChannel();
    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceChannel=new NotificationChannel(
                    CHAANEL_ID,
                    "Lotto Alert Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            if (!BasicDB.getAlert_sound(getApplicationContext())){

               serviceChannel.setSound(null,null);
            }

            if(BasicDB.getAlert_vibradtion(getApplicationContext()))
            {
                serviceChannel.setVibrationPattern(new long[]{0, 500});
            }
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }
}