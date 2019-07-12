package com.salthai.a17376042_khb_finaltest.Db;
import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class DBHelper extends SQLiteOpenHelper {
    private static DBHelper instant = null;
    //单例模式
    public static DBHelper getInstant(Context context) {
        if (instant == null) {
            instant = new DBHelper(context);
        }
        return instant;
    }

    private DBHelper(Context context) {
        super(context, DBConstant.DB_NAME, null, DBConstant.DB_VERSION);
    }


    @Override
    public void onCreate(SQLiteDatabase db) {
        String tb_user = "create table if not exists user(id integer primary key autoincrement,username text,password text)";
        //String tb_number = "create table if not exists news(id integer primary key autoincrement,name text,number text)";
        //db.execSQL(tb_number);
        db.execSQL(tb_user);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

    }
}
