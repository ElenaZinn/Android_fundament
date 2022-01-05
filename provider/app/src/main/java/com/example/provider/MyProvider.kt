package com.example.contactstest

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.content.UriMatcher.NO_MATCH
import android.database.Cursor
import android.net.Uri
import java.net.URI

class MyProvider : ContentProvider() {

    private val table1Dir = 0
    private val table1Item = 1
    private val table2Dir = 2
    private val table2Item = 3

    //UriMatcher.NO_MATCH表示不匹配任何路径的返回码
    private val uriMatcher = UriMatcher(UriMatcher.NO_MATCH)

    init{
        //  *： 任意长度字符
        //  #：任意长度数字

        //将期望匹配的内容URI格式传递进去
        uriMatcher.addURI("com.example.app.provider","table1",table1Dir)

        uriMatcher.addURI("com.example.app.provider","table1/#",table1Item)

        uriMatcher.addURI("com.example.app.provider","table2",table2Dir)

        uriMatcher.addURI("com.example.app.provider","table2/#",table2Item)

    }

    override fun onCreate(): Boolean {
        return false
    }

    override fun query(
        uri: Uri,
        p1: Array<out String>?,
        p2: String?,
        p3: Array<out String>?,
        p4: String?
    ): Cursor? {
        when (uriMatcher.match(uri)){
            table1Dir->{}
            table1Item->{}
            table2Dir->{}
            table2Item->{}
        }
    }

    override fun getType(uri: Uri)  = when ( uriMatcher.match(uri)){
        table1Dir ->"vnd.android.cursor.dir/vnd.com.example.app.provider.table1"
        table1Item ->"vnd.android.cursor.item/vnd.com.example.app.provider.table1"
        table2Dir ->"vnd.android.cursor.dir/vnd.com.example.app.provider.table2"
        table2Item ->"vnd.android.cursor.item/vnd.com.example.app.provider.table2"

    }


    override fun insert(p0: Uri, p1: ContentValues?): Uri? {
        return null
    }

    override fun delete(p0: Uri, p1: String?, p2: Array<out String>?): Int {
        return 0
    }

    override fun update(p0: Uri, p1: ContentValues?, p2: String?, p3: Array<out String>?): Int {
        return 0
    }
}