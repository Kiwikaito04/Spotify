package com.example.spotify.musichelper;

import android.content.ContentValues;
import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.Console;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

public class MusicHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "Music.db";
    public static final String TABLE_NAME = "tblMusic";
    public static final String COLUMN_ID = "ID_Music";
    public static final String COLUMN_MUSICNAME = "MusicName";
    public static final String COLUMN_FILENAME = "FileName";
    public static ArrayList<MusicAdapter> ListSongs = new ArrayList<>();
    public MusicHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 1);
        LoadListSongs();
        if(ListSongs.isEmpty())
        {
            CreateNewListSongs();
            LoadListSongs();
        }
    }

    private void CreateNewListSongs() {

        String[] TenBaiHat = {"ball in the jals","nigg"};
        String[] TenFile = {"music","music2"};
        for(int i = 0 ; i < TenFile.length ; i++) {
            MusicAdapter song = new MusicAdapter(i, TenBaiHat[i], TenFile[i], null);
            if (!InsertMusic(song))
                Log.w("Insert song","Some thing went wrong");
        }
    }

    private void LoadListSongs() {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor cursor = myDB.rawQuery(
                String.format("Select * from %s",TABLE_NAME),
                null
        );
        while (cursor.moveToNext())
        {
            Integer IDMusic = cursor.getInt(0);
            String MusicName = cursor.getString(1);
            String FileName = cursor.getString(2);
            ListSongs.add(new MusicAdapter(IDMusic, MusicName, FileName, null));
        }
        cursor.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s INTEGER, %s TEXT)",
                TABLE_NAME, COLUMN_ID, COLUMN_MUSICNAME, COLUMN_FILENAME)
        );

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL(
                String.format("drop Table if exists %s",TABLE_NAME)
        );
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
    public static ArrayList<MusicAdapter> getListSongs()
    {
        return ListSongs;
    }
}
