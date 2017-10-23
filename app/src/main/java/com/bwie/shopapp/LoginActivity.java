package com.bwie.shopapp;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.bwie.shopapp.bean.RegisterBean;
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
 * 时间：2017/10/12
 */

public class LoginActivity extends AppCompatActivity {

    private Button but_login;
    private EditText et_password;
    private EditText et_username;
    private ImageView iv_back;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login);
        et_username = (EditText) findViewById(R.id.et_username);
        et_password = (EditText) findViewById(R.id.et_password);
        but_login = (Button) findViewById(R.id.but_login);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        final Intent intent = getIntent();
        String username = intent.getStringExtra("username");
        et_username.setText(username);
        but_login.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData("http://192.168.23.144/mobile/index.php?act=login");
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(200,new Intent(LoginActivity.this,LoginZhuceActivity.class));
                finish();
            }
        });



    }

    public void getData(String url){
        OkHttpClient client=new OkHttpClient();
        RequestBody formbody=new FormBody.Builder()
                .add("username",et_username.getText().toString())
                .add("password",et_password.getText().toString())
                .add("client","android")
                .build();
        final Request request=new Request.Builder()
                .url(url)
                .post(formbody)
                .build();

        Call call = client.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                final SharedPreferences preferences = getSharedPreferences("config", MODE_PRIVATE);

                System.out.println("=====login："+string);
                Gson gson = new Gson();
                final RegisterBean registerBean = gson.fromJson(string, RegisterBean.class);
                final int code = registerBean.getCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (TextUtils.isEmpty(et_username.getText().toString())||TextUtils.isEmpty(et_password.getText().toString())){
                            Toast.makeText(LoginActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                        }else{
                        if (code==200){
                            String key = registerBean.getDatas().getKey();

                            System.out.println("==============key:"+key);
                            preferences.edit().putString("key",key).commit();
                            preferences.edit().putString("username",registerBean.getDatas().getUsername()).commit();
                            Toast.makeText(LoginActivity.this, "登录成功", Toast.LENGTH_SHORT).show();
                            Intent intent = new Intent(LoginActivity.this,LoginZhuceActivity.class);
                            setResult(300,intent);
                            finish();


                        }else{
                            Toast.makeText(LoginActivity.this, "登录失败", Toast.LENGTH_SHORT).show();

                        }}
                    }
                });

            }
        });
    }


}
