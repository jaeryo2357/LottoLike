package com.example.jaery.rotto;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.jaery.rotto.Database.BasicDB;
import com.example.jaery.rotto.Database.LottoDB;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;



public class MainActivity extends AppCompatActivity {

    TextView Today_LottoNumber;





    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        LottoDB db=new LottoDB(this);
        db.open();

        HashMap<String,String> today=db.GetNo(""+BasicDB.getRottoN(getApplicationContext()));

        db.close();
        for(int i=0;i<6;i++) {
            int resID;


            String resourcid= "L"+(i+1);
            resID= getResources().getIdentifier(resourcid,"id",getPackageName());
            Today_LottoNumber=(TextView)findViewById(resID);
            Today_LottoNumber.setText(today.get("N"+(i+1)));
        }


        Today_LottoNumber=findViewById(R.id.bonus);
        Today_LottoNumber.setText(today.get("bonusNo"));

    }


    public void GetFreeNumber(){
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

//        for(int i=0;i<6;i++) {
//            int resID;
//
//
//            String resourcid= "L"+(i+1);
//            resID= getResources().getIdentifier(resourcid,"id",getPackageName());
//            N=(TextView)findViewById(resID);
//            N.setText(Integer.toString(a.get(i)));
//            N.setVisibility(View.VISIBLE);
//
//        }
    }

}
