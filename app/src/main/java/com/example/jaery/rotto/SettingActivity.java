package com.example.jaery.rotto;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.Switch;

import com.example.jaery.rotto.Database.BasicDB;

public class SettingActivity extends AppCompatActivity {


    boolean sound_check;
    boolean vibration_check;

    Switch Switch_sound;
    Switch Switch_vibration;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_setting);


        findViewById(R.id.setting_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        sound_check = BasicDB.getAlert_sound(getApplicationContext());
        vibration_check = BasicDB.getAlert_vibradtion(getApplicationContext());

        Switch_sound = findViewById(R.id.setting_alert_sound_switch);
        Switch_vibration = findViewById(R.id.setting_alert_vibration_switch);

        Switch_sound.setChecked(sound_check);
        Switch_vibration.setChecked(vibration_check);


        Switch_sound.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BasicDB.setAlert_sound(getApplicationContext(),isChecked);
            }
        });

        Switch_vibration.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                BasicDB.setAlert_vibration(getApplicationContext(),isChecked);
            }
        });

    }
}
