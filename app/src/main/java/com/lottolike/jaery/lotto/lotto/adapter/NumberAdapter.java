package com.lottolike.jaery.lotto.lotto.adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.lotto.util.LottoUtil;
import com.lottolike.jaery.lotto.lotto.model.BasicItem;
import com.lottolike.jaery.lotto.lotto.model.LottoListItem;
import com.lottolike.jaery.lotto.lotto.model.LottoRoundItem;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.lotto.adapter.ViewHolder.Number_List_ViewHolder;
import com.lottolike.jaery.lotto.lotto.adapter.ViewHolder.What_drwN0_ViewHolder;

import java.util.ArrayList;

public class NumberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private ArrayList<BasicItem> items;

    public NumberAdapter(ArrayList<BasicItem> item) {
        this.items = item;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if (viewType == 0) //time
        {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.what_drwn0, parent, false);
            return new What_drwN0_ViewHolder(view);
        } else {
            view = LayoutInflater.from(parent.getContext()).inflate(R.layout.number_list, parent, false);
            return new Number_List_ViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if (items.get(position).getType() == 0) {
            What_drwN0_ViewHolder viewHolder = (What_drwN0_ViewHolder) holder;
            LottoRoundItem item = (LottoRoundItem) items.get(position);

            viewHolder.times.setText(item.getRound() + "회 당첨 결과");

            viewHolder.time.setText(item.getTime());
        } else {
            Number_List_ViewHolder viewHolder = (Number_List_ViewHolder) holder;
            LottoListItem item = (LottoListItem) items.get(position);

            String[] numbers = item.getNumbers().split(",");

            int n = Integer.parseInt(numbers[0]);
            viewHolder.L1.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(n));
            viewHolder.L1.setText(n + "");
            n = Integer.parseInt(numbers[1]);
            viewHolder.L2.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(n));
            viewHolder.L2.setText(n + "");
            n = Integer.parseInt(numbers[2]);
            viewHolder.L3.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(n));
            viewHolder.L3.setText(n + "");
            n = Integer.parseInt(numbers[3]);
            viewHolder.L4.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(n));
            viewHolder.L4.setText(n + "");
            n = Integer.parseInt(numbers[4]);
            viewHolder.L5.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(n));
            viewHolder.L5.setText(n + "");
            n = Integer.parseInt(numbers[5]);
            viewHolder.L6.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(n));
            viewHolder.L6.setText(n + "");


            if (item.getLevel() == -1) {
                viewHolder.Level.setText("대기");
                viewHolder.money.setText("미정");
            } else {
                if (item.getLevel() == 6) {
                    viewHolder.Level.setText("낙점");
                } else{
                    viewHolder.Level.setText(item.getLevel() + "등");
                }

                viewHolder.money.setText(item.getMoney());

                ArrayList<Integer> integers = item.getCorrects();
                for (int i = 0; i < 6; i++) {
                    TextView Lotto = viewHolder.IndexLotto(i);
                    if (!integers.contains(i)) {
                        Lotto.setTextColor(Color.BLACK);
                        Lotto.setBackgroundResource(0);
                    } else {
                        Lotto.setTextColor(Color.WHITE);
                    }
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
