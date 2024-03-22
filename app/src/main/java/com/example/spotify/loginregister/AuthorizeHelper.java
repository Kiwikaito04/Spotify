package com.example.spotify.loginregister;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class AuthorizeHelper extends SQLiteOpenHelper
{
    //Tên file DB được lưu vào
    public static final String DB_NAME = "Account.db";
    public  static final String TABLE_NAME = "tblUsers";
    public static final String COLUMN_EMAIL = "email";
    public static final String COLUMN_USERNAME = "username";
    public static final String COLUMN_PASSWORD = "password";
    //Constructor
    public AuthorizeHelper(@Nullable Context context)
    {
        super(context, DB_NAME, null, 1);
    }
    //Tạo bảng tblUsers nếu chưa tồn tại
    @Override
    public void onCreate(SQLiteDatabase db)
    {
        db.execSQL("create Table " + TABLE_NAME + "("
                + COLUMN_EMAIL + " Text primary key, "
                + COLUMN_USERNAME + " Text, "
                + COLUMN_PASSWORD + " Text)");
    }
    //Xóa bảng tblUser nếu đã tồn tại
    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion)
    {
        db.execSQL("drop Table if exists " + TABLE_NAME);
    }
    //Thêm dòng giá trị vào bảng
    public Boolean InsertData(UserAdapter _user)
    {
        //Thiết lập chế độ writeable cho db
        SQLiteDatabase myDB = this.getWritableDatabase();
        //Thiết lập content để đưa vào bảng
        ContentValues user = new ContentValues();
        user.put(COLUMN_EMAIL, _user.getEmail());
        user.put(COLUMN_USERNAME, _user.getUsername());
        user.put(COLUMN_PASSWORD, _user.getPassword());
        //Thêm user vào bảng
        long result = myDB.insert(TABLE_NAME, null, user);
        myDB.close();
        //Nếu không thành công, trả về false
        return result != -1;
    }
    public Cursor isAvailableEmailOrUserName(String Email, String Username)
    {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor user = myDB.rawQuery("Select * from tblUsers where (email = ? or username = ?)",new String[]{Email, Username});
        return user;
    }
    public Cursor CheckEmail(String email)
    {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor isAvailableEmail = myDB.rawQuery("Select * from tblUsers where email = ?", new String[]{email});

        return isAvailableEmail;
    }
    public Boolean CheckAccount(String Username, String Password)
    {
        SQLiteDatabase myDB = this.getReadableDatabase();
        Cursor isAvailableAccount = myDB.rawQuery("Select * from tblUsers where (? = ?)", new String[]{ Username, Password});
        return isAvailableAccount.getCount() > 0;
    }
}
