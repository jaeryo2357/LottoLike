package com.example.jaery.rotto.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaery.rotto.R;

public class What_drwN0_ViewHolder extends RecyclerView.ViewHolder {

    public TextView times;
    public TextView time;

    public What_drwN0_ViewHolder(@NonNull View itemView) {
        super(itemView);

        times = itemView.findViewById(R.id.what_drwn0_times);
        time = itemView.findViewById(R.id.what_drwn0_time);

    }
}
