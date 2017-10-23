package com.bwie.shopapp.fragments;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bwie.shopapp.LoginZhuceActivity;
import com.bwie.shopapp.R;
import com.bwie.shopapp.SetActivity;

/**
 * 作者：张玉轲
 * 时间：2017/9/29
 */

public class FragmentMe extends Fragment{

    private TextView tv_username;
    private TextView tv_set;
    private ImageView touxiang;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.me, null);
        tv_username = view.findViewById(R.id.tv_username);
        tv_set = view.findViewById(R.id.tv_set);
        touxiang = view.findViewById(R.id.touxiang);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        SharedPreferences preferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
        String username = preferences.getString("username", "");

        tv_username.setText(username);

        tv_set.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getActivity(), SetActivity.class);
                getActivity().startActivityForResult(intent,300);

            }
        });

        //头像
        touxiang.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder=new AlertDialog.Builder(getActivity());
                AlertDialog dialog = builder.create();


            }
        });
    }

    @Override
    public void onHiddenChanged(boolean hidden) {
        super.onHiddenChanged(hidden);
        if (hidden){

        }else{
            SharedPreferences preferences = getActivity().getSharedPreferences("config", Context.MODE_PRIVATE);
            String username = preferences.getString("username", "");

            tv_username.setText(username);
        }
    }
}
