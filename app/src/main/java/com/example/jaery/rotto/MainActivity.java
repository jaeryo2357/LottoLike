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


import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.text.DecimalFormat;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.jaery.rotto.LottoItem.GetBackgroundColor;
import static com.example.jaery.rotto.LottoItem.GetNumber;


public class MainActivity extends AppCompatActivity {

    TextView Today_LottoNumber;
    TextView Today_LottoMoney;
    TextView Today_LottoDay;
    int recentlyNum = 0;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Today_LottoNumber = findViewById(R.id.lottoResult_title);
        Today_LottoMoney = findViewById(R.id.recently_Lotto_money);
        Today_LottoDay = findViewById(R.id.lottoResult_day);
        recentlyNum = BasicDB.getRottoN(getApplicationContext());
        LottoGet();

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
            Today_LottoNumber = (TextView) findViewById(resID);
            Today_LottoNumber.setBackgroundResource(GetBackgroundColor(Integer.parseInt(hashMap.get("N" + (i + 1)))));
            Today_LottoNumber.setText(hashMap.get("N" + (i + 1)));
        }
        Today_LottoNumber = findViewById(R.id.bonus);
        Today_LottoNumber.setText(hashMap.get("bonusNo"));

        int winnerMoney = Integer.parseInt(hashMap.get("winner"));
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
                            int winner = jsonObject.getInt("firstWinamnt"); //1등 당첨금액
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
