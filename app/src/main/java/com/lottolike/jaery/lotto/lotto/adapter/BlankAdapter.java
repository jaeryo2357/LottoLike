package com.lottolike.jaery.lotto.lotto.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.lotto.util.LottoUtil;
import com.lottolike.jaery.lotto.lotto.model.BlankItem;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.lotto.adapter.ViewHolder.Number_Blank_ViewHolder;

import java.util.ArrayList;

public class BlankAdapter extends RecyclerView.Adapter<Number_Blank_ViewHolder> {

    private ArrayList<BlankItem> items;

    private OnBlankClickListener clickListener;

    public interface OnBlankClickListener{
        void OnClick(View view,int position);
    }

    public void setClickListener(OnBlankClickListener clickListener)
    {
        this.clickListener = clickListener;
    }


    public BlankAdapter(ArrayList<BlankItem> items)
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

        BlankItem item = items.get(position);
        holder.lotto.setText(item.getNumber()+"");

        if(item.isClick()){
            holder.lotto.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(item.getNumber()));
        }else
            holder.lotto.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(46));

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
