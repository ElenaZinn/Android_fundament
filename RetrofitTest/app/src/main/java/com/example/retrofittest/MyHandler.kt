package com.example.retrofittest

import org.xml.sax.helpers.DefaultHandler
import java.lang.StringBuilder

class MyHandler : DefaultHandler() {

    private var nodeName = ""

    private lateinit var id: StringBuilder
    private lateinit var name: StringBuilder
}