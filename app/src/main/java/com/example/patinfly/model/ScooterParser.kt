package com.example.patinfly.model

import android.util.Log
import com.google.gson.Gson

class ScooterParser {
    companion object{
        fun parseFromJson( json: String):Scooters{
            val gson: Gson = Gson()

            var scooters: Scooters
            json.let {
                scooters = gson.fromJson<Scooters>(json, Scooters::class.java)
            }
            Log.i("SCOOTER FILE 0", scooters.scooters.toString())
            return scooters
        }
    }
}