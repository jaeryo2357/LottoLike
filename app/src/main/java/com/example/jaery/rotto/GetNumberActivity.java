package com.example.jaery.rotto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Collections;

import static com.example.jaery.rotto.LottoItem.GetBackgroundColor;
import static com.example.jaery.rotto.LottoItem.GetFreeNumber;

public class GetNumberActivity extends AppCompatActivity {


    RelativeLayout if_selfInput;
    int count = 0 ; // 카운트다운

    TextView L1;
    TextView L2;
    TextView L3;
    TextView L4;
    TextView L5;
    TextView L6;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);
        if_selfInput = findViewById(R.id.get_number_if_self_input);

        L1 = findViewById(R.id.L1);
        L2 = findViewById(R.id.L2);
        L3 = findViewById(R.id.L3);
        L4 = findViewById(R.id.L4);
        L5 = findViewById(R.id.L5);
        L6 = findViewById(R.id.L6);

        SetNumber(GetFreeNumber());


        findViewById(R.id.get_number_test).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if_selfInput.setVisibility(View.VISIBLE);
                findViewById(R.id.get_number_self_end).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
            }
        });
    }

    public void self_Number_Setting()
    {


        if_selfInput.setVisibility(View.GONE);
    }


    public void SetNumber(ArrayList<Integer> numbers){

        int n = numbers.get(0);
        L1.setBackgroundResource(GetBackgroundColor(n));
        L1.setText(n+"");
        n = numbers.get(1);
        L2.setBackgroundResource(GetBackgroundColor(n));
        L2.setText(n+"");
        n = numbers.get(2);
        L3.setBackgroundResource(GetBackgroundColor(n));
        L3.setText(n+"");
        n = numbers.get(3);
        L4.setBackgroundResource(GetBackgroundColor(n));
        L4.setText(n+"");
        n = numbers.get(4);
        L5.setBackgroundResource(GetBackgroundColor(n));
        L5.setText(n+"");
        n = numbers.get(5);
        L6.setBackgroundResource(GetBackgroundColor(n));
        L6.setText(n+"");

    }





}
