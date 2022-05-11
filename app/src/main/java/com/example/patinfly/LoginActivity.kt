package com.example.patinfly

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.patinfly.databinding.ActivityLoginBinding
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.persitence.DevUtils
import com.example.patinfly.persitence.UserDao

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        //Splash Screen
        installSplashScreen()

        //Theme Configuration Settings
        themeSettings()

        //Set Content View
        binding = ActivityLoginBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        //Email Preference
        val sharedPref = getEncryptedPrefs()
        val username = sharedPref.getString(getString(R.string.preference_key_login_email), "")
        binding.loginEmail.setText(username)

        //Buttons
        binding.loginSignupbutton.setOnClickListener{
            val intent = Intent(this, SignupActivity::class.java)
            startActivity(intent)
        }

        binding.loginButton.setOnClickListener{
            val editor = sharedPref.edit()
            editor.putString(getString(R.string.preference_key_login_email), binding.loginEmail.text.toString())
            //Log.i("LOGIN_ACTIVITY_EMAIL", binding.loginEmail.text.toString())
            editor.apply()

            val tutorial = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("tutorial_switch", true)
            val intent:Intent = if(tutorial)
                Intent(this, TutorialActivity::class.java)
            else
                Intent(this, NavigationDrawerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val dbSecondary = AppDatabase.getInstance(this)
        val userDatabaseSecondary: UserDao = dbSecondary.userDao()
        databaseSecondary(userDatabaseSecondary)

    }

    fun databaseSecondary(userDao: UserDao){
        //DevUtils.deleteFakeData(userDao)
        DevUtils.insertFakeData(userDao)
        DevUtils.plotDBUsers(userDao)
    }

    fun databaseSecondary2(userDao: UserDao, view:TextView){
        DevUtils.deleteFakeData(userDao)
        DevUtils.insertFakeData(userDao)
        DevUtils.plotDBUsers(userDao)
        DevUtils.updateView(userDao, view)
    }

    private fun themeSettings(){
        val pref = PreferenceManager.getDefaultSharedPreferences(this).getString("theme", "Default")
        if(pref == "Light")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
        else if(pref == "Dark")
            AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
    }

    private fun getEncryptedPrefs(): SharedPreferences{
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        return EncryptedSharedPreferences.create(
            "secret_shared_prefs",
            masterKeyAlias,
            this,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }
}