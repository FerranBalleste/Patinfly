package com.example.patinfly.persitence

import androidx.room.*

@Dao
interface RentDao {
    @Query("SELECT * FROM rent")
    fun getAll(): List<Rent>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg users: Rent)

    @Delete
    fun delete(user: User)

    @Query ("DELETE FROM rent")
    fun deleteAll()
}