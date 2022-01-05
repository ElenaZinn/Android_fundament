package com.example.broadcasttest

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.OutcomeReceiver
import android.widget.Button
import android.widget.TimePicker
import android.widget.Toast

class MainActivity : AppCompatActivity() {
    lateinit var timeChangeReceiver: TimeChangeReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        /**添加action，action是指定接收的广播。
         * "android.intent.action.TIME_LOCK"是系统的一条广播
         * sdk-->platforms-->android api--?data--?broadcast_actions.txt中有完整的系统广播列表
         */
        val intentFilter = IntentFilter()
        intentFilter.addAction("android.intent.action.TIME_LOCK")
        //创建instance
        timeChangeReceiver = TimeChangeReceiver()
        //使用registerReceiver()注册
        registerReceiver(timeChangeReceiver,intentFilter)
        val button : Button = findViewById(R.id.button1)
        button.setOnClickListener{
            val intent = Intent("com.example.broadcasttest.MY_BROADCAST")
            intent.setPackage(packageName)
            sendBroadcast(intent)
            sendOrderedBroadcast(intent,null)
        }

    }
    override fun onDestroy(){
        //动态注册的BroadcastReceiver必须取消注册
        super.onDestroy()
        unregisterReceiver(timeChangeReceiver)
    }
    inner class TimeChangeReceiver :BroadcastReceiver(){
        override fun onReceive(p0: Context?, p1: Intent?) {
            Toast.makeText(p0,"Time is changed",Toast.LENGTH_SHORT).show()
        }

    }
}