package com.example.forcelogout

import android.app.Activity

object ActivityCollector {
    private val activities = ArrayList<Activity>()
    fun addActivity (activity: Activity){
        activities.add(activity)
    }
    fun removeActivity (activity: Activity){
        activities.remove(activity)
    }
    fun finishALl(){
        for (activity in activities){
            if(!activity.isFinishing){
                activity.finish()
            }
        }
        activities.clear()
    }
}