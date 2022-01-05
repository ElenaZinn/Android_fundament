package com.example.database

import android.content.ContentProvider
import android.content.ContentValues
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri

class DatabaseProvider : ContentProvider() {

    private val bookDir = 0
    private val bookItem = 1
    private val categoryDir = 2
    private val categoryItem   = 3

    private val authority = "com.example.database.provider"

    private var dbHelper: MyDatabaseHelper? = null

    private val uriMatcher by lazy{

        val matcher = UriMatcher(UriMatcher.NO_MATCH)

        matcher.addURI(authority,"book",bookDir)
        matcher.addURI(authority,"book/#",bookItem)
        matcher.addURI(authority,"category",categoryDir)
        matcher.addURI(authority,"category/#",categoryItem)

        matcher

    }

    override fun onCreate() = context?.let{
        dbHelper = MyDatabaseHelper(it,"BookStore.db",3)
        true
    } ?: false


    override fun query(
        uri: Uri, projection: Array<String>?, selection: String?,
        selectionArgs: Array<String>?, sortOrder: String?
    ) = dbHelper?.let {

        val db = it.readableDatabase

        val cursor = when (uriMatcher.match(uri)){

            bookDir -> db.query("Book",projection,selection,selectionArgs,null,null,sortOrder)
            bookItem ->{
                val bookid = uri.pathSegments[1]
                db.query("book", projection, "id = ?", arrayOf(bookid),null,null ,sortOrder)
            }
            categoryDir -> db.query("category", projection, selection, selectionArgs,null,null,sortOrder)
            categoryItem ->{
                val categoryid = uri.pathSegments[1]
                db.query("category", projection, "id = ?", arrayOf(categoryid),null,null ,sortOrder)
            }
            else -> null
        }
        cursor
    }


    override fun insert(uri: Uri, values: ContentValues?) = dbHelper?.let {
        val db  = it.writableDatabase
        val uriReturn = when (uriMatcher.match(uri)){
            bookDir,bookItem -> {
                val newBookId = db.insert("Book",null,values)
                Uri.parse("content://$authority/book/$newBookId")
            }
            categoryDir,categoryItem -> {
                val newcategoryId = db.insert("category",null,values)
                Uri.parse("content://$authority/category/$newcategoryId")
            }
            else -> null
        }
        uriReturn
    }



    override fun update(
        uri: Uri, values: ContentValues?, selection: String?,
        selectionArgs: Array<String>?
    ) = dbHelper ?. let {

        val db = it.writableDatabase

        val updateRows = when (uriMatcher.match(uri)){


            bookDir ->db.update("book",values,selection,selectionArgs)
            bookItem ->{
                val bookid  = uri.pathSegments[1]
                db.update("book",values,"id = ?", arrayOf(bookid))
            }
            categoryDir ->db.update("category",values,selection,selectionArgs)
            categoryItem ->{
                val categoryid = uri.pathSegments[1]
                db.update("category",values,"id = ?", arrayOf(categoryid))
            }

            else -> 0
        }
        updateRows
    } ?: 0

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?) = dbHelper?.let {
        val db = it.writableDatabase
        val deleteRows = when( uriMatcher.match(uri)){

            bookDir -> db.delete("book",selection,selectionArgs)
            bookItem->{
                val bookid = uri.pathSegments[1]
                db.delete("book","id = ?", arrayOf(bookid))
            }
            categoryDir -> db.delete("category",selection,selectionArgs)
            categoryItem->{
                val categoryid = uri.pathSegments[1]
                db.delete("category","id = ?", arrayOf(categoryid))
            }

            else -> 0
        }

        deleteRows

    } ?: 0

    override fun getType(uri: Uri) = when (uriMatcher.match(uri)){

        bookDir -> "vnd.android.cursor.dir/vnd.com.example.database.provider.book"
        bookItem -> "vnd.android.cursor.item/vnd.com.example.database.provider.book"
        categoryDir -> "vnd.android.cursor.dir/vnd.com.example.database.provider.category"
        categoryItem -> "vnd.android.cursor.item/vnd.com.example.database.provider.category"

        else -> null
    }







}