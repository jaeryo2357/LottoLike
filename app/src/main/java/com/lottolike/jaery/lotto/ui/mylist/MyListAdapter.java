package com.lottolike.jaery.lotto.ui.mylist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.userlottodata.UserLottoData;

import com.lottolike.jaery.lotto.R;

import java.util.List;

public class MyListAdapter extends RecyclerView.Adapter<MyListViewHolder> {
    private final List<UserLottoData> userLottoData;
    private final OfficialLottoMainData officialLottoMainData;

    public MyListAdapter(List<UserLottoData> userLottoData, OfficialLottoMainData officialLottoMainData) {
        this.userLottoData = userLottoData;
        this.officialLottoMainData = officialLottoMainData;
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

        holder.bindMyList(data, officialLottoMainData);
    }

    @Override
    public int getItemCount() {
        return userLottoData.size();
    }

}
