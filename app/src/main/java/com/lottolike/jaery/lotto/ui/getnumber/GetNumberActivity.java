package com.lottolike.jaery.lotto.ui.getnumber;

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

import com.lottolike.jaery.lotto.lotto.util.LottoUtil;
import com.lottolike.jaery.lotto.lotto.adapter.BlankAdapter;
import com.lottolike.jaery.lotto.lotto.db.LottoDB;
import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.lotto.model.BlankItem;

import java.util.ArrayList;
import java.util.Collections;

public class GetNumberActivity extends AppCompatActivity implements GetNumberContract.View{

    private GetNumberContract.Presenter presenter;

    private RelativeLayout selfInputLayout;

    private TextView lottoTextViewOne;
    private TextView lottoTextViewTwo;
    private TextView lottoTextViewThree;
    private TextView lottoTextViewFour;
    private TextView lottoTextViewFive;
    private TextView lottoTextViewSix;

    private EditText numberInputIndexOfOne;
    private EditText numberInputIndexOfTwo;
    private EditText numberInputIndexOfThree;
    private EditText numberInputIndexOfFour;
    private EditText numberInputIndexOfFive;
    private EditText numberInputIndexOfSix;

    private Button selfInputButton;

    private InputMethodManager methodManager;
    boolean checkSelf = false;

    String selfString = "";

    int focusIndex = 0;

    private RecyclerView recyclerView;
    private BlankAdapter adapter;
    private ArrayList<BlankItem> items = new ArrayList<>();
    private ArrayList<Integer> blankItem = new ArrayList<>();
g
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);

        presenter = setPresenter();

        initView();
        initEditTextView();
        initRecyclerView();
        initButtonView();

        methodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);

    }

    private void initView() {

        selfInputLayout = findViewById(R.id.get_number_if_self_input);

        lottoTextViewOne = findViewById(R.id.L1);
        lottoTextViewTwo = findViewById(R.id.L2);
        lottoTextViewThree = findViewById(R.id.L3);
        lottoTextViewFour = findViewById(R.id.L4);
        lottoTextViewFive = findViewById(R.id.L5);
        lottoTextViewSix = findViewById(R.id.L6);
    }

    private void initButtonView() {

        selfInputButton = findViewById(R.id.get_number_self);

        findViewById(R.id.get_number_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.get_number_recommend).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.recommendButtonClick();
            }
        });

        findViewById(R.id.get_number_self).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                final Button button = (Button) v;
                if (button.getText().toString().equals("직접 입력")) {
                    presenter.selfInputButtonClick();
                } else {
                    presenter.selfInputCancelButtonClick();
                }
            }
        });

        findViewById(R.id.get_number_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (!selfString.equals("") && selfString.split(",").length == 6)
                    InsertRecommend();
                else {
                    Toast.makeText(GetNumberActivity.this, "번호를 입력해주세요", Toast.LENGTH_LONG).show();
                }
            }
        });

        findViewById(R.id.get_number_self_end).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkSelf) {
                    button.setText("직접 입력");
                    setRecommendNumber(selfString);
                    selfNumberSettingEND();
                    SelfEditTextClear();
                    checkSelf = false;
                } else {
                    Toast.makeText(GetNumberActivity.this, "숫자를 올바르게 중복없이 입력해주세요", Toast.LENGTH_LONG).show();
                }
            }
        });

    }

    private void initEditTextView() {

        //EditText 초기화
        numberInputIndexOfOne = findViewById(R.id.input_L1);
        numberInputIndexOfTwo = findViewById(R.id.input_L2);
        numberInputIndexOfThree = findViewById(R.id.input_L3);
        numberInputIndexOfFour = findViewById(R.id.input_L4);
        numberInputIndexOfFive = findViewById(R.id.input_L5);
        numberInputIndexOfSix = findViewById(R.id.input_L6);

        numberInputIndexOfOne.setOnFocusChangeListener(focusChangeListener);
        numberInputIndexOfTwo.setOnFocusChangeListener(focusChangeListener);
        numberInputIndexOfThree.setOnFocusChangeListener(focusChangeListener);
        numberInputIndexOfFour.setOnFocusChangeListener(focusChangeListener);
        numberInputIndexOfFive.setOnFocusChangeListener(focusChangeListener);
        numberInputIndexOfSix.setOnFocusChangeListener(focusChangeListener);

        numberInputIndexOfOne.addTextChangedListener(recommend_Edit);
        numberInputIndexOfTwo.addTextChangedListener(recommend_Edit);
        numberInputIndexOfThree.addTextChangedListener(recommend_Edit);
        numberInputIndexOfFour.addTextChangedListener(recommend_Edit);
        numberInputIndexOfFive.addTextChangedListener(recommend_Edit);
        numberInputIndexOfSix.addTextChangedListener(recommend_Edit);
    }

    private void initRecyclerView() {
        adapter = new BlankAdapter(items);

        recyclerView = findViewById(R.id.lotto_btn_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        adapter.setClickListener(new BlankAdapter.OnBlankClickListener() {
            @Override
            public void OnClick(View view, int position) {
                TextView textView = (TextView) view;
                Integer n = Integer.parseInt(textView.getText().toString());
                boolean check = items.get(position).isClick();

                if (!check) {
                    if (blankItem.size() == 6) {
                        Toast.makeText(GetNumberActivity.this, "최대 6개까지 입력할 수 있습니다", Toast.LENGTH_LONG).show();
                    } else {
                        blankItem.add(n);
                        items.get(position).setClick(true);
                    }
                } else {
                    blankItem.remove(n);
                    items.get(position).setClick(false);
                }
                adapter.notifyDataSetChanged();
                blankBtnClick();
            }
        });
        recyclerView.setAdapter(adapter);


        for (int i = 1; i <= 45; i++) {
            items.add(new BlankItem(i, false));
        }

        if (!selfString.equals("")) {
            String[] numbers = selfString.split(",");
            for (int i = 0; i < numbers.length; i++) {
                blankItem.add(Integer.parseInt(numbers[i]));
                items.get(Integer.parseInt(numbers[i]) - 1).setClick(true);
            }
        }
        adapter.notifyDataSetChanged();
    }

    public void InsertRecommend() {

        LottoDB db = LottoDB.getInstance(this);
        db.myListInsert(selfString);

        Toast.makeText(GetNumberActivity.this, "저장완료했습니다", Toast.LENGTH_LONG).show();
    }

    private View.OnFocusChangeListener focusChangeListener = new View.OnFocusChangeListener() {
        @Override
        public void onFocusChange(View v, boolean hasFocus) {
            if (hasFocus) {
                ((EditText) v).setBackgroundResource(R.drawable.stroke);
                setFocusIndex(v.getId());

            } else {
                ((EditText) v).setBackgroundResource(R.drawable.stroke_not_focus);
            }
        }
    };


    public boolean selfInputCheck() {

        ArrayList<Integer> checks = new ArrayList<>();

        for (int i = 0; i < 6; i++) {
            EditText editText = isFocus(i);
            if (editText.getText().toString().equals("")) {
                return false;
            }
            int n = Integer.parseInt(editText.getText().toString());


            if (checks.contains(n)) {
                return false;
            } else {
                checks.add(n);
            }
        }

        Collections.sort(checks);

        for (int i = 0; i < checks.size(); i++) {
            if (i != 0) {
                selfString += ",";

            }
            selfString += checks.get(i) + "";
        }

        return true; //완벽
    }


    public void setFocusIndex(int res) {
        switch (res) {
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
                focusIndex = 3;
                break;
            case R.id.input_L5:
                focusIndex = 4;
                break;
            case R.id.input_L6:
                focusIndex = 5;
                break;
        }
    }

    public EditText isFocus(int index) {
        switch (index) {
            case 0:
                return numberInputIndexOfOne;
            case 1:
                return numberInputIndexOfTwo;
            case 2:
                return numberInputIndexOfThree;
            case 3:
                return numberInputIndexOfFour;
            case 4:
                return numberInputIndexOfFive;
            default:
                return numberInputIndexOfSix;
        }
    }

    public TextView lottoTextViewIndex(int index) {
        switch (index) {
            case 0:
                return lottoTextViewOne;
            case 1:
                return lottoTextViewTwo;
            case 2:
                return lottoTextViewThree;
            case 3:
                return lottoTextViewFour;
            case 4:
                return lottoTextViewFive;
            default:
                return lottoTextViewSix;
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
            selfString = "";
            try {
                int n = Integer.parseInt(s.toString());

                if (n <= 0) {
                    isFocus(focusIndex).setText("1");
                    Toast.makeText(GetNumberActivity.this, "로또 번호는 1번부터 시작됩니다.", Toast.LENGTH_LONG).show();
                } else if (n > 45) {
                    isFocus(focusIndex).setText("45");
                    Toast.makeText(GetNumberActivity.this, "로또 번호는 45번이 마지막입니다.", Toast.LENGTH_LONG).show();
                } else {
                    if (selfInputCheck())
                        selfNumberInputPerfect();
                    else
                        selfNumberInputNonPerfect();
                }
            } catch (NumberFormatException e) {
                selfNumberInputNonPerfect();
            }
        }
    };


    private void selfNumberInputPerfect() {

        Button button = findViewById(R.id.get_number_self_end);
        checkSelf = true;
        button.setBackgroundResource(R.drawable.corner_square);
    }

    private void selfNumberInputNonPerfect() {
        checkSelf = false;
        Button button = findViewById(R.id.get_number_self_end);
        button.setBackgroundResource(R.drawable.corner_square_not_input);
    }

    public void blankBtnClick() {
        selfString = "";
        Collections.sort(blankItem);
        TextView L;
        for (int i = 0; i < 6; i++) {
            L = lottoTextViewIndex(i);
            if (i < blankItem.size()) {
                if (i != 0) selfString = selfString + ",";
                int number = blankItem.get(i);
                L.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
                L.setText(number + "");
                selfString += number;
            } else {
                L.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(-1));
                L.setText("");
            }
        }
    }


    @Override
    public void clearRecommendView() {
        numberInputIndexOfOne.setText("");
        numberInputIndexOfTwo.setText("");
        numberInputIndexOfThree.setText("");
        numberInputIndexOfFour.setText("");
        numberInputIndexOfFive.setText("");
        numberInputIndexOfSix.setText("");
    }

    @Override
    public void hideSelfInputView() {
        checkSelf = false;
        selfInputButton.setText("직접 입력");
        methodManager.hideSoftInputFromWindow(numberInputIndexOfOne.getWindowToken(), 0);
        selfInputLayout.setVisibility(View.GONE);
    }

    @Override
    public void showRecommendView(ArrayList<Integer> recommend) {
        blankItem.clear();

        TextView lottoTextView;

        for (int i = 0; i < 45; i++) {
            items.get(i).setClick(false);
        }

        for (int i = 0; i < recommend.size(); i++) {
            int number = recommend.get(i);

            lottoTextView = lottoTextViewIndex(i);
            lottoTextView.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
            lottoTextView.setText(number + "");

            blankItem.add(number);
            items.get(number).setClick(true);
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSelfInputView() {
        selfInputButton.setText("취소");
        selfInputLayout.setVisibility(View.VISIBLE);
    }

    @Override
    public GetNumberContract.Presenter setPresenter() {
        return new GetNumberPresenter(this);
    }
}
