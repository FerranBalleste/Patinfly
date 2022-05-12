package com.example.patinfly.developing

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.widget.TextView
import com.example.patinfly.persitence.User
import com.example.patinfly.persitence.UserDao
import com.lambdapioneer.argon2kt.Argon2Kt
import com.lambdapioneer.argon2kt.Argon2Mode
import java.util.concurrent.Callable
import java.util.concurrent.Executors
import java.util.concurrent.Future

class DevUtils {
    companion object{
        fun deleteAllUsers(userDao: UserDao){
            Executors.newSingleThreadExecutor().execute(Runnable {
                userDao.deleteAll()
            })
        }

        fun insertUser(userDao: UserDao, user: User){
            Executors.newSingleThreadExecutor().execute(Runnable {
                //val user:User = User(0, "Tomas", "GiS")
                try {
                    userDao.insertAll(user)
                }catch (e: SQLiteConstraintException){
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            })

        }

        fun verifyUser(userDao: UserDao, email: String, password:String): Boolean{
            var user: User
            Log.i("VERIFY USER", "INICI")
            val executor = Executors.newSingleThreadExecutor()
            val future: Future<Boolean> = executor.submit(Callable<Boolean> {
                var verifyPassword = false
                try {
                    user = userDao.findByEmail(email)
                    Log.i("VERIFY USER", user.password.toString())
                    Log.i("VERIFY USER", password)
                    verifyPassword = verifyPassword(user.password, password)
                    Log.i("VERIFY USER RESULT 1", verifyPassword.toString())
                } catch (e: SQLiteConstraintException) {
                    Log.i("VERIFY USER", "e.toString()")
                }
                verifyPassword
            })
            val result = future.get()
            Log.i("VERIFY USER RESULT 2", result.toString())
            return result
        }

        private fun verifyPassword(encodedHash: String, password: String): Boolean{
            val argon2Kt = Argon2Kt()
            Log.i("VERIFY USER", "Verifying PASSWORD")
            val result = argon2Kt.verify(Argon2Mode.ARGON2_I, encodedHash, password.toByteArray())
            Log.i("VERIFY USER", "Result: " + result.toString())
            return result
        }

        /*
        fun plotDBUsers(userDao: UserDao) {
            var users: List<User> = LinkedList<User>()

            Executors.newSingleThreadExecutor().execute(Runnable {
                users = userDao.getAll()
                for (user in users) {
                    Log.d(
                        DevUtils::class.java.simpleName,
                        "User: (%d) %s %s".format(user.uid, user.name, user.surname)
                    )
                }
            })
        }
        fun updateView(userDao: UserDao, view:TextView){
            var users: List<User> = LinkedList<User>()

            Executors.newSingleThreadExecutor().execute(Runnable {
                users = userDao.getAll()
                for (user in users) {
                    view.setText("User: (%d) %s %s".format(user.uid, user.name, user.surname))
                }
            })
        }
        */
    }

}