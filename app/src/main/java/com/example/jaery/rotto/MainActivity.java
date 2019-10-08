package com.example.jaery.rotto;


import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;


import com.example.jaery.rotto.Database.BasicDB;
import com.example.jaery.rotto.Database.LottoDB;
import com.jaredrummler.materialspinner.MaterialSpinner;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

import static com.example.jaery.rotto.LottoItem.GetBackgroundColor;


public class MainActivity extends AppCompatActivity {

    TextView Today_LottoNumber;
    HashMap<String,String> today;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);



        GetNumber(BasicDB.getRottoN(getApplicationContext()));



        MaterialSpinner spinner = (MaterialSpinner) findViewById(R.id.main_spinner);
        LottoDB db=new LottoDB(this);
        db.open();
        spinner.setItems(db.GetList());
        db.close();

        spinner.setOnItemSelectedListener(new MaterialSpinner.OnItemSelectedListener<String>() {

            @Override public void onItemSelected(MaterialSpinner view, int position, long id, String item) {
//                Snackbar.make(view, "Clicked " + item, Snackbar.LENGTH_LONG).show();

               String num= item.split("회")[0];
               GetNumber(Integer.parseInt(num));
            }
        });


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
