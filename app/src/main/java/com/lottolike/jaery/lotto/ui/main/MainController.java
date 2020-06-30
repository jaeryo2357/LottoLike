package com.lottolike.jaery.lotto.ui.main;

//MVC에서 Controller는 Activity로 설정

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.core.util.Pair;
import androidx.lifecycle.Observer;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.barcode.BarcodeCaptureActivity;
import com.lottolike.jaery.lotto.ui.GetNumberActivity;
import com.lottolike.jaery.lotto.ui.MyListActivity;
import com.lottolike.jaery.lotto.ui.SettingActivity;
import com.lottolike.jaery.lotto.ui.detail.LottoDetailActivity;

public class MainController extends AppCompatActivity implements View.OnClickListener {

    MainView mainView;
    MainModel mainModel;
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //view 생성
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view);

        mainView = new MainView(this, view);
        mainModel = new MainModel(this);

        observeModel();

        mainModel.changeLottoInfo();
    }


    @Override
    public void onClick(View view) {
        Intent intent;
        switch (view.getId()) {
            case R.id.main_qrCode :
                intent = new Intent(MainController.this, BarcodeCaptureActivity.class);
                startActivity(intent);
                break;

            case R.id. main_myList:
                intent = new Intent(MainController.this, MyListActivity.class);
                startActivity(intent);
                break;

            case R.id.main_setting:
                intent = new Intent(MainController.this, SettingActivity.class);
                startActivity(intent);
                break;

            case R.id.main_get_random_number:
                intent = new Intent(MainController.this, GetNumberActivity.class);
                startActivity(intent);
                break;

            case R.id.main_info_image_btn:

                TextView today_LottoNumber = findViewById(R.id.lottoResult_title);
                RelativeLayout today_LottoLayout = findViewById(R.id.main_result_layout);

                intent = new Intent(MainController.this, LottoDetailActivity.class);
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        MainController.this,
                        new Pair<View,String>(today_LottoNumber,"lottoTitle"),
                        new Pair<View,String>(today_LottoLayout,"lottoNumber"));
                startActivity(intent,options.toBundle());
                break;
        }
    }

    //모델의 변수 변화를 구독 후, 변경되면 View 반영
    private void observeModel() {
        mainModel.getLottoNumbers().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String lottoNumber) {
                 // 1,2,3,4,5,6+7
                mainView.mainSetLottoNumber(lottoNumber);
            }
        });

        mainModel.getLottoRound().observe(this, new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                // 192
            }
        });
    }
}
