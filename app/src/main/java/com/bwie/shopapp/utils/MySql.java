package com.bwie.shopapp.utils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 作者：张玉轲
 * 时间：2017/10/11
 */

public class MySql extends SQLiteOpenHelper{

    public MySql(Context context) {
        super(context, "info.db", null, 1);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        sqLiteDatabase.execSQL("create table sousuo(_id integer primary key AUTOINCREMENT,name varchar(20))");
       sqLiteDatabase.execSQL("create table addjsons(json varchar(300))");
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {

    }
}
