package com.bwie.shopapp.utils;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import java.util.ArrayList;
import java.util.List;

/**
 * 作者：张玉轲
 * 时间：2017/10/11
 */

public class MyDao {
    private Context context;
    private MySql mySql;

    public MyDao(Context context) {
        this.context = context;
        mySql = new MySql(context);
        SQLiteDatabase db = mySql.getWritableDatabase();
    }
    //搜索
    public void add(String name){
        SQLiteDatabase db = mySql.getWritableDatabase();
        db.execSQL("insert into sousuo (name) values(?)",new Object[]{name});
    }
    public List<String> select(){
        List<String> list = new ArrayList<>();
        SQLiteDatabase db = mySql.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from sousuo", null);
        while (cursor.moveToNext()){
            String name = cursor.getString(cursor.getColumnIndex("name"));
            list.add(name);
        }
        return list;
    }
    public void delete(String name){
        SQLiteDatabase db = mySql.getWritableDatabase();
        db.execSQL("delete from sousuo where name=? ",new Object[]{name});
    }

//    //缓存数据
    public void addjson(String json){
        SQLiteDatabase db = mySql.getWritableDatabase();
        db.execSQL("insert into addjsons (json) values(?)",new Object[]{json});
    }
    public void updateJson(String json){
        SQLiteDatabase db = mySql.getWritableDatabase();
        db.execSQL("update addjsons set json=?",new Object[]{json});
    }

    public String selejson(){
        String json=null;
        SQLiteDatabase db = mySql.getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from addjsons", null);
        while (cursor.moveToNext()){
            json = cursor.getString(cursor.getColumnIndex("json"));

        }
        return json;
    }

}
