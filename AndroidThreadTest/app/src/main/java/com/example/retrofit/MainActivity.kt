package com.example.retrofit

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import android.widget.TextView
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val getAppData : Button = findViewById(R.id.getAppData)

        getAppData.setOnClickListener{

            //获取Service接口动态代理对象
            val retrofit = Retrofit.Builder()
                .baseUrl("http://172.25.160.8/")
                .addConverterFactory(GsonConverterFactory.create())
                .build()
            val appService = retrofit.create(AppService::class.java)
            //

            appService.getAppData().enqueue( object : Callback<List <App>>{
                override fun onResponse(call: Call<List<App>>, response: Response<List<App>>) {

                    val list = response.body()
                    var response: String = ""
                    if (list !=null){

                        for (app in list){

                            response  +=  "id is ${app.id} \n" + "name is ${app.name} \n"  + "version is ${app.version} \n"
                            Log.d("MainActivity","id is ${app.id}")
                            Log.d("MainActivity","name is ${app.name}")
                            Log.d("MainActivity","version is ${app.version}")
                        }
                        runOnUiThread{
                            val responsetext : TextView = findViewById( R.id.responseText)
                            responsetext.text = response
                        }
                    }
                }

                override fun onFailure(call: Call<List<App>>, t: Throwable) {
                    t.printStackTrace()
                }


            })
        }

    }
}