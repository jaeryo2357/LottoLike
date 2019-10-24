package com.example.jaery.rotto.Application;
import android.app.Application;
import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;

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

            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }
}