package com.lottolike.jaery.Lotto.Receivers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lottolike.jaery.Lotto.Database.BasicDB;
import com.lottolike.jaery.Lotto.Database.LottoDB;
import com.lottolike.jaery.Lotto.GetJson;
import com.lottolike.jaery.Lotto.Service.SenderAlert;
import com.lottolike.jaery.Lotto.Service.ShowNotify;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.lottolike.jaery.Lotto.LottoItem.GetFreeNumber;


public class AlarmReceiver extends BroadcastReceiver {

    Context context;
    @Override
    public void onReceive(final Context context, Intent intent) {

        this.context = context;

          GregorianCalendar now = new GregorianCalendar();
          now.set(Calendar.MINUTE,0);
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

        new Thread(){
            @Override
            public void run() {
                GetJson json = GetJson.getInstance();
                json.requestWebServer(BasicDB.getRottoN(context)+"",callback);
            }
        }.run();

    }

    public void InsertRecommend(LottoDB db,String date){

            GregorianCalendar gregorianCalendar = new GregorianCalendar();


            String[] dates  = date.split("-");

            gregorianCalendar.set(Calendar.YEAR,Integer.parseInt(dates[0])); //2019
            gregorianCalendar.set(Calendar.MONTH,Integer.parseInt(dates[1])-1); // 10
            gregorianCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dates[2]));//19
            gregorianCalendar.add(Calendar.DAY_OF_MONTH,7);

            db.open();
            db.MyListInsert(BasicDB.getRecommend(context),gregorianCalendar.get(Calendar.YEAR)+"-"+(gregorianCalendar.get(Calendar.MONTH)+1)+"-"+gregorianCalendar.get(Calendar.DAY_OF_MONTH),BasicDB.getRottoN(context)+1);


    }


    private Callback callback =new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

            int pastNum = BasicDB.getRottoN(context);
            Intent service= new Intent(context, ShowNotify.class);

            service.putExtra("drwNo",pastNum);
            context.startService(service);
        }

        @Override
        public void onResponse(Call call, Response response) throws IOException {

            String result = response.body().string(); //json 결과

            Log.d("url",result);
            try {
                JSONObject jsonObject=new JSONObject(result);

                if (jsonObject.getString("returnValue").equals("success")) // 가져온 결과 값이 정확한 회차 정보가 맞다면 실패 'fail'
                {
                    String date = jsonObject.getString("drwNoDate");
                    int N1 = jsonObject.getInt("drwtNo1");
                    int N2 = jsonObject.getInt("drwtNo2"); //번호 1번
                    int N3 = jsonObject.getInt("drwtNo3"); // 번호 2번
                    int N4 = jsonObject.getInt("drwtNo4");
                    int N5 = jsonObject.getInt("drwtNo5");
                    int N6 = jsonObject.getInt("drwtNo6");
                    long winner = jsonObject.getLong("firstWinamnt"); //1등 당첨금액
                    int bonus = jsonObject.getInt("bnusNo");// 보너스 번호
                    int drwNo = jsonObject.getInt("drwNo"); // 회차 번호


                    //디비 저장
                    LottoDB db = new LottoDB(context);
                    db.open();
                    db.LottoInsert(date
                            ,N1
                            ,N2 //번호 1번
                            ,N3// 번호 2번
                            ,N4
                            ,N5
                            ,N6
                            ,winner //1등 당첨금액
                            ,bonus // 보너스 번호
                            ,drwNo); // 회차 번호

                    ArrayList<Integer> numbers = new ArrayList<>();
                    numbers.add(N1);
                    numbers.add(N2);
                    numbers.add(N3);
                    numbers.add(N4);
                    numbers.add(N5);
                    numbers.add(N6);

                    db.MyListCheck(numbers,winner,bonus,drwNo);

                    InsertRecommend(db,date);

                }

            }catch (Exception e)
            {
               e.printStackTrace();
            }finally {
                int pastNum = BasicDB.getRottoN(context);
                Intent service= new Intent(context, ShowNotify.class);

                service.putExtra("drwNo",pastNum);
                context.startService(service);
            }
        }
    };
}
