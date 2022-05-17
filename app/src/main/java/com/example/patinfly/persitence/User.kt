package com.example.patinfly.persitence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class User(
    @PrimaryKey(autoGenerate = true) val uuid: Long = 0,
    @ColumnInfo(name = "name") var name: String,
    @ColumnInfo(name = "surname") var surname: String,
    @ColumnInfo(name = "email") var email: String,
    @ColumnInfo(name = "phone") var phone: Int,
    @ColumnInfo(name = "dni") val dni: String,
    @ColumnInfo(name = "password") val password: String
)
