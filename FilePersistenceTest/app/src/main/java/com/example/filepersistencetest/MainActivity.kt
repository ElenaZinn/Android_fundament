package com.example.filepersistencetest

import android.content.Context
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.EditText
import android.widget.Toast
import android.widget.Toast.*
import java.io.*

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        //调用load方法
        val inputText = load()
        //非空将内容填充到EditText
        if(! inputText.isEmpty()){
            val editText:EditText = findViewById(R.id.editText)
            editText.setText (inputText)
            //光标移动到文末
            editText.setSelection(inputText.length)
            //弹出成功提示
            Toast.makeText(this,"Successfully",Toast.LENGTH_SHORT).show()

        }
    }

    //存数据到文件
    override fun onDestroy() {
        super.onDestroy()
        val editText : EditText = findViewById(R.id.editText)
        val inputText = editText.text.toString()
        //保证关闭 activity 前一定会调用save
        save(inputText)
    }


    private fun save(inputText:String){
        try{
            val output = openFileOutput("data", Context.MODE_PRIVATE)
            val writer = BufferedWriter(OutputStreamWriter(output))
            writer.use{
                it.write(inputText)
            }
        }catch (e: IOException){
            e.printStackTrace()
        }

    }



    //从文件读取数据
    private fun load():String{
        val content = StringBuilder()
        try{
            val input = openFileInput("data")
            val reader = BufferedReader(InputStreamReader(input))
            reader.use{
                reader.forEachLine {
                    content.append(it)
                }
            }

        }catch (e:IOException){
            e.printStackTrace()
        }
        return content.toString()
    }

}