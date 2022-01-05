package com.example.sharedpreferencestest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val button : Button = findViewById(R.id.button)
        val restore : Button = findViewById(R.id.Restore)
        button.setOnClickListener{
            val editor = getSharedPreferences("data", Context.MODE_PRIVATE).edit()
            editor.putString("name","Tom")
            editor.putInt("age",20)
            editor.putBoolean("married",true)
            editor.apply()

        }
        restore.setOnClickListener{
            val prefs = getSharedPreferences("data", Context.MODE_PRIVATE)
            val name = prefs.getString("name","")
            val age = prefs.getInt("age",0)
            val marry = prefs.getBoolean("married",false)

            Log.d("MainActivity","name  is $name")
            Log.d("MainActivity","age  is $age")
            Log.d("MainActivity","marry is $marry")

        }

    }
}