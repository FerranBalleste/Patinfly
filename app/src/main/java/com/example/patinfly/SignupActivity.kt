package com.example.patinfly

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.example.patinfly.databinding.ActivitySignupBinding
import com.example.patinfly.developing.DevUtils
import com.example.patinfly.persitence.AppDatabase
import com.example.patinfly.persitence.User
import com.example.patinfly.persitence.UserDao
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2KtResult
import com.lambdapioneer.argon2kt.Argon2Mode
import java.lang.Math.random

class SignupActivity : AppCompatActivity() {
    private lateinit var binding: ActivitySignupBinding
    private lateinit var userDao: UserDao

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivitySignupBinding.inflate(layoutInflater)
        val view = binding.root
        setContentView(view)
        supportActionBar?.hide()

        val database = AppDatabase.getInstance(this)
        userDao = database.userDao()

        binding.signupButton.setOnClickListener{
            val newUser = checkUserParams()
            newUser?.let {
                DevUtils.insertUser(userDao, newUser)
                val intent = Intent(this, LoginActivity::class.java)
                startActivity(intent)
            }
        }
    }

    private fun checkUserParams(): User?{
        val message = binding.signupMessage
        try {
            val password = binding.signupPassword1.text.toString()
            val password2 = binding.signupPassword2.text.toString()
            if(password != password2){
                message.text = getString(R.string.introduce_same)
                return null
            }
            val email = binding.signupEmail.text.toString()
            //Check email already exists
            val name = binding.signupName.text.toString()
            val surname = binding.signupSurname.text.toString()
            val phone = binding.signupPhone.text.toString().toInt()
            val dni = binding.signupDni.text.toString()
            val hash = DevUtils.hashPassword(password, name+((random()*100).toString())+surname)
            return User(0, name, surname, email, phone, dni, hash)
        }catch (e:Exception){
            message.text = getString(R.string.wrong_parameters)
            return null
        }
    }
}