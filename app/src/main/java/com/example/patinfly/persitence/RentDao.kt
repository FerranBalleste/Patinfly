package com.example.patinfly.persitence

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.Query

@Dao
interface RentDao {
    @Query("SELECT * FROM rent")
    fun getAll(): List<Rent>

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query ("DELETE FROM user")
    fun deleteAll()
}