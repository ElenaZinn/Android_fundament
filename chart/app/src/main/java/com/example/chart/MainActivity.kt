package com.example.chart

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.EditText
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() , View.OnClickListener{
    private val msgList = ArrayList<Msg>();

    private lateinit var adapter :MsgAdapter //adapter设置成全局变量，初始化在onCreate中，所以只能先赋null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //判断是否初始化了
        if (!::adapter.isInitialized){
            adapter = MsgAdapter(msgList)
        }
        //


        initMsg()


        val layoutManager = LinearLayoutManager(this)
        val recyclerView :RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter  = MsgAdapter(msgList)
        recyclerView.adapter = adapter
        val send : Button = findViewById(R.id.send)
        send.setOnClickListener(this)
    }

    override fun onClick(v: View?) {
        val send : Button = findViewById(R.id.send)
        when(v){
             send ->{
                val inputText : EditText = findViewById(R.id.inputText)
                val content = inputText.text.toString()
                if (content.isNotEmpty()){
                    val msg = Msg(content,Msg.TYPE_SENT)
                    msgList.add(msg)
                    adapter .notifyItemInserted(msgList.size-1)  //当有新消息，刷新屏幕显示
                    val recyclerView :RecyclerView = findViewById(R.id.recyclerView)
                    recyclerView.scrollToPosition(msgList.size-1) //定位到最后一行
                    inputText.setText("")  //清空输入框的内容
                }
            }
        }

    }
    private  fun initMsg(){
        val msg1 = Msg ("Are you OK?",Msg.TYPE_RECEIVED)
        msgList.add(msg1)
        val msg2 = Msg ("Not bad",Msg.TYPE_SENT)
        msgList.add(msg2)
        val msg3 = Msg ("See you next day",Msg.TYPE_RECEIVED)
        msgList.add(msg3)

    }


}