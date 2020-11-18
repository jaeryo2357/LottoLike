package com.lottolike.jaery.lotto.ui.getnumber;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.lottolike.jaery.lotto.data.util.LottoUtil;
import com.lottolike.jaery.lotto.data.userlottodata.source.local.LottoDBHelper;
import com.lottolike.jaery.lotto.R;

import java.util.ArrayList;

public class GetNumberActivity extends AppCompatActivity implements GetNumberContract.View{

    private GetNumberContract.Presenter presenter;

    private TextView lottoTextViewOne;
    private TextView lottoTextViewTwo;
    private TextView lottoTextViewThree;
    private TextView lottoTextViewFour;
    private TextView lottoTextViewFive;
    private TextView lottoTextViewSix;

    private RecyclerView recyclerView;
    private GetNumberAdapter adapter;
    private ArrayList<Integer> selectedItem = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_get_number);

        presenter = setPresenter();

        initView();
        initRecyclerView();
        initButtonView();

    }

    private void initView() {

        lottoTextViewOne = findViewById(R.id.L1);
        lottoTextViewTwo = findViewById(R.id.L2);
        lottoTextViewThree = findViewById(R.id.L3);
        lottoTextViewFour = findViewById(R.id.L4);
        lottoTextViewFive = findViewById(R.id.L5);
        lottoTextViewSix = findViewById(R.id.L6);
    }

    private void initButtonView() {

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

        findViewById(R.id.get_number_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.clearButtonClick();
            }
        });

        findViewById(R.id.get_number_save).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                presenter.numberSaveButtonClick();
            }
        });
    }

    private void initRecyclerView() {
        adapter = new GetNumberAdapter(selectedItem, new GetNumberAdapter.OnBlankClickListener() {
            @Override
            public void OnClick(View view, int position) {
                presenter.selfNumberButtonClick(position);
            }
        });

        recyclerView = findViewById(R.id.lotto_btn_recycler);
        recyclerView.setLayoutManager(new GridLayoutManager(this, 5));
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
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

    @Override
    public void showRecommendView(ArrayList<Integer> recommend) {

        TextView lottoTextView;

        for (int i = 0; i < recommend.size(); i++) {
            int number = recommend.get(i);

            lottoTextView = lottoTextViewIndex(i);
            lottoTextView.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
            lottoTextView.setText(number + "");
        }

        adapter.setSelectedItem(recommend);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showSaveSuccess() {
        Toast.makeText(GetNumberActivity.this, "저장완료했습니다", Toast.LENGTH_LONG).show();
    }

    @Override
    public void showSaveError() {
        Toast.makeText(GetNumberActivity.this, "번호를 올바르게 입력해주세요", Toast.LENGTH_LONG).show();
    }

    @Override
    public void clearSelectedNumber() {
        TextView lottoTextView;

        for (int i = 0; i < 6; i++) {

            lottoTextView = lottoTextViewIndex(i);
            lottoTextView.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(-1));
            lottoTextView.setText("");
        }

        adapter.setSelectedItem(new ArrayList<Integer>());
        adapter.notifyDataSetChanged();
    }

    @Override
    public GetNumberContract.Presenter setPresenter() {
        return new GetNumberPresenter(this, );
    }
}
