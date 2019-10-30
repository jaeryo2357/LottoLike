package com.example.jaery.rotto;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.jaery.rotto.Database.BasicDB;

import java.lang.reflect.Array;
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

    EditText Input_L1;
    EditText Input_L2;
    EditText Input_L3;
    EditText Input_L4;
    EditText Input_L5;
    EditText Input_L6;

    InputMethodManager methodManager;

    boolean checkSelf = false;

    String selfString = "";

    int focusIndex = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);
        if_selfInput = findViewById(R.id.get_number_if_self_input);

        methodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        L1 = findViewById(R.id.L1);
        L2 = findViewById(R.id.L2);
        L3 = findViewById(R.id.L3);
        L4 = findViewById(R.id.L4);
        L5 = findViewById(R.id.L5);
        L6 = findViewById(R.id.L6);


        SetNumber(BasicDB.getRecommend(getApplicationContext()));


        findViewById(R.id.get_number_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.get_number_again).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ArrayList<Integer> integers = GetFreeNumber();
                selfString= "";

                for(int i = 0 ;i< integers.size();i++)
                {
                    if(i!=0)selfString +=",";

                    selfString += integers.get(i);
                }
                BasicDB.setRecommend(getApplicationContext(),selfString);
                SetNumber(selfString);
                selfString ="";
            }
        });

        findViewById(R.id.get_number_self).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Button button = (Button) v;
                if (button.getText().toString().equals("직접 입력")) {

                    selfString="";
                    button.setText("취소");
                    Input_L1 = findViewById(R.id.input_L1);
                    Input_L2 = findViewById(R.id.input_L2);
                    Input_L3 = findViewById(R.id.input_L3);
                    Input_L4 = findViewById(R.id.input_L4);
                    Input_L5 = findViewById(R.id.input_L5);
                    Input_L6 = findViewById(R.id.input_L6);

                    Input_L1.setOnFocusChangeListener(focusChangeListener);
                    Input_L2.setOnFocusChangeListener(focusChangeListener);
                    Input_L3.setOnFocusChangeListener(focusChangeListener);
                    Input_L4.setOnFocusChangeListener(focusChangeListener);
                    Input_L5.setOnFocusChangeListener(focusChangeListener);
                    Input_L6.setOnFocusChangeListener(focusChangeListener);

                    Input_L1.addTextChangedListener(recommend_Edit);
                    Input_L2.addTextChangedListener(recommend_Edit);
                    Input_L3.addTextChangedListener(recommend_Edit);
                    Input_L4.addTextChangedListener(recommend_Edit);
                    Input_L5.addTextChangedListener(recommend_Edit);
                    Input_L6.addTextChangedListener(recommend_Edit);

                    SetSelfNumber(BasicDB.getRecommend(getApplicationContext()));

                    if_selfInput.setVisibility(View.VISIBLE);
                    findViewById(R.id.get_number_self_end).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(checkSelf) {
                                button.setText("직접 입력");
                                SetNumber(selfString);
                                BasicDB.setRecommend(getApplicationContext(),selfString);
                                self_Number_Setting_END();
                                SelfEditTextClear();
                                checkSelf = false;
                            }
                            else
                            {
                                Toast.makeText(GetNumberActivity.this,"숫자를 올바르게 중복없이 입력해주세요",Toast.LENGTH_LONG).show();
                            }
                        }
                    });
                }else //취소
                {
                    button.setText("직접 입력");
                    self_Number_Setting_END();
                    checkSelf = false;
                    SelfEditTextClear();
                }
            }
        });
    }

    public void SelfEditTextClear(){
        Input_L1.setText("");
        Input_L2.setText("");
        Input_L3.setText("");
        Input_L4.setText("");
        Input_L5.setText("");
        Input_L6.setText("");
    }

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if(hasFocus)
            {
                ((EditText)v).setBackgroundResource(R.drawable.stroke);
                SetFocusIndex(v.getId());

            }else
            {
                ((EditText)v).setBackgroundResource(R.drawable.stroke_not_focus);
            }
        }
    };


    public boolean Self_InputCheck(){

        ArrayList<Integer> checks = new ArrayList<>();

        for(int i = 0 ;i<6;i++)
        {
            EditText editText = isFocus(i);
            if(editText.getText().toString().equals(""))
            {
                return false;
            }
            int n = Integer.parseInt(editText.getText().toString());


            if(checks.contains(n)) {
                return false;
            }
            else
            {
                checks.add(n);
            }
        }

        Collections.sort(checks);

        for(int i = 0 ;i<checks.size();i++)
        {
            if(i!=0)
            {
                selfString +=",";

            }
            selfString += checks.get(i)+"";
        }

        return true; //완벽
    }

    public void self_Number_Setting_END()
    {
        selfString = "";
        methodManager.hideSoftInputFromWindow(Input_L1.getWindowToken(),0);
        if_selfInput.setVisibility(View.GONE);
    }

    public void SetFocusIndex(int res)
    {
        switch (res)
        {
            case R.id.input_L1:
                focusIndex = 0;
                break;
            case R.id.input_L2:
                focusIndex = 1;
                break;
            case R.id.input_L3:
                focusIndex = 2;
                break;

            case R.id.input_L4:
                focusIndex =3;
                break;
            case R.id.input_L5:
                focusIndex = 4;
                break;
            case R.id.input_L6:
                focusIndex = 5;
                break;
        }
    }

    public EditText isFocus(int index)
    {
        switch (index)
        {
            case 0:
                return Input_L1;
            case 1:
                return Input_L2;
            case 2:
                return Input_L3;
            case 3:
                return Input_L4;
            case 4:
                return Input_L5;
            default:
                return Input_L6;
        }
    }

    private TextWatcher recommend_Edit = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {

            selfString="";
            try {
                int n = Integer.parseInt(s.toString());

                if (n <= 0) {
                    isFocus(focusIndex).setText("1");
                    Toast.makeText(GetNumberActivity.this, "로또 번호는 1번부터 시작됩니다.", Toast.LENGTH_LONG).show();
                } else if (n > 45) {
                    isFocus(focusIndex).setText("45");
                    Toast.makeText(GetNumberActivity.this, "로또 번호는 45번이 마지막입니다.", Toast.LENGTH_LONG).show();
                }else
                {
                    if(Self_InputCheck())
                        self_Number_Input_Perfect();
                    else
                        self_Number_Input_NonPerfect();
                }
            }catch (NumberFormatException e)
            {
               self_Number_Input_NonPerfect();
            }

        }
    };


    public void self_Number_Input_Perfect(){

        Button button = findViewById(R.id.get_number_self_end);
        checkSelf = true;
        button.setBackgroundResource(R.drawable.corner_square);
    }

    public void self_Number_Input_NonPerfect(){
        checkSelf = false;
        Button button = findViewById(R.id.get_number_self_end);
        button.setBackgroundResource(R.drawable.corner_square_not_input);
    }

    public void SetNumber(String recommend){

        String[] numbers = recommend.split(",");

        Log.d("test_1",recommend);

        int n = Integer.parseInt(numbers[0]);
        L1.setBackgroundResource(GetBackgroundColor(n));
        L1.setText(n+"");
        n = Integer.parseInt(numbers[1]);
        L2.setBackgroundResource(GetBackgroundColor(n));
        L2.setText(n+"");
        n = Integer.parseInt(numbers[2]);
        L3.setBackgroundResource(GetBackgroundColor(n));
        L3.setText(n+"");
        n =Integer.parseInt(numbers[3]);
        L4.setBackgroundResource(GetBackgroundColor(n));
        L4.setText(n+"");
        n = Integer.parseInt(numbers[4]);
        L5.setBackgroundResource(GetBackgroundColor(n));
        L5.setText(n+"");
        n = Integer.parseInt(numbers[5]);
        L6.setBackgroundResource(GetBackgroundColor(n));
        L6.setText(n+"");

    }

    public void SetSelfNumber(String recommend){

        String[] numbers = recommend.split(",");

        int n = Integer.parseInt(numbers[0]);
        Input_L1.setText(n+"");
        n = Integer.parseInt(numbers[1]);

        Input_L2.setText(n+"");
        n = Integer.parseInt(numbers[2]);

        Input_L3.setText(n+"");
        n =Integer.parseInt(numbers[3]);

        Input_L4.setText(n+"");
        n = Integer.parseInt(numbers[4]);

        Input_L5.setText(n+"");
        n = Integer.parseInt(numbers[5]);

        Input_L6.setText(n+"");

    }





}
