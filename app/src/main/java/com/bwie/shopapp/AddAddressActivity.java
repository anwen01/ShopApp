package com.bwie.shopapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bwie.shopapp.bean.AddressSuccessBean;
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
 * 时间：2017/10/19
 */

public class AddAddressActivity extends AppCompatActivity {

    private String key;
    private CheckBox moren;
    private Button save;
    private EditText add_name;
    private EditText add_phone;
    private EditText add_address;
    private EditText add_info;
    private SharedPreferences preferences;
    private ImageView iv_back;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addaddress);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        save = (Button) findViewById(R.id.but_address_save);
        add_name = (EditText) findViewById(R.id.add_name);
        add_phone = (EditText) findViewById(R.id.add_phone);
        add_address = (EditText) findViewById(R.id.add_address);
        add_info = (EditText) findViewById(R.id.add_info);
        moren = (CheckBox) findViewById(R.id.address_add_moren);


        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        key = preferences.getString("key", "");
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getAddress();
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddAddressActivity.this, AddressListActivity.class);
                setResult(200,intent);
                finish();
            }
        });

    }

    public void getAddress(){
        OkHttpClient client=new OkHttpClient();
        RequestBody formBody=new FormBody.Builder()
                .add("key",key)
                .add("true_name",add_name.getText().toString())
                .add("mob_phone",add_phone.getText().toString())
                .add("city_id","36")
                .add("area_id","37")
                .add("address",add_address.getText().toString())
                .add("area_info",add_info.getText().toString())
                .build();


        final Request request=new Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=member_address&op=address_add")
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
                System.out.println("=========添加地址:"+string);
                Gson gson = new Gson();
                final AddressSuccessBean addressSuccessBean = gson.fromJson(string, AddressSuccessBean.class);

                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (addressSuccessBean.getCode()==200){
                            Toast.makeText(AddAddressActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                            if (moren.isChecked()){
                                preferences.edit().putString("moren",addressSuccessBean.getDatas().getAddress_id()+"").commit();
                                preferences.edit().putString("truename",add_name.getText().toString()).commit();
                                preferences.edit().putString("truephone",add_phone.getText().toString()).commit();
                                preferences.edit().putString("trueaddress",add_address.getText().toString()).commit();
                            }

                        }else{
                            Toast.makeText(AddAddressActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
}
