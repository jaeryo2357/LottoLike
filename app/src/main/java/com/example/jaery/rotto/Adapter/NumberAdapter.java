package com.example.jaery.rotto.Adapter;

import android.app.Activity;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.jaery.rotto.Item.BasicItem;
import com.example.jaery.rotto.Item.List_Item;
import com.example.jaery.rotto.Item.What_DrwN0;
import com.example.jaery.rotto.LottoItem;
import com.example.jaery.rotto.R;
import com.example.jaery.rotto.ViewHolder.Number_List_ViewHolder;
import com.example.jaery.rotto.ViewHolder.What_drwN0_ViewHolder;

import java.text.DecimalFormat;
import java.util.ArrayList;

public class NumberAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {


    ArrayList<BasicItem> items;
    Activity activity;


    public NumberAdapter(ArrayList<BasicItem> item,Activity activity)
    {
        this.items = item;
        this.activity = activity;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {

        View view;
        if(viewType == 0 ) //time
        {
            view = activity.getLayoutInflater().inflate(R.layout.what_drwn0,parent,false);
            return new What_drwN0_ViewHolder(view);
        }else
        {
            view = activity.getLayoutInflater().inflate(R.layout.number_list,parent,false);
            return new Number_List_ViewHolder(view);
        }
    }

    @Override
    public int getItemViewType(int position) {
        return items.get(position).getType();
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {

        if(items.get(position).getType()==0)
        {
            What_drwN0_ViewHolder viewHolder =(What_drwN0_ViewHolder)holder;
            What_DrwN0  item = (What_DrwN0)items.get(position);

            viewHolder.times.setText(item.getDrwN0()+"회");
            viewHolder.time.setText(item.getTime());
        }else
        {
            Number_List_ViewHolder viewHolder = (Number_List_ViewHolder)holder;
            List_Item item = (List_Item)items.get(position);

            String[] numbers = item.getNumbers().split(",");

            int n = Integer.parseInt(numbers[0]);
            viewHolder.L1.setBackgroundResource(LottoItem.GetBackgroundColor(n));
            viewHolder.L1.setText(n+"");
            n = Integer.parseInt(numbers[1]);
            viewHolder.L2.setBackgroundResource(LottoItem.GetBackgroundColor(n));
            viewHolder.L2.setText(n+"");
            n = Integer.parseInt(numbers[2]);
            viewHolder.L3.setBackgroundResource(LottoItem.GetBackgroundColor(n));
            viewHolder.L3.setText(n+"");
            n = Integer.parseInt(numbers[3]);
            viewHolder.L4.setBackgroundResource(LottoItem.GetBackgroundColor(n));
            viewHolder.L4.setText(n+"");
            n = Integer.parseInt(numbers[4]);
            viewHolder.L5.setBackgroundResource(LottoItem.GetBackgroundColor(n));
            viewHolder.L5.setText(n+"");
            n = Integer.parseInt(numbers[5]);
            viewHolder.L6.setBackgroundResource(LottoItem.GetBackgroundColor(n));
            viewHolder.L6.setText(n+"");


            if(item.getLevel()==-1)
            {
                viewHolder.Level.setText("대기");
                viewHolder.money.setText("미정");
            }
            else
            {
                if(item.getLevel()==6) {
                    viewHolder.Level.setText("낙점");
                }else
                viewHolder.Level.setText(item.getLevel()+"등");

                DecimalFormat format = new DecimalFormat("###,###");
                String money= format.format(item.getMoney())+"원";

                if(item.getLevel()==3||item.getLevel()==2)
                {
                    money = "대략 "+money;
                }
                viewHolder.money.setText(money);
                ArrayList<Integer> integers = item.getCorrects();
                for(int i = 0 ;i<6;i++)
                {
                    TextView Lotto = viewHolder.IndexLotto(i);
                    if(!integers.contains(i))Lotto.setAlpha(0.2f);
                }
            }
        }

    }

    @Override
    public int getItemCount() {
        return items.size();
    }
}
