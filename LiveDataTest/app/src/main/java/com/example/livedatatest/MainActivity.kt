package com.example.livedatatest

import android.content.Context
import android.content.SharedPreferences
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import androidx.core.content.edit
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider

class MainActivity : AppCompatActivity() {
    private lateinit var viewModel: MainViewModel
    private lateinit var sp: SharedPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        sp = getPreferences(Context.MODE_PRIVATE) //MODE_PRIVATE 只能被自己的应用程序访问

        val countReserved = sp.getInt("count_reserved", 0) //getInt 读取数据

//        lifecycle.addObserver(MyObserver(lifecycle))

        viewModel = ViewModelProvider(
            this,
            MainViewModelFactory(countReserved)
        ).get(MainViewModel::class.java)

        val plusOneBtn: Button = findViewById(R.id.plusOneBtn)
        val clearBtn: Button = findViewById(R.id.cls)
        val infoText:TextView = findViewById(R.id.infoText)
        val getUserBtn : Button = findViewById(R.id.getUserBtn)

        plusOneBtn.setOnClickListener {
            viewModel.plusOne()
        }
        clearBtn.setOnClickListener {
            viewModel.clear()

        }
        getUserBtn.setOnClickListener{
            val userId = (0..1000).random().toString()
            viewModel.getUser(userId)
        }


        viewModel.user.observe(this, Observer { user ->
            infoText.text = user.firstName
        })

    }

    override fun onPause() {
        super.onPause()
        //或缺editor对象， 通过调用editor对象写入数据
        sp.edit {
            putInt("count_reserved", viewModel.counter.value ?: 0)
        }

    }
}