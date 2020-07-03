package com.lottolike.jaery.lotto.ui;

import android.content.Intent;
import android.os.Bundle;

import com.lottolike.jaery.lotto.lotto.db.LottoPreferences;
import com.lottolike.jaery.lotto.lotto.adapter.NumberAdapter;
import com.lottolike.jaery.lotto.lotto.db.LottoDB;
import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.lotto.model.BasicItem;
import com.lottolike.jaery.lotto.lotto.model.LottoRoundItem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity {

    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        LottoDB lottoDB = LottoDB.getInstance(this);
        ArrayList<BasicItem> items = lottoDB.getMyList();

        if (items.size() == 0) {
            findViewById(R.id.my_list_not_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyListActivity.this, GetNumberActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        } else {
            items.add(0, inputLottoRoundView());

            recyclerView = findViewById(R.id.my_list_recycler);
            recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.my_list_not_btn).setVisibility(View.GONE);
            findViewById(R.id.my_list_not_tv).setVisibility(View.GONE);

            NumberAdapter adapter = new NumberAdapter(items);
            recyclerView.setLayoutManager(new LinearLayoutManager(this));
            recyclerView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
        }

        findViewById(R.id.my_list_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    public LottoRoundItem inputLottoRoundView() {
        LottoPreferences pref = new LottoPreferences(this);

        int round = pref.getLottoNumber();
        String date = pref.getLottoDate();

        return new LottoRoundItem(0, round, date);
    }
}
