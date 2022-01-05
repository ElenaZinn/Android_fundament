package com.example.database

import android.annotation.SuppressLint
import android.content.ContentValues
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button


class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContentView(R.layout.activity_main)
        //构造器，指定数据库名字与版本号
        val dbHelper = MyDatabaseHelper(this,"BookStore.db",3)

        val button: Button = findViewById(R.id.button)
        val add_button :Button = findViewById(R.id.add_data)
        val update_button : Button = findViewById(R.id.update_data)
        val delete_button  :Button = findViewById(R.id.delete_data)
        val query_button  :Button = findViewById(R.id.query_data)
        val replace_button  :Button = findViewById(R.id.replace_data)

        button.setOnClickListener {
           dbHelper.writableDatabase
        }

        add_button.setOnClickListener {

            val db = dbHelper.writableDatabase
            //添加数据,使用ContentValues()
            val values1 = ContentValues().apply{
                put("name","The Da Vinci Code")
                put("author","Dan Brown")
                put("pages",454)
                put("price",16.96)
            }
            db.insert("Book",null,values1)

            val values2 = ContentValues().apply{
                put("name","The Lost Symbol")
                put("author","Dan Brown")
                put("pages",510)
                put("price",19.95)
            }
            db.insert("book",null,values2)
        }

        update_button.setOnClickListener {
            val db = dbHelper.writableDatabase

            val values2 = ContentValues()

            values2.put("price", 10.99 )

            db.update("book",values2,"name = ? " , arrayOf("The Da Vinci Code"))

        }

        delete_button.setOnClickListener {
            val db = dbHelper.writableDatabase
            db.delete("book","pages > ?", arrayOf("500"))
        }


        query_button.setOnClickListener {
            val db = dbHelper.writableDatabase
            val cursor = db.query("Book", null, null, null, null, null, null)
            if (cursor.moveToFirst()){
                do{
                    val name = cursor.getString(cursor.getColumnIndex("name"))
                    val author= cursor.getString(cursor.getColumnIndex("author"))
                    val  pages= cursor.getString(cursor.getColumnIndex("pages"))
                    val  price= cursor.getString(cursor.getColumnIndex("price"))

                    Log.d("MainActivity","book name is $name")
                    Log.d("MainActivity","book author is $author")
                    Log.d("MainActivity","book pages is $pages")
                    Log.d("MainActivity","book price is $price")


                }while (cursor.moveToNext())
            }
            cursor.close()
        }


        replace_button.setOnClickListener {
            val db = dbHelper.writableDatabase
            //开启事务
            db.beginTransaction()
            try {
                db.delete("book",null,null)
                //手动抛出一个异常，让事务失败
//                if (true){
//                    throw NullPointerException()
//                }

                val values = ContentValues().apply{
                    put("name","Game of Thrones")
                    put("author","George Martin")
                    put("pages",720)
                    put("price",56)
                }

                db.insert("book",null,values)
                db.setTransactionSuccessful()  //事务已经执行成功

            }catch (e: Exception){

                e.printStackTrace()

            }finally{

                db.endTransaction()

            }
        }

    }
}