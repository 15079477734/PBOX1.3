package com.planboxone.util;


import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteDatabase.CursorFactory;
import android.database.sqlite.SQLiteOpenHelper;

//DatabaseHelper作为一个访问SQLite的助手类，提供两个方面的功能，
//第一，getReadableDatabase(),getWritableDatabase()可以获得SQLiteDatabse对象，通过该对象可以对数据库进行操作
//第二，提供了onCreate()和onUpgrade()两个回调函数，允许我们在创建和升级数据库时，进行自己的操作

public class DBhelper extends SQLiteOpenHelper {

    private static final int VERSION = 1;
    private static String name = "mydb.db";

    //在SQLiteOepnHelper的子类当中，必须有该构造函数
    public DBhelper(Context context, String name, CursorFactory factory,
                    int version) {
        //必须通过super调用父类当中的构造函数
        super(context, name, factory, version);

    }

    public DBhelper(Context context, String name, int version) {
        this(context, name, null, version);
    }

    public DBhelper(Context context, String name) {
        this(context, name, VERSION);
    }

    public DBhelper(Context context) {
        super(context, name, null, VERSION);
    }
    //该函数是在第一次创建数据库的时候执行,实际上是在第一次得到SQLiteDatabse对象的时候，才会调用这个方法

    public void onCreate(SQLiteDatabase db) {
        ////创建数据库
        //execSQL函数用于执行SQL语句
        db.execSQL("create table user(_id INTEGER PRIMARY KEY AUTOINCREMENT,planOne text,planTwo text,planThree text,time text,week text,scoreOne text,scoreTwo text,scoreThree text)");
        db.execSQL("create table note(_id INTEGER PRIMARY KEY AUTOINCREMENT,note text,time text,priority text)");
    }

    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
    }

}
