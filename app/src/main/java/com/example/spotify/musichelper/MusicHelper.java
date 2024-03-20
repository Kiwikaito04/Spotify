package com.example.spotify.musichelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;

import androidx.annotation.Nullable;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class MusicHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "Music.db";
    public static final String TABLE_NAME = "tblMusic";
    public static final String COLUMN_ID = "ID_Music";
    public static final String COLUMN_MUSICNAME = "MusicName";
    public static final String COLUMN_FILENAME = "FileName";
    public static ArrayList<MusicAdapter> ListSongs;
    public MusicHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 1);
        LoadListSongs();
    }

    private void LoadListSongs() {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery("Select * from tblMusic", null);
        while (cursor.moveToNext())
        {
            Integer IDMusic = cursor.getInt(0);
            String MusicName = cursor.getString(1);
            String FileName = cursor.getString(2);
            ListSongs.add(new MusicAdapter(IDMusic, MusicName, FileName));
        }
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create Table " + TABLE_NAME + "("
                + COLUMN_ID + " Text primary key, "
                + COLUMN_MUSICNAME + " Integer, "
                + COLUMN_FILENAME + " Text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop Table if exists " + TABLE_NAME);
    }
    public Boolean InsertMusic(MusicAdapter _music)
    {
        //Thiết lập chế độ writeable cho db
        SQLiteDatabase myDB = this.getWritableDatabase();
        //Thiết lập content để đưa vào bảng
        ContentValues music = new ContentValues();
        music.put(COLUMN_ID, _music.getIDMusic());
        music.put(COLUMN_MUSICNAME, _music.getMusicName());
        music.put(COLUMN_FILENAME, _music.getFileName());
        //Thêm user vào bảng
        long result = myDB.insert(TABLE_NAME, null, music);
        myDB.close();
        //Nếu không thành công, trả về false
        return result != -1;
    }

}
