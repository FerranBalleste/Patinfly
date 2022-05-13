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
                message.text = "Introduce the same password"
                return null
            }
            val email = binding.signupEmail.text.toString()
            //Check email already exists
            val name = binding.signupName.text.toString()
            val surname = binding.signupSurname.text.toString()
            val phone = binding.signupPhone.text.toString().toInt()
            val dni = binding.signupDni.text.toString()
            val hash = hashPassword(password, ((random()*100).toString()))
            return User(0, name, surname, email, phone, dni, hash)
        }catch (e:Exception){
            message.text = "Wrong Parameters"
            return null
        }
    }

    private fun hashPassword(password: String, salt: String): String{
        val argon2Kt = Argon2Kt()
        val hashResult : Argon2KtResult = argon2Kt.hash(Argon2Mode.ARGON2_I, password.toByteArray(), salt.toByteArray())
        val verification = argon2Kt.verify(Argon2Mode.ARGON2_I, hashResult.encodedOutputAsString(), password.toByteArray())
        Log.i("SIGNUP HASH", "Verification Signup:" + verification.toString())
        Log.i("SIGNUP HASH", "HASH Result" + hashResult.encodedOutputAsString())
        Log.i("SIGNUP HASH", "Password" + password)
        Log.i("SIGNUP HASH", "Salt" + salt)
        return hashResult.encodedOutputAsString()
    }
}