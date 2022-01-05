package com.example.contactstest

import android.annotation.SuppressLint
import android.content.pm.PackageManager
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.provider.ContactsContract
import android.widget.Adapter
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private val peopleList = ArrayList<people>()
    var recyclerView: RecyclerView ? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //recyclerview
        val layoutManager = LinearLayoutManager(this)
        recyclerView  = findViewById(R.id.contactView)
        recyclerView?.layoutManager = layoutManager
        val adapter = peopleAdapter(peopleList)
        recyclerView?.adapter = adapter
        //recyclerview


        //判断是否已经获取权限，若没有则索要权限，若有则读通信录
        if (ContextCompat.checkSelfPermission(this,android.Manifest.permission.READ_CONTACTS)!= PackageManager.PERMISSION_GRANTED){
            ActivityCompat.requestPermissions(this, arrayOf(android.Manifest.permission.READ_CONTACTS),1)
        }else{
            readContacts()
        }


    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        when(requestCode){
            1  -> {
                if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                    readContacts()
                }else {

                    //我直接被denied了，😓,错因：OnCreate中的权限写错了
                    Toast.makeText(this,"You denied the permission", Toast.LENGTH_SHORT).show()
                }
            }
        }
    }


    @SuppressLint("Range")
    private fun readContacts(){
        contentResolver.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,null,null,null,null)?.apply{
            while(moveToNext()){
                var displayname :String = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME))
                var number: String = getString(getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER))
                peopleList.add(people("$displayname\n$number"))
            }
            //刷新RecyclerView
//            peopleAdapter.notifyDataSetChanged()
            close()
        }

    }
}


