package com.lottolike.jaery.lotto.ui.mylist;

import android.content.Intent;
import android.os.Bundle;

import com.lottolike.jaery.lotto.lotto.adapter.NumberAdapter;
import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.lotto.db.LottoDB;
import com.lottolike.jaery.lotto.lotto.db.LottoPreferences;
import com.lottolike.jaery.lotto.lotto.model.BasicItem;
import com.lottolike.jaery.lotto.ui.getnumber.GetNumberActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;

import java.util.ArrayList;

public class MyListActivity extends AppCompatActivity implements MyListContract.View {

    private MyListContract.Presenter presenter;
    private NumberAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_list);

        presenter = setPresenter();

        initView();
    }

    @Override
    public MyListContract.Presenter setPresenter() {
        return new MyListPresenter(this,
                LottoDB.getInstance(this),
                LottoPreferences.Companion.getInstance(this));
    }

    private void initView() {
        swipeRefreshLayout = findViewById(R.id.swiperefresh);
        swipeRefreshLayout.setOnRefreshListener(
                new SwipeRefreshLayout.OnRefreshListener() {
                    @Override
                    public void onRefresh() {
//                        Log.i(LOG_TAG, "onRefresh called from SwipeRefreshLayout");
//
//                        // This method performs the actual data-refresh operation.
//                        // The method calls setRefreshing(false) when it's finished.
//                        myUpdateOperation();
                        presenter.onSwipeRefresh();
                    }
                }
        );

        adapter = new NumberAdapter();

        recyclerView = findViewById(R.id.my_list_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setAdapter(adapter);

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
                presenter.calculateMyList();
            }
        });
    }

    @Override
    public void onStart() {
        super.onStart();

        presenter.start();
    }


    @Override
    public void showMyList(ArrayList<BasicItem> list) {

        recyclerView.setVisibility(View.VISIBLE);
        findViewById(R.id.my_list_not_btn).setVisibility(View.GONE);
        findViewById(R.id.my_list_not_tv).setVisibility(View.GONE);

        adapter.setItems(list);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showRefreshIndicator() {
        swipeRefreshLayout.setRefreshing(true);
    }

    @Override
    public void dismissRefreshIndicator() {
        swipeRefreshLayout.setRefreshing(false);
    }

    @Override
    public void showErrorListEmpty() {
        findViewById(R.id.my_list_not_btn).setVisibility(View.VISIBLE);
        findViewById(R.id.my_list_not_tv).setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
