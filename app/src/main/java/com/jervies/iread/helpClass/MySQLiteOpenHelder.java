package com.jervies.iread.helpClass;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * 创建数据库用于存储收藏数据
 */

public class MySQLiteOpenHelder extends SQLiteOpenHelper {
    public MySQLiteOpenHelder(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table if not exists content(_id integer primary key autoincrement, type integer, item_id integer, title text, summary text, image text)");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("drop table if exists content");
        onCreate(db);
    }
}
