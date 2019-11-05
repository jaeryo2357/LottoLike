package com.lottolike.jaery.Lotto;

import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;

public class GetJson {

    private OkHttpClient client;
    private static GetJson instance = new GetJson();
    public static GetJson getInstance() {
        return instance;
    }

    private GetJson(){ this.client = new OkHttpClient(); }


    /** 웹 서버로 요청을 한다. */
    public void requestWebServer(String parameter,Callback callback) {

        Request request = new Request.Builder()
                .url("http://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo="+parameter)
                .build();
        client.newCall(request).enqueue(callback);
    }


}
