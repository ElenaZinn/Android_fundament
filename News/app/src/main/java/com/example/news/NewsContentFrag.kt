package com.example.news

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.LinearLayout
import android.widget.TextView
import androidx.appcompat.widget.LinearLayoutCompat
import androidx.fragment.app.Fragment

class NewsContentFrag : Fragment() {
    //将新闻界面变得可见
    var newsTitle: TextView? = null
    var newsContent: TextView? = null
    //    var contentLayout :LinearLayout? = null
    var contentLayout :LinearLayout? =null
    //加载.xml布局
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        /**通过view找到layout，findViewById 封装在view 下
         * MainActivity可以直接用findViewById，因为Activity提供了findViewById
         * NewsContentFrag 不可以直接用，因为Fragment没有提供这个方法
         * 需要返回父布局view， 用view调用findViewById
         * */
//       val view = inflater.inflate(R.layout.news_content_frag,container,false)
//        contentLayout=view.findViewById(R.id.visibility_layout)
//        return view
        return inflater.inflate(R.layout.news_content_frag,container,false)
    }

    fun refresh (title:String,content:String){
        contentLayout?.visibility = View.VISIBLE
        newsTitle?.text = title
        newsContent?.text = content

    }
}