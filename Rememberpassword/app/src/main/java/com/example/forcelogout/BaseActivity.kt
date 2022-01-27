package com.example.forcelogout

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.content.IntentFilter
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AlertDialog
import androidx.appcompat.app.AppCompatActivity

open class BaseActivity : AppCompatActivity() {
    lateinit var receiver : ForceOfflineReceiver
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ActivityCollector.addActivity(this)
        Log.d("BaseActivity","onCreate")
    }

    override fun onDestroy() {
        super.onDestroy()
        ActivityCollector.removeActivity(this)
        Log.d("BaseActivity","onDestory")
    }

    /**
     * 重写onResume() 与 onPause() 保证只有栈顶接收广播
     */
    override fun onResume (){
        super.onResume()
        //创建action
        val intentFilter = IntentFilter()
        intentFilter.addAction("com.example.forcelogout.FORCE_OFFLINE")
        //创建instance
        receiver  = ForceOfflineReceiver()
        registerReceiver(receiver,intentFilter)
    }
    override fun onPause(){
        //取消注册
        super.onPause()
        unregisterReceiver(receiver)
    }


    inner class ForceOfflineReceiver:BroadcastReceiver(){
        override fun onReceive(context: Context?, intent: Intent?) {
            if (context != null) {
                //使用对话框
                AlertDialog.Builder(context).apply{
                    setTitle("Warning")
                    setMessage("Not allow to log in")
                    //设为不可取消
                    setCancelable(false)
                    setPositiveButton("OK"){_,_->
                        //销毁所有Activity
                        ActivityCollector.finishALl()
                        val i = Intent(context,Login::class.java)
                        context.startActivity(i)

                    }
                    show()

                }
            }

        }

    }
}