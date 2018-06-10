package com.example.jaery.rotto;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.HashMap;

public class MainActivity extends AppCompatActivity {

    TextView N;
    Button b;



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
}