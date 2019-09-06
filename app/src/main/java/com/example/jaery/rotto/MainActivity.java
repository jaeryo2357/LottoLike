package com.example.jaery.rotto;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;



import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {

    TextView N;
    Button b;

    private GetJson httpConn = GetJson.getInstance();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        b= (Button)findViewById(R.id.B1);

        b.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ArrayList<Integer> a =new ArrayList<Integer>();

                a.add((int)(Math.random()*45)+1);
                for(int i=1;i<6;)
                {
                    int e=(int)(Math.random()*45)+1;
                    if(!a.contains(e)) {
                        a.add(e);
                        i++;
                    }

                }

                b.setVisibility(View.INVISIBLE);
                b.setEnabled(false);
                for(int i=0;i<6;i++) {
                    int resID;


                    String resourcid= "L"+(i+1);
                    resID= getResources().getIdentifier(resourcid,"id",getPackageName());
                    N=(TextView)findViewById(resID);
                    N.setText(Integer.toString(a.get(i)));
                    N.setVisibility(View.VISIBLE);

                }


            }
        });
    }

    /** 웹 서버로 데이터 전송 */
    private void sendData() {
// 네트워크 통신하는 작업은 무조건 작업스레드를 생성해서 호출 해줄 것!!
        new Thread() {
            public void run() {
// 파라미터 2개와 미리정의해논 콜백함수를 매개변수로 전달하여 호출
                httpConn.requestWebServer("데이터2", callback);

            }
        }.start();
    }

    private final Callback callback = new Callback() {
        @Override
        public void onFailure(Call call, IOException e) {

        }
        @Override
        public void onResponse(Call call, Response response) throws IOException {
            String body = response.body().string();
            Log.d("tt", "서버에서 응답한 Body:"+body);



        }
    };



}
