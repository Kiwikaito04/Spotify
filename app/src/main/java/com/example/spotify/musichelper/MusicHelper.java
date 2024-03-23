package com.example.spotify.musichelper;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.util.Log;

import androidx.annotation.Nullable;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;

public class MusicHelper extends SQLiteOpenHelper
{
    public static final String DB_NAME = "Music.db";
    public static final String TABLE_NAME = "tblMusic";
    public static final String COLUMN_ID = "ID_Music";
    public static final String COLUMN_MUSICNAME = "MusicName";
    public static final String COLUMN_FILENAME = "FileName";
    public static final String COLUMN_IMAGE = "ImageMusic";
    public static ArrayList<MusicAdapter> ListSongs = new ArrayList<>();
    public MusicHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 1);
        LoadListSongs();
        if(ListSongs.isEmpty())
        {
            CreateNewListSongs(context);
            LoadListSongs();
        }
    }

    private void CreateNewListSongs(Context context) {
        if (!InsertMusic(new MusicAdapter(0, null, null, null)))
            Log.w("Insert song","Some thing went wrong");
        String[] TenBaiHat = { null,
                "Yoru ni Kakeru", "Halzion", "Yome Wo Nazotte", "Gunjo", "Monster", "Haruka", "Lover Letter", "RGB", "The Blessing", "Moshimo Inochi Ga Egaketara", "Idol", "Yuusha", "Tsubame", "Gods", "Legends never die", "ただ声一つ", "Lemon", "Unravel", "Hare hare ya", "Renai Circulation", "Senbonzakura", "Tháng tư là lời nói dối của em", "Một bước yêu vạn dặm đau", "Em của ngày hôm qua", "Hạ còn vương nắng", "Tình yêu màu Nắng", "Thu cuối", "Nếu như anh đến", "Chiếc khăn gió ấm", "Tấm lòng son", "Sóng gió", "Chạy về khóc với anh", "Short skirt", "Nàng thơ", "Shape of you", "Attention", "Havana", "Despacito", "Kawaiikute Gomen", "Đôi mắt", "Anh nhà ở đâu thế", "Lạc trôi", "Tabun", "BigCityBoi", "Vợ người ta"
            };
//        String[] TenBaiHat = {null, "Test 1", "Test 2"};
        byte[] img;

        for(int i = 1 ; i <= TenBaiHat.length ; i++) {
//            int imgID = context.getResources().getIdentifier(String.format("%s%s","pt_", i), "drawable", context.getPackageName());
//            if(imgID != 0) {
//                Bitmap bitmap = BitmapFactory.decodeResource(context.getResources(), imgID);
//                ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
//                bitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArray);
//                img = byteArray.toByteArray();
//            }
//            else img = null;
            MusicAdapter song = new MusicAdapter(
                    i,
                    TenBaiHat[i],
                    String.format("%s%s","msc_", i),
                    String.format("%s%s","pt_", i)
            );
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
            String ImageName = cursor.getString(3);
            ListSongs.add(new MusicAdapter(IDMusic, MusicName, FileName, ImageName));
        }
        cursor.close();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(
                String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s INTEGER, %s TEXT, %s byte[])",
                TABLE_NAME, COLUMN_ID, COLUMN_MUSICNAME, COLUMN_FILENAME, COLUMN_IMAGE)
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
        music.put(COLUMN_IMAGE, _music.getImageMusic());
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
