package com.example.playaudiotest

import android.media.MediaPlayer
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class MainActivity : AppCompatActivity() {

    private val mediaPlayer  = MediaPlayer()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initMediaPlayer()

        val play : Button = findViewById( R.id.play )
        val pause :Button = findViewById( R.id.pause )
        val stop :Button = findViewById( R.id.stop )

        play.setOnClickListener{
            if(!mediaPlayer.isPlaying){
                mediaPlayer.start()
            }
        }

        pause.setOnClickListener{
            if ( mediaPlayer.isPlaying ){
                mediaPlayer.pause()
            }
        }

        stop.setOnClickListener{
            if ( mediaPlayer.isPlaying ){
                mediaPlayer.reset() //停止播放
                initMediaPlayer()
            }
        }


    }


    private fun initMediaPlayer(){
        val assetManager = assets
        val fd = assetManager.openFd("music.mp3")
        mediaPlayer.setDataSource( fd.fileDescriptor, fd.startOffset, fd.length)
        mediaPlayer.prepare()
    }


    override fun onDestroy() {
        super.onDestroy()
        mediaPlayer.stop()
        mediaPlayer.release()
    }
}
