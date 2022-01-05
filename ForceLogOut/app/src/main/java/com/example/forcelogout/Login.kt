package com.example.forcelogout

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import android.widget.EditText
import android.widget.Toast

class Login : BaseActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        val login :Button = findViewById(R.id.login)
        login.setOnClickListener{
            val accountEdit :EditText = findViewById(R.id.accountEdit)
            val passwordedit:EditText  = findViewById(R.id.passwordEdit)
            val account =accountEdit.text.toString()
            val password= passwordedit.text.toString()
            if (account=="admin" && password =="123456"){
                val intent = Intent (this,MainActivity::class.java)
                startActivity(intent)
                finish()
            }else{
                Toast.makeText(this,"account invalid",Toast.LENGTH_SHORT).show()
            }

        }
    }
}