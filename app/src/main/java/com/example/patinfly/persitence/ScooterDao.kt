package com.example.patinfly.persitence

import androidx.room.*

@Dao
interface ScooterDao {
    @Query("SELECT * FROM scooter")
    fun getAll(): List<Scooter>

    @Query("SELECT * FROM scooter WHERE state LIKE 'ACTIVE' AND on_rent = 0")
    fun getActive(): List<Scooter>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    fun insertAll(vararg scooters: Scooter)

    @Delete
    fun delete(scooter: Scooter)

    @Query ("DELETE FROM scooter")
    fun deleteAll()
}