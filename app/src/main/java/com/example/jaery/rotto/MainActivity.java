package com.example.jaery.rotto;


import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.widget.TextView;


import com.example.jaery.rotto.Database.BasicDB;
import com.example.jaery.rotto.Database.LottoDB;


import java.util.HashMap;

import static com.example.jaery.rotto.LottoItem.GetBackgroundColor;


public class MainActivity extends AppCompatActivity {

    TextView Today_LottoNumber;
    HashMap<String,String> today;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        GetNumber(BasicDB.getRottoN(getApplicationContext()));


    }
    public void GetNumber(int N){
        LottoDB db=new LottoDB(this);
        db.open();
        today=db.GetNo(""+N);
        db.close();
        for(int i=0;i<6;i++) {
            int resID;


            String resourcid= "L"+(i+1);
            resID= getResources().getIdentifier(resourcid,"id",getPackageName());
            Today_LottoNumber=(TextView)findViewById(resID);
            Today_LottoNumber.setBackgroundResource(GetBackgroundColor(Integer.parseInt(today.get("N"+(i+1)))));
            Today_LottoNumber.setText(today.get("N"+(i+1)));
        }


        Today_LottoNumber=findViewById(R.id.bonus);
        Today_LottoNumber.setText(today.get("bonusNo"));

    }



}
