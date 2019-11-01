package com.example.jaery.rotto;

import android.content.Context;
import android.util.DisplayMetrics;

import com.example.jaery.rotto.Database.LottoDB;
import com.google.ads.mediation.admob.AdMobAdapter;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;



public class LottoItem {

    public static int GetBackgroundColor(int number)
    {
        if(number>=0&&number<=10)return R.drawable.yellow_circle;
        else if(number>10&&number<=20) return R.drawable.blue_circle;
        else if(number>20&&number<=30) return R.drawable.red_circle;
        else if(number>30&&number<=40) return R.drawable.shadow_circle;
        else if(number<=45)return R.drawable.green_circle;
        else return R.drawable.stroke_circle;
    }

    public static  HashMap<String, String> GetNumber(Context context, int N){  //회차에 해당하는 로또 번호 가져오는 함수

        LottoDB db=new LottoDB(context);
        db.open();
        HashMap<String,String> today=db.GetNo(""+N);
        db.close();

        return today;
    }

    public static AdLoader GetAdLoader(Context context){

        AdLoader adLoader = new AdLoader.Builder(context, context.getResources().getString(R.string.banner_ad_unit_id_for_test))
                .forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
                    @Override
                    public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {  //광고가 성공적으로 로딩
                        // Show the ad.
                    }
                })
                .withAdListener(new AdListener() {
                    // AdListener callbacks like OnAdFailedToLoad, OnAdOpened, OnAdClicked and
                    // so on, can be overridden here.
                    @Override
                    public void onAdFailedToLoad(int errorCode) { //광고 수명주기

                    }
                })
                .withNativeAdOptions(new NativeAdOptions.Builder()
                        // Methods in the NativeAdOptions.Builder class can be
                        // used here to specify individual options settings.
                        .build())
                .build();
        adLoader.loadAd(new AdRequest.Builder().build()); //광고 1개 요청
        return adLoader;
    }

    public static ArrayList<Integer> GetFreeNumber(){
        ArrayList<Integer> a =new ArrayList<Integer>();

        a.add((int)(Math.random()*45)+1);
        for(int i=1;i<6;)
        {
            int e=(int)(Math.random()*45)+1;
            if(!a.contains(e)) {
                a.add(e);
                i++;
            }
        }
        Collections.sort(a);
        return a;
    }






}
