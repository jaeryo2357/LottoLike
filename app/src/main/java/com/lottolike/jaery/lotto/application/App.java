package com.lottolike.jaery.lotto.application;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.os.Build;
import com.lottolike.jaery.lotto.util.SharedPreferences;

public class App extends Application {
    public static final String CHAANEL_ID="LottoChannel";
    SharedPreferences sharedPreferences;
    @Override
    public void onCreate() {
        super.onCreate();
        sharedPreferences = new SharedPreferences(getApplicationContext());
        createNotificationChannel();
    }

    private void createNotificationChannel(){

        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.O){
            NotificationChannel serviceChannel=new NotificationChannel(
                    CHAANEL_ID,
                    "Lotto Alert Service",
                    NotificationManager.IMPORTANCE_DEFAULT
            );

            if (!sharedPreferences.getSound()){

               serviceChannel.setSound(null,null);
            }

            if(sharedPreferences.getVibration())
            {
                serviceChannel.setVibrationPattern(new long[]{0, 500});
            }
            NotificationManager manager= getSystemService(NotificationManager.class);
            manager.createNotificationChannel(serviceChannel);
        }

    }
}