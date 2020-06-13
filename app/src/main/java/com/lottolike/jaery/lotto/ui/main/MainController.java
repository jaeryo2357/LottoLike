package com.lottolike.jaery.lotto.ui.main;

//MVC에서 Controller는 Activity로 설정

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.lottolike.jaery.lotto.R;
import com.lottolike.jaery.lotto.barcode.BarcodeCaptureActivity;
import com.lottolike.jaery.lotto.ui.MyListActivity;

public class MainController extends AppCompatActivity implements View.OnClickListener {

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //view 생성
        View view = getLayoutInflater().inflate(R.layout.activity_main, null);
        setContentView(view);
        //mainView에 전달 이후, initView() 호출

    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.main_qrCode :
                moveActivity(BarcodeCaptureActivity.class);
                break;

            case R.id. main_myList:
                moveActivity(MyListActivity.class);
                break;
        }
    }

    //인텐트 이동이 많아 별도의 함수로 생성 Class의 매개변수가 안전한 방식인지 확인 필요
    private void moveActivity(Class move) {
        Intent intent = new Intent(MainController.this, move);
        startActivity(intent);
    }
}
