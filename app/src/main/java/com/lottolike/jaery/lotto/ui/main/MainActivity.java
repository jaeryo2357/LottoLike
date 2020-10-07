package com.lottolike.jaery.lotto.ui.main;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;

import com.lottolike.jaery.lotto.R;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.lottolike.jaery.lotto.barcode.BarcodeCaptureActivity;
import com.lottolike.jaery.lotto.lotto.util.LottoUtil;
import com.lottolike.jaery.lotto.ui.GetNumberActivity;
import com.lottolike.jaery.lotto.ui.SettingActivity;
import com.lottolike.jaery.lotto.ui.main.detail.MainDetailController;
import com.lottolike.jaery.lotto.ui.mylist.MyListActivity;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements MainContract.View, View.OnClickListener {

    private MainContract.Presenter presenter;

    private TextView lottoRoundTextView;
    private TextView lottoRoundDateTextView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        initPresenter();
        initView();
        initAds();
    }

    @Override
    protected void onStart() {
        super.onStart();
        presenter.start();
    }

    private void initPresenter() {
        presenter = setPresenter();
    }

    private void initView() {

        lottoRoundTextView = findViewById(R.id.lottoResult_title);
        lottoRoundDateTextView = findViewById(R.id.lottoResult_day);

        findViewById(R.id.main_qrCode).setOnClickListener(this);
        findViewById(R.id.main_myList).setOnClickListener(this);
        findViewById(R.id.main_info_image_btn).setOnClickListener(this);
        findViewById(R.id.main_get_random_number).setOnClickListener(this);
        findViewById(R.id.main_setting).setOnClickListener(this);
    }

    private void initAds() {

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        AdView mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);
    }

    /**
     * 매 앱이 실행 될때마다 새로운 추천번호 보여주기
     *
     * @param numbers : String ex) 1,2,3,4,5,6
     */
    @Override
    public void showRecommendNumber(ArrayList<Integer> numbers) {
        TextView lottoTempTextView;
        for (int index = 0; index < numbers.size(); index++) {
            int number = numbers.get(index);
            String resourceid = "recommend_L" + (index + 1);
            int resID = getResources().getIdentifier(resourceid, "id", getPackageName());
            lottoTempTextView = findViewById(resID);
            lottoTempTextView.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
            lottoTempTextView.setText(number + "");
        }
    }

    @Override
    public void showQRCodeView() {
        Intent intent = new Intent(this, BarcodeCaptureActivity.class);
        startActivity(intent);
    }

    @Override
    public void showSettingView() {
        Intent intent = new Intent(this, SettingActivity.class);
        startActivity(intent);
    }

    @Override
    public void showMyLottoListView() {
        Intent intent = new Intent(this, MyListActivity.class);
        startActivity(intent);
    }

    @Override
    public void showAddLottoListView() {
        Intent intent = new Intent(this, GetNumberActivity.class);
        startActivity(intent);
    }


    @SuppressWarnings("unchecked")
    @Override
    public void showDetailLottoView() {
        RelativeLayout today_LottoLayout = findViewById(R.id.main_result_layout);

        Intent intent = new Intent(this, MainDetailController.class);
        ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                this,
                new Pair<View, String>(lottoRoundTextView, "lottoTitle"),
                new Pair<View, String>(today_LottoLayout, "lottoNumber"));
        startActivity(intent, options.toBundle());
    }

    /**
     * 이번주 로또 회차 번호 적용시키기
     *
     * @param numbers :  String    ex) 1,2,3,4,5,6+7
     */
    @Override
    public void showLottoNumber(String numbers) {
        String[] numberList = numbers.split(",");
        TextView lottoTempTextView;

        for (int index = 0; index < numberList.length; index++) {

            int number;
            if (index == numberList.length - 1) {
                number = Integer.parseInt(numberList[index].split("[+]")[0]);
            } else {
                number = Integer.parseInt(numberList[index]);
            }

            String resourceId = "L" + (index + 1);
            int resID = getResources().getIdentifier(resourceId, "id", getPackageName());
            lottoTempTextView = findViewById(resID);
            lottoTempTextView.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
            lottoTempTextView.setText(number + "");
        }
        //보너스 번호
        int number = Integer.parseInt(numberList[numberList.length - 1].split("[+]")[1]);
        lottoTempTextView = findViewById(R.id.bonus);
        lottoTempTextView.setBackgroundResource(LottoUtil.INSTANCE.getLottoBackgroundColor(number));
        lottoTempTextView.setText(number + "");
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_qrCode:
                presenter.qrCodeButtonClick();
                break;

            case R.id.main_myList:
                presenter.myLottoListButtonClick();
                break;

            case R.id.main_setting:
                presenter.settingButtonClick();
                break;

            case R.id.main_get_random_number:
                presenter.addLottoListButtonClick();
                break;

            case R.id.main_info_image_btn:
               presenter.detailButtonClick();
                break;
        }
    }

    @Override
    public void showLottoRound(int round) {
        lottoRoundTextView.setText(round + "회");
    }

    @Override
    public void showLottoRoundDate(String date) {
        lottoRoundDateTextView.setText(date);
    }

    @Override
    public MainContract.Presenter setPresenter() {
        return new MainPresenter(this, new MainModel());
    }
}
