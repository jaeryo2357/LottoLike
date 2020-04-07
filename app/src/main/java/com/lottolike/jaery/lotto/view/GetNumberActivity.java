package com.lottolike.jaery.lotto.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.lottolike.jaery.lotto.model.Adapter.BlankAdapter;
import com.lottolike.jaery.lotto.util.Database.LottoDB;
import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.model.blank_Item;
import com.lottolike.jaery.lotto.util.SharedPreferences;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.GregorianCalendar;
import java.util.HashMap;

import static com.lottolike.jaery.lotto.util.LottoItem.GetBackgroundColor;
import static com.lottolike.jaery.lotto.util.LottoItem.GetFreeNumber;
import static com.lottolike.jaery.lotto.util.LottoItem.GetNumber;

public class GetNumberActivity extends AppCompatActivity {

    RelativeLayout if_selfInput;
    SharedPreferences sharedPreferences;
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

    RecyclerView recyclerView;
    BlankAdapter adapter;
    ArrayList<blank_Item> items=new ArrayList<>();
    ArrayList<Integer> blankItem=new ArrayList<>();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);
        sharedPreferences = new SharedPreferences(this);

        if_selfInput = findViewById(R.id.get_number_if_self_input);
        TextView title = findViewById(R.id.get_number_times);

        recyclerView =findViewById(R.id.lotto_btn_recycler);
        adapter=new BlankAdapter(items);

        RecyclerSetUP();

        title.setText((sharedPreferences.getLottoNumber()+1)+"회");
        methodManager = (InputMethodManager)getSystemService(INPUT_METHOD_SERVICE);

        L1 = findViewById(R.id.L1);
        L2 = findViewById(R.id.L2);
        L3 = findViewById(R.id.L3);
        L4 = findViewById(R.id.L4);
        L5 = findViewById(R.id.L5);
        L6 = findViewById(R.id.L6);



        findViewById(R.id.get_number_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(!selfString.equals("")&&selfString.split(",").length==6)
               InsertRecommend();
                else
                {
                    Toast.makeText(GetNumberActivity.this,"번호를 입력해주세요",Toast.LENGTH_LONG).show();
                }
            }
        });


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

                SetNumber(selfString);
            }
        });

        findViewById(R.id.get_number_self).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Button button = (Button) v;
                if (button.getText().toString().equals("직접 입력")) {

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



                    if_selfInput.setVisibility(View.VISIBLE);
                    findViewById(R.id.get_number_self_end).setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(checkSelf) {
                                button.setText("직접 입력");
                                SetNumber(selfString);
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

    public void RecyclerSetUP()
    {

        recyclerView.setLayoutManager(new GridLayoutManager(this,5));
        adapter.setClickListener(new BlankAdapter.OnBlankClickListener() {
            @Override
            public void OnClick(View view, int position) {
                TextView textView = (TextView) view;
                Integer n = Integer.parseInt(textView.getText().toString());
                boolean check = items.get(position).isClick();

                if(!check)
                {
                    if(blankItem.size()==6)
                    {
                        Toast.makeText(GetNumberActivity.this,"최대 6개까지 입력할 수 있습니다",Toast.LENGTH_LONG).show();
                    }else
                    {
                        blankItem.add(n);
                        items.get(position).setClick(true);
                    }
                }else
                {
                    blankItem.remove(n);
                    items.get(position).setClick(false);
                }
                adapter.notifyDataSetChanged();
                blankBtnClick();
            }
        });
        recyclerView.setAdapter(adapter);


        for(int i = 1;i<=45;i++)
        {
            items.add(new blank_Item(i,false));
        }

        if(!selfString.equals(""))
        {
            String[] numbers = selfString.split(",");
            for(int i=0;i<numbers.length;i++)
            {
                blankItem.add(Integer.parseInt(numbers[i]));
                items.get(Integer.parseInt(numbers[i])-1).setClick(true);
            }
        }
        adapter.notifyDataSetChanged();
    }


    public void SelfEditTextClear(){
        Input_L1.setText("");
        Input_L2.setText("");
        Input_L3.setText("");
        Input_L4.setText("");
        Input_L5.setText("");
        Input_L6.setText("");
    }

    public void InsertRecommend(){

        GregorianCalendar gregorianCalendar = new GregorianCalendar();
        HashMap<String,String> temp = GetNumber(getApplicationContext(),sharedPreferences.getLottoNumber());

        if(temp.size()>0)
        {
            String date = temp.get("date");

            String[] dates  = date.split("-");

            gregorianCalendar.set(Calendar.YEAR,Integer.parseInt(dates[0])); //2019
            gregorianCalendar.set(Calendar.MONTH,Integer.parseInt(dates[1])-1); // 10
            gregorianCalendar.set(Calendar.DAY_OF_MONTH,Integer.parseInt(dates[2]));//19
            gregorianCalendar.set(Calendar.HOUR_OF_DAY,21);
            gregorianCalendar.add(Calendar.DAY_OF_MONTH,7);

            LottoDB db = new LottoDB(this);
            db.open();
            db.MyListInsert(selfString,gregorianCalendar.get(Calendar.YEAR)+"-"+(gregorianCalendar.get(Calendar.MONTH)+1)+"-"+gregorianCalendar.get(Calendar.DAY_OF_MONTH),sharedPreferences.getLottoNumber()+1);

            Toast.makeText(GetNumberActivity.this,"저장완료했습니다",Toast.LENGTH_LONG).show();
        }
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

    public TextView LottoIndex(int index)
    {
        switch (index)
        {
            case 0:
                return L1;
            case 1:
                return L2;
            case 2:
                return L3;
            case 3:
                return L4;
            case 4:
                return L5;
            default:
                return L6;
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

    public void blankBtnClick(){
        selfString  = "";
        Collections.sort(blankItem);
        TextView L;
        for(int i = 0;i<6;i++)
        {
            L = LottoIndex(i);
            if(i<blankItem.size()) {
                if(i!=0) selfString = selfString + ",";
                int n = blankItem.get(i);
                L.setBackgroundResource(GetBackgroundColor(n));
                L.setText(n + "");
                selfString +=n;
            }else
            {
                L.setBackgroundResource(GetBackgroundColor(46));
                L.setText("");
            }
        }



    }

    public void SetNumber(String recommend){
        blankItem.clear();
        String[] numbers = recommend.split(",");
        TextView L;
        for(int i = 0;i<numbers.length;i++) {
            int n = Integer.parseInt(numbers[i]);
            L= LottoIndex(i);
            L.setBackgroundResource(GetBackgroundColor(n));
            L.setText(n + "");

        }

        for(int i=0;i<45;i++)
        {
            items.get(i).setClick(false);
        }

        for(int i = 0; i<numbers.length;i++)
        {
            int n = Integer.parseInt(numbers[i]);

            blankItem.add(n);
            items.get(n-1).setClick(true);
        }

        adapter.notifyDataSetChanged();

    }
}
