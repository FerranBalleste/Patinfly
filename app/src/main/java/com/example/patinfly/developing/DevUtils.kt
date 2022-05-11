package com.example.patinfly.persitence

import android.database.sqlite.SQLiteConstraintException
import android.util.Log
import android.widget.TextView
import java.util.*
import java.util.concurrent.Executors

class DevUtils {
    companion object{
        fun deleteFakeData(userDao: UserDao){
            Executors.newSingleThreadExecutor().execute(Runnable {
                userDao.deleteAll()

            })
        }
        fun insertFakeData(userDao: UserDao){
            Executors.newSingleThreadExecutor().execute(Runnable {
                val user:User = User(0, "Tomas", "GiS")
                try {
                    userDao.insertAll(user)
                }catch (e: SQLiteConstraintException){
                    //Log.d(MainActivity::class.simpleName,"Unique value error")
                }
            })

        }

        fun plotDBUsers(userDao: UserDao) {
            var users: List<User> = LinkedList<User>()

            Executors.newSingleThreadExecutor().execute(Runnable {
                users = userDao.getAll()
                for (user in users) {
                    Log.d(
                        DevUtils::class.java.simpleName,
                        "User: (%d) %s %s".format(user.uid, user.firstName, user.lastName)
                    )
                }
            })
        }

        fun updateView(userDao: UserDao, view:TextView){
            var users: List<User> = LinkedList<User>()

            Executors.newSingleThreadExecutor().execute(Runnable {
                users = userDao.getAll()
                for (user in users) {
                    view.setText("User: (%d) %s %s".format(user.uid, user.firstName, user.lastName))
                }
            })
        }

    }

}