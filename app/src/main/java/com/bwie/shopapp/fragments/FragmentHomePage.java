package com.bwie.shopapp.fragments;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.bwie.shopapp.MainActivity;
import com.bwie.shopapp.R;
import com.bwie.shopapp.SouSuoActivity;
import com.bwie.shopapp.adapters.HomePageAdapter;
import com.bwie.shopapp.bean.HomePageBean;
import com.bwie.shopapp.presenter.HomePagePresenter;
import com.bwie.shopapp.utils.MyDao;
import com.bwie.shopapp.view.HomePageView;
import com.google.gson.Gson;
import com.google.zxing.activity.CaptureActivity;
import com.yalantis.phoenix.PullToRefreshView;

import java.io.IOException;
import java.util.List;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

import static android.app.Activity.RESULT_OK;

/**
 * 作者：张玉轲
 * 时间：2017/9/29
 */

public class FragmentHomePage extends Fragment implements HomePageView{

    private LinearLayout line1;
    private PullToRefreshView pull;
    private static final long REFRESH_DELAY = 2000;
    private ImageView saoyisao;
    private EditText sousuo;
    private ImageView xiangji;
    private RecyclerView recyclerView;

    private final static int REQ_CODE = 1028;


    //使用handler更新主线程
    private Handler handler=new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            Gson gson = new Gson();
            String str = (String)msg.obj;
            HomePageBean homePageBean = gson.fromJson(str, HomePageBean.class);
            HomePageBean.DataBean data = homePageBean.getData();
            //线性布局
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new HomePageAdapter(getActivity(),data));
        }
    };
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.homepage, null);
        line1 = view.findViewById(R.id.line1);
        pull = view.findViewById(R.id.pull);
        //扫描
        saoyisao = view.findViewById(R.id.iv_saoyisao);
        //搜索
        sousuo = view.findViewById(R.id.sousuo);
        //相机
        xiangji = view.findViewById(R.id.xiangji);
        recyclerView = view.findViewById(R.id.recyv);


        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);

        //下拉刷新
        pull.setOnRefreshListener(new PullToRefreshView.OnRefreshListener() {
            @Override
            public void onRefresh() {
                ////刷新时使布局隐藏
                line1.setVisibility(View.GONE);
                pull.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        pull.setRefreshing(false);
                        //刷新完成后使布局显示
                        line1.setVisibility(View.VISIBLE);
                    }
                },REFRESH_DELAY);
            }
        });
        //扫描二维码
        saoyisao.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), CaptureActivity.class);
                startActivityForResult(intent, REQ_CODE);
            }
        });

        //不弹出软键盘
        sousuo.setInputType(InputType.TYPE_NULL);
        //搜索的点击事件
        sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SouSuoActivity.class);
                startActivity(intent);
            }
        });

        //相机的点击事件
        xiangji.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
            }
        });

        HomePagePresenter homePagePresenter = new HomePagePresenter(this);
        homePagePresenter.getShow("http://m.yunifang.com/yunifang/mobile/home");


    }



    @Override
    public void getSuccess(String str) {
        System.out.println("===========Fragemnt" + str);

        //定义消息
        Message message = new Message();
        message.obj=str;
        //发送消息
        handler.sendMessage(message);
    }
}
