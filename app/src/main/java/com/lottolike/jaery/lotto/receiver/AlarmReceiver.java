package com.lottolike.jaery.lotto.receiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;

import com.lottolike.jaery.lotto.lotto.db.LottoDB;
import com.lottolike.jaery.lotto.util.FirebaseExt;
import com.lottolike.jaery.lotto.service.SenderAlert;
import com.lottolike.jaery.lotto.service.ShowNotify;
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences;

import org.json.JSONObject;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;


import kotlin.Unit;
import kotlin.jvm.functions.Function1;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.lottolike.jaery.lotto.util.LottoItem.GetFreeNumber;


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

        int pastNum = sharedPreferences.getLottoNumber();

        sharedPreferences.setLottoNumber(++pastNum);

        FirebaseExt.INSTANCE.getLottoNumber(new Function1<Integer, Unit>() {
                                                @Override
                                                public Unit invoke(Integer num) {
                                                    if(!num.equals(-1)){
                                                        if(sharedPreferences.getLottoNumber()>num) {
                                                            FirebaseExt.INSTANCE.updateLottoNumber(sharedPreferences.getLottoNumber());
                                                        }
                                                    }
                                                    return Unit.INSTANCE;
                                                }
                                            }
        );

        //////////////////////////// 새로운 추천 번호 ///////////////////////
        ArrayList<Integer> integers = GetFreeNumber();
        String recommend_Num_String = "";

        for(int i = 0 ;i< integers.size();i++)
        {
            if(i!=0)recommend_Num_String +=",";

            recommend_Num_String += integers.get(i);
        }
        sharedPreferences.setRecommend(recommend_Num_String);
        //////////////////////////////////////////////////////////////

        new Thread(){
            @Override
            public void run() {
                GetJson json = GetJson.getInstance();
                json.requestWebServer(sharedPreferences.getLottoNumber()+"",callback);
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
            db.MyListInsert(sharedPreferences.getRecommend(),gregorianCalendar.get(Calendar.YEAR)+"-"+(gregorianCalendar.get(Calendar.MONTH)+1)+"-"+gregorianCalendar.get(Calendar.DAY_OF_MONTH),sharedPreferences.getLottoNumber()+1);
    }


    private Callback callback =new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

            int pastNum = sharedPreferences.getLottoNumber();
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
                int pastNum = sharedPreferences.getLottoNumber();
                Intent service= new Intent(context, ShowNotify.class);
                service.putExtra("drwNo",pastNum);
                context.startService(service);
            }
        }
    };
}
