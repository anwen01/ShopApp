package com.bwie.shopapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.view.Gravity;
import android.view.View;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.PopupWindow;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.bwie.shopapp.bean.AddShopBean;
import com.bwie.shopapp.bean.SouSuoBean;
import com.bwie.shopapp.bean.XiangqingBean;
import com.google.gson.Gson;
import com.squareup.okhttp.Request;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.IOException;
import java.io.Serializable;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * 作者：张玉轲
 * 时间：2017/10/12
 */

public class GoodsXiangQingActivity extends AppCompatActivity{

    private WebView webView;
    private String goods_id;
    private ImageView iv_back;
    private Button guomai;
    private Button addshop;
    private String tu;
    private String name;
    private String price;
    private String key2;
    private PopupWindow popupWindow;
    private View view1;
    private int content=1;
    private List<SouSuoBean.DatasBean.GoodsListBean> list;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.goodsxiangqing);
        iv_back = (ImageView) findViewById(R.id.iv_back);
        addshop = (Button) findViewById(R.id.addshop);
        guomai = (Button) findViewById(R.id.guomai);
        webView= (WebView) findViewById(R.id.webview);
        final Intent intent = getIntent();
        //传的参数
        goods_id = intent.getStringExtra("goods_id");
        tu = intent.getStringExtra("tu");
        name = intent.getStringExtra("name");
        price = intent.getStringExtra("price");
        //list = (List<SouSuoBean.DatasBean.GoodsListBean>) intent.getSerializableExtra("soubean");


        webView.getSettings().setJavaScriptEnabled(true);
        webView.loadUrl("http://192.168.23.144/index.php?act=goods&op=index&goods_id="+goods_id);
        //覆盖WebView默认使用第三方或系统默认浏览器打开网页的行为，使网页用WebView打开
        webView.setWebViewClient(new WebViewClient(){
            @Override
            public boolean shouldOverrideUrlLoading(WebView view, String url) {
                //返回值是true的时候控制去WebView打开，为false调用系统浏览器或第三方浏览器
                view.loadUrl(url);
                return true;
            }
        });


            view1 = View.inflate(GoodsXiangQingActivity.this, R.layout.addgouwuche, null);
            //定义PopupWindow 将布局放进来
            popupWindow = new PopupWindow(view1, ActionBar.LayoutParams.MATCH_PARENT, ActionBar.LayoutParams.WRAP_CONTENT);
            addshop.setOnClickListener(new View.OnClickListener() {

                private TextView num;

                @Override
                public void onClick(View view) {
                    popupWindow.showAtLocation(view, Gravity.BOTTOM,0,0);
                    ImageView iv = view1.findViewById(R.id.iv);
                    TextView tv_name = view1.findViewById(R.id.tv_name);
                    TextView tv_price = view1.findViewById(R.id.tv_price);
                    Button delenum = view1.findViewById(R.id.delenum);
                    Button but_queding = view1.findViewById(R.id.but_queding);
                   Button but_quxiao= view1.findViewById(R.id.but_quxiao);

                    num = view1.findViewById(R.id.num);
                    Button addnum = view1.findViewById(R.id.addnum);
                    addnum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            content++;
                            num.setText(content+"");
                        }
                    });
                    delenum.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            if (content>1){
                                content--;
                                num.setText(content+"");
                            }else{
                                num.setText("1");
                            }
                        }
                    });


                    Glide.with(GoodsXiangQingActivity.this).load(tu).into(iv);
                    tv_name.setText(name);
                    tv_price.setText("￥"+price);
                    //添加
                    but_queding.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            SharedPreferences preferences2 = getSharedPreferences("config", Context.MODE_PRIVATE);
                            key2 = preferences2.getString("key", "");
                            if (key2.length()<=0){
                                Intent intent1 = new Intent(GoodsXiangQingActivity.this, LoginZhuceActivity.class);
                                startActivity(intent1);
                            }else{
                                addSuccess();
                                popupWindow.dismiss();
                            }

                        }
                    });
                    //取消
                    but_quxiao.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View view) {
                            popupWindow.dismiss();
                        }
                    });

                }
            });

        //购买
        guomai.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                SharedPreferences preferences2 = getSharedPreferences("config", Context.MODE_PRIVATE);
                key2 = preferences2.getString("key", "");
                if (key2.length()<=0){
                    Intent intent1 = new Intent(GoodsXiangQingActivity.this, LoginZhuceActivity.class);
                    startActivity(intent1);
                }else{
                    Intent intent1 = new Intent(GoodsXiangQingActivity.this, Dingdan1Activity.class);
                    intent1.putExtra("list",(Serializable) list);
                    intent1.putExtra("num",content+"");
                    intent1.putExtra("tu",tu);
                    intent1.putExtra("name",name);
                    intent1.putExtra("price",price);
                    startActivity(intent1);
                }

            }
        });




        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });

    }
    //添加方法
    public void addSuccess(){
        RequestBody formBody=new FormBody.Builder()
                .add("key",key2)
                .add("goods_id",goods_id)
                .add("quantity",content+"")
                .build();
        OkHttpClient okHttpClient=new OkHttpClient();
        okhttp3.Request request=new okhttp3.Request.Builder()
               .url("http://192.168.23.144/mobile/index.php?act=member_cart&op=cart_add")
               .post(formBody)
               .build();
        Call call = okHttpClient.newCall(request);
        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String string = response.body().string();
                System.out.println("==============add:"+string);
                Gson gson = new Gson();
                final AddShopBean addShopBean = gson.fromJson(string, AddShopBean.class);
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        if (addShopBean.getCode()==200){
                            Toast.makeText(GoodsXiangQingActivity.this, "添加成功", Toast.LENGTH_SHORT).show();
                        }else {
                            Toast.makeText(GoodsXiangQingActivity.this, "添加失败", Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });

    }

}
