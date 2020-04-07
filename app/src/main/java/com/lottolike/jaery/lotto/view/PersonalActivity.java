package com.lottolike.jaery.lotto.view;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.widget.TextView;

import com.lottolike.jaery.lotto.R;

public class PersonalActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_personal);

        findViewById(R.id.personal_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

        TextView textView = findViewById(R.id.personal_tv);

        textView.setText(Html.fromHtml(getString(R.string.personal)));
    }
}
