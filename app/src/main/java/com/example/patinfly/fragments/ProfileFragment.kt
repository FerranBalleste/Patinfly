package com.example.patinfly.fragments

import android.content.SharedPreferences
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.patinfly.R
import com.example.patinfly.databinding.FragmentProfileBinding
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.persitence.UserDao

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root

        context?.let {
            val database = AppDatabase.getInstance(it)
            userDao = database.userDao()
        }

        getEncryptedPrefs()?.let {
            val email = it.getString(getString(R.string.preference_key_login_email), "")
            if (email != null && userDao != null) {
                DevUtils.updateProfileView(userDao,binding,email)
            }
        }

        return view
    }

    private fun getEncryptedPrefs(): SharedPreferences? {
        val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
        context?.let {
            return EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                it,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }
        return null
    }
}