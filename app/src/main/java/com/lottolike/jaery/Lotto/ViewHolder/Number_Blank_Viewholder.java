package com.lottolike.jaery.Lotto.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.Lotto.R;



public class Number_Blank_Viewholder extends RecyclerView.ViewHolder {

    public TextView lotto;

    public Number_Blank_Viewholder(@NonNull View itemView) {
        super(itemView);

        lotto = itemView.findViewById(R.id.number_blank_tv);
    }
}
