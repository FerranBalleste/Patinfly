package com.example.patinfly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.preference.PreferenceManager
import com.example.patinfly.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Splash Screen
        installSplashScreen()

        //Theme Configuration
        val pref = PreferenceManager.getDefaultSharedPreferences(this).getString("theme", "Default")
        if(pref == "Light")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else if(pref == "Dark")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

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