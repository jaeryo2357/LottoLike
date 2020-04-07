package com.lottolike.jaery.lotto.model.Adapter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.R;



public class Number_Blank_ViewHolder extends RecyclerView.ViewHolder {

    public TextView lotto;

    public Number_Blank_ViewHolder(@NonNull View itemView) {
        super(itemView);
        lotto = itemView.findViewById(R.id.number_blank_tv);
    }
}
