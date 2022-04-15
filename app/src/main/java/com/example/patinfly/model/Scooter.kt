package com.example.patinfly.model

import android.os.Bundle

class Scooter(val name: String, val uuid: String, val lon: String, val lat: String, val battery: String, val distance: String, val lastMaintenanceDate: String, val state: String) {

    val bundle: Bundle
        get(){
            val bundle = Bundle()
            bundle.putString("name", name)
            bundle.putString("uuid", uuid)
            bundle.putString("lat", lat)
            bundle.putString("lon", lon)
            bundle.putString("battery", battery)
            bundle.putString("date", lastMaintenanceDate)
            bundle.putString("distance", distance)
            return bundle
        }
}