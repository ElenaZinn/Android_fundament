package com.example.providertest

import android.annotation.SuppressLint
import android.net.Uri


import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.core.content.contentValuesOf

class MainActivity : AppCompatActivity() {
    @SuppressLint("Range")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val add_button : Button = findViewById(R.id.add_button)
        val  query_button :Button = findViewById(R.id.query_button)
        val  update_button :Button = findViewById(R.id.update_button)
        val  delete_button :Button = findViewById(R.id.delete_button)

        var bookId: String? =null

        add_button.setOnClickListener{

            val uri = Uri.parse("content://com.example.database.provider/book")
            val values = contentValuesOf("name" to "A Clash of kings",
                    "author" to "George Martin" ,
                    "pages" to 1024,
                    "price" to 22.85)
            val newUri = contentResolver.insert(uri, values)
            bookId = newUri?.pathSegments?.get(1)

        }


        query_button.setOnClickListener{
            val uri = Uri.parse("content://com.example.database.provider/book")
            contentResolver.query(uri,null,null,null,null)?.apply{
                while (moveToNext()){
                    val name = getString(getColumnIndex("name"))
                    val author = getString(getColumnIndex("author"))
                    val pages = getInt(getColumnIndex("pages"))
                    val price = getDouble(getColumnIndex("price"))

                    Log.d("MainActivity","name is $name")
                    Log.d("MainActivity","author is $author")
                    Log.d("MainActivity","pages is $pages")
                    Log.d("MainActivity","price is $price")

                }
                //不要忘记关闭cursor()
                close()
            }


        }



        update_button.setOnClickListener{
            bookId?.let{
                val uri = Uri.parse("content://com.example.database.provider/book/$it")

                val values = contentValuesOf("name" to "A Storm of Swords",
                                                "pages" to 1216,
                                                "price" to 24.05)
                contentResolver.update(uri,values,null,null)
            }

        }


        delete_button.setOnClickListener{

            bookId?.let{
                val uri = Uri.parse("content://com.example.database.provider/book/$it")
                contentResolver.delete(uri,null,null)
            }

        }

    }
}