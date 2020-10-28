package com.lottolike.jaery.lotto.lotto.adapter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.lotto.adapter.BlankAdapter;


public class NumberBlankViewHolder extends RecyclerView.ViewHolder {

    public TextView lotto;

    public NumberBlankViewHolder(@NonNull View itemView, final BlankAdapter.OnBlankClickListener clickListener) {
        super(itemView);
        lotto = itemView.findViewById(R.id.number_blank_tv);

        if (clickListener != null)
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.OnClick(v, getAdapterPosition());
                }
            });
    }
}
