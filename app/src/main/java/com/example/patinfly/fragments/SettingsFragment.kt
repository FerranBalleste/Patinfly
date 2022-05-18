package com.example.patinfly.fragments

import android.content.Context
import android.content.Intent
import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.appcompat.app.AppCompatDelegate
import androidx.core.content.ContextCompat
import androidx.core.content.edit
import androidx.navigation.fragment.findNavController
import androidx.preference.Preference
import androidx.preference.PreferenceFragmentCompat
import androidx.preference.PreferenceManager
import com.example.patinfly.LoginActivity
import com.example.patinfly.R
import com.example.patinfly.base.AppConfig
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase


class SettingsFragment : PreferenceFragmentCompat() {


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val view = super.onCreateView(inflater, container, savedInstanceState)

        // To avoid problems with transparency
        view.setBackgroundColor(ContextCompat.getColor(context!!, R.color.main_background))

        findPreference<Preference>("logout_user")?.setOnPreferenceClickListener {
            startActivity(logoutIntent())
            true
        }

        findPreference<Preference>("change_password")?.setOnPreferenceClickListener {
            val navController = this.findNavController()
            navController.navigate(SettingsFragmentDirections.actionSettingsFragmentToPasswordFragment())
            true
        }

        findPreference<Preference>("delete_user")?.setOnPreferenceClickListener {
            deleteUser(context!!)
            startActivity(logoutIntent())
            true
        }

        return view
    }

    override fun onCreatePreferences(savedInstanceState: Bundle?, rootKey: String?) {
        setPreferencesFromResource(R.xml.root_preferences, rootKey)

        val sPref = PreferenceManager.getDefaultSharedPreferences(activity!!)
        val listener = SharedPreferences.OnSharedPreferenceChangeListener { prefs, key ->
            if (key == "theme") {
                when (prefs.getString("theme", "Default")) {
                    "Light" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_NO)
                    "Dark" -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_YES)
                    else -> AppCompatDelegate.setDefaultNightMode(AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM)
                }
            }
        }
        sPref.registerOnSharedPreferenceChangeListener(listener)
    }

    private fun deleteUser(context: Context){
        val sharedPref = DevUtils.getEncryptedPrefs(context)
        val storedEmail = sharedPref.getString(getString(R.string.preference_key_login_email), " ")
        val storedUuid = sharedPref.getLong(getString(R.string.preference_key_login_uuid), 0)
        val editor = sharedPref.edit()
        editor.putString(getString(R.string.preference_key_login_email), "")
        editor.putLong(getString(R.string.preference_key_login_uuid), 0)
        editor.apply()
        val database = AppDatabase.getInstance(context)
        DevUtils.deleteUser(database.userDao(), storedEmail!!, storedUuid)
    }

    private fun logoutIntent(): Intent{
        val logoutIntent = Intent(activity!!, LoginActivity::class.java)
        logoutIntent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        return logoutIntent
    }

}