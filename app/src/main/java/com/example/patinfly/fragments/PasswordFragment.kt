package com.example.patinfly.fragments

import android.content.Intent
import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Toast
import com.example.patinfly.LoginActivity
import com.example.patinfly.R
import com.example.patinfly.databinding.FragmentPasswordBinding
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.persitence.UserDao
import kotlin.random.Random
import kotlin.random.nextInt

class PasswordFragment : Fragment() {
    private lateinit var binding: FragmentPasswordBinding
    private lateinit var userDao: UserDao

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        binding = FragmentPasswordBinding.inflate(layoutInflater)
        val view = binding.root
        userDao = AppDatabase.getInstance(context!!).userDao()

        val shPrefs = DevUtils.getEncryptedPrefs(context!!)
        val email = shPrefs.getString(getString(R.string.preference_key_login_email), " ")!!

        binding.passwdButton.setOnClickListener {
            val new = binding.passwdNew.text.toString()
            val new2 = binding.passwdRepeat.text.toString()
            val current = binding.passwdCurrent.text.toString()
            if(!inputCheck(new, new2, current, email))
                return@setOnClickListener
            val newHash = DevUtils.hashPassword(new, current+((Math.random() *100).toString()))
            DevUtils.updateUserPasswd(userDao, email, newHash)
            Toast.makeText(context, "Password Changed Correctly", Toast.LENGTH_LONG).show()
            startActivity(logoutIntent())
        }
        return view
    }

    private fun inputCheck(new:String, new2:String, current:String, email: String):Boolean{
        if(new != new2 || new == current) {
            binding.passwdInfo.text = getString(R.string.wrong_password)
            return false
        }
        if(DevUtils.verifyUser(userDao, email, current))
            return true
        return false
    }

    private fun logoutIntent(): Intent {
        val logoutIntent = Intent(activity!!, LoginActivity::class.java)
        logoutIntent.flags =  Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TASK
        Thread.sleep(1000)
        return logoutIntent
    }

}