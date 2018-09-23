package com.example.jaery.rotto;

import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;

public class GetJoson {

    private OkHttpClient client;
    private static GetJoson instance = new GetJoson();
    public static GetJoson getInstance() {
        return instance;
    }

    private GetJoson(){ this.client = new OkHttpClient(); }


    /** 웹 서버로 요청을 한다. */
    public void requestWebServer(String parameter,Callback callback) {
        RequestBody body = new FormBody.Builder()

                .build();
        Request request = new Request.Builder()
                .url("http://www.nlotto.co.kr/common.do?method=getLottoNumber&drwNo="+parameter)
                .post(body)
                .build();
        client.newCall(request).enqueue(callback);
    }


}
