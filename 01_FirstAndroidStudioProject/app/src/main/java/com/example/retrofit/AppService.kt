package com.example.retrofit


import retrofit2.http.GET

interface AppService {

    @GET("get_data.json")
    fun getAppData() : retrofit2.Call<List<App>>
}