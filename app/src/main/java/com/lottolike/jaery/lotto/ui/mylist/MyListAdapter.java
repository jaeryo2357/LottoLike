package com.lottolike.jaery.lotto.ui.mylist;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.data.OfficialLottoData;
import com.lottolike.jaery.lotto.data.UserLottoData;

import com.lottolike.jaery.lotto.R;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListViewHolder> {
    private final List<UserLottoData> userLottoData;
    private final OfficialLottoData officialLottoData;

    public MyListAdapter(List<UserLottoData> userLottoData, OfficialLottoData officialLottoData) {
        this.userLottoData = userLottoData;
        this.officialLottoData = officialLottoData;
    }

    @NonNull
    @Override
    public MyListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_list, parent, false);
        return new MyListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyListViewHolder holder, int position) {

        UserLottoData data = userLottoData.get(position);

        holder.bindMyList(data, officialLottoData);
    }

    @Override
    public int getItemCount() {
        return userLottoData.size();
    }

}
