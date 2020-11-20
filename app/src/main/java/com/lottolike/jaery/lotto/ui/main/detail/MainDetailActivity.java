package com.lottolike.jaery.lotto.ui.main.detail;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.data.officiallottomaindata.OfficialLottoMainData;
import com.lottolike.jaery.lotto.data.officiallottomaindata.source.OfficialLottoMainDataRepositoryImpl;
import com.lottolike.jaery.lotto.data.officiallottomaindata.source.remote.RemoteOfficialLottoMainDataSource;
import com.lottolike.jaery.lotto.data.officiallottorankdata.OfficialLottoRankData;
import com.lottolike.jaery.lotto.data.officiallottorankdata.source.OfficialLottoRankDataRepositoryImpl;
import com.lottolike.jaery.lotto.data.officiallottorankdata.source.remote.RemoteOfficialLottoRankRankDataSource;
import com.lottolike.jaery.lotto.data.util.LottoUtil;

import java.util.List;

public class MainDetailActivity extends AppCompatActivity implements MainDetailContact.View{

    private MainDetailContact.Presenter presenter;

    private TextView lottoRound;

    private TextView lottoNumberOne;
    private TextView lottoNumberTwo;
    private TextView lottoNumberThree;
    private TextView lottoNumberFour;
    private TextView lottoNumberFive;
    private TextView lottoNumberSix;
    private TextView lottoNumberBonus;

    private TextView lottoRankMoneyOne;
    private TextView lottoRankPersonOne;
    private TextView lottoRankMoneyTwo;
    private TextView lottoRankPersonTwo;
    private TextView lottoRankMoneyThree;
    private TextView lottoRankPersonThree;
    private TextView lottoRankMoneyFour;
    private TextView lottoRankPersonFour;
    private TextView lottoRankMoneyFive;
    private TextView lottoRankPersonFive;

    @Override
    protected void onStart() {
        super.onStart();

        presenter.start();
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_lotto_detail);

        presenter = setPresenter();

        initView();

        findViewById(R.id.lotto_detail_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presenter.clickBackButton();
            }
        });
    }

    private void initView() {
        lottoRound = findViewById(R.id.lottoResult_title);

        lottoNumberOne = findViewById(R.id.detail_lottonumber_tv1);
        lottoNumberTwo = findViewById(R.id.detail_lottonumber_tv2);
        lottoNumberThree = findViewById(R.id.detail_lottonumber_tv3);
        lottoNumberFour = findViewById(R.id.detail_lottonumber_tv4);
        lottoNumberFive = findViewById(R.id.detail_lottonumber_tv5);
        lottoNumberSix = findViewById(R.id.detail_lottonumber_tv6);

        lottoRankMoneyOne = findViewById(R.id.detail_lottorankmoney_tv1);
        lottoRankPersonOne = findViewById(R.id.detail_lottorankperson_tv1);
        lottoRankMoneyTwo = findViewById(R.id.detail_lottorankmoney_tv2);
        lottoRankPersonTwo = findViewById(R.id.detail_lottorankperson_tv2);
        lottoRankMoneyThree = findViewById(R.id.detail_lottorankmoney_tv3);
        lottoRankPersonThree = findViewById(R.id.detail_lottorankperson_tv3);
        lottoRankMoneyFour = findViewById(R.id.detail_lottorankmoney_tv4);
        lottoRankPersonFour = findViewById(R.id.detail_lottorankperson_tv4);
        lottoRankMoneyFive = findViewById(R.id.detail_lottorankmoney_tv5);
        lottoRankPersonFive = findViewById(R.id.detail_lottorankperson_tv5);

        lottoNumberBonus = findViewById(R.id.detail_lottonumberbonus_tv);
    }

    @Override
    public void showAdRequest() {
        AdRequest adRequest = new AdRequest.Builder().build();

        ((AdView)findViewById(R.id.detail_adView)).loadAd(adRequest);
    }

    @Override
    public void showOfficialLottoData(OfficialLottoMainData officialLottoMainData, List<OfficialLottoRankData> officialLottoRankData) {
        showLottoNumber(officialLottoMainData);
        showLottoRank(officialLottoRankData);
    }

    private void showLottoNumber(OfficialLottoMainData data) {

        lottoRound.setText(data.getLottoRound() + "회");

        String[] number = data.getOfficialLottoNumber().split(",");

        lottoNumberOne.setText(number[0]);
        lottoNumberOne.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(Integer.parseInt(number[0])));

        lottoNumberTwo.setText(number[1]);
        lottoNumberTwo.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(Integer.parseInt(number[1])));

        lottoNumberThree.setText(number[2]);
        lottoNumberThree.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(Integer.parseInt(number[2])));

        lottoNumberFour.setText(number[3]);
        lottoNumberFour.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(Integer.parseInt(number[3])));

        lottoNumberFive.setText(number[4]);
        lottoNumberFive.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(Integer.parseInt(number[4])));

        lottoNumberSix.setText(number[5]);
        lottoNumberSix.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(Integer.parseInt(number[5])));

        lottoNumberBonus.setText(data.getBonusNumber() + "");
        lottoNumberBonus.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(data.getBonusNumber()));
    }

    private void showLottoRank(List<OfficialLottoRankData> officialLottoRankData) {

        lottoRankMoneyOne.setText(officialLottoRankData.get(0).getVictoryMoney());
        lottoRankPersonOne.setText(officialLottoRankData.get(0).getVictoryPerson());

        lottoRankMoneyTwo.setText(officialLottoRankData.get(1).getVictoryMoney());
        lottoRankPersonTwo.setText(officialLottoRankData.get(1).getVictoryPerson());

        lottoRankMoneyThree.setText(officialLottoRankData.get(2).getVictoryMoney());
        lottoRankPersonThree.setText(officialLottoRankData.get(2).getVictoryPerson());

        lottoRankMoneyFour.setText(officialLottoRankData.get(3).getVictoryMoney());
        lottoRankPersonFour.setText(officialLottoRankData.get(3).getVictoryPerson());

        lottoRankMoneyFive.setText(officialLottoRankData.get(4).getVictoryMoney());
        lottoRankPersonFive.setText(officialLottoRankData.get(4).getVictoryPerson());
    }


    @Override
    public void showMainView() {
        finishAfterTransition();
    }

    @Override
    public MainDetailContact.Presenter setPresenter() {
        return new MainDetailPresenter(this,
                OfficialLottoMainDataRepositoryImpl.getInstance(new RemoteOfficialLottoMainDataSource()),
                OfficialLottoRankDataRepositoryImpl.getInstance(new RemoteOfficialLottoRankRankDataSource()));
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        presenter.onDestroy();
    }
}
