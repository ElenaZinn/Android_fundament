package com.example.networktest

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import okhttp3.*
import org.json.JSONArray
import java.io.BufferedReader
import java.io.IOException
import java.io.InputStreamReader
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
//            sendRequestWithHttpURLConnection()
//            sendRequestWithOkHttp()
            val address : String = "http://172.25.160.8/get_data.json"

            //回调函数
            HttpUtil.sendOkHttpRequest(address, object :Callback{
                override fun onFailure(call: Call, e: IOException) {
//                    TODO("Not yet implemented")
                }

                override fun onResponse(call: Call, response: Response) {
                    val responseData  = response.body?.string()
                    if (responseData != null) {
                        parseJsonWithGSON(responseData)
                    }

                }

            })
        }
    }


    private fun sendRequestWithHttpURLConnection(){
        //使用HttpURLConnection
        thread{
            var connection: HttpURLConnection? = null

            try{
                val response = StringBuilder()
                //使用URL
                val url = URL("https://www.baidu.com")
                connection = url.openConnection() as HttpURLConnection
                //设置超时信息
                connection.connectTimeout = 8000
                connection.readTimeout = 8000


                val input = connection.inputStream
                //读取输入流
                val reader = BufferedReader(InputStreamReader(input))
                reader.use{
                    reader.forEachLine {
                        response.append(it)
                    }
                }
                //修改UI
                showResponse(response.toString())
            }catch (e : Exception){
                e.printStackTrace()
            }finally {
                connection?.disconnect()
            }
        }
    }

    private fun sendRequestWithOkHttp(){
        thread {
            try{

                val client = OkHttpClient()
                val request = Request.Builder()
//                    .url("https://www.baidu.com")
                    .url("http://172.25.160.8/get_data.json")
                    .build()

                val response = client.newCall(request).execute()

                val responseDate = response.body?.string()

                if (responseDate == null){
                    Log.d("MainActivity","responsedata is null")
                }else{

//                    showResponse(responseDate) //开启线程修改UI
//                    parseJsonWithJSONObject(responseDate)
                    parseJsonWithGSON(responseDate)
                }

            }catch(e: Exception) {
                e.printStackTrace()
            }
        }
    }


    private fun showResponse(response: String){
        runOnUiThread{
            val responseText : TextView = findViewById(R.id.responseText)
            responseText.text = response
        }

    }

    private fun parseJsonWithJSONObject(jsonData : String){
        try{
            val jsonArray = JSONArray(jsonData)

            for (i in 0 until jsonArray.length()){
                val jsonObject = jsonArray.getJSONObject(i)

                val id = jsonObject.getString("id")
                val name = jsonObject.getString("name")
                val version = jsonObject.getString("version")

                Log.d("MainActivity" , "id is $id")
                Log.d("MainActivity" , "name is $name")
                Log.d("MainActivity" , "version is $version")

            }

        }catch (e:Exception){
            e.printStackTrace()
        }


    }

    private fun parseJsonWithGSON( jsonData : String){

        val gson = Gson()
        val typeOf = object : TypeToken<List<App>>(){}.type
        val appList = gson.fromJson<List<App>>(jsonData,typeOf)

        for (app in appList){
            Log.d ("MainActivity", "id is ${app.id}")
            Log.d ("MainActivity", "name is ${app.name}")
            Log.d ("MainActivity", "version is ${app.version}")
        }


    }
}