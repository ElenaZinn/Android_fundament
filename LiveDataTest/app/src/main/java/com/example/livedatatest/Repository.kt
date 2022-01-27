package com.example.livedatatest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData

object Repository {

    fun getUser(userId: String): LiveData<User> {
        val liveData = MutableLiveData<User>()
        //根据传入的userId参数去服务器请求或者到数据库中查找相应的User对象
        //这里直接创建一个新的User对象   将传入的userId当作用户姓名来创建
        //每次调用getUser()方法都会返回一个新的LiveData实例
        liveData.value = User(userId,userId,0)
        return liveData
    }
}