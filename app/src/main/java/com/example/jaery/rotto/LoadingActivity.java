package com.example.jaery.rotto;

import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.example.jaery.rotto.Database.BasicDB;
import com.example.jaery.rotto.Database.LottoDB;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class LoadingActivity extends AppCompatActivity {

    int No=200; //회차 정보

    RelativeLayout downloadLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loading);

        downloadLayout=findViewById(R.id.loading_download_body);

        if(BasicDB.getInit(getApplicationContext())) //앱을 설치하고 처음 이라면 or 아직 다운을 다 안받았다면
        {
            downloadLayout.setVisibility(View.VISIBLE);



            findViewById(R.id.loading_download).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    findViewById(R.id.loading_tw3).setVisibility(View.GONE);
                    downloadLotto();
                    No=BasicDB.getRottoN(getApplicationContext());  //200~ 현재 회차까지
                }
            });

            findViewById(R.id.loading_cancel).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    downloadLayout.setVisibility(View.GONE);
                }
            });
        }
    }



   public void downloadLotto()
   {
       final GetJson json=GetJson.getInstance();


       new Thread()
       {
           @Override
           public void run() {
                json.requestWebServer(""+No,callback);
           }
       }.run();
       //서버 통신 후 다음 회차 정보를 다운 시작

   }

   private Callback callback =new Callback() {
       @Override
       public void onFailure(Call call, IOException e) {

           LoadingActivity.this.runOnUiThread(new Runnable() {
               @Override
               public void run() {
                   Toast.makeText(LoadingActivity.this,"단말기 네트워크 환경을 확인해주세요.",Toast.LENGTH_LONG).show();
               }
           });

       }

       @Override
       public void onResponse(Call call, Response response) throws IOException {

           String result = response.body().string(); //json 결과

           try {
               JSONObject jsonObject=new JSONObject(result);

               if (jsonObject.getString("returnValue").equals("success")) // 가져온 결과 값이 정확한 회차 정보가 맞다면 실패 'fail'
               {

                   BasicDB.setRottoN(getApplicationContext(), ++No); //값 증가 후 공유요소에 저장

                   //디비 저장
                   LottoDB db=new LottoDB(LoadingActivity.this);
                   db.open();

                   db.LottoInsert(jsonObject.getString("drwNoDate")
                   ,jsonObject.getInt("drwtNo1")
                           ,jsonObject.getInt("drwtNo1") //번호 1번
                           ,jsonObject.getInt("drwtNo2") // 번호 2번
                           ,jsonObject.getInt("drwtNo3")
                           ,jsonObject.getInt("drwtNo4")
                           ,jsonObject.getInt("drwtNo5")
                           ,jsonObject.getInt("16656793504") //1등 당첨금액
                           ,jsonObject.getInt("bnusNo") // 보너스 번호
                           ,jsonObject.getInt("drwNo")); // 회차 번호
                   db.close();

                   //////////

                   LoadingActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           //퍼센트 증가 UI효과
                           downloadLotto();  //다시 다운로드 시도
                       }
                   });
               } else    //최근 회차까지 저장 완료 값은 증가된 상태
               {
                   BasicDB.setRottoN(getApplicationContext(), --No); //값 감소 후 공유요소에 저장 -> 메인에 최근 정보 보여주기 위해
                   LoadingActivity.this.runOnUiThread(new Runnable() {
                       @Override
                       public void run() {
                           //퍼센트 UI 종료
                           //엑티비티 전환 효과
                       }
                   });

               }
           }catch (JSONException e)
           {

               LoadingActivity.this.runOnUiThread(new Runnable() {
                   @Override
                   public void run() {
                       Toast.makeText(LoadingActivity.this,"현재 서버 상태가 이상하여 해당 기능은 이용하실 수 없습니다.",Toast.LENGTH_LONG).show();
                       //퍼센트 UI 종료
                       //엑티비티 전환 효과
                   }
               });

           }
       }
   };

}

