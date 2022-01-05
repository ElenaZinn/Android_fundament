package com.example.retrofittest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import okhttp3.OkHttpClient
import okhttp3.Request
import org.xmlpull.v1.XmlPullParser
import org.xmlpull.v1.XmlPullParserFactory
import java.io.BufferedReader
import java.io.InputStreamReader
import java.io.StringReader
import java.lang.Exception
import java.lang.StringBuilder
import java.net.CacheResponse
import java.net.HttpURLConnection
import java.net.URL
import kotlin.concurrent.thread


class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val sendRequest : Button = findViewById( R.id.sendRequest )

        sendRequest.setOnClickListener{
            sendRequestWithOkHttp()
        }
    }
    private fun sendRequestWithOkHttp(){
        thread {
            try{

                val client = OkHttpClient()
                val request = Request.Builder()
                    .url("http://172.25.160.8/get_data.xml")
                    .build()

                val response = client.newCall(request).execute()

                val responseDate = response.body?.string()

                if (responseDate == null){
                    Log.d("MainActivity","responsedata is null")
                }else{
                    parseXMLWithPull(responseDate)
                }

            }catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }

   private fun parseXMLWithPull(xmlData: String) {
       try{
           //新建XmlPullParserFactory实例
           val factory = XmlPullParserFactory.newInstance()

           //设置xml数据
           val xmlPullParser  = factory.newPullParser()
           xmlPullParser.setInput( StringReader(xmlData) )

           var eventType = xmlPullParser.eventType
           var id = ""
           var name = ""
           var version = ""
           while(eventType != XmlPullParser.END_DOCUMENT){

               val nodeName = xmlPullParser.name

               when(eventType){

                   //解析节点
                   XmlPullParser.START_TAG ->{
                        when(nodeName){
                            "id" -> id = xmlPullParser.nextText()
                            "name" -> name = xmlPullParser.nextText()
                            "version" -> version = xmlPullParser.nextText()
                        }
                   }

                   //解析完成
                   XmlPullParser.END_TAG  -> {
                       if ("app" == nodeName) {
                           Log.d ("MainActivity", "id is $id")
                           Log.d ("MainActivity", "name is $name")
                           Log.d ("MainActivity", "version is $version")

                       }
                   }
               }

               //获取下一个事件
               eventType = xmlPullParser.next()
           }


       }catch (e : Exception){
           e.printStackTrace()
       }

   }

}


