package com.bwie.shopapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopapp.bean.ZhuxiaoBean;
import com.google.gson.Gson;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：张玉轲
 * 时间：2017/10/13
 */

public class SetActivity extends AppCompatActivity {

    private TextView tv_back;
    private String key;
    private String username;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.setitem);
        tv_back = (TextView) findViewById(R.id.tv_back);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        key = preferences.getString("key", "");
        username = preferences.getString("username", "");
        if (username.length()<=0){
            tv_back.setFocusable(false);
        }
        //退出登录
        tv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                zhuxiao("http://192.168.23.144/mobile/index.php?act=logout");

            }
        });
    }

    public void zhuxiao(String url){
        OkHttpClient client=new OkHttpClient();
        RequestBody formBody=new FormBody.Builder()
                .add("key",key)
                .add("username",username)
                .add("client","android")
                .build();


        final Request request=new Request.Builder()
                .url(url)
                .post(formBody)
                .build();
        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println("=========zhu:"+string);
                Gson gson = new Gson();
                final ZhuxiaoBean zhuxiaoBean = gson.fromJson(string, ZhuxiaoBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (zhuxiaoBean.getCode()==200){
                            preferences.edit().putString("key",null).commit();
                            preferences.edit().putString("username",null).commit();
                            Toast.makeText(SetActivity.this, "退出成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(SetActivity.this, HomeActivity.class);
                            setResult(400,intent);
                            finish();
                        }else{
                            Toast.makeText(SetActivity.this, "注销失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }
}
