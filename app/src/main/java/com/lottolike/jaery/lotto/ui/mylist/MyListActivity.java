package com.lottolike.jaery.lotto.ui.mylist;

import android.content.Intent;
import android.os.Bundle;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;
import com.lottolike.jaery.lotto.data.userlottodata.source.local.LottoDBHelper;
import com.lottolike.jaery.lotto.data.userlottodata.source.local.LottoPreferences;
import com.lottolike.jaery.lotto.ui.getnumber.GetNumberActivity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import java.util.List;

public class MyListActivity extends AppCompatActivity implements MyListContract.View {

    private MyListContract.Presenter presenter;

    private MyListAdapter adapter;
    private RecyclerView recyclerView;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView myListLottoRound;
    private TextView myListLottoDate;
    private TextView myListEmptyTextView;
    private Button myListEmptyButton;

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
                LottoDBHelper.getInstance(this),
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

        recyclerView = findViewById(R.id.mylist_recycler);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemViewCacheSize(10);

        myListLottoDate = findViewById(R.id.mylist_lottodate);
        myListLottoRound = findViewById(R.id.mylist_lottoround);
        myListEmptyTextView = findViewById(R.id.mylist_not_tv);
        myListEmptyButton = findViewById(R.id.my_list_not_btn);

        myListEmptyButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MyListActivity.this, GetNumberActivity.class);
                startActivity(intent);
                finish();
            }
        });

        findViewById(R.id.mylist_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.mylist_calculate).setOnClickListener(new View.OnClickListener() {
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
    public void showMyList(List<UserLottoData> userData, OfficialLottoMainData officialLottoMainData) {

        recyclerView.setVisibility(View.VISIBLE);
        myListEmptyButton.setVisibility(View.GONE);
        myListEmptyTextView.setVisibility(View.GONE);

        adapter = new MyListAdapter(userData, officialLottoMainData);
        recyclerView.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    @Override
    public void showOfficialDate(OfficialLottoMainData officialLottoMainData) {
        myListLottoRound.setText(officialLottoMainData.getLottoRound() + "");
        myListLottoDate.setText(officialLottoMainData.getLottoDate());
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
        myListEmptyButton.setVisibility(View.VISIBLE);
        myListEmptyTextView.setVisibility(View.VISIBLE);
        recyclerView.setVisibility(View.GONE);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
