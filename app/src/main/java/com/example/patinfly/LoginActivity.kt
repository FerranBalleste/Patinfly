package com.example.patinfly

import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.preference.PreferenceManager
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.patinfly.base.AppConfig
import com.example.patinfly.databinding.ActivityLoginBinding
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.model.Rents
import com.example.patinfly.model.Scooters
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.persitence.RentDao
import com.example.patinfly.persitence.ScooterDao
import com.example.patinfly.persitence.UserDao
import com.example.patinfly.repositories.HistoryRepository
import com.example.patinfly.repositories.ScooterRepository

class LoginActivity : AppCompatActivity() {
    private lateinit var binding: ActivityLoginBinding
    private lateinit var userDao: UserDao

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
            val email = binding.loginEmail.text.toString()
            val password = binding.loginPassword.text.toString()

            val correctPwd = DevUtils.verifyUser(userDao, email, password)
            if(!correctPwd){
                binding.loginResult.text = "INCORRECT PASSWORD"
                return@setOnClickListener
            }

            val editor = sharedPref.edit()
            editor.putString(getString(R.string.preference_key_login_email), email)
            editor.apply()

            val tutorial = PreferenceManager.getDefaultSharedPreferences(this).getBoolean("tutorial_switch", true)
            val intent: Intent = if (tutorial)
                Intent(this, TutorialActivity::class.java)
            else
                Intent(this, NavigationDrawerActivity::class.java)
            startActivity(intent)
        }
    }

    override fun onStart() {
        super.onStart()
        val database = AppDatabase.getInstance(this)
        userDao = database.userDao()
        fillDatabase(database.scooterDao(), database.rentDao())
    }

    private fun fillDatabase(scooterDao: ScooterDao, rentDao: RentDao){
        val scooters: Scooters = ScooterRepository.activeScooters(this, AppConfig.DEFAULT_SCOOTER_RAW_JSON_FILE)
        DevUtils.insertScooters(scooterDao, scooters)
        val rents: Rents = HistoryRepository.activeHistory(this, AppConfig.DEFAULT_HISTORY_RAW_JSON_FILE)
        DevUtils.insertRents(rentDao, rents)
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