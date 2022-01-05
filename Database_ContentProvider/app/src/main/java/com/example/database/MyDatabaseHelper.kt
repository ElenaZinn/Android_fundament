package com.example.database

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import android.widget.Toast

class MyDatabaseHelper(val context: Context,name:String,version : Int):SQLiteOpenHelper( context,name,null,version) {

    //创建数据库的SQL语法
    private val createBook = "create table book ( " +
            "id integer primary key autoincrement," +
            "author text, " +
            "price real," +
            "pages integer," +
            "name text)"

    private val createCategory = "create table Category ( " +
            "id integer primary key autoincrement," +
            "category_name text," +
            "category_code integer" +
            "category_id integer)"

    override fun onCreate(db: SQLiteDatabase?) {
        //创建数据库
        db?.execSQL(createBook)
        db?.execSQL(createCategory)
//        Toast.makeText(context,"Create succeeded",Toast.LENGTH_SHORT).show()  跨程序访问时我们不能直接使用Toast

    }

    override fun onUpgrade(db: SQLiteDatabase?, olderVersion: Int, newVersion: Int) {
        if (olderVersion <=1){
            db?.execSQL(createCategory)
        }

        if (olderVersion <=3){
            db?.execSQL("alter table Category add column category_id integer")
        }

    }
}