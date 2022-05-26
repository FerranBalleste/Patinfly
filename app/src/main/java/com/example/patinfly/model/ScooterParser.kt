package com.example.patinfly.model

import android.util.Log
import com.google.gson.Gson
import org.json.JSONObject

class ScooterParser {
    companion object{
        fun parseFromJson( json: String):Scooters{
            val gson: Gson = Gson()
            var scooters: Scooters
            json.let {
                scooters = gson.fromJson<Scooters>(json, Scooters::class.java)
            }
            return scooters
        }
    }
}