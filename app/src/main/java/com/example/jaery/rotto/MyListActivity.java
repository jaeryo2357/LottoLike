package com.example.jaery.rotto;

import android.content.Intent;
import android.os.Bundle;

import com.example.jaery.rotto.Adapter.NumberAdapter;
import com.example.jaery.rotto.Database.LottoDB;
import com.example.jaery.rotto.Item.BasicItem;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
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

        LottoDB lottoDB = new LottoDB(getApplicationContext());
        lottoDB.open();
        ArrayList<BasicItem> items = lottoDB.GetMyList();
        lottoDB.close();

        if(items.size()==0)
        {
            findViewById(R.id.my_list_not_btn).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(MyListActivity.this,GetNumberActivity.class);
                    startActivity(intent);
                    finish();
                }
            });
        }else{
            recyclerView = findViewById(R.id.my_list_recycler);
            recyclerView.setVisibility(View.VISIBLE);
            findViewById(R.id.my_list_not_btn).setVisibility(View.GONE);
            findViewById(R.id.my_list_not_tv).setVisibility(View.GONE);

            NumberAdapter adapter = new NumberAdapter(items,MyListActivity.this);
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

}
