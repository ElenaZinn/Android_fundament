package com.example.playvideotest

import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.VideoView

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //将mp4解析成Uri对象
        val video : VideoView = findViewById( R.id.video )
        val uri = Uri.parse("android.resource://$packageName/${R.raw.video}")


        video.setVideoURI(uri)

        //添加点击事件
        val start : Button = findViewById( R.id.play )
        val pause : Button = findViewById( R.id.pause )
        val replay : Button = findViewById( R.id.replay )
        start.setOnClickListener{
            if ( ! video.isPlaying) {
                video.start()
            }
        }

        pause.setOnClickListener{
            if (video.isPlaying){
                video.pause()
            }
        }

        replay.setOnClickListener{
            if(video.isPlaying){
                video.resume()   //重新播放
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
        val video : VideoView = findViewById( R.id.video )
        video.suspend()
    }
}