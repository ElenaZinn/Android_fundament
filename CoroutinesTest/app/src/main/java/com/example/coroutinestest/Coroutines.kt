package com.example.coroutinestest

import kotlinx.coroutines.*

fun main(){
    runBlocking {
        //test coroutines
        /**
        println("codes run in coroutine scope")
        delay(1500)
        println("codes run in coroutine scope finished")
        */


        //create Multiple coroutines
        /**
        launch {
            println("launch1")
            delay(1000)
            println("launch1 finished")
        }
        launch {
            println("launch2")
            delay(1000)
            println("launch2 finished")
        }
        */


        //coroutineScope函数，继承外部的协程的作用域并创建一个子协程，可以给任意挂起函数提供协程作用域了
        /**
        suspend fun printDot() = coroutineScope{
            launch{
                println(".")
                delay(1000)
            }
        }

        printDot()
        */


        //创建作用域常用写法
        /**
        val job = Job()
        val scope = CoroutineScope(job)
        scope.launch {
            //处理体逻辑
            println("创建作用域常用写法")
        }
        job.cancel()
        */


        //async函数
        /**
        runBlocking {
            val result = async {
                5 + 5
            }.await()
            println(result)
        }
       */

        //两个async串行，效率低
        /**
        runBlocking {
            val start = System.currentTimeMillis()
            val result = async {
                delay(1000)
                5 + 5
            }.await()
            val result2 = async {
                delay(1000)
                4 + 6
            }.await()
            println("result is ${result +  result2}.")
            val end = System.currentTimeMillis()
            println("cost ${end -start } ms.")
        }
        */

        //async并行：只在调用结果时使用await()获取
        /**
            val start = System.currentTimeMillis()
            val result = async {
                delay(1000)
                5 + 5
            }
            val result2 = async {
                delay(1000)
                4 + 6
            }
            println("result is ${ result.await() +  result2.await()}.")  //DIFF HERE
            val end = System.currentTimeMillis()
            println("cost ${end -start } ms.")
        }
         */


        //withContext()
        runBlocking {
            val result = withContext(Dispatchers.Default){
                5  + 5
            }
            println(result)
        }


    }
}