package com.example.patinfly

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Build
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.widget.Button
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.patinfly.databinding.ActivityLoginBinding

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    @RequiresApi(Build.VERSION_CODES.M)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Splash Screen
        installSplashScreen()

        //Theme Configuration Settings
        val pref = PreferenceManager.getDefaultSharedPreferences(this).getString("theme", "Default")
        if(pref == "Light")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else if(pref == "Dark")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)

        //Set Content View
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        //Email Preference
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        val sharedPref: SharedPreferences = EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
        val username = sharedPref.getString(getString(R.string.preference_key_login_email), "")
        binding.loginEmail.setText(username)

        //Buttons
        binding.loginSignupbutton.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }
        binding.loginButton.setOnClickListener{
            //Store email
            val editor = sharedPref.edit()
            editor.putString(getString(R.string.preference_key_login_email), binding.loginEmail.text.toString())
            Log.i("LOGIN_ACTIVITY_EMAIL", binding.loginEmail.text.toString())
            editor.apply()

            //Start Navigation Drawer
            val intent = Intent(this, TutorialActivity::class.java)
            startActivity(intent)
        }
    }
}