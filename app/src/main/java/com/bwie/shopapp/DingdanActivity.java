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
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.bwie.shopapp.adapters.DingdanAdapter;
import com.bwie.shopapp.adapters.GoodsDingdanAdapter;
import com.bwie.shopapp.bean.AllAddressBean;
import com.bwie.shopapp.bean.ShopInfoBean;
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
 * 时间：2017/10/19
 */

public class DingdanActivity extends AppCompatActivity {

    private LinearLayout line;
    private String key;
    private TextView dingdan_name;
    private TextView dingdan_phone;
    private TextView dingdan_address;
    private String moren;
    private SharedPreferences preferences;
    private List<ShopInfoBean.DatasBean.CartListBean.GoodsBean> list;
    private ListView lv;
    private ImageView iv_back;
    private TextView he;
    private Button but_submit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.dingdan);
        line = (LinearLayout) findViewById(R.id.line);
        dingdan_name = (TextView) findViewById(R.id.dingdan_name);
        dingdan_phone = (TextView) findViewById(R.id.dingdan_phone);
        dingdan_address = (TextView) findViewById(R.id.dingdan_address);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        lv = (ListView) findViewById(R.id.lv);
        he = (TextView) findViewById(R.id.heji);
        but_submit = (Button) findViewById(R.id.but_submit);

        //得到意图
        Intent intent = getIntent();

        list = (List<ShopInfoBean.DatasBean.CartListBean.GoodsBean>) intent.getSerializableExtra("bean");
        float heji1 = intent.getFloatExtra("heji", 0f);
        he.setText("合计:"+heji1);
        //listview适配
        lv.setAdapter(new DingdanAdapter(this, list));

        preferences = getSharedPreferences("config", Context.MODE_PRIVATE);
        key = preferences.getString("key", "");
        moren = preferences.getString("moren", "");
        getData();

        line.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DingdanActivity.this, AddressListActivity.class);
                startActivityForResult(intent1,100);
            }
        });


        //提交
        but_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent1 = new Intent(DingdanActivity.this,PayDemoActivity.class);
                startActivity(intent1);
            }
        });

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }

    public void getData(){
        OkHttpClient client = new OkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("key",key)
                .build();
        Request request = new Request.Builder()
                .url("http://192.168.23.144/mobile/index.php?act=member_address&op=address_list")
                .post(body)
                .build();
        client.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                final String string = response.body().string();
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Gson gson = new Gson();
                        AllAddressBean addressBean = gson.fromJson(string, AllAddressBean.class);
                        List<AllAddressBean.DatasBean.AddressListBean> address_list = addressBean.getDatas().getAddress_list();
                        if (address_list.size() > 0 ){
                            boolean flag = true;
                            for (int i = 0; i < address_list.size(); i++) {
                                String address_id = moren;
                                if (address_id.equals(address_list.get(i).getAddress_id())){
                                    flag = false;
                                    dingdan_name.setText("收货人 "+address_list.get(i).getTrue_name());
                                    dingdan_address.setText("收货地址 "+address_list.get(i).getAddress());
                                    dingdan_phone.setText("手机号码"+address_list.get(i).getMob_phone());
                                    break;
                                }
                            }
                            if (flag){
                                dingdan_name.setText("收货人 "+address_list.get(0).getTrue_name());
                                dingdan_address.setText("收货地址 "+address_list.get(0).getAddress());
                                dingdan_phone.setText("手机号码"+address_list.get(0).getMob_phone());
                            }
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
