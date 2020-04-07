package com.lottolike.jaery.lotto.model.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.model.blank_Item;
import com.lottolike.jaery.lotto.util.LottoItem;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.model.Adapter.ViewHolder.Number_Blank_ViewHolder;

import java.util.ArrayList;

public class BlankAdapter extends RecyclerView.Adapter<Number_Blank_ViewHolder> {

    private ArrayList<blank_Item> items;

    private OnBlankClickListener clickListener;

    public interface OnBlankClickListener{
        void OnClick(View view,int position);
    }

    public void setClickListener(OnBlankClickListener clickListener)
    {
        this.clickListener = clickListener;
    }


    public BlankAdapter(ArrayList<blank_Item> items)
    {
        this.items = items;
    }

    @NonNull
    @Override
    public Number_Blank_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.lotto_number_blank,parent,false);

        return new Number_Blank_ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull Number_Blank_ViewHolder holder, final int position) {

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
