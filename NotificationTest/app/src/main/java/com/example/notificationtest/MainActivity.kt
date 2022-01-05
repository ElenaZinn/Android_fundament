package com.example.notificationtest

import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.graphics.BitmapFactory
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.app.NotificationCompat

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val notify :Button = findViewById(R.id.notify)


        //使用pendingIntent给notification添加跳转
        val intent = Intent(this,Notify::class.java)
        val pi = PendingIntent.getActivity(this,0,intent,0)

        //

        val manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        if (Build.VERSION.SDK_INT >=Build.VERSION_CODES.O){
            val channel = NotificationChannel("normal","Normal",NotificationManager.IMPORTANCE_DEFAULT)
            manager.createNotificationChannel(channel)
        }

        notify.setOnClickListener{
            val notification = NotificationCompat.Builder(this,"normal")
                .setContentTitle("this is content title")
                .setContentText("this is content text")
                .setSmallIcon(R.drawable.small_icon)
                .setLargeIcon(BitmapFactory.decodeResource(getResources(),R.drawable.big_icon))
                .setContentIntent(pi)
                .setAutoCancel(true)
                .build()
            manager.notify(1,notification)
        }
    }
}