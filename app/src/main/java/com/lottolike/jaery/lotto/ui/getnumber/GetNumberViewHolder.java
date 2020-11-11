package com.lottolike.jaery.lotto.ui.getnumber;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.R;


public class GetNumberViewHolder extends RecyclerView.ViewHolder {

    public TextView lotto;

    public GetNumberViewHolder(@NonNull View itemView, final GetNumberAdapter.OnBlankClickListener clickListener) {
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
