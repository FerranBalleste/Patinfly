package com.example.patinfly.fragments

import android.content.SharedPreferences
import android.os.Bundle
import android.util.Log
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
import com.example.patinfly.persitence.RentDao
import com.example.patinfly.persitence.UserDao

class ProfileFragment : Fragment() {
    private lateinit var binding: FragmentProfileBinding
    private lateinit var userDao: UserDao
    private lateinit var rentDao: RentDao
    private var previousEmail: String = " "

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        binding = FragmentProfileBinding.inflate(layoutInflater)
        val view = binding.root

        context?.let {
            val database = AppDatabase.getInstance(it)
            userDao = database.userDao()
            rentDao = database.rentDao()

            DevUtils.getEncryptedPrefs(it)?.let {
                val email = it.getString(getString(R.string.preference_key_login_email), "")
                if (email != null && userDao != null) {
                    DevUtils.updateProfileView(userDao,binding,email)
                }
            }
        }
        return view
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.profileButton.setOnCheckedChangeListener(){_, isChecked ->
            if(isChecked){
                binding.profileName.setEnabled(true)
                binding.profileSurname.setEnabled(true)
                binding.profileEmail.setEnabled(true)
                binding.profilePhone.setEnabled(true)
                previousEmail = binding.profileEmail.text.toString()
            }else{
                Log.i("PROFILE", "clicked save")
                binding.profileName.setEnabled(false)
                binding.profileSurname.setEnabled(false)
                binding.profileEmail.setEnabled(false)
                binding.profilePhone.setEnabled(false)

                DevUtils.updateUser(userDao, binding, previousEmail)
                //If email is changed, rents might be lost if thy are not updated
                val newEmail = binding.profileEmail.text.toString()
                //DevUtils.updateRentEmails(rentDao,previousEmail, newEmail)
                previousEmail = newEmail
                val editor = DevUtils.getEncryptedPrefs(context!!).edit()
                editor.putString(getString(R.string.preference_key_login_email), newEmail)
                editor.apply()
            }
        }
    }
}