package com.example.patinfly.developing

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.widget.TextView
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKeys
import com.example.patinfly.databinding.FragmentProfileBinding
import com.example.patinfly.model.Rents
import com.example.patinfly.model.Scooters
import com.example.patinfly.persitence.*
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2Mode
import java.util.*
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class DevUtils {
    companion object{
        //Encrypted SharedPrefs
        fun getEncryptedPrefs(context: Context): SharedPreferences {
            val masterKeyAlias: String = MasterKeys.getOrCreate(MasterKeys.AES256_GCM_SPEC)
            return EncryptedSharedPreferences.create(
                "secret_shared_prefs",
                masterKeyAlias,
                context,
                EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
                EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
            )
        }

        //Users
        fun insertUser(userDao: UserDao, user: User){
            Executors.newSingleThreadExecutor().execute(Runnable {
                try {
                    userDao.insertAll(user)
                }catch (e: SQLiteConstraintException){
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            })
        }

        fun verifyUser(userDao: UserDao, email: String, password:String): Boolean{
            var user: User
            val executor = Executors.newSingleThreadExecutor()
            val future: Future<Boolean> = executor.submit(Callable<Boolean> {
                var verifyPassword = false
                try {
                    user = userDao.findByEmail(email)
                    verifyPassword = verifyPassword(user.password, password)
                } catch (e: SQLiteConstraintException) {
                    Log.i("VERIFY USER", "e.toString()")
                }
                verifyPassword
            })
            return future.get()
        }

        private fun verifyPassword(encodedHash: String, password: String): Boolean{
            val argon2Kt = Argon2Kt()
            val result = argon2Kt.verify(Argon2Mode.ARGON2_I, encodedHash, password.toByteArray())
            return result
        }

        fun updateProfileView(userDao: UserDao, binding: FragmentProfileBinding, email: String){
            Executors.newSingleThreadExecutor().execute(Runnable {
                val user = userDao.findByEmail(email)
                binding.profileEmail.text = user.email
                binding.profileName.text = user.name
                binding.profileSurname.text = user.surname
                binding.profilePhone.text = user.phone.toString()
            })
        }

        fun deleteAllUsers(userDao: UserDao){
            Executors.newSingleThreadExecutor().execute(Runnable {
                userDao.deleteAll()
            })
        }

        //Scooters
        fun insertScooters(scooterDao: ScooterDao, scooters: Scooters){
            Executors.newSingleThreadExecutor().execute(Runnable {
                try {
                    scooterDao.insertAll(*scooters.scooters.toTypedArray())
                }catch (e: SQLiteConstraintException){
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            })
        }

        fun getScooters(scooterDao: ScooterDao): Scooters{
            val executor = Executors.newSingleThreadExecutor()
            var scooters = Scooters()
            val future: Future<Scooters> = executor.submit(Callable<Scooters> {
                try {
                    val scooterList = scooterDao.getActive()
                    scooters.scooters = LinkedList(scooterList)
                } catch (e: SQLiteConstraintException) {
                    Log.i("VERIFY USER", "e.toString()")
                }
                scooters
            })
            return future.get()
        }

        fun deleteAllScooters(scooterDao: ScooterDao){
            Executors.newSingleThreadExecutor().execute(Runnable {
                scooterDao.deleteAll()
            })
        }

        //History
        fun insertRents(rentDao: RentDao, rents: Rents){
            Executors.newSingleThreadExecutor().execute(Runnable {
                try {
                    rentDao.insertAll(*rents.rents.toTypedArray())
                }catch (e: SQLiteConstraintException){
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            })
        }

        fun insertRent(rentDao: RentDao, rent: Rent){
            Executors.newSingleThreadExecutor().execute(Runnable {
                try {
                    rentDao.insertAll(rent)
                }catch (e: SQLiteConstraintException){
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            })
        }

        fun getRents(rentDao: RentDao, email: String): Rents{
            val executor = Executors.newSingleThreadExecutor()
            var rents = Rents()
            val future: Future<Rents> = executor.submit(Callable<Rents> {
                try {
                    val rentList = rentDao.getAllUser(email)
                    rents.rents = LinkedList(rentList)
                } catch (e: SQLiteConstraintException) {
                    Log.i("VERIFY USER", "e.toString()")
                }
                rents
            })
            return future.get()
        }

        fun deleteAllRents(rentDao: RentDao){
            Executors.newSingleThreadExecutor().execute(Runnable {
                rentDao.deleteAll()
            })
        }

    }

}