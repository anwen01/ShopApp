package com.bwie.shopapp.model;

import com.bwie.shopapp.bean.HomePageBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * 作者：张玉轲
 * 时间：2017/10/9
 */

public class HomepageModel {

    public void getData(String url, final HomepageListener listener) {
        OkHttpClient client = new OkHttpClient();
        final Request request = new Request.Builder()
                .url(url)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println("===========HomepageModel" + string);
                listener.getListener(string);

            }
        });
    }

    public interface HomepageListener{
        public void getListener(String str);
    }

}
