package com.example.livedatatest

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Transformations
import androidx.lifecycle.ViewModel

class MainViewModel(countReserved: Int) : ViewModel() {
    /**
     * 会暴露参数的写法
    val counter = MutableLiveData<Int>()

    init {
        counter.value = countReserved
    }

    fun plusOne() {
        val count = counter.value ?: 0
        counter.value = count + 1
    }

    fun clear() {
        counter.value = 0
    }
    */
    //计数
    val counter: LiveData<Int>
        get() = _counter

    private val _counter = MutableLiveData<Int>()

    init {
        _counter.value = countReserved
    }

    fun plusOne() {
        val count = counter.value ?: 0
        _counter.value = count + 1
    }

    fun clear() {
        _counter.value = 0
    }

    //User
    /**
     * map
    private val userLiveData = MutableLiveData<User>()

    val userName: LiveData<String> = Transformations.map(userLiveData){
        user ->"${user.firstName} ${user.lastName}"
    }
    */

//    fun getUser(userId).observe(this){User->}  getUser返回的是新LiveData实例，而这个写法一直观察老的LiveData实例
    //switchMap
    private val userIdLiveData = MutableLiveData<String>()

    val user: LiveData<User> = Transformations.switchMap(userIdLiveData){
        userId -> Repository.getUser(userId)
    }

    fun getUser(userId: String) {
       userIdLiveData.value = userId
    }

}
