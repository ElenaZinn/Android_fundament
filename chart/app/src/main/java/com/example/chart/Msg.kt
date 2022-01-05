package com.example.chart

import java.security.AccessControlContext

class Msg(val context: String, val type:Int) {
    companion object{
        const val TYPE_RECEIVED = 0
        const val TYPE_SENT = 1
    }
}