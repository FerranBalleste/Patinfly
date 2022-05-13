package com.example.patinfly.persitence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import java.time.Duration

@Entity
data class Rent(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "start_time") val startTime: String,
    @ColumnInfo(name = "end-time") val endTime: String,
    @ColumnInfo(name = "duration") val duration: String,
    @ColumnInfo(name = "price") val price: String,
    @ColumnInfo(name = "distance") val distance: String,
)