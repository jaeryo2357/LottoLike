package com.lottolike.jaery.lotto.ui.mylist;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.data.OfficialLottoData;
import com.lottolike.jaery.lotto.data.UserLottoData;
import com.lottolike.jaery.lotto.data.util.LottoUtil;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;


public class MyListViewHolder extends RecyclerView.ViewHolder {

    private TextView lottoTextViewOne;
    private TextView lottoTextViewTwo;
    private TextView lottoTextViewThree;
    private TextView lottoTextViewFour;
    private TextView lottoTextViewFive;
    private TextView lottoTextViewSix;

    private TextView money;
    private TextView level;


    private TextView getTextViewByIndex(int index) {
        switch (index)
        {
            case 0:
                return lottoTextViewOne;
            case 1:
                return lottoTextViewTwo;
            case 2:
                return lottoTextViewThree;
            case 3:
                return lottoTextViewFour;
            case 4:
                return lottoTextViewFive;
            default:
                return lottoTextViewSix;
        }
    }

    public MyListViewHolder(@NonNull View itemView) {
        super(itemView);

        level = itemView.findViewById(R.id.number_list_level);
        money = itemView.findViewById(R.id.number_list_money);

        lottoTextViewOne = itemView.findViewById(R.id.L1);
        lottoTextViewTwo = itemView.findViewById(R.id.L2);
        lottoTextViewThree = itemView.findViewById(R.id.L3);
        lottoTextViewFour = itemView.findViewById(R.id.L4);
        lottoTextViewFive = itemView.findViewById(R.id.L5);
        lottoTextViewSix = itemView.findViewById(R.id.L6);
    }

    public void bindMyList(UserLottoData userLottoData, OfficialLottoData officialLottoData) {

        String[] numbers = userLottoData.getUserNumbers().split(",");
        String[] officialNumbers = officialLottoData.getOfficialLottoNumber().split(",");


        for (int i = 0; i < numbers.length; i++) {
            Integer number = Integer.parseInt(numbers[i]);
            TextView textView = getTextViewByIndex(i);

            int position = -1;

            if (officialLottoData.getBonusNumber() != number) {
                position = Arrays.binarySearch(officialNumbers, numbers[0], new Comparator<String>() {
                    @Override
                    public int compare(String o1, String o2) {
                        return Integer.parseInt(o1) - Integer.parseInt(o2);
                    }
                });
            }

            if (position == -1 || officialLottoData.getBonusNumber() != number) {
                textView.setTextColor(Color.BLACK);
                textView.setBackgroundResource(0);
            } else {
                textView.setTextColor(Color.WHITE);
                textView.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
            }
            textView.setText(number.toString());
        }

        int levelValue = userLottoData.getLevel();
        if (levelValue == -1) {
            level.setText("대기");
            money.setText("미정");
        } else {
            if (userLottoData.getLevel() == 6) {
                level.setText("낙첨");
            } else {
                level.setText(levelValue + "등");
            }

            money.setText(userLottoData.getMoney());
        }
    }
}
