package com.example.forcelogout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        val offline : Button = findViewById(R.id.offline)
        offline.setOnClickListener{
            val intent = Intent("com.example.forcelogout.FORCE_OFFLINE")
//            intent.setPackage(packageName)
            sendBroadcast(intent)
        }
    }
}