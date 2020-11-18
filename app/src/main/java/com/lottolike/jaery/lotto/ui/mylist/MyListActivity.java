package com.lottolike.jaery.lotto.ui.mylist;

import android.content.Intent;
import android.os.Bundle;

import com.lottolike.jaery.lotto.lotto.adapter.NumberAdapter;
import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.lotto.model.BasicItem;
import com.lottolike.jaery.lotto.ui.GetNumberActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.View;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity implements MyListContract.MyListView {

    private RecyclerView recyclerView;
    private MyListPresenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        presenter = new MyListPresenter(this);

        initView();
    }

    private void initView() {
        recyclerView = findViewById(R.id.my_list_recycler);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        findViewById(R.id.my_list_not_btn).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyListActivity.this, GetNumberActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.my_list_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.my_list_calculate).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.reCalculateMyList();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.start(this);
    }


    @Override
    public void showMyList(ArrayList<BasicItem> list) {

        recyclerView.setVisibility(View.VISIBLE);
        findViewById(R.id.my_list_not_btn).setVisibility(View.GONE);
        findViewById(R.id.my_list_not_tv).setVisibility(View.GONE);

        NumberAdapter adapter = new NumberAdapter(list);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showErrorListEmpty() {
        findViewById(R.id.my_list_not_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.my_list_not_tv).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }
}
