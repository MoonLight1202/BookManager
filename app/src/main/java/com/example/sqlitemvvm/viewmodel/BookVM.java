package com.example.sqlitemvvm.viewmodel;

import android.content.ContentValues;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;

import com.example.sqlitemvvm.database.Database;
import com.example.sqlitemvvm.model.BookInfo;

import java.util.ArrayList;

public class BookVM {

    public ArrayList<BookInfo> getALl(){
        SQLiteDatabase database = Database.db;
        ArrayList<BookInfo> list = new ArrayList<>();
        String sqlSelect = "SELECT * FROM "+Database.TABLE_NAME;
        Cursor cs = database.rawQuery(sqlSelect, null);
        while (cs.moveToNext()){
            int id = cs.getInt(0);
            String name = cs.getString(1);
            int page = cs.getInt(2);
            int price = cs.getInt(3);
            String desc = cs.getString(4);
            byte[] avt = cs.getBlob(5);
            list.add(new BookInfo(id, name, page, price, desc,avt));
        }
        return list;
    }
    public long insert(BookInfo b){
        SQLiteDatabase database = Database.db;
        ContentValues cv = new ContentValues();
        cv.put("name", b.getName());
        cv.put("page", b.getPage());
        cv.put("price", b.getPrice());
        cv.put("desc", b.getDesc());
        cv.put("avt", b.getDesc());
        return database.insert(Database.TABLE_NAME, null, cv);
    }
    public int update(BookInfo b){
        SQLiteDatabase database = Database.db;
        ContentValues cv = new ContentValues();
        cv.put("name", b.getName());
        cv.put("page", b.getPage());
        cv.put("price", b.getPrice());
        cv.put("desc", b.getDesc());
        cv.put("avt", b.getDesc());
        String whereClause = "id = ?";
        String[] whereArgs = {b.getId()+""};
        return database.update(Database.TABLE_NAME, cv, whereClause, whereArgs);
    }
    public int delete(BookInfo b){
        SQLiteDatabase database = Database.db;
        String whereClause = "id = ?";
        String[] whereArgs = {b.getId()+""};
        return database.delete(Database.TABLE_NAME, whereClause, whereArgs);
    }
    public int delete(int id){
        SQLiteDatabase database = Database.db;
        String whereClause = "id = ?";
        String[] whereArgs = {id+""};
        return database.delete(Database.TABLE_NAME, whereClause, whereArgs);
    }

}
