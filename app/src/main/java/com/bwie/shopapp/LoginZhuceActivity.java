package com.bwie.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

/**
 * 作者：张玉轲
 * 时间：2017/10/12
 */

public class LoginZhuceActivity extends AppCompatActivity{
    private ImageView iv_back;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.loginzhuce);
        Button login_but= (Button) findViewById(R.id.login_btn);
        Button register_btn= (Button)findViewById(R.id.register_btn);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        login_but.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginZhuceActivity.this,LoginActivity.class);
                //登录
                startActivityForResult(intent,100);
                //startActivity(intent);
                finish();
            }
        });
        register_btn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginZhuceActivity.this,RegisterActivity.class);
                startActivityForResult(intent,100);
            }
        });
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(LoginZhuceActivity.this, HomeActivity.class);
                setResult(200,intent1);
                finish();

            }
        });

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==200){
            Toast.makeText(this, "回跳1", Toast.LENGTH_SHORT).show();
            Intent intent = new Intent(LoginZhuceActivity.this, HomeActivity.class);
            setResult(500,intent);
            finish();

        }
    }
}
