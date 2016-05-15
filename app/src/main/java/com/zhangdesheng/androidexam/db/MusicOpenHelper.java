package com.zhangdesheng.androidexam.db;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Administrator on 2016/5/14.
 */
public class MusicOpenHelper extends SQLiteOpenHelper {

    /*
    * Music建表语句
    * */public static final String CREATE_MUSIC = "create table Music (" +
            "id integer primary key autoincrement, " +
            "albumpic_big text, " +
            "albumpic_small text" +
            "downUrl text, " +
            "m4a text, " +
            "singername text, " +
            "songid integer, " +
            "songname text)";



    public MusicOpenHelper(Context context, String name,
                           SQLiteDatabase.CursorFactory factory,int version){
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(CREATE_MUSIC);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
