package com.bwie.shopapp;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.KeyEvent;
import android.view.View;
import android.widget.RadioButton;
import android.widget.Toast;

import com.bwie.shopapp.fragments.FragmentClassify;
import com.bwie.shopapp.fragments.FragmentMe;
import com.bwie.shopapp.fragments.FragmentHomePage;
import com.bwie.shopapp.fragments.FragmentShop;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;

/**
 * 作者：张玉轲
 * 时间：2017/9/28
 */

public class HomeActivity extends AppCompatActivity implements View.OnClickListener{

    private FragmentManager fragmentManager;
    private FragmentMe fragmentMe;
    private FragmentHomePage fragmentHomePage;

    private FragmentClassify fragmentClassify;
    private FragmentShop fragmentShop;
    private ArrayList<RadioButton> buttons;
    private ArrayList<Fragment> fragments;
    private static Boolean isQuit = false;
    private Timer timer = new Timer();
    private long exitTime = 0;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.homeactivity);

        buttons = new ArrayList<>();
        RadioButton but_homePage= (RadioButton) findViewById(R.id.but_homePage);
        RadioButton but_classify= (RadioButton) findViewById(R.id.but_classify);
        RadioButton but_shop= (RadioButton) findViewById(R.id.but_shop);
        RadioButton but_me= (RadioButton) findViewById(R.id.but_me);
        buttons.add(but_homePage);
        buttons.add(but_classify);
        buttons.add(but_shop);
        buttons.add(but_me);

        but_homePage.setOnClickListener(this);
        but_classify.setOnClickListener(this);
        but_shop.setOnClickListener(this);
        but_me.setOnClickListener(this);

        fragments = new ArrayList<>();

        //首页
        fragmentHomePage = new FragmentHomePage();

        //分类
        fragmentClassify = new FragmentClassify();
        //购物车
        fragmentShop = new FragmentShop();
        //我的
        fragmentMe = new FragmentMe();

        fragments.add(fragmentHomePage);
        fragments.add(fragmentClassify);
        fragments.add(fragmentShop);
        fragments.add(fragmentMe);

        fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        fragmentHomePage= (FragmentHomePage) fragmentManager.findFragmentByTag("homepage");
        if (fragmentHomePage==null){
            transaction.add(R.id.fl,fragments.get(0),"homepage")
                    .show(fragments.get(0))
                    .commit();
        }else {
            transaction.show(fragments.get(0))
                    .commit();
        }
        setColor(0);


    }

    //点击事件
    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.but_homePage:
                setColor(0);
                break;
            case R.id.but_classify:
                fragmentClassify = (FragmentClassify) fragmentManager.findFragmentByTag("fl");
                if (fragmentClassify == null){
                    FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                    beginTransaction.add(R.id.fl,fragments.get(1),"fl").commit();

                }
                setColor(1);
                break;
            case R.id.but_shop:
                SharedPreferences preferences1 = getSharedPreferences("config", Context.MODE_PRIVATE);
                String key1 = preferences1.getString("key", "");
                if (key1.length()<=0){
                    Intent intent = new Intent(HomeActivity.this, LoginZhuceActivity.class);
                    startActivityForResult(intent,100);
                    buttons.get(2).setChecked(false);
                    buttons.get(2).setTextColor(Color.BLACK);
                }else {
                    fragmentShop = (FragmentShop) fragmentManager.findFragmentByTag("shopcar");
                    if (fragmentShop == null){
                        Toast.makeText(this, "shop", Toast.LENGTH_SHORT).show();
                        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                        beginTransaction.add(R.id.fl,fragments.get(2),"shopcar").commit();
                    }
                    setColor(2);
                }
                break;
            case R.id.but_me:
                SharedPreferences preferences2 = getSharedPreferences("config", Context.MODE_PRIVATE);
                String key2 = preferences2.getString("key", "");
                if (key2.length()<=0){
                    Intent intent = new Intent(HomeActivity.this, LoginZhuceActivity.class);
                    startActivityForResult(intent,100);
                    buttons.get(3).setChecked(false);
                    buttons.get(3).setTextColor(Color.BLACK);
                }else {
                    fragmentMe = (FragmentMe) fragmentManager.findFragmentByTag("me");
                    if (fragmentMe == null){
                        Toast.makeText(this, "me", Toast.LENGTH_SHORT).show();
                        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                        beginTransaction.add(R.id.fl,fragments.get(3),"me").commit();
                    }
                    setColor(3);
                }
                break;
        }
    }


    //按钮
    public void setColor(int po){

        FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
        for (int i = 0; i <buttons.size() ; i++) {
            if (i==po){
                buttons.get(i).setChecked(true);
                buttons.get(i).setTextColor(Color.RED);
                beginTransaction.show(fragments.get(i));
            }else{
                buttons.get(i).setChecked(false);
                buttons.get(i).setTextColor(Color.BLACK);
                beginTransaction.hide(fragments.get(i));
            }
        }
        //0987654321
        beginTransaction.commit();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if(requestCode == 300 && resultCode ==400){
            Toast.makeText(this, "hit", Toast.LENGTH_SHORT).show();
            setColor(0);
            return;
        }
        SharedPreferences preferences2 = getSharedPreferences("config", Context.MODE_PRIVATE);
        String key2 = preferences2.getString("key", "");
        if (key2.length()>0){
            fragmentMe = (FragmentMe) fragmentManager.findFragmentByTag("me");
            if (fragmentMe == null){
                Toast.makeText(this, "me", Toast.LENGTH_SHORT).show();
                FragmentTransaction beginTransaction = fragmentManager.beginTransaction();
                beginTransaction.add(R.id.fl,fragments.get(3),"me").commit();
            }
            setColor(3);
        }
    }

    //不是第一个页面就先跳到第一个一面
    @Override
    public boolean dispatchKeyEvent(KeyEvent event) {
        if (event.getKeyCode() == KeyEvent.KEYCODE_BACK) {//点击的是返回键
           if (!buttons.get(0).isChecked()){
               setColor(0);
               return true;
           }
        }
        return super.dispatchKeyEvent(event);
    }

    //点击两次退出应用
    @Override
    public void onBackPressed() {
        if (isQuit == false) {
            isQuit = true;
            Toast.makeText(getBaseContext(), "再按一次返回键退出程序", Toast.LENGTH_SHORT).show();
            TimerTask task = null;
            task = new TimerTask() {
                @Override
                public void run() {
                    isQuit = false;
                }
            };
            timer.schedule(task, 2000);
        } else {
            finish();
            System.exit(0);
            android.os.Process.killProcess(android.os.Process.myPid());
        }
    }


    }
