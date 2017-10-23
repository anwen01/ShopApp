package com.bwie.shopapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Toast;

import com.bwie.shopapp.adapters.AddressListAdapter;
import com.bwie.shopapp.bean.AllAddressBean;
import com.bwie.shopapp.bean.DeleteBean;
import com.google.gson.Gson;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：张玉轲
 * 时间：2017/10/20
 */

public class AddressListActivity extends AppCompatActivity {

    private ListView lv;
    private Button but_add;
    private String key;
    private AddressListAdapter adapter;
    private ImageView iv_back;
    private String moren;
    private SharedPreferences preferences;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.addresslist);

        lv = (ListView) findViewById(R.id.lv);
        but_add = (Button) findViewById(R.id.but_add);
        iv_back = (ImageView) findViewById(R.id.iv_back);

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        key = preferences.getString("key", "");
        moren = preferences.getString("moren", "");

        //返回
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressListActivity.this, DingdanActivity.class);
                setResult(200,intent);
                Intent intent1 = new Intent(AddressListActivity.this, Dingdan1Activity.class);
                setResult(300,intent1);
                finish();

            }
        });
        //添加跳转
        but_add.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(AddressListActivity.this, AddAddressActivity.class);
                startActivityForResult(intent,100);
            }
        });
        getData();

    }

    //查询订单信息
    public void getData() {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("key", key)
                .build();


        final Request request = new Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=member_address&op=address_list")
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
                System.out.println("=========查询的地址:" + string);
                Gson gson = new Gson();
                final AllAddressBean allAddressBean = gson.fromJson(string, AllAddressBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (allAddressBean.getCode() == 200) {
                            final List<AllAddressBean.DatasBean.AddressListBean> address_list = allAddressBean.getDatas().getAddress_list();
                            adapter = new AddressListAdapter(AddressListActivity.this, address_list);
                            lv.setAdapter(adapter);
                            Toast.makeText(AddressListActivity.this, "返回成功", Toast.LENGTH_SHORT).show();
                            //删除
                            adapter.setSetDeleteListener(new AddressListAdapter.SetDeleteListener() {
                                @Override
                                public void getListener(String address_id) {
                                    deleteAddress(address_id);
                                }
                            });

                        } else {
                            Toast.makeText(AddressListActivity.this, "返回失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    //删除地址
    public void deleteAddress(String id) {
        OkHttpClient client = new OkHttpClient();
        RequestBody formBody = new FormBody.Builder()
                .add("key", key)
                .add("address_id", id)
                .build();


        final Request request = new Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=member_address&op=address_del")
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
                System.out.println("=========删除地址地址:" + string);
                Gson gson = new Gson();
                final DeleteBean deleteBean = gson.fromJson(string, DeleteBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (deleteBean.getCode() == 200) {
                            Toast.makeText(AddressListActivity.this, "删除成功", Toast.LENGTH_SHORT).show();
                        } else {
                            Toast.makeText(AddressListActivity.this, "删除失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode==100&&resultCode==200){

            getData();
        }
    }
}
