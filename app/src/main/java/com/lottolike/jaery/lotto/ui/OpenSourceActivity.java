package com.lottolike.jaery.lotto.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.util.Linkify;
import android.view.View;
import android.widget.TextView;

import com.lottolike.jaery.lotto.R;

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
        TextView jsoup = findViewById(R.id.open_source_jsoup_link);
        TextView vision = findViewById(R.id.open_source_vision_link);

        Linkify.TransformFilter mTransform = new Linkify.TransformFilter() {
            @Override public String transformUrl(Matcher match, String url) { return ""; }
        };

        Pattern pattern = Pattern.compile(jsoup.getText().toString());
        Linkify.addLinks(jsoup, pattern,"https://github.com/jhy/jsoup",null,mTransform);
        pattern = Pattern.compile(vision.getText().toString());
        Linkify.addLinks(vision, pattern,"https://developers.google.com/vision",null,mTransform);
    }
}
