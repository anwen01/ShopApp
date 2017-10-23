package com.bwie.shopapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;

import com.bwie.shopapp.adapters.SousuoAdapter;
import com.bwie.shopapp.utils.MyDao;

import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/8
 */

public class SouSuoActivity extends AppCompatActivity {

    private ImageView iv_back;
    private EditText et_name;
    private Button but_sousuo;
    private ListView lv;
    private Button but_clear;
    private MyDao myDao;
    private List<String> list;
    private SousuoAdapter adapter;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.sousuo);

        myDao = new MyDao(this);

        iv_back = (ImageView) findViewById(R.id.iv_back);
        et_name = (EditText) findViewById(R.id.et_name);
        but_sousuo = (Button) findViewById(R.id.but_sousuo);
        lv = (ListView) findViewById(R.id.lv);
        but_clear = (Button) findViewById(R.id.but_clear);
        //返回
        iv_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        list = myDao.select();
        adapter = new SousuoAdapter(this, list);
        lv.setAdapter(adapter);

        but_sousuo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
              String name = et_name.getText().toString();
                boolean flag=false;
                for (int i = 0; i <list.size() ; i++) {
                    if (name.equals(list.get(i))){
                        flag=true;
                    }
                }
                if (flag==false){
                    myDao.add(name);
                }
                Intent intent = new Intent(SouSuoActivity.this, SouSuoXiangQingActivity.class);
                intent.putExtra("name", name);
                startActivity(intent);
                finish();
            }
        });
        lv.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> adapterView, View view, final int po, long l) {
                AlertDialog.Builder builder=new AlertDialog.Builder(SouSuoActivity.this);
                builder.setMessage("确定要删除吗");
                builder.setNegativeButton("取消",null);
                builder.setPositiveButton("确定", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        myDao.delete(list.get(po));
                        list = myDao.select();
                        adapter = new SousuoAdapter(SouSuoActivity.this, list);
                        lv.setAdapter(adapter);
                    }
                });
                builder.create().show();
                return true;
            }
        });
        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, final int po, long l) {
                Intent intent = new Intent(SouSuoActivity.this, SouSuoXiangQingActivity.class);
                intent.putExtra("name", list.get(po));
                startActivity(intent);
                finish();
            }
        });

       but_clear.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                list=myDao.select();
                for (int i = 0; i <list.size() ; i++) {
                    myDao.delete(list.get(i));
                }
                list=myDao.select();
                adapter = new SousuoAdapter(SouSuoActivity.this, list);
                lv.setAdapter(adapter);
            }
        });




    }
}
