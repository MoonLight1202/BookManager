package com.example.sqlitemvvm.database;

import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import androidx.annotation.Nullable;

public class Database extends SQLiteOpenHelper {
    public static final String DB_NAME = "qlsach.sqlite";
    public static final int VERSION = 3;
    public static final String TABLE_NAME = "BOOKS";
    public static SQLiteDatabase db = null;
    public Database(@Nullable Context context) {
        super(context, DB_NAME, null, VERSION);
        db = getWritableDatabase();
    }
    public Database(@Nullable Context context, @Nullable String name, @Nullable SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase sqLiteDatabase) {
        String sqlCreateDB = "CREATE TABLE "+TABLE_NAME + "(id integer primary key autoincrement, name text, page integer, price float, descr text,avt byte)";
        sqLiteDatabase.execSQL(sqlCreateDB);
    }
    public void excuteSQL(String sql){
        SQLiteDatabase database = getReadableDatabase();
        database.execSQL(sql);
    }

    @Override
    public void onUpgrade(SQLiteDatabase sqLiteDatabase, int i, int i1) {
        sqLiteDatabase.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);
        onCreate(sqLiteDatabase);
    }
    public Cursor selectSQL(String sql){
        SQLiteDatabase database = getReadableDatabase();
        return database.rawQuery(sql,null);
    }

}

