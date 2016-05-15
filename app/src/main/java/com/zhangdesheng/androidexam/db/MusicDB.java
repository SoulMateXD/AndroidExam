package com.zhangdesheng.androidexam.db;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.zhangdesheng.androidexam.model.Music;

import java.util.ArrayList;

/**
 * Created by Administrator on 2016/5/15.
 */
public class MusicDB {

    public static final String DB_NAME = "cool_weather";
    public static final int VERSION = 2;
    private static MusicDB MusicDB;
    private SQLiteDatabase db;
    private ArrayList<Music> datas;

    private MusicDB(Context context){
        MusicOpenHelper dbHelper = new MusicOpenHelper(context, DB_NAME, null, VERSION);
        db = dbHelper.getWritableDatabase();//若磁盘空间已满会报异常
    }

    public synchronized static MusicDB getInstance(Context context){ //这里为什么要用synchronized呢？
        if (MusicDB == null ){
            MusicDB = new MusicDB(context);
        }
        return MusicDB;
    }

    public void deleteMusic(Music music){
        db.delete("Music", "songid = ?", new String[]{ String.valueOf(music.getSongid())} );
    }

    public void saveMusicInHot(Music music){
        boolean isExit = false;
        datas = loadMusic();
        /*防止多次存储*/
        for (int i =0; i<datas.size(); i++){
            if (music.getSongid() == datas.get(i).getSongid()){
                isExit = true;
            }

        }
        if (music != null && !isExit){
            ContentValues values = new ContentValues();
            values.put("albumpic_big", music.getAlbumpic_big()+"");
            values.put("albumpic_small", music.getAlbumpic_small()+"");
            values.put("m4a", music.getUrl()+"");
            values.put("singername", music.getSingername()+"");
            values.put("songid", music.getSongid());
            values.put("songname", music.getSongname()+"");
            db.insert("Music", null, values);
        }
    }

    public void saveMusicInSearch(Music music){
        boolean isExit = false;
        datas = loadMusic();
        /*防止多次存储*/
        for (int i =0; i<datas.size(); i++){
            if (music.getSongid() == datas.get(i).getSongid()){
                isExit = true;
            }

        }
        if (music != null && !isExit){
            ContentValues values = new ContentValues();
            values.put("albumpic_big", music.getAlbumpic_big()+"");
            values.put("albumpic_small", music.getAlbumpic_small()+"");
            values.put("m4a", music.getM4a()+"");
            values.put("singername", music.getSingername()+"");
            values.put("songid", music.getSongid());
            values.put("songname", music.getSongname()+"");
            db.insert("Music", null, values);
        }
    }

    public ArrayList<Music> loadMusic(){
        ArrayList<Music> arrayList = new ArrayList<>();
        Cursor cursor = db.query("Music",null, null, null, null, null, null);
        if (cursor.moveToFirst()){
            do{
                Music music = new Music();
                music.setAlbumpic_big(cursor.getString(cursor.getColumnIndex("albumpic_big")));
                music.setAlbumpic_small(cursor.getString(cursor.getColumnIndex("albumpic_small")));
                music.setM4a(cursor.getString(cursor.getColumnIndex("m4a")));
                music.setSingername(cursor.getString(cursor.getColumnIndex("singername")));
                music.setSongid(cursor.getInt(cursor.getColumnIndex("songid")));
                music.setSongname(cursor.getString(cursor.getColumnIndex("songname")));
                arrayList.add(music);
            }while(cursor.moveToNext());
        }

        if (cursor != null){
            cursor.close();
        }

        return arrayList;
    }
}
