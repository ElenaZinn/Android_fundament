package com.example.forcelogout

import android.content.Context
import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.CheckBox
import android.widget.EditText
import android.widget.Toast

class Login : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val login :Button = findViewById(R.id.login)

        //记住密码
        // activity中获取sharedpreferenc对象，使用getPreferences
        val prefs = getPreferences(Context.MODE_PRIVATE)
        val isRemember = prefs.getBoolean("remember_pd",false)
        if (isRemember){
            //从sharedpreferce中取值
            val account = prefs.getString("account"," ")
            val pd = prefs.getString("password"," ")

            //赋值
            val accountEdit :EditText = findViewById(R.id.accountEdit)
            val password :EditText = findViewById(R.id.passwordEdit)
            val rememberpress:CheckBox = findViewById(R.id.rememberpass)
            accountEdit.setText(account)
            password.setText(pd)
            rememberpress.isChecked = true

        }
        //记住密码


        login.setOnClickListener{
            val accountEdit :EditText = findViewById(R.id.accountEdit)
            val passwordedit:EditText  = findViewById(R.id.passwordEdit)
            val rememberpress:CheckBox = findViewById(R.id.rememberpass)

            val account =accountEdit.text.toString()
            val password= passwordedit.text.toString()


            if (account=="admin" && password =="123456"){
                //存数据
                    val editor = prefs.edit()
                if(rememberpress.isChecked){
                    editor.putBoolean("remember_pd",true)
                    editor.putString("account",account)
                    editor.putString("password",password)
                }else{
                    editor.clear()
                }
                editor.apply()
                //存数据


                val intent = Intent (this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"account invalid",Toast.LENGTH_SHORT).show()
            }

        }
    }
}