package com.example.patinfly.model

import androidx.room.ColumnInfo
import androidx.room.PrimaryKey
import com.example.patinfly.persitence.Rent
import com.example.patinfly.persitence.Scooter
import java.util.*

data class VolleyRent(
    val uuid: String,
    val scooter: Scooter,
    val date_start: String,
    val date_stop: String?
)