package com.example.news

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import android.widget.Toast
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import java.lang.StringBuilder

class NewsTitleFrag: Fragment() {
    var newsTitleRV : RecyclerView ?=null

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val view = inflater.inflate(R.layout.news_title_frag, container, false)
        newsTitleRV= view.findViewById(R.id.newsTitleRV)
        return view
    }

    override fun onActivityCreated(savedInstanceState: Bundle?) {
        super.onActivityCreated(savedInstanceState)
        val layoutManager  = LinearLayoutManager(activity)
        newsTitleRV?.layoutManager = layoutManager
        val adapter = NewsAdapter(getNews())
        newsTitleRV?.adapter = adapter
    }


    inner class NewsAdapter(val newList:List<News>) : RecyclerView.Adapter<NewsAdapter.ViewHolder>(){
        inner class ViewHolder (view:View):RecyclerView.ViewHolder(view){
            val newsTitle :TextView = view.findViewById(R.id.news_title)
        }

        override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
            val view = LayoutInflater.from(parent.context).inflate(R.layout.news_item,parent,false)
            val holder  = ViewHolder(view)
            holder.itemView.setOnClickListener{
//                //切换成另一个fragment
                Toast.makeText(parent.context,"you clicked on me",Toast.LENGTH_SHORT).show()
                val news  = newList[holder.adapterPosition]
                val title = news.title
                val content = news.content
//                val intent = Intent(context,NewsContentFrag::class.java).apply{
//                    putExtra("news_title",news.title)
//                    putExtra("news_content",news.content)
//
//                }

                if (title !=null && content !=null){
                    val fragment = NewsContentFrag()
                    fragment.refresh(title,content)
                    replaceFragment(fragment)
//                    replaceFragment(NewsContentFrag())

                }

//                NewsContentActivity.actionStart(parent.context,news.title,news.content)  启动 activity
            }
                return holder
        }


        override fun onBindViewHolder(holder: NewsAdapter.ViewHolder, position: Int) {
            val news = newList[position]
            holder.newsTitle.text= news.title
        }

        override fun getItemCount()=newList.size



    }
    operator fun String.times(n:Int) = repeat(n)
    private  fun getRandomLengthString(str:String)= str * (1..20).random()
    private fun getNews() : List<News>{
        val newsList  = ArrayList<News>()
        for(i in 1..40){
            val news = News("This is news title $i",getRandomLengthString("This is news content $i"))
            newsList.add(news)
        }
        return newsList
    }
    private fun replaceFragment (fragment: Fragment){
        val manager = (activity as FragmentActivity).supportFragmentManager
        val transaction =manager.beginTransaction()
        transaction?.replace(R.id.newsTitlelayout,fragment)
        transaction.addToBackStack(null)
        transaction?.commit()//提交
    }
}