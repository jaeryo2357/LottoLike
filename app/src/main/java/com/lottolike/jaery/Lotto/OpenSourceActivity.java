package com.lottolike.jaery.Lotto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class OpenSourceActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_open_source);

        findViewById(R.id.open_source_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        TextView okHttp = findViewById(R.id.open_source_okhttp_link);

        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override public String transformUrl(Matcher match, String url) { return ""; }
        };

        Pattern pattern1 = Pattern.compile(okHttp.getText().toString());

        Linkify.addLinks(okHttp, pattern1, "https://github.com/square/okhttp",null,mTransform);



    }
}
