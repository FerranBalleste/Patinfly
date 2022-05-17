package com.example.patinfly.developing

import android.content.Context
import android.content.SharedPreferences
import android.database.sqlite.SQLiteConstraintException
import android.util.Log
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
            Executors.newSingleThreadExecutor().execute {
                try {
                    userDao.insertAll(user)
                } catch (e: SQLiteConstraintException) {
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            }
        }

        fun getUser(userDao: UserDao, email: String): User?{
            var user: User?
            val executor = Executors.newSingleThreadExecutor()
            val future: Future<User?> = executor.submit(Callable<User?> {
                user = try {
                    userDao.findByEmail(email)
                } catch (e: SQLiteConstraintException) {
                    Log.i("VERIFY USER", "e.toString()")
                    null
                }
                user
            })
            return future.get()
        }

        fun verifyUser(userDao: UserDao, email: String, password:String): Boolean{
            var user: User
            val executor = Executors.newSingleThreadExecutor()
            val future: Future<Boolean> = executor.submit(Callable {
                var verifyPassword = false
                try {
                    user = userDao.findByEmail(email)
                    user?.let {
                        verifyPassword = verifyPassword(user.password, password)
                    }
                } catch (e: SQLiteConstraintException) {
                    Log.i("VERIFY USER", "e.toString()")
                }
                verifyPassword
            })
            return future.get()
        }

        private fun verifyPassword(encodedHash: String, password: String): Boolean {
            val argon2Kt = Argon2Kt()
            return argon2Kt.verify(Argon2Mode.ARGON2_I, encodedHash, password.toByteArray())
        }

        fun updateProfileView(userDao: UserDao, binding: FragmentProfileBinding, email: String){
            Executors.newSingleThreadExecutor().execute {
                val user = userDao.findByEmail(email)
                binding.profileEmail.setText(user.email)
                binding.profileName.setText(user.name)
                binding.profileSurname.setText(user.surname)
                binding.profilePhone.setText(user.phone.toString())
            }
        }

        fun updateUser(userDao: UserDao, binding: FragmentProfileBinding, email: String){
            Executors.newSingleThreadExecutor().execute {
                val user = userDao.findByEmail(email)
                user.email = binding.profileEmail.text.toString()
                user.name = binding.profileName.text.toString()
                user.surname = binding.profileSurname.text.toString()
                user.phone = binding.profilePhone.text.toString().toInt()
                userDao.updateUser(user)
            }
        }

        fun deleteAllUsers(userDao: UserDao){
            Executors.newSingleThreadExecutor().execute {
                userDao.deleteAll()
            }
        }

        //Scooters
        fun insertScooters(scooterDao: ScooterDao, scooters: Scooters){
            Executors.newSingleThreadExecutor().execute {
                try {
                    scooterDao.insertAll(*scooters.scooters.toTypedArray())
                } catch (e: SQLiteConstraintException) {
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            }
        }

        fun getScooters(scooterDao: ScooterDao): Scooters{
            val executor = Executors.newSingleThreadExecutor()
            var scooters = Scooters()
            val future: Future<Scooters> = executor.submit(Callable {
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
            Executors.newSingleThreadExecutor().execute {
                scooterDao.deleteAll()
            }
        }

        //History
        fun insertRents(rentDao: RentDao, rents: Rents){
            Executors.newSingleThreadExecutor().execute {
                try {
                    rentDao.insertAll(*rents.rents.toTypedArray())
                } catch (e: SQLiteConstraintException) {
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            }
        }

        fun insertRent(rentDao: RentDao, rent: Rent){
            Executors.newSingleThreadExecutor().execute {
                try {
                    rentDao.insertAll(rent)
                } catch (e: SQLiteConstraintException) {
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            }
        }

        fun getRents(rentDao: RentDao, uuid: Long): Rents{
            val executor = Executors.newSingleThreadExecutor()
            var rents = Rents()
            val future: Future<Rents> = executor.submit(Callable {
                try {
                    val rentList = rentDao.getAllRents(uuid)
                    rents.rents = LinkedList(rentList)
                } catch (e: SQLiteConstraintException) {
                    Log.i("VERIFY USER", "e.toString()")
                }
                rents
            })
            return future.get()
        }

        /*
        fun updateRentEmails(rentDao: RentDao, previousEmail:String, newEmail:String){
            Executors.newSingleThreadExecutor().execute(Runnable {
                val rentList = rentDao.findByEmail(previousEmail)
                rentList.forEach(){ it.user = newEmail }
                rentDao.updateRents(rentList)
            })
        }
        */

        fun deleteAllRents(rentDao: RentDao){
            Executors.newSingleThreadExecutor().execute {
                rentDao.deleteAll()
            }
        }

    }

}