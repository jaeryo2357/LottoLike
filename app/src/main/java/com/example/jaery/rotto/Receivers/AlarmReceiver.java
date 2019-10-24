package com.example.jaery.rotto.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.TextView;

import com.example.jaery.rotto.Database.BasicDB;
import com.example.jaery.rotto.Service.SenderAlert;
import com.example.jaery.rotto.Service.ShowNotify;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


import static com.example.jaery.rotto.LottoItem.GetFreeNumber;


public class AlarmReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

          GregorianCalendar now = new GregorianCalendar();
          now.add(Calendar.DAY_OF_MONTH,7);
        SenderAlert.senderAlarm(context,now); //7일 뒤에 다시 설정

        int pastNum = BasicDB.getRottoN(context);

        BasicDB.setRottoN(context,++pastNum);

        //////////////////////////// 새로운 추천 번호 ///////////////////////
        ArrayList<Integer> integers = GetFreeNumber();
        String recommend_Num_String = "";

        for(int i = 0 ;i< integers.size();i++)
        {
            if(i!=0)recommend_Num_String +=",";

            recommend_Num_String += integers.get(i);
        }

        BasicDB.setRecommend(context,recommend_Num_String);

        //////////////////////////////////////////////////////////////


        Intent service= new Intent(context, ShowNotify.class);

        service.putExtra("drwNo",pastNum);
        context.startService(service);
    }
}
