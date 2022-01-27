package com.example.livedatatest

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider

class MainViewModelFactory(private val countReserved :Int):ViewModelProvider.Factory {
    //这里create方法执行时机与Activity的生命周期无关
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
//        TODO("Not yet implemented")
        return  MainViewModel(countReserved) as T

    }
}