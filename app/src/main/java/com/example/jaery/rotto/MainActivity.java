package com.example.jaery.rotto;


import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;


import com.example.jaery.rotto.Database.BasicDB;
import com.example.jaery.rotto.Database.LottoDB;
import com.example.jaery.rotto.Service.SenderAlert;


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.jaery.rotto.LottoItem.GetBackgroundColor;
import static com.example.jaery.rotto.LottoItem.GetFreeNumber;
import static com.example.jaery.rotto.LottoItem.GetNumber;


public class MainActivity extends AppCompatActivity {

    TextView Today_LottoNumber;
    TextView Today_LottoMoney;
    TextView Today_LottoDay;
    TextView Lotto;
    int recentlyNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Today_LottoNumber = findViewById(R.id.lottoResult_title);
        Today_LottoMoney = findViewById(R.id.recently_Lotto_money);
        Today_LottoDay = findViewById(R.id.lottoResult_day);


        findViewById(R.id.main_get_random_number).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this,GetNumberActivity.class);
                startActivity(intent);
            }
        });


        String recommend_Num_String="";
        if(!BasicDB.getInit(getApplicationContext())){

            recentlyNum = 877;

            GregorianCalendar init = new GregorianCalendar(2019,8,21,21,0); //2019년 9월 21일 오후 9시 0분
            GregorianCalendar now = new GregorianCalendar();

            long diff=now.getTimeInMillis()-init.getTimeInMillis();
            long sec = diff / 1000;
            long min = diff / (60 * 1000);
            long hour = diff / (60 * 60 * 1000);
            long day = diff / (24 * 60 * 60 * 1000);

            recentlyNum += day/7;

            init.add(Calendar.DAY_OF_MONTH, (int) (7*(day/7+1)));

             SenderAlert.senderAlarm(getApplicationContext(),init); //알람 설정


            BasicDB.setInit(getApplicationContext(),true); //초기화 설정 완료
            BasicDB.setRottoN(getApplicationContext(),recentlyNum);

        }else
        {
            recentlyNum = BasicDB.getRottoN(getApplicationContext());

            recommend_Num_String = BasicDB.getRecommend(getApplicationContext());
        }
        LottoGet();


        if(recommend_Num_String.equals("")) //초기 설정
        {
            ArrayList<Integer> integers = GetFreeNumber();


            for(int i = 0 ;i< integers.size();i++)
            {
                if(i!=0)recommend_Num_String +=",";

                recommend_Num_String += integers.get(i);
                int resID;
                String resourceid = "recommend_L" + (i + 1);
                resID = getResources().getIdentifier(resourceid, "id", getPackageName());
                Lotto = (TextView) findViewById(resID);
                Lotto.setBackgroundResource(GetBackgroundColor(integers.get(i)));
                Lotto.setText(integers.get(i)+"");

            }

            BasicDB.setRecommend(getApplicationContext(),recommend_Num_String);
        }else // ,로 구분
        {
            String[] recommends = recommend_Num_String.split(",");

            for(int i = 0; i<recommends.length;i++)
            {
                int n = Integer.parseInt(recommends[i]);
                int resID;
                String resourceid = "recommend_L" + (i + 1);
                resID = getResources().getIdentifier(resourceid, "id", getPackageName());
                Lotto = (TextView) findViewById(resID);
                Lotto.setBackgroundResource(GetBackgroundColor(n));
                Lotto.setText(n+"");

            }
        }



    }


    @Override
    protected void onResume() {
        super.onResume();

        String s = BasicDB.getRecommend(getApplicationContext());

        String[] recommends = s.split(",");

        for(int i = 0; i<recommends.length;i++)
        {
            int n = Integer.parseInt(recommends[i]);
            int resID;
            String resourceid = "recommend_L" + (i + 1);
            resID = getResources().getIdentifier(resourceid, "id", getPackageName());
            Lotto = (TextView) findViewById(resID);
            Lotto.setBackgroundResource(GetBackgroundColor(n));
            Lotto.setText(n+"");

        }
    }

    public void LottoGet(){
        HashMap<String,String> today = GetNumber(getApplicationContext(),recentlyNum);

        if(today.size()>0) mainSetLottoNumber(today);
        else
        {
            new Thread(){
                @Override
                public void run() {
                    GetJson json = GetJson.getInstance();
                    json.requestWebServer(recentlyNum+"",callback);
                }
            }.run();

        }
    }


    public void mainSetLottoNumber(HashMap<String,String> hashMap)
    {

        Today_LottoNumber.setText(recentlyNum+"회");

        for (int i = 0; i < 6; i++) {
            int resID;
            String resourcid = "L" + (i + 1);
            resID = getResources().getIdentifier(resourcid, "id", getPackageName());
            Lotto = (TextView) findViewById(resID);
            Lotto.setBackgroundResource(GetBackgroundColor(Integer.parseInt(hashMap.get("N" + (i + 1)))));
            Lotto.setText(hashMap.get("N" + (i + 1)));
        }
        Lotto = findViewById(R.id.bonus);
        Lotto.setBackgroundResource(GetBackgroundColor(Integer.parseInt(hashMap.get("bonusNo"))));
        Lotto.setText(hashMap.get("bonusNo"));

        long winnerMoney = Long.parseLong(hashMap.get("winner"));


        DecimalFormat format = new DecimalFormat("###,###");
        Today_LottoMoney.setText(format.format(winnerMoney)+"원");

        Today_LottoDay.setText(hashMap.get("date"));

    }




    private Callback callback =new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

            MainActivity.this.runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    Toast.makeText(MainActivity.this,"단말기 네트워크 환경을 확인해주세요.\n 정보를 불러오는데 필요합니다.",Toast.LENGTH_LONG).show();
                }
            });
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
                    LottoDB db=new LottoDB(MainActivity.this);
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
                    db.close();

                    final HashMap<String,String> newLotto = new HashMap<>();
                    newLotto.put("date",date);//날짜
                    newLotto.put("N1",""+N1);//번호1
                    newLotto.put("N2",""+N2);//번호2
                    newLotto.put("N3",""+N3);//번호3
                    newLotto.put("N4",""+N4);//번호4
                    newLotto.put("N5",""+N5);//번호5
                    newLotto.put("N6",""+N6);//번호6
                    newLotto.put("winner",""+winner);//1등 당첨금액
                    newLotto.put("bonusNo",""+bonus);//보너스 번호


                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            mainSetLottoNumber(newLotto);
                        }
                    });



                } else    //최근 회차까지 저장 완료 값은 증가된 상태
                {
                    BasicDB.setRottoN(getApplicationContext(), --recentlyNum); //값 감소 후 공유요소에 저장 -> 해당 회수 아래를 보여줌

                    MainActivity.this.runOnUiThread(new Runnable() {
                        @Override
                        public void run() {
                            LottoGet();
                        }
                    });

                }
            }catch (JSONException e)
            {

              MainActivity.this.runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Toast.makeText(MainActivity.this,"현재 서버 상태가 이상하여 올바른 로또 회차를 불러올 수 없습니다.",Toast.LENGTH_LONG).show();
                    }
                });

            }
        }
    };




}
