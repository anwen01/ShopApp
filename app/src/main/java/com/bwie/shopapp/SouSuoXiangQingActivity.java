package com.bwie.shopapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.adapters.SousuoXiangqAdapter;
import com.bwie.shopapp.bean.SouSuoBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.Serializable;
import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/11
 */

public class SouSuoXiangQingActivity extends AppCompatActivity{

    private ImageView iv_back;
    private TextView tv_name;
    private ListView lv;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuoxiangqing);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        tv_name = (TextView) findViewById(R.id.tv_name);
        lv = (ListView) findViewById(R.id.lv);
        Intent intent = getIntent();
        String name = intent.getStringExtra("name");
        tv_name.setText(name);
        if (name.equals("劳力士")){
            getData("http://192.168.23.144/mobile/index.php?act=goods&op=goods_list&page=100");

        }else{
            Toast.makeText(this, "没有找到此商品", Toast.LENGTH_SHORT).show();
        }

        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
    }

    //请求数据
    public void getData(String url){
        OkHttpUtils
                .get()
                .url(url)
                .build()
                .execute(new StringCallback() {
                    @Override
                    public void onError(Request request, Exception e) {

                    }

                    @Override
                    public void onResponse(String response) {
                        Gson gson = new Gson();
                        SouSuoBean souSuoBean = gson.fromJson(response, SouSuoBean.class);
                        final List<SouSuoBean.DatasBean.GoodsListBean> goods_list = souSuoBean.getDatas().getGoods_list();
                        lv.setAdapter(new SousuoXiangqAdapter(SouSuoXiangQingActivity.this,goods_list));
                        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                                Intent intent = new Intent(SouSuoXiangQingActivity.this, GoodsXiangQingActivity.class);
                                intent.putExtra("goods_id",goods_list.get(i).getGoods_id());
                                intent.putExtra("tu",goods_list.get(i).getGoods_image_url());
                                intent.putExtra("name",goods_list.get(i).getGoods_name());
                                intent.putExtra("price",goods_list.get(i).getGoods_price()+"");
                                //intent.putExtra("soubean",(Serializable) goods_list);
                                startActivity(intent);
                            }
                        });

                    }
                });
    }

}
