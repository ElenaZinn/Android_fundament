package com.example.servicetest

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.os.IBinder
import android.widget.Button

class MainActivity : AppCompatActivity() {

    lateinit var downloadBinder : MyService.DownloadBinder

    private val connection = object : ServiceConnection{
        override fun onServiceConnected(p0: ComponentName?, p1: IBinder?) {
            downloadBinder = p1 as MyService.DownloadBinder
            downloadBinder.startDownload()
            downloadBinder.getProcess()
        }

        override fun onServiceDisconnected(p0: ComponentName?) {
//            TODO("Not yet implemented")
        }

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)






        val startService: Button = findViewById( R.id.startService )
        val stopService : Button = findViewById( R.id.stopService )
        val bindService : Button = findViewById( R.id.bindService )
        val unbindService: Button = findViewById( R.id.unbindService )

        //启动和停止
        startService.setOnClickListener{
            val intent = Intent(this, MyService::class.java)
            startService(intent)
        }

        stopService.setOnClickListener{
            val intent = Intent(this,MyService::class.java)
            stopService(intent)
        }

        //绑定和解绑定
        bindService.setOnClickListener{
            val intent = Intent(this, MyService::class.java)
            bindService(intent, connection, Context.BIND_AUTO_CREATE)
        }

        unbindService.setOnClickListener{
            unbindService(connection)
        }


    }
}