package com.lottolike.jaery.lotto.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.data.userlottodata.source.local.LottoPreferences;

public class SettingActivity extends AppCompatActivity {
    boolean sound_check;
    boolean vibration_check;
    Switch Switch_sound;
    Switch Switch_vibration;
    LottoPreferences sharedPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);

        sharedPreferences = LottoPreferences.Companion.getInstance(this);

        findViewById(R.id.setting_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.setting_personal).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(SettingActivity.this, PersonalActivity.class);
                startActivity(intent);
            }
        });
        findViewById(R.id.setting_open_source).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(SettingActivity.this, OpenSourceActivity.class);
                startActivity(intent);
            }
        });
        sound_check = sharedPreferences.getSound();
        vibration_check = sharedPreferences.getVibration();

        Switch_sound = findViewById(R.id.setting_alert_sound_switch);
        Switch_vibration = findViewById(R.id.setting_alert_vibration_switch);

        Switch_sound.setChecked(sound_check);
        Switch_vibration.setChecked(vibration_check);


        Switch_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.setSound(isChecked);
            }
        });

        Switch_vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                sharedPreferences.setVibration(isChecked);
            }
        });

    }
}
