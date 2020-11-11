package com.lottolike.jaery.lotto.ui.getnumber;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.data.util.LottoUtil;

import com.lottolike.jaery.lotto.R;

import java.util.ArrayList;

public class GetNumberAdapter extends RecyclerView.Adapter<GetNumberViewHolder> {

    private ArrayList<Integer> selectedItem;

    private OnBlankClickListener clickListener;

    public interface OnBlankClickListener {
        void OnClick(View view, int position);
    }

    public void setClickListener(OnBlankClickListener clickListener) {
        this.clickListener = clickListener;
    }

    public void setSelectedItem(ArrayList<Integer> item) {
        this.selectedItem = item;
    }

    public GetNumberAdapter(ArrayList<Integer> items, OnBlankClickListener listener) {
        this.selectedItem = items;
        this.clickListener = listener;
    }

    @NonNull
    @Override
    public GetNumberViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lotto_number_blank, parent, false);
        return new GetNumberViewHolder(view, clickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull GetNumberViewHolder holder, final int position) {

        Integer value = position;
        holder.lotto.setText(value.toString());

        if (selectedItem.contains(value)) {
            holder.lotto.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(value));
            holder.lotto.setTextColor(Color.WHITE);
        } else {
            holder.lotto.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(-1));
            holder.lotto.setTextColor(Color.BLACK);
        }
    }

    @Override
    public int getItemCount() {
        return 45;
    }
}
