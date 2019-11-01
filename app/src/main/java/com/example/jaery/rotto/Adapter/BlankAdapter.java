package com.example.jaery.rotto.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaery.rotto.Item.blank_Item;
import com.example.jaery.rotto.LottoItem;
import com.example.jaery.rotto.R;
import com.example.jaery.rotto.ViewHolder.Number_Blank_Viewholder;

import java.util.ArrayList;

public class BlankAdapter extends RecyclerView.Adapter<Number_Blank_Viewholder> {

    ArrayList<blank_Item> items;
    Activity activity;

    OnBlankClickListener clickListener;

    public interface OnBlankClickListener{
        void OnClick(View view,int position);
    }

    public void setClickListener(OnBlankClickListener clickListener)
    {
        this.clickListener = clickListener;
    }


    public BlankAdapter(ArrayList<blank_Item> items,Activity activity)
    {
        this.activity = activity;
        this.items = items;
    }

    @NonNull
    @Override
    public Number_Blank_Viewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = activity.getLayoutInflater().inflate(R.layout.lotto_number_blank,parent,false);

        return new Number_Blank_Viewholder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Number_Blank_Viewholder holder, final int position) {

        blank_Item item = items.get(position);
        holder.lotto.setText(item.getNumber()+"");

        if(item.isClick()){
            holder.lotto.setBackgroundResource(LottoItem.GetBackgroundColor(item.getNumber()));
        }else
            holder.lotto.setBackgroundResource(LottoItem.GetBackgroundColor(46));

        if(clickListener!=null)
            holder.lotto.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    clickListener.OnClick(v,position);
                }
            });
    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
