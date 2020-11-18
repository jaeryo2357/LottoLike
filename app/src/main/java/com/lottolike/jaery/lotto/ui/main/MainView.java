package com.lottolike.jaery.lotto.ui.main;

import android.content.Context;

import android.view.View;
import android.widget.TextView;

import com.lottolike.jaery.lotto.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lottolike.jaery.lotto.lotto.util.LottoUtil;

import java.util.ArrayList;

public class MainView {

    View view;
    MainController controller;
    Context context;

    MainView(MainController controller, View view) {
        this.view = view;
        this.controller = controller;
        this.context = controller;

        initView();
        initAds();
    }

    private void initView() {

        view.findViewById(R.id.main_qrCode).setOnClickListener(controller);
        view.findViewById(R.id.main_myList).setOnClickListener(controller);
        view.findViewById(R.id.main_info_image_btn).setOnClickListener(controller);
        view.findViewById(R.id.main_get_random_number).setOnClickListener(controller);
        view.findViewById(R.id.main_setting).setOnClickListener(controller);
    }

    private void initAds() {

        MobileAds.initialize(context, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = view.findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    public void mainSetLottoRound(int round) {
        TextView lottoRound = view.findViewById(R.id.lottoResult_title);
        lottoRound.setText(round + "회");
    }

    public void mainSetLottoRoundDate(String date) {
        TextView lottoRoundDate = view.findViewById(R.id.lottoResult_day);
        lottoRoundDate.setText(date);
    }

    /**
     * 매 앱이 실행 될때마다 새로운 추천번호 보여주기
     * @param numbers : String ex) 1,2,3,4,5,6
     */
    public void mainSetRecommendNumber(ArrayList<Integer> numbers) {
        TextView Lotto;
        for(int index = 0; index < numbers.size(); index++)
        {
            int number = numbers.get(index);
            String resourceid = "recommend_L" + (index + 1);
            int resID = context.getResources().getIdentifier(resourceid, "id", context.getPackageName());
            Lotto = view.findViewById(resID);
            Lotto.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
            Lotto.setText(number+"");
        }
    }

    /**
     * 이번주 로또 회차 번호 적용시키기
     * @param numbers :  String    ex) 1,2,3,4,5,6+7
     */
    public void mainSetLottoNumber(String numbers)
    {
        String[] numberList = numbers.split(",");
        TextView Lotto;

        for (int index = 0; index < numberList.length; index++) {

            int number;
            if (index == numberList.length - 1) {
                number = Integer.parseInt(numberList[index].split("[+]")[0]);
            } else {
                number = Integer.parseInt(numberList[index]);
            }

            String resourceId = "L" + (index + 1);
            int resID = context.getResources().getIdentifier(resourceId, "id", context.getPackageName());
            Lotto = view.findViewById(resID);
            Lotto.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
            Lotto.setText(number + "");
        }
        //보너스 번호
        int number = Integer.parseInt(numberList[numberList.length - 1].split("[+]")[1]);
        Lotto = view.findViewById(R.id.bonus);
        Lotto.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
        Lotto.setText(number + "");
    }
}
