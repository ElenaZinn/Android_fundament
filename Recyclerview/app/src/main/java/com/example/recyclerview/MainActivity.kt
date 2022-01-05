package com.example.recyclerview

import android.app.TaskStackBuilder
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import java.lang.StringBuilder
import java.util.ArrayList

class MainActivity : AppCompatActivity() {
    private val fruitList = ArrayList<Fruit>()
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
        initFruits()

//        val layoutManager = LinearLayoutManager (this)   普通的layout
        val layoutManager = StaggeredGridLayoutManager(3,StaggeredGridLayoutManager.VERTICAL)

        val recyclerView : RecyclerView = findViewById(R.id.recyclerView)
        recyclerView.layoutManager = layoutManager
        val adapter = FruitAdapter(fruitList)
        recyclerView.adapter = adapter
    }

    private fun initFruits(){
        repeat(5){
//            fruitList.add(Fruit(R.drawable.orange,"Orange"))
//            fruitList.add(Fruit(R.drawable.banana,"Banana"))
//            fruitList.add(Fruit(R.drawable.watermelon,"Watermelon"))
            fruitList.add(Fruit(R.drawable.orange,getRandomlenString("Orange")) )
            fruitList.add(Fruit(R.drawable.banana,getRandomlenString("Banana")))
            fruitList.add(Fruit(R.drawable.watermelon,getRandomlenString("Watermelon")))
        }
    }
    private  fun getRandomlenString(str: String) :String{
        val n =(1..20).random()
        val builder = StringBuilder()
        repeat(n){
            builder.append(str)
        }
        return builder.toString()
    }


}