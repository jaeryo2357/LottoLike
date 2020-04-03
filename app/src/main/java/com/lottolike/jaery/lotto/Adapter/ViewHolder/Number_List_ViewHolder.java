package com.lottolike.jaery.lotto.Adapter.ViewHolder;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.R;


public class Number_List_ViewHolder extends RecyclerView.ViewHolder {

    public TextView L1;
    public TextView L2;
    public TextView L3;
    public TextView L4;
    public TextView L5;
    public TextView L6;

    public TextView money;
    public TextView Level;


    public TextView IndexLotto(int index)
    {
        switch (index)
        {
            case 0:
                return L1;
            case 1:
                return L2;
            case 2:
                return L3;
            case 3:
                return L4;
            case 4:
                return L5;
            default:
                return L6;
        }
    }

    public Number_List_ViewHolder(@NonNull View itemView) {
        super(itemView);

        Level = itemView.findViewById(R.id.number_list_level);
        money = itemView.findViewById(R.id.number_list_money);

        L1 = itemView.findViewById(R.id.L1);
        L2 = itemView.findViewById(R.id.L2);
        L3 = itemView.findViewById(R.id.L3);
        L4 = itemView.findViewById(R.id.L4);
        L5 = itemView.findViewById(R.id.L5);
        L6 = itemView.findViewById(R.id.L6);
    }
}
