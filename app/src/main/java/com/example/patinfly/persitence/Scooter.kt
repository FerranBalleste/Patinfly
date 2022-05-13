package com.example.patinfly.persitence

import android.os.Bundle
import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity
data class Scooter(
    @PrimaryKey val uuid: String,
    @ColumnInfo(name = "name") val name: String,
    @ColumnInfo(name = "longitude") val longitude: Double,
    @ColumnInfo(name = "latitude") val latitude: Double,
    @ColumnInfo(name = "battery_level") val battery_level: Double,
    @ColumnInfo(name = "km_use") val km_use: Double,
    @ColumnInfo(name = "date_last_maintenance") val date_last_maintenance: String?,
    @ColumnInfo(name = "state") val state: String,
    @ColumnInfo(name = "on_rent") val onRent: Boolean
){
    val bundle: Bundle
        get(){
            val bundle = Bundle()
            bundle.putString("uuid", uuid)
            bundle.putString("name", name)
            bundle.putDouble("lat", latitude)
            bundle.putDouble("lon", longitude)
            bundle.putDouble("battery", battery_level)
            bundle.putString("date", date_last_maintenance)
            bundle.putDouble("distance", km_use)
            bundle.putBoolean("on_rent", onRent)
            return bundle
        }
}