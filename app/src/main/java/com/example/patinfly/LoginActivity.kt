package com.example.patinfly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button

class LoginActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)
        supportActionBar?.hide()

        val signupButton = findViewById<Button>(R.id.login_signupbutton)
        signupButton.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        val loginButton = findViewById<Button>(R.id.login_button)
        loginButton.setOnClickListener{
            val intent = Intent(this, NavigationDrawerActivity::class.java)
            startActivity(intent)
        }
    }
}