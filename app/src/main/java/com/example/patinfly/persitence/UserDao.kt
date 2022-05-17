package com.example.patinfly.persitence

import androidx.room.*

@Dao
interface UserDao {
    @Query("SELECT * FROM user")
    fun getAll(): List<User>

    @Query("SELECT * FROM user WHERE uuid = :uuid LIMIT 1")
    fun getUser(uuid: Long): List<User>

    @Query("SELECT * FROM user WHERE uuid IN (:userIds)")
    fun loadAllByIds(userIds: IntArray): List<User>

    @Query("SELECT * FROM user WHERE name LIKE :first AND " +
            "surname LIKE :last LIMIT 1")
    fun findByName(first: String, last: String): User

    @Query("SELECT * FROM user WHERE email LIKE :email LIMIT 1")
    fun findByEmail(email: String): User

    @Insert
    fun insertAll(vararg users: User)

    @Delete
    fun delete(user: User)

    @Query ("DELETE FROM user")
    fun deleteAll()

    @Update
    fun updateUser(user: User)
}