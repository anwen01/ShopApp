package com.bwie.shopapp;

import android.content.Intent;
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

public class RegisterActivity extends AppCompatActivity {

    private EditText ed_username;
    private EditText ed_password;
    private EditText ed_againpassword;
    private EditText ed_email;
    private Button but_zhuce;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.zhuce);
        ed_username = (EditText) findViewById(R.id.ed_username);
        ed_password = (EditText) findViewById(R.id.ed_password);
        ed_againpassword = (EditText) findViewById(R.id.ed_againpassword);
        ed_email = (EditText) findViewById(R.id.ed_email);
        but_zhuce = (Button) findViewById(R.id.but_zhuce);
        ImageView iv_back = (ImageView) findViewById(R.id.iv_back);

        //回跳
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setResult(200,new Intent(RegisterActivity.this,LoginZhuceActivity.class));
                finish();
            }
        });


        but_zhuce.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getData("http://192.168.23.144/mobile/index.php?act=login&op=register");

            }
        });


    }

    public void getData(String url){
        OkHttpClient client=new OkHttpClient();
        RequestBody formbody=new FormBody.Builder()
                .add("username",ed_username.getText().toString())
                .add("password",ed_password.getText().toString())
                .add("password_confirm",ed_againpassword.getText().toString())
                .add("email",ed_email.getText().toString())
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
                Gson gson = new Gson();
                final RegisterBean registerBean = gson.fromJson(string, RegisterBean.class);
                final int code = registerBean.getCode();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                       if (!TextUtils.isEmpty(ed_username.getText().toString())||!TextUtils.isEmpty(ed_password.getText().toString())||!TextUtils.isEmpty(ed_againpassword.getText().toString())||!TextUtils.isEmpty(ed_email.getText().toString())){
                           if (code==200){
                               Toast.makeText(RegisterActivity.this, "注册成功", Toast.LENGTH_SHORT).show();
                               Intent intent = new Intent(RegisterActivity.this,LoginActivity.class);
                               intent.putExtra("username",registerBean.getDatas().getUsername());
                               startActivity(intent);
                               finish();

                           }else{
                               Toast.makeText(RegisterActivity.this, "注册失败", Toast.LENGTH_SHORT).show();
                           }

                       }else{
                           Toast.makeText(RegisterActivity.this, "信息不能为空", Toast.LENGTH_SHORT).show();
                       }
                    }
                });

            }
        });
    }
}
