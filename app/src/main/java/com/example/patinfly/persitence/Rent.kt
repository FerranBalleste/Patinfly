package com.example.patinfly.persitence

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Rent(
    @PrimaryKey val uuid: Int,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "battery_level") val batteryLevel: Double,
    @ColumnInfo(name = "km_use") val kmUse: Double,
    @ColumnInfo(name = "date_last_maintenance") val dateLastMaintenance: String,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "on_rent") val onRent: Boolean,
)